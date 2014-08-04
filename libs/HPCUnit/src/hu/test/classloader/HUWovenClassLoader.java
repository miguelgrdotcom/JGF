package hu.test.classloader;

import hu.dsl.AspectIngredient;
import hu.dsl.AspectRecipe;
import hu.dsl.HUSetRecipe;
import hu.dsl.HUSetRecipeMaker;
import hu.test.annotations.parameter.HUFriend;
import hu.test.annotations.parameter.HUSkip;
import hu.test.annotations.parameter.HUTrace;
import hu.test.nulloutput.NullOutput;
import hu.test.nulloutput.NullOutputRunner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.tools.ajc.Main;
import org.junit.runners.model.FrameworkMethod;

public class HUWovenClassLoader extends ClassLoader
{
    private static final String classPathProperty      = "java.class.path";
    
    private static String uidString;
    
    private final static String huWorkDirectory        = "HUWorkDirectory";    
    private final static String aspectsSrcDirectory    = "huAspects";

    private static final String prefix                 = "file:./";
    private static final String appJar                 = "app.jar";
    private static final String aspectsJar             = "aspects.jar";
    private static final String testJar                = "test.jar";
    private static final String newAppJar              = "newApp.jar";

    private URLClassLoader loader;
    private static HUWovenClassLoader instance;
    private static boolean isWoven = false;
    
    private static String huWorkPath () {
        return huWorkDirectory + uidString;
    }
    
    private static String aspectsSrcPath () {
        return huWorkPath() + "/" + aspectsSrcDirectory;
    }
    
    private static String appPath () {
        return appJar;
    }
    
    private static String aspectsPath () {
        return huWorkPath() + "/" + aspectsJar;
    }
    
    private static String testPath () {
        return testJar;
    }
    
    private static String newAppPath () {
        return huWorkPath() + "/" + newAppJar;
    }

    private HUWovenClassLoader() throws MalformedURLException
    {
        URL[] urls = new URL[]{new URL(prefix + newAppPath()), new URL(prefix + testPath()), new URL(prefix + aspectsPath())};
        loader = new URLClassLoader(urls, ClassLoader.getSystemClassLoader()); 
    }
    
    /**
     * @return a instance of HUWovenClassLoader
     * @throws MalformedURLException
     */
    public static HUWovenClassLoader getInstance() throws MalformedURLException {
        if(!isWoven) throw new RuntimeException("hutraces are not woven yet");
        if(instance != null)
            return instance;
        return instance = new HUWovenClassLoader();
    }
    
    public static Map<FrameworkMethod, List<HUSetRecipe>> weaveHUTrace(List<FrameworkMethod> methods) {
        if(isWoven) throw new RuntimeException("hutraces are not woven yet");

        uidString = new UID().toString().replace(":", "_");

        makeWorkDirectory();
        makeAspectsSrcDirectory();
        Map<FrameworkMethod, List<HUSetRecipe>> huTestParameters = makeAspectsFromHUTestMethods(methods);
        if(hasAspect(huTestParameters)) {
            compileAspects();
        }
        weaveAspects();
        //removeWorkDirectory();
        isWoven = true;
        return huTestParameters;
    }
    
    private static boolean hasAspect(Map<FrameworkMethod, List<HUSetRecipe>> huTestParameters) {
        for (Map.Entry<FrameworkMethod, List<HUSetRecipe>> entry : huTestParameters.entrySet())
            for (HUSetRecipe recipe : entry.getValue())
                if(recipe instanceof AspectRecipe)
                    return true;
        return false;
    }
    
    private static Map<FrameworkMethod, List<HUSetRecipe>> makeAspectsFromHUTestMethods (List<FrameworkMethod> methods) {
        Map<FrameworkMethod, List<HUSetRecipe>> huTestParameters = new HashMap<FrameworkMethod, List<HUSetRecipe>>();
        for (FrameworkMethod huMethod : methods)
        {
        	List<HUSetRecipe> params = new ArrayList<HUSetRecipe>();
        	List<String> fileNames   = makeAspectFileNames(huMethod);
        	List<String> friends     = makeFriends(huMethod, fileNames);
        	for (int i = 0; i < huMethod.getMethod().getParameterAnnotations().length; i++) {
        		boolean isSkipped = false;
        		String dslBody = "";
        		for (Annotation annotation : huMethod.getMethod().getParameterAnnotations()[i]) {
        			if(annotation instanceof HUTrace)
        				dslBody = ((HUTrace)annotation).value();
        			if(annotation instanceof HUSkip)
        				isSkipped = true;
        		}
        		params.add(makeAspectFileFromDSL(new AspectIngredient(fileNames.get(i), dslBody, isSkipped, removeSelfFromFriends(friends, fileNames.get(i)))));
        		huTestParameters.put(huMethod, params);
			}
        }
        return huTestParameters;
    }
    
    private static List<String> makeAspectFileNames(FrameworkMethod method) {
    	List<String> list = new ArrayList<String>();
    	for (int i = 0; i < method.getMethod().getParameterAnnotations().length; i++) {
    		list.add("HUTraceRecipe$" + method.getName() + "$" + i);
		}
    	return list;
    }

    private static List<String> makeFriends(FrameworkMethod method, List<String> fileNames) {
    	List<String> list = new ArrayList<String>();
    	for (int i = 0; i < method.getMethod().getParameterAnnotations().length; i++) {
    		for (Annotation annotation:method.getMethod().getParameterAnnotations()[i]) {
    			if (annotation instanceof HUFriend)
    				list.add(fileNames.get(i));
			}
    	}
    	return list;
    }
    
    private static List<String> removeSelfFromFriends(List<String> friends, String self) {
    	List<String> list = new ArrayList<String>();
    	for (String friend : friends) {
    		if(!self.equals(friend))
    			list.add(friend);
		}
    	return list;
    }
    
    private static void makeWorkDirectory () {
        File directory = new File(huWorkPath());
        directory.mkdir();
    }
    
    private static void makeAspectsSrcDirectory () {
        File directory = new File(aspectsSrcPath());
        directory.mkdir();
    }
    
    public static void removeWorkDirectory() {
        File directory = new File(huWorkPath());
        if(directory.exists()) {
        	clean(directory);
        }
    }

    /**
     * ファイル/ディレクトリを削除する。
     * @param root 削除対象
     */
    public static final void clean( File root ) {
    	if ( root == null || !root.exists() ) { return; }
    	if ( root.isFile() ) {
    		if ( root.exists() && !root.delete() ) {
    			root.deleteOnExit();
    		}
    	} else {
    		File[] list = root.listFiles();
    		for ( int i = 0 ; i < list.length ; i++ ) {
    			clean( list[i] );
    		}
    		if ( root.exists() && !root.delete() ) {
    			root.deleteOnExit();
    		}
    	}
    }
    
    private static HUSetRecipe makeAspectFileFromDSL (AspectIngredient aspectIngredient) {
    	HUSetRecipe recipe = HUSetRecipeMaker.makeHUSetRecipe(aspectIngredient);

    	if(recipe instanceof AspectRecipe) {
    		AspectRecipe aspectRecipe = (AspectRecipe)recipe;
    		String aspectName    = aspectRecipe.fileName();
    		String body          = aspectRecipe.body();
    		File file = new File(aspectsSrcPath() + "/" + aspectName +".aj");
    		PrintWriter pw = null;
    		try
    		{
    			pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
    			pw.write(body);
    		}
    		catch (IOException e)
    		{
    			System.out.println("fail to write : " + aspectName);
    			e.printStackTrace();
    		}
    		finally {
    			pw.close();
    		}
    	}
    	return recipe;
    }

     private static void compileAspects() {
         NullOutput.run(new NullOutputRunner() {
             @Override
             public void run()
             {
                 Main ajc = new Main(); 
                 ajc.runMain(new String[]{
                         "-1.6",
                         "-Xmx256M",
                         "-sourceroots", aspectsSrcPath(), 
                         "-classpath", System.getProperty(classPathProperty)+":"+appPath(), 
                         "-outjar", aspectsPath()}, false);
             }

         });
     }

     private static void weaveAspects() {
         Main ajc = new Main(); 
         ajc.runMain(new String[]{
                 "-1.6",
                 "-Xmx256M",
                 "-inpath", appPath(), 
                 "-aspectpath", aspectsPath(), 
                 "-classpath", System.getProperty(classPathProperty), 
                 "-outjar", newAppPath()}, false);      
         isWoven = true;
     }

    
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return loader.loadClass(name);
    }
}

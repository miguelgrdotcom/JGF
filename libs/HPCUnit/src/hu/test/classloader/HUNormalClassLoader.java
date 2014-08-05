package hu.test.classloader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class HUNormalClassLoader extends ClassLoader
{
    private static final String prefix    = "file:./";
    private static final String appJar    = "app.jar";
    private static final String testJar   = "test.jar";
 
    private URLClassLoader loader;
   
    public HUNormalClassLoader() 
    {
		try {
			URL[] urls = new URL[]{new URL(prefix + appJar), new URL(prefix + testJar)};
			loader = new URLClassLoader(urls, ClassLoader.getSystemClassLoader()); 
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
    }
    
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return loader.loadClass(name);
    }
}
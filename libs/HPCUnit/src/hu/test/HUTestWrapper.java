package hu.test;

import static hu.test.HUTestUtil.tracerView;
import hu.dsl.AspectRecipe;
import hu.dsl.HUSetRecipe;
import hu.dsl.Range;
import hu.dsl.RangesRecipe;
import hu.list.HUSetMaker;
import hu.test.classloader.HUWovenClassLoader;
import hu.tracer.HUTraceRecipe;
import hu.tracer.HUTracerView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;

import mpi.MPI;
import scala.collection.immutable.List;

@SuppressWarnings("unchecked")
public class HUTestWrapper
{

   public static void $huTest(Object target, Method targetMethod, HUSetRecipe[] recipes) throws Throwable {

        HUWovenClassLoader loader = HUWovenClassLoader.getInstance();
        Class<?> hutracer = loader.loadClass("hu.tracer.HUTracer");
        Method getTracerView = hutracer.getDeclaredMethod("getGatheredTracerView", new Class<?>[]{});
        tracerView = (HUTracerView)getTracerView.invoke(null, new Object[]{});

        try
        {
            targetMethod.invoke(target, getParams(recipes));
        }
        catch (InvocationTargetException e)
        {
            throw e.getCause();
        }
    }
   
    public static void $huDistributedTest(Object target, Method targetMethod, HUSetRecipe[] recipes) throws Throwable {

        HUWovenClassLoader loader = HUWovenClassLoader.getInstance();
        Class<?> hutracer = loader.loadClass("hu.tracer.HUTracer");
        Method getTracerView = hutracer.getDeclaredMethod("getGatheredTracerView", new Class<?>[]{});
        tracerView = (HUTracerView)getTracerView.invoke(null, new Object[]{});

        System.out.println("run " + targetMethod.getName() + " @ rank " + MPI.COMM_WORLD.Rank());

        try
        {
            targetMethod.invoke(target, getParams(recipes));
        }
        catch (InvocationTargetException e)
        {
            throw e.getCause();
        }
    }

    public static void $huGatheredTest(Object target, Method targetMethod, HUSetRecipe[] recipes) throws Throwable {

        HUWovenClassLoader loader = HUWovenClassLoader.getInstance();
        Class<?> hutracer = loader.loadClass("hu.tracer.HUTracer");
        Method getTracerView = hutracer.getDeclaredMethod("getGatheredTracerView", new Class<?>[]{});
        tracerView = (HUTracerView)getTracerView.invoke(null, new Object[]{});

        if(MPI.COMM_WORLD.Rank() == 0) {
            System.out.println("run " + targetMethod.getName() + " @ rank 0");
            try
            {
                targetMethod.invoke(target, getParams(recipes));
            }
            catch (InvocationTargetException e)
            {
                throw e.getCause();
            }
        }
    }

   
    private static Object[] getParams(HUSetRecipe[] recipes)
            throws MalformedURLException, ClassNotFoundException,
            SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {

        HUWovenClassLoader loader = HUWovenClassLoader.getInstance();
               Object[] params = new Object[recipes.length];
        Class<HUSetMaker> husetMaker = (Class<HUSetMaker>)loader.loadClass("hu.list.HUSetMaker");

        for (int i = 0; i < params.length; i++) {
            HUSetRecipe recipe = recipes[i];
            if(recipe instanceof AspectRecipe) {
                AspectRecipe aspectRecipe = (AspectRecipe)recipe;
                Class<HUTraceRecipe<?>> clazz = (Class<HUTraceRecipe<?>>)loader.loadClass(aspectRecipe.fileName());
                Method aspectOf = clazz.getDeclaredMethod("aspectOf", new Class<?>[]{});
                HUTraceRecipe<?> traceRecipe = (HUTraceRecipe<?>)aspectOf.invoke(null, new Object[]{});
                params[i] = tracerView.get(traceRecipe); 
            }
            else if(recipe instanceof RangesRecipe) {
                RangesRecipe rangesRecipe = (RangesRecipe)recipe;
                List<Range> list = rangesRecipe.ranges();
                String methodName = "make" + list.size();
                Method make = husetMaker.getDeclaredMethod(methodName, new Class<?>[]{int[].class});

                int[] minMax = new int[list.size()*2];

                int count = 0;
                while(!list.isEmpty()) {
                    Range range = list.head();
                    minMax[count*2]   = range.min(); 
                    minMax[count*2+1] = range.max(); 
                    list = list.drop(1);
                    count++;
                }

                params[i] = make.invoke(null, new Object[]{minMax});
           }
        }
        return params;
   }
    

}

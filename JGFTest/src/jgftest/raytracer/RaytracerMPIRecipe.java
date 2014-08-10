package jgftest.raytracer;


import hu.list.tuple.HUTuple2;
import hu.tracer.HUTraceRecipe;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author yoshiki
 */
@Aspect
public class RaytracerMPIRecipe extends HUTraceRecipe<HUTuple2<Integer, Integer>> {
    public static final Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("JGFTest");    
    
    @Before("call (void jgf.mpi.raytracer.RayTracer.HUKernel(int, int, int, int)) && args(x, y, r, size)")
    public void beforeHUKernel(int x, int y, int r, int size) {
        //logger.info("mpi ({},{})@{}=>({},{})",i,j,r,i+(r*size),j);
        logger.info("mpi ({},{})@{}",x, y,r);

        add(new HUTuple2<Integer, Integer>(x, y));
    }

    @Override
    protected HUTraceRecipe<HUTuple2<Integer, Integer>>[] friends() {
        return new HUTraceRecipe[]{};
    }
}

package jgftest.raytracer;


import hu.tracer.HUTraceRecipe;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import ch.qos.logback.classic.Logger;
import hu.list.tuple.HUTuple2;
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
public class RaytracerParallelRecipe extends HUTraceRecipe<HUTuple2<Integer, Integer>> {
    public static final Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("JGFTest");    

    @Before("call (void jgf.parallel.raytracer.RayTracer.HUKernel(int, int)) && args(x, y)")
    public void beforeHUKernel(int x, int y) {
        logger.info("parallel ({},{})", x, y);
        add(new HUTuple2<Integer, Integer>(x,y));
    }

    @Override
    protected HUTraceRecipe<HUTuple2<Integer, Integer>>[] friends() {
        return new HUTraceRecipe[]{};
    }
}

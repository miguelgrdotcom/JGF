package jgftest;


import hu.list.tuple.HUTuple1;
import hu.tracer.HUTraceRecipe;
import jgf.parallel.series.SeriesTest;
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
public class SORParallelRecipe extends HUTraceRecipe<HUTuple2<Integer, Integer>> {
    public static final Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("JGFTest");    

    @Before("call (void jgf.parallel.sor.SORRunner.HUKernel(int, int)) && args(i, j)")
    public void beforeHUKernel(int i, int j) {
        logger.info("parallel {} {}", i, j);
        add(new HUTuple2<Integer, Integer>(i, j));
    }

    @Override
    protected HUTraceRecipe<HUTuple2<Integer, Integer>>[] friends() {
        return new HUTraceRecipe[]{};
    }
    
//    @After("call (void jgf.parallel.series.JGFSeriesBench.JGFrun(int))")
//    public void postProcess() {
//        HUTracerView traceView = HUTracer.getTracerView();
//        HUSet<HUTuple1<Integer>> set = (HUSet<HUTuple1<Integer>>) traceView.get(this);
//    }
}

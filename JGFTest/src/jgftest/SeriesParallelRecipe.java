package jgftest;


import hu.list.tuple.HUTuple1;
import hu.tracer.HUTraceRecipe;
import jgf.parallel.series.SeriesTest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import ch.qos.logback.classic.Logger;
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
public class SeriesParallelRecipe extends HUTraceRecipe<HUTuple1<Integer>> {
    public static final Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("JGFTest");    

    @Before("call (void jgf.parallel.series.SeriesRunner.HUKernel(int, double)) && args(i, d)")
    public void beforeHUKernel(int i, double d) {
        logger.info("parallel {}", i);
        add(new HUTuple1<Integer>(i));
    }

    @Override
    protected HUTraceRecipe<HUTuple1<Integer>>[] friends() {
        return new HUTraceRecipe[]{};
    }
    
//    @After("call (void jgf.parallel.series.JGFSeriesBench.JGFrun(int))")
//    public void postProcess() {
//        HUTracerView traceView = HUTracer.getTracerView();
//        HUSet<HUTuple1<Integer>> set = (HUSet<HUTuple1<Integer>>) traceView.get(this);
//    }
}

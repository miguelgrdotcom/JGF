package jgftest;


import hu.list.HUSet;
import hu.list.tuple.HUTuple1;
import hu.tracer.HUTracer;
import hu.tracer.HUTraceRecipe;
import hu.tracer.HUTracerView;
import jgf.parallel.series.SeriesTest;
import org.aspectj.lang.annotation.After;
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
public class SeriesSequentialRecipe extends HUTraceRecipe<HUTuple1<Integer>> {
    public static final Logger logger = LoggerFactory.getLogger(SeriesTest.class);

    
    @Before("call (void jgf.sequential.series.SeriesTest.HUKernel(int, double)) && args(i, d)")
    public void beforeHUKernel(int i, double d) {
        logger.info("{}",i);
        add(new HUTuple1<Integer>(i));
    }

    @Override
    protected HUTraceRecipe<HUTuple1<Integer>>[] friends() {
        return new HUTraceRecipe[]{};
    }
    
//    @After("call (void jgf.sequential.series.JGFSeriesBench.JGFrun(int))")
//    public void postProcess() {
//        HUTracerView traceView = HUTracer.getTracerView();
//        HUSet<HUTuple1<Integer>> set = (HUSet<HUTuple1<Integer>>) traceView.get(this);
//    }
}

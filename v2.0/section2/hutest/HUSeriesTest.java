package hutest;


import hu.list.HUSet;
import hu.list.tuple.HUTuple1;
import hu.tracer.HUTracer;
import hu.tracer.HUTraceRecipe;
import hu.tracer.HUTracerView;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author yoshiki
 */
@Aspect
public class HUSeriesTest extends HUTraceRecipe {
    
    @Before("call (void series.SeriesTest.HUKernel(int, double)) && args(i, d)")
    public void beforeHUKernel(int i, double d) {
        add(new HUTuple1(i));
    }

    @Override
    protected HUTraceRecipe[] friends() {
        return new HUTraceRecipe[]{};
    }
    
    @After("call (void series.JGFSeriesBench.JGFrun(int))")
    public void postProcess() {
        HUTracerView traceView = HUTracer.getTracerView();
        HUSet<HUTuple1<Integer>> set = traceView.get(this);
        for (HUTuple1<Integer> s: set) {
            System.out.println(s);
        }
    }
}

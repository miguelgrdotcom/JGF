package jgftest;


import hu.list.tuple.HUTuple1;
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
public class SORMPIRecipe extends HUTraceRecipe<HUTuple2<Integer, Integer>> {
    public static final Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("JGFTest");    
    
    @Before("call (void jgf.mpi.sor.SOR.HUKernel(int, int, int, int)) && args(i, j, r, size)")
    public void beforeHUKernel(int i, int j, int r, int size) {
        //logger.info("mpi ({},{})@{}=>({},{})",i,j,r,i+(r*size),j);
        logger.info("mpi ({},{})@{}",i+(r*size),j,r);

        add(new HUTuple2<Integer, Integer>(i+(r*size), j));
    }

    @Override
    protected HUTraceRecipe<HUTuple2<Integer, Integer>>[] friends() {
        return new HUTraceRecipe[]{};
    }
    
//    @After("call (void jgf.sequential.series.JGFSeriesBench.JGFrun(int))")
//    public void postProcess() {
//        HUTracerView traceView = HUTracer.getTracerView();
//        HUSet<HUTuple1<Integer>> set = (HUSet<HUTuple1<Integer>>) traceView.get(this);
//    }
}

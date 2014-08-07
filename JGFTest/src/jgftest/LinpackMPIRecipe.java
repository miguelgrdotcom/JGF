package jgftest;


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
public class LinpackMPIRecipe extends HUTraceRecipe<HUTuple2<Integer, Integer>> {
    public static final Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("JGFTest");    
    
    @Before("call (void jgf.mpi.lufact.Linpack.HUKernel(int, int, int, int)) && args(k, j, r, size)")
    public void beforeHUKernel(int k, int j, int r, int size) {
        //logger.info("mpi ({},{})@{}",k+(r*size),j,r);
        logger.info("mpi ({},{})@{}",k,j,r);        
        add(new HUTuple2<Integer, Integer>(k, j));
    }

    @Override
    protected HUTraceRecipe<HUTuple2<Integer, Integer>>[] friends() {
        return new HUTraceRecipe[]{};
    }
}

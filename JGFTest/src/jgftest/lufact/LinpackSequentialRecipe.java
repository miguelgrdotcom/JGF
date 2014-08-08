package jgftest.lufact;


import hu.list.tuple.HUTuple1;
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
public class LinpackSequentialRecipe extends HUTraceRecipe<HUTuple2<Integer,Integer>> {
    public static final Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("JGFTest");    

    
    @Before("call (void jgf.sequential.lufact.Linpack.HUKernel(int,int)) && args(k,j)")
    public void beforeHUKernel(int k,int j) {
        logger.info("sequential {} {}",k, j);
        add(new HUTuple2<Integer, Integer>(k,j));
    }

    @Override
    protected HUTraceRecipe<HUTuple2<Integer, Integer>>[] friends() {
        return new HUTraceRecipe[]{};
    }    
}

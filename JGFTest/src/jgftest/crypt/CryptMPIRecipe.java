package jgftest.crypt;


import hu.list.tuple.HUTuple1;
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
public class CryptMPIRecipe extends HUTraceRecipe<HUTuple1<Integer>> {
    public static final Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("JGFTest");    
    
    @Before("call (void jgf.mpi.crypt.IDEATest.HUKernel(int, int, int)) && args(i, r, length)")
    public void beforeHUKernel(int i, int r, int length) {
        //logger.info("mpi {}@{}:{}",i,r,i+(r*length));
        add(new HUTuple1<Integer>(i+(r*length)));
    }

    @Override
    protected HUTraceRecipe<HUTuple1<Integer>>[] friends() {
        return new HUTraceRecipe[]{};
    }

}

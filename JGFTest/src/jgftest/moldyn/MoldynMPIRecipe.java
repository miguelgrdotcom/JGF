package jgftest.moldyn;


import hu.list.tuple.HUTuple2;
import hu.tracer.HUTraceRecipe;
import jgftest.moldyn.Tag;
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
public class MoldynMPIRecipe extends HUTraceRecipe<HUTuple2<Tag, Integer>> {
    public static final Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("JGFTest");    
    
    @Before("call (void jgf.mpi.moldyn.md.HUKernel(jgftest.moldyn.Tag, int, int, int)) && args(tag, i, r, size)")
    public void beforeHUKernel(Tag tag, int i, int r, int size) {
        //logger.info("mpi {} {}", tag, i+(r*size));
        switch (tag) {
            case domove: add(new HUTuple2<Tag, Integer>(tag, i+(r*size)));
                break;
            case force: add(new HUTuple2<Tag, Integer>(tag, i));
                break;
            case mkekin: add(new HUTuple2<Tag, Integer>(tag, i+(r*size)));
                break;
            case velavg: add(new HUTuple2<Tag, Integer>(tag, i+(r*size)));
                break;
        }
    }

    @Override
    protected HUTraceRecipe<HUTuple2<Tag, Integer>>[] friends() {
        return new HUTraceRecipe[]{};
    }
}

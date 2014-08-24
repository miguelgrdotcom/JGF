package jgftest.moldyn;


import hu.list.tuple.HUTuple1;
import hu.tracer.HUTraceRecipe;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import ch.qos.logback.classic.Logger;
import hu.list.tuple.HUTuple2;
import jgftest.moldyn.Tag;
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
public class MoldynSequentialRecipe extends HUTraceRecipe<HUTuple2<Tag, Integer>> {
    public static final Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("JGFTest");    

    
    @Before("call (void jgf.sequential.moldyn.md.HUKernel(jgftest.moldyn.Tag, int)) && args(tag, i)")
    public void beforeHUKernel(Tag tag, int i) {
        //logger.info("sequential {} {}",tag, i);
        switch (tag) {
            case domove: add(new HUTuple2<Tag, Integer>(tag, i));
                break;
            case force: add(new HUTuple2<Tag, Integer>(tag, i));
                break;
            case mkekin: add(new HUTuple2<Tag, Integer>(tag, i));
                break;
            case velavg: add(new HUTuple2<Tag, Integer>(tag, i));
                break;
        }
    }    
}

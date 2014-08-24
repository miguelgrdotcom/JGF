package jgftest.moldyn;


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
 * force以外のdomove, mkekin, velavgはsequential実行xスレッド数分だけ無駄に実行されている
 * プロファイラでも確認済み
 * @author yoshiki
 */
@Aspect
public class MoldynParallelRecipe extends HUTraceRecipe<HUTuple2<Tag, Integer>> {
    public static final Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("JGFTest");    

    @Before("call (void jgf.parallel.moldyn.mdRunner.HUKernel(jgftest.moldyn.Tag, int)) && args(tag, i)")
    public void beforeHUKernel(Tag tag, int i) {
        //logger.info("parallel {} {}", tag, i);
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

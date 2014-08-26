/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jgftest;

import ch.qos.logback.classic.Level;
import hu.list.HUSet;
import hu.list.tuple.HUTuple1;
import hu.list.tuple.HUTuple2;
import hu.tracer.HUGatheredTracerView;
import hu.tracer.HUTracer;
import hu.tracer.HUTracerView;
import static jgftest.JGFTest.logger;
import jgftest.moldyn.Tag;
import jgftest.moldyn.MoldynMPIRecipe;
import jgftest.moldyn.MoldynParallelRecipe;
import jgftest.moldyn.MoldynSequentialRecipe;
import mpi.MPI;
import org.aspectj.lang.Aspects;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 *
 * @author yoshiki
 */
public class MoldynTest extends JGFTest {
    
    public MoldynTest() {
        logger.setLevel(Level.INFO);
    }

        
    /**
     * *******
     * 初期化 *******
     */
    public void init(int _num_threads, int _rank, int _nprocess) {
        super.init(_num_threads, _rank, _nprocess);
        size-=1;
        
        sequential3 = new jgf.sequential.moldyn.JGFMolDynBench();
        parallel3 = new jgf.parallel.moldyn.JGFMolDynBench(_num_threads);
        if (!(_rank == 0 && _nprocess == 0)) {
            distributed3 = new jgf.mpi.moldyn.JGFMolDynBench(_nprocess, _rank);
        }
    }
    
    public void testMoldynParallel() {
        init(num_threads, rank, nprocess);

        runSequential(null);
        runParallel(null);
        
        long begin = System.currentTimeMillis();

        HUTracerView traceView = HUTracer.getTracerView();
        HUSet<HUTuple2<Tag, Integer>> s = (HUSet<HUTuple2<Tag, Integer>>) traceView.get(Aspects.aspectOf(MoldynSequentialRecipe.class));
        HUSet<HUTuple2<Tag, Integer>> p = (HUSet<HUTuple2<Tag, Integer>>) traceView.get(Aspects.aspectOf(MoldynParallelRecipe.class));
        HUSet<HUTuple2<Tag, Integer>> diff = s.difference(p);
        
        assertThat(diff.isEmpty(), is(true));
        long end = System.currentTimeMillis();        
        logger.info("time = " + (end-begin));        
        
        logger.info("sequential size = {}, parallel size = {}", s.size(), p.size());
        logger.info(getMemoryInfo());
        

        //assertThat(s, is(p));
    }    
    
    
    
    public void $mpi_testMPI(String[] args) {
        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int nprocess = MPI.COMM_WORLD.Size();
        init(num_threads, rank, nprocess);

        if (rank == 0) {
            runSequential(args);
        }
        runMPI(args);
        
        long begin = System.currentTimeMillis();

        HUTracerView traceView = HUTracer.getTracerView();
        HUSet<HUTuple2<Tag, Integer>> s = (HUSet<HUTuple2<Tag, Integer>>) traceView.get(Aspects.aspectOf(MoldynSequentialRecipe.class));
        HUGatheredTracerView gatherdTraceView = HUTracer.getGatheredTracerView();
        HUSet<HUTuple2<Tag, Integer>> d = (HUSet<HUTuple2<Tag, Integer>>) gatherdTraceView.get(Aspects.aspectOf(MoldynMPIRecipe.class));
        HUSet<HUTuple2<Tag, Integer>> dd = gatherdTraceView.gather(d);

        if (rank == 0) {
            logger.info("{}", s.size());
        }
        logger.info("{}@{}", d.size(), rank);

        if (rank == 0) {
            HUSet<HUTuple2<Tag, Integer>> diff = s.difference(dd);
            logger.info("diff = {}", diff.size());

            assertThat(diff.isEmpty(), is(true));
            long end = System.currentTimeMillis();        
            logger.info("time = " + (end-begin));            

            //assertThat(s.difference(dd).size(), is(0));
        }
        MPI.Finalize();
    }
}

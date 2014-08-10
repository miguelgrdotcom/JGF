/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jgftest;

import hu.list.HUSet;
import hu.list.tuple.HUTuple1;
import hu.tracer.HUGatheredTracerView;
import hu.tracer.HUTracer;
import hu.tracer.HUTracerView;
import static jgftest.JGFTest.logger;
import jgftest.series.SeriesMPIRecipe;
import jgftest.series.SeriesParallelRecipe;
import jgftest.series.SeriesSequentialRecipe;
import mpi.MPI;
import org.aspectj.lang.Aspects;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author yoshiki
 */
public class SeriesTest extends JGFTest {
    
    public SeriesTest() {
    }

        
    /**
     * *******
     * 初期化 *******
     */
    public void init(int _num_threads, int _rank, int _nprocess) {
        super.init(_num_threads, _rank, _nprocess);

        sequential2 = new jgf.sequential.series.JGFSeriesBench();
        parallel2 = new jgf.parallel.series.JGFSeriesBench(num_threads);
        if (!(_rank == 0 && _nprocess == 0)) {
            distributed2 = new jgf.mpi.series.JGFSeriesBench(_nprocess, _rank);
        }
    }
    
    public void testSeriesParallel() {
        init(num_threads, rank, nprocess);
        runSequential(null);
        runParallel(null);

        HUTracerView traceView = HUTracer.getTracerView();
        HUSet<HUTuple1<Integer>> s = (HUSet<HUTuple1<Integer>>) traceView.get(Aspects.aspectOf(SeriesSequentialRecipe.class));

        HUSet<HUTuple1<Integer>> p = (HUSet<HUTuple1<Integer>>) traceView.get(Aspects.aspectOf(SeriesParallelRecipe.class));

        HUSet<HUTuple1<Integer>> diff = s.difference(p);
        assertThat(s, is(p));
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

        HUTracerView traceView = HUTracer.getTracerView();
        HUSet<HUTuple1<Integer>> s = (HUSet<HUTuple1<Integer>>) traceView.get(Aspects.aspectOf(SeriesSequentialRecipe.class));
        HUGatheredTracerView gatherdTraceView = HUTracer.getGatheredTracerView();
        HUSet<HUTuple1<Integer>> d = (HUSet<HUTuple1<Integer>>) gatherdTraceView.get(Aspects.aspectOf(SeriesMPIRecipe.class));
        HUSet<HUTuple1<Integer>> dd = gatherdTraceView.gather(d);

        if (rank == 0) {
            logger.info("{}", s.size());
        }
        logger.info("{}@{}", d.size(), rank);

        if (rank == 0) {
            logger.info("diff = {}", s.difference(dd).size());
            logger.info("diff: {}", s.difference(dd));
            assertThat(s.difference(dd).size(), is(0));
        }
        MPI.Finalize();
    }    
}

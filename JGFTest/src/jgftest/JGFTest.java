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
import jgf.parallel.series.SeriesTest;
import mpi.MPI;
import org.aspectj.lang.Aspects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 *
 * @author yoshiki
 */
public class JGFTest {

    private jgf.sequential.series.JGFSeriesBench sequential = null;
    private jgf.parallel.series.JGFSeriesBench parallel = null;
    private jgf.mpi.series.JGFSeriesBench distributed = null;

    private int size = 0;
    private int rank = 0;
    private int nprocess = 0;
    private int num_threads = 0;

    public static final Logger logger = LoggerFactory.getLogger(SeriesTest.class);

    public JGFTest(int _size) {
        size = _size;
    }

    private void initialize(int _num_threads, int _rank, int _nprocess) {
        rank = _rank;
        nprocess = _nprocess;
        num_threads = num_threads;

        sequential = new jgf.sequential.series.JGFSeriesBench();
        //parallel = new jgf.parallel.series.JGFSeriesBench(_num_threads);
        distributed = new jgf.mpi.series.JGFSeriesBench(_nprocess, _rank);
    }

    public void runSeries0(String[] argv) {
        sequential.JGFrun(size);
//        parallel.JGFrun(size);
        distributed.JGFrun(size);

    }

    public void runSeries(String[] argv) {
        distributed.JGFrun(size);
    }

    public void testSeries() {
        HUTracerView traceView = HUTracer.getTracerView();
        HUSet<HUTuple1<Integer>> s = (HUSet<HUTuple1<Integer>>) traceView.get(Aspects.aspectOf(SeriesSequentialRecipe.class));

//        HUSet<HUTuple1<Integer>> p = (HUSet<HUTuple1<Integer>>) traceView.get(Aspects.aspectOf(SeriesParallelRecipe.class));
//        System.out.println(p.size());
//        HUSet<HUTuple1<Integer>> diff = s.difference(p);
//        System.out.println(diff);
//        assertThat(s, is(p));
        HUGatheredTracerView gatherdTraceView = HUTracer.getGatheredTracerView();
        HUSet<HUTuple1<Integer>> d = (HUSet<HUTuple1<Integer>>) gatherdTraceView.get(Aspects.aspectOf(SeriesMPIRecipe.class));
        HUSet<HUTuple1<Integer>> dd = gatherdTraceView.gather(d);
        
        
        if ( rank == 0 ) {
            logger.warn("{}", s.size());
            logger.warn("{}@{}", dd.size(), rank);
            assertThat(s, is(dd));     

        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        JGFTest launcher = new JGFTest(0);

        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int nprocess = MPI.COMM_WORLD.Size();
        launcher.initialize(16, rank, nprocess);

        if (rank == 0) {
            launcher.runSeries0(args);
        } else {
            launcher.runSeries(args);
        }
        launcher.testSeries();

        MPI.Finalize();

    }
}

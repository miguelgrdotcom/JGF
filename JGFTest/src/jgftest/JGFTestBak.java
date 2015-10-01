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
import jgf.sequential.sparsematmult.JGFSparseMatmultBench;
import jgf.sequential.util.JGFSection2;
import jgftest.series.SeriesMPIRecipe;
import jgftest.series.SeriesParallelRecipe;
import jgftest.series.SeriesSequentialRecipe;
import jgftest.sparsematmult.MatmultParallelRecipe;
import jgftest.sparsematmult.MatmultSequentialRecipe;
import junit.framework.TestCase;
import mpi.MPI;
import mpi.MPIException;
import org.aspectj.lang.Aspects;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author yoshiki
 */
public class JGFTestBak extends TestCase {

    private jgf.sequential.util.JGFSection2 sequential = null;
    private jgf.parallel.util.JGFSection2 parallel = null;
    private jgf.mpi.util.JGFSection2 distributed = null;

    private int size = 0;
    private int rank = 0;
    private int nprocess = 0;
    private int num_threads = 0;
    private String[] args = {};

    public static final Logger logger = LoggerFactory.getLogger(JGFTestBak.class);

    JGFTestBak(String name) {
        super(name);
    }

    JGFTestBak(int _size) {
        size = _size;
    }

    private void initSeries(int _num_threads, int _rank, int _nprocess) {
        rank = _rank;
        nprocess = _nprocess;
        num_threads = num_threads;

        sequential = new jgf.sequential.series.JGFSeriesBench();
        parallel = new jgf.parallel.series.JGFSeriesBench(num_threads);
        if (!(_rank == 0 || _nprocess == 0)) {
            distributed = new jgf.mpi.series.JGFSeriesBench(_nprocess, _rank);
        }
    }

    private void initMatmult(int _num_threads, int _rank, int _nprocess) {
        rank = _rank;
        nprocess = _nprocess;
        num_threads = _num_threads;

        sequential = new jgf.sequential.sparsematmult.JGFSparseMatmultBench();
        parallel = new jgf.parallel.sparsematmult.JGFSparseMatmultBench(_num_threads);
        if (!(_rank == 0 || _nprocess == 0)) {
            distributed = new jgf.mpi.sparsematmult.JGFSparseMatmultBench(_nprocess, _rank);
        }
    }

    public void runSeries0(String[] argv) {
        sequential.JGFrun(size);
        parallel.JGFrun(size);
    }

    public void runMatmult0(String[] argv) {
        sequential.JGFrun(size);
        parallel.JGFrun(size);
    }

    public void runSeries(String[] argv) throws MPIException {
        distributed.JGFrun(size);
    }

    public void verifySeries() {
        HUTracerView traceView = HUTracer.getTracerView();
        HUSet<HUTuple1<Integer>> s = (HUSet<HUTuple1<Integer>>) traceView.get(Aspects.aspectOf(SeriesSequentialRecipe.class));

        HUSet<HUTuple1<Integer>> p = (HUSet<HUTuple1<Integer>>) traceView.get(Aspects.aspectOf(SeriesParallelRecipe.class));

        HUSet<HUTuple1<Integer>> diff = s.difference(p);
        assertThat(s, is(p));

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
    }

    private void setArgs(String[] _args) {
        args = _args;
    }

    public void _testSeriesParallel() {
        initSeries(16, 0, 0);
        runSeries0(null);
        HUTracerView traceView = HUTracer.getTracerView();
        HUSet<HUTuple1<Integer>> s = (HUSet<HUTuple1<Integer>>) traceView.get(Aspects.aspectOf(SeriesSequentialRecipe.class));

        HUSet<HUTuple1<Integer>> p = (HUSet<HUTuple1<Integer>>) traceView.get(Aspects.aspectOf(SeriesParallelRecipe.class));

        HUSet<HUTuple1<Integer>> diff = s.difference(p);
        assertThat(s, is(p));
    }

    public void testMatmultParallel() {
        initMatmult(16, 0, 0);
        runMatmult0(null);

        HUTracerView traceView = HUTracer.getTracerView();
        HUSet<HUTuple1<Integer>> s = (HUSet<HUTuple1<Integer>>) traceView.get(Aspects.aspectOf(MatmultSequentialRecipe.class));

        HUSet<HUTuple1<Integer>> p = (HUSet<HUTuple1<Integer>>) traceView.get(Aspects.aspectOf(MatmultParallelRecipe.class));

        HUSet<HUTuple1<Integer>> diff = s.difference(p);
        logger.warn("sequential size = {}, parallel size = {}", s.size(), p.size());
        
        assertThat(s, is(p));
    }

    public void _testSeriesAll() throws MPIException {
        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int nprocess = MPI.COMM_WORLD.Size();
        initSeries(16, rank, nprocess);

        if (rank == 0) {
            runSeries0(args);
        }
        runSeries(args);
        verifySeries();

        MPI.Finalize();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JGFTestBak launcher = new JGFTestBak(0);
//        launcher.runSeries0(args);
//        launcher.testSeriesParallel();
//        
//        launcher.runMatmult0(args);
//        launcher.testMatmultParallel();
//        
//        launcher.setArgs(args);     
//        launcher._testSeriesAll();
    }
}

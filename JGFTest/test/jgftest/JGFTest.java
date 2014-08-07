/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgftest;

import ch.qos.logback.classic.Level;
import hu.list.HUSet;
import hu.list.tuple.HUTuple1;
import hu.tracer.HUTracer;
import hu.tracer.HUTracerView;
import org.aspectj.lang.Aspects;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;
import hu.list.tuple.HUTuple2;
import hu.tracer.HUGatheredTracerView;
import mpi.MPI;
import static org.hamcrest.CoreMatchers.is;
import org.slf4j.LoggerFactory;

/**
 *
 * @author yoshiki
 */
public class JGFTest {

    private jgf.sequential.util.JGFSection2 sequential = null;
    private jgf.parallel.util.JGFSection2 parallel = null;
    private jgf.mpi.util.JGFSection2 distributed = null;

    private int size = 0;
    private int rank = 0;
    private int nprocess = 0;
    private int num_threads = 0;
    private String[] args = {};

    public static final Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("JGFTest");

    public JGFTest() {
        /* Used by JUnit */
        setUpClass();
    }

    @BeforeClass
    public static void setUpClass() {
        logger.setLevel(Level.INFO);
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * *******
     * テスト実行 *******
     */
    //@Test
    public void testSeriesParallel() {
        initSeries(16, 0, 0);
        runSequential(null);
        runParallel(null);

        HUTracerView traceView = HUTracer.getTracerView();
        HUSet<HUTuple1<Integer>> s = (HUSet<HUTuple1<Integer>>) traceView.get(Aspects.aspectOf(SeriesSequentialRecipe.class));

        HUSet<HUTuple1<Integer>> p = (HUSet<HUTuple1<Integer>>) traceView.get(Aspects.aspectOf(SeriesParallelRecipe.class));

        HUSet<HUTuple1<Integer>> diff = s.difference(p);
        assertThat(s, is(p));
    }

    //@Test
    public void testMatmulParallel() {
        initMatmult(16, 0, 0);
        runSequential(null);
        runParallel(null);

        HUTracerView traceView = HUTracer.getTracerView();
        HUSet<HUTuple1<Integer>> s = (HUSet<HUTuple1<Integer>>) traceView.get(Aspects.aspectOf(MatmultSequentialRecipe.class));
        HUSet<HUTuple1<Integer>> p = (HUSet<HUTuple1<Integer>>) traceView.get(Aspects.aspectOf(MatmultParallelRecipe.class));

        HUSet<HUTuple1<Integer>> diff = s.difference(p);
        logger.info("sequential size = {}, parallel size = {}", s.size(), p.size());

        assertThat(s, is(p));
    }

    //@Test
    public void testSORParallel() {
        initSOR(16, 0, 0);
        runSequential(null);
        runParallel(null);

        HUTracerView traceView = HUTracer.getTracerView();
        HUSet<HUTuple2<Integer, Integer>> s = (HUSet<HUTuple2<Integer, Integer>>) traceView.get(Aspects.aspectOf(SORSequentialRecipe.class));
        HUSet<HUTuple2<Integer, Integer>> p = (HUSet<HUTuple2<Integer, Integer>>) traceView.get(Aspects.aspectOf(SORParallelRecipe.class));

        HUSet<HUTuple2<Integer, Integer>> diff = s.difference(p);
        logger.info("sequential size = {}, parallel size = {}", s.size(), p.size());

        assertThat(s, is(p));
    }
    
    //@Test
    public void testCryptParallel() {
        initCrypt(16, 0, 0);
        runSequential(null);
        runParallel(null);

        HUTracerView traceView = HUTracer.getTracerView();
        HUSet<HUTuple1<Integer>> s = (HUSet<HUTuple1<Integer>>) traceView.get(Aspects.aspectOf(CryptSequentialRecipe.class));
        HUSet<HUTuple1<Integer>> p = (HUSet<HUTuple1<Integer>>) traceView.get(Aspects.aspectOf(CryptParallelRecipe.class));

        HUSet<HUTuple1<Integer>> diff = s.difference(p);
        logger.info("sequential size = {}, parallel size = {}", s.size(), p.size());

        assertThat(s, is(p));
    }

    @Test
    public void testLinpackParallel() {
        initLinpack(16, 0, 0);
        runSequential(null);
        runParallel(null);

        HUTracerView traceView = HUTracer.getTracerView();
        HUSet<HUTuple2<Integer,Integer>> s = (HUSet<HUTuple2<Integer,Integer>>) traceView.get(Aspects.aspectOf(LinpackSequentialRecipe.class));
        HUSet<HUTuple2<Integer,Integer>> p = (HUSet<HUTuple2<Integer,Integer>>) traceView.get(Aspects.aspectOf(LinpackParallelRecipe.class));

        HUSet<HUTuple2<Integer,Integer>> diff = s.difference(p);
        logger.info("sequential size = {}, parallel size = {}", s.size(), p.size());

        assertThat(s, is(p));
    }
    
    
    public void $mpi_testSeriesMPI(String[] args) {
        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int nprocess = MPI.COMM_WORLD.Size();
        initSeries(16, rank, nprocess);

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

    public void $mpi_testMatmulMPI(String[] args) {
        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int nprocess = MPI.COMM_WORLD.Size();
        initMatmult(16, rank, nprocess);

        if (rank == 0) {
            runSequential(args);
        }
        runMPI(args);

        HUTracerView traceView = HUTracer.getTracerView();
        HUSet<HUTuple1<Integer>> s = (HUSet<HUTuple1<Integer>>) traceView.get(Aspects.aspectOf(MatmultSequentialRecipe.class));
        HUGatheredTracerView gatherdTraceView = HUTracer.getGatheredTracerView();
        HUSet<HUTuple1<Integer>> d = (HUSet<HUTuple1<Integer>>) gatherdTraceView.get(Aspects.aspectOf(MatmultMPIRecipe.class));
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

    public void $mpi_testSORMPI(String[] args) {
        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int nprocess = MPI.COMM_WORLD.Size();
        initSOR(16, rank, nprocess);

        if (rank == 0) {
            runSequential(args);
        }
        runMPI(args);

        HUTracerView traceView = HUTracer.getTracerView();
        HUSet<HUTuple2<Integer, Integer>> s = (HUSet<HUTuple2<Integer, Integer>>) traceView.get(Aspects.aspectOf(SORSequentialRecipe.class));
        HUGatheredTracerView gatherdTraceView = HUTracer.getGatheredTracerView();
        HUSet<HUTuple2<Integer, Integer>> d = (HUSet<HUTuple2<Integer, Integer>>) gatherdTraceView.get(Aspects.aspectOf(SORMPIRecipe.class));
        HUSet<HUTuple2<Integer, Integer>> dd = gatherdTraceView.gather(d);

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

    public void $mpi_testCryptMPI(String[] args) {
        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int nprocess = MPI.COMM_WORLD.Size();
        initCrypt(16, rank, nprocess);

        if (rank == 0) {
            runSequential(args);
        }
        runMPI(args);

      
        HUTracerView traceView = HUTracer.getTracerView();
        HUSet<HUTuple1<Integer>> s = (HUSet<HUTuple1<Integer>>) traceView.get(Aspects.aspectOf(CryptSequentialRecipe.class));
        HUGatheredTracerView gatherdTraceView = HUTracer.getGatheredTracerView();
        HUSet<HUTuple1<Integer>> d = (HUSet<HUTuple1<Integer>>) gatherdTraceView.get(Aspects.aspectOf(CryptMPIRecipe.class));
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
    
    
    public void $mpi_testLinpackMPI(String[] args) {
        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int nprocess = MPI.COMM_WORLD.Size();
        initLinpack(16, rank, nprocess);

        if (rank == 0) {
            runSequential(args);
        }
        runMPI(args);

        HUTracerView traceView = HUTracer.getTracerView();
        HUSet<HUTuple2<Integer, Integer>> s = (HUSet<HUTuple2<Integer, Integer>>) traceView.get(Aspects.aspectOf(LinpackSequentialRecipe.class));
        HUGatheredTracerView gatherdTraceView = HUTracer.getGatheredTracerView();
        HUSet<HUTuple2<Integer, Integer>> d = (HUSet<HUTuple2<Integer, Integer>>) gatherdTraceView.get(Aspects.aspectOf(LinpackMPIRecipe.class));
        HUSet<HUTuple2<Integer, Integer>> dd = gatherdTraceView.gather(d);

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
    
        
    /**
     * *******
     * 初期化 *******
     */
    public void initSeries(int _num_threads, int _rank, int _nprocess) {
        rank = _rank;
        nprocess = _nprocess;
        num_threads = _num_threads;

        sequential = new jgf.sequential.series.JGFSeriesBench();
        parallel = new jgf.parallel.series.JGFSeriesBench(num_threads);
        if (!(_rank == 0 && _nprocess == 0)) {
            distributed = new jgf.mpi.series.JGFSeriesBench(_nprocess, _rank);
        }
    }

    public void initMatmult(int _num_threads, int _rank, int _nprocess) {
        rank = _rank;
        nprocess = _nprocess;
        num_threads = _num_threads;

        sequential = new jgf.sequential.sparsematmult.JGFSparseMatmultBench();
        parallel = new jgf.parallel.sparsematmult.JGFSparseMatmultBench(_num_threads);
        if (!(_rank == 0 && _nprocess == 0)) {
            distributed = new jgf.mpi.sparsematmult.JGFSparseMatmultBench(_nprocess, _rank);
        }
    }

    public void initSOR(int _num_threads, int _rank, int _nprocess) {
        rank = _rank;
        nprocess = _nprocess;
        num_threads = _num_threads;

        sequential = new jgf.sequential.sor.JGFSORBench();
        parallel = new jgf.parallel.sor.JGFSORBench(_num_threads);
        if (!(_rank == 0 && _nprocess == 0)) {
            distributed = new jgf.mpi.sor.JGFSORBench(_nprocess, _rank);
        }
    }
    
    public void initCrypt(int _num_threads, int _rank, int _nprocess) {
        rank = _rank;
        nprocess = _nprocess;
        num_threads = _num_threads;

        sequential = new jgf.sequential.crypt.JGFCryptBench();
        parallel = new jgf.parallel.crypt.JGFCryptBench(_num_threads);
        if (!(_rank == 0 && _nprocess == 0)) {
            distributed = new jgf.mpi.crypt.JGFCryptBench(_nprocess, _rank);
        }
    }    
    
    public void initLinpack(int _num_threads, int _rank, int _nprocess) {
        rank = _rank;
        nprocess = _nprocess;
        num_threads = _num_threads;

        sequential = new jgf.sequential.lufact.JGFLUFactBench();
        parallel = new jgf.parallel.lufact.JGFLUFactBench(_num_threads);
        if (!(_rank == 0 && _nprocess == 0)) {
            distributed = new jgf.mpi.lufact.JGFLUFactBench(_nprocess, _rank);
        }
    }       
    

    /**
     * *******
     * 対象実行 *******
     */
    public void runSequential(String[] argv) {
        sequential.JGFrun(size);
    }

    public void runParallel(String[] argv) {
        parallel.JGFrun(size);
    }

    public void runMPI(String[] argv) {
        distributed.JGFrun(size);
    }

    /**
     * *******
     * MPIはJUnitから実行できないためmainに記述 *******
     */
    public static void main(String[] args) {
        JGFTest tester = new JGFTest();
        //tester.$mpi_testSeriesMPI(args);
        //tester.$mpi_testMatmulMPI(args);
        //tester.$mpi_testSORMPI(args);        
        //tester.$mpi_testCryptMPI(args);
        tester.$mpi_testLinpackMPI(args);

    }
}

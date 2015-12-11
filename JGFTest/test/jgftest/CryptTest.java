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
import jgftest.crypt.CryptMPIRecipe;
import jgftest.crypt.CryptParallelRecipe;
import jgftest.crypt.CryptSequentialRecipe;
import jgftest.series.SeriesMPIRecipe;
import jgftest.series.SeriesParallelRecipe;
import jgftest.series.SeriesSequentialRecipe;
import mpi.MPI;
import mpi.MPIException;
import org.aspectj.lang.Aspects;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author yoshiki
 */
public class CryptTest extends JGFTest {
    
    public CryptTest() {
    }

        
    /**
     * *******
     * 初期化 *******
     */
    public void init(int _num_threads, int _rank, int _nprocess) {
        super.init(_num_threads, _rank, _nprocess);

        sequential2 = new jgf.sequential.crypt.JGFCryptBench();
        parallel2 = new jgf.parallel.crypt.JGFCryptBench(_num_threads);
        if (!(_rank == 0 && _nprocess == 0)) {
            distributed2 = new jgf.mpi.crypt.JGFCryptBench(_nprocess, _rank);
        }
    }
    
//    public void testCryptParallel() {
//        init(num_threads, rank, nprocess);
//        runSequential(null);
//        runParallel(null);
//
//        long begin = System.currentTimeMillis();
//        
//        HUTracerView traceView = HUTracer.getTracerView();
//        HUSet<HUTuple1<Integer>> s = (HUSet<HUTuple1<Integer>>) traceView.get(Aspects.aspectOf(jgftest.crypt.CryptSequentialRecipe.class));
//        HUSet<HUTuple1<Integer>> p = (HUSet<HUTuple1<Integer>>) traceView.get(Aspects.aspectOf(jgftest.crypt.CryptParallelRecipe.class));
//        HUSet<HUTuple1<Integer>> diff = s.difference(p);
//        
//        assertThat(diff.isEmpty(), is(true));
//        long end = System.currentTimeMillis();        
//        logger.info("time = " + (end-begin));
//        logger.info("sequential size = {}, parallel size = {}", s.size(), p.size());
//        logger.info(getMemoryInfo());
//
//        //assertThat(s, is(p));
//    }
    
    public void testCryptParallelIncr() {
        init(num_threads, rank, nprocess);
        CryptSequentialRecipe seqrec = Aspects.aspectOf(CryptSequentialRecipe.class);
        CryptParallelRecipe parrec = Aspects.aspectOf(CryptParallelRecipe.class);

        HUTracerView traceView = HUTracer.getTracerView();
                
        HUSet<HUTuple1<Integer>> s = (HUSet<HUTuple1<Integer>>) traceView.get(seqrec);
        runSequential(null);
        
        long begin = System.currentTimeMillis();        
        parrec.setTarget(s);
        
        HUSet<HUTuple1<Integer>> p = (HUSet<HUTuple1<Integer>>) traceView.get(parrec);
        runParallel(null);
        
        HUSet<HUTuple1<Integer>> diff = s;                
        assertThat(diff.isEmpty(), is(true));        
        long end = System.currentTimeMillis();        
        
        logger.info("time = " + (end-begin));
        logger.info("sequential size = {}, parallel size = {}", s.size(), p.size());
        logger.info(getMemoryInfo());

        //assertThat(s, is(p));
    }        
    
    public void $mpi_testMPI(String[] args) throws MPIException {
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
        HUSet<HUTuple1<Integer>> s = (HUSet<HUTuple1<Integer>>) traceView.get(Aspects.aspectOf(CryptSequentialRecipe.class));
        HUGatheredTracerView gatherdTraceView = HUTracer.getGatheredTracerView();
        HUSet<HUTuple1<Integer>> d = (HUSet<HUTuple1<Integer>>) gatherdTraceView.get(Aspects.aspectOf(CryptMPIRecipe.class));
        HUSet<HUTuple1<Integer>> dd = gatherdTraceView.gather(d);

        if (rank == 0) {
            logger.info("{}", s.size());
        }
        logger.info("{}@{}", d.size(), rank);

        if (rank == 0) {
            HUSet<HUTuple1<Integer>> diff = s.difference(dd);
            logger.info("diff = {}", diff.size());

            assertThat(diff.isEmpty(), is(true));
            long end = System.currentTimeMillis();        
            logger.info("time = " + (end-begin));
            //assertThat(s.difference(dd).size(), is(0));
        }
        MPI.Finalize();
    }
    
    public static void main(String[] args) throws Exception {
        JGFTest tester = new CryptTest();        
        tester.$mpi_testMPI(args);    
    }    
     
}

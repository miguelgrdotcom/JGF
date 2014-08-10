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
import jgftest.montecarlo.MontecarloMPIRecipe;
import jgftest.montecarlo.MontecarloSequentialRecipe;
import jgftest.raytracer.RaytracerMPIRecipe;
import jgftest.raytracer.RaytracerParallelRecipe;
import jgftest.raytracer.RaytracerSequentialRecipe;
import mpi.MPI;
import org.aspectj.lang.Aspects;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 *
 * @author yoshiki
 */
public class RaytracerTest extends JGFTest {
    
    public RaytracerTest() {
        logger.setLevel(Level.INFO);
    }

        
    /*********
     * 初期化 
     ********/
    public void init(int _num_threads, int _rank, int _nprocess) {
        super.init(_num_threads, _rank, _nprocess);

        sequential3 = new jgf.sequential.raytracer.JGFRayTracerBench();
        parallel3 = new jgf.parallel.raytracer.JGFRayTracerBench(_num_threads);
        if (!(_rank == 0 && _nprocess == 0)) {
            distributed3 = new jgf.mpi.raytracer.JGFRayTracerBench(_nprocess, _rank);
        }
    }
    
    public void testRaytracerParallel() {
        init(num_threads, rank, nprocess);
        runSequential(null);
        runParallel(null);

        HUTracerView traceView = HUTracer.getTracerView();
        HUSet<HUTuple2<Integer,Integer>> s = (HUSet<HUTuple2<Integer,Integer>>) traceView.get(Aspects.aspectOf(RaytracerSequentialRecipe.class));
        HUSet<HUTuple2<Integer,Integer>> p = (HUSet<HUTuple2<Integer,Integer>>) traceView.get(Aspects.aspectOf(RaytracerParallelRecipe.class));

        HUSet<HUTuple2<Integer,Integer>> diff = s.difference(p);
        logger.info("sequential size = {}, parallel size = {}", s.size(), p.size());

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
        HUSet<HUTuple2<Integer,Integer>> s = (HUSet<HUTuple2<Integer,Integer>>) traceView.get(Aspects.aspectOf(RaytracerSequentialRecipe.class));
        HUGatheredTracerView gatherdTraceView = HUTracer.getGatheredTracerView();
        HUSet<HUTuple2<Integer,Integer>> d = (HUSet<HUTuple2<Integer,Integer>>) gatherdTraceView.get(Aspects.aspectOf(RaytracerMPIRecipe.class));
        HUSet<HUTuple2<Integer,Integer>> dd = gatherdTraceView.gather(d);

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

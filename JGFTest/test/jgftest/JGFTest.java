/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgftest;

import jgftest.series.SeriesSequentialRecipe;
import jgftest.series.SeriesMPIRecipe;
import jgftest.series.SeriesParallelRecipe;
import jgftest.sor.SORSequentialRecipe;
import jgftest.sor.SORMPIRecipe;
import jgftest.sor.SORParallelRecipe;
import jgftest.sparsematmult.MatmultParallelRecipe;
import jgftest.sparsematmult.MatmultMPIRecipe;
import jgftest.sparsematmult.MatmultSequentialRecipe;
import jgftest.lufact.LinpackMPIRecipe;
import jgftest.lufact.LinpackSequentialRecipe;
import jgftest.lufact.LinpackParallelRecipe;
import jgftest.crypt.CryptSequentialRecipe;
import jgftest.crypt.CryptMPIRecipe;
import jgftest.crypt.CryptParallelRecipe;
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

    protected jgf.sequential.util.JGFSection2 sequential = null;
    protected jgf.parallel.util.JGFSection2 parallel = null;
    protected jgf.mpi.util.JGFSection2 distributed = null;

    protected int size = 0;
    protected int rank = 0;
    protected int nprocess = 0;
    protected int num_threads = 16;
    protected String[] args = {};

    public static final Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("JGFTest");

    public JGFTest() {
        /* Used by JUnit */
        setUpClass();
    }

    @BeforeClass
    public static void setUpClass() {
        logger.setLevel(Level.WARN);
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
     * 初期化 *******
     */
    public void init(int _num_threads, int _rank, int _nprocess) {
        rank = _rank;
        nprocess = _nprocess;
        num_threads = _num_threads;        
    }
  
    
    /**
     * *******
     * テスト実行 *******
     */
    @Test
    public void testSeriesParallel() {
    }
    
    @Test
    public void testMatmultParallel() {
    }

    @Test
    public void testSORParallel() {
    }
    
    @Test
    public void testCryptParallel() {
    }

    @Test
    public void testLinpackParallel() {
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

    public void $mpi_testMPI(String[] args) { }
    
    /**
     * *******
     * MPIはJUnitから実行できないためmainに記述 *******
     */
    public static void main(String[] args) {
        JGFTest tester = new LinpackTest();
        tester.$mpi_testMPI(args);
    }
}

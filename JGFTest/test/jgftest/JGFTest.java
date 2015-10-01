/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgftest;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import java.text.DecimalFormat;
import mpi.MPIException;
import net.sourceforge.sizeof.SizeOf;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.LoggerFactory;

/**
 *
 * @author yoshiki
 */
public class JGFTest {

    protected jgf.sequential.util.JGFSection2 sequential2 = null;
    protected jgf.parallel.util.JGFSection2 parallel2 = null;
    protected jgf.mpi.util.JGFSection2 distributed2 = null;
    
    protected jgf.sequential.util.JGFSection3 sequential3 = null;
    protected jgf.parallel.util.JGFSection3 parallel3 = null;
    protected jgf.mpi.util.JGFSection3 distributed3 = null;    

    protected int size = 2;
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
        //logger.setLevel(Level.WARN);
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

    @Test
    public void testMoldynParallel() {
    }  
    
    @Test
    public void testMontecarloParallel() {
    } 
    
    @Test
    public void testRaytracerParallel() {
    } 

    /**
     * *******
     * 対象実行 *******
     */
    public void runSequential(String[] argv) {
        if (sequential2 != null) sequential2.JGFrun(size);
        if (sequential3 != null) sequential3.JGFrun(size);

    }

    public void runParallel(String[] argv) {
        if (parallel2 != null) parallel2.JGFrun(size);
        if (parallel3 != null) parallel3.JGFrun(size);
        
    }

    public void runMPI(String[] argv) throws MPIException {
        if (distributed2 != null) distributed2.JGFrun(size);
        if (distributed3 != null) distributed3.JGFrun(size);
        
    }

    public void $mpi_testMPI(String[] args) throws MPIException { }

    public static String getMemoryInfo() {
        System.gc();
        DecimalFormat f1 = new DecimalFormat("#,###KB");
        DecimalFormat f2 = new DecimalFormat("##.#");
        long free = Runtime.getRuntime().freeMemory() / 1024;
        long total = Runtime.getRuntime().totalMemory() / 1024;
        long max = Runtime.getRuntime().maxMemory() / 1024;
        long used = total - free;
        double ratio = (used * 100 / (double)total);
        String info = 
                "Total=" + f1.format(total) + "、" +
                "Used=" + f1.format(used) + " (" + f2.format(ratio) + "%)、" +
                "Max="+f1.format(max);
        return info;
    }    
    
    public static String getObjectSize(Object o) {
        return SizeOf.humanReadable(SizeOf.sizeOf(o));
    }
    /**
     * *******
     * MPIはJUnitから実行できないためmainに記述 *******
     */
}

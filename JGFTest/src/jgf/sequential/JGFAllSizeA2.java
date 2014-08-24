package jgf.sequential;

/**************************************************************************
*                                                                         *
*             Java Grande Forum Benchmark Suite - Version 2.0             *
*                                                                         *
*                            produced by                                  *
*                                                                         *
*                  Java Grande Benchmarking Project                       *
*                                                                         *
*                                at                                       *
*                                                                         *
*                Edinburgh Parallel Computing Centre                      *
*                                                                         * 
*                email: epcc-javagrande@epcc.ed.ac.uk                     *
*                                                                         *
*                                                                         *
*      This version copyright (c) The University of Edinburgh, 1999.      *
*                         All rights reserved.                            *
*                                                                         *
**************************************************************************/


import jgf.sequential.util.JGFInstrumentor;
import jgf.sequential.sparsematmult.JGFSparseMatmultBench;
import jgf.sequential.sor.JGFSORBench;
import jgf.sequential.series.JGFSeriesBench;
import jgf.sequential.lufact.JGFLUFactBench;
import jgf.sequential.heapsort.JGFHeapSortBench;
import jgf.sequential.fft.JGFFFTBench;
import jgf.sequential.crypt.JGFCryptBench;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JGFAllSizeA2{
    
  public static void main(String argv[]){
   
    int size = 0; 

    
    JGFInstrumentor.printHeader(2,size);

    JGFSeriesBench se = new JGFSeriesBench(); 
    se.JGFrun(size);

    JGFLUFactBench lub = new JGFLUFactBench();
    lub.JGFrun(size);    

    JGFHeapSortBench hb = new JGFHeapSortBench();
    hb.JGFrun(size);    
    
    JGFCryptBench cb = new JGFCryptBench();
    cb.JGFrun(size);    

    JGFFFTBench fft = new JGFFFTBench(); 
    fft.JGFrun(size);

    JGFSORBench jb = new JGFSORBench(); 
    jb.JGFrun(size);
   
    JGFSparseMatmultBench smm = new JGFSparseMatmultBench(); 
    smm.JGFrun(size);
 
  }
}

/**************************************************************************
*                                                                         *
*         Java Grande Forum Benchmark Suite - Thread Version 1.0          *
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
*      This version copyright (c) The University of Edinburgh, 2001.      *
*                         All rights reserved.                            *
*                                                                         *
**************************************************************************/


package jgf.parallel.moldyn; 

import ch.qos.logback.classic.Logger;
import jgf.parallel.util.JGFInstrumentor;
import jgf.parallel.util.JGFSection3;
import java.io.*;
import jgf.parallel.util.*;
import org.slf4j.LoggerFactory;

public class JGFMolDynBench extends md implements JGFSection3 {

  public static int nthreads;

  public JGFMolDynBench(int nthreads) {
        this.nthreads=nthreads;
  }


//   int size;

  public void JGFsetsize(int size){
    this.size = size;
  }

  public void JGFinitialise(){

      initialise();

  }

  public void JGFapplication(){ 

    runiters();

  } 

  public static final Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("JGFMolDynBench");    


  public void JGFvalidate(){
    double refval[] = {1731.4306625334357,7397.392307839352};
    double dev = Math.abs(ek[0] - refval[size]);
    logger.warn("ek = {}", ek[0]);

    if (dev > 1.0e-10 ){
      System.out.println("Validation failed");
      System.out.println("Kinetic Energy = " + ek[0] + "  " + dev + "  " + size);
    }
  }

  public void JGFtidyup(){    

//    one = null;
    System.gc();
  }


  public void JGFrun(int size){

    JGFInstrumentor.addTimer("Section3:MolDyn:Total", "Solutions",size);
    JGFInstrumentor.addTimer("Section3:MolDyn:Run", "Interactions",size);

    JGFsetsize(size); 

    JGFInstrumentor.startTimer("Section3:MolDyn:Total");

    JGFinitialise(); 
    JGFapplication(); 
    JGFvalidate(); 
    JGFtidyup(); 

    JGFInstrumentor.stopTimer("Section3:MolDyn:Total");

    JGFInstrumentor.addOpsToTimer("Section3:MolDyn:Run", (double) interactions);
    JGFInstrumentor.addOpsToTimer("Section3:MolDyn:Total", 1);

    JGFInstrumentor.printTimer("Section3:MolDyn:Run"); 
    JGFInstrumentor.printTimer("Section3:MolDyn:Total"); 
  }


}
 

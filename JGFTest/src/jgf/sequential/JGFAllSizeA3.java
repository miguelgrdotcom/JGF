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


import jgf.sequential.raytracer.JGFRayTracerBench;
import jgf.sequential.montecarlo.JGFMonteCarloBench;
import jgf.sequential.moldyn.JGFMolDynBench;
import jgf.sequential.util.JGFInstrumentor;

import jgf.sequential.util.*; 

public class JGFAllSizeA3{

  public static void main(String argv[]){
   
    int size = 0; 

    JGFInstrumentor.printHeader(3,size);

    JGFMolDynBench mdb = new JGFMolDynBench();
    mdb.JGFrun(size);

    JGFMonteCarloBench mcb = new JGFMonteCarloBench();
    mcb.JGFrun(size);

    JGFRayTracerBench rtb = new JGFRayTracerBench();
    rtb.JGFrun(size);

  }
}

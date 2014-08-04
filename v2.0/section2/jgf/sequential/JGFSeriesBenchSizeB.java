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
import jgf.sequential.series.JGFSeriesBench; 

public class JGFSeriesBenchSizeB{ 

  public static void main(String argv[]){

    JGFInstrumentor.printHeader(2,1);

    JGFSeriesBench se = new JGFSeriesBench(); 
    se.JGFrun(1);

  }
}

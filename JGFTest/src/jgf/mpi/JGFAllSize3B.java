package jgf.mpi;

/**************************************************************************
*                                                                         *
*             Java Grande Forum Benchmark Suite - MPJ Version 1.0         *
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


import jgf.mpi.raytracer.JGFRayTracerBench;
import jgf.mpi.montecarlo.JGFMonteCarloBench;
import jgf.mpi.moldyn.JGFMolDynBench;
import jgf.mpi.util.*;
import mpi.*;

public class JGFAllSize3B{ 

  public static int nprocess;
  public static int rank;

  public static void main(String argv[]) throws MPIException{

/* Initialise MPI */
    MPI.Init(argv);
    rank = MPI.COMM_WORLD.Rank();
    nprocess = MPI.COMM_WORLD.Size();

    int size = 1;

    if(rank==0) {
      JGFInstrumentor.printHeader(3,1,nprocess);
    }

    JGFMolDynBench mold = new JGFMolDynBench(nprocess,rank); 
    mold.JGFrun(size);

    JGFMonteCarloBench mc = new JGFMonteCarloBench(nprocess,rank);
    mc.JGFrun(size);

    JGFRayTracerBench rtb = new JGFRayTracerBench(nprocess,rank);
    rtb.JGFrun(size);




/* Finalise MPI */
    MPI.Finalize();

 
  }
}



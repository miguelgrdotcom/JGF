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


import jgf.mpi.util.JGFInstrumentor;
import jgf.mpi.sparsematmult.JGFSparseMatmultBench;
import jgf.mpi.sor.JGFSORBench;
import jgf.mpi.series.JGFSeriesBench;
import jgf.mpi.lufact.JGFLUFactBench;
import jgf.mpi.crypt.JGFCryptBench;
import jgf.mpi.util.*; 
import mpi.*;

public class JGFAllSizeB{ 

  public static int nprocess;
  public static int rank;

  public static void main(String argv[]) throws MPIException{

/* Initialise MPI */
     MPI.Init(argv);
     rank = MPI.COMM_WORLD.Rank();
     nprocess = MPI.COMM_WORLD.Size();

    int size = 1;

    if(rank==0) {
      JGFInstrumentor.printHeader(2,1,nprocess);
    }

    JGFSeriesBench se = new JGFSeriesBench(nprocess,rank);
    se.JGFrun(size);

    JGFLUFactBench lub = new JGFLUFactBench(nprocess,rank);
    lub.JGFrun(size);

    JGFCryptBench cb = new JGFCryptBench(nprocess,rank); 
    cb.JGFrun(size);

    JGFSORBench jb = new JGFSORBench(nprocess,rank);
    jb.JGFrun(size);

    JGFSparseMatmultBench smm = new JGFSparseMatmultBench(nprocess,rank);
    smm.JGFrun(size);

/* Finalise MPI */
     MPI.Finalize();
 
  }
}



package hu.tracer;

import mpi.MPI;
import mpi.MPIException;

public abstract aspect HUMPITraceRecipe <T extends Comparable<T>> extends HUTraceRecipe<T>{
	private int rank;
	private int size;
    
    public HUMPITraceRecipe() {
        super();
        try
        {
            rank = MPI.COMM_WORLD.Rank();
            size = MPI.COMM_WORLD.Size();
        }
        catch (MPIException e)
        {
            new RuntimeException(e);
        }
    }
    
    protected int mpiRank() {
        return rank;
    }
    
    protected int mpiSize()
    {
        return size;
    }
}

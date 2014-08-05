package hu.tracer;

import hu.list.HUSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import mpi.MPI;
import mpi.MPIException;

public class HUGatheredTracerView extends HUTracerView
{
    public HUGatheredTracerView(HashMap<HUTraceRecipe<?>, HUSet<?>> map)
    {
        super(map);
        ArrayList<HUTraceRecipe<?>> keys = new ArrayList<HUTraceRecipe<?>>(
                map.keySet());
        Collections.sort(keys);

        HashMap<HUTraceRecipe<?>, HUSet<?>> newMap = new HashMap<HUTraceRecipe<?>, HUSet<?>>();

        for (HUTraceRecipe<?> key : keys)
            newMap.put(key, gather(map.get(key)));
        
        this.map = newMap;
    }

    /**
     * MPIを利用した複数プロセス実行をしている際に、各ノードの情報を0ノードに集めます
     * @throws MPIException
     */
    public <T extends Comparable<T>> HUSet<T> gather(HUSet<T> list)
    {
        @SuppressWarnings("unchecked")
        // ここでしか使わないので大丈夫です
        HUSet<T>[] sendbuf = new HUSet[]
                { list };

        try
        {
            int size = MPI.COMM_WORLD.Size();
            int rank = MPI.COMM_WORLD.Rank();

            @SuppressWarnings("unchecked")
            HUSet<T>[] recvbuf = new HUSet[size];

            MPI.COMM_WORLD.Barrier();

            MPI.COMM_WORLD.Gather(sendbuf, 0, 1, MPI.OBJECT, recvbuf, 0, 1,
                    MPI.OBJECT, 0);

            MPI.COMM_WORLD.Barrier();

            HUSet<T> newList = new HUSet<T>();
            newList.addAll(list);
            if (rank == 0)
                if (size != 1)
                    for (int i = 1; i < recvbuf.length; i++)
                        newList.addAll(recvbuf[i]);

            return newList;
        }

        catch (MPIException e)
        {
            throw new RuntimeException(e);
        }
    }
}

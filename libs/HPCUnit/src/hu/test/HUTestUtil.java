package hu.test;

import hu.list.HUSet;
import hu.tracer.HUTraceRecipe;
import hu.tracer.HUTracerView;

public class HUTestUtil
{
    static HUTracerView tracerView;
     
    public static <T extends Comparable<T>> HUSet<T> getTrace(HUTraceRecipe<T> strategy) {
        return tracerView.get(strategy);
    }
}

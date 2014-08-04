package hu.tracer;

import hu.list.HUSet;

import java.util.HashMap;

public class HUTracerView
{
    protected HashMap<HUTraceRecipe<?>, HUSet<?>> map;
    
    protected HUTracerView()
    {
        
    }

    public HUTracerView(HashMap<HUTraceRecipe<?>, HUSet<?>> map)
    {
        this.map = map;
    }

    @SuppressWarnings("unchecked")
    public <T extends Comparable<T>> HUSet<T> get(HUTraceRecipe<T> trace)
    {
        return (HUSet<T>) map.get(trace);   
    }
}

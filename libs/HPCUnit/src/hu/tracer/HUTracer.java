package hu.tracer;

import hu.list.HUSet;

import java.util.HashMap;

public class HUTracer {
    private static HashMap<HUTraceRecipe<?>, HUSet<?>> map = new HashMap<HUTraceRecipe<?>, HUSet<?>>();
	
	public static <T extends Comparable<T>> void set(HUTraceRecipe<T> trace, HUSet<T> list) {
	  map.put(trace, list);
	}

	public static HUTracerView getTracerView () {
	    return new HUTracerView(map);
	}
	
	public static HUGatheredTracerView getGatheredTracerView () {
	    return new HUGatheredTracerView(map);
	}
}

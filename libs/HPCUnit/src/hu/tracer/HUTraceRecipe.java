package hu.tracer;

import hu.list.HUSet;
import org.aspectj.lang.annotation.Aspect;


//public abstract aspect HUTraceRecipe<T extends Comparable<T>> implements
public abstract class HUTraceRecipe<T extends Comparable<T>> implements
		Comparable<HUTraceRecipe<?>> {
	private HUSet<T> simulationArea;
	private int id;
	private static int cnt = 0;
	private HUTraceRecipe<T>[] friendsCache;
	private boolean friendsFlag = false;

	public HUTraceRecipe() {
		simulationArea = new HUSet<T>();
		HUTracer.set(this, simulationArea);
		id = cnt++;
	}

	protected void add(T tuple) {
		simulationArea.add(tuple);
	}

	protected HUTraceRecipe<T>[] friends() {
		return null;
	}

	private HUTraceRecipe<T>[] friendsInner() {
		if (friendsFlag)
			return friendsCache;
		friendsFlag = true;
		return friendsCache = friends();
	}

	protected int count() {
		int num = simulationArea.size();
		if (friendsInner() != null)
			for (HUTraceRecipe<T> friend : friendsInner())
				num += friend.simulationArea.size();
		return num;
	}

	protected long time() {
		return System.currentTimeMillis();
	}

	protected long threadId() {
		return Thread.currentThread().getId();
	}

	@Override
	public int compareTo(HUTraceRecipe<?> o) {
		return this.id - o.id;
	}
}

package hu.list.tuple;

import hu.list.HUMapper;

import java.io.Serializable;

public class HUTuple5<T0 extends Comparable<T0>, T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>>
        implements Comparable<HUTuple5<T0, T1, T2, T3, T4>>, Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -58547905416077269L;

    private T0  el0;

    private T1  el1;

    private T2  el2;

    private T3  el3;

    private T4  el4;

    private int hashCode = 0;

    public HUTuple5(T0 el0, T1 el1, T2 el2, T3 el3, T4 el4)
    {
        this.el0 = el0;
        this.el1 = el1;
        this.el2 = el2;
        this.el3 = el3;
        this.el4 = el4;
    }

    public T0 el0()
    {
        return el0;
    }

    public T1 el1()
    {
        return el1;
    }

    public T2 el2()
    {
        return el2;
    }

    public T3 el3()
    {
        return el3;
    }

    public T4 el4()
    {
        return el4;
    }

    @Override
    public int compareTo(HUTuple5<T0, T1, T2, T3, T4> o)
    {
        return this.el0.compareTo(o.el0) != 0 ? this.el0.compareTo(o.el0) : this.el1.compareTo(o.el1) != 0 ? this.el1.compareTo(o.el1) : this.el2.compareTo(o.el2) != 0 ? this.el2.compareTo(o.el2) : this.el3.compareTo(o.el3) != 0 ? this.el3.compareTo(o.el3) : this.el4.compareTo(o.el4) != 0 ? this.el4.compareTo(o.el4) : 0;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof HUTuple5))
        {
            return false;
        }
        @SuppressWarnings("unchecked")
        HUTuple5<T0, T1, T2, T3, T4> oo = (HUTuple5<T0, T1, T2, T3, T4>) o;
        return this.el0.equals(oo.el0) && this.el1.equals(oo.el1)
                && this.el2.equals(oo.el2) && this.el3.equals(oo.el3)
                && this.el4.equals(oo.el4);
    }

    public int hashCode()
    {
        if (hashCode == 0)
        {
            int result = 17;
            result = 37 * result + el0.hashCode();
            result = 37 * result + el1.hashCode();
            result = 37 * result + el2.hashCode();
            result = 37 * result + el3.hashCode();
            result = 37 * result + el4.hashCode();
            hashCode = result;
        }
        return hashCode;
    }

    public String toString()
    {
        return "(" + el0.toString() + ", " + el1.toString() + ", "
                + el2.toString() + ", " + el3.toString() + ", "
                + el4.toString() + ")";
    }

    public <U0 extends Comparable<U0>, U1 extends Comparable<U1>, U2 extends Comparable<U2>, U3 extends Comparable<U3>> HUTuple4<U0, U1, U2, U3> map(
            HUMapper<HUTuple5<T0, T1, T2, T3, T4>, HUTuple4<U0, U1, U2, U3>> mapper)
    {
        return mapper.apply(this);
    }
}

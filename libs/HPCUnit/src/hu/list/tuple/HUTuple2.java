package hu.list.tuple;

import hu.list.HUMapper;

import java.io.Serializable;

public class HUTuple2<T0 extends Comparable<T0>, T1 extends Comparable<T1>>
        implements Comparable<HUTuple2<T0, T1>>, Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1334526221232097572L;

    private T0  el0;

    private T1  el1;

    private int hashCode = 0;

    public HUTuple2(T0 el0, T1 el1)
    {
        this.el0 = el0;
        this.el1 = el1;
    }

    public T0 el0()
    {
        return el0;
    }

    public T1 el1()
    {
        return el1;
    }

    @Override
    public int compareTo(HUTuple2<T0, T1> o)
    {
        return this.el0.compareTo(o.el0) != 0 ? this.el0.compareTo(o.el0) : this.el1.compareTo(o.el1) != 0 ? this.el1.compareTo(o.el1) : 0;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof HUTuple2))
        {
            return false;
        }
        @SuppressWarnings("unchecked")
        HUTuple2<T0, T1> oo = (HUTuple2<T0, T1>) o;
        return this.el0.equals(oo.el0) && this.el1.equals(oo.el1);
    }

    public int hashCode()
    {
        if (hashCode == 0)
        {
            int result = 17;
            result = 37 * result + el0.hashCode();
            result = 37 * result + el1.hashCode();
            hashCode = result;
        }
        return hashCode;
    }

    public String toString()
    {
        return "(" + el0.toString() + ", " + el1.toString() + ")";
    }

    public <U0 extends Comparable<U0>> HUTuple1<U0> map(
            HUMapper<HUTuple2<T0, T1>, HUTuple1<U0>> mapper)
    {
        return mapper.apply(this);
    }
}

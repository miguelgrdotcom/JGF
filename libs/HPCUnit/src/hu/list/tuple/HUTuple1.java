package hu.list.tuple;

import java.io.Serializable;

public class HUTuple1<T0 extends Comparable<T0>> implements
        Comparable<HUTuple1<T0>>, Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -3215897901100260567L;

    private T0  el0;

    private int hashCode = 0;

    public HUTuple1(T0 el0)
    {
        this.el0 = el0;
    }

    public T0 el0()
    {
        return el0;
    }

    @Override
    public int compareTo(HUTuple1<T0> o)
    {
        return this.el0.compareTo(o.el0) != 0 ? this.el0.compareTo(o.el0) : 0;
    }

    @Override
    public boolean equals(Object o)
    {
//        if (this == o)
//        {
//            return true;
//        }
        if (!(o instanceof HUTuple1))
        {
            return false;
        }
        @SuppressWarnings("unchecked")
        HUTuple1<T0> oo = (HUTuple1<T0>) o;
        return this.el0.equals(oo.el0);
    }

    public int hashCode()
    {
        if (hashCode == 0)
        {
            int result = 17;
            result = 37 * result + el0.hashCode();
            hashCode = result;
        }
        return hashCode;
    }

    public String toString()
    {
        return "(" + el0.toString() + ")";
    }
}

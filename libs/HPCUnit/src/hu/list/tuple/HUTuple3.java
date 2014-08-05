package hu.list.tuple;

import hu.list.HUMapper;

import java.io.Serializable;

public class HUTuple3<T0 extends Comparable<T0>, T1 extends Comparable<T1>, T2 extends Comparable<T2>>
        implements Comparable<HUTuple3<T0, T1, T2>>, Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 6414271318017690821L;

    private T0  el0;

    private T1  el1;

    private T2  el2;

    private int hashCode = 0;

    public HUTuple3(T0 el0, T1 el1, T2 el2)
    {
        this.el0 = el0;
        this.el1 = el1;
        this.el2 = el2;
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

    @Override
    public int compareTo(HUTuple3<T0, T1, T2> o)
    {
        return this.el0.compareTo(o.el0) != 0 ? this.el0.compareTo(o.el0) : this.el1.compareTo(o.el1) != 0 ? this.el1.compareTo(o.el1) : this.el2.compareTo(o.el2) != 0 ? this.el2.compareTo(o.el2) : 0;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof HUTuple3))
        {
            return false;
        }
        @SuppressWarnings("unchecked")
        HUTuple3<T0, T1, T2> oo = (HUTuple3<T0, T1, T2>) o;
        return this.el0.equals(oo.el0) && this.el1.equals(oo.el1)
                && this.el2.equals(oo.el2);
    }

    public int hashCode()
    {
        if (hashCode == 0)
        {
            int result = 17;
            result = 37 * result + el0.hashCode();
            result = 37 * result + el1.hashCode();
            result = 37 * result + el2.hashCode();
            hashCode = result;
        }
        return hashCode;
    }

    public String toString()
    {
        return "(" + el0.toString() + ", " + el1.toString() + ", "
                + el2.toString() + ")";
    }

    public <U0 extends Comparable<U0>, U1 extends Comparable<U1>> HUTuple2<U0, U1> map(
            HUMapper<HUTuple3<T0, T1, T2>, HUTuple2<U0, U1>> mapper)
    {
        return mapper.apply(this);
    }
}

package hu.list;


import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * 以下の基本的な集合演算を提供する重複を許すリストの実装です。<br>
 * 内部実装はArrayListです。<br>
 * ・和集合 <br>
 * ・積集合 <br>
 * ・差集合 <br>
 * ・写像 <br>
 * ・射影 <br>
 */
public class HUSet<T extends Comparable<T>> implements Serializable, Iterable<T>, Collection<T>
{
    /**
     * 
     */
    private static final long serialVersionUID = 396895978870247744L;
    
   //TreeMultiset<T> set;
   List<T> set;

    /**
     * 空の重複を許すリストを作成します。
     */
    public HUSet()
    {
        //set = TreeMultiset.create();
        //set = new ArrayList<T>();
		set = Collections.synchronizedList(new ArrayList<T>());
    }
    
    private HUSet(Iterable<? extends T> elements) {
        //set = TreeMultiset.create(elements);
        //set = new ArrayList<T>(); 
		set = Collections.synchronizedList(new ArrayList<T>());
        for (T t : elements)
            set.add(t); 
    }
    
    @Override
    public int size () {
        return set.size();
    }
    
    @Override
    public Iterator<T> iterator() {
       Collections.sort(set); // MultiTreeSet の場合には不要。
       return set.iterator(); 
    }
    
    @Override
    public boolean addAll(Collection<? extends T> elementsToAdd) {
       return set.addAll(elementsToAdd);
    }

    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof HUSet))
            return false;
        @SuppressWarnings("unchecked")
        HUSet<T> oo = (HUSet<T>) o;
        if (this.size() != oo.size())
            return false;
        Iterator<T> it_this = this.iterator();
        Iterator<T> it_oo = oo.iterator();

        while (it_this.hasNext())
        {
            if (!it_this.next().equals(it_oo.next()))
                return false;
        }
        return true;
    }
        
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<T> it = this.iterator();
        while(it.hasNext()) {
            sb.append(it.next());
            sb.append("\n");
        }
        return sb.toString();
    }
 

    /**
     * この集合と指定された集合の和集合を新規に作成し、返します。 
     */
    public HUSet<T> union(HUSet<T> list)
    {
        HUSet<T> copy = new HUSet<T>(this);
        copy.addAll(list);
        return copy;
    }

    /**
     * この集合と指定された集合の積集合を新規に作成し、返します。 
     */
    public HUSet<T> intersection(HUSet<T> list)
    {
        HUSet<T> copy = new HUSet<T>(this);
        copy.retainAll(list);
        return copy;
    }

    /**
     * この集合と指定された集合の差集合を新規に作成し、返します。 
     */
    public HUSet<T> difference(HUSet<T> list)
    {
        HUSet<T> copy = new HUSet<T>(this);
        copy.removeAll(list);
        return copy;
    }

    /**
     * 指定された射影に従い、新たな重複を許すリストを作成し、返します。
     */
    public <U extends Comparable<U>> HUSet<U> map(HUMapper<T, U> mapper)
    {
        HUSet<U> newSet = new HUSet<U>();
        for (T tuple : this)
            newSet.add(mapper.apply(tuple));
        return newSet;
    }
    
    /**
     * 指定された畳込み関数と初期値に従い、リストを畳込みこんだ結果を返します
     */
    public <U extends Comparable<U>> U fold(HUFolder<U, T> folder, U origin) {
       Iterator<T> it = iterator();
       U result = origin;
       while(it.hasNext())
          result = folder.apply(result, it.next());
       return result;
    }
       

    @Override
    public boolean isEmpty()
    {
        return set.isEmpty();
    }

    @Override
    public boolean contains(Object o)
    {
        return set.contains(o);
    }

    @Override
    public Object[] toArray()
    {
        return set.toArray();
    }

    @Override
    public <E> E[] toArray(E[] a)
    {
        return set.toArray(a);
    }

    @Override
    public boolean add(T e)
    {
        return set.add(e);
    }

    @Override
    public boolean remove(Object o)
    {
        return set.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c)
    {
        return set.containsAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c)
    {
        return set.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c)
    {
        return set.retainAll(c);
    }

    @Override
    public void clear()
    {
       set.clear(); 
    }
    
}

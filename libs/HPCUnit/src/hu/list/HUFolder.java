package hu.list;

public interface HUFolder<U, T>
{
    public U apply(U result, T el);
}

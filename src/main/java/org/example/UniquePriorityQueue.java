package org.example;

import java.util.PriorityQueue;

public class UniquePriorityQueue<E> extends PriorityQueue<E> {
    @Override
    public boolean offer(E e)
    {
        boolean isAdded = false;
        if(!super.contains(e))
        {
            isAdded = super.offer(e);
        }
        return isAdded;
    }
}

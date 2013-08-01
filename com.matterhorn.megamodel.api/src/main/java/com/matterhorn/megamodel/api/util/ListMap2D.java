package com.matterhorn.megamodel.api.util;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public interface ListMap2D<A,B,V> {
	public void add(A a, B b, V value);
	public List<V> get(A a,B b);
	public void remove(A a,B b, V value);
	public Set<A> keySet();
	public Set<B>keySet(A a);
	public Set<Entry<B, List<V>>>entrySet(A a);
	public void clear();

}

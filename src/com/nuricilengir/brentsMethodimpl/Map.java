package com.nuricilengir.brentsMethodimpl;

import java.security.InvalidKeyException;

public interface Map<K, V> {

	int size();

	boolean isEmpty();

	AbstractMap.MapEntry<K, V> get(K k, V v);

	V put(K key, V value, int count);

	V remove(K key);

	Iterable<K> keySet();

	Iterable<V> values();

	Iterable<Entry<K, V>> entrySet();
}
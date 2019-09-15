package com.nuricilengir.brentsMethodimpl;

import java.util.Iterator;

public abstract class AbstractMap<K, V> implements Map<K, V> {

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	// ---------------- nested MapEntry class ----------------

	protected static class MapEntry<K, V> implements Entry<K, V> {
		private K k; // key
		private V v; // value
		private int countWord;

		public MapEntry(K key, V value) {
			k = key;
			v = value;
			this.countWord = 1;
		}

		// public methods of the Entry interface
		public K getKey() {
			return k;
		}

		public V getValue() {
			return v;
		}

		public int getCount() {
			return countWord;
		}

		// utilities not exposed as part of the Entry interface
		public void setKey(K key) {
			k = key;
		}

		public V setValue(V value) {
			V old = v;
			v = value;
			return old;
		}

		public void setCount(int count) {
			 countWord += count;
		}

		public boolean equals(Object o) {
			MapEntry<K, V> entry;
			try {
				entry = (MapEntry<K, V>) o;
			} catch (ClassCastException ex) {
				return false;
			}
			return (entry.getKey() == k) && (entry.getValue() == v);
		}

		/** Returns string representation (for debugging only) */
		public String toString() {
			return "[" + k + ", " + v + ", " + countWord + "]";
		}

	}

	private class KeyIterator implements Iterator<K> {
		private Iterator<Entry<K, V>> entries = entrySet().iterator(); // reuse entrySet

		public boolean hasNext() {
			return entries.hasNext();
		}

		public K next() {
			return entries.next().getKey();
		} // return key!

		public void remove() {
			throw new UnsupportedOperationException("remove not supported");
		}
	}

	private class KeyIterable implements Iterable<K> {
		public Iterator<K> iterator() {
			return new KeyIterator();
		}
	}

	@Override
	public Iterable<K> keySet() {
		return new KeyIterable();
	}

	private class ValueIterator implements Iterator<V> {
		private Iterator<Entry<K, V>> entries = entrySet().iterator(); // reuse entrySet

		public boolean hasNext() {
			return entries.hasNext();
		}

		public V next() {
			return entries.next().getValue();
		} // return value!

		public void remove() {
			throw new UnsupportedOperationException("remove not supported");
		}
	}

	private class ValueIterable implements Iterable<V> {
		public Iterator<V> iterator() {
			return new ValueIterator();
		}
	}

	@Override
	public Iterable<V> values() {
		return new ValueIterable();
	}
}
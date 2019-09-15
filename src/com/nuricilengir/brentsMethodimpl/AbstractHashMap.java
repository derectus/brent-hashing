package com.nuricilengir.brentsMethodimpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.security.InvalidKeyException;
/**
 * @author derectus
 *
 */
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public abstract class AbstractHashMap<K, V> extends AbstractMap<K, V> {
	protected int n = 0; // x number of entries in the dictionary
	protected int capacity; // length of the table
	private int prime; // prime factor
	private long scale, shift; // the shift and scaling factors

	/** Creates a hash table with the given capacity and prime factor. */
	public AbstractHashMap(int cap, int p) {
		prime = p;
		capacity = cap;
		Random rand = new Random();
		scale = rand.nextInt(prime - 1) + 1;
		shift = rand.nextInt(prime);
		createTable();
	}

	/** Creates a hash table with given capacity and prime factor 109345121. */
	public AbstractHashMap(int cap) {
		this(cap, 5021);
	} // default prime

	/** Creates a hash table with capacity 17 and prime factor 109345121. */
	public AbstractHashMap() {
		this(5000);
	} // default capacity

	@Override
	public int size() {
		return n;
	}

	@Override
	public AbstractMap.MapEntry<K, V> get(K key, V value) {
		return (AbstractMap.MapEntry<K, V>) bucketGet(key, value);
	}

	@Override
	public V remove(K key) {
		return bucketRemove(hashValue(key), key);
	}

	@Override
	public V put(K key, V value, int count) {
		V answer = bucketPut(hashValue(key), key, value, count);
		return answer;
	}

	
	public void readFile() {
		BufferedReader input = null;
		try {
			input = new BufferedReader(new FileReader("resources/story.txt"));
			Scanner scanner = new Scanner(input);

			while (scanner.hasNext()) {
				V[] words = (V[]) scanner.nextLine().toLowerCase().split(" ");
				for (int i = 0; i < words.length; i++) {
					put(hashFuction(words[i]), words[i], 1);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public K hashFuction(V value) {
		int hashedValue = 0;
		char[] stringToKey = ((String) value).toCharArray();
		if (hashedValue == 0 && stringToKey.length > 0) {
			for (char ch : stringToKey)
				hashedValue = (int) Math.abs((hashedValue * 31) + ch);
		}
		K key = (K) Integer.valueOf(hashedValue);
		return key;
	}

	/** Hash function applying MAD method to default hash code. */
	protected int hashValue(K key) {
	    return (int) ((Math.abs(key.hashCode() * scale + shift) % prime) % capacity);
	}

	/** Updates the size of the hash table and rehashes all entries. */
	private void resize(int newCap) {
		ArrayList<Entry<K, V>> buffer = new ArrayList<>(n);
		for (Entry<K, V> e : entrySet())
			buffer.add(e);
		capacity = newCap;
		createTable(); // based on updated capacity
		n = 0; // will be recomputed while reinserting entries
		for (Entry<K, V> e : buffer)
			put(e.getKey(), e.getValue(), e.getCount());
	}

	// protected abstract methods to be implemented by subclasses
	/** Creates an empty table having length equal to current capacity. */
	protected abstract void createTable();

	protected abstract V bucketGet(K key, V value);

	protected abstract V bucketPut(int h, K k, V v, int C);

	protected abstract V bucketRemove(int h, K k);


}
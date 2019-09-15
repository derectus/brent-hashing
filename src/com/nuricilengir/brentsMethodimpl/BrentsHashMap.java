package com.nuricilengir.brentsMethodimpl;

import java.util.ArrayList;

public class BrentsHashMap<K, V> extends AbstractHashMap<K, V> {
	public MapEntry<K, V>[] bucket; // a fixed array of entries (all initially null)

	// provide same constructors as base class
	/** Creates a hash bucket with capacity 17 and prime factor 109345121. */
	public BrentsHashMap() {
		super();
	}

	/** Creates a hash bucket with given capacity and prime factor 109345121. */
	public BrentsHashMap(int cap) {
		super(cap);
	}

	/** Creates a hash bucket with the given capacity and prime factor. */
	public BrentsHashMap(int cap, int p) {
		super(cap, p);
	}

	/** Creates an empty bucket having length equal to current capacity. */
	@Override
	@SuppressWarnings({ "unchecked" })
	protected void createTable() {
		bucket = (MapEntry<K, V>[]) new MapEntry[capacity]; // safe cast
	}

	/** Returns true if location is either empty or the "defunct" sentinel. */
	private boolean isAvailable(int j) {
		return (bucket[j] == null);
	}

	private int increment(K key) {
		int increment = ((hashCoder(key) / capacity) % capacity);
		if (increment == 0)
			increment = 1;
		return Math.abs(increment);
	}

	@Override
	protected V bucketGet(K key, V value) {
		int homeSlot = theHashFuction(key);
		int i = 0;
		if (bucket[homeSlot] != null) {
			if (hashCoder(key) == hashCoder(bucket[homeSlot].getKey())) {
				System.out.println("Key   : " + bucket[homeSlot].getKey() + ",\nValue : " + bucket[homeSlot].getValue()
						+ ",\nCount : " + bucket[homeSlot].getCount() + ",\nIndex : "
						+ hashValue(hashFuction((bucket[homeSlot].getValue()))) + ",\n");
			} else {
				while (bucket[homeSlot] != null) {
					if (hashCoder(bucket[homeSlot].getKey()) != hashCoder(key)) {
						homeSlot = (homeSlot + increment(key)) % capacity;
						i += 1;
						if (i >= capacity) {
							return null;
						}
					} else {
						System.out.println("Key : " + bucket[homeSlot].getKey() + ",\n Value : "
								+ bucket[homeSlot].getValue() + ",\nCount : " + bucket[homeSlot].getCount());
					}
				}
			}
		} else {
			System.out.println("This word is not available !");
		}
		return null;
	}

	@Override
	protected V bucketPut(int h, K k, V v, int C) {
		MapEntry<K, V> currentEntry = new MapEntry<K, V>(k, v);
		MapEntry<K, V> tempEntry = currentEntry;
		int homeSlot = theHashFuction(currentEntry.getKey());

		int primaryProbeChain[];
		primaryProbeChain = new int[1000];
		int bestPositionArray[];
		bestPositionArray = new int[1000];
		int totalOffset[];
		totalOffset = new int[1000];

		int s = 2, i = 1, j = 1;
		int seconderyChainIndex = 1;
		boolean isOkey = false;

		if (v == null) {
			throw new IllegalArgumentException("Invalid value");
		} else if (isExist(k) != null) {
			V temp = isExist(k).getValue();
			bucket[homeSlot].setCount(1);
			return temp;
		} else {
			if (bucket[homeSlot] == null) {
				bucket[homeSlot] = currentEntry;
				isOkey = true;
			} else {
				int offset = increment(currentEntry.getKey()) % capacity;
				primaryProbeChain[0] = homeSlot;
				int loc = homeSlot;
				while (bucket[loc] != null) {
					loc = (loc + increment(currentEntry.getKey())) % capacity;
					primaryProbeChain[s] = loc;
					if (loc == homeSlot)
						System.out.println("Hash Table Is Full");
					s = s + 1;
				}
				primaryProbeChain[s] = loc;
			}
			int offset = 0;
			while (((i + j) < s) && !isOkey) {
				if (bucket[(primaryProbeChain[seconderyChainIndex] + offset) % capacity] == null) {
					bestPositionArray[seconderyChainIndex - 1] = i + j;
					totalOffset[seconderyChainIndex - 1] = offset;
					if (bestPositionArray[seconderyChainIndex - 1] < bestPositionArray[seconderyChainIndex]) {
						int tempSlot = (primaryProbeChain[seconderyChainIndex - 1]
								+ totalOffset[seconderyChainIndex - 1]) % capacity;
						tempEntry = bucket[primaryProbeChain[seconderyChainIndex - 1] % capacity];
						bucket[primaryProbeChain[seconderyChainIndex - 1] % capacity] = currentEntry;
						bucket[tempSlot] = tempEntry;
						isOkey = true;
					} else {
						i = 1;
						j++;
						seconderyChainIndex++;
						offset = 0;
					}
				} else {
					offset = offset + increment(bucket[primaryProbeChain[seconderyChainIndex]].getKey());
					i = i + 1;
				}
			}
		}
		return null;
	}

	private MapEntry<K, V> isExist(K key) {
		int homeSlot = theHashFuction(key);
		int i = 0;
		if (bucket[homeSlot] != null) {
			if (hashCoder(key) == hashCoder(bucket[homeSlot].getKey())) {
				return bucket[homeSlot];
			} else {
				while (bucket[homeSlot] != null) {
					if (hashCoder(bucket[homeSlot].getKey()) != hashCoder(key)) {
						homeSlot = (homeSlot + increment(key)) % capacity;
						i += 1;
						if (i >= capacity)
							return null;
					} else {
						return bucket[homeSlot];
					}

				}
			}
		}
		return null;
	}

	@Override
	protected V bucketRemove(int h, K k) {
		int j = 0;
		if (j < 0)
			return null; // nothing to remove
		V answer = bucket[j].getValue();
		n--;
		return answer;
	}

	@Override
	public Iterable<Entry<K, V>> entrySet() {
		ArrayList<Entry<K, V>> buffer = new ArrayList<>();
		for (int h = 0; h < capacity; h++)
			if (!isAvailable(h))
				buffer.add(bucket[h]);
		return buffer;
	}

	private int theHashFuction(K key) {
		int index = (hashCoder(key) % capacity);
		if (index < 0) {
			index += capacity;
		}
		return index;
	}

	public int hashCoder(K key) {
		return (int) key;
	}
}
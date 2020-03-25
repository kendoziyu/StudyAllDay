package collections;

import java.util.*;

/**
 * 描述: 尝试自己实现一个HashMap，来理解HashMap的原理 <br>
 *
 * @author: skilled-peon <br>
 * @date: 2020/3/21 0021 <br>
 */
public class HashMap7<K, V> extends AbstractMap<K, V> {

    /**
     * 初始化容量, 必须是 2 的 次幂
     */
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
    /**
     * 最大容量, 2的30次方
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;
    /**
     * 无参构造函数中指定的负载系数
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    static final Entry<?, ?>[] EMPTY_TABLE = {};

    Entry<K, V>[] table = (Entry<K, V>[]) EMPTY_TABLE;

    /**
     * 表示扩容的临界阀值
     */
    int threshold;
    float loadFactor;
    /**
     * 保存的键值对数量
     */
    int size;

    public HashMap7(int initialCapacity, float loadFactor) {
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;

        this.loadFactor = loadFactor;
        threshold = initialCapacity;
    }

    public HashMap7(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    public HashMap7() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public HashMap7(Map<? extends K, ? extends V> m) {
        this(Math.max((int) (m.size() / DEFAULT_LOAD_FACTOR) + 1,
                DEFAULT_INITIAL_CAPACITY), DEFAULT_LOAD_FACTOR);
        inflateTable(threshold);

        putAllForCreate(m);
    }

    private void inflateTable(int toSize) {
        int capacity = roundUpToPowerOf2(toSize);
        threshold = (int) (capacity * loadFactor);
        table = new Entry[capacity];
    }

    private void putAllForCreate(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
//            K key = entry.getKey();
//            V value = entry.getValue();
//            int hash = hash(key);
//            int index = indexFor(hash, table.length);
//
//            Entry<K, V> bucket = table[index];
//            table[index] = new Entry<>(key, value, bucket);
            putForCreate(entry.getKey(), entry.getValue());
        }
    }

    private void putForCreate(K key, V value) {
        int hash = key == null ? 0 : hash(key);
        int i = indexFor(hash, table.length);

        for (Entry<K, V> e = table[i]; e != null; e = e.next) {
//            if ((e.key == null && key == null) ||
//                    (e.key != null && e.key.equals(key))) {
            Object k;
            if ((k = e.key) == key && (key != null && key.equals(k))) {
                e.value = value;
                // break;
                return;
            }
        }

        createEntry(key, value, i);
    }

    private void createEntry(K key, V value, int bucketIndex) {
        Entry<K, V> e = table[bucketIndex];
        table[bucketIndex] = new Entry<>(key, value, e);
        size++;
    }

    private int hash(Object key) {
//        int hashCode = k.hashCode();
//        return hashCode >> 16 ^ hashCode;
        int h;
        return key == null ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    private int indexFor(int hash, int length) {
        // return hash % length;
        // length must be a non-zero power of 2
        return hash & (length - 1);
    }

    /**
     * 向上取整至 2 的 n 次幂
     * 需要注意的临界值有最大容量，和负数
     *
     * @param number 待向上取整的数
     * @return
     */
    private int roundUpToPowerOf2(int number) {
        return number >= MAXIMUM_CAPACITY ?
                MAXIMUM_CAPACITY :
                number >= 1 ? Integer.highestOneBit(number - 1) << 1 : 1;
    }


    @Override
    public V put(K key, V value) {
        if (EMPTY_TABLE == table) {
            inflateTable(DEFAULT_INITIAL_CAPACITY);
        }

        int hash = key == null ? 0 : hash(key);
        int i = indexFor(hash, table.length);
        for (Entry<K, V> e = table[i]; e != null; e = e.next) {
            Object k;
            if ((k = e.key) == key && (key != null && key.equals(k))) {
                V oldVal = e.value;
                e.value = value;
                // break;
                return oldVal;
            }
        }

        addEntry(key, value, i);

        return null;
    }

    private void addEntry(K key, V value, int bucketIndex) {
        if (size >= threshold && table.length <= MAXIMUM_CAPACITY) {
            resize();
        }

        createEntry(key, value, bucketIndex);
    }

    private void resize() {
        int oldCapacity = table.length;
        int newCapacity = oldCapacity >= MAXIMUM_CAPACITY ? MAXIMUM_CAPACITY : oldCapacity << 1;
        threshold = (int) (newCapacity * loadFactor);
        Entry<K, V>[] newTable = new Entry[newCapacity];

        for (Map.Entry<K, V> e : entrySet()) {
            K key = e.getKey();
            V value = e.getValue();
            int hash = key == null ? 0 : key.hashCode();
            int i = hash(key);

            newTable[i] = getEntry(key, value, newTable[i]);
        }
    }

    private Entry<K, V> getEntry(K key, V value, Entry<K, V> next) {
        return new Entry<>(key, value, next);
    }

    public static class Entry<K, V> implements Map.Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        public Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) o;
            Object k1 = this.getKey();
            Object k2 = entry.getKey();
            if (k1 == k2 || (k1 != null && k1.equals(k2))) {
                Object v1 = this.getValue();
                Object v2 = entry.getValue();
                if (v1 == v2 || (v1 != null && v1.equals(v2))) {
                    return true;
                }
            }

            return false;
        }

        public int hashCode() {
            return Objects.hashCode(getKey()) ^ Objects.hashCode(getValue());
        }

        @Override
        public String toString() {
            return getKey() + "=" + getValue();
        }
    }

    public Set<Map.Entry<K, V>> entrySet() {
        return null;
    }
}

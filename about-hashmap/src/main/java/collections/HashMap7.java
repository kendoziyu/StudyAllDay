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

    private EntrySet entrySet;

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
    public V get(Object key) {
        if (key == null) {
            return getForNullKey();
        }

        Entry<K, V> entry = getEntry(key);
        return entry == null ? null : entry.getValue();
    }

    private V getForNullKey() {
        if (size == 0) {
            return null;
        }
        for (Entry<K, V> e = table[0]; e != null; e = e.next) {
            if (e.key == null) {
                return e.value;
            }
        }
        return null;
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
//        if (size >= threshold && table.length <= MAXIMUM_CAPACITY) {
//            resize();
//        }
        if (size >= threshold && null != table[bucketIndex]) {
            resize(table.length * 2);
            int hash = key == null ? 0 : hash(key);
            bucketIndex = indexFor(hash, table.length);
        }

        createEntry(key, value, bucketIndex);
    }

    private void resize(int newCapacity) {
//        int oldCapacity = table.length;
//        int newCapacity = oldCapacity >= MAXIMUM_CAPACITY ? MAXIMUM_CAPACITY : oldCapacity << 1;

        Entry[] oldTable = table;
        int oldCapacity = oldTable.length;
        if (oldCapacity == MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return;
        }

//        threshold = (int) (newCapacity * loadFactor);
//        Entry<K, V>[] newTable = new Entry[newCapacity];
//
//        for (Map.Entry<K, V> e : entrySet()) {
//            K key = e.getKey();
//            V value = e.getValue();
//            int hash = key == null ? 0 : key.hashCode();
//            int i = hash(key);
//
//            newTable[i] = getEntry(key, value, newTable[i]);
//        }
        Entry[] newTable = new Entry[newCapacity];
        transfer(newTable);
        table = newTable;
        threshold = (int) (newCapacity * loadFactor);
    }


    private void transfer(Entry[] newTable) {
        int newCapacity = newTable.length;
        Entry[] oldTable = table;
//        for (int i = 0; i < oldCapacity; i++) {
        for (Entry e : oldTable) {
//            Entry e = oldTable[i];
            while (e != null) {
                Entry next = e.next;

//                int hash = hash(e.getKey());
//                int newIndex = indexFor(hash, newCapacity);
//                newTable[newIndex] = new Entry(e.getKey(), e.getValue(), newTable[newIndex]);
//                e = e.next;
                int hash = hash(e.key);
                int i = indexFor(hash, newCapacity);
                e.next = newTable[i];
                newTable[i] = e;
                e = next;
            }
        }
    }

//    private Entry<K, V> getEntry(K key, V value, Entry<K, V> next) {
//        return new Entry<>(key, value, next);
//    }


    @Override
    public void clear() {
        // super.clear();
        Arrays.fill(table, null);
        size = 0;
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
        return entrySet0();
    }

    private Set<Map.Entry<K, V>> entrySet0() {
        Set<Map.Entry<K, V>> es = entrySet;
        return es != null ? es : (entrySet = new EntrySet());
//        return new AbstractSet<Map.Entry<K, V>>() {
//            @Override
//            public Iterator<Map.Entry<K, V>> iterator() {
//                return new HashIterator();
//            }
//
//            @Override
//            public int size() {
//                return size;
//            }
//        };
    }

    private class EntrySet extends AbstractSet<Map.Entry<K, V>> {

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return newEntryIterator();
        }

        @Override
        public int size() {
            return size;
        }

        @Override
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<K, V> e = (Map.Entry<K, V>) o;
            Entry<K, V> candidate = getEntry(e.getKey());
            return candidate != null && candidate.equals(e);
        }

        @Override
        public boolean remove(Object o) {
            return removeMapping(o) != null;
        }

        @Override
        public void clear() {
//             super.clear();
            HashMap7.this.clear();
        }
    }


    Entry<K, V> removeMapping(Object o) {
        if (size == 0 || !(o instanceof Map.Entry)) {
            return null;
        }

        Map.Entry<K, V> entry = (Map.Entry<K, V>) o;
        Object key = entry.getKey();
        int hash = (key == null) ? 0 : hash(key);
        int i = indexFor(hash, table.length);

        Entry<K, V> e = table[i];
        Entry<K, V> prev = e;
        while (e != null) {
            Entry<K, V> next = e.next;
            if (e.equals(entry)) {
                size--;
                if (e == prev) {
                    table[i] = next;
                } else {
                    prev.next = next;
                }
                // 找到了目标元素，可以直接返回了
                return e;
            }

            prev = e;
            e = next;
        }
        return e;
    }


    final Entry<K, V> getEntry(Object key) {
        if (size == 0) {
            return null;
        }

        int hash = key == null ? 0 : hash(key);
        int i = indexFor(hash, table.length);
        for (Entry<K, V> e = table[i];
             e != null;
             e = e.next) {
            Object k;
            if ((k = e.key) == key || (k != null && k.equals(key))) {
                return e;
            }
        }
        return null;
    }

    private abstract class HashIterator<E> implements Iterator<E> {
        Entry<K, V> current;
        Entry<K, V> next;
        int index;

        public HashIterator() {
            // 找到第一个元素，赋值给next
//            for (index = 0; (next = table[index]) == null; index++)
//                ;
            if (size > 0) {
                Entry[] t = table;
                while (index < t.length && (next = t[index++]) == null)
                    ;
            }
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        Entry<K, V> nextEntry() {
//            if (size == 0) return null;
            if (next == null) {
                throw new NoSuchElementException();
            }

            Entry<K, V> e = next;
            if ((next = e.next) == null) {
                Entry[] t = table;
                while (index < t.length && (next = t[index++]) == null)
                    ;
            }
            current = e;
            return e;
//            if (e.next != null) {
//                next = e.next;
//                return current;
//            }
//            int capacity = table.length;
//            while ((e = table[index++]) == null && index < capacity)
//                ;
//
//            next = e;
//            return current;
        }

        @Override
        public void remove() {
            if (current == null) {
                throw new IllegalStateException();
            }
            Object k = current.getKey();
            current = null;
            HashMap7.this.removeEntryForKey(k);
        }
    }

    private Entry<K, V> removeEntryForKey(Object key) {
        if (size == 0) {
            return null;
        }

        int hash = key == null ? 0 : hash(key);
        int i = indexFor(hash, table.length);

        // 因为需要删除元素，所以需要保存前一个元素
        Entry prev = table[i];
        // 这个是需要删除的元素
        Entry e = prev;

        while (e != null) {
            // 使用了多次e.next
            Entry next = e.next;
            Object k;
            // 判断键相等，执行删除
            if ((k = e.key) == key || (k != null && k.equals(key))) {
                size--;
                if (prev == e) {
                    table[i] = next;
                } else {
                    prev.next = next;
                }
                return e;
            }

            prev = e;
            e = next;
        }

        return e;
    }

    private class EntryIterator extends HashIterator<Map.Entry<K, V>> {

        @Override
        public Map.Entry<K, V> next() {
            return nextEntry();
        }
    }

    private class KeyIterator extends HashIterator<K> {

        @Override
        public K next() {
            Entry<K, V> e = nextEntry();
            return e == null ? null : e.getKey();
        }
    }

    private class ValueIterator extends HashIterator<V> {

        @Override
        public V next() {
            Entry<K, V> e = nextEntry();
            return e == null ? null : e.getValue();
        }
    }

    Iterator<K> newKeyIterator() {
        return new KeyIterator();
    }

    Iterator<V> newValueIterator() {
        return new ValueIterator();
    }

    Iterator<Map.Entry<K, V>> newEntryIterator() {
        return new EntryIterator();
    }


//    private class HashIterator implements Iterator<Map.Entry<K, V>> {
//        Entry<K, V> cursor;
//        int index = 0;
//
//        @Override
//        public boolean hasNext() {
//            return index < size;
//        }
//
//        @Override
//        public Entry<K, V> next() {
//            int capacity = table.length;
//            int hash = cursor == null ? 0 : hash(cursor.getKey());
//            int bucketIndex = cursor == null ? 0 : indexFor(hash, capacity);
//
//            cursor = cursor != null ? cursor.next : null;
//            if (cursor != null) {
//                index++;
//                return cursor;
//            }
//            Entry<K, V> e;
//            int i = index == 0 ? 0 : bucketIndex + 1;
//            for (; i < capacity; i++, bucketIndex++) {
//                e = table[i];
//                if (e != null) {
//                    cursor = e;
//                    index++;
//                    return cursor;
//                }
//            }
//            return null;
//        }
//
//        @Override
//        public void remove() {
//            if (cursor == null) {
//                return;
//            }
//            int capacity = table.length;
//            int hash = hash(cursor.getKey());
//            int bucketIndex = indexFor(hash, capacity);
//            Entry<K, V> e = table[bucketIndex];
//            if (e == cursor) {
//                table[bucketIndex] = cursor.next;
//                index--;
//                return;
//            }
//            while (e.next != cursor) {
//                e = e.next;
//            }
//            e.next = cursor.next;
//            index--;
//        }
//    }
}

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
        // inflateTable(threshold);

        // putAllForCreate(m);
    }

    @Override
    public V put(K key, V value) {
        return super.put(key, value);
    }

    public static class Entry<K, V> implements Map.Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

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

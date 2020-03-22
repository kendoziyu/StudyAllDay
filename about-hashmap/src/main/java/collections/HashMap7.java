package collections;

import java.util.*;

/**
 * 描述: 尝试自己实现一个HashMap，来理解HashMap的原理 <br>
 *
 * @author: skilled-peon <br>
 * @date: 2020/3/21 0021 <br>
 */
public class HashMap7<K, V> extends AbstractMap<K, V> {

    Entry<K, V>[] table;

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

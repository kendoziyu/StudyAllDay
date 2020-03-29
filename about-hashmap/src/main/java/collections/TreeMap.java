package collections;

import java.util.*;

/**
 * 描述:  尝试自己实现一下红黑树<br>
 *
 * @author: skilled-peon <br>
 * @date: 2020/3/29 0029 <br>
 */
public class TreeMap<K, V> extends AbstractMap<K, V> {

    private Entry<K, V> root;
    private int size;
    private Comparator<? super K> comparator;

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return null;
    }

    @Override
    public V put(K key, V value) {
        Entry<K, V> e = root;
        if (e == null) {
            root = new Entry<>(key, value, null);
            size++;
            return null;
        }
        Entry<K, V> parent;
        int cmp = 0;
        Comparator<? super K> cpr = this.comparator;
        if (cpr != null) {
            // 使用比较器来比较
            do {
                parent = e;
                cmp = cpr.compare(parent.key, key);
                if (cmp > 0) {
                    e = e.left;
                } else if (cmp < 0) {
                    e = e.right;
                } else {
                    return e.setValue(value);
                }
            } while (e != null);
//            while (parent != null) {
//                cmp = cpr.compare(parent.key, key);
//                if (cmp > 0) {
//                    // 继续从左子树搜索
//                    e = parent;
//                    parent = e.left;
//                } else if (cmp < 0) {
//                    // 继续从右子树搜索
//                    e = parent;
//                    parent = e.right;
//                } else {
                    // 替换结点的关键字
//                    V oldValue = e.value;
//                    e.value = value;
//                    return oldValue;
//                    return e.setValue(value);
//                }
//            }
        } else {
            // 直接用对象比较
//            Comparable<K> comp = (Comparable<K>) parent.key;
//            while (parent != null) {
//                cmp = comp.compareTo(key);
//                if (cmp > 0) {
//                    // 继续从左子树搜索
//                    e = parent;
//                    parent = e.left;
//                } else if (cmp < 0) {
//                    // 继续从右子树搜索
//                    e = parent;
//                    parent = e.right;
//                } else {
//                    // 替换结点的关键字
//                    V oldValue = e.value;
//                    e.value = value;
//                    return oldValue;
//                }
//            }
            if (key == null) {
                throw new NullPointerException();
            }
            Comparable<? super K> k = (Comparable<? super K>) key;
            do {
                parent = e;
                cmp = k.compareTo(e.key);
                if (cmp < 0) {
                    e = e.left;
                } else if (cmp > 0) {
                    e = e.right;
                } else {
                    return e.setValue(value);
                }
            } while (e != null);

        }

        Entry<K, V> node = new Entry<>(key, value, parent);
        if (cmp > 0) {
            parent.left = node;
        } else {
            parent.right = node;
        }
        fixAfterInsertion(node);
        size++;
        return null;
    }

    private void fixAfterInsertion(Entry<K, V> node) {

    }

    static <K, V> boolean colorOf(Entry<K, V> node) {
        return node == null ? BLACK : node.color;
    }

    static <K, V> Entry<K, V> parentOf(Entry<K, V> node) {
        return node == null ? null : node.parent;
    }

    static <K, V> Entry<K, V> leftOf(Entry<K, V> node) {
        return node == null ? null : node.left;
    }

    static <K, V> Entry<K, V> rightOf(Entry<K, V> node) {
        return node == null ? null : node.right;
    }

    // 红黑机制
    private static final boolean BLACK = true;
    private static final boolean RED = false;

    static class Entry<K, V> implements Map.Entry<K, V> {
        K key;
        V value;
        Entry<K, V> left;
        Entry<K, V> right;
        Entry<K, V> parent;
        boolean color = BLACK;

        public Entry(K key, V value, Entry<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        @Override
        public String toString() {
            return getKey() + "=" + getValue();
        }

        @Override
        public int hashCode() {
            int keyHash = key == null ? 0 : key.hashCode();
            int valueHash = value == null ? 0 : value.hashCode();
            return keyHash ^ valueHash;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> entry = (Map.Entry<?, ?>) o;
            return valEquals(entry.getKey(), key) && valEquals(entry.getValue(), value);
        }
    }

    static boolean valEquals(Object a, Object b) {
//        return a == b || (a != null && a.equals(b));
        return Objects.equals(a, b);
    }
}

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

    private void fixAfterInsertion(Entry<K, V> c) {
//        if (c == root) {
//            c.color = BLACK;
//            return;
//        }
//        c.color = RED;
//        if (colorOf(parentOf(c)) == BLACK) {
//            return;
//        }
        c.color = RED;
        while (c != null && c != this.root && c.parent.color == RED) {
            if (leftOf(parentOf(parentOf(c))) == parentOf(c)) {
                // 父结点是祖父结点的左结点
                Entry<K, V> uncle = rightOf(parentOf(parentOf(c)));
                if (colorOf(uncle) == RED) {
                    // 叔叔结点是红色
//                parentOf(c).color = BLACK;
//                rightOf(parentOf(parentOf(c))).color = BLACK;
//                parentOf(parentOf(c)).color = RED;
                    setColor(parentOf(c), BLACK);
                    setColor(uncle, BLACK);
                    setColor(parentOf(parentOf(c)), RED);
                    // 把祖父结点当做新增结点，重新调整
//                fixAfterInsertion(parentOf(parentOf(c)));
                    c = parentOf(parentOf(c));
                } else {
//                    Entry<K, V> tree = parentOf(parentOf(c));
//                    Entry<K, V> parent = parentOf(c);
                    // 叔叔结点不存在或者是黑色
                    if (rightOf(parentOf(c)) == c) {
                        // 4.2 三角关系
//                        leftRotate(parentOf(c));
                        c = parentOf(c);
                        leftRotate(c);
                    }

//                    tree.color = !tree.color;
//                    parent.color = !parent.color;
                    // 4.1 三点一线
//                    rightRotate(tree);
                    setColor(parentOf(c), BLACK);
                    setColor(parentOf(parentOf(c)), RED);
                    rightRotate(parentOf(parentOf(c)));
                }
            } else {
                // 父结点是祖父结点的右结点
                Entry<K, V> uncle = leftOf(parentOf(parentOf(c)));
                if (colorOf(uncle) == RED) {
                    // 叔叔结点是红色
//                    parentOf(c).color = BLACK;
//                    leftOf(parentOf(parentOf(c))).color = BLACK;
//                    parentOf(parentOf(c)).color = RED;
                    setColor(parentOf(c), BLACK);
                    setColor(uncle, BLACK);
                    setColor(parentOf(parentOf(c)), RED);
                    // 把祖父结点当做新增结点，重新调整
//                    fixAfterInsertion(parentOf(parentOf(c)));
                    c = parentOf(parentOf(c));
                } else {
//                    Entry<K, V> tree = parentOf(parentOf(c));
//                    Entry<K, V> parent = parentOf(c);
                    // 叔叔结点不存在或者是黑色
                    if (leftOf(parentOf(c)) == c) {
                        // 4.2 三角关系
                        c = parentOf(c);
                        rightRotate(c);
                    }
                    // 4.1 三点一线
//                    tree.color = !tree.color;
//                    parent.color = !parent.color;
//                    leftRotate(tree);
                    setColor(parentOf(c), BLACK);
                    setColor(parentOf(parentOf(c)), RED);
                    leftRotate(parentOf(parentOf(c)));
                }
            }
        }
        root.color = BLACK;
    }

    private void leftRotate(Entry<K, V> root) {
        if (root != null) {
            Entry<K, V> pivot = root.right;
            root.right = pivot.left;
            if (pivot.left != null)
                pivot.left.parent = root;
            pivot.parent = root.parent;
            if (root.parent == null)
                this.root = pivot;
            else if (root.parent.left == root)
                root.parent.left = pivot;
            else
                root.parent.right = pivot;
            pivot.left = root;
            root.parent = pivot;
//            root.right = null;
        }
    }

    private void rightRotate(Entry<K, V> root) {
        if (root != null) {
            Entry<K, V> pivot = root.left;
            root.left = pivot.right;
            if (pivot.right != null)
                pivot.right.parent = root;
            pivot.parent = root.parent;
            if (root.parent == null)
                this.root = pivot;
            else if (root.parent.left == root)
                root.parent.left = pivot;
            else
                root.parent.right = pivot;
            pivot.right = root;
            root.parent = pivot;
//            root.left = null;
        }
    }



    static <K, V> void setColor(Entry<K, V> node, boolean color) {
        if (node != null) {
            node.color = color;
        }
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

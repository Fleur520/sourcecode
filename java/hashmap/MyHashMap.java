import java.util.Objects;

/** 用数组和链表实现的学习版散列表，允许 null 键和值。 */
public class MyHashMap<K, V> {
    private static final int INITIAL_CAPACITY = 16;
    private static final int MAX_CAPACITY = 1 << 30;
    private static final float LOAD_FACTOR = 0.75f;

    private static final class Node<K, V> {
        final int hash;
        final K key;
        V value;
        Node<K, V> next;

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private Node<K, V>[] table;
    private int size;
    private int threshold;

    @SuppressWarnings("unchecked")
    public MyHashMap() {
        table = (Node<K, V>[]) new Node[INITIAL_CAPACITY];
        threshold = (int) (INITIAL_CAPACITY * LOAD_FACTOR);
    }

    private int hash(Object key) {
        int h = key == null ? 0 : key.hashCode();
        return h ^ (h >>> 16);
    }

    public V put(K key, V value) {
        int hash = hash(key);
        int index = hash & (table.length - 1);
        for (Node<K, V> node = table[index]; node != null; node = node.next) {
            if (node.hash == hash && Objects.equals(node.key, key)) {
                V previous = node.value;
                node.value = value;
                return previous;
            }
        }
        table[index] = new Node<>(hash, key, value, table[index]);
        if (++size > threshold) resize();
        return null;
    }

    private Node<K, V> find(Object key) {
        int hash = hash(key);
        for (Node<K, V> node = table[hash & (table.length - 1)]; node != null; node = node.next) {
            if (node.hash == hash && Objects.equals(node.key, key)) return node;
        }
        return null;
    }

    public V get(Object key) {
        Node<K, V> node = find(key);
        return node == null ? null : node.value;
    }

    public boolean containsKey(Object key) {
        return find(key) != null;
    }

    public V remove(Object key) {
        int hash = hash(key);
        int index = hash & (table.length - 1);
        Node<K, V> previous = null;
        for (Node<K, V> node = table[index]; node != null; node = node.next) {
            if (node.hash == hash && Objects.equals(node.key, key)) {
                if (previous == null) table[index] = node.next;
                else previous.next = node.next;
                size--;
                return node.value;
            }
            previous = node;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        if (table.length == MAX_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return;
        }
        Node<K, V>[] expanded = (Node<K, V>[]) new Node[table.length * 2];
        for (Node<K, V> head : table) {
            while (head != null) {
                Node<K, V> next = head.next;
                int index = head.hash & (expanded.length - 1);
                head.next = expanded[index];
                expanded[index] = head;
                head = next;
            }
        }
        table = expanded;
        threshold = (int) (table.length * LOAD_FACTOR);
    }

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }

    public void clear() {
        java.util.Arrays.fill(table, null);
        size = 0;
    }
}


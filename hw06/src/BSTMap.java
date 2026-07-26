import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    /**
     * Associates the specified value with the specified key in this map.
     * If the map already contains the specified key, replaces the key's mapping
     * with the value specified.
     *
     * @param key
     * @param value
     */
    private BSTNode root;
    private int size;
    public BSTMap() {
        root = null;
        size = 0;
    }

    private class BSTNode {
        K  key;
        V value;
        BSTNode left;
        BSTNode right;

        private BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }



    private BSTNode putHelper(K key, V value, BSTNode node) {
        if (node == null) {
            size++;
            return new BSTNode(key, value);
        }
        if (key.compareTo(node.key) < 0) {
            node.left = putHelper(key, value,node.left);
        }
        if (key.compareTo(node.key) > 0) {
            node.right = putHelper(key, value,node.right);
        }
        if (key.compareTo(node.key) == 0) {
            node.value = value;
        }
        return node;
    }

    @Override
    public void put(K key, V value) {
        if(key == null){
            throw new NullPointerException("key is null");
        }
        root = putHelper(key, value, root);
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    private V getHelper(K key,  BSTNode node) {
        if (node == null) {
            return null;
        }
        if (key.compareTo(node.key) < 0) {
            return getHelper(key, node.left);
        }
        if (key.compareTo(node.key) > 0) {
            return getHelper(key, node.right);
        }
        return node.value;
    }

    @Override
    public V get(K key) {
        V value = getHelper(key, root);
        if (key == null) {
            throw new RuntimeException("no that key");
        }
        return value;
    }

    private boolean containsHelper(BSTNode curr, K targetKey) {
        // 走到空节点 = 不存在该key
        if (curr == null) {
            return false;
        }
        // 比较key大小
        int cmp = targetKey.compareTo(curr.key);
        if (cmp < 0) {
            // 目标更小，去左树查找
            return containsHelper(curr.left, targetKey);
        } else if (cmp > 0) {
            // 目标更大，去右树查找
            return containsHelper(curr.right, targetKey);
        } else {
            // cmp == 0，key匹配，存在
            return true;
        }
    }
    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        return containsHelper(root, key);
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        return Set.of();
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key can not be null");
        }
        // 先查询是否存在该key，不存在直接返回null
        BSTNode target = findNode(root, key);
        if (target == null) {
            return null;
        }
        V oldVal = target.value;
        // 执行删除，更新根
        root = removeHelper(root, key);
        size -= 1;
        return oldVal;
    }

    private BSTNode getMinNode(BSTNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    private BSTNode removeMin(BSTNode node) {
        if (node.left == null) {
            return node.right;
        }
        node.left = removeMin(node.left);
        return node;
    }

    private BSTNode removeHelper(BSTNode curr, K targetKey) {
        if (curr == null) return null;

        int cmp = targetKey.compareTo(curr.key);
        if (cmp < 0) {
            // 去左子树删
            curr.left = removeHelper(curr.left, targetKey);
        } else if (cmp > 0) {
            // 去右子树删
            curr.right = removeHelper(curr.right, targetKey);
        } else {
            // ========== 找到要删除的节点 curr ==========
            // 情况1：没有左孩子 ｜ 情况2：没有右孩子
            if (curr.left == null) return curr.right;
            if (curr.right == null) return curr.left;

            // 情况3：左右都有孩子 → Hibbard 删除核心
            // 1. 取出右子树最小节点
            BSTNode minRight = getMinNode(curr.right);
            // 2. 覆盖当前节点的key、value（不用删当前节点，替换内容）
            curr.key = minRight.key;
            curr.value = minRight.value;
            // 3. 删掉右子树里那个被拿来顶替的最小节点
            curr.right = removeMin(curr.right);
        }
        return curr;
    }

    private BSTNode findNode(BSTNode curr, K target) {
        if (curr == null) return null;
        int cmp = target.compareTo(curr.key);
        if (cmp < 0) return findNode(curr.left, target);
        else if (cmp > 0) return findNode(curr.right, target);
        else return curr;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        return null;
    }
}

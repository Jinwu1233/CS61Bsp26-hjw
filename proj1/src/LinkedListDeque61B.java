import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    private int size = 0;
    private Node sentinel = new Node();

    private class Node{
        Node prev;
        T data;
        Node next;
        private Node(){
            this.data = null;
            this.prev = null;
            this.next = null;
        }

        private Node(T data){
            this.data = data;
            this.prev = null;
            this.next = null;
        }
    }

    public LinkedListDeque61B() {
        this.size = 0;
        sentinel.data  = null;
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    @Override
    public void addFirst(T x) {
        Node oldFirst = sentinel.next;
        Node newFirst = new Node(x);
        sentinel.next = newFirst;
        newFirst.prev = sentinel;
        newFirst.next = oldFirst;
        oldFirst.prev = newFirst;
        size++;
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addLast(T x) {
        Node oldLast =  sentinel.prev;
        Node newLast = new Node(x);
        oldLast.next = newLast;
        newLast.prev = oldLast;
        sentinel.prev = newLast;
        newLast.next = sentinel;
        size++;
    }

    /**
     * Returns a List copy of the deque. Does not alter the deque.
     *
     * @return a new list copy of the deque.
     */
    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node p = sentinel.next;
        while (p != sentinel) {
            returnList.add(p.data);
            p = p.next;
        }
        return returnList;
    }

    /**
     * Returns if the deque is empty. Does not alter the deque.
     *
     * @return {@code true} if the deque has no elements, {@code false} otherwise.
     */
    @Override
    public boolean isEmpty() {
        return sentinel.next.equals(sentinel);
    }

    /**
     * Returns the size of the deque. Does not alter the deque.
     *
     * @return the number of items in the deque.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Return the element at the front of the deque, if it exists.
     *
     * @return element, otherwise {@code null}.
     */
    @Override
    public T getFirst() {
        return sentinel.next.data;
    }

    /**
     * Return the element at the back of the deque, if it exists.
     *
     * @return element, otherwise {@code null}.
     */
    @Override
    public T getLast() {
        return sentinel.prev.data;
    }

    /**
     * Remove and return the element at the front of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeFirst() {
        if(this.isEmpty()){
            throw new NoSuchElementException();
        }
        Node removed = sentinel.next;
        sentinel.next = removed.next;
        sentinel.next.prev = sentinel;
        removed.next = null;
        removed.prev = null;
        return removed.data;
    }

    /**
     * Remove and return the element at the back of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeLast() {
        if(this.isEmpty()){
            throw new NoSuchElementException();
        }
        Node removed = sentinel.prev;
        sentinel.prev = removed.prev;
        removed.prev.next = sentinel;
        removed.prev = null;
        removed.next = null;
        return removed.data;
    }

    /**
     * The Deque61B abstract data type does not typically have a get method,
     * but we've included this extra operation to provide you with some
     * extra programming practice. Gets the element, iteratively. Returns
     * null if index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T get(int index) {
        if(index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        Node p = sentinel.next;
        while (p != sentinel && index != 0) {
            p = p.next;
            index--;
        }
        return p.data;
    }

    /**
     * This method technically shouldn't be in the interface, but it's here
     * to make testing nice. Gets an element, recursively. Returns null if
     * index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T getRecursive(int index) {
        if(index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        return getRecursiveHelper(index, sentinel.next);
    }

    private T getRecursiveHelper(int index, Node p) {
        if(index == 0){
            return p.data;
        }
        return getRecursiveHelper(index-1, p.next);
    }

    public static void main(String[] args) {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();
        lld.addLast(0);   // [0]
        lld.addLast(1);   // [0, 1]
        lld.addFirst(-1); // [-1, 0, 1]
    }
}

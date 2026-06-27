import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class ArrayDeque61B<T> implements Deque61B<T> {
    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addFirst(T x) {
        Items[nextFirst] = x;
        nextFirst = Math.floorMod(nextFirst - 1, capacity);
        size++;
        if(size > capacity * 0.75){
            this.Items = resizeUp();
        }
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addLast(T x) {
        Items[nextLast] = x;
        nextLast = Math.floorMod(nextLast + 1, capacity);
        size++;
        if(size > capacity * 0.75){
            this.Items = resizeUp();
        }
    }

    /**
     * Returns a List copy of the deque. Does not alter the deque.
     *
     * @return a new list copy of the deque.
     */
    @Override
    public List<T> toList() {
        List<T> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            result.add(this.get(i));
        }
        return result;
    }

    /**
     * Returns if the deque is empty. Does not alter the deque.
     *
     * @return {@code true} if the deque has no elements, {@code false} otherwise.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
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
        return this.isEmpty() ? null : Items[Math.floorMod(nextFirst + 1, capacity)];
    }

    /**
     * Return the element at the back of the deque, if it exists.
     *
     * @return element, otherwise {@code null}.
     */
    @Override
    public T getLast() {
        return this.isEmpty() ? null : Items[Math.floorMod(nextLast - 1, capacity)];
    }

    /**
     * Remove and return the element at the front of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeFirst() {
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        T removed = this.get(0);
        nextFirst =  Math.floorMod(nextFirst + 1, capacity);
        size --;
        if(size < capacity * 0.25){
            this.Items = resizeDown();
        }
        return removed;
    }

    /**
     * Remove and return the element at the back of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeLast() {
        if (isEmpty()){
            throw new NoSuchElementException();
        }
        T removed = this.get(size - 1);
        nextLast =   Math.floorMod(nextLast - 1, capacity);
        size --;
        if(size < capacity * 0.25){
            this.Items = resizeDown();
        }
        return removed;
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
        return index >= 0 && index < size? Items[Math.floorMod(nextFirst + index + 1, capacity)] : null;
    }

    public T[] resizeUp() {
        T[] result = (T[]) new Object[capacity * 2];
        for (int i = 0; i < size; i++) {
            result[i] = this.get(i);
        }
        nextLast = Math.floorMod(size , capacity);
        nextFirst = Math.floorMod(-1 , capacity);
        return result;
    }

    public T[] resizeDown() {
        T[] result = (T[]) new Object[capacity / 2];
        for (int i = 0; i < size; i++) {
            result[i] = this.get(i);
        }
        nextLast = Math.floorMod(size , capacity);
        nextFirst = Math.floorMod(-1 , capacity);
        return result;
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
        throw new UnsupportedOperationException("No need to implement getRecursive for ArrayDeque61B.");
    }

    private int nextFirst;
    private int nextLast;
    private int size;
    private T[] Items;
    private int capacity;
    public ArrayDeque61B(){
        capacity = 8;
        Items = (T[]) new Object[capacity];
        nextFirst = 0;
        nextLast = 1;
        size = 0;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return new arrayDequeIterator();
    }

    public class arrayDequeIterator implements Iterator<T> {
        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        private int wizPos = 0;
        public arrayDequeIterator() {
            wizPos = 0;
        }
        @Override
        public boolean hasNext() {
            return wizPos < size;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public T next() {
                wizPos++;
                return (T) Items[Math.floorMod(nextFirst + wizPos, capacity)];
        }
    }

    @Override
    public boolean equals(Object o) {

        if(o == this){
            return true;
        }
        if(!(o instanceof Deque61B)){
            return false;
        }

        Deque61B<T> d = (Deque61B<T>) o;
        if(!(d.size() == this.size)){
            return false;
        }
        for(T i : this){
            for(T j : d){
                if(i.equals(j)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(T i : this){
            sb.append(i).append(",");
        }
        sb.append("]");
        return sb.toString();
    }
}

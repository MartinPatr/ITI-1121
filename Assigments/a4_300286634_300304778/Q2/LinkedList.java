import java.util.NoSuchElementException;

public class LinkedList<E> implements List<E> {

    private static class Node<T> {

        private T value;
        private Node<T> prev;
        private Node<T> next;

        private Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    private Node<E> head;
    private int size;

    public LinkedList() {
        head = new Node<E>(null, null, null); // dummy node!
        head.prev = head.next = head;
        size = 0;
    }

    private class LinkedListIterator implements Iterator<E> {

        private Node<E> current = head;
        private int index = 0;
        
        public boolean hasNext() {
            return (current.next != head);
        }

        public E next() {

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            current = current.next;
            index++;

            return current.value;
        }

        public int nextIndex(){
            return index;
        }
    }

    public Iterator<E> iterator() {
        return new LinkedListIterator();
    }

    public Iterator<E> iterator(int nextIndex) {
        //Check if nextIndex is out of bounds
        if (nextIndex < 0 || nextIndex > size) {
            throw new IndexOutOfBoundsException("nextIndex = out of bounds");
        }
        LinkedListIterator it = new LinkedListIterator();
        for (int i = 0; i < nextIndex; i++) {
            it.next();
        }
        return it;
    }

    public Iterator<E> iterator(Iterator<E> other) {
        //Check if other is null
        if (other == null) {
            throw new NullPointerException();
        }

        LinkedListIterator it = new LinkedListIterator();
        for (int i = 0; i < other.nextIndex(); i++) {
            it.next();
        }
        return it;
    }


    public int size() {
        return size;
    }

    public E get(int index) {

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }

        Node<E> p = head.next;

        for (int i = 0; i < index; i++) {
            p = p.next;
        }

        return p.value;
    }

    public void addFirst(E elem) {

        if (elem == null) {
            throw new NullPointerException();
        }

        Node<E> second = head.next;

        head.next = new Node<E>(elem, head, second);
        second.prev = head.next;

        size++;
    }

    public void add(E elem) {

        if (elem == null) {
            throw new NullPointerException();
        }

        Node<E> before = head.prev, after = head;

        before.next = new Node<E>(elem, before, after);
        after.prev = before.next;

        size++;
    }
    
}

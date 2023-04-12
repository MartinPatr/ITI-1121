
public class OrderedList implements OrderedStructure {

    // Implementation of the doubly linked nodes (nested-class)

    private static class Node {

        private Comparable value;
        private Node previous;
        private Node next;
    
        private Node(Comparable value, Node previous, Node next) {
            this.value = value;
            this.previous = previous;
            this.next = next;
        }
    }
    
    // Instance variables
    
    Node head;
    
    // Representation of the empty list.
    
    public OrderedList() {
        head = null;
    }
    
    // Calculates the size of the list
    
    public int size() {
        int size = 0;
        Node current = head;
        while (current != null) {
            size++;
            current = current.next;
        }
        return size;
    }
    
    public Object get(int pos) {
        if (pos < 0 || pos >= size()) {
            throw new IndexOutOfBoundsException("Index out of bound");
        }
        Node current = head;
        for (int i = 0; i < pos; i++) {
            current = current.next;
        }
        return current.value;
    }
    
    // Adding an element while preserving the order
    
    public boolean add(Comparable o) {
        if (o == null) {
            throw new IllegalArgumentException("null");
        }
        if (head == null) {
            head = new Node(o, null, null);
            return true;
        }
        Node current = head;
        while (current != null) {
            // Checking if the same
            if (current.value.compareTo(o) == 0) {
                return false;
            }
            // Checking if the new element is smaller
            if (current.value.compareTo(o) > 0) {
                if (current.previous == null) {
                    current.previous = new Node(o, null, current);
                    head = current.previous;
                    return true;
                }
                else{
                    current.previous.next = new Node(o, current.previous, current);
                    current.previous = current.previous.next;
                    return true;
                }
            }
            // Checking if we are at the last element
            if (current.next == null) {
                current.next = new Node(o, current, null);
                return true;
            }
            // Moving to the next element
            current = current.next;
        }
        return false;
    }
    
    // Removes one item from the position pos.
    
    public void remove(int pos) {
        if (pos < 0 || pos >= size()) {
            throw new IndexOutOfBoundsException("Index out of bound");
        }
        Node current = head;
        if (pos == 0) {
            head = head.next;
            if (head != null) {
                head.previous = null;
            }
            return;
        }
        for (int i = 0; i < pos; i++) {
            current = current.next;
        }
        current.previous.next = current.next;
        if (current.next != null) {
            current.next.previous = current.previous;
        }
    }
    
    // Knowing that both lists store their elements in increasing
    // order, both lists can be traversed simultaneously.
    public void merge(OrderedList other) {
        Node current = other.head;
        while (current != null) {
            this.add(current.value);
            current = current.next;
        }
    }
}
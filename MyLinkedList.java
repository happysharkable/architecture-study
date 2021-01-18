package lesson4;

import java.util.Iterator;

public class MyLinkedList<T> implements Iterable<T>{
    private Node first;
    private Node last;
    private int size;

    @Override
    public Iterator<T> iterator() {
        return new Iter();
    }

    public MyLinkedList() {
        first = null;
        last = null;
    }

    private class Node {
        T value;
        Node next;
        Node previous;

        public Node(T value, Node next) {
            this.value = value;
            this.next = next;
        }

        public Node(T value) {
            this.value = value;
        }

        public Node(T value, Node next, Node previous) {
            this.value = value;
            this.next = next;
            this.previous = previous;
        }
    }

    private class Iter implements Iterator<T>{
        Node current = new Node(null, first);
        @Override
        public boolean hasNext() {
            return current.next != null;
        }

        @Override
        public T next() {
            current = current.next;
            return current.value;
        }
    }

    public void insertFirst(T item) {
        Node newNode = new Node(item, first);
        if (isEmpty()) {
            last = newNode;
        } else {
            first.previous = newNode;
        }
        first = newNode;
        size++;
    }

    public void insertLast(T item) {
        Node newNode = new Node(item);
        if (isEmpty()) {
            first = newNode;
        } else {
            newNode.previous = last;
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    public T deleteFirst() {
        if (isEmpty()) {
            throw new RuntimeException("List is empty");
        }
        Node oldFirst = first;
        first = first.next;
        if (isEmpty()) {
            last = null;
        } else {
            first.previous = null;
        }

        size--;
        return oldFirst.value;
    }

    public T deleteLast() {
        if (isEmpty()) {
            throw new RuntimeException("List is empty");
        }
        Node oldLast = last;
        if (last.previous == null) {
            first = null;
        } else {
            last.previous.next = null;
        }
        last = last.previous;
        size--;
        return oldLast.value;
    }

    public void insert(int index, T item) {
        if (index < 0 || index > size) {
            throw new RuntimeException("Incorrect index: " + index);
        }
        if (index == 0) {
            insertFirst(item);
            return;
        }
        if (index == size) {
            insertLast(item);
            return;
        }

        Node current = first;
        int i = 0;
        while (i < index - 1) {
            current = current.next;
            i++;
        }
        Node newNode = new Node(item, current.next);
        newNode.previous = current;
        current.next = newNode;
        newNode.next.previous = newNode;
        size++;
    }

    public boolean delete(T item) {
        if (isEmpty()) {
            return false;
        }
        if (first.value.equals(item)) {
            deleteFirst();
            return true;
        }

        Node current = first;
        while (current!= null && !current.value.equals(item)) {
            current = current.next;
        }
        if (current == null) {
            return false;
        }
        if(current == last){
            deleteLast();
            return true;
        }

        current.previous.next = current.next;
        current.next.previous = current.previous;
        size--;
        return true;
    }


    public T getFirst() {
        if (isEmpty()) {
            throw new RuntimeException("List is empty");
        }
        return first.value;
    }

    public T getLast() {
        if (isEmpty()) {
            throw new RuntimeException("List is empty");
        }
        return last.value;
    }

    public int indexOf(T item) {
        return index(item);
    }

    private int index(T item) {
        Node current = first;
        int index = 0;
        while (current != null) {
            if (current.value.equals(item)) {
                return index;
            }
            current = current.next;
            index++;
        }
        return -1;
    }

    public boolean contains(T item) {
        return index(item) > -1;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        Node current = first;
        StringBuilder sb = new StringBuilder("[");

        while (current != null) {
            sb.append(current.value).append(", ");
            current = current.next;
        }
        if (sb.length() > 2) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("]");

        return sb.toString();
    }
}

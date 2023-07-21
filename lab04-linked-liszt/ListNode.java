/**
 * A class representing a single element in a linked list.
 * 
 * Written 2023 Dan Barowy
 */
public class ListNode<E> {
    private E value; // the value of the element
    private ListNode<E> next; // a reference to the next node

    /**
     * Constructs a node with the given value.
     * 
     * @param value An element value of type E.
     */
    public ListNode(E value) {
        this.value = value;
    }

    /**
     * Returns the value stored in the ListNode.
     * 
     * @return The element value.
     */
    public E getValue() {
        return value;
    }

    /**
     * Changes the value stored in the ListNode.
     * 
     * @param value The new element value.
     */
    public void setValue(E value) {
        this.value = value;
    }

    /**
     * Returns a reference to the next ListNode in the linked
     * list. If the ListNode is the last element in the list,
     * returns null.
     * 
     * @return A reference to the next ListNode.
     */
    public ListNode<E> getNext() {
        return next;
    }

    /**
     * Changes the reference to the next ListNode.
     * 
     * @param node A reference to the new next ListNode.
     */
    public void setNext(ListNode<E> node) {
        next = node;
    }
}

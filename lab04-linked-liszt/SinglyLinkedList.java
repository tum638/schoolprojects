
/**
 * A SinglyLinkedList class.
 * 
 */

import java.util.ArrayList;

import structure5.*;

public class SinglyLinkedList<E> implements IList<E> {
    // the head node of the list
    private ListNode<E> head;
    // a variable to keep track of the size
    private int size = 0;

    /**
     * Returns the ListNode of the element at the given
     * index. Returns null if the index is out of bounds.
     * 
     * @pre 0 <= index < size()
     * @param index
     * @return The node, or null on failure.
     */
    private ListNode<E> nodeAt(int index) {
        Assert.pre(index >= 0, "The index " + index + " is a value greater than or equal to 0");
        Assert.pre(index <= this.size(), "The index " + index + " is a value not more than the size of the list.");
        // create an object handler pointing to head.
        ListNode<E> finger = head;
        // loop until you get to the position of the node you want.
        while (index > 0) {
            finger = finger.getNext();
            index--;
        }
        return finger;
    }

    /**
     * Add an object to tail of list.
     *
     * @post value is added to tail of list
     * 
     * @param value The value to be added to tail of list.
     * @see #addLast
     */
    public void add(E value) {
        this.addLast(value);
    }

    /**
     * Insert value at location.
     *
     * @pre 0 <= i <= size()
     * @post adds ith entry of list to value o
     * @param i index of this new value
     * @param o value to be stored
     */
    public void add(int i, E o) {
        Assert.pre(i >= 0, "The index, " + i + "  is a value greater than or equal to 0.");
        Assert.pre(i <= this.size(), "The index, " + i + " is a value not more than the size of the list.");
        // create a new node.
        ListNode<E> node = new ListNode<>(o);
        // check to see if list is empty, point the head to the node if it is.
        if (this.isEmpty()) {
            head = node;
            size++;
            return;
        }
        // create an object handler called finger.
        ListNode<E> finger = head;
        // check to see if the node needs to be inserted at the start of the list,
        // insert the node at start of list, and exit function
        // otherwise, skip the condition
        if (i == 0) {

            node.setNext(finger);
            head = node;
            size++;
            return;
        }
        // create an object handler to keep track of the previous node, intitialize it
        // to null.
        ListNode<E> prev = null;
        // loop until you find the position to put the node.
        while (i > 0) {
            prev = finger;
            finger = finger.getNext();
            i--;
        }

        // insert node at position.
        prev.setNext(node);
        prev = prev.getNext();
        prev.setNext(finger);
        size++;
        Assert.post(nodeAt(indexOf(o)).getValue().equals(o), "The value at " + indexOf(o) + " should be " + o);

    }

    /**
     * Add a value to the head of the list.
     *
     * @post value is added to beginning of list
     * 
     * @param value The value to be added to the head of the list.
     */
    public void addFirst(E value) {
        this.add(0, value);
        Assert.post(nodeAt(0).getValue().equals(value), "The value at index 0 should be " + value);
    }

    /**
     * Add a value to tail of list.
     *
     * @post value is added to end of list
     * 
     * @param value The value to be added to tail of list.
     */
    public void addLast(E value) {
        this.add(this.size(), value);
        Assert.post(nodeAt(this.size() - 1).getValue().equals(value),
                "The value at the end of the list should be " + value);
    }

    /**
     * Remove all elements of list.
     *
     * @post empties list
     */
    public void clear() {
        // point head to null, the rest of the nodes are garbage collected.
        head = null;
        size = 0;
        Assert.post(this.size() == 0, "The size of the list must be 0 after a successful clear");
    }

    /**
     * Check to see if a value is in list.
     *
     * @pre value is not null
     * @post returns true iff list contains an object equal to value
     * 
     * @param value value sought.
     * @return True if value is within list.
     */
    public boolean contains(E value) {
        Assert.pre(value != null, "The value is not null.");
        if (indexOf(value) != -1) {
            return true;
        }
        return false;

    }

    /**
     * Get value at location i.
     *
     * @pre 0 <= i < size()
     * @post returns object found at that location
     *
     * @param i position of value to be retrieved.
     * @return value retrieved from location i (returns null if i invalid)
     */
    public E get(int i) {
        Assert.pre(i >= 0, "Index is a value greater than or equal to 0.");
        Assert.pre(i <= this.size(), "Index is a value not more than the size of the list.");
        ListNode<E> node = nodeAt(i);
        return node.getValue();
    }

    /**
     * Fetch first element of list.
     *
     * @pre list is not empty
     * @post returns first value in list
     * 
     * @return A reference to first element of list.
     */
    public E getFirst() {
        Assert.pre(!this.isEmpty(), "The list is non-empty.");
        Assert.post(this.nodeAt(0).equals(head),
                "The value obtained from the first position should be " + head.getValue());
        return head.getValue();

    }

    /**
     * Fetch last element of list.
     *
     * @pre list is not empty
     * @post returns last value in list
     * 
     * @return A reference to last element of list.
     */
    public E getLast() {
        Assert.pre(!this.isEmpty(), "The list is non-empty.");
        ListNode<E> last = this.nodeAt(this.size() - 1);
        return last.getValue();
    }

    /**
     * Determine first location of a value in list.
     *
     * @pre value is not null
     * @post returns (0-origin) index of value,
     *       or -1 if value is not found
     * 
     * @param value The value sought.
     * @return index (0 is first element) of value, or -1
     */
    public int indexOf(E value) {
        Assert.pre(value != null, "The value is not null");
        int i = 0;
        ListNode<E> finger = head;
        while (finger != null) {
            if (finger.getValue().equals(value)) {
                return i;
            }
            finger = finger.getNext();
            i++;
        }
        return -1;
    }

    /**
     * Determine if list is empty.
     *
     * @post returns true iff list has no elements
     * 
     * @return True if list has no elements.
     */
    public boolean isEmpty() {
        if (head == null) {
            return true;
        }
        return false;
    }

    /**
     * 
     * @param i the index of the node where consecutives should come from
     * @return
     */
    protected ArrayList<ListNode<E>> consecutiveNodes(int i) {
        ArrayList<ListNode<E>> consecutiveNodes = new ArrayList<>();
        ListNode<E> prev = null;
        ListNode<E> finger = head;

        while (i > 0) {
            prev = finger;
            finger = finger.getNext();
            i--;
        }
        consecutiveNodes.add(prev);
        consecutiveNodes.add(finger);
        return consecutiveNodes;
    }

    /**
     * a function that removes a node from and returns it
     * 
     * @param nodes the list of consecutive nodes
     * @return return the node that has been removed from a list
     */
    protected ListNode<E> connectedPreviousToNextofCurrent(ArrayList<ListNode<E>> nodes) {
        ListNode<E> reject = nodes.get(1);
        nodes.get(0).setNext(nodes.get(1).getNext());
        size--;
        return reject;
    }

    /**
     * Remove and return value at location i.
     *
     * @pre 0 <= i < size()
     * @post removes and returns object found at that location
     *
     * @param i position of value to be retrieved.
     * @return value retrieved from location i (returns null if i invalid)
     */
    public E remove(int i) {
        if (i == 0) {
            E reject = head.getValue();
            head = head.getNext();
            size--;
            return reject;
        }

        return connectedPreviousToNextofCurrent(consecutiveNodes(i)).getValue();

    }

    /**
     * Remove a value from list. At most one of value
     * will be removed.
     *
     * @post removes and returns element equal to value
     * 
     * 
     * 
     * @param value The value to be removed.
     * @return The actual value removed.
     */
    public E remove(E value) {
        int position = indexOf(value);
        if (position == -1) {
            return null;
        }
        if (position == 0) {
            return remove(0);
        }

        return connectedPreviousToNextofCurrent(consecutiveNodes(position)).getValue();
    }

    /**
     * Remove a value from first element of list.
     *
     * @pre list is not empty
     * @post removes first value from list
     * 
     * @return The value actually removed.
     */
    public E removeFirst() {
        return remove(0);
    }

    /**
     * Remove last value from list.
     *
     * @pre list is not empty
     * @post removes last value from list
     * 
     * @return The value actually removed.
     */
    public E removeLast() {
        return remove(this.size() - 1);
    }

    /**
     * Set value stored at location i to object o, returning old value.
     *
     * @pre 0 <= i < size()
     * @post sets ith entry of list to value o;
     *       returns old value
     * @param i location of entry to be changed.
     * @param o new value
     * @return former value of ith entry of list.
     */
    public E set(int i, E o) {
        ListNode<E> node = this.nodeAt(i);
        E value = node.getValue();
        node.setValue(o);
        return value;
    }

    /**
     * Determine size of list.
     *
     * @post returns number of elements in list
     * 
     * @return The number of elements in list.
     */
    public int size() {
        return size;
    }

    /**
     * A function to reverse the nodes of a list.
     * 
     * @return the head of the reversed list.
     */

    public ListNode<E> reverse() {
        ListNode<E> prev = null;
        ListNode<E> finger = head;
        ListNode<E> next = finger.getNext();
        while (finger != null) {
            finger.setNext(prev);
            prev = finger;
            finger = next;
            if (next != null) {
                next = next.getNext();
            }
        }
        head = prev;
        return head;
    }

    /**
     * Returns a string representation of the list.
     *
     * @return a string representation of the list.
     */

    public String toString() {
        ListNode<E> finger = head;
        String s = "SinglyLinkedList [";
        while (finger != null) {
            s += finger.getValue();
            if (finger.getNext() != null) {
                s += ", ";
            }
            finger = finger.getNext();
        }
        return s + "]";
    }

    /**
     * an entry point function to the program
     * 
     * @param args a list containing the users arguments entered at run time
     */
    public static void main(String[] args) {
        SinglyLinkedList<String> slist = new SinglyLinkedList<>();
        System.out.println(slist.size());
        slist.addFirst("6767");
        System.out.println(slist.size());

    }

}
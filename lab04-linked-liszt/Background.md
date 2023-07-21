---
layout: page
title: "Background: Singly Linked Lists"
---

<style>
  strong {
    font-size: larger;
    font-variant: small-caps;
    font-weight: bold;
  }
  table {
    border: solid 1px grey;
    border-collapse: collapse;
    border-spacing: 0;
  }
  table thead th {
    background-color: grey;
    border: solid 1px grey;
    color: white;
    padding: 10px;
    text-align: left;
  }
  table tbody td {
    border: solid 1px grey;
    color: #333;
    padding: 10px;
    text-shadow: 1px 1px 1px #fff;
  }
  blockquote {
    margin-left: 2em;
    margin-right: 2em;
  }
  .red {
	color: red;
  }
  .blue {
	color: blue;
  }
  hr.style12 {
	height: 6px;
	background: url(../../images/hr-12.png) repeat-x 0 0;
    border: 0;
  }
  b {
    font-family: sans-serif;
	font-weight: 900;
  }
  .center {
	margin: auto;
	width: 100%;
	text-align: center;
  }
</style>

Let's start by defining a singly linked list. A _singly linked list_ (SLL) is either:

- empty, or
- a node, containing a value and a pointer to a linked list.

Because the definition of a SLL refers to itself in its own definition, it is a _recursive_ data structure.

It is sometimes easier to think about things graphically, so here is a way to visualize the above definition.
The empty case is represented by the symbol <img src="https://williams-cs.s3.amazonaws.com/linked-liszt/null.png" alt="null symbol" style="height: 1.5em;" />.
A node contains storage for a value and a pointer (to a list) and is represented by the symbol <img src="https://williams-cs.s3.amazonaws.com/linked-liszt/node.png" alt="node symbol" style="height: 1.5em;" />.

Hence, graphically, a singly linked list is either:

- <img src="https://williams-cs.s3.amazonaws.com/linked-liszt/null.png" alt="null symbol" style="height: 3em;" />, or
- <img src="https://williams-cs.s3.amazonaws.com/linked-liszt/node.png" alt="node symbol" style="height: 3em;" />

Here is an example, graphically, of a list that contains three elements:

<img src="https://williams-cs.s3.amazonaws.com/linked-liszt/list3.png" alt="a list with three linked nodes" style="height: 5em; display:block; margin-left:auto; margin-right:auto;" />

If we print the elements in this list from beginning to end, we should see:

```
1
2
3
```

Observe that a node always points at a _list_.
The following is not a valid list, because one of the pointers points at nothing:

<img src="https://williams-cs.s3.amazonaws.com/linked-liszt/invalid_list.png" alt="an invalid list" style="height: 5em; display:block; margin-left:auto; margin-right:auto;" />

<hr class="style12" />

## The parts of a list

The parts of a list have names: the _head_ and the _tail_.

Conventionally, the head of the list is the node at the start or "front" of the list.

<img src="https://williams-cs.s3.amazonaws.com/linked-liszt/listhead.png" alt="the head of the list" style="height: 7em; display:block; margin-left:auto; margin-right:auto;" />

The tail is the node at the end or "back" of the list.

<img src="https://williams-cs.s3.amazonaws.com/linked-liszt/listtail.png" alt="the tail of the list" style="height: 7em; display:block; margin-left:auto; margin-right:auto;" />

The nodes making up the list also have parts.
A node is an object that stores values of generic type `E`.
One field stores the _value_.
The other field stores a _reference to another list_.
In this class, a node is defined by the `ListNode<E>` class.

<img src="https://williams-cs.s3.amazonaws.com/linked-liszt/conscell.png" alt="the parts of a node" style="height: 4em; display:block; margin-left:auto; margin-right:auto;" />

Although we draw the fields here side-by-side, this is merely a convention.
The locations of the fields inside the object are ultimately determined by Java.

Perhaps the most important observation about the above structure is this: a singly linked list is a recursive data structure.
A `ListNode<E>`'s `next` field always points to a singly-linked list.
That singly linked list is a smaller subcomponent of the larger list.

<hr class="style12" />

## `SinglyLinkedList` wrapper class

Although a singly linked is is essentially just a chain of list nodes, it is difficult to use a list at this level of granularity.
Consequently, we usually encapsulate a singly linked list in a "wrapper class" so that users are not exposed to raw `ListNode<E>` objects.
The `SinglyLinkedList` class stores a reference to the `head` of the singly linked list and hides `ListNode<E>`s.
Instead, users manipulate the list by calling methods defined on the `SinglyLinkedList` object.

<img src="https://williams-cs.s3.amazonaws.com/linked-liszt/wrapper.png" alt="wrapper class" style="height: 6em; display:block; margin-left:auto; margin-right:auto;" />

<hr class="style12" />

## Fundamental list operations

As with many data structures, a `SinglyLinkedList` may have many methods.
We focus only on the essential methods here.

<hr class="style12" />

### Creating a list

It should be possible to construct an empty `SinglyLinkedList`. The constructor,

```
public SinglyLinkedList()
```

creates and returns an _empty list_.
In other words, the `SinglyLinkedList`'s `head` field is `null`.

<img src="https://williams-cs.s3.amazonaws.com/linked-liszt/empty_list.png" alt="an empty list" style="height: 5em; display:block; margin-left:auto; margin-right:auto;" />

<hr class="style12" />

### Inserting elements

Applications often need to insert elements into the list at one end or the other.
Let's consider the two cases.

The first method _prepends_ an element.
Prepending changes the `head` field of the wrapper class, and sets the `next` field of the new node to point at the old head of the list:

```
public void addFirst(E value)
```

So the list:

<img src="https://williams-cs.s3.amazonaws.com/linked-liszt/pre_prepend.png" alt="before prepend" style="height: 6.5em; display:block; margin-left:auto; margin-right:auto;" />

prepended with the element `3` becomes:

<img src="https://williams-cs.s3.amazonaws.com/linked-liszt/post_prepend.png" alt="after prepend" style="height: 12em; display:block; margin-left:auto; margin-right:auto;" />

The second method _appends_ an element.
Appending changes the `next` field of the last node:

```
public void addLast(E value)
```

So the list:

<img src="https://williams-cs.s3.amazonaws.com/linked-liszt/pre_prepend.png" alt="before append" style="height: 6.5em; display:block; margin-left:auto; margin-right:auto;"/>

appended with the element containing `3` becomes:

<img src="https://williams-cs.s3.amazonaws.com/linked-liszt/post_append.png" alt="after append" style="height: 9em; display:block; margin-left:auto; margin-right:auto;" />

Observe that the tail of a list is not directly accessible from the `head` field stored in the `SinglyLinkedList` class.
To access the tail, an implementation must first access the `ListNode<E>` stored in `head`, and then follow the `ListNode<E>`'s `next` reference until it finds the last `ListNode<E>`.
We call iterating over the components of a data structure a _traversal_.

More generally, we may want to add an element at an arbitrary position in the list.
Consider the following method,

```
public void add(int i, E o)
```

which lets us add an element of type `E` at position `i`.
Because this method can also add elements at the head or the tail of a list, it is probably worth your time to consider how you might use it to implement `addFirst` and `addLast`.

<hr class="style12" />

### Retrieving elements

The `getFirst` function returns the value of type `E` stored at the head of the list:

```
public E getFirst()
```

The `getLast` function returns the value of type `E` stored at the tail of the list:

```
public E getLast()
```

As with the `addLast` method, the `getLast` method must traverse the entire list, starting from the `head`, in order to access the `ListNode<E>` at the tail.

More generally, we may want to retrieve an element at an arbitrary position in the list.
Consider the following method,

```
public E get(int i)
```

which lets us retrieve an element of type `E` at position `i`.
Because this method can also retrieve elements at the head or the tail of a list, it is probably worth your time to consider how you might use it to implement `getFirst` and `getLast`.

<hr class="style12" />

## Asymptotic complexity

Newcomers to linked lists are often surprised that computer scientists overwhelmingly favor prepending data over appending data. Why? Prepending is _much_ faster.

<hr class="style12" />

### How many operations?

Consider how many traversal operations are performed when calling the prepend operation `addFirst`:

- Create a new `ListNode<E>` and store the value in it.
- Store the current `head` reference into the `next` field of that `ListNode<E>`.
- Replace the reference stored in `head` with a reference to the new `ListNode<E>`.

That's 3 steps any time `addFirst` is called.

By contrast, consider how many traversal operations are performed when calling the append operation `addLast`:

- Create a new `ListNode<E>` and store the value in it.
- Store the `head` reference into a temporary variable, `finger`.
- If the `next` field of the `ListNode<E>` pointed to by `finger` is not `null`, update `finger` to point to the next `ListNode<E>`. Repeat until the last `ListNode<E>` is located.
- Update the `next` field of the `ListNode<E>` pointed to by `finger` to point at the new `ListNode<E>` created in the first step.

That's many more steps than prepending, and the cost depends on the length of the list.

<hr class="style12" />

### Big-O Notation

We often compare the efficiency of algorithms using a measure called _asymptotic complexity_.
We will discuss asymptotic complexity in detail in class, and it is covered in detail in _Bailey_.
The key idea is to ask how the cost of running an algorithm, measured in terms of _steps_, changes as the size of the data structure changes.
We often use the variable, _n_, to denote the size of a data structure.
The asymptotic complexity measure answers the following question:
when a data structure contains _n_ elements, how many steps must it perform?

As you will see when you start to use this measure, we often make two important assumptions.
First, unless otherwise stated, we usually consider the number of steps in the "worst case."
Second, we ignore constant factors.

Above, we determined that `addFirst` requires three steps.
Since it requires only three steps, regardless of how many elements _n_ the list contains, we say that `addFirst` is _constant time_.
We denote constant time as _O(1)_, which we read as "order one" or "Big-Oh of one".

We also determined that `addLast` requires at least three steps (in the event the list is empty), but possibly many more depending on how many elements _n_ the list contains.
Since the dominant cost is determined by the length of the list, we say that `addLast` is _linear time_.
We denote linear time as _O(n)_, which we read as "order _n_" or "Big-Oh of _n_".

<hr class="style12" />

### `addFirst` wins: O(1) is asymptotically faster than O(n)

If we can accomplish something in _O(1)_ steps vs _O(n)_ steps, we will generally favor the _O(1)_ version unless there is a compelling reason not to.
You might notice that, when using `addFirst`, the elements of our list end up in the reverse order that we inserted them.
That is not usually a compelling reason to favor `addLast`.
Why?
Because we can reverse a list in _O(n)_.
While that doesn't sound like a big win when adding a single element, when adding _n_ elements, the savings are large.

Prepending and reversing a list of _n_ elements: _O(1) &times; n + O(n) = O(n)_

Appending a list of _n_ elements: _O(n) &times; n = O(n<sup>2</sup>)_

Another reason to favor prepending is that, when adding a new node, the original list is totally unchanged.
The change to the list is confined to the newly added `ListNode<E>` and the `SinglyLinkedList`'s `head` field.

<hr style="border-color: purple;" />

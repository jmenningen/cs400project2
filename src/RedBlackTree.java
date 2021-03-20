// --== CS400 File Header Information ==--
// Name: Xin Cai
// Email: xcai72@wisc.edu
// Team: DF
// TA: Dan
// Lecturer: Gary Dahl
// Notes to Grader: None

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Red-Black Tree implementation with a Node inner class for representing
 * the nodes of the tree. Currently, this implements a Binary Search Tree that
 * we will turn into a red black tree by modifying the insert functionality.
 * In this activity, we will start with implementing rotations for the binary
 * search tree insert algorithm. You can use this class' insert method to build
 * a regular binary search tree, and its toString method to display a level-order
 * traversal of the tree.
 */
public class RedBlackTree<T extends Comparable<T>> implements SortedCollectionInterface<T> {

  /**
   * This class represents a node holding a single value within a binary tree
   * the parent, left, and right child references are always maintained.
   */
  static class Node<T> {
    public T data;
    public Node<T> parent;  // null for root node
    public Node<T> leftChild; 
    public Node<T> rightChild; 
    public Node(T data) { this.data = data; }
    public boolean isBlack = false;
        
    /**
     * @return true when this node has a parent and is the left child of
     * that parent, otherwise return false
     */
    public boolean isLeftChild() {
      return parent != null && parent.leftChild == this;
    }
    
    /** return -1, if it is a left child; 1, right child; 0, root; */
    public int side() {
      if (parent == null) return 0;
      if (isLeftChild()) return -1;
      else return 1;
    }
    
    /** return a left child if param is -1, right child if param is 1 */
    public Node<T> getChild(int s) {
      if (s == -1) return leftChild;
      if (s ==  1) return rightChild;
      else 
        throw new IllegalArgumentException("error input");
    }
    
    /** flip node's color */
    public void flipColor() {
      isBlack = !isBlack;
    }
    
    /** bind child as its left child if side is -1, right child if side is 1 */
    public void bind(Node<T> child, int side) {
      switch (side) {
        case -1: this.leftChild  = child; break;
        case  1: this.rightChild = child; break;
        default: throw new IllegalArgumentException("error input");
      }
      if (child != null) child.parent = this;
    }
    
    /**
     * @return  0: if has 0 children
     *          2: if has 2 children
     *         -1: if has only left child
     *          1: if has only right child 
     */
    public int childStat() {
      if (leftChild == null)
        return (rightChild == null) ?  0 : 1;
      else
        return (rightChild == null) ? -1 : 2;
    }
    
    public int redNum() {
      int red = 0;
      if (leftChild  != null && !leftChild.isBlack ) red++;
      if (rightChild != null && !rightChild.isBlack) red++;
      return red;
    }
    
    /**
     * This method performs a level order traversal of the tree rooted
     * at the current node. The string representations of each data value
     * within this tree are assembled into a comma separated string within
     * brackets (similar to many implementations of java.util.Collection).
     * Note that the Node's implementation of toString generates a level
     * order traversal. The toString of the RedBlackTree class below
     * produces an inorder traversal of the nodes / values of the tree.
     * This method will be helpful as a helper for the debugging and testing
     * of your rotation implementation.
     * @return string containing the values of this tree in level order
     */
    @Override
    public String toString() {
      String output = "[";
      LinkedList<Node<T>> q = new LinkedList<>();
      q.add(this);
      while(!q.isEmpty()) {
        Node<T> next = q.removeFirst();
        if (next.leftChild != null)  q.add(next.leftChild);
        if (next.rightChild != null) q.add(next.rightChild);
        output += next.data.toString();
        if(!q.isEmpty()) output += ", ";
      }
      return output + "]";
    }
    
    /* for test
    public void showNexts() {
      System.out.println("parent: " + ((this.parent == null) ? "null" : parent.data.toString()));
      System.out.println("crrent: " + this.data.toString());
      System.out.println("  Lelf: " + ((leftChild == null) ? "null" : leftChild.data));
      System.out.println(" Right: " + ((rightChild == null) ? "null" : rightChild.data));
      System.out.println();
    }
    */
  }
  
  // RedBlackTree // ======================================================================== //
  
  protected Node<T> root = null; // reference to root node of tree, null when empty
  protected int size = 0; // the number of values in the tree
  private Node<T> cache = null;  // trace the contained node
  private Comparator<? super T> comptr = null;
  
  /** default constructor */
  public RedBlackTree() { 
    
  }
  
  /** constructor with defined comparator args */
  public RedBlackTree(Comparator<? super T> comptr) {
    this.comptr = comptr;
  }
  
  
  private void setRoot(Node<T> newNode) {
    root = newNode;
    newNode.parent = null;
    root.isBlack = true;
  }
  
  /**
   * Performs a naive insertion into a binary search tree: adding the input
   * data value to a new node in a leaf position within the tree. After  
   * this insertion, no attempt is made to restructure or balance the tree.
   * This tree will not hold null references, nor duplicate data values.
   * @param data to be added into this binary search tree
   * @return true if the value was inserted, false if not
   * @throws NullPointerException when the provided data argument is null
   */
  @Override
  public boolean insert(T data) throws NullPointerException, IllegalArgumentException {
    // null references cannot be stored within this tree
    if (data == null) throw new NullPointerException(
         "This RedBlackTree cannot store null references.");
    
    Node<T> newNode = new Node<>(data);
    
    if (root == null) { // add first node to an empty tree
      setRoot(newNode);
      size++;
      return true; 
    }
    else {        
      insertHelper(newNode, root);      // recursively insert into subtree
      enforceRBTreePropertiesAfterInsert(newNode);  // maintain properties
      size++;
      root.isBlack = true;
      return true;
    }
  }

  /** 
   * Recursive helper method to find the subtree with a null reference in the
   * position that the newNode should be inserted, and then extend this tree
   * by the newNode in that position.
   * @param newNode is the new node that is being added to this tree
   * @param subtree is the reference to a node within this tree which the 
   *      newNode should be inserted as a descenedent beneath
   * @return true is the value was inserted in subtree, false if not
   */
  private boolean insertHelper(Node<T> newNode, Node<T> subtree) {
    // left: -1, right: 1
    int side = (comptr == null)
        ?  Integer.signum(newNode.data.compareTo(subtree.data))        // comparable interface
        :  Integer.signum(comptr.compare(newNode.data, subtree.data)); // comparator interface
        
    if (side == 0) side = -1;  // duplicated element goes to left subtree
      
    if (subtree.getChild(side) == null) {  
      subtree.bind(newNode, side);  // subtree empty: insert
      return true;
    } else                          // otherwise, continue search
      return insertHelper(newNode, subtree.getChild(side));
  }
  
  /** recursive helper to maintain RBTree properties 
   * @param newNode the node that may cause double-red violation
   */
  private void enforceRBTreePropertiesAfterInsert(Node<T> newNode) {
    // if no violation, return
    if (newNode == root || newNode.parent.isBlack)
      return;
    
    int side = newNode.parent.side();                       // get the side of parent
    Node<T> uncle = newNode.parent.parent.getChild(-side);  // get the uncle node
    
    // case 1, 2: null/black uncle
    if (uncle == null || uncle.isBlack) {
      Node<T> temp = newNode;
      // operation 2: rotate two red nodes
      if (newNode.side() != newNode.parent.side()) {
        temp = newNode.parent;
        rotate(newNode, newNode.parent);
      }
      // operation 1: rotate and color swap parent with grandparent
      rotate(temp.parent, temp.parent.parent); 
    }
    else {          
      // case 3: red uncle
      // operation 3: flip color of parent, uncle and grandparent,
      //              then, check grandparent
      uncle.flipColor();
      newNode.parent.flipColor();
      newNode.parent.parent.flipColor();
      enforceRBTreePropertiesAfterInsert(newNode.parent.parent);
    }
  }
  
  /**
   * Performs the rotation operation on the provided nodes within this tree.
   *  - Right rotation: When the provided child is a  leftChild of the provided parent
   *  - Left  rotation: When the provided child is a rightChild of the provided parent
   *  - IllegalArgumentException: when neither of the above cases
   *  <p>
   * @param child is the node being rotated from child to parent position
   *      (between these two node arguments)
   * @param parent is the node being rotated from parent to child position
   *      (between these two node arguments)
   * @throws IllegalArgumentException when the provided child and parent
   *      node references are not initially (pre-rotation) related that way
   */
  private void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {
    // check parent-child relation
    int side = 0;
    if (parent.leftChild == child) side = -1;
    else if (parent.rightChild == child) side = 1;
    
    // if neither left child nor right child
    if (side == 0)
      throw new IllegalArgumentException("Error: illegal argument");
    
    colorSwap(child, parent);
    
    Node<T> temp = child.getChild(-side);  // the opposite-side's child
    if (parent == root) 
      setRoot(child);
    else 
      parent.parent.bind(child, parent.side());  // grandparent -> child
    
    child.bind(parent, -side);   // child -> parent
    parent.bind(temp, side);     // parent -> temp
  }

  
  private void colorSwap(Node<T> a, Node<T> b) {
    boolean temp = a.isBlack;
    a.isBlack = b.isBlack;
    b.isBlack = temp;
  }
    
  /** remove */
  public boolean remove(T data) {
    // if NOT contains -> false
    if (!contains(data)) return false;
    
    Node<T> rmd = cache;       // get the node to be removed
    int side = rmd.side();  // get its side
    
    /* case: 0 children ---------------------------------------- */
    if (rmd.childStat() == 0) {
      
      if (rmd == root) { 
        clear(); 
        return true;
      }
      
      rmd.parent.bind(null, rmd.side());
      if (rmd.isBlack) {
        fixDoubleBlack(rmd.parent, side);
      }
      size--;
      return true;
    }
    
    /* case: 2 children ---------------------------------------- */
    else if (rmd.childStat() == 2) {
      // replace Pred/Succ's data, and remove Pred/Succ node
      T backup = getNext(rmd, -1).data;
      remove(backup);
      rmd.data = backup;
      return true;
    } 
    
    /* case: 1 child ------------------------------------------- */
    else {
      Node<T> child = rmd.getChild(rmd.childStat());
      if (rmd == root) {
        setRoot(child);
      } else {
        rmd.parent.bind(child, rmd.side());
        child.isBlack = true;
      }
      size--;
      return true;
    }
  }
  
  /**
   * This method fixes the double-black violation.
   * The reason of passing the parent of dbb-node, rathar
   * than pass the dbb-node itself, is that dbb may be null.
   * <p>
   * @param parent double-black node's parent
   * @param side   double-black node's side
   */
  private void fixDoubleBlack(Node<T> parent, int side) {
    if (parent == null) return;  // dbb is root 
    
    Node<T> sib = parent.getChild(-side);  // get sibling
    
    if (sib.isBlack) {
      // case 2: NO Red children
      if (sib.redNum() == 0) {
        sib.flipColor();
        if (!parent.isBlack) 
          parent.flipColor();   
        else
          fixDoubleBlack(parent.parent, parent.side());
      }
      // case 1: has Red children
      else {
        // if sibling's same-side child is NOT RED
        if (sib.getChild(-side) == null || 
            sib.getChild(-side).isBlack) {
          rotate(sib.getChild(side), sib);
          fixDoubleBlack(parent, side);
        }
        else {
          // sibling's same-side child must be RED now
          sib.getChild(-side).isBlack = true;
          rotate(sib, sib.parent);
        }
      }
    } 
    // case 3: sibling.isRED
    else {
      rotate(sib, parent);
      fixDoubleBlack(parent, side);
    }
  }
  
  /** side = -1, find PRED.; side = 1, find SUCC. */
  private Node<T> getNext(Node<T> curr, int side) {
    if (curr.getChild(side) == null)
      return null;
    
    Node<T> pred = curr.getChild(side);
    while (pred.getChild(-side) != null)
      pred = pred.getChild(-side);
    
    return pred;
  }
  
  /** return an ArrayList of T of all objects in this tree */
  public ArrayList<T> toArrayList() {
    ArrayList<T> list = new ArrayList<>(size);
    toListHelper(list, root);
    return list;
  }
  
  private void toListHelper(ArrayList<T> list, Node<T> curr) {
    if (curr == null) return;
    
    if (curr.leftChild != null)
      toListHelper(list, curr.leftChild);
    
    list.add(curr.data);
    
    if (curr.rightChild != null)
      toListHelper(list, curr.rightChild);
  }
  
  
  /**
   * @param min - minimum value (inclusive) of searching range
   * @param max - maximum value (inclusive) of searching range
   * @return an ArrayList of elements within range
   */
  public ArrayList<T> subSet(T min, T max) {
    ArrayList<T> list = new ArrayList<>();
    helper(root, list, min, max);
    return list;
  }
  
  /**
   * helper method of subSet()
   * <p>
   * @param curr - current node to check
   * @param list - an ArrayList storing all elements within range
   * @param min - minimum value (inclusive) of searching range
   * @param max - maximum value (inclusive) of searching range
   */
  private void helper(Node<T> curr, ArrayList<T> list, T min, T max) {
    if (curr == null) return;
    
    int cmpMin, cmpMax;
    if (comptr == null) {
      cmpMin = curr.data.compareTo(min);
      cmpMax = curr.data.compareTo(max);
    } else {
      cmpMin = comptr.compare(curr.data, min);
      cmpMax = comptr.compare(curr.data, max);
    }
    
    if (cmpMin < 0) 
      helper(curr.rightChild, list, min, max);
    else if (cmpMax > 0) 
      helper(curr.leftChild, list, min, max);
    else {
      helper(curr.leftChild, list, min, max);
      list.add(curr.data);
      helper(curr.rightChild, list, min, max);
    }
  }
  
  
  /**
   * Get the size of the tree (its number of nodes).
   * @return the number of nodes in the tree
   */
  @Override
  public int size() {
    return size;
  }

  /**
   * Method to check if the tree is empty (does not contain any node).
   * @return true of this.size() return 0, false if this.size() > 0
   */
  @Override
  public boolean isEmpty() {
    return this.size() == 0;
  }

  /**
   * Checks whether the tree contains the value *data*.
   * @param data the data value to test for
   * @return true if *data* is in the tree, false if it is not in the tree
   */
  @Override
  public boolean contains(T data) {
    // null references will not be stored within this tree
    if (data == null) throw new NullPointerException(
        "This RedBlackTree cannot store null references.");
    return this.containsHelper(data, root);
  }

  /**
   * Recursive helper method that recurses through the tree and looks
   * for the value *data*.
   * @param data the data value to look for
   * @param subtree the subtree to search through
   * @return true of the value is in the subtree, false if not
   */
  private boolean containsHelper(T data, Node<T> subtree) {
    if (subtree == null) {
      // we are at a null child, value is not in tree
      return false;
    } else {
      int compare = (comptr == null) ? data.compareTo(subtree.data)
                                     : comptr.compare(data, subtree.data);
      if (compare < 0) {
        // go left in the tree
        return containsHelper(data, subtree.leftChild);
      } else if (compare > 0) {
        // go right in the tree
        return containsHelper(data, subtree.rightChild);
      } else {
        // we found it :)
        cache = subtree;
        return true;
      }
    }
  }

  public void clear() {
    size = 0;
    root = null;
  }
    
  /**
   * Returns an iterator over the values in in-order (sorted) order.
   * @return iterator object that traverses the tree in in-order sequence
   */
  @Override
  public Iterator<T> iterator() {
    // use an anonymous class here that implements the Iterator interface
    // we create a new on-off object of this class everytime the iterator
    // method is called
    return new Iterator<T>() {
      // a stack and current reference store the progress of the traversal
      // so that we can return one value at a time with the Iterator
      Stack<Node<T>> stack = null;
      Node<T> current = root;

      /**
       * The next method is called for each value in the traversal sequence.
       * It returns one value at a time.
       * @return next value in the sequence of the traversal
       * @throws NoSuchElementException if there is no more elements in the sequence
       */
       public T next() {
         // if stack == null, we need to initialize the stack and current element
         if (stack == null) {
           stack = new Stack<Node<T>>();
           current = root;
         }
         // go left as far as possible in the sub tree we are in until we hit a null
         // leaf (current is null), pushing all the nodes we fund on our way onto the
         // stack to process later
         while (current != null) {
           stack.push(current);
           current = current.leftChild;
         }
         // as long as the stack is not empty, we haven't finished the traversal yet;
         // take the next element from the stack and return it, then start to step down
         // its right subtree (set its right sub tree to current)
         if (!stack.isEmpty()) {
           Node<T> processedNode = stack.pop();
           current = processedNode.rightChild;
           return processedNode.data;
        } else {
           // if the stack is empty, we are done with our traversal
           throw new NoSuchElementException("There are no more elements in the tree");
        }
       }

      /**
       * Returns a boolean that indicates if the iterator has more elements (true),
       * or if the traversal has finished (false)
       * @return boolean indicating whether there are more elements / steps for the traversal
       */
      public boolean hasNext() {
        // return true if we either still have a current reference, or the stack
        // is not empty yet
        return !(current == null && (stack == null || stack.isEmpty()) );
      }  
    };
  }

  /**
   * This method performs an inorder traversal of the tree. The string 
   * representations of each data value within this tree are assembled into a
   * comma separated string within brackets (similar to many implementations 
   * of java.util.Collection, like java.util.ArrayList, LinkedList, etc).
   * Note that this RedBlackTree class implementation of toString generates an
   * inorder traversal. The toString of the Node class class above
   * produces a level order traversal of the nodes / values of the tree.
   * @return string containing the ordered values of this tree (in-order traversal)
   */
  @Override
  public String toString() {
    // use the inorder Iterator that we get by calling the iterator method above
    // to generate a string of all values of the tree in (ordered) in-order
    // traversal sequence
    Iterator<T> treeNodeIterator = this.iterator();
    StringBuffer sb = new StringBuffer();
    sb.append("[ ");
    if (treeNodeIterator.hasNext())
      sb.append(treeNodeIterator.next());
    while (treeNodeIterator.hasNext()) {
      T data = treeNodeIterator.next();
      sb.append(", ");
      sb.append(data.toString());
    }
    sb.append(" ]");
    return sb.toString();
  }

  
// ------------ [ Extra Methods for Testing ] ------------ //
  //*
  public void showTree() {
    if (isEmpty()) {
      System.out.println("\nTree:<empty>");
      return;
    }
    LinkedList<String> list = levelSearch();
      
    int h = height();
    int w = (1 << h) - 1;
    String[][] table = new String[h][w];
    String sep = "   ";
      
    for (int r = 0; r < h; ++r) {
        
      int a = (1 << h - r - 1) - 1;
      int b = (1 << h - r    ) - 1;
        
      for (int c = 0; c < w; ++c) {
        if (c == a && !list.isEmpty()) {
          table[r][c] = list.removeFirst();
          a += b + 1;
          continue;
        }
        table[r][c] = sep;
      }
    }
      
    System.out.println("\nTree(" + size() + ") root = " + root.data.toString());
    for (int r = 0; r < h; ++r) {
      System.out.print("[ ");
      for (int c = 0; c < w; ++c) {
        System.out.print(table[r][c]);
      }
      System.out.println(" ]");
    }
    System.out.println();
  }
  
  private LinkedList<String> levelSearch() {
    LinkedList<String> list = new LinkedList<>();
      
    LinkedList<Node<T>> queue = new LinkedList<>();
    queue.addLast(root);
      
    int full = (1 << height()) - 1;
    while(list.size() < full) {
      Node<T> curr = queue.pollFirst();
      String color = (curr != null && curr.isBlack) ? "B" : "r";
      list.add(curr == null ? "---" : curr.data.toString() + color);
      if (curr != null) {
        queue.addLast(curr.leftChild);
        queue.addLast(curr.rightChild);
      }
      else {
        queue.add(null);
        queue.add(null);
      }
    }
    return list;
  }
  
  public int height() {
    return height(root);
  }
    
  private int height(Node<T> curr) {
    if (curr == null) return 0;
    return 1 + Math.max(height(curr.leftChild), height(curr.rightChild));
  }
//*/

}




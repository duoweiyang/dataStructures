import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Your implementation of a binary search tree.
 *
 * @author Duo-Wei Yang
 * @userid dyang305
 * @GTID 903213022
 * @version 1.0
 */
public class BST<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private BSTNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the BST with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular
     * for loop will not work here. What other type of loop would work?
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) {
        if (data == null || data.contains(null)) {
            throw new IllegalArgumentException("The collection of data"
                    + "is null, which is invalid.");
        } else {
            for (T item : data) {
                if (item == null) {
                    throw new IllegalArgumentException("The data element "
                            + "is null, which is invalid.");
                }
                add(item);
            }
        }
    }

    /**
     * Helper method that adds data to a BST.
     *
     *
     * @param data the data to add to the tree
     * @param node the current node
     * @return the current node being traversed or the newest addition
     */
    private BSTNode<T> add(T data, BSTNode<T> node) {
        if (node == null) {
            BSTNode<T> newNode = new BSTNode<T>(data);
            size += 1;
            return newNode;
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(add(data, node.getLeft()));
            return node;
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(add(data, node.getRight()));
            return node;
        }
        return node;
    }

    /**
     * Helper method that removes data from a BST.
     *
     *
     * @param data the data to remove from the tree
     * @param node the current node
     * @param dummy2 the variable that saves the removed data
     * @return the data that was removed
     */
    private BSTNode<T> remove(T data, BSTNode<T> node, BSTNode<T> dummy2) {
        BSTNode<T> dummy = new BSTNode<T>(null);
        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(remove(data, node.getLeft(), dummy2));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(remove(data, node.getRight(), dummy2));
        } else {
            size -= 1;
            dummy2.setData(node.getData());
            if (node.getLeft() == null && node.getRight() == null) {
                return null;
            } else if (node.getLeft() == null) {
                return node.getRight();
            } else if (node.getRight() == null) {
                return node.getLeft();
            } else {
                node.setRight(findMinOnRight(node.getRight(), dummy));
                node.setData(dummy.getData());
            }
        }
        return node;
    }

    /**
     * Helper method that finds the successor node, which is the
     * node in the right subtree that has the smallest value.
     *
     *
     * @param node the current node
     * @param dummy the variable where we save the removed data
     * @return the successor node
     */
    private BSTNode<T> findMinOnRight(BSTNode<T> node, BSTNode<T> dummy) {
        if (node.getLeft() == null) {
            dummy.setData(node.getData());
            return node.getRight();
        }
        node.setLeft(findMinOnRight(node.getLeft(), dummy));
        return node;
    }

    /**
     * Helper method that returns a arraylist with elements through
     * preorder traversal. First, we check if the current node
     * is null. If it is, it means we're all the way in the leaf.
     * Otherwise, we look at the data, recurse left, then recurse
     * right.
     *
     * @param root the first node we start the traversal from
     * @return the arraylist that has elements in order according to
     * the preorder attributes.
     */
    private List<T> preorder(BSTNode<T> root) {
        if (root == null) {
            return new ArrayList<T>();
        }

        ArrayList<T> preOrderList = new ArrayList<T>();
        preOrderList.add(root.getData());
        preOrderList.addAll(preorder(root.getLeft()));
        preOrderList.addAll(preorder(root.getRight()));

        return preOrderList;
    }

    /**
     * Helper method that returns a list with elements through
     * postorder traversal. First, we check if the current node
     * is null. If it is, it means we're all the way in the leaf.
     * Otherwise, we recurse left and right then look at the data.
     *
     *
     * @param root the first node we start the traversal from
     * @return the list that has elements in order according to
     * the postorder attributes.
     */
    private List<T> postorder(BSTNode<T> root) {
        if (root == null) {
            return new ArrayList<T>();
        }

        List<T> postOrderList = postorder(root.getLeft());
        postOrderList.addAll(postorder(root.getRight()));
        postOrderList.add(root.getData());

        return postOrderList;
    }

    /**
     * Helper method that gets an arraylist with elements through
     * inorder traversal. First, we check if the current node
     * is null. If it is, it means we're all the way in the leaf.
     * Otherwise, we recurse left, look at the data, then recurse right.
     *
     * @param root the first node we start the traversal from
     * @return the arraylist that has elements in order according to
     * the inorder attributes.
     */
    private List<T> inorder(BSTNode<T> root) {
        if (root == null) {
            return new ArrayList<T>();
        }

        List<T> inOrderList = inorder(root.getLeft());
        inOrderList.add(root.getData());
        inOrderList.addAll(inorder(root.getRight()));

        return inOrderList;
    }

    /**
     * Helper method that finds the k largest numbers
     * and puts them in a list.
     *
     * @param node the first node we start the traversal from
     * @param count the number of elements we need to find
     * @param list the list we created in the public method
     * @return the linked list that has the k largest elements
     */
    private List<T> kLargest(List<T> list, BSTNode<T> node, int count) {
        if (count == 0) {
            return list;
        }
        if (node == null) {
            return list;
        } else {
            if (list.size() < count) {
                kLargest(list, node.getRight(), count);
            }
            if (list.size() < count) {
                list.add(0, node.getData());
            }
            if (list.size() < count) {
                kLargest(list, node.getLeft(), count);
            }
        }
        return list;
    }

    /**
     * Helper method that finds the height of a BST
     *
     * @param root the first node we start the traversal from
     * @return the height of the BST
     */
    private int height(BSTNode<T> root) {
        if (root == null) {
            return -1;
        }
        return (Math.max(height(root.getLeft()), height(root.getRight())) + 1);
    }

    /**
     * Add the data as a leaf in the BST. Should traverse the tree to find the
     * appropriate location. If the data is already in the tree, then nothing
     * should be done (the duplicate shouldn't get added, and size should not be
     * incremented).
     * 
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null can't be added"
                    + "to the tree.");
        }
        root = add(data, root);
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data.
     * You must use recursion to find and remove the successor (you will likely
     * need an additional helper method to handle this case efficiently).
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null can't be removed"
                    + "from the tree.");
        }
        if (data == null) {
            throw new NoSuchElementException("A node that contains"
                    + "the data couldn't be found.");
        }
        BSTNode<T> result = new BSTNode<T>(null);
        root = remove(data, root, result);
        return result.getData();
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        BSTNode<T> node = root;
        if (data == null) {
            throw new IllegalArgumentException("Null isn't a valid value"
                    + "you can get from a BST.");
        }

        while (node != null) {
            BSTNode<T> leaf;

            if (node.getData().compareTo(data) < 0) {
                leaf = node.getRight();
            } else {
                leaf = node.getLeft();
            }

            if (node.getData().equals(data)) {
                return node.getData();
            } else if (leaf == null) {
                throw new NoSuchElementException("Node containing the data "
                        + "couldn't be found.");
            } else {
                node = leaf;
            }
        }
        throw new NoSuchElementException("Node containing the data"
                + "couldn't be found.");
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null isn't a valid value"
                    + "you can find in a BST.");
        }
        try {
            get(data);
        } catch (NoSuchElementException exception) {
            return false;
        }
        return true;
    }

    /**
     * Should run in O(n).
     *
     * @return a preorder traversal of the tree
     */
    public List<T> preorder() {
        return preorder(root);
    }

    /**
     * Should run in O(n).
     *
     * @return an inorder traversal of the tree
     */
    public List<T> inorder() {
        return inorder(root);
    }

    /**
     * Should run in O(n).
     *
     * @return a postorder traversal of the tree
     */
    public List<T> postorder() {
        return postorder(root);
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * To do this, add the root node to a queue. Then, while the queue isn't
     * empty, remove one node, add its data to the list being returned, and add
     * its left and right child nodes to the queue. If what you just removed is
     * {@code null}, ignore it and continue with the rest of the nodes.
     *
     * Should run in O(n).
     *
     * @return a level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> levelOrderList = new ArrayList<T>();
        Queue<BSTNode<T>> queueList = new LinkedList<BSTNode<T>>();

        if (size == 0) {
            return levelOrderList;
        }

        queueList.add(root);

        while (!queueList.isEmpty()) {
            BSTNode<T> current = queueList.poll();
            levelOrderList.add(current.getData());

            if (current.getLeft() != null) {
                queueList.add(current.getLeft());
            }
            if (current.getRight() != null) {
                queueList.add(current.getRight());
            }
        }
        return levelOrderList;
    }

    /**
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in the efficiency penalty.
     *
     * EXAMPLE: Given the BST below composed of Integers:
     *
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     *
     * kLargest(5) should return the list [25, 37, 40, 50, 75].
     * kLargest(3) should return the list [40, 50, 75].
     *
     * Should have a running time of O(log(n) + k) for a balanced tree and a
     * worst case of O(n + k).
     *
     * @throws java.lang.IllegalArgumentException if k > n, the number of data
     * in the BST
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     */
    public List<T> kLargest(int k) {
        BSTNode<T> node = root;
        List<T> largeList = new LinkedList<T>();
        if (k > size) {
            throw new IllegalArgumentException("Number of largest elements"
                    + "to return exceeds the BST's size.");
        }
        return kLargest(largeList, root, k);
    }

    /**
     * Clears the tree.
     *
     * Should run in O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Calculate and return the height of the root of the tree. A node's
     * height is defined as {@code max(left.height, right.height) + 1}. A leaf
     * node has a height of 0 and a null child should be -1.
     *
     * Should be calculated in O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return height(root);
    }

    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the number of elements in the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}

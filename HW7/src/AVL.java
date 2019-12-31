import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Duo-Wei Yang
 * @userid dyang305
 * @GTID 903213022
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular
     * for loop will not work here. What other type of loop would work?
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
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
     * Add the data to the AVL. Start by adding it as a leaf and rotate the tree
     * as needed. Should traverse the tree to find the appropriate location.
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The collection of data"
                    + "is null, which is invalid.");
        }
        root = add(data, root);
    }

    /**
     * Helper method that adds data to an AVL tree
     *
     * @param data the data we want to add
     * @param node the current node
     * @return the height of the node
     */
    private AVLNode<T> add(T data, AVLNode<T> node) {
        if (node == null) {
            node = new AVLNode<T>(data);
            size++;
        }
        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(add(data, node.getLeft()));
            node.setBalanceFactor(getHeight(node.getLeft())
                - getHeight(node.getRight()));
            node = balance(node);
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(add(data, node.getRight()));
            node.setBalanceFactor(getHeight(node.getLeft())
                    - getHeight(node.getRight()));
            node = balance(node);
        }
        node.setHeight(Math.max(getHeight(node.getRight()),
                getHeight(node.getLeft())) + 1);
        node.setBalanceFactor(getHeight(node.getLeft())
                - getHeight(node.getRight()));
        return node;
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the predecessor to replace the data,
     * not the successor.
     * You must use recursion to find and remove the predecessor (you will
     * likely need an additional helper method to handle this case efficiently).
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
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
        AVLNode<T> dummy = new AVLNode<T>(null);
        root = remove(root, data, dummy);
        return dummy.getData();
    }

    /**
     * Helper method that removes data from an AVL tree
     *
     * @param data the data to remove from the tree
     * @param node the current node
     * @param dummy2 the variable that saves the removed data
     * @return the data that was removed
     */
    private AVLNode<T> remove(AVLNode<T> node, T data, AVLNode<T> dummy2) {
        AVLNode<T> dummy = new AVLNode<T>(null);
        if (node == null) {
            throw new java.util.NoSuchElementException("Data can't be found.");
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(remove(node.getLeft(), data, dummy2));
            node.setHeight(Math.max(getHeight(node.getRight()),
                    getHeight(node.getLeft())) + 1);
            node.setBalanceFactor(getHeight(node.getLeft())
                    - getHeight(node.getRight()));
            node = balance(node);

        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(remove(node.getRight(), data, dummy2));
            node.setHeight(Math.max(getHeight(node.getRight()),
                    getHeight(node.getLeft())) + 1);
            node.setBalanceFactor(getHeight(node.getLeft())
                    - getHeight(node.getRight()));
            node = balance(node);
        } else {
            if (node.getLeft() == null) {
                size--;
                dummy2.setData(node.getData());
                node.setBalanceFactor(getHeight(node.getLeft())
                        - getHeight(node.getRight()));

                return node.getRight();
            } else if (node.getRight() == null) {
                size--;
                dummy2.setData(node.getData());
                node.setBalanceFactor(getHeight(node.getLeft())
                        - getHeight(node.getRight()));

                return node.getLeft();
            } else {
                size--;
                dummy2.setData(node.getData());
                node.setLeft(findMinOnLeft(node.getLeft(), dummy));
                node.setData(dummy.getData());
                node.setHeight(Math.max(getHeight(node.getRight()),
                        getHeight(node.getLeft())) + 1);
                node.setBalanceFactor(getHeight(node.getLeft())
                        - getHeight(node.getRight()));
                node = balance(node);
            }
        }
        node.setBalanceFactor(getHeight(node.getLeft())
                - getHeight(node.getRight()));

        return node;
    }


    /**
     * Helper method that helps us find predecessor
     *
     * @param node is the root we search from
     * @param dummy the variable where we save the removed data
     * @return the predecessor
     */
    private AVLNode<T> findMinOnLeft(AVLNode<T> node, AVLNode<T> dummy) {
        if (node.getRight() == null) {
            dummy.setData(node.getData());
            return node.getLeft();
        }
        node.setRight(findMinOnLeft(node.getRight(), dummy));
        node.setHeight(Math.max(getHeight(node.getRight()),
                getHeight(node.getLeft())) + 1);
        node.setBalanceFactor(getHeight(node.getLeft())
                - getHeight(node.getRight()));
        node = balance(node);
        return node;
    }

    /**
     * Helper method that balances the tree when balance factor
     * exceeds 1 or is less than -1.
     *
     * @param node is the node to rotate
     * @return the rotated node
     */
    private AVLNode<T> balance(AVLNode<T> node) {
        if (node.getBalanceFactor() > 1) {
            if (node.getLeft() != null
                    && node.getLeft().getBalanceFactor() >= 0) {

                node = rightRotation(node);
            } else {
                node = leftrightRotation(node);
            }
        } else if (node.getBalanceFactor() < -1) {
            if (node.getRight() != null
                    && node.getRight().getBalanceFactor() <= 0) {
                node = leftRotation(node);
            } else {
                node = rightleftRotation(node);
            }
        }
        return node;
    }

    /**
     * Helper method that right rotates
     *
     * @param node is the node to rotate
     * @return the rotated node
     */
    private AVLNode<T> rightRotation(AVLNode<T> node) {
        AVLNode<T> temp = node.getLeft();
        node.setLeft(temp.getRight());
        temp.setRight(node);

        node.setHeight(Math.max(getHeight(node.getRight()),
                getHeight(node.getLeft())) + 1);
        temp.setHeight(Math.max(getHeight(temp.getRight()),
                getHeight(temp.getLeft())) + 1);
        node.setBalanceFactor(getHeight(node.getLeft())
                - getHeight(node.getRight()));
        temp.setBalanceFactor(getHeight(temp.getLeft())
                - getHeight(temp.getRight()));

        return temp;

    }

    /**
     * Helper method that left rotates
     *
     * @param node is the node to rotate
     * @return the rotated node
     */
    private AVLNode<T> leftRotation(AVLNode<T> node) {
        AVLNode<T> temp = node.getRight();
        node.setRight(temp.getLeft());
        temp.setLeft(node);

        node.setHeight(Math.max(getHeight(node.getRight()),
                getHeight(node.getLeft())) + 1);
        temp.setHeight(Math.max(getHeight(temp.getRight()),
                getHeight(temp.getLeft())) + 1);
        node.setBalanceFactor(getHeight(node.getLeft())
                - getHeight(node.getRight()));
        temp.setBalanceFactor(getHeight(temp.getLeft())
                - getHeight(temp.getRight()));

        return temp;
    }


    /**
     * Helper method for double rotation for a right-left rotation
     *
     * @param node the node we want to rotate
     * @return the rotated node
     */
    private AVLNode<T> rightleftRotation(AVLNode<T> node) {
        node.setRight(rightRotation(node.getRight()));
        return leftRotation(node);
    }


    /**
     * Helper method for double rotation for a left-right rotation
     *
     * @param node the node we want to rotate
     * @return the rotated node
     */
    private AVLNode<T> leftrightRotation(AVLNode<T> node) {
        node.setLeft(leftRotation(node.getLeft()));
        return rightRotation(node);
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null, "
                    + "which is invalid.");
        }
        return get(data, root);
    }

    /**
     * Helper method to get data from AVL tree.
     *
     * @param data the data we want to find
     * @param node the current node
     * @return the data of the node we want
     */
    private T get(T data, AVLNode<T> node) {
        if (node == null) {
            throw new NoSuchElementException("Null is an invalid node to get.");
        }
        if (data.compareTo(node.getData()) == 0) {
            return node.getData();
        } else if (data.compareTo(node.getData()) < 0) {
            return get(data, node.getLeft());
        } else if (data.compareTo(node.getData()) > 0) {
            return get(data, node.getRight());
        }
        throw new NoSuchElementException("Data couldn't be found in AVL tree.");
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null, "
                    + "which is invalid.");
        }
        return contains(data, root);
    }

    /**
     * Helper method to find out if data is in AVL tree.
     *
     * @param data the data we want to add
     * @param node the current node
     * @return the height of the node
     */
    private boolean contains(T data, AVLNode<T> node) {
        if (node == null) {
            return false;
        }
        if (data.compareTo(node.getData()) == 0) {
            return true;
        } else if (data.compareTo(node.getData()) < 0) {
            return contains(data, node.getLeft());
        } else {
            return contains(data, node.getRight());
        }
    }


    /**
     * Returns the data in the deepest node. If there are more than one node
     * with the same deepest depth, return the right most node with the
     * deepest depth.
     *
     * Must run in O(log n) for all cases
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      3
     *        \
     *         1
     * Max Deepest Node:
     * 1 because it is the deepest node
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      4
     *        \    /
     *         1  3
     * Max Deepest Node:
     * 3 because it is the maximum deepest node (1 has the same depth but 3 > 1)
     *
     * @return the data in the maximum deepest node or null if the tree is empty
     */
    public T maxDeepestNode() {
        if (size == 0) {
            return null;
        }
        return maxDeepestNode(root);
    }

    /**
     * Helper method that returns the max deepest node in the AVL tree
     *
     * @param node current node we're on
     * @return the data of the deepest node or the bigger number between
     * several nodes with same max depth of the AVL tree.
     */
    private T maxDeepestNode(AVLNode<T> node) {
        if (node.getLeft() == null && node.getRight() == null) {
            return node.getData();
        }
        // If BF is less than 0, go left. If BF is 0 or right heavy, go right
        // We got right when it's balanced because we're looking for the max.
        if (node.getBalanceFactor() > 0) {
            return maxDeepestNode(node.getLeft());
        } else {
            return maxDeepestNode(node.getRight());
        }
    }

    /**
     * Returns the data of the deepest common ancestor between two nodes with
     * the given data. The deepest common ancestor is the lowest node (i.e.
     * deepest) node that has both data1 and data2 as descendants.
     * If the data are the same, the deepest common ancestor is simply the node
     * that contains the data. You may not assume data1 < data2.
     * (think carefully: should you use value equality or reference equality?).
     *
     * Must run in O(log n) for all cases
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      3
     *        \
     *         1
     * deepestCommonAncestor(3, 1): 2
     *
     * Example
     * Tree:
     *           3
     *        /    \
     *       1      4
     *      / \
     *     0   2
     * deepestCommonAncestor(0, 2): 1
     *
     * @param data1 the first data
     * @param data2 the second data
     * @throws java.lang.IllegalArgumentException if one or more of the data
     *          are null
     * @throws java.util.NoSuchElementException if one or more of the data are
     *          not in the tree
     * @return the data of the deepest common ancestor
     */
    public T deepestCommonAncestor(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("One or more of the data"
                    + "is null, which is invalid.");
        }
        if (data1.compareTo(data2) > 0) {
            return deepestCommonAncestor(data2, data1, root);
        } else {
            return deepestCommonAncestor(data1, data2, root);
        }
    }

    /**
     * Helper method that returns ancestor of two data inputs.
     *
     * @param data1 the first data (smaller input)
     * @param data2 the second data (larger input)
     * @param node the current node we're working with
     * @throws java.lang.IllegalArgumentException if one or more of the data
     *         are null
     * @throws java.util.NoSuchElementException if one or more of the data are
     *         not in the tree
     * @return the data of the deepest common ancestor
     */
    private T deepestCommonAncestor(T data1, T data2, AVLNode<T> node) {
        /* If the current node is null, we've reached the end and one of the
        data wasn't found.
        */
        if (node == null) {
            throw new NoSuchElementException("Data doesn't exist in AVL tree.");
        }
        if (data1.compareTo(node.getData()) < 0
                && data2.compareTo(node.getData()) < 0) {
            return deepestCommonAncestor(data1, data2, node.getLeft());
        }
        if (data1.compareTo(node.getData()) > 0
                && data2.compareTo(node.getData()) > 0) {
            return deepestCommonAncestor(data1, data2, node.getRight());
        }
        // Common ancestor may have been found
        if (data1.compareTo(node.getData()) < 0
                && data2.compareTo(node.getData()) > 0) {
            if (contains(data1, node.getLeft())) {
                if (contains(data2, node.getRight())) {
                    return node.getData();
                }
            }
            /* If the subtree doesn't contain the other data,
            we don't have an ancestor for two numbers we can return.
            */
            throw new NoSuchElementException("Data doesn't exist "
                        + "in AVL tree.");
        }
        /* If data1 equals current node's data and data2 exists on the right
        somewhere, then the common ancestor is data1
        */
        if (data1.equals(node.getData())) {
            if (contains(data2, node.getRight())) {
                return node.getData();
            } else if (data2.equals(node.getData())) {
                return node.getData();
            } else {
                throw new NoSuchElementException("Data doesn't exist "
                        + "in AVL tree.");
            }
        }
        /* If data2 equals current node's data and data1 exists on the right
        somewhere, then the common ancestor is data2
        */
        if (data2.equals(node.getData())) {
            if (contains(data1, node.getLeft())) {
                return data2;
            } else if (data1.equals(node.getData())) {
                return node.getData();
            } else {
                throw new NoSuchElementException("Data doesn't exist "
                        + "in AVL tree.");
            }
        }
        return null;
    }

    /**
     * Clear the tree.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Return the height of the root of the tree.
     *
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return height(root);
    }

    /**
     * Helper method that finds the height of the node.
     *
     * @param node the current node
     * @return the height of the node
     */
    private int height(AVLNode<T> node) {
        if (node == null) {
            return -1;
        } else {
            return 1 + Math.max(height(node.getLeft()),
                    height(node.getRight()));
        }
    }

    /**
     * Helper method that gets the height of the node.
     *
     * @param node the current node
     * @return the height of the node
     */
    private int getHeight(AVLNode<T> node) {
        if (node == null) {
            return -1;
        } else {
            return node.getHeight();
        }
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
     * DO NOT USE IT IN YOUR CODE
     * DO NOT CHANGE THIS METHOD
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}

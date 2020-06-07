/*
 * Name: Linghang Kong
 * PID: A16127732
 */

import java.util.*;

/**
 * DAFTree class
 *
 * @param <K> Generic type of key
 * @param <D> Generic type of data
 * @author Linghang Kong
 * @since June 5th 2020
 */

@SuppressWarnings("rawtypes")
public class DAFTree<K extends Comparable<? super K>, D> implements Iterable {

    // instance variables
    private DAFNode<K, D> root; // root node
    private int nElems; // number of elements stored
    private int nKeys; // number of unique keys stored

    /**
     * DAFNode class
     *
     * @param <K> Generic type of key
     * @param <D> Generic type of data
     */
    protected class DAFNode<K extends Comparable<? super K>, D> {
        K key;
        D data;
        DAFNode<K, D> left, dup, right; // children
        DAFNode<K, D> par; // parent

        /**
         * Initializes a DAFNode object.
         *
         * @param key  key of the node
         * @param data data of the node
         * @throws NullPointerException if key or data is null
         */
        public DAFNode(K key, D data) {
            if(key == null || data == null) {
                throw new NullPointerException();
            }
            this.key = key;
            this.data = data;
            this.left = null;
            this.right = null;
            this.par = null;
            this.dup = null;
        }

        /**
         * Check if obj equals to this object.
         *
         * @param obj object to compare with
         * @return true if equal, false otherwise
         */
        @Override
        public boolean equals(Object obj) {
            if(obj == null) {
                throw new NullPointerException();
            }
            if(obj == this) {
                return true;
            }
            //compare key and data
            return (((DAFNode<K,D>)(obj)).key == this.key && ((DAFNode<K,D>)(obj)).data == this.data);
        }

        /**
         * Returns the hash value of current node.
         *
         * @return hash value
         */
        @Override
        public int hashCode() {
            return (data.hashCode()+key.hashCode());
        }

        /* PROVIDED HELPERS, MODIFY WITH CAUTION! */

        /**
         * Public helper to swap all DAFNode references of this and the given node.
         *
         * @param other Node to swap with this
         */
        public void swapReferencesWith(DAFNode<K, D> other) {
            DAFNode<K, D> temp = this.left;
            this.left = other.left;
            other.left = temp;
            if (this.left != null) {
                this.left.par = this;
            }
            if (other.left != null) {
                other.left.par = other;
            }

            temp = this.right;
            this.right = other.right;
            other.right = temp;
            if (this.right != null) {
                this.right.par = this;
            }
            if (other.right != null) {
                other.right.par = other;
            }

            // no swap of dup as dup is coupled with the node

            temp = this.par;
            this.changeParentTo(other, other.par);
            other.changeParentTo(this, temp);
        }

        /**
         * Public helper to change this node's par to the given parent. The given child
         * is used to determine which child (left, right, dup) of the given parent this
         * node should be. Only the connection between this and the given parent will
         * update. Does nothing if the given child is not a child of parent.
         *
         * @param child  Old child of the given parent
         * @param parent New parent of this node
         * @throws NullPointerException if child is null
         */
        public void changeParentTo(DAFNode<K, D> child, DAFNode<K, D> parent) {
            if (child == null) {
                throw new NullPointerException();
            }
            if (parent == null) {
                this.par = null;
                return;
            }

            if (parent.left == child) {
                parent.left = this;
                this.par = parent;
            } else if (parent.right == child) {
                parent.right = this;
                this.par = parent;
            } else if (parent.dup == child) {
                parent.dup = this;
                this.par = parent;
            }
        }
    }

    /**
     * Initializes an empty DAFTree.
     */
    public DAFTree() {
        this.root = null;
        this.nElems = 0;
        this.nKeys = 0;
    }

    /**
     * Returns the total number of elements stored in the tree.
     *
     * @return total number of elements stored
     */
    public int size() {
        return this.nElems;
    }

    /**
     * Returns the total number of unique keys stored in the tree.
     *
     * @return total number of unique keys stored
     */
    public int nUniqueKeys() {
        return this.nKeys;
    }

    /**
     * Inserts a new node that has given key and data to the tree.
     *
     * @param key  key to insert
     * @param data data to insert
     * @return the inserted node object, or null if already exist
     * @throws NullPointerException if key or data is null
     */
    public DAFNode<K, D> insert(K key, D data) {
        if(key == null || data == null) {
            throw new NullPointerException();
        }
        //update number of keys and elements
        if(lookup(key,data)) {
            return null;
        }
        this.nElems++;
        if (!lookupAny(key)){
            this.nKeys++;
        }
        DAFNode<K, D> node= new DAFNode<K, D>(key,data);
        //if root is null
        if(root == null){
            root = node;
            return node;
        }

        DAFNode<K, D> curr = root;
        while(true){
            if(node.key.compareTo(curr.key) < 0){
                if(curr.left == null){
                    curr.left = node;
                    node.par = curr;
                    return node;
                }
                //go left
                curr = curr.left;
            }
            else if(node.key.compareTo(curr.key)>0){
                if(curr.right == null){
                    curr.right = node;
                    node.par = curr;
                    return node;
                }
                //go right
                curr = curr.right;
            }
            else{
                if(curr.dup == null){
                    curr.dup = node;
                    node.par = curr;
                    return node;
                }
                curr = curr.dup;
            }
        }
    }

    /**
     * Checks if the key is stored in the tree.
     *
     * @param key key to search
     * @return true if found, false otherwise
     * @throws NullPointerException if the key is null
     */
    public boolean lookupAny(K key) {
        if(key == null) {
            throw new NullPointerException();
        }
        return lookupAnyHelper(root,key);
    }

    /**
     * helper function for look up any
     * @param node root where to start
     * @param key key to search for
     * @return true if found, false otherwise
     */
    private boolean lookupAnyHelper(DAFNode<K, D> node, K key){
        if(node == null) {
            return false;
        }
        if(node.key == key) {
            return true;
        }
        return (lookupAnyHelper(node.left, key) || lookupAnyHelper(node.right, key));
    }

    /**
     * Checks if the specified key-data pair is stored in the tree.
     *
     * @param key  key to search
     * @param data data to search
     * @return true if found, false otherwise
     * @throws NullPointerException if key or data is null
     */
    public boolean lookup(K key, D data) {

        if(key == null || data == null) {
            throw new NullPointerException();
        }
        return lookupHelper(root,key,data);
    }

    /**
     * helper function for look up
     * @param node root to start
     * @param key key to match
     * @param data data to match
     * @return true if found and false otherwise
     */
    private boolean lookupHelper(DAFNode<K, D> node, K key, D data){
        if(node == null) {
            return false;
        }
        if(node.key == key){
            DAFNode<K, D> curr = node;
            while(curr!=null){
                if(curr.data == data) {
                    return true;
                }
                else {
                    curr = curr.dup;
                }
            }
            return false;
        }
        // look up in the subtrees
        return (lookupHelper(node.left, key, data) || lookupHelper(node.right, key, data));
    }

    /**
     * helper method, find the node that contains key recursively
     * @param node root of the tree or sub tree
     * @param key key to find
     * @return
     */
    private DAFNode<K, D> findNodeHelper(DAFNode<K, D> node, K key){
        if(node == null) {
            return null;
        }
        if(node.key == key) {
            return node;
        }
        DAFNode<K,D> leftNode;
        DAFNode<K,D> rightNode;
        //find the node recursively
        leftNode = findNodeHelper(node.left, key);
        rightNode = findNodeHelper(node.right, key);
        //return the result
        if(leftNode == null && rightNode == null) {
            return null;
        }
        else{
            if(leftNode!=null) {
                return leftNode;
            }
            else {
                return rightNode;
            }
        }
    }

    /**
     * Returns a LinkedList of all data associated with the given key.
     *
     * @return list of data (empty if no data found)
     * @throws NullPointerException if the key is null
     */
    public LinkedList<D> getAllData(K key) {
        if(key == null) {
            throw new NullPointerException();
        }
        //if not contained return an empty linked list
        if(!this.lookupAny(key)) {
            LinkedList<D> resultList = new LinkedList<D>();
            return resultList;
        }
        DAFNode<K, D> temp = findNodeHelper(root,key);
        LinkedList<D> result = new LinkedList<D>();
        DAFNode<K, D> curr = temp;
        while(curr!=null){
            result.add(curr.data);
            curr = curr.dup;
        }
        return result;
    }

    /**
     * Removes the node with given key and data from the tree.
     *
     * @return true if removed, false if this node was not found
     * @throws NullPointerException if key or data is null
     */
    public boolean remove(K key, D data) {
        if(key == null || data == null) {
            throw new NullPointerException();
        }
        if(!this.lookup(key,data)) {
            return false;
        }
        DAFNode<K, D> temp = findNodeHelper(root,key);
        DAFNode<K, D> curr = temp;
        while(curr!=null){
            //delete and break
            if(curr.data == data) {
                this.remove(curr);
                break;
            }
            else {
                curr = curr.dup;
            }
        }
        return true;
    }

    /**
     * Removes all nodes with given key from the tree.
     *
     * @return true if any node is removed, false otherwise
     * @throws NullPointerException if the key is null
     */
    public boolean removeAll(K key) {
        if(key == null) {
            throw new NullPointerException();
        }
        if(!this.lookupAny(key)) {
            return false;
        }
        //delete the nodes having the same key
        Stack<DAFNode<K, D>> stack = new Stack<DAFNode<K, D>>();
        DAFNode<K, D> temp = findNodeHelper(root,key);
        DAFNode<K, D> curr = temp;
        while(curr!=null){
            stack.push(curr);
            curr = curr.dup;
        }
        //delete all the nodes in the stack
        while(!stack.empty()){
            DAFNode<K, D> temp2 = stack.peek();
            remove(temp2);
            stack.pop();
        }
        return true;
    }

    /**
     * Returns a tree iterator instance.
     *
     * @return iterator
     */
    public Iterator<DAFNode<K, D>> iterator() {
        return new DAFTreeIterator();
    }

    /**
     * iterator class that iterates the elements in inorder
     */
    public class DAFTreeIterator implements Iterator<DAFNode<K, D>> {
        //use a stack to store values
        public Stack<DAFNode<K, D>> stack;
        /**
         * Initializes a tree iterator instance.
         */
        public DAFTreeIterator() {
            stack = new Stack<>();
            //add the left most chain
            DAFNode<K, D> curr = root;
            while(curr!=null){
                stack.push(curr);
                curr = curr.left;
            }
        }

        /**
         * Checks if the iterator has next element.
         *
         * @return true if there is a next, false otherwise
         */
        public boolean hasNext() {
           return (!stack.isEmpty());
        }

        /**
         * Returns the next node of the iterator.
         *
         * @return next node
         * @throws NoSuchElementException if the iterator reaches the end of traversal
         */
        public DAFNode<K, D> next() {
            DAFNode<K, D> result = stack.pop();
            DAFNode<K, D> curr = result;
            //if cur has duplicates, push the duplicates into stack
            if(curr.dup!=null)
                stack.push(curr.dup);
            else {
                //go to the origin of dups
                while (true){
                    DAFNode<K, D> origin = curr.par;
                    if(origin!=null && origin.dup == curr){
                        origin = origin.par;
                        curr = curr.par;
                    }
                    else
                        break;
                }
                if (curr.right != null) {
                    curr = curr.right;
                    while (curr != null) {
                        stack.push(curr);
                        curr = curr.left;
                    }
                }
            }
            return result;
        }
    }
    /* PROVIDED HELPERS, MODIFY WITH CAUTION! */
    /**
     * Public helper to remove the given node in BST's remove style.
     *
     * @param cur Node to remove
     * @boolean true always
     */
    public boolean remove(DAFNode<K, D> cur) {
        if (cur.dup == null && (cur.par == null || cur.par.dup != cur))
            nKeys--;

        if (cur == root) {
            root = removeHelper(cur, cur.key, cur.data);
            if (root != null) {
                root.par = null;
            }
        } else {
            // passing in par to let helper update both par and child reference
            removeHelper(cur.par, cur.key, cur.data);
        }
        nElems--;
        return true;
    }

    /**
     * Helper to remove node recursively in BST style of replacement by in-order
     * successor, with modification of handling dup and also node are swapped
     * instead of data field being replaced.
     *
     * @param root Root
     * @param key  To be removed
     * @return The node that replaces the node to be removed
     */
    private DAFNode<K, D> removeHelper(DAFNode<K, D> root, K key, D data) {
        if (root == null)
            return null;

        // update child reference and make replacement if root is the target
        DAFNode<K, D> replacedChild = null; // this is different from bst
        if (key.compareTo(root.key) < 0) {
            root.left = replacedChild = removeHelper(root.left, key, data);
        } else if (key.compareTo(root.key) > 0) {
            root.right = replacedChild = removeHelper(root.right, key, data);
        } else if (!data.equals(root.data)) { // this is different from bst
            root.dup = replacedChild = removeHelper(root.dup, key, data);
        } else if (root.dup != null) { // this is different from bst
            // swap only left & right
            root.dup.left = root.left;
            root.dup.right = root.right;
            if (root.left != null) {
                root.left.par = root.dup;
            }
            if (root.right != null) {
                root.right.par = root.dup;
            }

            root = root.dup;
        } else if (root.left != null && root.right != null) {
            // the following is all different from bst
            DAFNode<K, D> successor = findMin(root.right);
            DAFNode<K, D> nextRoot = root.right;
            DAFNode<K, D> temp;

            // swap content
            root.swapReferencesWith(successor);
            // swap the pointer back
            temp = root;
            root = successor;
            successor = temp;

            // special case: if root's right is successor,
            // references/connection between them will be broken
            // but will still be handled correctly by following code
            if (nextRoot == root)
                nextRoot = successor;

            root.right = replacedChild = removeHelper(nextRoot, successor.key, successor.data);
        } else {
            root = (root.left != null) ? root.left : root.right;
        }

        // update parent reference
        if (replacedChild != null) // this is different from bst
            replacedChild.par = root;

        return root;
    }

    /**
     * Helper to return the smallest node from a given subroot.
     *
     * @param root Smallest node will be found from this node
     * @return The smallest node from the 'root' node
     */
    private DAFNode<K, D> findMin(DAFNode<K, D> root) {
        DAFNode<K, D> cur = root;
        while (cur.left != null)
            cur = cur.left;
        return cur;
    }
}
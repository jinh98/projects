public class BinaryTreeNode<E> {

    //variables
    private E item;
    private BinaryTreeNode<E> left;
    private BinaryTreeNode<E> right;


    //constructor
    public BinaryTreeNode(E item){
        this.item=item;


    }


    /**
     * setter for item
     * @param item
     */
    public void setItem(E item) {
        this.item = item;
    }

    /**
     * setter for left child
     * @param left
     */
    public void setLeft(BinaryTreeNode<E> left) {
        this.left = left;
    }

    /**
     * setter for right child
     * @param right
     */
    public void setRight(BinaryTreeNode<E> right) {
        this.right = right;
    }

    /**
     * getter for item in node
     * @return
     */
    public E getItem (){
        return this.item;
    }

    /**
     * getter for left child
     * @return
     */
    public BinaryTreeNode<E> getLeft(){
        return this.left;


    }

    /**
     * getter for right child
     * @return
     */
    public BinaryTreeNode<E> getRight(){
        return this.right;
    }


    /**
     * checks if a node is a leaf
     * @return
     */
    public boolean isLeaf(){
        if (this.left == null && this.right == null){ //it is a leaf when both left and right child are null
            return true;
        }

        return false;
    }

}

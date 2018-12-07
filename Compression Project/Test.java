public class Test {
    public static void main (String args[]){
        BinaryTree<String> myTree = new BinaryTree<String>();

        Stack<String> myStack = new Stack<String>();
        myTree.addOrdered(null);
        String theTree ="((c d) ((a b) (e f)))";


        Decoder myD = new Decoder(theTree);

        myTree.getRoot().setLeft(new BinaryTreeNode<String>(null));
        myTree.getRoot().setRight(new BinaryTreeNode<String>("c"));
        myTree.getRoot().getLeft().setLeft(new BinaryTreeNode<String>("a"));

        myTree.getRoot().getLeft().setRight(new BinaryTreeNode<String>("b"));

        //theTree = myTree.makeTreeString(myTree.getRoot());
        System.out.println(theTree);

        myD.setMyStack(myD.stringToStack(myD.getTreeString()));
        myD.stackToRoot();
        myD.stackToTree( myD.getMyTree().getRoot(), false);
        System.out.println("11111111111111");
        myD.getMyTree().preOrderTraverseTree(myD.getMyTree().getRoot());
        String newtree = myD.getMyTree().makeTreeString(myD.getMyTree().getRoot());
        System.out.println(newtree);

        System.out.println(myD.switchAscii(newtree));
        System.out.println(myD.asciiToString(myD.switchAscii(newtree)));
    }
}

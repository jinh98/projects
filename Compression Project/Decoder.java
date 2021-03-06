public class Decoder {
    private String treeString;
    private BinaryTree<String> myTree = new BinaryTree<String>();

    private Stack<String> myStack;

    public Decoder(String treeString){
        this.treeString =treeString;
    }

    public String switchAscii(String treeString){//this turns a character represented string tree to a ascii represented string tree

        for(int i = 0; i<treeString.length(); i++){
            String temp;
            if (treeString.charAt(i)!=('(')&& treeString.charAt(i)!=(' ')&& treeString.charAt(i)!=(')') ) {
                    temp = ""+(int)treeString.charAt(i);
                    treeString = treeString.substring(0, i) + temp + treeString.substring(i + 1);

                    i = i + temp.length();
            }
        }
        return treeString;
    }

    public String asciiToString(String treeString){ //this turns an ascii represented string tree to a character represented string tree
        for(int i = 0; i<treeString.length(); i++){
            String temp ="";
            int end=i;
            if (treeString.charAt(i)!=('(')&& treeString.charAt(i)!=(' ')&& treeString.charAt(i)!=(')') ) {

                do {
                    end++;


                }while (treeString.charAt(end)!=('(')&& treeString.charAt(end)!=(' ')&& treeString.charAt(end)!=(')'));
                temp = treeString.substring(i,end);
                int value = Integer.parseInt(temp);
                Character replace = (char)value;
                treeString = treeString.substring(0, i) + replace + treeString.substring(end);
            }
        }
        return treeString;
    }

    public String getTreeString() {
        return treeString;
    }

    public void setTreeString(String treeString) {
        this.treeString = treeString;
    }

    public Stack stringToStack(String tree){
        Stack<String> myStack = new Stack<String>();
        for(int i =0; i< tree.length()-1; i++){
            myStack.push(tree.substring(i, i+1));
        }
        myStack.push(tree.substring(tree.length()-1));
        return myStack;
    }

    public void stackToRoot(){
        myStack.pop();
        myTree.addOrdered(null);
    }


    //recursive
    public void stackToTree(BinaryTreeNode<String> focusNode, boolean isLeft){
        if (myStack.isEmpty() == true){

            //base case
            return;
        }else{

            String temp = myStack.pop("");
            if (temp.equals(")")){//create a node

                System.out.println("right )!");
                if (isLeft == false) {
                    focusNode.setRight(new BinaryTreeNode<String>(null));

                    stackToTree(focusNode.getRight(), false);

                    stackToTree(focusNode, false);
                }else{
                    focusNode.setLeft(new BinaryTreeNode<String>(null));
                    stackToTree(focusNode.getLeft(), false);
                    stackToTree(focusNode, false);
                }


            }else if(temp.equals(" ")){
                System.out.println("space!");
                stackToTree(focusNode, true);

            }else if(temp.equals("(")){

                System.out.println("left (!");
                //end
                return;
            }else{
                //all the letters
                System.out.println("letter! is ! "+ temp);
                if (isLeft==false){
                    focusNode.setRight(new BinaryTreeNode<String>(temp));
                }else{
                    focusNode.setLeft(new BinaryTreeNode<String>(temp));
                }
                stackToTree(focusNode, false);

            }
        }

    }

    public BinaryTreeNode<String> stringToNode(String temp){

        return new BinaryTreeNode<String>(temp);
    }


    public BinaryTree<String> getMyTree() {
        return myTree;
    }

    public void setMyTree(BinaryTree<String> myTree) {
        this.myTree = myTree;
    }

    public Stack<String> getMyStack() {
        return myStack;
    }

    public void setMyStack(Stack<String> myStack) {
        this.myStack = myStack;
    }
}

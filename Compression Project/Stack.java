public class Stack<E> {
    private StackNode<E> top = new StackNode<E>(null, null);

    public void push(E item){
        if (top == null || top.getItem() == null){
            top.setItem(item);
        }else{
            StackNode tempNode = top;
            top = new StackNode<E> (item, tempNode);
        }

    }
    public void pop(){
        if (top == null || top.getItem() == null){
        }else{
            top = top.getNext();
        }

    }

    public E pop(E item){

        if (top == null || top.getItem() == null){
            item = null;
        }else{
            item = top.getItem();
            top = top.getNext();
        }
        return item;
    }

    public void showAll(){
        StackNode<E> focus = top;
        while(focus!= null && focus.getItem()!=null){
            System.out.println(focus.getItem());
            focus = focus.getNext();
        }
    }

    public boolean isEmpty(){
        if (top == null){
            return true;
        }
        return false;
    }

    public int size(){
        StackNode<E> focus = top;
        int num=0;
        while(focus!= null){
            num++;

            focus = focus.getNext();
        }
        return num;
    }


}

public class StackNode <E> {

    private E item;
    private StackNode<E> next;

    public StackNode(E item, StackNode<E> next){
        this.item=item;
        this.next= next;


    }

    public StackNode<E> getnext(){
        return this.next;

    }
    public StackNode<E> getNext(){
        return this.next;
    }

    public void setNext(StackNode<E> next){
        this.next = next;
    }

    public E getItem (){
        return this.item;
    }

    public void setItem(E item){
        this.item = item;
    }
}

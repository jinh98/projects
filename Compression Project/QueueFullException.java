import java.util.HashMap;
/**
 *Exception to indicate that Queue is full.
 */
class QueueFullException extends RuntimeException {
     
    public QueueFullException(){
        super();
    }
    
    public QueueFullException(String message){
        super(message);
    }
    
}
 
/**
 *Exception to indicate that Queue is empty.
 */
class QueueEmptyException extends RuntimeException {
     
    public QueueEmptyException(){
        super();
    }
    
    public QueueEmptyException(String message){
        super(message);
    }
    
}
 
class PriorityQueue {
    
    private BinaryTree[] prioQueueAr;
    private int size;//Size of Queue
    private int number;  //holds number of elements in Priority Queue, initialized with 0 by default
 
    public PriorityQueue(int size){
           this.size = size;
           prioQueueAr = new BinaryTree[this.size];
           number = 0;
    }
 
 
    /**
     * Insert element in Priority Queue, element will be inserted on basis of priority.
     */
    public void insert(BinaryTree value, HashMap map){
           int i;
           if(isFull()){
                  throw new QueueFullException("Cannot insert "+value+", Queue is full");
           }                       
           if (number == 0)
                  prioQueueAr[number++] = value; //If no values in PriorityQueue- insert at starting position, i.e. at 0th position.
           else{
                  for (i=number - 1; i>= 0; i--){              
                        if (value.sumFrequency(value.getRoot(),map) > prioQueueAr[i].sumFrequency(prioQueueAr[i].getRoot(),map))
                               prioQueueAr[i + 1] = prioQueueAr[i]; //if value is larger, shift elements upward till value is larger.
                        else
                               break;
                  }
                  prioQueueAr[++i] = value; // insert element in space created by upward shift of elements.
                  number++;
           }
    }
 
 
    /**
     * Remove elements from Priority Queue
     */
    public BinaryTree remove(){
           if(isEmpty()){
                  throw new QueueEmptyException("Queue is empty");
           }
           return prioQueueAr[--number];
    }
 
    /**
     * Returns true if Priority Queue is full
     * @return
     */
    public boolean isFull(){
           return (number == size);
    }
    
    /**
     * Returns true if Priority Queue is empty
     * @return
     */
    public boolean isEmpty(){
           return (number == 0);
    }
    
    public int size(){
      return number;
    }
 
    
}

 
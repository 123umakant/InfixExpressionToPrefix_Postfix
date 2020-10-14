import java.util.LinkedList;
import java.util.Queue;

public class StackUsingQueue {
    static Queue<Integer> firstQueue = new LinkedList<Integer>();
    static Queue<Integer> secondQueue = new LinkedList<Integer>();

    static int currentSize=0;

    StackUsingQueue(){
        currentSize=0;
    }

    static void push(int element){

        currentSize++;
        secondQueue.add(element);

        while (!firstQueue.isEmpty()){
            secondQueue.add(firstQueue.peek());
            firstQueue.remove();
        }

        Queue<Integer> queue =firstQueue;
        firstQueue=secondQueue;
        secondQueue=queue;

    }
    static void pop(){
        if(firstQueue.isEmpty())
            return;

        firstQueue.remove();
        currentSize--;
    }
    static int top()
    {
        if (firstQueue.isEmpty())
            return -1;
        return firstQueue.peek();
    }

    static int size()
    {
        return currentSize;
    }

    public static void main(String[] args) {
       StackUsingQueue s = new StackUsingQueue();
        s.push(1);
        s.push(2);
        s.push(3);

        System.out.println(top());

    }
}

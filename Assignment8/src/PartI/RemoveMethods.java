package PartI;
import java.util.LinkedList;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RemoveMethods {

  public static void remAllStack(Stack<Object> stack, Object item) {
    Stack<Object> stack1 = new Stack<>();
    while(!stack.empty()){
      if(!stack.peek().equals(item))
        stack1.push(stack.peek());
      stack.pop();
    }
    while(!stack1.empty()){
      stack.push(stack1.peek());
      stack1.pop();
    }
  }

  public static void remAllQueue(Queue<Object> queue, Object item) {
    int cap = queue.size();
    for(int i = 0; i<cap ;i++){
      Object it = queue.poll();
      if(!it.equals(item))
        queue.add(it);
    }
  }

  public static void main(String[] args) {
    Stack<Object> stk = new Stack<Object>();
    stk.push(new Integer(24));
    stk.push(new Integer(2));
    stk.push(new Integer(9));
    stk.push(new Integer(2));
    stk.push(new Integer(7));
    stk.push(new Integer(10));
    stk.push(new Integer(16));
    System.out.println("begin: stk is " + stk);
    RemoveMethods.remAllStack(stk, new Integer(2));
    System.out.println("end: stk is " + stk);
    RemoveMethods.remAllStack(stk, new Integer(24));
    System.out.println("end: stk is " + stk);

    Queue<Object> q = new LinkedList<>(); // you should probably find a concrete class for this
    q.offer(new Integer(24));
    q.offer(new Integer(2));
    q.offer(new Integer(9));
    q.offer(new Integer(2));
    q.offer(new Integer(7));
    q.offer(new Integer(10));
    q.offer(new Integer(16));
    System.out.println("begin: q is " + q);
    RemoveMethods.remAllQueue(q, new Integer(2));
    System.out.println("end: q is " + q);
    RemoveMethods.remAllQueue(q, new Integer(24));
    System.out.println("end: q is " + q);

  }
}

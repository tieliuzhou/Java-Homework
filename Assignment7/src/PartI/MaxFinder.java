package PartI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.PriorityQueue;

public class MaxFinder<T extends Number>{
  private PriorityQueue<T> q;

  MaxFinder(){
    this.q = new PriorityQueue<T>(new Comparator<T>() {
      @Override
      public int compare(T o1, T o2) {
        return o2.byteValue() - o1.byteValue();
      }
    });
  }

  public void add(Collection<T> c){
    for(Object it : c){
      q.add((T) it);
    }
  }
  public void add(T t){
    q.add(t);
  }
  public T max(){
    return q.peek();
  }

  public static void main(String[] args){
    MaxFinder<Integer> m1 = new MaxFinder<Integer>();
    MaxFinder<Double> m2 = new MaxFinder<Double>();
    Collection<Integer> a= new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
    Collection<Double> b = new ArrayList<>(Arrays.asList(1.5, 2.5, 3.5, 4.5, 5.5));
    m1.add(a);
    m2.add(b);
    System.out.println("The Max Number in Collection of Integers is "+m1.max());
    System.out.println("The Max Number in Collection of Doubles is "+m2.max());
  }
}
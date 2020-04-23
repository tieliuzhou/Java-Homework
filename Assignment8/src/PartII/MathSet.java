package PartII;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MathSet<E> extends HashSet<E> {

  public MathSet() {
    super();
  }
  public MathSet(List<E> list) {
    super(list);
  }
  public MathSet(MathSet<E> mathset) {
    super(mathset);
  }

  public MathSet<E> intersection(MathSet<E> s2) {
    MathSet<E> s = new MathSet<>();
    for(E it : this){
      if(s2.contains(it)){
        s.add(it);
      }
    }
    return s;
  }

  public MathSet<E> union(MathSet<E> s2) {
    MathSet<E> s = new MathSet<>(this);
    for(E it : s2){
      if(!this.contains(it))
        s.add(it);
    }
    return s;
  }

  public <T> MathSet<Pair<E,T>> cartesianProduct(Set<T> s2) {
    MathSet<Pair<E,T>> s = new MathSet<>();
    for(E it : this){
      for(T that : s2){
        s.add(new Pair(it,that));
      }
    }
    return s;
  }



  public static void main(String[] args) {
    // create two MathSet objects of the same type.
    // See if union and intersection works.

    // create another MathSet object of a different type
    // calculate the cartesian product of this set with one of the
    // above sets

    MathSet<Integer> s1 =  new MathSet<>(Arrays.asList(1,2,3,4));
    MathSet<Integer> s2 = new MathSet<>(Arrays.asList(1,2,3,19,500));
    MathSet<Integer> s3 = s1.intersection(s2);
    MathSet<Integer> s4 = s1.union(s2);

    MathSet<String> s5 = new MathSet<>(Arrays.asList("A","B"));
    MathSet<Pair<Integer,String>> s6 = s1.cartesianProduct(s5);

    System.out.println("s1 is :" + s1);
    System.out.println("s2 is : " + s2);
    System.out.println("the intersection of s1 and s2 is : " + s3);
    System.out.println("the union of s1 and s2 is :" + s4);
    System.out.println("s5 is : " + s5);
    System.out.println("the cartesianProduct of s1 and s5 is :" + s6);
  }
}

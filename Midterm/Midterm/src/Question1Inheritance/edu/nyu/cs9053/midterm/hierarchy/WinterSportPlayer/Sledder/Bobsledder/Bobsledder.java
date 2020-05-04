package Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer.Sledder.Bobsledder;

import Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer.Sledder.Sledder;
import java.util.Objects;


public class Bobsledder extends Sledder {
  private double TheAverageFinishTime;
  public Bobsledder(){
    super();
    this.TheAverageFinishTime = 0;
  }
  public Bobsledder(String s, int age, String color, double t){
    super(s,age,color);
    this.TheAverageFinishTime = t;
  }

  public double getTheAverageFinishTime(){
    return this.TheAverageFinishTime;
  }
  public boolean equals(Object o){
    if(o.getClass() != this.getClass())
      return false;
    else{
      Bobsledder cmp = (Bobsledder) o;
      if(cmp.getName() == this.getName()
          && cmp.getAge() == this.getAge()
          && cmp.getSledColor() == this.getSledColor()
          && cmp.getTheAverageFinishTime() == this.getTheAverageFinishTime())
        return true;
      else
        return false;
    }
  }
  public int hashCode(){
    return Objects.hash(this.getName(),this.getAge(),this.getSledColor(),this.getTheAverageFinishTime());
  }
}

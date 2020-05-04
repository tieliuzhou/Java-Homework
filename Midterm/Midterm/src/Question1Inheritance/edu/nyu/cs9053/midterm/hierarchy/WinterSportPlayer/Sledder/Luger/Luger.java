package Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer.Sledder.Luger;

import Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer.Sledder.Bobsledder.Bobsledder;
import Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer.Sledder.Sledder;
import java.util.Objects;


public class Luger extends Sledder {
  private String FingerSpikesColor;
  public Luger(){
    super();
    this.FingerSpikesColor = null;
  }
  public Luger(String s, int age, String c, String color){
    super(s,age,c);
    this.FingerSpikesColor = color;
  }

  public String getFingerSpikesColor() {
    return this.FingerSpikesColor;
  }
  public boolean equals(Object o){
    if(o.getClass() != this.getClass())
      return false;
    else{
      Luger cmp = (Luger) o;
      if(cmp.getName() == this.getName()
          && cmp.getAge() == this.getAge()
          && cmp.getSledColor() == this.getSledColor()
          && cmp.getFingerSpikesColor() == this.getFingerSpikesColor())
        return true;
      else
        return false;
    }
  }
  public int hashCode(){
    return Objects.hash(this.getName(),this.getAge(),this.getSledColor(),this.getFingerSpikesColor());
  }
}

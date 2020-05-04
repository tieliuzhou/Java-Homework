package Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer.Sledder;

import Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer.Skier.Skier;
import Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer.WinterSportPlayer;
import java.util.Objects;

public class Sledder extends WinterSportPlayer {
  private String SledColor;
  public Sledder(){
    super();
    this.SledColor = null;
  }
  public Sledder(String s, int age, String color){
    super(s,age);
    this.SledColor = color;
  }
  public String getSledColor(){
    return this.SledColor;
  }


  public boolean equals(Object o){
    if(o.getClass() != this.getClass())
      return false;
    else{
      Sledder cmp = (Sledder) o;
      if(cmp.getName() == this.getName()
          && cmp.getAge() == this.getAge()
          && cmp.getSledColor() == this.getSledColor())
        return true;
      else
        return false;
    }
  }
  public int hashCode(){
    return Objects.hash(this.getName(),this.getAge(),this.getSledColor());
  }
}

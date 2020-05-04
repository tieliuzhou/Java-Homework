package Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer.TeamSports.Curler;

import Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer.Sledder.Sledder;
import Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer.TeamSports.TeamSports;
import java.util.Objects;

public class Curler extends TeamSports {
  public enum COLOR{
    RED,BLUE;
  }
  private String StoneColor;
  public Curler(){
    super();
    this.StoneColor = null;
  }
  /*public Curler(String s, int age, String sc){
    super(s,age);
    this.StoneColor = sc;
  }*/
  public Curler(String s, int age, COLOR c){
    super(s,age);
    this.StoneColor = c.toString();
  }

  public String getStoneColor(){
    return this.StoneColor;
  }
  public boolean equals(Object o){
    if(o.getClass() != this.getClass())
      return false;
    else{
      Curler cmp = (Curler) o;
      if(cmp.getName() == this.getName()
          && cmp.getAge() == this.getAge()
          && cmp.getStoneColor() == this.getStoneColor())
        return true;
      else
        return false;
    }
  }
  public int hashCode(){
    return Objects.hash(this.getName(),this.getAge(),this.getStoneColor());
  }
}

package Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer.Skier;

import Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer.IceSkater.IceSkater;
import Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer.WinterSportPlayer;
import java.util.Objects;

public class Skier extends WinterSportPlayer {
  private int SkiLength;
  public Skier(){
    super();
    this.SkiLength = 0;
  }
  public Skier(String name,int age, int SkiLength){
    super(name,age);
    this.SkiLength = SkiLength;
  }

  public int getSkiLength(){
    return this.SkiLength;
  }

  public boolean equals(Object o){
    if(o.getClass() != this.getClass())
      return false;
    else{
      Skier cmp = (Skier) o;
      if(cmp.getName() == this.getName()
          && cmp.getAge() == this.getAge()
          && cmp.getSkiLength() == this.getSkiLength())
        return true;
      else
        return false;
    }
  }
  public int hashCode(){
    return Objects.hash(this.getName(),this.getAge(),this.getSkiLength());
  }
}

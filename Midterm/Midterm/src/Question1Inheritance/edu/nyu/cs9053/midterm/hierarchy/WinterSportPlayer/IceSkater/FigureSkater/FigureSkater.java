package Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer.IceSkater.FigureSkater;

import Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer.IceSkater.IceSkater;
import java.util.Objects;

public class FigureSkater extends IceSkater {
  private double SkillScore;
  public FigureSkater(){
    super();
    this.SkillScore = 0;
  }
  public FigureSkater(String name, int age, int SkateSize, double score){
    super(name,age,SkateSize);
    this.SkillScore = score;
  }

  public double getSkillScore(){
    return this.SkillScore;
  }

  public boolean equals(Object o){
    if(o.getClass() != this.getClass())
      return false;
    else{
      FigureSkater cmp = (FigureSkater) o;
      if(cmp.getName() == this.getName()
          && cmp.getAge() == this.getAge()
          && cmp.getSkateSize() == this.getSkateSize()
          && cmp.getSkillScore() == this.getSkillScore())
        return true;
      else
        return false;
    }
  }

  public int hashCode(){
    return Objects.hash(this.getName(),this.getAge(),this.getSkateSize(),this.getSkillScore());
  }


}

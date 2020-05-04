package Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer.Skier.MogulSkier;

import Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer.Skier.CrossCountrySkier.CrossCountrySkier;
import Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer.Skier.Skier;
import java.util.Objects;

public class MogulSkier extends Skier {
  private double PerformanceScore;
  public MogulSkier(){
    super();
    this.PerformanceScore = 0;
  }
  public MogulSkier(String name,int age, int SkiLength, double score){
    super(name,age,SkiLength);
    this.PerformanceScore = score;
  }

  public double getPerformanceScore(){
    return this.PerformanceScore;
  }
  public boolean equals(Object o){
    if(o.getClass() != this.getClass())
      return false;
    else{
      MogulSkier cmp = (MogulSkier) o;
      if(cmp.getName() == this.getName()
          && cmp.getAge() == this.getAge()
          && cmp.getSkiLength() == this.getSkiLength()
          && cmp.getPerformanceScore() == this.getPerformanceScore())
        return true;
      else
        return false;
    }
  }
  public int hashCode(){
    return Objects.hash(this.getName(),this.getAge(),this.getSkiLength(),this.getPerformanceScore());
  }
}

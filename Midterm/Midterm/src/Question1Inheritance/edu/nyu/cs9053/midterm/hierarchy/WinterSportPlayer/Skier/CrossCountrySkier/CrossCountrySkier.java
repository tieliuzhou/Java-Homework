package Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer.Skier.CrossCountrySkier;

import Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer.Skier.Skier;
import java.util.Objects;

public class CrossCountrySkier extends Skier {
  private String PropulsionTechniques;
  public CrossCountrySkier(){
    super();
    this.PropulsionTechniques = null;
  }
  public CrossCountrySkier(String name,int age, int SkiLength,String PT){
    super(name,age,SkiLength);
    this.PropulsionTechniques = PT;
  }

  public String getPropulsionTechniques(){
    return this.PropulsionTechniques;
  }
  public boolean equals(Object o){
    if(o.getClass() != this.getClass())
      return false;
    else{
      CrossCountrySkier cmp = (CrossCountrySkier) o;
      if(cmp.getName() == this.getName()
          && cmp.getAge() == this.getAge()
          && cmp.getSkiLength() == this.getSkiLength()
          && cmp.getPropulsionTechniques() == this.getPropulsionTechniques())
        return true;
      else
        return false;
    }
  }
  public int hashCode(){
    return Objects.hash(this.getName(),this.getAge(),this.getSkiLength(),this.getPropulsionTechniques());
  }
}

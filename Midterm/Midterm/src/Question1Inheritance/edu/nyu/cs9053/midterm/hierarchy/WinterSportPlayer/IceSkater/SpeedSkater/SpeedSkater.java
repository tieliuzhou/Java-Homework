package Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer.IceSkater.SpeedSkater;

import Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer.IceSkater.FigureSkater.FigureSkater;
import Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer.IceSkater.IceSkater;
import java.util.Objects;

public class SpeedSkater extends IceSkater {
  private double Perimeter;
  public SpeedSkater(){
    super();
    this.Perimeter = 0;
  }
  public SpeedSkater(String name, int age, int SkateSize, double p){
    super(name,age,SkateSize);
    this.Perimeter = p;
  }

  public double getPerimeter(){
    return this.Perimeter;
  }
  public boolean equals(Object o){
    if(o.getClass() != this.getClass())
      return false;
    else{
      SpeedSkater  cmp = (SpeedSkater ) o;
      if(cmp.getName() == this.getName()
          && cmp.getAge() == this.getAge()
          && cmp.getSkateSize() == this.getSkateSize()
          && cmp.getPerimeter() == this.getPerimeter())
        return true;
      else
        return false;
    }
  }

  public int hashCode(){
    return Objects.hash(this.getName(),this.getAge(),this.getSkateSize(),this.getPerimeter());
  }
}

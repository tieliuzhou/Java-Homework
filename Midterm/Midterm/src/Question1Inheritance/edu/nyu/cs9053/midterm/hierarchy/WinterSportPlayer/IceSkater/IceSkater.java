package Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer.IceSkater;

import Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer.WinterSportPlayer;
import java.util.Objects;

public class IceSkater extends WinterSportPlayer {
  private int SkateSize;
  public  IceSkater(){
    super();
    this.SkateSize = 0;
  }
  public IceSkater(String name,int age, int SkateSize){
    super(name,age);
    this.SkateSize = SkateSize;
  }

  public int getSkateSize(){
    return this.SkateSize;
  }

  public boolean equals(Object o){
    if(o.getClass() != this.getClass())
      return false;
    else{
      IceSkater cmp = (IceSkater) o;
      if(cmp.getName() == this.getName()
          && cmp.getAge() == this.getAge()
          && cmp.getSkateSize() == this.getSkateSize())
        return true;
      else
        return false;
    }
  }
  public int hashCode(){
    return Objects.hash(this.getName(),this.getAge(),this.getSkateSize());
  }
}

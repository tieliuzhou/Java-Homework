package Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer;

public abstract class WinterSportPlayer {
  private String Name;
  private int Age;
  public WinterSportPlayer(){
    Name = null;
    Age = 0;
  }
  public WinterSportPlayer(String s, int age){
    this.Name = s;
    this.Age = age;
  }


  public String getName() {
    return this.Name;
  }

  public int getAge() {
    return this.Age;
  }
}

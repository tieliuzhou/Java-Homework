package Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer.TeamSports;

import Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer.WinterSportPlayer;

public abstract class TeamSports extends WinterSportPlayer {
  public TeamSports(){
    super();
  }
  public TeamSports(String s, int age){
    super(s,age);
  }
}

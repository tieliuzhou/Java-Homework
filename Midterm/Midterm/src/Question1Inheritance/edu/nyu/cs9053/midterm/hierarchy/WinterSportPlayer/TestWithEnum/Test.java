package Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer.TestWithEnum;


import Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer.IceSkater.FigureSkater.FigureSkater;
import Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer.IceSkater.IceSkater;
import Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer.IceSkater.SpeedSkater.SpeedSkater;
import Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer.Skier.CrossCountrySkier.CrossCountrySkier;
import Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer.Skier.MogulSkier.MogulSkier;
import Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer.Skier.Skier;
import Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer.Sledder.Bobsledder.Bobsledder;
import Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer.Sledder.Luger.Luger;
import Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer.Sledder.Sledder;
import Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer.TeamSports.Curler.Curler;
import Question1Inheritance.edu.nyu.cs9053.midterm.hierarchy.WinterSportPlayer.TeamSports.Curler.Curler.COLOR;



public class Test {


  public static void main(String[] args){
    //Please note that in the Curler class
    // I wrote an enum COLOR to limit the value of its color.
    //When constructing Curler,
    // the value must be taken from the limited colors (RED, BLUE).

    IceSkater i = new IceSkater();
    FigureSkater f = new FigureSkater();
    SpeedSkater s = new SpeedSkater();
    Skier sk = new Skier();
    CrossCountrySkier c = new CrossCountrySkier();
    MogulSkier m = new MogulSkier();
    Sledder sl = new Sledder();
    Bobsledder b = new Bobsledder();
    Luger l = new Luger();
    Curler cu = new Curler();
    System.out.println("Are they equal? "+((i.equals(f)==true)?"YES":"NO"));

    Curler curler = new Curler("curler",18,COLOR.RED);
    Curler curler1 = new Curler("curler",20,COLOR.BLUE);
    System.out.println("Are they equal? "+((curler.equals(curler1)==true)?"YES":"NO"));

    Curler curler2 = new Curler("curler",18,COLOR.RED);
    System.out.println("Are they equal? "+((curler.equals(curler2)==true)?"YES":"NO"));

    Sledder sledder = new Sledder("sledder",23,Color.PURPLE.toString());
    System.out.println("Are they equal? "+((sledder.equals(curler)==true)?"YES":"NO"));

    Luger luger
        = new Luger("sledder",23,Color.PURPLE.toString(),MixColor.PLUS.Mix(Color.ORANGE,Color.GREEN));
    System.out.println("Are they equal? "+((sledder.equals(luger)==true)?"YES":"NO"));
    System.out.println("The luger's FingerSpikesColor is "+luger.getFingerSpikesColor());

    MogulSkier mogulskier = new MogulSkier("mogulskier",23,150,100.0);
    SpeedSkater speedskater = new SpeedSkater("speedskater",23,150,100.0);
    System.out.println("Are they equal? "+((mogulskier.equals(speedskater)==true)?"YES":"NO"));

    Bobsledder bo = new Bobsledder(null,0,null,0);
    System.out.println("Are they equal? "+((bo.equals(b)==true)?"YES":"NO"));
    System.out.println("Are they equal? "+((bo.equals(bo)==true)?"YES":"NO"));
  }
}

enum Color{
  RED, ORANGE, YELLOW, GREEN,BLUE, PURPLE;
}
enum MixColor {
  PLUS {
    String Mix(Color x, Color y) {
      return x.toString()+y.toString();
    }
  },
  MINUS{
    String Mix(Color x, Color y) {
      return x.toString()+"-"+y.toString();
    }
  };
  abstract String Mix(Color x, Color y);
}
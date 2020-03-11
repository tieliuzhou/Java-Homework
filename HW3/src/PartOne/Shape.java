package PartOne;

public class Shape {
  private String color;
  private boolean filled;

  public Shape(){
    this.color = "green";
    this.filled = true;
  }
  public Shape(String color, boolean filled){
    this.color = color;
    this.filled = filled;
  }

  public String getColor(){
    return this.color;
  }
  public void setColor(String color){
    this.color = color;
  }
  public boolean isFilled(){
    return this.filled;
  }
  public void setFilled(boolean filled){
    this.filled = filled;
  }
  public String toString(){
    return "A Shape with color of "+this.color+" and "+(isFilled()?"filled":"Not filled");
  }

  public static void main(String[] args){//test
    Shape s = new Shape();
    System.out.println(s.toString());
    Shape s2 = new Shape("white",false);
    System.out.println(s2.toString());
    s2.setColor("blue");
    s2.setFilled(true);
    System.out.println("if the Shape is filled? " + s2.isFilled());
    System.out.println(s2.toString());
  }
}

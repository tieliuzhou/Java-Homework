package PartOne;

public class Square extends Rectangle {
  public Square(){
    super();
  }
  public Square(double side){
    super(side,side);
  }
  public Square(double side, String color, boolean filled){
    super(side,side,color,filled);
  }

  public double getSide(){
    return this.getLength();
  }
  public void setSide(double side){
    super.setLength(side);
    super.setWidth(side);
  }
  public void setWidth(double side){
    this.setSide(side);
  }
  public void setLength(double side){
    this.setSide(side);
  }
  public String toString(){
    return "A Square with side = "+this.getSide()+" ,which is a subclass of "+
        super.toString();
  }

  public static void main(String[] args){//test
    Square s = new Square(10,"blue",true);
    System.out.println(s.toString());
    s.setWidth(15);
    System.out.println(s.getSide());
    s.setLength(20);
    System.out.println(s.getSide());
    System.out.println("No, we don't need to override getArea() and getPerimeter()");
    System.out.println("Because the subclass inherit from the superclass Rectangle");
    System.out.println(",and the methods to calculate the area and perimeter "
        + "are the same as Rectangle");
    System.out.println("The Area is : "+s.getArea());
    System.out.println("The Perimeter is : "+s.getPerimeter());
  }
}

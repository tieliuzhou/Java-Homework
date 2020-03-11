package PartOne;

public class Rectangle extends Shape{
  private double width;
  private double length;

  public Rectangle(){
    this.width = 1.0;
    this.length = 1.0;
  }
  public Rectangle(double width, double length){
    this.width = width;
    this.length = length;
  }
  public Rectangle(double width, double length, String color, boolean filled){
    this.width = width;
    this.length = length;
    this.setColor(color);
    this.setFilled(filled);
  }

  public double getWidth(){
    return this.width;
  }
  public void setWidth(double width){
    this.width = width;
  }
  public double getLength(){
    return this.length;
  }
  public void setLength(double length){
    this.length = length;
  }
  public double getArea(){
    return this.width*this.length;
  }
  public double getPerimeter(){
    return 2*(this.width+this.length);
  }
  public String toString(){
    return "A Rectangle with width = "+this.width+" and length = "+this.length+
        " which is a subclass of "+super.toString();
  }

  public static void main(String[] args){//test
    Rectangle r = new Rectangle();
    Rectangle r1 = new Rectangle(4,5);
    Rectangle r2 = new Rectangle(40,50,"red",true);
    System.out.println(r.toString());
    System.out.println(r1.toString());
    System.out.println(r2.toString());
    System.out.println(r2.getLength());
    System.out.println(r2.getWidth());
    System.out.println(r2.getArea());
    System.out.println(r2.getPerimeter());
  }
}

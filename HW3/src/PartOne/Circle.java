package PartOne;

public class Circle extends Shape{
  private double radius;

  public Circle(){
    this.radius = 1.0;
  }
  public Circle(double radius){
    this.radius = radius;
  }
  public Circle(double radius,String color, boolean filled){
    this.radius = radius;
    this.setColor(color);
    this.setFilled(filled);
  }

  public double getRadius(){
    return radius;
  }
  public void setRadius(double radius) {
    this.radius = radius;
  }
  public double getArea(){
    return 4*Math.atan(1.0)*radius*radius;
  }
  public double getPerimeter(){
    return 2*4*Math.atan(1.0)*radius;
  }
  public String toString(){
    return "A Circle with radius = "+this.radius+", which is a subclass of "+super.toString();
  }

  public static void main(String[] args){//test
    Circle c = new Circle();
    System.out.println(c.toString());
    System.out.println(c.getRadius());
    System.out.println(c.getArea());
    System.out.println(c.getPerimeter());
    Circle c1 = new Circle(15);
    System.out.println(c1.toString());
    Circle c2 = new Circle(20,"black",true);
    System.out.println(c2.toString());
  }
}

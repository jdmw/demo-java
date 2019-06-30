package jd.demo.example.common.thingsbysubject.math.shape;

public class Circle implements Shape {

	private double radius ;
	public Circle(double radius) {
		this.radius = radius;
	}
	
	@Override
	public double area() {
		return 0;
	}
	public double getRadius() {
		return radius*radius*Math.PI;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}

}

package jd.demo.example.common.thingsbysubject.math.shape;

public class Rectangle implements Shape {

	public double width ;
    public double height ;
    
	@Override
	public double area() {
		return width * height;
	}

	public Rectangle(double width, double height) {
		this.width = width;
		this.height = height;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public void setHeight(double height) {
		this.height = height;
	}

}

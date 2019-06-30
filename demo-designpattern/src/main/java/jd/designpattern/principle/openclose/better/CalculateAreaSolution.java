package jd.designpattern.principle.openclose.better;

import jd.demo.example.common.thingsbysubject.math.shape.Circle;
import jd.demo.example.common.thingsbysubject.math.shape.Rectangle;
import jd.demo.example.common.thingsbysubject.math.shape.Shape;

public class CalculateAreaSolution {

	public static void main(String[] args) {
		Shape rect = new Rectangle(10,5);
		System.out.println("Area of Rectangle: " + rect.area());
		
		Shape cir = new Circle(5);
		System.out.println("Area of Circle: " + cir.area());
	}

}

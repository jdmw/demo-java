package jd.demo.example.common.thingsbysubject.math.shape;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Circle implements Shape {

	private double radius ;
	
	@Override
	public double area() {
		return radius*radius*Math.PI;
	}


}

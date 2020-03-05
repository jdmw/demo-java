package jd.demo.example.common.thingsbysubject.math.shape;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Rectangle implements Shape {

	public double width ;
    public double height ;
    
	@Override
	public double area() {
		return width * height;
	}

}

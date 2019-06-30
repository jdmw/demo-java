package jd.designpattern.principle.openclose.bad;

import java.awt.Rectangle;

public class CalculateAreaOrigin {

	public double Area(Rectangle shape)
    {
        return shape.getHeight() * shape.getWidth();
    }
}

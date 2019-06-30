package jd.designpattern.concurrent.immutable;

public final class Point {
	private final int x ;
	private final int y ;
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public String toString() {
		return String.format("(%d,%d)",x,y);
	}
}

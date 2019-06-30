package jd.designpattern.concurrent.immutable;

import jd.util.lang.concurrent.CcUt;

public class Map {

	//public List<Point> list = new ArrayList<>();
	
	public void move(Point point) {
		//list.add(list);
		System.out.format("[%s]\tmoves to %s \n",Thread.currentThread().getName(),point);
	}
	public static void main(String[] args) {
		Map map = new Map();
		CcUt.start(()->{
			int x = (int) (Math.random()*100) ;
			map.move(new Point(x,x));
		}, 100);
		
	}

}

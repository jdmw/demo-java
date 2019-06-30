package jd.demo.example.shop.pkg;

import jd.demo.example.shop.Goods;

/**
 * box goods
 * @author jdmw
 *
 */
public class Boxer {

	public Boxer() {
		System.out.println("making a boxer");
	}
	
	/**
	 * box goods
	 */
	public Box box(Goods goods) {
		System.out.println("boxing a goods with a boxer");
		return new Box(goods);
	}

}

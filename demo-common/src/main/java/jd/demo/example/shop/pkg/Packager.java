package jd.demo.example.shop.pkg;

import jd.demo.example.shop.Goods;

/**
 * package goods
 * @author jdmw
 *
 */
public class Packager {

	public static Box box(Goods goods) {
		System.out.println("boxing a goods with the packager");
		return new Box(goods);
	}
}

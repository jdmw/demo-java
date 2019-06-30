package jd.demo.example.shop.pkg;

import static java.lang.System.out;

import jd.demo.example.shop.Goods;

public class Box {
	private Goods goods ;

	public Box(String goodsName) {
		out.println("making a box and package the goods:" + goodsName);
		goods = new Goods(goodsName);
	}

	public Box(String goodsName,int goodsNum) {
		goods = new Goods(goodsName);
		goods.setNum(goodsNum);
		out.println("making a box and package the goods:" + goodsName + "," + goodsNum);
	}
	
	public Box(Goods goods) {
		out.println("making a box and package the goods:" + goods);
		this.goods = goods;
	}
	
	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		out.println("package the goods:" + goods);
		this.goods = goods;
	}
}

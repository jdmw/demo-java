package jd.designpattern.dict.topdesign.structure.microkernel.ext.impl;

import jd.designpattern.dict.topdesign.structure.microkernel.ext.bean.Food;

public class Store extends AbstractFoodStock{

	public void addGoods(String name,int num,double price){
		Food food = super.stockIn(name, num);
		food.setPrice(price);
		food.setStoreLocation("James' Glosary");
		System.out.println("add " + num + " of " + name + " into " + food.getStoreLocation() + "\n" + food.toString());
	}
	
}

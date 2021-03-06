package jd.designpattern.dict.topdesign.structure.microkernel.ext.impl;

import jd.designpattern.dict.topdesign.structure.microkernel.ext.bean.Food;

public class HomeStock extends AbstractFoodStock{

	public void storeFood(String name,int num){
		Food food = super.stockIn(name, num);
		food.setStoreLocation("HOME");
		System.out.println("add " + num + " of " + name + " into " + food.getStoreLocation() + "\n" + food.toString());
	}

}

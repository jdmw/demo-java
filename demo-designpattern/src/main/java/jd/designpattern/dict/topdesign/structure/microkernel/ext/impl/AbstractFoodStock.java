package jd.designpattern.dict.topdesign.structure.microkernel.ext.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import jd.designpattern.dict.topdesign.structure.microkernel.ext.GetFoods;
import jd.designpattern.dict.topdesign.structure.microkernel.ext.bean.Food;

public abstract class AbstractFoodStock implements GetFoods {

	private Map<String,Food> stock = new LinkedHashMap<>();

	
	protected Food stockIn(String name,int num){
		Food food = stock.get(name);
		if(food == null){
			food = new Food(name,num);
			stock.put(name, food);
		}else{
			food.addNum(num);
		}
		return food;
	}
	
	private boolean checkNum(Food food,int num){
		if(food != null){
			return food.getNum() >= num;
		}
		return false;
	}
	
	protected Food stockOut(String name,int num){
		Food food = stock.get(name);
		if(food != null && checkNum(food,num)){
			food.decNum(num);
			System.out.println("Take " + num + " of " + name + " out of " + food.getStoreLocation() + "\n" + food.toString());
			return food ;
		}
		return null;
	}
	
	
	@Override
	public Food get(String name) {
		Food food =  stockOut(name, 1);
		return food ;
	}
}

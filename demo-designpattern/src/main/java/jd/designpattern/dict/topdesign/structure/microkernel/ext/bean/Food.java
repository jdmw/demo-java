package jd.designpattern.dict.topdesign.structure.microkernel.ext.bean;

import java.util.LinkedHashMap;

public class Food extends LinkedHashMap<String,Object> {

	private String name;
	private String kind;
	private int num ;
	private double price ;
	private String storeLocation ;
	
	public String toString(){
		return    "------ Food Info -------" 
				+ "\n name           :\t" + name
				+ (kind!=null?
				  "\n kind           :\t"+kind:"")
				+ "\n reserved number:\t" + num 
				+ (price>0?
				  "\n price          :\t"+price:"")
				+ (storeLocation!=null?
				  "\n from           :\t"+storeLocation:"")
				+ "\n-------------------------\n";
	}
	
	public Food(String name) {
		this.name = name;
		this.num = 1 ;
	}
	
	public Food(String name,  int num) {
		this.name = name;
		this.num = num ;
	}
	
	public Food(String name, int num, double price) {
		this.name = name;
		this.num = num;
		this.price = price;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getName() {
		return name;
	}

	public int getNum() {
		return num;
	}

	public double getPrice() {
		return price;
	}

	public String getStoreLocation() {
		return storeLocation;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public void addNum(int add) {
		this.num += add;
	}
	
	public void decNum(int dec) {
		this.num -= dec;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}

	public void setStoreLocation(String storeLocation) {
		this.storeLocation = storeLocation;
	}
}

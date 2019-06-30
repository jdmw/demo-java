package jd.demo.example.shop;

import static java.lang.System.out;

public class Goods {

	private String name ;
	private double price ; // sell price
	
	private int num ;
	private double purchasePrice ;
	private String description;
	
	public Goods() {
		out.println("making goods:");
	}
	
	public Goods(String name) {
		this.name = name;
		out.println("making goods[" + name + "]");
	}
	
	public Goods(String name, double price) {
		this.name = name;
		this.price = price;
		out.println("making goods[" + name + "," + price+"]");
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		out.println("set the goods's name:" + name);
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public int getNum() {
		return num;
	}
	public double getPurchasePrice() {
		return purchasePrice;
	}
	public String getDescription() {
		return description;
	}
	public void setPrice(double price) {
		out.println("set the goods's price:" + price);
		this.price = price;
	}
	public void setNum(int num) {
		out.println("set the goods's number:" + num);
		this.num = num;
	}
	public void setPurchasePrice(double purchasePrice) {
		out.println("set the goods's purchase price:" + purchasePrice);
		this.purchasePrice = purchasePrice;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String toString() {
		return "Goods[name="+ name 
				+ (num>0 			? (",number=" + num)					:"")
				+ (price>0 			? (",sell price=" + price  )			:"")
				+ (purchasePrice>0 	? (",purchas ePrice=" + purchasePrice)	:"")
				+ "][hashcode:" +this.hashCode()+"]" ;
	}
}

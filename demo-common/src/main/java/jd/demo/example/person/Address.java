package jd.demo.example.person;

public class Address {

	String country ;
	String province ;
	String district ;
	String city ;
	String detail ;
	int postcode ;
	
	public Address(String province, String district, String city, String detail) {
		this.province = province;
		this.district = district;
		this.city = city;
		this.detail = detail;
	}
	
	public Address(String country, String province, String district, String city, String detail) {
		this.country = country;
		this.province = province;
		this.district = district;
		this.city = city;
		this.detail = detail;
	}
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public int getPostcode() {
		return postcode;
	}
	public void setPostcode(int postcode) {
		this.postcode = postcode;
	}
	
	public String toString() {
		return "["+country+"]"+province+district+city+detail+"|"
				+ (postcode>0?postcode:"");
	}
}

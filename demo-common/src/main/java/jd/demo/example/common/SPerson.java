package jd.demo.example.common;

import java.util.Arrays;
import java.util.List;

public class SPerson {

	public static enum Sex { MALE,FEMALE } ;
	
	private String name ;	
	private Sex gender ;
	private int age ;
	private String city ;
	private String email ;
	private String phone ;
	
	public SPerson() {}
	
	public SPerson(String name, Sex gender, int age) {
		this.name = name;
		this.gender = gender;
		this.age = age;
	}
	
	public SPerson(String name, Sex gender, int age,String city) {
		this.name = name;
		this.gender = gender;
		this.age = age;
		this.city = city ;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Sex getGender() {
		return gender;
	}
	public void setGender(Sex gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public static  List<SPerson> demoList() {
		 return Arrays.asList(
					new SPerson("Tom",		Sex.MALE,	21,"London"),
					new SPerson("Taha",		Sex.MALE,	22,"London"),
					new SPerson("Hassan",	Sex.MALE,	23,"London"),
					new SPerson("Jake",		Sex.MALE,	24,"London"),
					new SPerson("Peter",		Sex.MALE,	25,"New York"),
					new SPerson("Junior",	Sex.MALE,	26,"New York"),
					new SPerson("Iran",		Sex.MALE,	27,"New York"),
					new SPerson("Jackson",	Sex.MALE,	28,"Sydney"),
					new SPerson("James",		Sex.MALE,	29,"Sydney"),
					new SPerson("Justin",	Sex.MALE,	30,"Sydney"),
					new SPerson("Oliva",		Sex.FEMALE,	21,"New York"),
					new SPerson("Alice",		Sex.FEMALE,	22,"New York"),
					new SPerson("Emma",		Sex.FEMALE,	23,"New York"),
					new SPerson("Sofia",		Sex.FEMALE,	24,"Moscow"),
					new SPerson("Maria",		Sex.FEMALE,	25,"Moscow"),
					new SPerson("Mary",		Sex.FEMALE,	26,"Moscow"),
					new SPerson("Mia",		Sex.FEMALE,	27,"Moscow"),
					new SPerson("Lily",		Sex.FEMALE,	28,"Moscow"),
					new SPerson("Jennifer",	Sex.FEMALE,	29,"Moscow"),
					new SPerson("Victoria",	Sex.FEMALE,	30,"Moscow")
		) ;
	}
}

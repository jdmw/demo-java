package jd.demo.example.person;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PersonInfo {

	public static enum Sex{Male,Female}
	
	private String name;
	private Sex sex ;
	private Date birthday ;
	private Address homeAddr;
	private Address workAddr;
	private String phone ;
	
	private List<PersonInfo> friends = new ArrayList<>();

	public PersonInfo(String name) {
		this.name = name;
	}

	public PersonInfo(String name, Sex sex) {
		this.name = name;
		this.sex = sex;
	}
	
	public PersonInfo(String name, Sex sex, Date birthday) {
		this.name = name;
		this.sex = sex;
		this.birthday = birthday;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Address getHomeAddr() {
		return homeAddr;
	}

	public void setHomeAddr(Address home) {
		this.homeAddr = home;
	}

	public Address getWorkAddr() {
		return workAddr;
	}

	public void setWorkAddr(Address work) {
		this.workAddr = work;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<PersonInfo> getFriends() {
		return friends;
	}

	public void setFriends(List<PersonInfo> friends) {
		this.friends = friends;
	}
	
	public void addFriend(PersonInfo friend) {
		if(null == this.friends) {
			this.friends = new ArrayList<>();
		}
		this.friends.add(friend);
	}
	
	public void addFriends(PersonInfo ... friends) {
		if(null == this.friends) {
			this.friends = new ArrayList<>();
		}
		for (PersonInfo person : friends) {
			this.friends.add(person);
		}
	}

}

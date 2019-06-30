package jd.demo.jodd.json;

import jd.demo.example.person.Address;
import jd.demo.example.person.Person;
import jd.demo.example.person.Person.Sex;
import jodd.json.JsonSerializer;

public class JoddJsonFilter1 {

	public static void main(String[] args) {
		Person person = new Person("Tom",Sex.Male);
		person.setHomeAddr(new Address("上海市", "上海市", "黄浦区" , "云南路81号"));
		person.setWorkAddr(new Address("上海市", "上海市", "浦东新区", "金桥路123号"));
		person.addFriends(new Person("katty"),new Person("Buff"));
		JsonSerializer jsonSerializer = new JsonSerializer();
		String json = jsonSerializer
				//.exclude("*")
				.include("name")
				.include("homeAddr")
				.exclude("homeAddr.country")
				.exclude("homeAddr.postcode")
				.include("friends")
				.exclude("friends.*")
				.include("friends.name")
				.serialize(person);
		System.out.println(json);

		
	}

}

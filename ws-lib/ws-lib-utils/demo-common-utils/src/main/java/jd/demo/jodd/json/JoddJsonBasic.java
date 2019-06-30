package jd.demo.jodd.json;

import jd.demo.example.person.Address;
import jd.demo.example.person.Person;
import jodd.json.JsonSerializer;

public class JoddJsonBasic {

	public static void main(String[] args) {
        Person person = new Person("Tom");
        person.setHomeAddr(new Address("上海市","上海市","静安区","共和新路318"));
        JsonSerializer jsonSerializer = new JsonSerializer();
        String json = jsonSerializer.serialize(person);
        System.out.println(json); 
        
        
        json = jsonSerializer.deep(true).serialize(person);
        System.out.println(json); 
	}

}

package jd.demo.jodd.json;

import jd.demo.example.common.Book;
import jodd.json.JsonParser;
import jodd.json.JsonSerializer;

public class JoddJsonDeep {

	public static void main(String[] args) {
		Book book = new Book();
		book.setNo(120);  
        book.setAuthor("David");  
        book.setName("java android");  
        
	    String json = new JsonSerializer()
	            .include("authors")
	            .serialize(book);
	    System.out.println(json);  
	    
	    
	    Book book2 = new JsonParser()
	            .parse(json, Book.class);
        System.out.println(book2); 

	}

}

package jd.demo.example.common;

public class Book {

	private Integer no;  
    private String name;  
    private String author;  
    public Book(Integer no, String name, String author) {  
        this.no = no;  
        this.name = name;  
        this.author = author;  
    }  
    public Book(){  
          
    }  
  
      
    public Integer getNo() {  
        return no;  
    }  
    public void setNo(Integer no) {  
        this.no = no;  
    }  
    public String getName() {  
        return name;  
    }  
    public void setName(String name) {  
        this.name = name;  
    }  
    public String getAuthor() {  
        return author;  
    }  
    
    public void setAuthor(String author) {  
        this.author = author;  
    }  
    @Override  
    public String toString() {  
        return "Book [author=" + author + ", name=" + name + ", no=" + no + "]";  
    }  
}

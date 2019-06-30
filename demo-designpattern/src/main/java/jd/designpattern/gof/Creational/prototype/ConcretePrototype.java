package jd.designpattern.gof.Creational.prototype;

public class ConcretePrototype implements Cloneable {

    private String name ;
    public ConcretePrototype(String name){
        this.name = name ;
    }
    public ConcretePrototype clone(){
        return new ConcretePrototype(this.name);
    }

    public static void main(String[] args){
        ConcretePrototype prototype = new ConcretePrototype("Room");
        Cloneable obj = prototype.clone();
        System.out.println("new object's name is " + ((ConcretePrototype) obj).name);
    }
}


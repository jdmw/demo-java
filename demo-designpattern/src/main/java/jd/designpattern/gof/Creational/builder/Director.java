package jd.designpattern.gof.Creational.builder;

public class Director {

    //Builder builder = new ConcreteBuilder();

    public Director(Builder builder){
        builder.builderPartA();
        builder.builderPartB();
        builder.builderPartC();
    }
}

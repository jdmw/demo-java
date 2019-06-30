package jd.demo.example.service.impl;

import jd.demo.example.service.Greeting;

public class GreetingImpl implements Greeting {
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
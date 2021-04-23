package jd.demo.se.collections.list;

import jd.demo.example.person.PersonInfo;

import java.util.*;

/**
 * Created by huangxia on 2008/4/13.
 */
public class DemoListSort {


    private static List<Users> createUsers()
    {
        return Arrays.asList(
                new Users(12311, "ME"),
                new Users(10211, "CSE"),
                new Users(33111, "CSE"),
                new Users(21000, "IT"),
                new Users(12133, "IT"),
                new Users(21445, "CSE"));
    }


    private static class Users extends PersonInfo {
        public Users(int id,String name) {
            super(name);
            setId(id);
        }
        public String toString(){
            return getId() + "-" +  getName() ;
        }
    }


    public static void main(String[] args){
        List<Boolean> bs = new ArrayList<>(Arrays.asList(true,false,true));
        bs.sort((Comparator<Boolean>)Comparator.naturalOrder().reversed());

        List<Integer> is = new ArrayList<>(Arrays.asList(1,4,3));
        is.sort((Comparator<Integer>)Comparator.naturalOrder().reversed());
        System.out.println(bs);
        System.out.println(is);

        List<Users> list = createUsers();
        System.out.printf("before sort: %s%n", list);
        list.sort(Comparator.comparing(Users::getName).thenComparingLong(PersonInfo::getId));
        System.out.printf("after sort: %s%n", list);
    }
}

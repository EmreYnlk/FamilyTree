package com.example.familytree;

import java.util.LinkedList;

public class human {
    String name;
    String surname;
    int age;
    LinkedList<human> childlist;

    public human(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }
    public void add_child(human child){
        childlist.add(child);
    }
}

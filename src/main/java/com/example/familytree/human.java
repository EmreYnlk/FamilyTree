package com.example.familytree;

import java.util.LinkedList;

public class human {
    String name;
    String surname;
    int bornyear;
    LinkedList<human> childlist ;                 // kişinin kendi çocuklarını tutcak

    public human(String name, String surname, int bornyear) {
        this.name = name;
        this.surname = surname;
        this.bornyear = bornyear;
        this.childlist = new LinkedList<>();
    }
    public void addChild(human child){
        childlist.add(child);
    }

    public String getFullName() {
        return name + " " + surname;
    }


    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}

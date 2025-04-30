package com.example.familytree;

import java.util.LinkedList;

public class human {
    String name;
    String surname;
    char cinsiyet;
    int bornyear;
    LinkedList<human> childlist ;                 // kişinin kendi çocuklarını tutcak

    public human(String name, String surname, int bornyear,char cinsiyet) {
        this.name = name;
        this.surname = surname;
        this.bornyear = bornyear;
        this.cinsiyet=cinsiyet;
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

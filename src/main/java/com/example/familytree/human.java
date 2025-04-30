package com.example.familytree;

import java.util.LinkedList;

public class human {
    String name;
    String surname;
    char cinsiyet;
    int bornyear;
    human parent;
    human partner;
    int personalid;
    LinkedList<human> childlist ;                 // kişinin kendi çocuklarını tutcak
    static int idCount= 0;

    public human(String name, String surname, int bornyear,char cinsiyet) {
        this.name = name;
        this.surname = surname;
        this.bornyear = bornyear;
        this.cinsiyet=cinsiyet;
        this.partner=null;
        this.parent=null;
        this.personalid = idCount++;
        this.childlist = new LinkedList<>();
    }
    public void addChild(human child){
        childlist.add(child);
        child.parent=this;
    }

    public String getFullName() {
        if (partner != null) {
            return name + " & " + partner.name + surname;
        } else {
            return this.name;
        }
    }


}

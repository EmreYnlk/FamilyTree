package com.example.familytree;

import java.util.LinkedList;

public class human {
    String name;
    String surname;
    char cinsiyet;
    String bornyear;                          //  GG-AA-YYYY şeklinde tutulacak
    human parent;
    human partner;
    LinkedList<human> childlist ;                 // kişinin kendi çocuklarını tutcak

    public human(String name, String surname, String bornyear,char cinsiyet) {
        this.name = name;
        this.surname = surname;
        this.bornyear = bornyear;
        this.cinsiyet=cinsiyet;
        this.partner=null;
        this.parent=null;
        this.childlist = new LinkedList<>();
    }
    public void addChild(human child){
        childlist.add(child);
        child.parent=this;
    }


//******************************(Getter and Setter)******************************//

    public String getFullNameWithPartner() {
        if (partner != null) {
            return name + "&" + partner.name +" "+ surname;
        } else {
            return name+ " " + surname;
        }
    }
    public String getFullNameWithoutPartner(){

        return name + " " + surname;

    }
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public char getCinsiyet() {
        return cinsiyet;
    }

    public String getBornyear() {
        return bornyear;
    }

    public human getParent() {
        return parent;
    }

    public human getPartner() {
        return partner;
    }

    public LinkedList<human> getChildlist() {
        return childlist;
    }


    //******************************(Getter and Setter)******************************//




}

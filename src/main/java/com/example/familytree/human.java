package com.example.familytree;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class human {
    double personalid;
    String name;
    String surname;
    char cinsiyet;                            // E veya K
    String bornyear;                          //  GG-AA-YYYY şeklinde tutulacak
    human partner;
    human parent;
    MyLinkedList<human> childlist ;                 // kişinin kendi çocuklarını tutcak
    
    public human(String name, String surname, String bornyear,char cinsiyet) {
        this.personalid = readandwriteFromTxT();
        this.name = name;
        this.surname = surname;
        this.bornyear = bornyear;
        this.cinsiyet=cinsiyet;
        this.partner=null;
        this.parent=null;
        this.childlist = new MyLinkedList<>();
    }
    public static int readandwriteFromTxT(){
        int sayi=0;
        String txtFilePath = "src/main/java/TxtFile/lastpersonalid.txt";
        try {
            String line = Files.readAllLines(Paths.get(txtFilePath)).get(0);
            sayi = Integer.parseInt(line.trim());
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        sayi++;     // bu satır personalid her zaman aynı olmasın diye
        try (FileWriter writer = new FileWriter(txtFilePath)) {
            writer.write(String.valueOf(sayi));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sayi;
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
    @Override
    public String toString(){
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

    public MyLinkedList<human> getChildlist() {
        return childlist;
    }


    //******************************(Getter and Setter)******************************//




}

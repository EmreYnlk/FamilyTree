package com.example.familytree;

import java.util.LinkedList;

public class FamilyTree {
    String familytreename;
    human root;
    static LinkedList<FamilyTree> allfamilytree;

    public FamilyTree(String familytreename) {
        this.familytreename = familytreename;
        allfamilytree.add(this);
    }

    void findtreelenght(){}          // tablo oluştururken işimize yaricak
    void findmaxchild(){}            // tablo oluştururken işimize yaricak
    void findleef(){}               //yaprakları bul
    void finddedscendant(){}        //torun bul
    void findancestor(){}           // dedeler bul
    void search(){}

}

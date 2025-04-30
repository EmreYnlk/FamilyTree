package com.example.familytree;

import java.util.LinkedList;

public class FamilyTree {
    String familytreename;
    human root;
    private static LinkedList<FamilyTree> allfamilytree = new LinkedList<>();

    public static boolean isFamilyNameinList(String familyname) {
        for (FamilyTree tree : allfamilytree) {
            if (tree.familytreename.equals(familyname)) {
                return true;
            }
        }
        return false;
    }



//------------------------------constructors-------------------------------//
    public FamilyTree(String familytreename) {
        this.familytreename = familytreename;
        allfamilytree.add(this);}
    public FamilyTree(String familytreename,human root){
        this.familytreename = familytreename;
        allfamilytree.add(this);
        this.root = root;}
//------------------------------constructors-------------------------------//

    //bunlar burda mı duracak yoksa controller da mı emin değilim
    void findleef(){}               //yaprakları bul
    void findlineage(){}            //üst soy
    void findsubtree(){}           // alt soy






//******************************(Getter and Setter)******************************//
public static human getRoot(String familyname) {
    for (FamilyTree tree : allfamilytree) {
        if (tree.familytreename.equals(familyname)) {
            return tree.root;
        }
    }
    return null;
}
//******************************(Getter and Setter)******************************//
}

package com.example.familytree;


public class FamilyTree {
    String familytreename;
    human root;
    private static MyLinkedList<FamilyTree> allfamilytree = new MyLinkedList<>();

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




    //******************************(Getter and Setter)******************************//
    public static human getRoot(String familyname) {
        for (FamilyTree tree : allfamilytree) {
            if (tree.familytreename.equals(familyname)) {
                return tree.root;
            }
        }
        return null;
    }

    public static void setRoot(String familyname,human human1){
        for (FamilyTree tree : allfamilytree) {
            if (tree.familytreename.equals(familyname)) {
                tree.root=human1;

            }
        }
    }
    //******************************(Getter and Setter)******************************//
}

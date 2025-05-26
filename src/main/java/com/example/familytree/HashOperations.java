package com.example.familytree;

public class HashOperations {
    private MyLinkedListSpecial[] bagliListeDizisi;
    private int boyut;

    public HashOperations(int boyut) {
        this.boyut = boyut;
        bagliListeDizisi = new MyLinkedListSpecial[boyut];
        for (int i = 0; i < boyut; i++) {
            bagliListeDizisi[i] = new MyLinkedListSpecial();
        }
    }

    private int hash(int veri) {
        return veri % boyut;
    }

    public void ekle(human kisi) {
        int index = hash((int) kisi.personalid);
        bagliListeDizisi[index].ekle(kisi.personalid, kisi);
    }

    public human ara(double personalid) {
        int index = hash((int) personalid);
        return bagliListeDizisi[index].ara(personalid);
    }


    public boolean aravarmi(double veri) {
    int index = hash((int) veri);
    Hash_Node current = bagliListeDizisi[index].head;

    while (current != null) {
        if (current.veri == veri) {
            return true;
        }
        current = current.next;
    }

    return false;
}}


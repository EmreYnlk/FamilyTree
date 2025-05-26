package com.example.familytree;

public class MyLinkedListSpecial {
    Hash_Node head;

    public void ekle(double veri, human kisi) {
        Hash_Node yeni = new Hash_Node(veri, kisi);
        yeni.next = head;
        head = yeni;
    }

    public human ara(double veri) {
        Hash_Node current = head;
        while (current != null) {
            if (current.veri == veri) {
                return current.kisi;
            }
            current = current.next;
        }
        return null;
    }
}

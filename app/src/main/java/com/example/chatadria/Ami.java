package com.example.chatadria;

public class Ami {
    public String id;
    public String nom;
    public String prenom;
    public long Téléphone;

    public Ami() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public long getTéléphone() {
        return Téléphone;
    }

    public void setTéléphone(long téléphone) {
        Téléphone = téléphone;
    }

    public Ami(String id, String nom, String prenom, long téléphone) {
        this.id=id;
        this.nom = nom;
        this.prenom = prenom;
        Téléphone = téléphone;
    }
}

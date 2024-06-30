package com.upcycling;


import java.io.Serializable;

public class Produits implements Serializable {
    private boolean showCheckBox;
    private String nom;
    private String marque;
    private String score;

    public Produits(String nom, String marque, String score) {
        this.nom = nom;
        this.marque = marque;
        this.score = score;
    }

    public boolean isShowCheckBox() {
        return showCheckBox;
    }

    public void setShowCheckBox(boolean showCheckBox) {
        this.showCheckBox = showCheckBox;
    }

    public String getNom() {
        return nom;
    }

    public String getMarque() {
        return marque;
    }

    public String getScore() {
        return score;
    }
}
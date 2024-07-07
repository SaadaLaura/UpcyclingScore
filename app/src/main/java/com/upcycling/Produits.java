package com.upcycling;


import java.io.Serializable;

public class Produits implements Serializable {
    private boolean showCheckBox;
    private String nom;
    private String emballage;
    private int score;
    private boolean isChecked = false;

    public Produits(String nom, String emballage, int score) {
        this.nom = nom;
        this.emballage = emballage;
        this.score = score;
    }
    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        this.isChecked = checked;
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

    public String getEmballage() {
        return emballage;
    }

    public int getScore() {
        return score;
    }
    public int getScoreColor() {
        if (score >= 15) {
            return R.color.green; // Vert pour les scores >= 15
        } else if (score >= 10) {
            return R.color.orange; // Orange pour les scores entre 10 et 14
        } else {
            return R.color.red; // Rouge pour les scores < 10
        }
    }

}
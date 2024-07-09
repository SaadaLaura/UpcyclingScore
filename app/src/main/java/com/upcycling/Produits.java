package com.upcycling;


import java.io.Serializable;

public class Produits implements Serializable {
    private boolean showCheckBox;
    private boolean isChecked = false;
    private long barcode;
    private String name;
    private int score;
    private String urlImage;
    private Packaging[] packagings;


    public Produits(long barcode, String name, int score, String urlImage, Packaging[] packagings) {
        this.barcode = barcode;
        this.name = name;
        this.score = score;
        this.urlImage = urlImage;
        this.packagings = packagings;
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
    public long getBarcode() {
        return barcode;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public Packaging[] getPackagings() {
        return packagings;
    }
}
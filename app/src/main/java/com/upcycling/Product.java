package com.upcycling;

import java.io.Serializable;

public class Product implements Serializable {
    private boolean showCheckBox = false;
    private boolean isChecked = false;
    private long barcode;
    private String name;
    private int score;
    private String urlImage;
    private Packaging[] packagings;

    public Product(long barcode, String name, int score, String urlImage, Packaging[] packagings) {
        this.barcode = barcode;
        this.name = name;
        this.score = score;
        this.urlImage = urlImage;
        this.packagings = packagings;
    }

    public int getScoreColor() {
        if (this.score >= 15) {
            return R.color.green; // Vert pour les scores >= 15
        } else if (this.score >= 10) {
            return R.color.orange; // Orange pour les scores entre 10 et 14
        } else {
            return R.color.red; // Rouge pour les scores < 10
        }
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

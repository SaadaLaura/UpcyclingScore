package com.upcycling;

public class Product {
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

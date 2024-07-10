package com.upcycling;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "product")
public class ProductHistory {
    @PrimaryKey
    public int barcode;
    public String name;
    public float score;
    public String imageUrl;
    public String packagings;

    public ProductHistory () {}

    public ProductHistory(int barcode,
                          String name,
                          float score,
                          String imageUrl,
                          String packagings) {
        this.barcode = barcode;
        this.name = name;
        this.score = score;
        this.imageUrl = imageUrl;
        this.packagings = packagings;
    }
}


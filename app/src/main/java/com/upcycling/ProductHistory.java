package com.upcycling;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "product")
public class ProductHistory {
    @PrimaryKey
    public long barcode;
    public String name;
    public float score;
    public String imageUrl;
    public String packagings;

    public ProductHistory () {}

    public ProductHistory(long barcode,
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


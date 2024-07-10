package com.upcycling;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HistoryRequests {
    @Query("SELECT * FROM product")
    List<ProductHistory> getAll();

    @Query("SELECT * FROM product WHERE barcode IN (:barcodes)")
    List<ProductHistory> getByBarcodes(int[] barcodes);

    @Query("SELECT barcode FROM product")
    List<Integer> getBarcodes();

    @Insert
    void insertAll(ProductHistory... products);

    @Query("DELETE FROM product where barcode = :productBarcode")
    void delete(int productBarcode);

    @Query("DELETE from product")
    void clear();
}

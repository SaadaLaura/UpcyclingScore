package com.upcycling;

public class Packaging {
    private String packagingType;
    private int quantity;
    private ReuseIdea[] reuseIdeas;

    public Packaging(String packagingType, int quantity, ReuseIdea[] reuseIdeas) {
        this.packagingType = packagingType;
        this.quantity = quantity;
        this.reuseIdeas = reuseIdeas;
    }

    public String getPackagingType() {
        return packagingType;
    }

    public int getQuantity() {
        return quantity;
    }

    public ReuseIdea[] getReuseIdeas() {
        return reuseIdeas;
    }

    @Override
    public String toString() {
        return this.packagingType;
    }
}

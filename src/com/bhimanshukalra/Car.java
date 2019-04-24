package com.bhimanshukalra;

abstract public class Car {
    protected int ID;
    protected String model;
    protected int price;
    protected int resaleValue;

    public Car(int ID, String model, int price) {
        this.ID=ID;
        this.model=model;
        this.price=price;
    }

    abstract int calculateResaleValue(int price);

    public String getModel() {
        return model;
    }

    public int getPrice() {
        return price;
    }

    public int getResaleValue() {
        return resaleValue;
    }
}
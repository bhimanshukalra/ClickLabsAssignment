package com.bhimanshukalra;

abstract public class Car {
    protected int Id;
    protected String model;
    protected int price;
    protected int resaleValue;

    public Car(int Id, String model, int price) {
        this.Id=Id;
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
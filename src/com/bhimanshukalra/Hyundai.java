package com.bhimanshukalra;

public class Hyundai extends Car{

    public Hyundai(int Id, String model, int price){
        super(Id, model, price);
        this.resaleValue=calculateResaleValue(price);
    }

    int calculateResaleValue(int price){
        return (int)(price*0.4);
    }
}
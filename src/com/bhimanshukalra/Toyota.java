package com.bhimanshukalra;

public class Toyota extends Car{

    public Toyota(int Id, String model, int price){
        super(Id, model, price);
        this.resaleValue=calculateResaleValue(price);
    }

    int calculateResaleValue(int price){
        return (int)(price*0.8);
    }
}
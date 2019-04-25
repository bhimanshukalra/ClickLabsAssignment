package com.bhimanshukalra;

public class Maruti extends Car{

    public Maruti(int Id, String model, int price){
        super(Id, model, price);
        this.resaleValue=calculateResaleValue(price);
    }

    int calculateResaleValue(int price){
        return (int)(price*0.6);
    }
}
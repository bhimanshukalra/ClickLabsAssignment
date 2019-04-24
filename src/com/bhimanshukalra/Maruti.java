package com.bhimanshukalra;

public class Maruti extends Car{

    public Maruti(int ID, String model, int price){
        super(ID, model, price);
        this.resaleValue=calculateResaleValue(price);
    }

    int calculateResaleValue(int price){
        return (int)(price*0.6);
    }
}
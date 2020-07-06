package com.example.shop.Data.Converter;

public class EuroConverter {

    public static String  convertToEuro(int value){
        int euro = value/100;
        int cent = value%100;
        String centString;
        if (cent == 0) centString = "00";
        else centString = String.valueOf(cent);
        return euro +","+centString+"€";
    }
}

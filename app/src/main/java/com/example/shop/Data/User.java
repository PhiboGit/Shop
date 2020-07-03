package com.example.shop.Data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Benutzer")
public class User {

    @PrimaryKey
    private int personalNummer;

    private String name = "";


    private int c30, c40, c50, c60, c80, c100, c120;

    public User(int personalNummer, String name) {
        this.personalNummer = personalNummer;
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPersonalNummer() {
        return personalNummer;
    }

    public int getC30() {
        return c30;
    }

    public void setC30(int c30) {
        this.c30 = c30;
    }

    public int getC40() {
        return c40;
    }

    public void setC40(int c40) {
        this.c40 = c40;
    }

    public int getC50() {
        return c50;
    }

    public void setC50(int c50) {
        this.c50 = c50;
    }

    public int getC60() {
        return c60;
    }

    public void setC60(int c60) {
        this.c60 = c60;
    }

    public int getC80() {
        return c80;
    }

    public void setC80(int c80) {
        this.c80 = c80;
    }

    public int getC100() {
        return c100;
    }

    public void setC100(int c100) {
        this.c100 = c100;
    }

    public int getC120() {
        return c120;
    }

    public void setC120(int c120) {
        this.c120 = c120;
    }
}

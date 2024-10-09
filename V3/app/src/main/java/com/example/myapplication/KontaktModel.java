package com.example.myapplication;

public class KontaktModel{ //model za kontakt
    public String ime;
    public String prezime;
    public String telefon;

    public String skype;

    public KontaktModel(String ime, String prezime, String telefon, String skype) {
        this.ime = ime;
        this.prezime = prezime;
        this.telefon = telefon;
        this.skype = skype;
    }
}

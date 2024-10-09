package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class IzmenaDodaj extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_izmena_dodaj);

        Intent intent = getIntent();
        Button dodajIzmeni = findViewById(R.id.btnDodajIzmeni);
        EditText imeEditText = findViewById(R.id.imeEditText);
        EditText prezimeEditText = findViewById(R.id.prezimeEditText);
        EditText telefonEditText = findViewById(R.id.phoneEditText);
        EditText skypeEditText = findViewById(R.id.skypeEditText);
        TextView porukaTextView = findViewById(R.id.porukaTextView);

        if(intent.getStringExtra("isIzmenaOrDodaj").equals("izmena")){//ako se radi izmena
            dodajIzmeni.setText("Izmeni");
            imeEditText.setText(intent.getStringExtra("ime"));
            prezimeEditText.setText(intent.getStringExtra("prezime"));
            telefonEditText.setText(intent.getStringExtra("telefon"));  //setujemo text od input polja na vrednosti kontakta
            skypeEditText.setText(intent.getStringExtra("skype"));
            Intent noviIntent = new Intent(this,MainActivity.class);
            dodajIzmeni.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {//validacija da li su sva polja uneta
                    if(imeEditText.getText().toString().isEmpty() || prezimeEditText.getText().toString().isEmpty() || telefonEditText.getText().toString().isEmpty() || skypeEditText.getText().toString().isEmpty())
                    {
                        porukaTextView.setText("Niste uneli sva polja, molimo popunite sva polja! ");
                        porukaTextView.setTextColor(Color.RED);
                    }
                    else {
                        porukaTextView.setText("");
                        noviIntent.putExtra("ime", imeEditText.getText().toString());
                        noviIntent.putExtra("prezime", prezimeEditText.getText().toString());
                        noviIntent.putExtra("telefon", telefonEditText.getText().toString());//izmenjenje podatke vracamo main activity
                        noviIntent.putExtra("skype", skypeEditText.getText().toString());
                        noviIntent.putExtra("index", intent.getIntExtra("index", 0));
                        noviIntent.putExtra("isIzmenaOrDodaj", "izmena");
                        startActivity(noviIntent);
                    }
                }
            });
        }else if(intent.getStringExtra("isIzmenaOrDodaj").equals("dodaj")){ //ako se radi dodavanje
            dodajIzmeni.setText("Dodaj");
            Intent noviIntent = new Intent(this,MainActivity.class);
            dodajIzmeni.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {//validacija
                    if(imeEditText.getText().toString().isEmpty() || prezimeEditText.getText().toString().isEmpty() || telefonEditText.getText().toString().isEmpty() || skypeEditText.getText().toString().isEmpty())
                    {
                        porukaTextView.setText("Niste uneli sva polja, molimo popunite sva polja! ");
                        porukaTextView.setTextColor(Color.RED);
                    }
                    else {
                        noviIntent.putExtra("ime", imeEditText.getText().toString());
                        noviIntent.putExtra("prezime", prezimeEditText.getText().toString());
                        noviIntent.putExtra("telefon", telefonEditText.getText().toString()); //podatke o novom kontaktu vracamo main activity
                        noviIntent.putExtra("skype", skypeEditText.getText().toString());
                        noviIntent.putExtra("isIzmenaOrDodaj", "dodaj");
                        startActivity(noviIntent);
                    }
                }
            });
        }

    }
}
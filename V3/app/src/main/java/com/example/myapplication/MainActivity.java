package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<KontaktModel> kontakti = generisiKontakte(50);
    ArrayList<KontaktModel> obrisaniKontakti = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout container = findViewById(R.id.container);

        Intent childIntent = getIntent();

        //provera da li je ovo prvo pokretanje ili ga je pokrenuo neki od drugih activity-a
        if (childIntent.getExtras() != null){
            if(childIntent.getStringExtra("isIzmenaOrDodaj").equals("izmena")){
                int index = childIntent.getIntExtra("index", 0); //ako se radi izmena kontaktu se menjaju vrednosti na vrednosti iz prethodne forme
                kontakti.get(index).ime = childIntent.getStringExtra("ime");
                kontakti.get(index).prezime = childIntent.getStringExtra("prezime");
                kontakti.get(index).telefon = childIntent.getStringExtra("telefon");
                kontakti.get(index).skype = childIntent.getStringExtra("skype");
            }else if(childIntent.getStringExtra("isIzmenaOrDodaj").equals("dodaj")){//ako se radi dodavanje na listu kontakata se dodaje novi kontakt
                kontakti.add(new KontaktModel(childIntent.getStringExtra("ime"),childIntent.getStringExtra("prezime"),childIntent.getStringExtra("telefon"),childIntent.getStringExtra("skype")));
            }
        }
        iscrtajKontakte(kontakti,container);//iscrtava kontakte

        SearchView searchView = findViewById(R.id.search);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {//brise sve kontakte iz liste i dodaje samo one koji ispunjavaju filter
                ArrayList<KontaktModel> filtriraniKontakti = filtrirajKontakte(newText,kontakti);
                container.removeAllViews();
                iscrtajKontakte(filtriraniKontakti, container);
                return true;
            }
        });
    }


    private ArrayList<KontaktModel> generisiKontakte(int brojKontakata) { //generise radnom 50 kontakata
        ArrayList<KontaktModel> kontakti = new ArrayList<>();

        for (int i = 0; i < brojKontakata; i++) {
            KontaktModel kontakt = new KontaktModel(
                    "Ime " + i,
                    "Prezime " + i,
                    "Telefon " + i,
                    "Skype " + i
            );
            kontakti.add(kontakt);
        }

        return kontakti;
    }

    private void iscrtajKontakte(ArrayList<KontaktModel> kontakti,LinearLayout container) {//iscrtava kontakte u scrollviewu
        LayoutInflater inflater = getLayoutInflater();

        int redniBroj = 0; //sluzi da bi svaki drugi red bio druge boje

        for (int i = 0; i < kontakti.size(); i++) {
            View v = inflater.inflate(R.layout.jedan_red, null);

            TextView ime = v.findViewById(R.id.ime);
            TextView prezime = v.findViewById(R.id.prezime);
            TextView telefon = v.findViewById(R.id.telefon);
            TextView skype = v.findViewById(R.id.skype);
            Button obrisi = v.findViewById(R.id.btnObrisi);
            Button dodaj = findViewById(R.id.btnDodaj);

            KontaktModel kontakt = kontakti.get(i);

            if (obrisaniKontakti.contains(kontakt)) {
                continue;
            }else{
                redniBroj++;
            }

            v.setBackgroundColor(redniBroj % 2 == 0 ? Color.BLUE : Color.GRAY);//preko rednog broja radi jer kad obrisemo i da on opet svaki drugi boji razlicitom bojom

            ime.setText(kontakt.ime);
            prezime.setText(kontakt.prezime);
            telefon.setText(kontakt.telefon);
            skype.setText(kontakt.skype);

            container.addView(v);

            Intent intent = new Intent(this,IzmenaDodaj.class);

            int index = i;

            obrisi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    obrisaniKontakti.add(kontakti.get(index)); // Dodaj u listu obrisanih
                    kontakti.remove(index); // brisemo iz liste kontakata
                    container.removeAllViews(); // brisemo sve viewe
                    iscrtajKontakte(kontakti, container); // iscrtavamo novo stanje kontakata
                }
            });

            dodaj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intent.putExtra("isIzmenaOrDodaj","dodaj");
                    startActivity(intent);//kod dodavanja prosledimo da radimo dodavanje i startujemo intent sa formom za dodavanje
                }
            });

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { //radimo izmenu, prosledimo indeks zajedno sa starim podacima o kontaktu
                    intent.putExtra("ime",kontakt.ime);
                    intent.putExtra("prezime",kontakt.prezime);
                    intent.putExtra("telefon",kontakt.telefon);
                    intent.putExtra("skype",kontakt.skype);
                    intent.putExtra("index",index);
                    intent.putExtra("isIzmenaOrDodaj","izmena");
                    startActivity(intent);
                }
            });
        }
    }

    private ArrayList<KontaktModel> filtrirajKontakte(String query, ArrayList<KontaktModel> originalKontakti) {
        ArrayList<KontaktModel> filtriraniKontakti = new ArrayList<>();//po odredjenom query-u vracamo samo korisnike koji se poklapaju bar po 1 polju
        query = query.toLowerCase().trim();

        for (KontaktModel kontakt : originalKontakti) {
            if (kontakt.ime.toLowerCase().contains(query) ||
                    kontakt.prezime.toLowerCase().contains(query) ||
                    kontakt.telefon.toLowerCase().contains(query) ||
                    kontakt.skype.toLowerCase().contains(query)) {
                filtriraniKontakti.add(kontakt);
            }
        }

        return filtriraniKontakti;
    }

}
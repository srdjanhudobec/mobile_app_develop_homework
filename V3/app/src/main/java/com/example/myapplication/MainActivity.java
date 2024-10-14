package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ArrayList<KontaktModel> kontakti = new ArrayList<>();
    ArrayList<KontaktModel> obrisaniKontakti = new ArrayList<>();

    HashMap<Integer, Integer> originalniIndeksi = new HashMap<>();

    RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kontakti.add(new KontaktModel("dsadas","dsadsa","dsadas","dsada"));
        RecyclerView container = findViewById(R.id.containerRec);
        container.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(kontakti);
        container.setAdapter(adapter);

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
                return true;
            }
        });

        Button dodaj = findViewById(R.id.btnDodaj);
        Intent i = new Intent(this,IzmenaDodaj.class);
        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.putExtra("isIzmenaOrDodaj","dodaj");
                startActivity(i);
            }
        });
    }

    private ArrayList<KontaktModel> filtrirajKontakte(String query, ArrayList<KontaktModel> originalKontakti) {
        ArrayList<KontaktModel> filtriraniKontakti = new ArrayList<>();//po odredjenom query-u vracamo samo korisnike koji se poklapaju bar po 1 polju
        query = query.toLowerCase().trim();
        originalniIndeksi.clear();

        for (int i = 0; i < originalKontakti.size(); i++) {
            KontaktModel kontakt = originalKontakti.get(i);
            if (kontakt.ime.toLowerCase().contains(query) ||
                    kontakt.prezime.toLowerCase().contains(query) ||
                    kontakt.telefon.toLowerCase().contains(query) ||
                    kontakt.skype.toLowerCase().contains(query)) {
                filtriraniKontakti.add(kontakt);
                originalniIndeksi.put(filtriraniKontakti.size() - 1, i); // Sačuvamo originalni indeks
            }
        }

        return filtriraniKontakti;
    }

}
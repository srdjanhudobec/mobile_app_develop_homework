package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private ArrayList<KontaktModel> kontakti;

    public RecyclerViewAdapter(ArrayList<KontaktModel> kontakti) {
        this.kontakti = kontakti;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jedan_red,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.getIme().setText(kontakti.get(position).ime);
        holder.getPrezime().setText(kontakti.get(position).prezime);
        holder.getBrtel().setText(kontakti.get(position).telefon);
        holder.getSkype().setText(kontakti.get(position).skype);
//        if (holder.getSlika() == null){
//            kontakti.get(position)
//        }
    }

    @Override
    public int getItemCount() {
        return kontakti.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView ime,prezime,brtel,skype;
        ImageView slika;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ime = itemView.findViewById(R.id.ime);
            prezime = itemView.findViewById(R.id.prezime);
            brtel = itemView.findViewById(R.id.telefon);
            skype = itemView.findViewById(R.id.skype);
            slika = itemView.findViewById(R.id.slikaMacke);
        }

        public TextView getIme() {
            return ime;
        }

        public TextView getPrezime() {
            return prezime;
        }

        public TextView getBrtel() {
            return brtel;
        }

        public TextView getSkype() {
            return skype;
        }

        public ImageView getSlika() {
            return slika;
        }
    }
}

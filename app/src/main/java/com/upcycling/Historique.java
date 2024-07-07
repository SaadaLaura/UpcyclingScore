package com.upcycling;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import androidx.fragment.app.Fragment;
import android.widget.TextView;
import android.app.AlertDialog;


public class Historique extends Fragment {

    private RecyclerView recyclerView;
    private ProduitsAdapter adapter;
    private Button deleteButton, cancelButton; // Add cancelButton here


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historique, container, false);

        deleteButton = view.findViewById(R.id.delete_button);
        cancelButton = view.findViewById(R.id.cancel_button); // Initialize the cancel button

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.deleteSelectedItems();
                deleteButton.setVisibility(View.GONE);
                cancelButton.setVisibility(View.GONE);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Disable selection mode
                adapter.disableSelection();
                // Make both delete and cancel buttons invisible
                deleteButton.setVisibility(View.GONE);
                cancelButton.setVisibility(View.GONE);
            }
        });

        Button fabScan = view.findViewById(R.id.scan);
        fabScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setMessage("Scan lancé")
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
            }
        });
        List<Produits> produitsList = new ArrayList<>();
        produitsList.add(new Produits("Petit pot de crème saveur vanille", "Plastique, Carton", 0));
        produitsList.add(new Produits("Moutarde douce", "Verre, Plastique", 10));
        produitsList.add(new Produits("Thon entier au naturel", "Plastique, concerve", 15));
        produitsList.add(new Produits("Pomme Cassis Framboise", "Canettes, plastique", 20));
        produitsList.add(new Produits("Céréales trésor chocolat noisettes", "Boite en carton", 5));
        produitsList.add(new Produits("Œufs frais poules plein air", "Carton d'oeuf", 18));
        produitsList.add(new Produits("Lait Demi-Ecreme", "Brique en carton", 12));


        TextView emptyListMessage = view.findViewById(R.id.empty_list_message);
        if (produitsList.isEmpty()) {
            emptyListMessage.setVisibility(View.VISIBLE);
        } else {
            emptyListMessage.setVisibility(View.GONE);
        }



        recyclerView = view.findViewById(R.id.produits_container);
        TextView bandeau = view.findViewById(R.id.bandeau);
        adapter = new ProduitsAdapter(produitsList, deleteButton, cancelButton, bandeau);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }
}
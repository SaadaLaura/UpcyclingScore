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

import android.widget.ImageView;
import android.widget.TextView;



public class Historique extends Fragment {

    private RecyclerView recyclerView;
    private ProduitsAdapter adapter;
    private Button deleteButton, cancelButton; // Add cancelButton here

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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

        // Create instances of Produits
        List<Produits> produitsList = new ArrayList<>();

        Produits FirstProduit = new Produits(
                2148818887685L,
                "Skip Capsules",
                14,
                "https://www.azerty.com/",
                new Packaging[]{
                        new Packaging(
                                "Boite en carton",
                                2,
                                new ReuseIdea[]{
                                        new ReuseIdea(
                                                ReuseIdea.ReuseType.PRACTICAL,
                                                "Stockage",
                                                "Réutiliser pour stocker des choses",
                                                "https://www.azerty.com/"
                                        ),
                                        new ReuseIdea(
                                                ReuseIdea.ReuseType.PRACTICAL,
                                                "Chapeau",
                                                "Découpez et pliez la boite afin de pouvoir vous protéger de la pluie",
                                                "https://www.azerty.com/"
                                        ),
                                        new ReuseIdea(
                                                ReuseIdea.ReuseType.ARTISTIC,
                                                "Origami",
                                                "Un peu chiant",
                                                "https://www.azerty.com/"
                                        ),
                                }
                        )
                }
        );
        Produits SecondProduit = new Produits(
                301908123,
                "Thon entier naturel",
                10,
                "https://www.azerty.com/",
                new Packaging[]{
                        new Packaging(
                                "Boite de conserve",
                                3,
                                new ReuseIdea[]{
                                        new ReuseIdea(
                                                ReuseIdea.ReuseType.PRACTICAL,
                                                "Stockage",
                                                "Réutiliser pour stocker des choses",
                                                "https://www.azerty.com/"
                                        ),
                                        new ReuseIdea(
                                                ReuseIdea.ReuseType.PRACTICAL,
                                                "Chapeau",
                                                "Découpez et pliez la boite afin de pouvoir vous protéger de la pluie",
                                                "https://www.azerty.com/"
                                        ),
                                        new ReuseIdea(
                                                ReuseIdea.ReuseType.ARTISTIC,
                                                "Origami",
                                                "Un peu chiant",
                                                "https://www.azerty.com/"
                                        ),
                                }
                        ),
                        new Packaging(
                                "Plastique",
                                1,
                                new ReuseIdea[]{

                                }
                        )
                }
        );
        produitsList.add(FirstProduit);
        produitsList.add(SecondProduit);


        // Check if the list is empty
        TextView emptyListMessage = view.findViewById(R.id.empty_list_message);
        if (produitsList.isEmpty()) {
            emptyListMessage.setVisibility(View.VISIBLE);
        } else {
            emptyListMessage.setVisibility(View.GONE);
        }

        // Initialize the RecyclerView and its adapter
        recyclerView = view.findViewById(R.id.produits_container);
        TextView bandeau = view.findViewById(R.id.bandeau);
        ImageView logo_upcycling = view.findViewById(R.id.logo_upcycling);
        adapter = new ProduitsAdapter(produitsList, deleteButton, cancelButton, bandeau, logo_upcycling);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }
}
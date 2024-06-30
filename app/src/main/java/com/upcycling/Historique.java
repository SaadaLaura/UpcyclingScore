package com.upcycling;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import java.util.ArrayList;
import java.util.List;
import androidx.fragment.app.Fragment;
import android.view.KeyEvent;
import android.widget.TextView;

public class Historique extends Fragment {

    private RecyclerView recyclerView;
    private ProduitsAdapter adapter;
    private Button deleteButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_historique, container, false);
        deleteButton = view.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Confirmation")
                        .setMessage("Ligne supprimé")
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
            }
        });

        // Create instances of Produits
        List<Produits> produitsList = new ArrayList<>();

        produitsList.add(new Produits("Le petit pot de crème saveur vanille", "La laitière", "Bon"));
        produitsList.add(new Produits("Moutarde douce", "Amora", "Bon"));
        produitsList.add(new Produits("Eau minérale gazeuse naturelle", "Perrier", "Bon"));
        // Check if the list is empty
        TextView emptyListMessage = view.findViewById(R.id.empty_list_message);
        if (produitsList.isEmpty()) {
            emptyListMessage.setVisibility(View.VISIBLE);
        } else {
            emptyListMessage.setVisibility(View.GONE);
        }

        // Initialize the RecyclerView and its adapter
        recyclerView = view.findViewById(R.id.produits_container);
        adapter = new ProduitsAdapter(produitsList, deleteButton);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    if (deleteButton.getVisibility() == View.VISIBLE) {
                        // Disable selection mode
                        deleteButton.setVisibility(View.GONE);
                        adapter.disableSelection();
                        return true; // This key event has been handled
                    }
                }
                return false; // This key event has not been handled
            }
        });

        return view;
    }
}
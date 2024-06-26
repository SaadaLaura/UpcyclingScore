package com.upcycling;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class Historique extends Fragment {

    // ...

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_historique, container, false);

        TextView produit1 = view.findViewById(R.id.Produit1);
        TextView produit2 = view.findViewById(R.id.Produit2);
        TextView produit3 = view.findViewById(R.id.Produit3);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Détails du produit")
                        .setMessage("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod.")
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
            }
        };

        produit1.setOnClickListener(listener);
        produit2.setOnClickListener(listener);
        produit3.setOnClickListener(listener);

        return view;
    }
}
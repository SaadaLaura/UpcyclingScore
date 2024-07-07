package com.upcycling;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.view.ViewGroup;


public class ElementProduits extends Fragment {

    private static final String ARG_PROD = "produit";

    private Produits produit;

    public ElementProduits() {
        // Required empty public constructor
    }

    public static ElementProduits newInstance(Produits produit) {
        ElementProduits fragment = new ElementProduits();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PROD, produit);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            produit = (Produits) getArguments().getSerializable(ARG_PROD);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_element_produits, container, false);

        TextView textView = view.findViewById(R.id.produit_text);
        textView.setText(produit.getNom() + "\n" + produit.getEmballage() + "\n" + produit.getScore());

        CheckBox checkBox = view.findViewById(R.id.checkbox);

        // Ajouter un OnClickListener au TextView pour cocher/décocher la CheckBox
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox.setChecked(!checkBox.isChecked());
            }
        });

        return view;
    }
}
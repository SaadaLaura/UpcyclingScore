package com.upcycling;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Button;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;
import java.util.List;
import android.app.AlertDialog;
import android.content.DialogInterface;


public class ProduitsAdapter extends RecyclerView.Adapter<ProduitsAdapter.ViewHolder> {

    private List<Produits> produitsList;
    private Button deleteButton;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView produitText;
        public CheckBox checkBox;

        public ViewHolder(View view) {
            super(view);
            produitText = view.findViewById(R.id.produit_text);
            checkBox = view.findViewById(R.id.checkbox);
        }
    }

    public ProduitsAdapter(List<Produits> produitsList, Button deleteButton) {
        this.produitsList = produitsList;
        this.deleteButton = deleteButton;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_element_produits, parent, false);

        return new ViewHolder(itemView);
    }

   @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Produits produit = produitsList.get(position);
        holder.produitText.setText(produit.getNom() + "\n" + produit.getMarque() + "\n" + produit.getScore());

        // Set the visibility of the CheckBox based on the showCheckBox field of the product
        holder.checkBox.setVisibility(produit.isShowCheckBox() ? View.VISIBLE : View.GONE);

        // Set an OnClickListener on the TextView
        holder.produitText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display product details
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Détail du produit");
                builder.setMessage("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
                builder.setPositiveButton("OK", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        // Set an OnLongClickListener on the TextView
        holder.produitText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Set the showCheckBox field to true for all products
                for (Produits p : produitsList) {
                    p.setShowCheckBox(true);
                }

                // Notify the adapter that the data has changed
                notifyDataSetChanged();

                // Make the delete button visible
                deleteButton.setVisibility(View.VISIBLE);
                return true;
            }
        });
    }
    public void disableSelection() {
        for (Produits produit : produitsList) {
            produit.setShowCheckBox(false);
        }
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return produitsList.size();
    }
}
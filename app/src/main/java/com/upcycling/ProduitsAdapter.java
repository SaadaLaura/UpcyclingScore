package com.upcycling;

import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Button;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import android.app.AlertDialog;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.graphics.Color;
import coil.Coil;
import coil.request.ImageRequest;



public class ProduitsAdapter extends RecyclerView.Adapter<ProduitsAdapter.ViewHolder> {

    private List<Produits> produitsList;
    private Button deleteButton;
    private Button cancelButton;
    private TextView bandeau;
    private ImageView logo_upcycling;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView produitText, scoreText;
        public CheckBox checkBox;
        public ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            produitText = view.findViewById(R.id.produit_text);
            scoreText = view.findViewById(R.id.score_text);
            checkBox = view.findViewById(R.id.checkbox);
            imageView = view.findViewById(R.id.image_view);
        }
    }

    public ProduitsAdapter(List<Produits> produitsList, Button deleteButton, Button cancelButton, TextView bandeau, ImageView logo_upcycling) {
        this.produitsList = produitsList;
        this.deleteButton = deleteButton;
        this.cancelButton = cancelButton;
        this.bandeau = bandeau;
        this.logo_upcycling = logo_upcycling;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_element_produits, parent, false);

        return new ViewHolder(itemView);
    }

    public void updateBandeauVisibility() {
        boolean isSelectionMode = false;
        for (Produits produit : produitsList) {
            if (produit.isShowCheckBox()) {
                isSelectionMode = true;
                break;
            }
        }
        bandeau.setVisibility(isSelectionMode ? View.GONE : View.VISIBLE);
        logo_upcycling.setVisibility(isSelectionMode ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Produits produit = produitsList.get(position);
        SpannableStringBuilder builders = new SpannableStringBuilder();

        // Ajouter le nom
        builders.append(produit.getName() + "\n");

        for (Packaging packaging : produit.getPackagings()) {
            SpannableString packagingSpannable = new SpannableString(packaging.getPackagingType());
            packagingSpannable.setSpan(new StyleSpan(Typeface.ITALIC), 0, packagingSpannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builders.append(packagingSpannable);
            if (packaging != produit.getPackagings()[produit.getPackagings().length - 1]) {
                builders.append(", "); // Ajouter une virgule entre les emballages sauf après le dernier
            }
        }

        holder.produitText.setText(builders);

        holder.scoreText.setText(String.valueOf(produit.getScore()) + "/20");


        ImageRequest request = new ImageRequest.Builder(holder.imageView.getContext())
                .data(produit.getUrlImage())
                .target(holder.imageView)
                .crossfade(true)
                .build();

        Coil.imageLoader(holder.imageView.getContext()).enqueue(request);

        // Set the visibility of the CheckBox based on the showCheckBox field of the product
        boolean isSelectionMode = produit.isShowCheckBox();
        holder.checkBox.setVisibility(isSelectionMode ? View.VISIBLE : View.GONE);
        holder.checkBox.setChecked(produit.isChecked()); // Ensure the CheckBox reflects the current state

        // Update CheckBox state on click and ensure it's reflected in the Produits object
        holder.checkBox.setOnClickListener(v -> {
            boolean isChecked = holder.checkBox.isChecked();
            produit.setChecked(isChecked);
        });

        holder.produitText.setOnClickListener(v -> {
            if (isSelectionMode) {
                boolean newCheckedState = !holder.checkBox.isChecked();
                holder.checkBox.setChecked(newCheckedState);
                produit.setChecked(newCheckedState); // Update isChecked state when text is clicked in selection mode
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Détail du produit");
                builder.setMessage("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
                builder.setPositiveButton("OK", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        // Set an OnLongClickListener on the TextView
        holder.produitText.setOnLongClickListener(v -> {
            for (Produits p : produitsList) {
                p.setShowCheckBox(true);
                //p.setChecked(true); // Reset checked state when entering selection mode
            }
            produitsList.get(holder.getAdapterPosition()).setChecked(true);
            notifyDataSetChanged(); // Notify data changed
            deleteButton.setVisibility(View.VISIBLE); // Show delete button
            cancelButton.setVisibility(View.VISIBLE); // Show cancel button
            //updateBandeauVisibility();
            return true;
        });
        updateBandeauVisibility();

    }
    public void disableSelection() {
        for (Produits produit : produitsList) {
            produit.setShowCheckBox(false);
        }
        notifyDataSetChanged();
        updateBandeauVisibility();
    }
    public void deleteSelectedItems() {
        // Itération à l'envers pour éviter les problèmes de décalage d'index lors de la suppression
        for (int i = produitsList.size() - 1; i >= 0; i--) {
            Produits produit = produitsList.get(i);
            if (produit.isShowCheckBox() && produit.isChecked()) {
                produitsList.remove(i); // Suppression de l'élément
            }
        }
        notifyDataSetChanged(); // Notifier l'adaptateur du changement
        disableSelection(); // Désactiver le mode de sélection
        updateBandeauVisibility();
    }

    @Override
    public int getItemCount() {
        return produitsList.size();
    }
}
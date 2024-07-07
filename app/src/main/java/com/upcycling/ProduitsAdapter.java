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
import androidx.core.content.ContextCompat;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ColorDrawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.graphics.Typeface;


public class ProduitsAdapter extends RecyclerView.Adapter<ProduitsAdapter.ViewHolder> {

    private List<Produits> produitsList;
    private Button deleteButton;
    private Button cancelButton;
    private TextView bandeau;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView produitText, scorePastille;
        public CheckBox checkBox;

        public ViewHolder(View view) {
            super(view);
            produitText = view.findViewById(R.id.produit_text);
            checkBox = view.findViewById(R.id.checkbox);
            scorePastille = view.findViewById(R.id.score_pastille); // Reference to the score badge TextView
        }
    }

    public ProduitsAdapter(List<Produits> produitsList, Button deleteButton, Button cancelButton, TextView bandeau) {
        this.produitsList = produitsList;
        this.deleteButton = deleteButton;
        this.cancelButton = cancelButton;
        this.bandeau = bandeau;
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
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Produits produit = produitsList.get(position);
        String produitInfo = produit.getNom() + "\n" + produit.getEmballage();
        holder.produitText.setText(produitInfo);

        // Construction du texte avec Spannable pour un affichage personnalisé
        String scoreText = produit.getScore() + " / 20";
        SpannableString spannableString = new SpannableString(scoreText);

        // Appliquer un style gras au score et au "20" si nécessaire
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, scoreText.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        // Mise à jour du texte du scorePastille avec le SpannableString
        holder.scorePastille.setText(spannableString);

        int color = ContextCompat.getColor(holder.itemView.getContext(), produit.getScoreColor());
        // Apply the color to the score badge background
        Drawable background = holder.scorePastille.getBackground();
        if (background instanceof ShapeDrawable) {
            ((ShapeDrawable) background).getPaint().setColor(color);
        } else if (background instanceof GradientDrawable) {
            ((GradientDrawable) background).setColor(color);
        } else if (background instanceof ColorDrawable) {
            ((ColorDrawable) background).setColor(color);
        }

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
                // Display product details if not in selection mode
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Détail du produit");
                builder.setMessage("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
                builder.setPositiveButton("OK", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        holder.produitText.setOnLongClickListener(v -> {
            for (Produits p : produitsList) {
                p.setShowCheckBox(true);
                p.setChecked(false); // Reset checked state when entering selection mode
            }
            notifyDataSetChanged(); // Notify data changed
            deleteButton.setVisibility(View.VISIBLE); // Show delete button
            cancelButton.setVisibility(View.VISIBLE); // Show cancel button
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
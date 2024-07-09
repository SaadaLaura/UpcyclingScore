package com.upcycling;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.ArrayList;
import java.util.List;

import android.widget.ImageView;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.app.AlertDialog;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProduitsAdapter adapter;
    private Button deleteButton, cancelButton; // Add cancelButton here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        deleteButton = findViewById(R.id.delete_button);
        cancelButton = findViewById(R.id.cancel_button); // Initialize the cancel button

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
                "Ice Tea Raspberry",
                0,
                "https://erposcar.msol.dev/focus/products/3502110010674.webp",
                new Packaging[]{
                        new Packaging(
                                "Bouteille en plastique",
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
                18,
                "https://images.openfoodfacts.org/images/products/301/908/123/9237/front_fr.36.full.jpg",
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
        TextView emptyListMessage = findViewById(R.id.empty_list_message);
        if (produitsList.isEmpty()) {
            emptyListMessage.setVisibility(View.VISIBLE);
        } else {
            emptyListMessage.setVisibility(View.GONE);
        }

        // Initialize the RecyclerView and its adapter
        recyclerView = findViewById(R.id.produits_container);
        TextView bandeau = findViewById(R.id.bandeau);
        ImageView logo_upcycling = findViewById(R.id.logo_upcycling);
        adapter = new ProduitsAdapter(produitsList, deleteButton, cancelButton, bandeau, logo_upcycling);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        //fetchProductDetails(3019081239237L);
    }

    /* Pour Faire appel à l'API  en localhost
    private void showResultPopup(String message) {
        getActivity().runOnUiThread(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Résultat de la requête");
            builder.setMessage(message);
            builder.setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
            AlertDialog dialog = builder.create();
            dialog.show();
        });}


    private void fetchProductDetails(long codebarre) {
        new Thread(() -> {
            String urlString = "http://10.0.2.2:5000/products/" + codebarre; //remplacer par l'adresse mis dans le network
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    showResultPopup("Réponse de l'API : " + response.toString());
                } else {
                    showResultPopup("Erreur API : Code de réponse : " + responseCode);
                }
            } catch (Exception e) {
                showResultPopup("Exception API : " + e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }
    */
}
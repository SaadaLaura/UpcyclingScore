package com.upcycling;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Spinner;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class BottomSheetFragment extends Fragment {

    private TextView productTextView;

    private TabLayout reuseTypeTabView;

    private Spinner packagingsSpinnerView;
    private ArrayAdapter<Packaging> adapter;

    private RecyclerView recyclerView;
    private ReuseIdeaAdapter reuseIdeaAdapter;

    public BottomSheetBehavior<FrameLayout> bottomSheetBehavior;

    private Packaging selectedPackaging;
    private ReuseIdea.ReuseType selectedReuseType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);

        // Find the bottom_sheet view and initialize BottomSheetBehavior
        FrameLayout bottomSheetView = view.findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        productTextView = view.findViewById(R.id.produit_text);

        reuseTypeTabView = view.findViewById(R.id.reuse_type_category);
        reuseTypeTabView.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectedReuseType = ReuseIdea.getType(tab.getText().toString());
                updateReuseIdeas();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        packagingsSpinnerView = view.findViewById(R.id.packagings_selectbox);
        packagingsSpinnerView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPackaging = (Packaging) parent.getItemAtPosition(position);
                updateReuseIdeas();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){
                // Handle the case when nothing is selected (if needed)
            }
        });

        recyclerView = view.findViewById(R.id.reuse_ideas_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    public void showBottomSheet (Product product, boolean alwaysExpanded) {
        insertProductData(product);

        if (alwaysExpanded) {
            bottomSheetBehavior.setPeekHeight(0);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

//            bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//               @Override
//               public void onStateChanged(@NonNull View bottomSheet, int newState) {
//                       bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN); // Hide when collapsed
//                   }
//               }
//
//                @Override
//                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//                    // Not used in this case
//                }
//            });
        } else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            bottomSheetBehavior.setPeekHeight(500); // TODO : Set Peek height at the right place (just below the main part)
        }
    }

    private void insertProductData(Product product) {
        String packagingLine;
        switch (product.getPackagings().length){
            case 0:
                packagingLine = "ERREUR: Pas d'emballage";
                break;
            case 1:
                packagingLine = product.getPackagings()[0].getPackagingType();
                break;
            case 2:
                packagingLine = product.getPackagings()[0].getPackagingType() + ", " +
                        product.getPackagings()[1].getPackagingType();
                break;
            default:
                packagingLine = product.getPackagings()[0].getPackagingType() + ", " +
                        product.getPackagings()[1].getPackagingType() + ", ...";
                break;
        }
        String productText = product.getName() + "\n" + packagingLine;
        productTextView.setText(productText);

        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<Packaging> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                product.getPackagings()
        );
        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner.
        packagingsSpinnerView.setAdapter(adapter);

        selectedPackaging = product.getPackagings()[0];
        selectedReuseType = ReuseIdea.ReuseType.PRACTICAL;
        updateReuseIdeas();


        // TODO : Fill the rest of the content (see Figma)
    }

    private void updateReuseIdeas() {
        List<ReuseIdea> filteredIdeas = Arrays.stream(selectedPackaging.getReuseIdeas())
                .filter(reuseIdea -> reuseIdea.getReuseType() == selectedReuseType)
                .collect(Collectors.toList());

        reuseIdeaAdapter = new ReuseIdeaAdapter(filteredIdeas);
        recyclerView.setAdapter(reuseIdeaAdapter);
    }
}
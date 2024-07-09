package com.upcycling;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;


public class BottomSheetFragment extends Fragment {

    private TextView productTextView;
    BottomSheetBehavior<FrameLayout> bottomSheetBehavior;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);

        // Find the bottom_sheet view and initialize BottomSheetBehavior
        FrameLayout bottomSheetView = view.findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        productTextView = view.findViewById(R.id.produit_text);

        return view;
    }

    public void showBottomSheet (Product product, boolean alwaysExpanded) {
        insertProductData(product);

        bottomSheetBehavior.setPeekHeight(500); // TODO : Set Peek height at the right place (just below the main part)
        if (alwaysExpanded) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

            bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
               @Override
               public void onStateChanged(@NonNull View bottomSheet, int newState) {
                   if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                       bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN); // Hide when collapsed
                   }
               }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                    // Not used in this case
                }
            });
        } else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
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

        // TODO : Fill the rest of the content (see Figma)
    }
}
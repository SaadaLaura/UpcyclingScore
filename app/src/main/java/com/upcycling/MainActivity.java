package com.upcycling;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.FrameLayout;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private Product displayProduct = new Product(
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

//    private final ActivityResultLauncher<Intent> barcodeLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            result -> {
//                if (result.getResultCode() == RESULT_OK) {
//                    Intent data = result.getData();
//                    if (data != null) {
//                        String barcode = data.getStringExtra("barcode");
//                        //barcodeTextView.setText(barcode);
//
//                        insertProductData(displayProduct);
//
//                        BottomSheetBehavior<FrameLayout> bottomSheet = BottomSheetBehavior.from(
//                                findViewById(R.id.bottom_sheet));
//                        // bottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
//                        showBottomSheet(bottomSheet);
//                    }
//                }
//            }
//    );

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

        FloatingActionButton scanFab = findViewById(R.id.scan_fab);

        scanFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
//                barcodeLauncher.launch(intent);
                startActivity(intent);
//                finish();
            }
        });
        BottomSheetFragment bottomSheetFragment = (BottomSheetFragment) getSupportFragmentManager().findFragmentById(R.id.bottom_sheet_fragment_container);
        Button testbutton = findViewById(R.id.test_button);
        testbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetFragment.showBottomSheet(displayProduct, true);
            }
        });
    }

    // TODO : Add Historic
}

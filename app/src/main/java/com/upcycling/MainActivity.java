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

    private TextView barcodeTextView;
    private final ActivityResultLauncher<Intent> barcodeLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        String barcode = data.getStringExtra("barcode");
                        barcodeTextView.setText(barcode);

                        BottomSheetBehavior<FrameLayout> bottomSheet = BottomSheetBehavior.from(
                                findViewById(R.id.bottom_sheet));
                        // bottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
                        showBottomSheet(bottomSheet);
                    }
                }
            }
    );

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

        barcodeTextView = findViewById(R.id.barcode_text_view);
        FloatingActionButton scanFab = findViewById(R.id.scan_fab);

        scanFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
                barcodeLauncher.launch(intent);
            }
        });

        BottomSheetBehavior<FrameLayout> bottomSheet = BottomSheetBehavior.from(
                findViewById(R.id.bottom_sheet));
        bottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    private void showBottomSheet (BottomSheetBehavior<FrameLayout> bottomSheet) {
        // TODO : Get Product data from API
        bottomSheet.setPeekHeight(750); // TODO : Set Peek height at the right place (just below the main part)
        bottomSheet.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }
}

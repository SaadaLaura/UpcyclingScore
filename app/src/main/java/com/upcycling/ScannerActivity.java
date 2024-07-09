package com.upcycling;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Size;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.barcode.common.Barcode;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScannerActivity extends AppCompatActivity {

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

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1;
    private ExecutorService cameraExecutor;

    @androidx.camera.core.ExperimentalGetImage
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        }
        cameraExecutor = Executors.newSingleThreadExecutor();
    }

    @androidx.camera.core.ExperimentalGetImage
    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindCameraPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                // Handle any errors (including cancellation) here.
            }
        }, ContextCompat.getMainExecutor(this));
    }

    @androidx.camera.core.ExperimentalGetImage
    private void bindCameraPreview(@NonNull ProcessCameraProvider cameraProvider) {
        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setTargetResolution(new Size(1280, 720))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();

        BarcodeScannerOptions options = new BarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_EAN_13)
                .build();
        BarcodeScanner scanner = BarcodeScanning.getClient(options);

        imageAnalysis.setAnalyzer(cameraExecutor, imageProxy -> {
            if (imageProxy.getImage() != null) {
                InputImage inputImage = InputImage.fromMediaImage(imageProxy.getImage(), imageProxy.getImageInfo().getRotationDegrees());
                scanner.process(inputImage).addOnSuccessListener(barcodes -> {
                    for (Barcode barcode : barcodes) {
                        String rawValue = barcode.getRawValue();
                        Toast.makeText(ScannerActivity.this, "Barcode: " + rawValue, Toast.LENGTH_SHORT).show();

                        BottomSheetFragment bottomSheetFragment = (BottomSheetFragment) getSupportFragmentManager().findFragmentById(R.id.bottom_sheet_fragment_container);

                        // TODO : Get Product data from API
                        bottomSheetFragment.showBottomSheet(displayProduct, false);
                    }
                })
                .addOnFailureListener(e -> {
                    imageProxy.close();
                })
                .addOnCompleteListener(task -> {
                    imageProxy.close();
                });
            }
        });

        // Create a Preview use case
        Preview preview = new Preview.Builder().build();

        // Get a reference to the PreviewView in your layout
        PreviewView viewFinder = findViewById(R.id.viewFinder);

        // Bind the Preview use case to the PreviewView
        preview.setSurfaceProvider(viewFinder.getSurfaceProvider());

        CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;
        cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis);
    }

    @androidx.camera.core.ExperimentalGetImage
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraExecutor.shutdown();
    }
}

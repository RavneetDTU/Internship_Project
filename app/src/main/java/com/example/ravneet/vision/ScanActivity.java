package com.example.ravneet.vision;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import android.Manifest.permission.*;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class ScanActivity extends AppCompatActivity {

    private SurfaceView cameraView;
    private BarcodeDetector barcode;
    private SurfaceHolder surfaceHolder;
    private CameraSource cameraSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        cameraView = findViewById(R.id.surfaceView);
        cameraView.setZOrderMediaOverlay(true);

        surfaceHolder = cameraView.getHolder();
        barcode = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();

        if(!barcode.isOperational()){
            Toast.makeText(getApplicationContext(), "Sorry Code not Valid", Toast.LENGTH_SHORT).show();
            this.finish();
        }

        cameraSource = new CameraSource.Builder(this, barcode)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(30)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1920, 1024).build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {

                    if(ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                        cameraSource.start(cameraView.getHolder());
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

        barcode.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcode = detections.getDetectedItems();
                if(barcode.size() > 0){
                    Intent intent = new Intent();
                    intent.putExtra("barcode", barcode.valueAt(0));
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

    }
}

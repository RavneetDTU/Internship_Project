package com.example.ravneet.vision;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.vision.barcode.Barcode;

public class MainActivity extends AppCompatActivity {

    private Button btn_scan, btn_set;
    private EditText name, rno;
    private TextView tv_result;
    public static final int REQUEST_CODE = 101;
    public static final int PERMISSION_REQUEST = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_scan = findViewById(R.id.btn_MainActivity_Scan);
        tv_result = findViewById(R.id.tv_resultText);
        btn_scan = findViewById(R.id.btn_MainActivity_Scan);
        name = findViewById(R.id.et_MainActivity_Name);
        rno = findViewById(R.id.et_MainActivity_rno);
        tv_result.setText("");

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {android.Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }

        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScanActivity.class);

                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            if(data != null){
                final Barcode barcode = data.getParcelableExtra("barcode");
                tv_result.post(new Runnable() {
                    @Override
                    public void run() {
                        tv_result.setText(barcode.displayValue);
                    }
                });
            }
        }
    }
}

package com.example.ravneet.vision;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ravneet.vision.Pojo.ItemDetails;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "fasle";

    private Button btn_scan, btn_set, btn_seeAllData;
    private EditText et_name, et_rno;
    private TextView tv_result;
    public static final int REQUEST_CODE = 101;
    public static final int PERMISSION_REQUEST = 102;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private String currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Main");

        btn_seeAllData = findViewById(R.id.btn_seeAllData);
        btn_scan = findViewById(R.id.btn_MainActivity_Scan);
        tv_result = findViewById(R.id.tv_resultText);
        btn_set = findViewById(R.id.btn_MainActivity_set);
        et_name = findViewById(R.id.et_MainActivity_Name);
        et_rno = findViewById(R.id.et_MainActivity_rno);

        currentDate = DateFormat.getDateTimeInstance().format(new Date());

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

        btn_seeAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListActivity.class));
            }
        });
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, @Nullable final Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            if(data != null){
                final Barcode barcode = data.getParcelableExtra("barcode");
                Log.d(TAG, data.toString());
                tv_result.setText(barcode.displayValue);
                String name = et_name.getText().toString();
                String rno = et_rno.getText().toString();

                if(!name.equals("") && !rno.equals("")){

                    final ItemDetails details = new ItemDetails(barcode.displayValue,name,rno, currentDate, false );
                    btn_set.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myRef.child(barcode.displayValue).setValue(details);
                            et_name.setText("");
                            et_rno.setText("");
                            tv_result.setText("");
                            Toast.makeText(MainActivity.this, "Sending Data", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(MainActivity.this, "Fields shouldn't be empty", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }
}

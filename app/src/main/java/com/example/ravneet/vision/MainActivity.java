package com.example.ravneet.vision;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
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

import com.example.ravneet.vision.Adapter.SingleListAdapter;
import com.example.ravneet.vision.Pojo.ItemDetails;
import com.example.ravneet.vision.Pojo.ListObject;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "fasle";

    private Button btn_scan, btn_set, btn_signout, btn_singleList;
    private EditText et_name, et_rno, et_mbo;
    private TextView tv_result;
    public static final int REQUEST_CODE = 101;
    public static final int PERMISSION_REQUEST = 102;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference listref;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    public static final int RC_SIGN_IN = 1;

    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build()
    );

    private String currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Toast.makeText(MainActivity.this, "User Signed In", Toast.LENGTH_SHORT).show();
                }else{
                    startActivityForResult(
                            AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .setIsSmartLockEnabled(false)
                            .build(),
                    RC_SIGN_IN);

                }
            }
        };

        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Main");
        listref = database.getReference("List");
        btn_scan = findViewById(R.id.btn_MainActivity_Scan);
        btn_signout = findViewById(R.id.btn_signout);
        tv_result = findViewById(R.id.tv_resultText);
        btn_set = findViewById(R.id.btn_MainActivity_set);
        et_name = findViewById(R.id.et_MainActivity_Name);
        btn_singleList = findViewById(R.id.btn_singleList);
        et_rno = findViewById(R.id.et_MainActivity_rno);
        et_mbo = findViewById(R.id.et_mobileNumber);


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

        btn_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
        btn_singleList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SingleListActivity.class));
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
                final String name = et_name.getText().toString();
                String rno = et_rno.getText().toString();
                String str_mno = et_mbo.getText().toString();

                if(!name.equals("") && !rno.equals("")){

                    final String id = myRef.push().getKey();

                    final ItemDetails details = new ItemDetails(barcode.displayValue,name,rno, currentDate, false, id, str_mno );
                    final ListObject listObject = new ListObject(barcode.displayValue);
                    btn_set.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listref.child(barcode.displayValue).setValue(listObject);
                            myRef.child(id).setValue(details);
                            et_name.setText("");
                            et_rno.setText("");
                            tv_result.setText("");
                            et_mbo.setText("");
                            Toast.makeText(MainActivity.this, "Sending Data", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(MainActivity.this, "Fields shouldn't be empty", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    public void signOut(){
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this, "User SignOut", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
}

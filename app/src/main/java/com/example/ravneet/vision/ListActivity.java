 package com.example.ravneet.vision;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.example.ravneet.vision.Pojo.ItemDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

 public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DataAdapter dataAdapter;

    private ProgressBar progressBar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            ArrayList<ItemDetails> itemDetails = new ArrayList<>();
            for(DataSnapshot dataSnapshotchild : dataSnapshot.getChildren()){
                ItemDetails thisItem = new ItemDetails(dataSnapshotchild.child("code").getValue().toString(),
                        dataSnapshotchild.child("name").getValue().toString(),
                        dataSnapshotchild.child("rno").getValue().toString(),
                        dataSnapshotchild.child("date").getValue().toString(),
                        (Boolean)dataSnapshotchild.child("returned").getValue());
                itemDetails.add(thisItem);
            }
            progressBar.setVisibility(ProgressBar.GONE);
            dataAdapter.updateAdapter(itemDetails);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        progressBar = findViewById(R.id.progressBar_ListActivity);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Main");

        recyclerView = findViewById(R.id.rv_listActivity);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataAdapter = new DataAdapter(this, new ArrayList<ItemDetails>());
        recyclerView.setAdapter(dataAdapter);

        databaseReference.addValueEventListener(valueEventListener);

        progressBar.setVisibility(ProgressBar.VISIBLE);

    }
}

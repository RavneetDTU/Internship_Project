package com.example.ravneet.vision;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;

import com.example.ravneet.vision.Adapter.OnItemClickListner;
import com.example.ravneet.vision.Adapter.SingleListAdapter;
import com.example.ravneet.vision.Pojo.ListObject;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SingleListActivity extends AppCompatActivity {

    private SingleListAdapter listAdapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            ArrayList<ListObject> listObjects = new ArrayList<>();

            for(DataSnapshot dataSnapshotchild : dataSnapshot.getChildren()){
                ListObject thisObject = new ListObject(dataSnapshotchild.child("uid").getValue().toString()
                        ,dataSnapshotchild.child("heading").getValue().toString());
                listObjects.add(thisObject);
            }
            listAdapter.updateList(listObjects);
            progressBar.setVisibility(ProgressBar.GONE);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_list);
        progressBar = findViewById(R.id.progressBar_SingleListActivity);
        progressBar.setVisibility(ProgressBar.VISIBLE);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("List");

        recyclerView = findViewById(R.id.rv_singleListActivity);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listAdapter = new SingleListAdapter(new ArrayList<ListObject>());
        recyclerView.setAdapter(listAdapter);

        databaseReference.addValueEventListener(valueEventListener);

        listAdapter.setOnItemClickListner(new OnItemClickListner() {
            @Override
            public void onItemClick(String heading) {
                Intent intent = new Intent(SingleListActivity.this, ListActivity.class);
                intent.putExtra("head",heading);
                startActivity(intent);
            }
        });

    }
}

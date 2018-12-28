 package com.example.ravneet.vision;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ravneet.vision.Adapter.DataAdapter;
import com.example.ravneet.vision.Pojo.ItemDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

 public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DataAdapter dataAdapter;
     private String retDate;

    private ProgressBar progressBar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            ArrayList<ItemDetails> itemDetails = new ArrayList<>();
            for(DataSnapshot dataSnapshotchild : dataSnapshot.getChildren()){
                ItemDetails thisItem = new ItemDetails(dataSnapshotchild.child("id").getValue().toString(),
                        dataSnapshotchild.child("mbno").getValue().toString(),
                        dataSnapshotchild.child("code").getValue().toString(),
                        dataSnapshotchild.child("name").getValue().toString(),
                        dataSnapshotchild.child("rno").getValue().toString(),
                        dataSnapshotchild.child("issueDate").getValue().toString(),
                        dataSnapshotchild.child("returnDate").getValue().toString(),
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

        String heading = getIntent().getStringExtra("head");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        Query query = databaseReference.child("Main").orderByChild("code").equalTo(heading);
        query.addListenerForSingleValueEvent(valueEventListener);

        recyclerView = findViewById(R.id.rv_listActivity);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataAdapter = new DataAdapter(this, new ArrayList<ItemDetails>());
        recyclerView.setAdapter(dataAdapter);

        //databaseReference.addValueEventListener(valueEventListener);

        progressBar.setVisibility(ProgressBar.VISIBLE);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                retDate = DateFormat.getDateTimeInstance().format(new Date());
                ItemDetails seletedItem = dataAdapter.getItemAt(viewHolder.getAdapterPosition());
                seletedItem.setReturned(true);
                seletedItem.setReturnDate(retDate);
                databaseReference.child("Main").child(seletedItem.getId()).setValue(seletedItem);
                Toast.makeText(ListActivity.this, "Item returned", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

    }
}

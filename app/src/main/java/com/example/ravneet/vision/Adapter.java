package com.example.ravneet.vision;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class Adapter extends RecyclerView.Adapter<AdapterViewHolder> {

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder adapterViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

class AdapterViewHolder extends RecyclerView.ViewHolder{

    public AdapterViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}

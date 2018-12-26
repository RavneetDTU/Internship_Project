package com.example.ravneet.vision.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ravneet.vision.Pojo.ListObject;
import com.example.ravneet.vision.R;

import java.util.ArrayList;

public class SingleListAdapter extends RecyclerView.Adapter<SingleListAdapter.SigleListHolder> {

    private ArrayList<ListObject> listObjects;
    private OnItemClickListner onItemClickListner;

    public void setOnItemClickListner(OnItemClickListner onItemClickListner){
        this.onItemClickListner = onItemClickListner;
    }

    public SingleListAdapter(ArrayList<ListObject> listObjects){
        this.listObjects = listObjects;
    }

    public void updateList(ArrayList<ListObject> listObjects){
        this.listObjects = listObjects;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public SigleListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.singlelist_item,viewGroup,false);
        return new SigleListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SigleListHolder sigleListHolder, int i) {
        final ListObject thisobject = listObjects.get(i);
        sigleListHolder.textView.setText(thisobject.getHeading());
        sigleListHolder.thisView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListner != null){
                    onItemClickListner.onItemClick(thisobject.getHeading());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listObjects.size();
    }

    class SigleListHolder extends RecyclerView.ViewHolder{

        TextView textView;
        View thisView;

        public SigleListHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_heading_singleList);

            thisView = itemView;
        }
    }
}

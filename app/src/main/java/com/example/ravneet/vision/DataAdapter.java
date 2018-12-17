package com.example.ravneet.vision;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ravneet.vision.Pojo.ItemDetails;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<AdapterViewHolder> {

    private Context context;
    private ArrayList<ItemDetails> itemDetailsArrayList;

    public DataAdapter(Context context, ArrayList<ItemDetails> arrayList){
        this.context = context;
        this.itemDetailsArrayList = arrayList;
    }

    public void updateAdapter(ArrayList<ItemDetails> arrayList){
        this.itemDetailsArrayList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item, viewGroup, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int i) {
        final ItemDetails thisItem = itemDetailsArrayList.get(i);

        holder.tv_item.setText(thisItem.getCode());
        holder.tv_name.setText(thisItem.getName());
        holder.tv_rno.setText(thisItem.getRno());

    }

    @Override
    public int getItemCount() {
        if(itemDetailsArrayList.size() != 0){
            return itemDetailsArrayList.size();
        }else {
            return 0;
        }
    }
}

class AdapterViewHolder extends RecyclerView.ViewHolder{

    TextView tv_item, tv_name, tv_rno;

    public AdapterViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_item = itemView.findViewById(R.id.tv_listItem_ItemType);
        tv_name = itemView.findViewById(R.id.tv_listItem_name);
        tv_rno = itemView.findViewById(R.id.tv_listItem_rno);
    }
}

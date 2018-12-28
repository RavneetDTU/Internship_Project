package com.example.ravneet.vision.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ravneet.vision.Pojo.ItemDetails;
import com.example.ravneet.vision.R;

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

    public ItemDetails getItemAt(int position){
        return itemDetailsArrayList.get(position);
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
        holder.tv_name.setText("Name: "+thisItem.getName());
        holder.tv_rno.setText("R.No: "+thisItem.getRno());

        boolean value = thisItem.getReturned();
        if(value == false){
            //holder.btn_revived.setText("Not Returned");
            holder.btn_revived.setBackgroundColor(Color.RED);
            holder.datetime.setText("Issue Date: "+thisItem.getIssueDate());
        }if(value == true){
            //holder.btn_revived.setText("Returned");
            holder.datetime.setText("Return Date: "+thisItem.getReturnDate());
            holder.btn_revived.setBackgroundColor(Color.GREEN);
        }
        holder.btn_revived.setText("Ph: "+thisItem.getMbno());
//        holder.btn_revived.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

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

    TextView tv_item, tv_name, tv_rno, datetime;
    Button btn_revived;

    public AdapterViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_item = itemView.findViewById(R.id.tv_listItem_ItemType);
        tv_name = itemView.findViewById(R.id.tv_listItem_name);
        tv_rno = itemView.findViewById(R.id.tv_listItem_rno);
        datetime = itemView.findViewById(R.id.tv_date);
        btn_revived = itemView.findViewById(R.id.btn_returned);
    }
}

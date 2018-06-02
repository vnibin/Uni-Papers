package com.example.virtuzo.dummyproject.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.virtuzo.dummyproject.R;

import java.util.List;

/**
 * Created by Virtuzo on 13/04/18.
 */

public class Undergraduation_adapter extends RecyclerView.Adapter<Undergraduation_adapter.MyViewholder> {

    private List<String> Undergraduation_list;
    private Context context;

    public Undergraduation_adapter(List<String> listitems, Context context) {
        this.Undergraduation_list = listitems;
        this.context = context;
    }

    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.undergraduation_list,parent,false);
        return new Undergraduation_adapter.MyViewholder(v);
    }

    @Override
    public void onBindViewHolder(MyViewholder holder, int position) {

        final String listItem=Undergraduation_list.get(position);
        holder.textviewheading.setText(listItem);

    }

    @Override
    public int getItemCount() {
        return Undergraduation_list.size();

    }

    public class MyViewholder extends RecyclerView.ViewHolder {
        public TextView textviewheading;
        public MyViewholder(View itemView) {
            super(itemView);
            textviewheading=(TextView)itemView.findViewById(R.id.textviewhead);
        }
    }
}

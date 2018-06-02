package com.example.virtuzo.dummyproject.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.virtuzo.dummyproject.R;

import java.util.List;

/**
 * Created by Virtuzo on 13/04/18.
 */

public class Postgraduation_adapter extends RecyclerView.Adapter<Postgraduation_adapter.MyViewholder> {

    private List<String> postgraduation_list;
    private Context context;
    private Postgraduation_adapter.OnClickListener listener;

    public Postgraduation_adapter(List<String> listitems, Context context,Postgraduation_adapter.OnClickListener onClickListener) {
        this.postgraduation_list = listitems;
        this.context = context;
        this.listener = onClickListener;    }

    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.postgraduation_list,parent,false);
        return new Postgraduation_adapter.MyViewholder(v);
    }

    @Override
    public void onBindViewHolder(MyViewholder holder, int position) {

        final String listItem=postgraduation_list.get(position);
        holder.textviewheading.setText(listItem);
        //holder.mca_image.
        holder.bind(listItem);

    }

    @Override
    public int getItemCount() {
        return postgraduation_list.size();

    }

    public interface OnClickListener {
        void onItemClick(String postgraduation_name);
    }

    public class MyViewholder extends RecyclerView.ViewHolder {
        public TextView textviewheading;
       // public ImageView mca_image;
        public  View myview;
        public MyViewholder(View itemView) {
            super(itemView);
            textviewheading=(TextView)itemView.findViewById(R.id.textviewhead);
            //mca_image=(ImageView)itemView.findViewById(R.id.img_mca);
            myview=itemView;
        }

        public void bind(final String postrgraduation_name)
        {
            myview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    listener.onItemClick(postrgraduation_name);
                }
            });
        }

    }
}

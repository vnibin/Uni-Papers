package com.example.virtuzo.dummyproject.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//import com.example.virtuzo.dummyproject.Modal_Class.Listitem;
import com.example.virtuzo.dummyproject.R;

import java.util.List;

/**
 * Created by Virtuzo on 12/04/18.
 */

public class University_adapter extends RecyclerView.Adapter<University_adapter.MyViewholder>
{
    private List<String>listitems;
    private Context context;
    private University_adapter.OnClickListener listener;


    public University_adapter(List<String> listitems, Context context, University_adapter.OnClickListener onClickListener)
    {
        this.listitems = listitems;
        this.context=context;
        this.listener = onClickListener;

    }

    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem,parent,false);
        return new MyViewholder(v);
    }

    @Override
    public void onBindViewHolder(MyViewholder holder, int position) {
        final String listItem=listitems.get(position);
        holder.textviewheading.setText(listItem);
        holder.bind(listItem);


    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }
    public interface OnClickListener {
        void onItemClick(String universityname);
    }

    public class MyViewholder extends RecyclerView.ViewHolder {
        public TextView textviewheading;
        public View myview;
        public MyViewholder(View itemView) {
            super(itemView);

            textviewheading=(TextView)itemView.findViewById(R.id.textviewhead);
            myview = itemView;
        }

        public void bind(final String universityname)
        {
            myview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    listener.onItemClick(universityname);
                }
            });
        }


    }
}

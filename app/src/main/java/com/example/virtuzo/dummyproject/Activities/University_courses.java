package com.example.virtuzo.dummyproject.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.virtuzo.dummyproject.Adapters.Postgraduation_adapter;
import com.example.virtuzo.dummyproject.Adapters.Undergraduation_adapter;
import com.example.virtuzo.dummyproject.Adapters.University_adapter;
import com.example.virtuzo.dummyproject.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class University_courses extends AppCompatActivity {
TextView  textView;
    private RecyclerView recyclerView_pg,recyclerView_ug;
    private RecyclerView.Adapter adapter_postgraduation,adapter_undergraduation;
    private List<String>postgraduationlist;
    private List<String>undergraduationlist;
    private StorageReference islandRef;
     private FirebaseStorage storage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storage = FirebaseStorage.getInstance();
        // storageRef= storage.getReferenceFromUrl("gs://fir-18244.appspot.com/MCA.pdf");


        setContentView(R.layout.activity_university_courses);
        textView=(TextView)findViewById(R.id.textView2) ;
        Intent intent=getIntent();
        String name = intent.getStringExtra("university_name");
        textView.setText(name);
        recyclerView_pg =(RecyclerView) findViewById(R.id.recyclerview_pg);
        recyclerView_pg.setHasFixedSize(true);       // all list iszes has fixed size
        recyclerView_pg.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        postgraduationlist= new ArrayList<>();
        postgraduationlist.add("MCA");
        postgraduationlist.add("MBA");
        postgraduationlist.add("M-TECH");
        postgraduationlist.add("MCOM");
        postgraduationlist.add("MPHARM");
        postgraduationlist.add("MSC");

        adapter_postgraduation = new Postgraduation_adapter(postgraduationlist, getApplicationContext(), new Postgraduation_adapter.OnClickListener() {


            @Override
            public void onItemClick(String postgraduation_name) {

                Intent intent;


                switch (postgraduation_name)
                {
                    case "MCA":

//                   intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://firebasestorage.googleapis.com/v0/b/fir-18244.appspot.com/o/MCA.pdf?alt=media&token=d543311a-fa5e-4258-aeca-bb159cfd07c9"));
//                    startActivity(intent);

                    break;

                    case "MBA":
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://firebasestorage.googleapis.com/v0/b/fir-18244.appspot.com/o/MBA.pdf?alt=media&token=88a81ed8-8b80-4935-bf1d-5d674627d618"));
                        startActivity(intent);
                        break;


                    case "M-TECH":

                       Toast.makeText(getApplicationContext()," Under Construction",Toast.LENGTH_SHORT).show();
                        break;

                    case "MCOM":
                        Toast.makeText(getApplicationContext()," Under Construction",Toast.LENGTH_SHORT).show();
                        break;

                    case "MPHARM" :
                        Toast.makeText(getApplicationContext()," Under Construction",Toast.LENGTH_SHORT).show();
                        break;

                    case "MSC":
                        Toast.makeText(getApplicationContext()," Under Construction",Toast.LENGTH_SHORT).show();
                        break;

                }






            }
        });
        recyclerView_pg.setAdapter(adapter_postgraduation);



        recyclerView_ug =(RecyclerView) findViewById(R.id.recyclerview_ug);
        recyclerView_ug.setHasFixedSize(true);
        recyclerView_ug.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));// all list iszes has fixed size
        undergraduationlist= new ArrayList<>();
        undergraduationlist.add("BCA");
        undergraduationlist.add("BBA");
        undergraduationlist.add("B-TECH");
        undergraduationlist.add("BCOM");
        undergraduationlist.add("B-PHARM");
        undergraduationlist.add("BSC");
        adapter_undergraduation = new Undergraduation_adapter(undergraduationlist,getApplicationContext());
        recyclerView_ug.setAdapter(adapter_undergraduation);



    }
}

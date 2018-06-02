package com.example.virtuzo.dummyproject.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.virtuzo.dummyproject.Adapters.University_adapter;
//import com.example.virtuzo.dummyproject.Modal_Class.Listitem;
import com.example.virtuzo.dummyproject.R;
import com.example.virtuzo.dummyproject.Activities.University_courses;

import java.util.ArrayList;
import java.util.List;


public class University extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<String> university_listitems;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_university, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView =(RecyclerView) view.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);       // all list iszes has fixed size
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        university_listitems= new ArrayList<>();
        university_listitems.add("IP university");
        university_listitems.add("Delhi University (DU)");
        university_listitems.add("Jawahar Lal Nehru University(JNU)");
        university_listitems.add("Jamia Hamdard");
        university_listitems.add("Kurukshetra University");
        university_listitems.add("N I T");
        university_listitems.add("I I T");
        university_listitems.add("Aligargh Universiy");
        university_listitems.add("Punjab Technical University");
        university_listitems.add("lovely Professional University");
        university_listitems.add("Amity University");
        university_listitems.add("Maharshi Dayanand University");


        adapter = new University_adapter(university_listitems, getContext(), new University_adapter.OnClickListener() {
            @Override
            public void onItemClick(String universityname) {
              //  Toast.makeText(getContext()," item clicked",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(getContext(),University_courses.class);
                intent.putExtra("university_name",universityname);
                startActivity(intent);

            }
        });




        recyclerView.setAdapter(adapter);

    }


}

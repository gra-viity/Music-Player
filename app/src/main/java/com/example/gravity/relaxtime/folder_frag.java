package com.example.gravity.relaxtime;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


public class folder_frag extends Fragment {
    View view;
    RecyclerView recyclerView;
    ArrayAdapter arrayAdapter;
    Intent serviceIntent;
    boolean musicPlaying=false;


    public folder_frag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_folder_frag, container, false);
        recyclerView=view.findViewById(R.id.folder_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        serviceIntent=new Intent(getActivity(),MusicService.class);

        return view;
    }
    class myAdaptor extends RecyclerView.Adapter<myAdaptor.holder>{

        @Override
        public myAdaptor.holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(myAdaptor.holder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
        class holder extends RecyclerView.ViewHolder{


            public holder(View itemView) {
                super(itemView);
            }
        }
    }



}

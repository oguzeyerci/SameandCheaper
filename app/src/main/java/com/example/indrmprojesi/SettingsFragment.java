package com.example.indrmprojesi;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends Fragment {
    profileClass pro=new profileClass();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<String> basliklar=new ArrayList<String>();
    private List<String> ayrintilar=new ArrayList<String>();
    private List<String> magazalar=new ArrayList<String>();
    private List<String> turu=new ArrayList<String>();
    private List<String> gonderen=new ArrayList<String>();
    String favlayan;
    String mail;
    sqlite db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vq= inflater.inflate(R.layout.favori_ekrani,container,false);
        if(getArguments()!=null){
            mail=getArguments().getString("mail");
            Log.i("qTAG", mail+"0909");
        }
        db=new sqlite(vq.getContext());
        pro=db.profildondur(mail);
        favlayan=pro.getIsim();
        List<indirimClass> asd= db.favlistele(favlayan);
        for(int i=0;i<asd.size();i++){
            indirimClass ind = asd.get(i);
            turu.add(0,ind.turu);
            magazalar.add(0,ind.magaza);
            ayrintilar.add(0,ind.ayrinti);
            basliklar.add(0,ind.baslik);
            gonderen.add(0,ind.gonderen);
        }



        recyclerView = vq.findViewById(R.id.Recyclerview_Fav);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(vq.getContext());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new RecyclerViewAdapter(vq.getContext(),basliklar,ayrintilar,magazalar,turu,gonderen,favlayan);
        recyclerView.setAdapter(mAdapter);
        return vq;
    }
}

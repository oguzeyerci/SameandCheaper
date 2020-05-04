package com.example.indrmprojesi;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String mail;
    private List<String> basliklar=new ArrayList<String>();
    private List<String> ayrintilar=new ArrayList<String>();
    private List<String> magazalar=new ArrayList<String>();
    private List<String> turu=new ArrayList<String>();
    private List<String> gonderen=new ArrayList<String>();
    profileClass pro=new profileClass();
    sqlite db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.indirim_ekrani,container,false);
        if(getArguments()!=null){
            mail=getArguments().getString("mail");
            Log.i("qTAG", mail+"0909");
        }
        db=new sqlite(v.getContext());
        pro=db.profildondur(mail);

        basliklar.add("Sucuk");
        basliklar.add("Yüzüklerin Efendisi İki Kule");
        basliklar.add("iPhone 11");
        basliklar.add("Sarı Yağmurluk");
        basliklar.add("Nike Phantom Krampon");
        basliklar.add("Twenty One Pilots Trench");
        basliklar.add("Rotring 500");
        ayrintilar.add("15 TL");
        ayrintilar.add("28 TL");
        ayrintilar.add("6500 TL");
        ayrintilar.add("189 TL");
        ayrintilar.add("225 TL");
        ayrintilar.add("19 USD");
        ayrintilar.add("35 TL");
        magazalar.add("Ankamall Migros");
        magazalar.add("Podium D&R");
        magazalar.add("Serhat Altunbilekler");
        magazalar.add("Ankamall Colins");
        magazalar.add("Podium Nike");
        magazalar.add("amazon.de");
        magazalar.add("hepsiburada.com");
        turu.add("Gida");
        turu.add("Kitap");
        turu.add("Teknoloji");
        turu.add("Kiyafet");
        turu.add("Spor");
        turu.add("Müzik");
        turu.add("Kirtasiye");
        gonderen.add("Oğuzhan Eyerci");
        gonderen.add("Oğuzhan Eyerci");
        gonderen.add("Oğuzhan Eyerci");
        gonderen.add("Oğuzhan Eyerci");
        gonderen.add("Oğuzhan Eyerci");
        gonderen.add("Oğuzhan Eyerci");
        gonderen.add("Oğuzhan Eyerci");

        //db.insertIndirim("teknoloji","Logitech G29","2499TL","Ankamall MediaMarkt");
       List<indirimClass> asd= db.listele();

        for(int i=0;i<asd.size();i++){
            indirimClass ind = asd.get(i);
            turu.add(0,ind.turu);
            magazalar.add(0,ind.magaza);
            ayrintilar.add(0,ind.ayrinti);
            basliklar.add(0,ind.baslik);
            gonderen.add(0,ind.gonderen);
        }



        recyclerView = v.findViewById(R.id.Recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new RecyclerViewAdapter(v.getContext(),basliklar,ayrintilar,magazalar,turu,gonderen,pro.getIsim());
        recyclerView.setAdapter(mAdapter);


        FloatingActionButton btn= v.findViewById(R.id.floatingActionButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogActivity dialogActivity = new DialogActivity(pro.getIsim());
                dialogActivity.show(getFragmentManager(),"Dialog");
            }
        });


        return v;
    }
}

package com.example.indrmprojesi;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {
    private String isim,nick,mail;
    private TextView maill,telefonn;
    private TextView isimsoyisim;
    profileClass pro=new profileClass();
    sqlite db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.profil_ekrani,container,false);
        if(getArguments()!=null){
            mail=getArguments().getString("mail");
            Log.i("qTAG", mail+"1010");
        }

        db=new sqlite(v.getContext());
        pro=db.profildondur(mail);


        isimsoyisim=v.findViewById(R.id.isim);
        maill=v.findViewById(R.id.mail_text);
        telefonn=v.findViewById(R.id.telefon_text);

        isimsoyisim.setText(pro.getIsim());
        maill.setText(pro.getMail());
        telefonn.setText(pro.getTelefon_no());
        return v ;
    }


}


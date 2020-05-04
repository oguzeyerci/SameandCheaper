package com.example.indrmprojesi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

public class DialogActivity extends AppCompatDialogFragment implements AdapterView.OnItemSelectedListener {
    private EditText baslik,ayrinti,magaza;
    String turu,gonderen;
    sqlite db;

    public DialogActivity(String isim) {
        this.gonderen=isim;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog .Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view=inflater.inflate(R.layout.dialog,null);
        builder.setView(view)
                .setTitle("İndirim Ekle")
                .setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Ekle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db= new sqlite(view.getContext());
                        String s1=baslik.getText().toString();
                        String s2=ayrinti.getText().toString();
                        String s3=magaza.getText().toString();

                        if(s1.equals("")||s2.equals("")||s3.equals("")){
                            Toast.makeText(view.getContext(), "Lütfen Bilgileri Doldurunuz", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            db.insertIndirim(turu, s1, s2, s3, gonderen);
                        }
                    }
                });
        Spinner spinner=view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(view.getContext(),R.array.türler,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        baslik=view.findViewById(R.id.dialog_baslik);
        ayrinti=view.findViewById(R.id.dialog_ayrinti);
        magaza=view.findViewById(R.id.dialog_magaza);

        return builder.create();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        turu=parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

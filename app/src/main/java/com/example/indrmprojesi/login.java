package com.example.indrmprojesi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class login extends AppCompatActivity {
    private Button buton;
    EditText mail_ge,parola_ge;
    EditText isim_ko,telefon_ko,parola_ko,mail_ko;
    sqlite db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giris_ekrani);
        db= new sqlite(this);
        buton= findViewById(R.id.girisbutton);
        parola_ge=findViewById(R.id.parola_ge);
        mail_ge=findViewById(R.id.mail_ge);

        buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                girisyap();
            }
        });

        final TextView kayitol=findViewById(R.id.kayitol);
        kayitol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kayitol();
            }
        });

    }
    public void girisyap(){
        parola_ge=findViewById(R.id.parola_ge);
        mail_ge=findViewById(R.id.mail_ge);

        String maill=mail_ge.getText().toString().trim();
        String parolaa=parola_ge.getText().toString().trim();
        boolean abc=db.loginkontrol(maill,parolaa);

        if(abc==true){
            Toast.makeText(this, "Giriş Başarılı", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("girisnick",mail_ge.getText().toString());
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Giriş Başarısız", Toast.LENGTH_SHORT).show();
        }

    }
    public void kayitol(){
        setContentView(R.layout.kayit_ol);
        TextView giris=findViewById(R.id.girisyap);
        isim_ko=findViewById(R.id.isim_ko);
        mail_ko=findViewById(R.id.mail_ko);
        telefon_ko=findViewById(R.id.telefon_ko);
        parola_ko=findViewById(R.id.parola_ko);
        Button ko=findViewById(R.id.kayitol_ko);

        ko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1=isim_ko.getText().toString();
                String s2=telefon_ko.getText().toString();
                String s3=mail_ko.getText().toString();
                String s4=parola_ko.getText().toString();
                if(s1.equals("")||s2.equals("")||s3.equals("")||s4.equals("")){
                    Toast.makeText(login.this, "Lütfen Bilgileri Doldurunuz", Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean a=db.mailkontrol(s3);
                    if(a==true){
                        Boolean i=db.insert(s1,s3,s2,s4);
                        if(i==true){
                            Toast.makeText(login.this, "Kayıt Başarılı.\nGiriş Yapabilirsiniz.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(login.this, "Bu maile ait bir hesap var.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getIntent());
                finish();
                startActivity(intent);
            }
        });
    }





}

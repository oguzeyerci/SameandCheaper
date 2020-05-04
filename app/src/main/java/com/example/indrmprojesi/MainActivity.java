package com.example.indrmprojesi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView kaydetbutonu;
    private EditText mail,telefon,adres;
    private TextView isim,nick;
    String newString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profileClass profil = new profileClass();



        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString("girisnick");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("STRING_I_NEED");
        }

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);



        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view_tag,new StartFragment()).commit();


    }



    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment= null;
                    profileClass profil = new profileClass();

                    switch (item.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            Bundle abyss = new Bundle();
                            abyss.putString("mail",newString);
                            selectedFragment.setArguments(abyss);
                            break;
                        case R.id.nav_favorites:
                            selectedFragment = new SettingsFragment();
                            Bundle args1 = new Bundle();
                            args1.putString("mail",newString);
                            selectedFragment.setArguments(args1);
                            break;
                        case R.id.nav_profil:
                            selectedFragment = new ProfileFragment();
                            Bundle args = new Bundle();
                            args.putString("mail",newString);
                            selectedFragment.setArguments(args);
                            break;
                    }


                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view_tag,selectedFragment).commit();
                    return true;
                }
            };




    private void hideSystemUI() {

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    @Override
    public void onClick(View v) {
    }
}

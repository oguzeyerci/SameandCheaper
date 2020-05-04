package com.example.indrmprojesi;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private List<String> basliklar = new ArrayList<>();
    private List<String> ayrintilar = new ArrayList<>();
    private List<String> magazalar = new ArrayList<>();
    private List<String> turu= new ArrayList<>();
    private List<String> gonderen= new ArrayList<>();
    private Context mContext;
    private String kullanici;
    sqlite db;

    public RecyclerViewAdapter( Context mContext,List<String> basliklar, List<String> ayrintilar, List<String> magazalar,List<String> turu,List<String> gonderen,String favlayan) {
        this.basliklar = basliklar;
        this.ayrintilar = ayrintilar;
        this.magazalar= magazalar;
        this.mContext = mContext;
        this.gonderen=gonderen;
        this.kullanici=favlayan;
        this.turu=turu;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view,parent,false);
        ViewHolder holder = new ViewHolder(view);
        db=new sqlite(view.getContext());
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

            holder.baslik.setText(basliklar.get(position));
            holder.ayrinti.setText(ayrintilar.get(position));
            holder.magaza.setText(magazalar.get(position));
            holder.gonderen_text.setText(gonderen.get(position));

        //her tür için farklı icon
        switch (turu.get(position)){
            case "Teknoloji" :
                holder.image.setImageResource(R.drawable.technology);
                break;
            case "Market" :
                holder.image.setImageResource(R.drawable.shopicon);
                break;
            case "Kiyafet" :
                holder.image.setImageResource(R.drawable.clothes);
                break;
            case "Spor" :
                holder.image.setImageResource(R.drawable.sports);
                break;
            case "Kitap" :
                holder.image.setImageResource(R.drawable.bookicon);
                break;
            case "Müzik" :
                holder.image.setImageResource(R.drawable.musicicon);
                break;
            case "Kirtasiye" :
                holder.image.setImageResource(R.drawable.pencil);
                break;
            case "Evaletleri" :
                holder.image.setImageResource(R.drawable.evaletleri);
                break;
            case "Gida" :
                holder.image.setImageResource(R.drawable.food);
                break;
        }

        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,basliklar.get(position)+" "+ayrintilar.get(position),Toast.LENGTH_SHORT).show();
            }
        });

        final indirimClass ind=new indirimClass();
        ind.setTuru(turu.get(position));
        ind.setBaslik(basliklar.get(position));
        ind.setMagaza(magazalar.get(position));
        ind.setAyrinti(ayrintilar.get(position));
        ind.setGonderen(gonderen.get(position));
        ind.setFavlayan(kullanici);

        //halihazırda favori ise kalki dolduruyor
        if(db.isfav(ind)){
            holder.fav.setImageResource(R.drawable.ic_favorite_black_24dp);
        }
        //bu favoriye basınca koyu renk yapıyor.
        holder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!db.isfav(ind)){
                    db.favaekle(ind);
                    Log.i("qTAG", String.valueOf(ind.getFavlayan()));
                    holder.fav.setImageResource(R.drawable.ic_favorite_black_24dp);
                    Toast.makeText(mContext, "Favorilere Eklendi", Toast.LENGTH_SHORT).show();
                }
                else{
                    db.favdansil(ind);
                    holder.fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    Toast.makeText(mContext, "Favorilerden kaldırıldı", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return basliklar.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView baslik,ayrinti,magaza,gonderen_text;
        ImageView fav,image;
        RelativeLayout parent_layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            baslik=itemView.findViewById(R.id.baslik);
            ayrinti=itemView.findViewById(R.id.ayrinti);
            magaza=itemView.findViewById(R.id.magaza);
            fav=itemView.findViewById(R.id.fav);
            gonderen_text=itemView.findViewById(R.id.gonderen_Text);
            image=itemView.findViewById(R.id.circleimage);
            parent_layout=itemView.findViewById(R.id.parent_layout);
        }
    }
}

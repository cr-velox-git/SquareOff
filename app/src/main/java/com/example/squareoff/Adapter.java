package com.example.squareoff;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.Viewholder> {

private List<Model> modelList;

    public Adapter(List<Model> modelList) {
        this.modelList = modelList;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull Adapter.Viewholder holder, int position) {

        String name = modelList.get(position).getName();
        String slug = modelList.get(position).getSlug();
        String img = modelList.get(position).getImg();
        long status = modelList.get(position).getStatus();

        boolean even = false;
        if (position%2 == 0){
            even = true;
        }


        holder.setData(name, slug,img,status,even);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        private TextView nameText, slugText, statusText;
        private ImageView image;
        private ConstraintLayout layout;

        public Viewholder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.name);
            slugText = itemView.findViewById(R.id.slug);
            statusText = itemView.findViewById(R.id.status);
            image = itemView.findViewById(R.id.image);
            layout = itemView.findViewById(R.id.item_background);
        }

        @SuppressLint({"SetTextI18n", "ResourceAsColor"})
        public void setData(String name, String slug, String img, long status, boolean even) {


            if (itemView.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                nameText.setText("Name:- " + name + " Slug:- " + slug);

                String slu[] = slug.split("-");

               if (even){
                   layout.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#9EE8B365")));
               }

                slugText.setText("Year:- " + slu[slu.length - 1]);
                statusText.setText("No of Dash Character " + String.valueOf(slu.length - 1));
                if (!img.equals("")) {
                    Glide.with(itemView.getContext()).load(img).into(image);
                } else {
                    image.setVisibility(View.GONE);
                }


            }else if(itemView.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                nameText.setText(name);

                if (!img.equals("")){
                    Glide.with(itemView.getContext()).load(img).into(image);
                }else{
                    image.setVisibility(View.INVISIBLE);
                    itemView.setVisibility(View.GONE);
                }

            }



        }
    }

}

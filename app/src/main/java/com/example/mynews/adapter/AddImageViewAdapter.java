package com.example.mynews.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mynews.R;
import com.example.mynews.bean.Categoryitem;
import com.example.mynews.bean.ImageModel;

import java.util.ArrayList;
import java.util.List;

public class AddImageViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;

    private List<ImageModel> imageModelList =new ArrayList<>();



    public AddImageViewAdapter(Context context, List<ImageModel> imageModelList) {
        this.context = context;
        this.imageModelList = imageModelList;
    }



    public List<ImageModel>  getIM(){
        return imageModelList;
    }




    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new AddImageViewHolder(LayoutInflater.from(context).inflate(R.layout.add_image_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
              AddImageViewHolder addImageViewHolder= (AddImageViewHolder) holder;
              Glide.with(context).load(imageModelList.get(position).getImageurl()).into(addImageViewHolder.add_image);
              addImageViewHolder.remove_image.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {

                  }
              });
    }

    @Override
    public int getItemCount() {
        return imageModelList.size();
    }



    class AddImageViewHolder extends  RecyclerView.ViewHolder{

        ImageView add_image;

        ImageView remove_image;

        public AddImageViewHolder(View itemView) {
            super(itemView);
            add_image=(ImageView) itemView.findViewById(R.id. add_image);
            remove_image=(ImageView) itemView.findViewById(R.id.remove_image);

        }
    }
}

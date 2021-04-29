package com.example.apptourdulich;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class SlideListTourAdapter extends RecyclerView.Adapter<SlideListTourAdapter.SlideViewHolder> {
    List<Photo> mPhoto;
    ViewPager2 viewPager2;
    public SlideListTourAdapter (List<Photo> mPhoto,ViewPager2 viewPager2){
        this.mPhoto=mPhoto;
        this.viewPager2=viewPager2;
    }
    @NonNull
    @Override
    public SlideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SlideViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fm_slide_listtour,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SlideViewHolder holder, int position) {
        holder.setImageView(mPhoto.get(position));
        if(position==mPhoto.size()-2){
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return mPhoto.size();
    }

    class SlideViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView imageView;

        SlideViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.slideImage);
        }


        void setImageView(Photo photo) {
            imageView.setImageResource(photo.getResourceID());
        }
    }
    public Runnable runnable=new Runnable() {
        @Override
        public void run() {
            mPhoto.addAll(mPhoto);
            notifyDataSetChanged();
        }
    };
}
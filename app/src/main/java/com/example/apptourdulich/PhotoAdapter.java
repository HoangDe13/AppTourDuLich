package com.example.apptourdulich;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.List;

public class PhotoAdapter extends PagerAdapter{
    private Context mContext;
    private List<Photo> mPhoTo;
    public PhotoAdapter (Context mContext, List<Photo> mPhoTo){
        this.mContext=mContext;
        this.mPhoTo=mPhoTo;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view= LayoutInflater.from(container.getContext()).inflate(R.layout.layout,container,false);
        ImageView imageView=view.findViewById(R.id.img_Photo);
        Photo photo=mPhoTo.get(position);
        if(photo!=null)
        {
            Glide.with(mContext).load(photo.getResourceID()).into(imageView);
        }
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        if(mPhoTo!=null)
        {
            return mPhoTo.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}

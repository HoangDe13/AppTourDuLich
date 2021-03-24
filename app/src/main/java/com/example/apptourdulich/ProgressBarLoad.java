package com.example.apptourdulich;

import android.content.Context;
import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressBarLoad extends Animation{

    public Context context;
    public ProgressBar progressBar;
    public TextView tv_Load;
    public float from;
    public float to;

    public ProgressBarLoad(Context context,ProgressBar progressBar,TextView tv_Load,float from,float to){
        this.context=context;
        this.progressBar=progressBar;
        this.tv_Load=tv_Load;
        this.from=from;
        this.to=to;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);

        float value=from+(to-from)*interpolatedTime;
        progressBar.setProgress((int)value);
        tv_Load.setText((int)value+" %");

        if(value==to){
            context.startActivity(new Intent(context,StartActivity.class));
        }

    }
}


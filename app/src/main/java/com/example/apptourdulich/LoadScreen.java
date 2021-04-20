package com.example.apptourdulich;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoadScreen extends AppCompatActivity {

    ProgressBar progressBar;
    TextView tvLoadl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_screen);

        progressBar=findViewById(R.id.progress_Bar_Load);
        tvLoadl=findViewById(R.id.tv_phantramLoad);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        progressBar.setMax(100);
        progressBar.setScaleY(3f);

        progressBarLoad();
    }
    public void progressBarLoad(){
        ProgressBarLoad load=new ProgressBarLoad(this,progressBar,tvLoadl,0f,100f);
        load.setDuration(7000);
        progressBar.setAnimation(load);

    }
}
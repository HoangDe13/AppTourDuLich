package com.example.apptourdulich;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class ChangeLanguage extends AppCompatActivity {

    TextView tvvn,tven;
    ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_language);
        tvvn=findViewById(R.id.tvVn);
        Bundle b= getIntent().getExtras();
        String sdt= b.getString("SoDienThoai");

        imgBack=findViewById(R.id.imgBackLanguage);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tven=findViewById(R.id.tvEn);
        tvvn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ChangeLanguage.this,Home.class);
                changeLanguage("vi");
                //xử lý nhấp nháy khi click vào textview
                Animation mAnimation = new AlphaAnimation(1, 0);
                mAnimation.setDuration(200);
                mAnimation.setRepeatCount(Animation.INFINITE);
                mAnimation.setRepeatMode(Animation.REVERSE);
                tvvn.startAnimation(mAnimation);
                i.putExtra("SoDienThoai",sdt);
                startActivity(i);
            }
        });
        tven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ChangeLanguage.this,Home.class);
                changeLanguage("en");
                //xử lý nhấp nháy khi click vào textview
                Animation mAnimationm = new AlphaAnimation(1, 0);
                mAnimationm.setDuration(200);
                mAnimationm.setRepeatCount(Animation.INFINITE);
                mAnimationm.setRepeatMode(Animation.REVERSE);
                tven.startAnimation(mAnimationm);
                i.putExtra("SoDienThoai",sdt);
                startActivity(i);
            }
        });

    }
    private void changeLanguage(String language){
        Locale locale=new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration =new Configuration();
        configuration.locale=locale;
        getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor=getSharedPreferences("Settings", Context.MODE_PRIVATE).edit();
        editor.putString("My_Lang",language);
        editor.apply();
    }

}
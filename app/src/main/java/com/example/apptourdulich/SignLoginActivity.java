package com.example.apptourdulich;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SignLoginActivity extends AppCompatActivity {
 Button dangnhap,dangki;
 TextView Quenmatkhau;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_login);

        dangnhap=(Button)findViewById(R.id.btndn);
        dangki=(Button)findViewById(R.id.btnDangKi);

        dangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(SignLoginActivity.this,sign_up.class);
                startActivity(i);
            }
        });

        dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SignLoginActivity.this,HomeScreen.class);
                startActivity(i);
            }
        });
    }
}
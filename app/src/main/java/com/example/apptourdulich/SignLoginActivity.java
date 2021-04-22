package com.example.apptourdulich;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignLoginActivity extends AppCompatActivity {
 Button dangnhap;
 TextView  dangki;
 EditText edtSoDienThoai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_login);

        dangnhap=findViewById(R.id.btndn);
        dangki=findViewById(R.id.btnDangKi);
        edtSoDienThoai=findViewById(R.id.edtSDT);
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
                Intent i=new Intent(SignLoginActivity.this,Confirm_otp.class);
                Bundle b= new Bundle();
                b.putString("SoDienThoai",edtSoDienThoai.getText().toString());
                i.putExtras(b);
                startActivity(i);
            }
        });
    }
}
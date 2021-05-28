package com.example.apptourdulich;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Confirm_otp extends AppCompatActivity {
    Button btnXacNhan;
    TextView tvSoDienThoai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_otp);
        btnXacNhan=findViewById(R.id.btnXacNhanOTP);
        tvSoDienThoai=findViewById(R.id.tvSoDienThoaiConfirm);
        Bundle b= getIntent().getExtras();
        String sdt=(b.getString("SoDienThoai"));
        tvSoDienThoai.setText(sdt);


        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Confirm_otp.this,Home.class);
                startActivity(i);
            }
        });
    }
}
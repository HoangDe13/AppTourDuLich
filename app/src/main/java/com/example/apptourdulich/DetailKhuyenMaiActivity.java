package com.example.apptourdulich;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailKhuyenMaiActivity extends AppCompatActivity {
    TextView Ten,NgayBatDau,NgayKetThuc,Ma,ThongTin;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_khuyen_mai);
        Ten=findViewById(R.id.tvTenKhuyenMaiDetail);
        NgayBatDau=findViewById(R.id.tvNgayBatDauKhuyenMai);
        NgayKetThuc=findViewById(R.id.tvNgayKetThucKhuyenMai);
        Ma=findViewById(R.id.tvMaKhuyenMai);
        ThongTin=findViewById(R.id.tvThongTinkhuyenMai);
        back=findViewById(R.id.imgBackDetailKM);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Bundle b= getIntent().getExtras();

        Ma.setText(b.getString("MaKhuyenMai"));
        ThongTin.setText(b.getString("ThongTin"));
        NgayBatDau.setText(b.getString("NgayBatDau"));
        NgayKetThuc.setText(b.getString("NgayKetThuc"));
        Ten.setText(  b.getString("Ten"));


    }
}
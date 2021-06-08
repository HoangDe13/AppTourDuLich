package com.example.apptourdulich;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailKhuyenMaiActivity extends AppCompatActivity {
    TextView Ten,NgayBatDau,NgayKetThuc,Ma,ThongTin;
    ImageView back;
    Button btncopy;
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
        btncopy=findViewById(R.id.tvSaoChep);
        btncopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                }
                ClipData clip = ClipData.newPlainText("text", Ma.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(DetailKhuyenMaiActivity.this,"Sao chép thành công !",Toast.LENGTH_SHORT).show();
            }
        });
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
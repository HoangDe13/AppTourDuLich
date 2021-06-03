package com.example.apptourdulich;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class Bill extends AppCompatActivity {
    TextView TenTour,NgayKhoiHanh,NoiKhoiHanh,SoNgay,HoTenBill,SoDienThoai,DiaChi,SLNguoiLon,SLTreEm,TienNguoiLon,TienTreEm,ChietKhau,TongTien,TongThanhToan;
    DatabaseReference databaseReference;
    Button ApDungKM;
    ImageView back;
    EditText MaKhuyenMai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        TenTour=findViewById(R.id.tvTenTourBill);
        NgayKhoiHanh=findViewById(R.id.tvNgayKhoiHanhBill);
        NoiKhoiHanh=findViewById(R.id.tvNoiKhoiHanhBill);
        SoNgay=findViewById(R.id.tvSoNgayBill);
        HoTenBill=findViewById(R.id.tvHoTenBill);
        SoDienThoai=findViewById(R.id.tvSoDienThoaiBill);
        DiaChi=findViewById(R.id.tvDiaChiBill);
        SLNguoiLon=findViewById(R.id.tvSoLuongNguoiLonBill);
        SLTreEm=findViewById(R.id.tvSoLuongTreEmBill);
        TienNguoiLon=findViewById(R.id.tvGiaNguoiLonBill);
        TienTreEm=findViewById(R.id.tvGiaTreEmBill);
        ChietKhau=findViewById(R.id.tvChietKhauBill);
        TongThanhToan=findViewById(R.id.tvSoTienThanhToanBill);
        TongTien=findViewById(R.id.tvTongTienBill);
        back=findViewById(R.id.tvBackBill);
        ApDungKM=findViewById(R.id.btnApDungKhuyenMai);
        MaKhuyenMai=findViewById(R.id.etMaKhuyenMaiBill);
        Bundle b=getIntent().getExtras();
        int id=b.getInt("IDTour");
        int ChietKhau=0;
        int SoLuongNguoiLon=b.getInt("NguoiLon");
        int SoLuongTreEm=b.getInt("TreEm");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ApDungKM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        SLNguoiLon.setText(String.valueOf(SoLuongNguoiLon));
        SLTreEm.setText(String.valueOf(SoLuongTreEm));
        databaseReference= FirebaseDatabase.getInstance().getReference("Tour");
        databaseReference.child(String.valueOf(id)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        DataSnapshot dataSnapshot = task.getResult();
                        String Ten = String.valueOf(dataSnapshot.child("tenTour").getValue());
                        String donGia = String.valueOf(dataSnapshot.child("donGia").getValue());
                        String ngayKhoiHanh = String.valueOf(dataSnapshot.child("ngayKhoiHanh").getValue());
                        String noiKhoiHanh = String.valueOf(dataSnapshot.child("noiKhoiHanh").getValue());
                        String soNgay = String.valueOf(dataSnapshot.child("soNgay").getValue());
                        TenTour.setText(Ten);
                        NgayKhoiHanh.setText(ngayKhoiHanh);
                        NoiKhoiHanh.setText(noiKhoiHanh);
                        SoNgay.setText(soNgay);
                        int DonGiaTinh=Integer.parseInt(donGia);
                        int GiaNguoiLon=SoLuongNguoiLon*DonGiaTinh;
                        int GiaTreEm=SoLuongTreEm*(DonGiaTinh*3/4);
                        TienNguoiLon.setText(String.valueOf(GiaNguoiLon));
                        TienTreEm.setText(String.valueOf(GiaTreEm));
                        int Tong=GiaNguoiLon+GiaTreEm;
                        TongTien.setText(String.valueOf(Tong));

                        int ThanhToan=Tong-ChietKhau;
                        TongThanhToan.setText(String.valueOf(ThanhToan));


                    }
                }
            }
        });
    }
}

package com.example.apptourdulich;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;


import java.text.SimpleDateFormat;
import java.util.Date;

import java.text.DecimalFormat;
import java.text.NumberFormat;


public class Bill extends AppCompatActivity {
    TextView TenTour,NgayKhoiHanh,NoiKhoiHanh,SoNgay,HoTenBill,SoDienThoaiBill,DiaChi,
            SLNguoiLon,SLTreEm,TienNguoiLon,TienTreEm,ChietKhau,TongTien,TongThanhToan;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferenceKhachHang;
    DatabaseReference databaseReferenceKhuyenMai;
    DatabaseReference refThongBao;
    Button ApDungKM,btnThanhToan;
    ImageView back;
    EditText MaKhuyenMai;
    long maxid,maxidThongBao;
    DatabaseReference Ref;
    int DonGiaTinh;
    int ThanhToan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        TenTour=findViewById(R.id.tvTenTourBill);
        NgayKhoiHanh=findViewById(R.id.tvNgayKhoiHanhBill);
        NoiKhoiHanh=findViewById(R.id.tvNoiKhoiHanhBill);
        SoNgay=findViewById(R.id.tvSoNgayBill);
        HoTenBill=findViewById(R.id.tvHoTenBill);
        SoDienThoaiBill=findViewById(R.id.tvSoDienThoaiBill);
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
        btnThanhToan=findViewById(R.id.btnThanhToan);

        Bundle b=getIntent().getExtras();
        String SoDienThoai=b.getString("SoDienThoai");

        int id=b.getInt("IDTour");
        //int ChietKhau=0;
        int SoLuongNguoiLon=b.getInt("NguoiLon");
        int SoLuongTreEm=b.getInt("TreEm");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String KhuyenMai=MaKhuyenMai.getText().toString().trim();

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

                        DonGiaTinh=Integer.parseInt(donGia);


                        int DonGiaTinh=Integer.parseInt(donGia);

                        int GiaNguoiLon=SoLuongNguoiLon*DonGiaTinh;

                        int DonGiaTreEm=DonGiaTinh/2;
                        int GiaTreEm=SoLuongTreEm*DonGiaTreEm;

                        NumberFormat fmDonGia = new DecimalFormat("#,###");
                        double DonGia = Double.parseDouble(String.valueOf(GiaNguoiLon));
                        String fmdongiaNguoiLon = fmDonGia.format(DonGia);
                        TienNguoiLon.setText(fmdongiaNguoiLon);

                        double DonGia1= Double.parseDouble(String.valueOf(GiaTreEm));
                        String fmdongiatreem = fmDonGia.format(DonGia1);
                        TienTreEm.setText(String.valueOf(fmdongiatreem));



                        int Tong=GiaNguoiLon+GiaTreEm;

                        double DonGia4 = Double.parseDouble(String.valueOf(Tong));
                        String fmdongiaTong = fmDonGia.format(DonGia4);


                        TongTien.setText(String.valueOf(fmdongiaTong));
                        int TinhChietKhau=Integer.parseInt(ChietKhau.getText().toString());
                        ThanhToan=Tong-(Tong*(TinhChietKhau/100));

                        double DonGia3 = Double.parseDouble(String.valueOf(ThanhToan));
                        String fmdongiathanhtoan = fmDonGia.format(DonGia3);
                        TongThanhToan.setText(fmdongiathanhtoan);

                    }
                }
            }
        });

        databaseReferenceKhachHang= FirebaseDatabase.getInstance().getReference("KhachHang");
        databaseReferenceKhachHang.orderByChild("sdt").equalTo(SoDienThoai).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    KhachHang kh = child.getValue(KhachHang.class);
                    HoTenBill.setText(kh.getHoTen());
                    DiaChi.setText(kh.getDiaChi());
                    SoDienThoaiBill.setText(kh.getSDT());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ApDungKM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReferenceKhuyenMai= FirebaseDatabase.getInstance().getReference("KhuyenMai");
                databaseReferenceKhuyenMai.orderByChild("maKhuyenMai").equalTo(MaKhuyenMai.getText().toString().trim()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot child : snapshot.getChildren()) {
                            if (child != null) {
                                ThongTinKhuyenMai kh = child.getValue(ThongTinKhuyenMai.class);
                                ChietKhau.setText(String.valueOf(kh.getChietKhau())+"%");

                                int GiaNguoiLon=SoLuongNguoiLon*DonGiaTinh;
                                int DonGiaTreEm=DonGiaTinh/2;
                                int GiaTreEm=SoLuongTreEm*DonGiaTreEm;
                                int Tong=GiaNguoiLon+GiaTreEm;
                                ThanhToan=Tong-(Tong*kh.getChietKhau()/100);
                                NumberFormat fmDonGia = new DecimalFormat("#,###");
                                double thanhtoan = Double.parseDouble(String.valueOf(ThanhToan));
                                String tt = fmDonGia.format(thanhtoan);
                                TongThanhToan.setText(tt);
                            } else {
                                ChietKhau.setText("0");
                                Toast.makeText(Bill.this, "Không tìm thấy khuyến mãi", Toast.LENGTH_SHORT).show();
                                MaKhuyenMai.setText("");
                            }

                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


        ThongTinHoaDon hoaDon=new ThongTinHoaDon();
        Ref = FirebaseDatabase.getInstance().getReference().child("HoaDon");
        Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    maxid = snapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ThongTinThongBao thongBao=new ThongTinThongBao();
        refThongBao = FirebaseDatabase.getInstance().getReference().child("ThongBao");
        refThongBao.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    maxid = snapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sdt=SoDienThoaiBill.getText().toString().trim();
                int NL=Integer.parseInt(SLNguoiLon.getText().toString().trim());
                int TE=Integer.parseInt(SLTreEm.getText().toString().trim());

                String KhuyenMaiid=MaKhuyenMai.getText().toString().trim();
                String noidung="Cảm ơn quý khách đã đặt thành công chuyến đi "+TenTour.getText().toString().trim()+", được khởi hành vào ngày "+NgayKhoiHanh.getText().toString().trim();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String currentDateandTime = sdf.format(new Date());
                hoaDon.setMaHoaDon((int)maxid + 1);
                hoaDon.setMaKhuyenMai(KhuyenMaiid);
                hoaDon.setMaTour(id);
                hoaDon.setSoLuongNguoiLon(NL);
                hoaDon.setSoLuongTreEm(TE);
                hoaDon.setSoDienThoai(sdt);

                hoaDon.setNgayThanhToan(currentDateandTime);
                hoaDon.setTongTien(ThanhToan);
                thongBao.setMaThongBao((int)maxidThongBao+1);
                thongBao.setNgayThongBao(currentDateandTime);
                thongBao.setNoiDung(noidung);
                thongBao.setSoDienThoai(SoDienThoaiBill.getText().toString().trim());
                Ref.push().setValue(hoaDon);
                refThongBao.push().setValue(thongBao);

                Toast.makeText(Bill.this, "Thanh Toán Thành Công", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(Bill.this,Home.class);
                Bundle b=new Bundle();
                b.putString("SoDienThoai",sdt);
                i.putExtras(b);
                startActivity(i);
            }
        });


    }
}

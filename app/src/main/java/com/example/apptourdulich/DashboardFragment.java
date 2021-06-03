package com.example.apptourdulich;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;

import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.apptourdulich.ui.dashboard.DashboardViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardFragment extends Fragment {

    Button CongLon, TruLon,CongTre,TruTre;
    int slnguoilon,sltreem;
    EditText NguoiLon,TreEm;

    DatabaseReference databaseReference;

    private DashboardViewModel dashboardViewModel;
    TextView ngayKhoiHanh,ngayKetThuc,noiKhoiHanh,phuongTien,giaVe;
    EditText edtSLNguoiLon,edtSLTreEm;
    Button btnDatTour;
    int id;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ngayKhoiHanh=root.findViewById(R.id.tvNgayKhoiHanh);
        ngayKetThuc=root.findViewById(R.id.tvNgayKetThuc);
        noiKhoiHanh=root.findViewById(R.id.tvNoiKhoihanh);
        phuongTien=root.findViewById(R.id.tvPhuongTien);
        giaVe=root.findViewById(R.id.tvGiaVe);
        btnDatTour=root.findViewById(R.id.btnDatTour);
        edtSLNguoiLon=root.findViewById(R.id.edtSoLuongNguoiLon);
        edtSLTreEm=root.findViewById(R.id.edtSoLuongTreEm);
        btnDatTour=root.findViewById(R.id.btnDatTour);
        //khai báo

        CongLon=root.findViewById(R.id.btnCongNguoiLon);
        CongTre=root.findViewById(R.id.btnCongTreEm);
        TruLon=root.findViewById(R.id.btnTruNguoiLon);
        TruTre= root.findViewById(R.id.btnTruTreEm);
        NguoiLon=root.findViewById(R.id.edtSoLuongNguoiLon);
        TreEm=root.findViewById(R.id.edtSoLuongTreEm);
//        slnguoilon=Integer.parseInt(NguoiLon.getText().toString().trim());
//        sltreem=Integer.parseInt(TreEm.getText().toString().trim());
        CongLon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slnguoilon=Integer.parseInt(NguoiLon.getText().toString().trim());
                slnguoilon=slnguoilon+1;
                NguoiLon.setText(slnguoilon);
            }
        });
        TruLon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slnguoilon=Integer.parseInt(NguoiLon.getText().toString().trim());
                slnguoilon=slnguoilon-1;
                NguoiLon.setText(slnguoilon);
            }
        });
        CongTre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sltreem=Integer.parseInt(TreEm.getText().toString().trim());
                sltreem=sltreem+1;
                TreEm.setText(sltreem);
            }
        });
        TruTre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sltreem=Integer.parseInt(TreEm.getText().toString().trim());
                sltreem=sltreem-1;
                TreEm.setText(sltreem);


        Bundle i= getActivity().getIntent().getExtras();
        id=i.getInt("IDTour");
        String SoDienThoai=i.getString("SoDienThoai");
        databaseReference= FirebaseDatabase.getInstance().getReference("Tour");
        databaseReference.child(String.valueOf(id)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        DataSnapshot dataSnapshot = task.getResult();
                        String phuongTienstr = String.valueOf(dataSnapshot.child("phuongTien").getValue());
                        String khachSanstr = String.valueOf(dataSnapshot.child("khachSan").getValue());
                        String donGiastr = String.valueOf(dataSnapshot.child("donGia").getValue());
                        String ngayKhoiHanhstr= String.valueOf(dataSnapshot.child("ngayKhoiHanh").getValue());
                        String ngayKetThucstr = String.valueOf(dataSnapshot.child("ngayketThuc").getValue());
                        String noiKhoiHanhstr = String.valueOf(dataSnapshot.child("noiKhoiHanh").getValue());
                        String soNgaystr = String.valueOf(dataSnapshot.child("soNgay").getValue());
                        String imagestr = String.valueOf(dataSnapshot.child("image").getValue());
                        ngayKhoiHanh.setText(ngayKhoiHanhstr);
                        ngayKetThuc.setText(ngayKetThucstr);
                        noiKhoiHanh.setText(noiKhoiHanhstr);
                        phuongTien.setText(phuongTienstr);
                        giaVe.setText(donGiastr);




                    }
                }
            }
        });

                    btnDatTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(root.getContext(),Bill.class);
                Bundle b=new Bundle();
                int NL=Integer.parseInt(edtSLNguoiLon.getText().toString());
                int TE=Integer.parseInt(edtSLTreEm.getText().toString());
                b.putString("SoDienThoai",SoDienThoai);
                b.putInt("IDTour",id);
                b.putInt("NguoiLon",NL);
                b.putInt("TreEm",TE);
                i.putExtras(b);
                startActivity(i);
            }
        });
        return root;

    }

}

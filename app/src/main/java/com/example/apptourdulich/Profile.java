package com.example.apptourdulich;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class Profile extends AppCompatActivity {
    TextView HoTen,SoDienThoai,GioiTinh,NgaySinh,CMND,DiaChi;
    DatabaseReference databaseReference;
    Button dangxuat;
    ImageView imageView,imgBack;
    FloatingActionButton CapNhat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        dangxuat=findViewById(R.id.btnDangXuat);
        Bundle b= getIntent().getExtras();
        String sdt= b.getString("SoDienThoai");
        CapNhat= findViewById(R.id.floatProfile);
        imgBack=findViewById(R.id.imgBackProfile);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        databaseReference= FirebaseDatabase.getInstance().getReference("KhachHang");
        databaseReference.orderByChild("sdt").equalTo(sdt).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot child : snapshot.getChildren()) {
                            KhachHang kh = child.getValue(KhachHang.class);

                            DiaChi.setText(kh.getDiaChi());
                            HoTen.setText(kh.getHoTen());
                            SoDienThoai.setText(kh.getSDT());
                            GioiTinh.setText(kh.getGioitinh());
                            NgaySinh.setText(kh.getNgaySinh());
                            CMND.setText(kh.getCMND());
                            String img = String.valueOf(kh.getImageid());
                            Task<Uri> task = FirebaseStorage.getInstance().getReference().child("Images/" + img).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    System.out.println(uri);
                                    Glide.with(getApplicationContext()).load(uri).into(imageView);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });


        dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i= new Intent(getApplicationContext(),SignLoginActivity.class);
                startActivity(i);
            }
        });
        CapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),UpdateProfile.class);
                i.putExtra("SoDienThoai",sdt);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        int id=3;
        HoTen=findViewById(R.id.tvHoTen);
        GioiTinh=findViewById(R.id.tvGioiTinh);
        NgaySinh=findViewById(R.id.tvNgaySinh);
        SoDienThoai=findViewById(R.id.tvSoDienThoai);
        CMND=findViewById(R.id.tvCMND);
        DiaChi=findViewById(R.id.tvDiaChi);
        imageView=findViewById(R.id.imgCapNhat);

    }
}

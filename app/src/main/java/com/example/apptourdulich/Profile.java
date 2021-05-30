package com.example.apptourdulich;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

public class Profile extends AppCompatActivity {
    TextView HoTen,SoDienThoai,GioiTinh,NgaySinh,CMND,DiaChi;
    DatabaseReference databaseReference;
    Button dangxuat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        dangxuat=findViewById(R.id.btnDangXuat);
        dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
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
        ReadData(id);
    }

    private void ReadData(int id) {
        databaseReference= FirebaseDatabase.getInstance().getReference("KhachHang");

        databaseReference.child(String.valueOf(id)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        DataSnapshot dataSnapshot = task.getResult();
                        String Ten = String.valueOf(dataSnapshot.child("hoTen").getValue());
                        String gioitinh = String.valueOf(dataSnapshot.child("gioitinh").getValue());
                        String ngaySinh = String.valueOf(dataSnapshot.child("ngaySinh").getValue());
                        String cmnd = String.valueOf(dataSnapshot.child("cmnd").getValue());
                        String sdt = String.valueOf(dataSnapshot.child("sdt").getValue());
                        String dc = String.valueOf(dataSnapshot.child("diaChi").getValue());
                        //String image = String.valueOf(dataSnapshot.child("imageid").getValue());
                        HoTen.setText(Ten);
                        GioiTinh.setText(gioitinh);
                        NgaySinh.setText(ngaySinh);
                        CMND.setText(cmnd);
                        SoDienThoai.setText(sdt);
                        DiaChi.setText(dc);
                        String image = String.valueOf(dataSnapshot.child("imageid").getValue());
                        ImageView imageProfile = (ImageView) findViewById(R.id.imgProfile);
                        Task<Uri> storageReference = FirebaseStorage.getInstance().getReference().child("Images/"+image).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                // Got the download URL for 'users/me/profile.png'
                                                       System.out.println(uri);
                                Glide.with(Profile.this).load(uri).into(imageProfile);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });
//                        System.out.println(storageReference);


                    }
                }
            }
        });
    }
}
package com.example.apptourdulich;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class InfoTour extends AppCompatActivity {
    DatabaseReference databaseReference;
    ImageView imageHinhNen;
    TextView tvTen;
    ImageView back;
    Button DatTour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_tour);

        tvTen=findViewById(R.id.tvTenTour);
        back=findViewById(R.id.imageBackDetailTour);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_info, R.id.navigation_details)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.navkhoanchi);
        NavigationUI.setupWithNavController(navView, navController);

        Bundle b=getIntent().getExtras();
        int id=b.getInt("IDTour");

        databaseReference= FirebaseDatabase.getInstance().getReference("Tour");
        databaseReference.child(String.valueOf(id)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        DataSnapshot dataSnapshot = task.getResult();
                        String Ten = String.valueOf(dataSnapshot.child("tenTour").getValue());
                        String phuongTien = String.valueOf(dataSnapshot.child("phuongTien").getValue());
                        String khachSan = String.valueOf(dataSnapshot.child("khachSan").getValue());
                        String donGia = String.valueOf(dataSnapshot.child("donGia").getValue());
                        String khuVuc = String.valueOf(dataSnapshot.child("khuVuc").getValue());
                        String moTa = String.valueOf(dataSnapshot.child("moTa").getValue());
                        String ngayKhoiHanh= String.valueOf(dataSnapshot.child("ngayKhoiHanh").getValue());
                        String ngayKetThuc = String.valueOf(dataSnapshot.child("ngayketThuc").getValue());
                        String noiKhoiHanh = String.valueOf(dataSnapshot.child("noiKhoiHanh").getValue());
                        String soNgay = String.valueOf(dataSnapshot.child("soNgay").getValue());
                        String image = String.valueOf(dataSnapshot.child("image").getValue());
                        tvTen.setText(Ten);

                        imageHinhNen = (ImageView) findViewById(R.id.imgHinhNen);
                        Task<Uri> storageReference = FirebaseStorage.getInstance().getReference().child("Images/"+image).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                // Got the download URL for 'users/me/profile.png'
                                System.out.println(uri);
                                Glide.with(InfoTour.this).load(uri).into(imageHinhNen);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });
                        System.out.println(storageReference);

                    }
                }
            }
        });
    }


    }


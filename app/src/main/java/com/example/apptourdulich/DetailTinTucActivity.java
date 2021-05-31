package com.example.apptourdulich;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;

public class DetailTinTucActivity extends AppCompatActivity {

    ImageView image,back;
    TextView Ten,Thongtin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tin_tuc);
        image=findViewById(R.id.imageDetailTinTuc);
        Ten= findViewById(R.id.tvTenDetaiTinTuc);
        Thongtin=findViewById(R.id.tvThongTin);
        back=findViewById(R.id.imageDetailTinTuc);
        Bundle b= getIntent().getExtras();


        Ten.setText(b.getString("Ten"));
        Thongtin.setText(b.getString("ThongTin"));
        String ima=b.getString("Image");
        Task<Uri> storageReference = FirebaseStorage.getInstance().getReference().child("Images/"+ima).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                System.out.println(uri);
                Glide.with(DetailTinTucActivity.this).load(uri).into(image);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
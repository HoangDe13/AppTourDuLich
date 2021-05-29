package com.example.apptourdulich;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class tour extends AppCompatActivity {
EditText Ten,PhuongTien,KhachSan,DonGia,NgayKhoiHanh,NgayKetThuc,MoTa,TinhTrang,KhuVuc,NoiKhoiHanh,SoNgay;
ImageView imTour;
Button ch,up;
StorageReference mStoreRef;
DatabaseReference dbref;
ThongTinTour thongTinTour;

    long maxid;
public Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);
        Ten=findViewById(R.id.edtTenTour);
        PhuongTien=findViewById(R.id.PhuongTien);
        KhachSan=findViewById(R.id.Khachsan);
        DonGia=findViewById(R.id.DonGia);
        NgayKhoiHanh=findViewById(R.id.NgayKhoiHanh);
        NgayKetThuc=findViewById(R.id.NgayKetThuc);
        MoTa=findViewById(R.id.MoTa);
        TinhTrang=findViewById(R.id.TinhTrang);
        KhuVuc=findViewById(R.id.KhuVuc);
        NoiKhoiHanh=findViewById(R.id.NoiKhoiHanh);
        SoNgay=findViewById(R.id.SoNgay);
        ch=findViewById(R.id.btnChonAnh);
        up=findViewById(R.id.btnLuu);
        imTour=findViewById(R.id.imageTrour);
        thongTinTour=new ThongTinTour();
        dbref= FirebaseDatabase.getInstance().getReference("Tour");
        dbref.addValueEventListener(new ValueEventListener() {
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
            mStoreRef= FirebaseStorage.getInstance().getReference("Images");
        ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImage();
            }
        });
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Upload();

            }
        });

    }
    private String getExtension(Uri uri){
        ContentResolver cr=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }
    private void Upload() {
        thongTinTour.setTenTour(Ten.getText().toString().trim());
        thongTinTour.setKhachSan(KhachSan.getText().toString().trim());
        thongTinTour.setPhuongTien(PhuongTien.getText().toString().trim());
        thongTinTour.setDonGia(Integer.parseInt(DonGia.getText().toString().trim()));
        thongTinTour.setNgayKhoiHanh(NgayKhoiHanh.getText().toString().trim());
        thongTinTour.setNgayketThuc(NgayKetThuc.getText().toString().trim());
        thongTinTour.setMoTa(MoTa.getText().toString().trim());
        thongTinTour.setTinhTrang(TinhTrang.getText().toString().trim());
        thongTinTour.setNoiKhoiHanh(NoiKhoiHanh.getText().toString().trim());
        thongTinTour.setSoNgay(SoNgay.getText().toString().trim());
        thongTinTour.setKhuVuc(KhuVuc.getText().toString().trim());
        String i=System.currentTimeMillis()+"."+getExtension(uri);
        thongTinTour.setImage(i);
        dbref.child(String.valueOf(maxid + 1)).setValue(thongTinTour);


        StorageReference Ref=mStoreRef.child(i);
        Ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(tour.this,"ThanhCong",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void ChooseImage() {
        Intent i= new Intent();
        i.setType("image/*");
        i.setAction(i.ACTION_GET_CONTENT);
        startActivityForResult(i,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            uri=data.getData();
            imTour.setImageURI(uri);
        }
    }
}
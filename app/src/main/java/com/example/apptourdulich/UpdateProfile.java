package com.example.apptourdulich;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.sql.Ref;
import java.util.HashMap;
import java.util.UUID;

public class UpdateProfile extends AppCompatActivity {

    TextView HoTen,SoDienThoai,GioiTinh,NgaySinh,CMND,DiaChi;
    DatabaseReference databaseReference;
    KhachHang khachHang=new KhachHang();
    ImageView imageView,imgBack;
    Button CapNhat;
    public Uri uri;
    StorageReference mStoreRef;
    String sdt;
   String i;

    // request code
    private final int PICK_IMAGE_REQUEST = 22;

    // instance for firebase storage and StorageReference
    FirebaseStorage storage;
    StorageReference storageReference;
    StorageTask uploadTask;
    FirebaseAuth firebaseAuth;
    private int requestCode;
    private int resultCode;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
imageView=findViewById(R.id.imgCapNhat);
CapNhat=findViewById(R.id.btnUpdate);
imgBack=findViewById(R.id.imgBackUpdate);
imgBack.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        finish();
    }
});
        storageReference=FirebaseStorage.getInstance().getReference().child("Image");
        Bundle b= getIntent().getExtras();
         sdt= b.getString("SoDienThoai");
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

        imageView.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             ChooseImage();


                                         }
                                     });
        CapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStoreRef= FirebaseStorage.getInstance().getReference("Images");
                String Hoten=HoTen.getText().toString().trim();
                String Ngaysinh=NgaySinh.getText().toString().trim();
                String Gioitinh=GioiTinh.getText().toString().trim();
                String Diachi=DiaChi.getText().toString().trim();
                String Cmnd=CMND.getText().toString().trim();
                String dt=SoDienThoai.getText().toString().trim();

                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("KhachHang");

                userRef.orderByChild("sdt").equalTo(dt).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String id= dataSnapshot.getKey();
                            khachHang.setHoTen(Hoten);
                            khachHang.setGioitinh(Gioitinh);
                            khachHang.setNgaySinh(Ngaysinh);
                            if(uri==null)
                            {
                                Toast.makeText(getApplicationContext(),"Vui Lòng Chọn Hình Ảnh",Toast.LENGTH_SHORT).show();
                            }else {
                                i = System.currentTimeMillis() + "." + getExtension(uri);
                                khachHang.setImageid(i);

                                khachHang.setSDT(dt);
                                khachHang.setCMND(Cmnd);
                                khachHang.setDiaChi(Diachi);

                                userRef.child(id).setValue(khachHang);
                                Toast.makeText(getApplicationContext(), "Cập Nhật Thành Công", Toast.LENGTH_SHORT).show();

                                StorageReference Ref = mStoreRef.child(i);

                                Ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });
                            }


                        }
                    };
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }


    private String getExtension(Uri uri){

    ContentResolver cr = getContentResolver();
    MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
    return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));

    }
    @Override
    protected void onStart() {
        super.onStart();

        HoTen = findViewById(R.id.edtHoTen);
        GioiTinh = findViewById(R.id.edtGioiTinh);
        NgaySinh = findViewById(R.id.edtNgaySinh);
        SoDienThoai = findViewById(R.id.edtSoDienThoai);
        CMND = findViewById(R.id.edtCCCD);
        DiaChi = findViewById(R.id.edtDiaChi);
        imageView = findViewById(R.id.imgCapNhat);
        CapNhat = findViewById(R.id.btnUpdate);


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
            imageView.setImageURI(uri);
        }
    }

}



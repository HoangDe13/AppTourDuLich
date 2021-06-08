package com.example.apptourdulich;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class ThongBao extends AppCompatActivity {
RecyclerView rcvThongBao;
AdapterThongBao adapterThongBao;
ThongTinThongBao thongTinThongBao;
ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_bao);
        back=findViewById(R.id.imgThoatThongBao);
        rcvThongBao=findViewById(R.id.rvThongBao);
        Bundle b= getIntent().getExtras();
        String sdt=b.getString("SoDienThoai");

        rcvThongBao.setLayoutManager(new LinearLayoutManager(ThongBao.this));
        FirebaseRecyclerOptions<ThongTinThongBao> thongTinTinTucFirebaseOptions=new FirebaseRecyclerOptions.Builder<ThongTinThongBao>().setQuery(FirebaseDatabase.getInstance().getReference().child("ThongBao").orderByChild("soDienThoai").equalTo(sdt),ThongTinThongBao.class).build();

        adapterThongBao=new AdapterThongBao(thongTinTinTucFirebaseOptions);
        rcvThongBao.setAdapter(adapterThongBao);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapterThongBao.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterThongBao.stopListening();
    }
}
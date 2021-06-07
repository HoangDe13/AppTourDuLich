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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class lovelist extends AppCompatActivity {


    RecyclerView recyclerView;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference reference,fvt;
    Boolean fvrtChecker=false;
    ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lovelist);

        imgBack=findViewById(R.id.imgBackLoveList);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView=findViewById(R.id.rcvLove);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Bundle bundle=this.getIntent().getExtras();
        String SoDienThoai=bundle.getString("SoDienThoai");

        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        String currentUserid=user.getPhoneNumber();

        fvt=database.getReference("Likes");
        reference=database.getReference("LikeList").child(currentUserid);

        FirebaseRecyclerOptions<ThongTinTour> options=
                new FirebaseRecyclerOptions.Builder<ThongTinTour>()
                        .setQuery(reference,ThongTinTour.class).build();

        FirebaseRecyclerAdapter<ThongTinTour,AdapterTour> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<ThongTinTour, AdapterTour>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AdapterTour holder, int i, @NonNull ThongTinTour thongTinTour) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String currentUserid = user.getPhoneNumber();
                        final String postkey = getRef(i).getKey();

                        holder.setLikeList(getApplication(), thongTinTour.getMaTour(), thongTinTour.getTenTour(),
                                thongTinTour.getPhuongTien(), thongTinTour.getKhachSan(), thongTinTour.getDonGia(),
                                thongTinTour.getNgayKhoiHanh(), thongTinTour.getNgayketThuc(), thongTinTour.getMoTa(), thongTinTour.getTinhTrang(),
                                thongTinTour.getKhuVuc(), thongTinTour.getImage(), thongTinTour.getSoNgay(), thongTinTour.getNoiKhoiHanh());
                        //Glide.with(holder.imageItemTour.getContext()).load(thongTinTour.getImage().into(holder.imageItemTour));
                        String image = thongTinTour.getImage();
                        Task<Uri> storageReference = FirebaseStorage.getInstance().getReference().child("Images/" + image).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                // Got the download URL for 'users/me/profile.png'
                                System.out.println(uri);
                                Glide.with(holder.imgList.getRootView()).load(uri).into(holder.imgList);

                            }
                            }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });

                        final int idT = getItem(i).getMaTour();

                        holder.imgRemoveLike.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                delete(idT);
                            }
                        });

                        holder.imgList.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AppCompatActivity appCompatActivity=(AppCompatActivity) v.getContext();
                                Intent i=new Intent(appCompatActivity,InfoTour.class);
                                Bundle b=new Bundle();
                                b.putInt("IDTour",thongTinTour.getMaTour());
                                b.putString("SoDienThoai",SoDienThoai);
                                i.putExtras(b);
                                startActivity(i);
                            }
                        });

                    }
                    @NonNull
                    @Override
                    public AdapterTour onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view1=LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.favtour_item,parent,false);
                        return new AdapterTour(view1);
                    }
                };
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }
    void delete(int idT){
        Query query=reference.orderByChild("maTour").equalTo(idT);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    dataSnapshot.getRef().removeValue();
                     
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query query1=fvt.orderByChild("maTour").equalTo(idT);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    dataSnapshot.getRef().removeValue();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}
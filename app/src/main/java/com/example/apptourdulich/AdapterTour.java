package com.example.apptourdulich;


import android.app.Application;

import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.fragment.app.FragmentActivity;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.RecyclerView;


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
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;


public  class AdapterTour extends RecyclerView.ViewHolder{

    ImageView imageItemTour,imgBtnfav,imgList,imgRemoveLike;
    TextView ItemtenTour,ItemngayKhoiHanh,ItemgiaTour,ItemsoNgay;
    DatabaseReference favoriteref;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    public AdapterTour(@NonNull View itemView) {
        super(itemView);
    }


    //todo: xử lý lấy dữ liệu tour từ firebase load lên recycleview
    public void setItem(FragmentActivity activity, int maTour, String tenTour,
              String phuongTien, String khachSan, int donGia, String ngayKhoiHanh,
             String ngayketThuc,String moTa, String tinhTrang, String khuVuc, String noiKhoiHanh,
            String soNgay, String image){

            imageItemTour=itemView.findViewById(R.id.imgTourItemFav);
            ItemtenTour=itemView.findViewById(R.id.tvTenTourItemFav);
            ItemngayKhoiHanh=itemView.findViewById(R.id.tvNgayKhoiHanhItemFav);
            ItemgiaTour=itemView.findViewById(R.id.tvGiaItemFav);
            ItemsoNgay=itemView.findViewById(R.id.tvSoNgayItemFav);

            //Picasso.get().load(" http://xxx.xxx.com/images/New%20folder/Desert.jpg.").into(imageItemTour);
            //imageItemTour.setImageResource(Integer.parseInt(image));
            ItemtenTour.setText(tenTour);
            ItemngayKhoiHanh.setText(ngayKhoiHanh);
        NumberFormat fmDonGia = new DecimalFormat("#,###");
        double DonGia = Double.parseDouble(String.valueOf(donGia));
        String fmdongia = fmDonGia.format(DonGia);


        ItemgiaTour.setText(fmdongia);
            ItemsoNgay.setText(soNgay);

    }

    //todo: xử lý thay đổi trạng thái like
    public void favoriteChecker(String postkey) {
        imgBtnfav = itemView.findViewById(R.id.btnFavoriteFav);
        favoriteref = database.getReference("Likes");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getPhoneNumber();

        favoriteref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(postkey).hasChild(uid)) {
                    imgBtnfav.setImageResource(R.drawable.ic_baseline_favorite_24);
                } else {
                    imgBtnfav.setImageResource(R.drawable.ic_baseline_favoritee_24);
                }
            }

            @Override

            public void onCancelled(@NonNull DatabaseError error) {


            }
        });


    }

    public void setLikeList(Application activity, int maTour, String tenTour,
                        String phuongTien, String khachSan, int donGia, String ngayKhoiHanh,
                        String ngayketThuc,String moTa, String tinhTrang, String khuVuc, String noiKhoiHanh,
                        String soNgay, String image){

         imgList=itemView.findViewById(R.id.imgTourItem);
        TextView listTourName=itemView.findViewById(R.id.tvTenTourItem);
        TextView listNgay=itemView.findViewById(R.id.tvNgayKhoiHanh);
        TextView listGia=itemView.findViewById(R.id.tvGiaItem);
        imgRemoveLike=itemView.findViewById(R.id.btnFavorite);

        //Picasso.get().load(" http://xxx.xxx.com/images/New%20folder/Desert.jpg.").into(imageItemTour);
        //imageItemTour.setImageResource(Integer.parseInt(image));
        listTourName.setText(tenTour);
        listNgay.setText(ngayKhoiHanh);
        listGia.setText(String.valueOf(donGia));


    }


}




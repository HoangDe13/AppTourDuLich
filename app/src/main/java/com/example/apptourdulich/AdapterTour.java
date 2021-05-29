package com.example.apptourdulich;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;

import java.text.DecimalFormat;
import java.text.NumberFormat;


public  class AdapterTour extends FirebaseRecyclerAdapter<ThongTinTour,AdapterTour.MyViewHolder>{


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdapterTour(@NonNull FirebaseRecyclerOptions<ThongTinTour> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int i, @NonNull ThongTinTour thongTinTour) {
        holder.ten.setText(thongTinTour.getTenTour());
        holder.ngayKhoiHanh.setText(thongTinTour.getNgayKhoiHanh());
        holder.soNgay.setText(thongTinTour.getSoNgay());
        NumberFormat formatterthu = new DecimalFormat("#,###");
        double myNumberthu = Double.parseDouble(String.valueOf(thongTinTour.getDonGia()));
        String formattedNumberthu = formatterthu.format(myNumberthu);
        holder.gia.setText(formattedNumberthu);
        String image=thongTinTour.getImage();
        Task<Uri> storageReference = FirebaseStorage.getInstance().getReference().child("Images/"+image).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                System.out.println(uri);
                Glide.with(holder.imageView.getContext()).load(uri).into(holder.imageView);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.touritem,parent,false);
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        Button btnFavorite;
        TextView ten,ngayKhoiHanh,soNgay,gia;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
                imageView=itemView.findViewById(R.id.imgTourItem);
                ten=itemView.findViewById(R.id.tvTenTourItem);
                soNgay=itemView.findViewById(R.id.tvSoNgayItem);
                ngayKhoiHanh=itemView.findViewById(R.id.tvNgayKhoiHanhItem);
                gia=itemView.findViewById(R.id.tvGiaItem);
                btnFavorite=itemView.findViewById(R.id.btnFavorite);

            }
        }
    }


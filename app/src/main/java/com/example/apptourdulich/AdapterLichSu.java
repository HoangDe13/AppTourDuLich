package com.example.apptourdulich;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class AdapterLichSu extends FirebaseRecyclerAdapter<ThongTinHoaDon,AdapterLichSu.MyViewHolder> {

    ThongTinTour thongTinTour=new ThongTinTour();

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdapterLichSu(@NonNull FirebaseRecyclerOptions<ThongTinHoaDon> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull AdapterLichSu.MyViewHolder holder, int position, @NonNull ThongTinHoaDon model) {

        int id= model.getMaTour();

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Tour");
        databaseReference.child(String.valueOf(id)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        DataSnapshot dataSnapshot = task.getResult();
                        String Ten = String.valueOf(dataSnapshot.child("tenTour").getValue());
                        String NgayKhoiHanh=String.valueOf(dataSnapshot.child("ngayKhoiHanh").getValue());
                        holder.tvNameTourHis.setText(Ten);
                        holder.tvNgaydiHis.setText(NgayKhoiHanh);
                    }
                }
            }
        });
        holder.tvNgayThanhtoanHis.setText(model.getNgayThanhToan());
        int tongtien=model.getTongTien();
        NumberFormat fmDonGia = new DecimalFormat("#,###");
        double thanhtoan = Double.parseDouble(String.valueOf(tongtien));
        String tt = fmDonGia.format(thanhtoan);
        holder.tvTongtienHis.setText(tt);

        holder.imgDetailsHis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) v.getContext();
                Intent i = new Intent(appCompatActivity, infoHistory.class);
                Bundle b = new Bundle();
                b.putString("maHoaDon",String.valueOf(model.getMaHoaDon()));
                b.putString("maKhuyenMai",model.getMaKhuyenMai());
                b.putString("maTour",String.valueOf(model.getMaTour()));
                b.putString("ngayThanhToan",model.getNgayThanhToan());
                b.putString("soDienThoai",model.getSoDienThoai());
                b.putInt("soLuongNguoiLon",model.getSoLuongNguoiLon());
                b.putInt("soLuongTreEm",model.getSoLuongTreEm());
                b.putString("tongTien",String.valueOf(model.getTongTien()));
                i.putExtras(b);

                appCompatActivity.startActivity(i);
            }
        });
    }

    @NonNull
    @Override
    public AdapterLichSu.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history,null);
        return new AdapterLichSu.MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvNameTourHis,tvNgaydiHis,tvNgayThanhtoanHis,tvTongtienHis;
        CardView imgDetailsHis;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNameTourHis=itemView.findViewById(R.id.tvNameTourHis);
            tvNgaydiHis=itemView.findViewById(R.id.tvDayHis);
            tvNgayThanhtoanHis=itemView.findViewById(R.id.tvDayPaymentHis);
            tvTongtienHis=itemView.findViewById(R.id.tvgiaHis);

            imgDetailsHis=itemView.findViewById(R.id.imgDetailsHis);

        }
    }
}

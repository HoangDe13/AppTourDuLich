package com.example.apptourdulich;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AdapterThongBao extends FirebaseRecyclerAdapter<ThongTinThongBao,AdapterThongBao.MyViewHolder>{


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdapterThongBao(@NonNull FirebaseRecyclerOptions<ThongTinThongBao> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull ThongTinThongBao thongTinThongBao) {
        holder.noidung.setText(thongTinThongBao.getNoiDung());
        holder.ngay.setText(thongTinThongBao.getNgayThongBao());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemthongbao,parent,false);
        return new AdapterThongBao.MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView noidung,ngay;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            noidung=itemView.findViewById(R.id.tvNoiDung);
            ngay=itemView.findViewById(R.id.tvNgayThongBao);

        }
    }
}

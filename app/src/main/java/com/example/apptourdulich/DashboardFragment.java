package com.example.apptourdulich;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.apptourdulich.ui.dashboard.DashboardViewModel;
import com.example.apptourdulich.ui.notifications.NotificationsViewModel;

public class DashboardFragment extends Fragment {
    Button CongLon, TruLon,CongTre,TruTre;
    int slnguoilon,sltreem;
    EditText NguoiLon,TreEm;
    private DashboardViewModel dashboardViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        //khai báo
        CongLon=root.findViewById(R.id.btnCongNguoiLon);
        CongTre=root.findViewById(R.id.btnCongTreEm);
        TruLon=root.findViewById(R.id.btnTruNguoiLon);
        TruTre= root.findViewById(R.id.btnTruTreEm);
        NguoiLon=root.findViewById(R.id.edtSoLuongNguoiLon);
        TreEm=root.findViewById(R.id.edtSoLuongTreEm);
//        slnguoilon=Integer.parseInt(NguoiLon.getText().toString().trim());
//        sltreem=Integer.parseInt(TreEm.getText().toString().trim());
        CongLon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slnguoilon=Integer.parseInt(NguoiLon.getText().toString().trim());
                slnguoilon=slnguoilon+1;
                NguoiLon.setText(slnguoilon);
            }
        });
        TruLon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slnguoilon=Integer.parseInt(NguoiLon.getText().toString().trim());
                slnguoilon=slnguoilon-1;
                NguoiLon.setText(slnguoilon);
            }
        });
        CongTre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sltreem=Integer.parseInt(TreEm.getText().toString().trim());
                sltreem=sltreem+1;
                TreEm.setText(sltreem);
            }
        });
        TruTre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sltreem=Integer.parseInt(TreEm.getText().toString().trim());
                sltreem=sltreem-1;
                TreEm.setText(sltreem);
            }
        });
        return root;
    }
}

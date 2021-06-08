package com.example.apptourdulich;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;


import com.example.apptourdulich.ui.notifications.NotificationsViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class NotificationsFragment extends Fragment{

    private NotificationsViewModel notificationsViewModel;
    DatabaseReference databaseReference;
    TextView khachSan,moTa;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        //khai báo
        khachSan=root.findViewById(R.id.tvKhachSanDetail);
        moTa=root.findViewById(R.id.tvMoTa);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle i= getActivity().getIntent().getExtras();
        int id=i.getInt("IDTour");

        //khai báo

        databaseReference= FirebaseDatabase.getInstance().getReference("Tour");
        databaseReference.child(String.valueOf(id)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        DataSnapshot dataSnapshot = task.getResult();
                        String moTaStr = String.valueOf(dataSnapshot.child("moTa").getValue());
                        String khachSanstr = String.valueOf(dataSnapshot.child("khachSan").getValue());


                        moTa.setText(moTaStr);
                        khachSan.setText(khachSanstr);




                    }
                }
            }
        });
    }
}

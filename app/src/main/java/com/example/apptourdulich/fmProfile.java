package com.example.apptourdulich;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.rpc.context.AttributeContext;

import java.util.Locale;

import io.grpc.internal.SharedResourceHolder;

import static android.content.Context.MODE_PRIVATE;
import static androidx.core.app.ActivityCompat.finishAffinity;
import static androidx.core.app.ActivityCompat.recreate;

import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fmProfile#newInstance} factory method to
 * create an instance of this fragment.
 */

public class fmProfile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    CardView changeLang;//thay doi ngon ngu
    CardView lovee;
    public fmProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fmProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static fmProfile newInstance(String param1, String param2) {
        fmProfile fragment = new fmProfile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    TextView HoTen,Profile;
    DatabaseReference databaseReference;
    ImageView imageProfile;
    CardView DieuKhoan, LienHe,LichSu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_fm_profile, container, false);
        changeLang=(view).findViewById(R.id.changeMyLang);//
        lovee=(view).findViewById(R.id.love);
        imageProfile= (ImageView) view.findViewById(R.id.imgProfilesetting);
        HoTen=view.findViewById(R.id.tvHoTenProfile);
        Bundle b= getActivity().getIntent().getExtras();
        String sdt=b.getString("SoDienThoai");
        DieuKhoan=view.findViewById(R.id.cvDieuKhoan);
        LienHe= view.findViewById(R.id.cvLienHe);
        LichSu=view.findViewById(R.id.cvHistory);
        DieuKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getActivity().getApplicationContext(),Rules.class);
                startActivity(i);
            }
        });
        LienHe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity().getApplicationContext(),Contact.class);
                startActivity(i);
            }
        });
        databaseReference= FirebaseDatabase.getInstance().getReference("KhachHang");
        databaseReference.orderByChild("sdt").equalTo(sdt).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                KhachHang kh= child.getValue(KhachHang.class);


                HoTen.setText(kh.getHoTen());
                String img= String.valueOf(kh.getImageid());
                Task<Uri> task=FirebaseStorage.getInstance().getReference().child("Images/"+img).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        System.out.println(uri);
                        Glide.with(view.getContext()).load(uri).into(imageProfile);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }}


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        changeLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),ChangeLanguage.class);
                i.putExtra("SoDienThoai",sdt);
                startActivity(i);
            }
        });
        lovee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),lovelist.class);
                i.putExtra("SoDienThoai",sdt);
                startActivity(i);
            }
        });

        HoTen=view.findViewById(R.id.tvHoTenProfile);
        Profile=view.findViewById(R.id.tvProfile);
        HoTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(view.getContext(),Profile.class);
                i.putExtra("SoDienThoai",sdt);

                startActivity(i);
            }
        });
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(view.getContext(),Profile.class);
                i.putExtra("SoDienThoai",sdt);
                startActivity(i);
            }
        });

        LichSu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(view.getContext(),History.class);
                i.putExtra("SoDienThoai",sdt);
                startActivity(i);
            }
        });




        return view;
    }



}
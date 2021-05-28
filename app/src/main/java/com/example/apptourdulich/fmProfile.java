package com.example.apptourdulich;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

import com.google.rpc.context.AttributeContext;

import java.util.Locale;

import io.grpc.internal.SharedResourceHolder;

import static android.content.Context.MODE_PRIVATE;
import static androidx.core.app.ActivityCompat.finishAffinity;
import static androidx.core.app.ActivityCompat.recreate;

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

    CardView changeLang;//

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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_fm_profile, container, false);
        changeLang=(view).findViewById(R.id.changeMyLang);//

        changeLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),ChangeLanguage.class);
                //xử lý nhấp nháy khi click vào view
                Animation mAnimationm = new AlphaAnimation(1, 0);
                mAnimationm.setDuration(200);
                mAnimationm.setRepeatCount(Animation.INFINITE);
                mAnimationm.setRepeatMode(Animation.REVERSE);
                changeLang.startAnimation(mAnimationm);
                startActivity(i);
            }
        });


        return view;
    }



}
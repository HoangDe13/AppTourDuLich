package com.example.apptourdulich;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fmLocation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fmLocation extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fmLocation() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fmLocation.
     */
    // TODO: Rename and change types and number of parameters
    public static fmLocation newInstance(String param1, String param2) {
        fmLocation fragment = new fmLocation();
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
    RecyclerView rcvTinTuc;
    AdapterTinTuc adapterTinTuc;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_fm_location, container, false);
        rcvTinTuc=view.findViewById(R.id.rclTinTuc);
        rcvTinTuc.setLayoutManager(new LinearLayoutManager(view.getContext()));
        FirebaseRecyclerOptions<ThongTinTinTuc> thongTinTinTucFirebaseOptions=new FirebaseRecyclerOptions.Builder<ThongTinTinTuc>().setQuery(FirebaseDatabase.getInstance().getReference().child("TinTuc"),ThongTinTinTuc.class).build();

        adapterTinTuc=new AdapterTinTuc(thongTinTinTucFirebaseOptions);
        rcvTinTuc.setAdapter(adapterTinTuc);

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        adapterTinTuc.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        adapterTinTuc.stopListening();
    }
}
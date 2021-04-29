package com.example.apptourdulich;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fmTour#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fmTour extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fmTour() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fmTour.
     */
    // TODO: Rename and change types and number of parameters
    public static fmTour newInstance(String param1, String param2) {
        fmTour fragment = new fmTour();
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
    Button btnTatCa;
    Button btnMienBac;
    Button btnMientrung;
    Button btnMienNam;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view= inflater.inflate(R.layout.fragment_fm_tour, container, false);
        btnMienBac=view.findViewById(R.id.btnMienBac);
        btnMientrung=view.findViewById(R.id.btnMienTrung);
        btnMienNam=view.findViewById(R.id.btnMienNam);
        btnTatCa=view.findViewById(R.id.btnTatCa);
        loadFragment(new fragmentTour());
        return view;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment;
        switch (v.getId()) {
            case R.id.btnTatCa:
                fragment=new fragmentTour();
                loadFragment(fragment);
                break;
            case R.id.btnMienBac:
                fragment=new fm_tour_mien_bac();
                loadFragment(fragment);
                break;
            case R.id.btnMienTrung:
                fragment=new fm_tour_mien_trung();
                loadFragment(fragment);
                break;
            case R.id.btnMienNam:
                fragment=new fm_tour_mien_nam();
                loadFragment(fragment);
                break;
            default:
                break;
        }

    }
    private void loadFragment(Fragment fragment) {
        // load Fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
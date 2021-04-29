package com.example.apptourdulich;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fmHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fmHome extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fmHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fmHome.
     */
    // TODO: Rename and change types and number of parameters
    public static fmHome newInstance(String param1, String param2) {
        fmHome fragment = new fmHome();
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
    private ViewPager viewPager;
    PhotoAdapter photoAdapter;
    SlideListTourAdapter slideListTourAdapter;
    CircleIndicator circleIndicator;
    List<Photo> mList;
    Timer timer;
    List<Photo> mlisttour;
    ViewPager2 viewPager2;
    Handler handler=new Handler();



    TextView etTimKiem1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_fm_home, container, false);
        viewPager=view.findViewById(R.id.viewPager);
        viewPager2=view.findViewById(R.id.viewPagerImage);
        etTimKiem1=view.findViewById(R.id.etTimKiem);
      //  viewPager3=view.findViewById(R.id.viewPagerImageNews);
        circleIndicator=view.findViewById(R.id.circleIndicator);
        mlisttour=getListPhotoTour();
        mList=getListPhoto();

        photoAdapter=new PhotoAdapter(getContext(),mList);

        viewPager.setAdapter(photoAdapter);
        circleIndicator.setViewPager(viewPager);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        autoSlideImage();

        viewPager2.setAdapter(new SlideListTourAdapter(mlisttour,viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer=new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r=1-Math.abs(position);
                page.setScaleY(0.85f+r*0.15f);

            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable,6000);
            }
        });

        etTimKiem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getContext(),Search.class);
                startActivity(i);
            }
        });
        return view;
    }

    private List<Photo> getListPhotoTour() {
        List<Photo> list=new ArrayList<>();
        list.add(new Photo(R.drawable.hagiangchu));
        list.add(new Photo(R.drawable.dalate));
        list.add(new Photo(R.drawable.vinhhalonge));
        list.add(new Photo(R.drawable.phuquoce));
        list.add(new Photo(R.drawable.danange));
        list.add(new Photo(R.drawable.phanxipange));
        list.add(new Photo(R.drawable.huee));
        list.add(new Photo(R.drawable.nhatrange));
        return list;
    }
    private List<Photo> getListPhoto() {
        List<Photo> list=new ArrayList<>();
        list.add(new Photo(R.drawable.dalat));
        list.add(new Photo(R.drawable.hagiang));
        list.add(new Photo(R.drawable.halong));
        list.add(new Photo(R.drawable.phuquoc));
        return list;
    }

    private void autoSlideImage(){
        if(mList==null||mList==null||viewPager==null){
            return;
        }
        if(timer==null)
        {
            timer= new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem=viewPager.getCurrentItem();
                        int totalItem=mList.size()-1;
                        if(currentItem<totalItem){
                            currentItem++;
                            viewPager.setCurrentItem(currentItem);
                        }else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        },3000,6000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(timer!=null){
            timer.cancel();
            timer=null;
        }

    }
    public Runnable runnable=new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
        }
    };

}
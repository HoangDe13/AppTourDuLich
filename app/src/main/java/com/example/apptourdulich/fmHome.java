package com.example.apptourdulich;

import android.content.Intent;

import android.icu.util.LocaleData;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseOptions;

import com.google.firebase.database.DataSnapshot;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import com.google.type.DateTime;

import com.squareup.picasso.Picasso;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;


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
    //todo: khơi tạo biến cử lý like
    FirebaseDatabase database= FirebaseDatabase.getInstance();
    DatabaseReference reference,fvrtref,fvrt_listRef;
    Boolean fvrtChecker=false;
    RecyclerView recyclerView;
    ThongTinTour thongTinTour;

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


    ImageView etTimKiem1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_fm_home, container, false);
        viewPager=view.findViewById(R.id.viewPager);
        viewPager2=view.findViewById(R.id.viewPagerImage);

        etTimKiem1=view.findViewById(R.id.etTimKiem);

        //todo: xử lý get data đưa vào recycleview
        recyclerView= view.findViewById(R.id.rcvTour);
        //recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        reference=database.getReference("Tour");

        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        String currentUserid=user.getPhoneNumber();///
        thongTinTour=new ThongTinTour();
        fvrtref=database.getReference("Likes");
        fvrt_listRef=database.getReference("LikeList").child(currentUserid);

        FirebaseRecyclerOptions<ThongTinTour> options=
                new FirebaseRecyclerOptions.Builder<ThongTinTour>()
                        .setQuery(reference,ThongTinTour.class).build();
        FirebaseRecyclerAdapter<ThongTinTour,AdapterTour> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<ThongTinTour, AdapterTour>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AdapterTour holder, int i, @NonNull ThongTinTour thongTinTour) {
                        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                        String currentUserid=user.getPhoneNumber();///
                        final String postkey=getRef(i).getKey();

                        holder.setItem(getActivity(),thongTinTour.getMaTour(),thongTinTour.getTenTour(),
                                thongTinTour.getPhuongTien(),thongTinTour.getKhachSan(),thongTinTour.getDonGia(),
                                thongTinTour.getNgayKhoiHanh(),thongTinTour.getNgayketThuc(),thongTinTour.getMoTa(),thongTinTour.getTinhTrang(),
                                thongTinTour.getKhuVuc(),thongTinTour.getImage(),thongTinTour.getSoNgay(),thongTinTour.getNoiKhoiHanh());
                        //Glide.with(holder.imageItemTour.getContext()).load(thongTinTour.getImage().into(holder.imageItemTour));
                        //todo: chỗ này xử lý load ảnh nè
                        String image=thongTinTour.getImage();
                        Task<Uri> storageReference = FirebaseStorage.getInstance().getReference().child("Images/"+image).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                // Got the download URL for 'users/me/profile.png'
                                System.out.println(uri);
                                Glide.with(holder.imageItemTour.getContext()).load(uri).into(holder.imageItemTour);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });

                        int idT=getItem(i).getMaTour();
                        String name=getItem(i).getTenTour();
                        String pt=getItem(i).getPhuongTien();
                        String ks=getItem(i).getKhachSan();
                        int dg=getItem(i).getDonGia();
                        String nkh=getItem(i).getNgayKhoiHanh();
                        final String nkt=getItem(i).getNgayketThuc();
                        String mt=getItem(i).getMoTa();
                        String tt=getItem(i).getTinhTrang();
                        String kv=getItem(i).getKhuVuc();
                        String imgg=getItem(i).getImage();
                        String sng=getItem(i).getSoNgay();
                        String noikh=getItem(i).getNoiKhoiHanh();

                        //todo:chỗ này xử lý lưu tour nè
                        holder.favoriteChecker(postkey);
                        holder.imgBtnfav.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                fvrtChecker=true;
                                fvrtref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(fvrtChecker.equals(true))
                                        {
                                            if (snapshot.child(postkey).hasChild(currentUserid)){
                                                fvrtref.child(postkey).child(currentUserid).removeValue();
                                                delete(idT);
                                                fvrtChecker=false;
                                            }
                                            else
                                            {
                                                fvrtref.child(postkey).child(currentUserid).setValue(true);
                                                thongTinTour.setMaTour(idT);
                                                thongTinTour.setTenTour(name);
                                                thongTinTour.setNgayKhoiHanh(nkh);
                                                thongTinTour.setPhuongTien(pt);
                                                thongTinTour.setKhachSan(ks);
                                                thongTinTour.setNgayketThuc(nkt);
                                                thongTinTour.setDonGia(dg);
                                                thongTinTour.setMoTa(mt);
                                                thongTinTour.setTinhTrang(tt);
                                                thongTinTour.setKhuVuc(kv);
                                                thongTinTour.setImage(imgg);
                                                thongTinTour.setSoNgay(sng);
                                                thongTinTour.setNoiKhoiHanh(noikh);

                                                String id=fvrt_listRef.push().getKey();
                                                fvrt_listRef.child(id).setValue(thongTinTour);
                                                fvrtChecker=false;

                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                        });
                        //todo: chỗ này xử lý bundle qua chi tiết tour nè
//                        holder.imageItemTour.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                AppCompatActivity appCompatActivity=(AppCompatActivity) v.getContext();
//                                Intent i=new Intent(appCompatActivity,InfoTour.class);
//                                Bundle b=new Bundle();
//                                b.putInt("IDTour",thongTinTour.getMaTour());
//
//                                i.putExtras(b);
//                                appCompatActivity.startActivity(i);
//                            }
//                        });

                    }

                    @NonNull
                    @Override
                    public AdapterTour onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view1=LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.touritem,parent,false);
                        return new AdapterTour(view1);
                    }
                };
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
        //todo: end.

        //  viewPager3=view.findViewById(R.id.viewPagerImageNews);
        circleIndicator=view.findViewById(R.id.circleIndicator);
        mlisttour=getListPhotoTour();
        mList=getListPhoto();


        photoAdapter = new PhotoAdapter(getContext(), mList);

        viewPager.setAdapter(photoAdapter);
        circleIndicator.setViewPager(viewPager);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        autoSlideImage();

        viewPager2.setAdapter(new SlideListTourAdapter(mlisttour, viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);

            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 6000);
            }
        });

        etTimKiem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Search.class);
                startActivity(i);
            }
        });

        return view;
    }



    void delete(int idT){
        Query query=fvrt_listRef.orderByChild("maTour").equalTo(idT);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    dataSnapshot.getRef().removeValue();
                    Toast.makeText(getContext(),"deleted",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
package com.example.apptourdulich;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fm_tour_mien_trung#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fm_tour_mien_trung extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fm_tour_mien_trung() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fm_tour_mien_trung.
     */
    // TODO: Rename and change types and number of parameters
    public static fm_tour_mien_trung newInstance(String param1, String param2) {
        fm_tour_mien_trung fragment = new fm_tour_mien_trung();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    RecyclerView recyclerView;
    AdapterTour adapterTour;
    Toolbar myToolbar;
    FirebaseDatabase database= FirebaseDatabase.getInstance();
    DatabaseReference reference,fvrtref,fvrt_listRef;
    Boolean fvrtChecker=false;
    String SoDienThoai;
    ThongTinTour thongTinTour;
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
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_fm_tour_mien_trung, container, false);
        {
            Bundle bundle = getActivity().getIntent().getExtras();
            SoDienThoai= bundle.getString("SoDienThoai");
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String currentUserid = user.getPhoneNumber();///
            thongTinTour = new ThongTinTour();
            fvrtref = database.getReference("Likes");
            fvrt_listRef = database.getReference("LikeList").child(currentUserid);

            recyclerView = view.findViewById(R.id.rvMienTrung);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            reference = database.getReference("Tour");
            FirebaseRecyclerOptions<ThongTinTour> options =
                    new FirebaseRecyclerOptions.Builder<ThongTinTour>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("Tour").orderByChild("khuVuc").equalTo("Miền Trung"), ThongTinTour.class).build();
            FirebaseRecyclerAdapter<ThongTinTour, AdapterTour> firebaseRecyclerAdapter =
                    new FirebaseRecyclerAdapter<ThongTinTour, AdapterTour>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull AdapterTour holder, int i, @NonNull ThongTinTour thongTinTour) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String currentUserid = user.getPhoneNumber();///
                            final String postkey = getRef(i).getKey();

                            holder.setItem(getActivity(), thongTinTour.getMaTour(), thongTinTour.getTenTour(),
                                    thongTinTour.getPhuongTien(), thongTinTour.getKhachSan(), thongTinTour.getDonGia(),
                                    thongTinTour.getNgayKhoiHanh(), thongTinTour.getNgayketThuc(), thongTinTour.getMoTa(), thongTinTour.getTinhTrang(),
                                    thongTinTour.getKhuVuc(), thongTinTour.getImage(), thongTinTour.getSoNgay(), thongTinTour.getNoiKhoiHanh());
                            //Glide.with(holder.imageItemTour.getContext()).load(thongTinTour.getImage().into(holder.imageItemTour));
                            //todo: chỗ này xử lý load ảnh nè
                            String image = thongTinTour.getImage();
                            Task<Uri> storageReference = FirebaseStorage.getInstance().getReference().child("Images/" + image).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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

                            int idT = getItem(i).getMaTour();
                            String name = getItem(i).getTenTour();
                            String pt = getItem(i).getPhuongTien();
                            String ks = getItem(i).getKhachSan();
                            int dg = getItem(i).getDonGia();
                            String nkh = getItem(i).getNgayKhoiHanh();
                            final String nkt = getItem(i).getNgayketThuc();
                            String mt = getItem(i).getMoTa();
                            String tt = getItem(i).getTinhTrang();
                            String kv = getItem(i).getKhuVuc();
                            String imgg = getItem(i).getImage();
                            String sng = getItem(i).getSoNgay();
                            String noikh = getItem(i).getNoiKhoiHanh();

                            //todo:chỗ này xử lý lưu tour nè
                            holder.favoriteChecker(postkey);
                            holder.imgBtnfav.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    fvrtChecker = true;
                                    fvrtref.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (fvrtChecker.equals(true)) {
                                                if (snapshot.child(postkey).hasChild(currentUserid)) {
                                                    fvrtref.child(postkey).child(currentUserid).removeValue();
                                                    delete(idT);
                                                    fvrtChecker = false;
                                                } else {
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

                                                    String id = fvrt_listRef.push().getKey();
                                                    fvrt_listRef.child(id).setValue(thongTinTour);
                                                    fvrtChecker = false;

                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                }
                            });
                            // todo: chỗ này xử lý bundle qua chi tiết tour nè
                            holder.imageItemTour.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AppCompatActivity appCompatActivity = (AppCompatActivity) v.getContext();
                                    Intent i = new Intent(appCompatActivity, InfoTour.class);
                                    Bundle b = new Bundle();
                                    b.putInt("IDTour", thongTinTour.getMaTour());
                                    b.putString("SoDienThoai", SoDienThoai);
                                    i.putExtras(b);
                                    startActivity(i);
                                }
                            });


                        }




                        @NonNull
                        @Override
                        public AdapterTour onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view1 = LayoutInflater.from(parent.getContext())
                                    .inflate(R.layout.touritem, parent, false);
                            return new AdapterTour(view1);
                        }


                    };
            firebaseRecyclerAdapter.startListening();
            recyclerView.setAdapter(firebaseRecyclerAdapter);



        }
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
}
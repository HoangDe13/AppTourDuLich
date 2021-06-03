package com.example.apptourdulich;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;


import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class Search extends AppCompatActivity {
RecyclerView recyclerView;
    AdapterTour2 adapterTour;
   Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
         myToolbar =findViewById(R.id.mytoolbar);
         myToolbar.setTitle("");
         setSupportActionBar(myToolbar);
      recyclerView=findViewById(R.id.recycleviewSearch);
      recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<ThongTinTour> thongTinTourFirebaseOptions=new FirebaseRecyclerOptions.Builder<ThongTinTour>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Tour")
                        , ThongTinTour.class).build();

        adapterTour=new AdapterTour2((thongTinTourFirebaseOptions));
        recyclerView.setAdapter(adapterTour);

    }
    @Override
    public void onStart() {
        super.onStart();
        adapterTour.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        adapterTour.stopListening();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem menuItem=menu.findItem(R.id.search_button);
        SearchView searchView= (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Processseach(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Processseach(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }

    private void Processseach(String query) {
        FirebaseRecyclerOptions<ThongTinTour> thongTinTourFirebaseOptions=new FirebaseRecyclerOptions.Builder<ThongTinTour>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Tour").orderByChild("tenTour").startAt(query).endAt(query+"\uf8ff")
                        , ThongTinTour.class).build();

       adapterTour=new AdapterTour2(thongTinTourFirebaseOptions);
       adapterTour.startListening();
       recyclerView.setAdapter(adapterTour);

    }

}
package com.example.parrking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parrking.Adapter.PagerAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {
RecyclerView recyclerView;
MenuItem menuItem;
ImageView mapimg;
    private FirebaseFirestore mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // Toolbar tool=findViewById(R.id.toolbar);
      //  tool.setTitle("Parking");
      //  setSupportActionBar(tool);
        mapimg=findViewById(R.id.map);
     //   Toolbar toolbar = findViewById(R.id.toolbar);
       // toolbar.setTitle(R.string.app_name);
        //toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        //setSupportActionBar(toolbar);

        recyclerView=findViewById(R.id.recyclerview);
        mDatabase = FirebaseFirestore.getInstance();
        final LinearLayout noDataLayout = findViewById(R.id.no_data_layout_categories);
        final TextView noDataText = findViewById(R.id.no_data_text_categories);
        final ProgressBar progressBar = findViewById(R.id.no_data_progress_bar_categories);
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        // Log.e(TAG,"Title text = "+data.getTitle());
        //Log.e(TAG,"Audio text = "+data.getAudio());
//         Log.e(TAG,"Category text = "+data.getCategory());
        //adding persistence settings
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();

        //adding the settings to Firestore instance
        firebaseFirestore.setFirestoreSettings(settings);

        FirestorePagingAdapter adapter = new PagerAdapter(
                getFirestorePagingOptions(firebaseFirestore), noDataLayout, noDataText,
                progressBar, getContext(),this);

        //initializing the recyclerView and setting layout and adapter

//        childrecycler=findViewById(R.id.child_recycler);
//        childrecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false));
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false));
//        childrecycler.setAdapter(adapter);
        recyclerView.setAdapter(adapter);

mapimg.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
         Intent i=new Intent(MainActivity.this,MapsActivity.class);
         startActivity(i);
    }
});


    }
    private Query getQuery(FirebaseFirestore firebaseFirestore) {
        return firebaseFirestore.collection("MapData").orderBy("parkingname", Query.Direction.DESCENDING);
                //.orderBy("category");
    }
    private FirestorePagingOptions<MapsData> getFirestorePagingOptions(
            FirebaseFirestore firebaseFirestore) {
        //creating the base query
        Query baseQuery = getQuery(firebaseFirestore);

        //Allowing only a set number of elements to be loaded at a time
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setPrefetchDistance(15)
                .setPageSize(15)
                .build();

        // The options for the adapter combine the paging configuration with query information
        // and application-specific options for lifecycle, etc.

        return new FirestorePagingOptions.Builder<MapsData>()
                .setLifecycleOwner(this)
                .setQuery(baseQuery, config, MapsData.class)
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.overflow_menu, menu);

        //check if the video is set as favorite - set the action bar icon accordingly
        //favorite = isFavorite();
        menuItem = menu.findItem(R.id.action_map);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       /// Intent i=new Intent(MainActivity.this,MapsActivity.class);
       // startActivity(i);
        Toast.makeText(this, "open maps", Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }
}

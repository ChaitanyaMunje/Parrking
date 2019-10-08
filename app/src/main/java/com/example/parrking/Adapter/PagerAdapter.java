package com.example.parrking.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.parrking.ItemViewHolder;
import com.example.parrking.MainActivity;
import com.example.parrking.MapsActivity;
import com.example.parrking.MapsData;
import com.example.parrking.R;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.firebase.ui.firestore.paging.LoadingState;

import java.security.AccessControlContext;

public class PagerAdapter extends FirestorePagingAdapter<MapsData, ItemViewHolder> {
    private static final String TAG = "Adapter";
    private View noDataLayout;
    private TextView noDataText;
    private ProgressBar progressBar;
    private Context context;


    public PagerAdapter(FirestorePagingOptions<MapsData> firestorePagingOptions, LinearLayout noDataLayout, TextView noDataText, ProgressBar progressBar, AccessControlContext context, MainActivity mainActivity) {
        super(firestorePagingOptions);
        this.noDataLayout = noDataLayout;
        this.noDataText = noDataText;
        this.progressBar = progressBar;

    }



    @Override
    protected void onBindViewHolder(@NonNull final ItemViewHolder itemViewHolder, int i, @NonNull final MapsData mapsData) {
//Log.e(TAG,"Latitude= "+mapsData.getLatitude());
        itemViewHolder.setAvaliableparking(mapsData.getAvaliablepark());
        itemViewHolder.setLatitude(mapsData.getLatitude());
        itemViewHolder.setLongitude(mapsData.getLongitude());
        itemViewHolder.setParkingname(mapsData.getParkingname());

      //  Log.e(TAG,"Longitude= "+mapsData.getLongitude());
       // Log.e(TAG,"avaliable parking= "+mapsData.getAvaliablepark());
        //Log.e(TAG,"park name= "+mapsData.getParkingname());

        itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i=new Intent(context, MapsActivity.class);
               // startMaps(itemViewHolder,iteview);

                startMaps(itemViewHolder.itemView,mapsData.getParkingname(),mapsData.getLatitude(),mapsData.getLongitude(),mapsData.getAvaliablepark());
                /*
                 Intent i=new Intent(context, VideoViewActivity.class);
                startVideoActivity(itemViewHolder.itemView,sectionData.getTitle(),sectionData.getVideoID(),sectionData.getAudio(),sectionData.getCategory(),sectionData.getText(),sectionData.getDuration());

                 */
//                Toast.makeText(context, "Park Name = ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new ItemViewHolder(view);
    }
    private boolean isNetworkStatusAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
            if (netInfos != null)
                return netInfos.isConnected();
        }
        return false;
    }
private void startMaps(View itemview,String parkingname,String latitude,String longitude,String avaliable){
    Intent intent = new Intent(itemview.getContext(), MapsActivity.class);


    intent.putExtra("Longitude", longitude);
    intent.putExtra("parkingname", parkingname);
    intent.putExtra("Latitude", latitude);
    intent.putExtra("Avaliable",avaliable);
    itemview.getContext().startActivity(intent);

    Log.e(TAG,"Longitude= "+longitude);
    Log.e(TAG,"avaliable parking= "+parkingname);
    Log.e(TAG,"park name= "+latitude);

}
    private void toggleNoDataLayout(boolean noData, boolean initialLoad) {
        if (initialLoad) {
            noDataLayout.setVisibility(View.VISIBLE);
            noDataText.setText(R.string.loading_text);
            progressBar.setVisibility(View.VISIBLE);
        } else if (noData) {
            noDataLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            if (!isNetworkStatusAvailable(context)) {
                noDataText.setText(R.string.no_internet_connection_text);
            } else { //no data was found
                noDataText.setText(R.string.no_fresh_videos_found);
            }
        } else {
            noDataLayout.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onLoadingStateChanged(@NonNull LoadingState state) {
        switch (state) {
            case LOADING_INITIAL:
                // The initial load has begun
                toggleNoDataLayout(true, true); //show loading view
                break;
            case LOADING_MORE:
                // The adapter has started to load an additional page
                super.onLoadingStateChanged(state); //default implementation is desired
                break;
            case LOADED:
                // The previous load (either initial or additional) completed
                int count = getItemCount();

                if (count > 0) {
                    toggleNoDataLayout(false, false);
                    Log.d(TAG, getCurrentList().getDataSource().toString());
                } else {
                    toggleNoDataLayout(true, false);
                }
                break;
            case ERROR:
                // The previous load (either initial or additional) failed. Call
                // the retry() method in order to retry the load operation.
                retry();
                super.onLoadingStateChanged(state);
        }
    }
}

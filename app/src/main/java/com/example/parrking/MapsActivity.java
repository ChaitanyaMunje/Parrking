package com.example.parrking;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.maps.android.SphericalUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private RecyclerView recyclerView;
    private List<MapsData> data;
    Double distance;
    private FirebaseFirestore db;
    Location currentlocation;
    LatLng from,to;
    LatLng or;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    Double mslat = 21.146633, mslon = 79.088860;
    String lat, lon, avaliabletxt;
    String l, la, title, avaliable;
    private GoogleMap mMap;
    Double doublat, doublong;
    private static final LatLng USER = new LatLng(21.152317, 79.108368);
    Double mlat, mlon;
    ///21.1804418,78.978855
    ////21.1804418,78.978855
    private LatLngBounds NAGPUR = new LatLngBounds(
            //new LatLng(21.1804418,78.978855),new LatLng(21.1804418,78.978855));
            new LatLng(21.146633, 79.088860), new LatLng(21.146634, 79.088861));
    // Constrain the camera target to the Adelaide bounds.
    String latitude, longitude;
    String TAG = "Map";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        //  mMap.setMyLocationEnabled(true);
        setContentView(R.layout.activity_maps);
        recyclerView = findViewById(R.id.recyclerview_map);
        data = new ArrayList<>();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        Log.e(TAG," lon = ="+mapsData.getLongitude());
        //  Log.e(TAG,"lat == "+mapsData.getLatitude());
        // Intent intent = getIntent();
        Intent intent = getIntent();
        title = intent.getStringExtra("parkingname");
        latitude = intent.getStringExtra("Latitude");
        longitude = intent.getStringExtra("Longitude");
        avaliabletxt = intent.getStringExtra("Avaliable");


    }

    private void fetchlastlocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);

                return;
            }
        }
        // code for getting current location

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location!=null){
                    currentlocation=location;
                    Toast.makeText(MapsActivity.this, "lat = "+currentlocation.getLatitude()+"lon = "+currentlocation.getLongitude(), Toast.LENGTH_SHORT).show();
Log.e(TAG,"lat = "+currentlocation.getLatitude()+"lon = "+currentlocation.getLongitude());
                     or = new LatLng(currentlocation.getLatitude(),currentlocation.getLongitude());
                    // .icon(BitmapDescriptorFactory.fromResource(R.drawable.arrow
                    mMap.addMarker(new MarkerOptions().position(or).title("user location")
                            .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.ic_directions_car_black_24dp)
                                 //   Toast.makeText(this, "dist = "+doublong+doublat, Toast.LENGTH_SHORT).show();

                            )
                    );
                    Toast.makeText(MapsActivity.this, "lat = "+doublat+doublong, Toast.LENGTH_SHORT).show();

/*
                    int Radius = 6371;// radius of earth in Km
                    double lat1 = currentlocation.getLatitude();
                    double lat2 = mlat;
                    double lon1 = currentlocation.getLongitude();
                    double lon2 = mlon;
                    double dLat = Math.toRadians(lat2 - lat1);
                    double dLon = Math.toRadians(lon2 - lon1);
                    double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                            + Math.cos(Math.toRadians(lat1))
                            * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                            * Math.sin(dLon / 2);
                    double c = 2 * Math.asin(Math.sqrt(a));
                    double valueResult = Radius * c;
                    double km = valueResult / 1;
                    DecimalFormat newFormat = new DecimalFormat("####");
                    int kmInDec = Integer.valueOf(newFormat.format(km));
                    double meter = valueResult % 1000;
                    int meterInDec = Integer.valueOf(newFormat.format(meter));
                    Log.e(TAG, "" + valueResult + "   KM  " + kmInDec
                            + " Meter   " + meterInDec);
                    Toast.makeText(MapsActivity.this, "km value = "+valueResult+"\n"+kmInDec+"\n"+meterInDec, Toast.LENGTH_SHORT).show();
                    //  Toast.makeText(MapsActivity.this, "", Toast.LENGTH_SHORT).show();

*/
getDirection();
                }
            }
        });
    }

    public String makeURL (double sourcelat, double sourcelog, double destlat, double destlog ){
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/directions/json");
        urlString.append("?origin=");// from
        urlString.append(Double.toString(sourcelat));
        urlString.append(",");
        urlString
                .append(Double.toString( sourcelog));
        urlString.append("&destination=");// to
        urlString
                .append(Double.toString( destlat));
        urlString.append(",");
        urlString.append(Double.toString(destlog));
        urlString.append("&sensor=false&mode=driving&alternatives=true");
        urlString.append("&key=SERVER-KEY");
        return urlString.toString();
    }

    private void getDirection(){
        //Getting the URL
        String url = makeURL(currentlocation.getLatitude(), currentlocation.getLongitude(), doublat, doublong);
Log.e(TAG,"url = "+url);
        Toast.makeText(this, "url = "+url, Toast.LENGTH_SHORT).show();
        //Showing a dialog till we get the route
      //  final ProgressDialog loading = ProgressDialog.show(this, "Getting Route", "Please wait...", false, false);

        //Creating a string request
        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // loading.dismiss();
                        //Calling the method drawPath to draw the path
                        drawPath(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ///loading.dismiss();
                    }
                });

        //Adding the request to request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void drawPath(String  result) {
        //Getting both the coordinates
        from = new LatLng(currentlocation.getLatitude(),currentlocation.getLongitude());
         to = new LatLng(doublat,doublong);

        //Calculating the distance in meters
         distance = SphericalUtil.computeDistanceBetween(from, to);

        //Displaying the distance
        Toast.makeText(this,String.valueOf(distance/1000+"Km"),Toast.LENGTH_SHORT).show();


        try {
            //Parsing json
            final JSONObject json = new JSONObject(result);
            JSONArray routeArray = json.getJSONArray("routes");
            JSONObject routes = routeArray.getJSONObject(0);
            JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
            String encodedString = overviewPolylines.getString("points");
            List<LatLng> list = decodePoly(encodedString);
            Polyline line = mMap.addPolyline(new PolylineOptions()
                    .addAll(list)
                    .width(20)
                    .color(Color.RED)
                    .geodesic(true)
            );


        }
        catch (JSONException e) {
            Toast.makeText(this, "ep ="+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng( (((double) lat / 1E5)),
                    (((double) lng / 1E5) ));
            poly.add(p);
        }

        return poly;
    }
    // }
    //getting all markers on map

private void start(){
    db.collection("MapData").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
        @Override
        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
            if (!queryDocumentSnapshots.isEmpty()){
                List<DocumentSnapshot>list=queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot d : list){
                    MapsData m=d.toObject(MapsData.class);
                    data.add(m);
                    Log.e(TAG,"lat = "+d.getString("latitude"));
                    Log.e(TAG,"long = "+d.getString("longitude"));
                    lat=d.getString("latitude");
                    lon=d.getString("longitude");
                    avaliable=d.getString("avaliablepark");
                    title=d.getString("parkingname");
                    for (int i=0;i<data.size();i++){
                        l=data.get(i).latitude;
                        la=data.get(i).longitude;
                        mlat=Double.valueOf(l);
                        Log.e(TAG,"avaliable park = "+avaliable);
                        mlon=Double.valueOf(la);
                        Log.e(TAG," name = "+title);
                        Log.e(TAG,"Lat = "+mlat+"Long = "+mlon);
                        LatLngBounds NAGPUR=new LatLngBounds(
                                new LatLng(21.1804418,78.978855),new LatLng(21.1804418,78.978855)
                        );
                        mMap.addMarker(new MarkerOptions().position(new LatLng(mlat,mlon)).title(title+"\t("+avaliable+")")
                        .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.ic_local_parking_black_24dp)));
                   mMap.animateCamera(CameraUpdateFactory.zoomTo(9.0f));
                   LatLng car=new LatLng(mlat,mlon);
mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(NAGPUR.getCenter(),10));

                    }
                }
            }
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(MapsActivity.this, "fail = "+e, Toast.LENGTH_SHORT).show();
        }
    });
}

//getting location for particular list item.

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


       // LatLng one = new LatLng(21.1804418,78.978855);
        //LatLng two = new LatLng(21.1804418,78.978855);
        //

        if (doublat == null && doublong == null && title == null) {


            start();

        } else {


            fetchlastlocation();

            LatLngBounds NAGPUR=new LatLngBounds(
                    new LatLng(21.1804418,78.978855),new LatLng(21.1804418,78.978855)
            );
            //LatLng latLng=new LatLng(currentlocation.getLatitude(),currentlocation.getLongitude());
           // MarkerOptions opt=new MarkerOptions().position(latLng).title("User");
            //mMap.addMarker(opt);
            doublat=Double.valueOf(latitude);
            doublong=Double.valueOf(longitude);
            mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
            // Add a marker in Sydney and move the camera
            LatLng sydney = new LatLng(doublat, doublong);
            // .icon(BitmapDescriptorFactory.fromResource(R.drawable.arrow
            mMap.addMarker(new MarkerOptions().position(sydney).title(title+"\t ("+avaliabletxt+")")
                    .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.ic_local_parking_black_24dp)
                    )
            );
           // LatLng user=new LatLng(currentlocation.getLatitude(),currentlocation.getLongitude());
           // Log.e(TAG,"lat,lng = "+user);
            //Toast.makeText(this, "user = "+user, Toast.LENGTH_SHORT).show();
          //  mMap.addMarker(new MarkerOptions().position(user).title("user"));
          //  mMap.addMarker(new MarkerOptions().position(USER).title("User"));
           // mMap.addPolygon((new PolygonOptions()).add(USER,sydney).strokeWidth(5).strokeColor(Color.RED)
                    //.geodesic(true));
//            Location location=mMap.getMyLocation();
           // Log.e(TAG,"user lat = "+location.getLatitude()+"user lon = "+location.getLongitude());
            //LatLng ngp=new LatLng(mslat,mslon);
           // mMap.addMarker(new MarkerOptions().position(ngp).title("user"));

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(NAGPUR.getCenter(),10));
        }
    }
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
/*
//get latlong for corners for specified city

    LatLng one = new LatLng(27.700769, 85.300140);
    LatLng two = new LatLng(27.800769, 85.400140);

    LatLngBounds.Builder builder = new LatLngBounds.Builder();

    //add them to builder
    builder.include(one);
    builder.include(two);

    LatLngBounds bounds = builder.build();

    //get width and height to current display screen
    int width = getResources().getDisplayMetrics().widthPixels;
    int height = getResources().getDisplayMetrics().heightPixels;

    // 20% padding
    int padding = (int) (width * 0.20);

    //set latlong bounds
    mMap.setLatLngBoundsForCameraTarget(bounds);

    //move camera to fill the bound to screen
    mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding));

    //set zoom to level to current so that you won't be able to zoom out viz. move outside bounds
    mMap.setMinZoomPreference(mMap.getCameraPosition().zoom);
 */
// mMap.setMinZoomPreference(6.0f);
// mMap.setMaxZoomPreference(17.0f);
//  mMap.getMaxZoomLevel();

//mMap.setLatLngBoundsForCameraTarget(NAGPUR);
        /*
        var iconBase = 'https://maps.google.com/mapfiles/kml/shapes/';
  var marker = new google.maps.Marker({
    position: myLatLng,
    map: map,
    icon: iconBase + 'parking_lot_maps.png'
  });
   .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.effil)));

         */



       /* for (int i = 0; i < mapsData.getLongitude(); i++){
            String lon=mapsData.getLatitude();
            String lat=mapsData.getLatitude();

        }*/


        /* public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.e(TAG, "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);
        Toast.makeText(this, "km value = "+valueResult+"\n"+kmInDec+"\n"+meterInDec, Toast.LENGTH_SHORT).show();

        return Radius * c;
    }*/
/*
// to generate url for map...
    public String makeURL (double sourcelat, double sourcelog, double destlat, double destlog ){
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/directions/json");
        urlString.append("?origin=");// from
        urlString.append(Double.toString(sourcelat));
        urlString.append(",");
        urlString
                .append(Double.toString( sourcelog));
        urlString.append("&destination=");// to
        urlString
                .append(Double.toString( destlat));
        urlString.append(",");
        urlString.append(Double.toString(destlog));
        urlString.append("&sensor=false&mode=driving&alternatives=true");
        urlString.append("&key=SERVER-KEY");
        return urlString.toString();
    }
    */
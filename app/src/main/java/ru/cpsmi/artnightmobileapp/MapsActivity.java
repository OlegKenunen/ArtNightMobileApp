package ru.cpsmi.artnightmobileapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import ru.cpsmi.artnightmobileapp.data.Museum;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    private GoogleMap mMap;
    private Button searchButton;
    private ImageView searchIcon;
    private ImageView settingsIcon;

    // It holds the list of Museum objects fetched from Database
    private List<Museum> museumList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        DataController dataController = DataController.getInstance();
        dataController.setTestDataToLocalDB(this);

        museumList = dataController.getListOfMuseums(this);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(this);

        searchIcon = (ImageView) findViewById(R.id.imageViewSearchIcon);
        searchIcon.setImageResource(R.drawable.search_icon);

        settingsIcon = (ImageView) findViewById(R.id.imageViewSettingsIcon);
        settingsIcon.setImageResource(R.drawable.settings_icon);
        //


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

		/*
         * You'll need this in your class to release the helper when done.
		 */
        DataController dataController = DataController.getInstance();
        dataController.releaseHelper();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add markers
        DataController dataController = DataController.getInstance();
        List<Museum> listOfMuseums = dataController.getListOfMuseums(this);

        BitmapDescriptor eveningMarker = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
        BitmapDescriptor nightMarker = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
        BitmapDescriptor markerColor = nightMarker;
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        for (Museum currentMuseum : listOfMuseums) {
            LatLng museum = new LatLng(currentMuseum.getLatitude(), currentMuseum.getLongitude());
            try {
                if (currentMuseum.getEndTime().after((Date) formatter.parse("12:00"))) {
                    markerColor = eveningMarker;
                    Log.d("Art", "открытие: "+currentMuseum.getStartTime().toString());
                    Log.d("Art", "закрытие: "+currentMuseum.getEndTime().toString());
                }
            } catch (ParseException e) {
                Log.d("Art", "ParseException");
                e.printStackTrace();
            }

            mMap.addMarker(new MarkerOptions()
                    .position(museum)
                    .icon(markerColor)
                    .title(currentMuseum.getTitle()));


        }


        LatLng centerOfTheMap = new LatLng(59.9495479, 30.3162639);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(centerOfTheMap));
        mMap.setMinZoomPreference(10.0f);


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //   ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return;
        }
        mMap.setMyLocationEnabled(true);


    }

    public void showSelectedMuseum() {
        LatLng selectedMuseum = new LatLng(59.9495479, 30.3162639);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(selectedMuseum));
    }

    @Override
    public void onClick(View v) {

        Log.i("Art", "" + v + " pressed");
        if (v == searchButton) {
            startActivity(new Intent(this, SearchActivity.class));
        }
    }


}

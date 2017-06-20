package ru.cpsmi.artnightmobileapp;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import ru.cpsmi.artnightmobileapp.data.Museum;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    private GoogleMap mMap;


    // It holds the list of StudentDetails objects fetched from Database
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


        String[] museumTitles = dataController.getMuseumTitles(this);

        // Получаем ссылку на элемент AutoCompleteTextView в разметке
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoComplete);
        // Создаем адаптер для автозаполнения элемента AutoCompleteTextView
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, museumTitles);
        autoCompleteTextView.setAdapter(adapter);
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
        for (Museum currentMuseum : listOfMuseums) {
            LatLng museum = new LatLng(currentMuseum.getLatitude(), currentMuseum.getLongitude());
            mMap.addMarker(new MarkerOptions().position(museum).title(currentMuseum.getTitle()));
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

}

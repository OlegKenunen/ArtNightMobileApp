package ru.cpsmi.artnightmobileapp;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        LatLng museum1 = new LatLng(59.970984,30.32144);
        mMap.addMarker(new MarkerOptions().position(museum1).title("Ботанический сад Петра Великого"));
        LatLng museum2 = new LatLng(59.955366,30.311068);
        mMap.addMarker(new MarkerOptions().position(museum2).title("Планетарий"));
        LatLng museum3 = new LatLng(59.921772,30.35646);
        mMap.addMarker(new MarkerOptions().position(museum3).title("Лофт Проект ЭТАЖИ"));
        LatLng museum4 = new LatLng(59.965376,30.315603);
        mMap.addMarker(new MarkerOptions().position(museum4).title("Музей интерактивной науки «ЛабиринтУм»"));
        LatLng museum5 = new LatLng(59.958073,30.317481);
        mMap.addMarker(new MarkerOptions().position(museum5).title("Ленфильм"));


        mMap.moveCamera(CameraUpdateFactory.newLatLng(museum5));
        mMap.setMinZoomPreference(10.0f);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
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

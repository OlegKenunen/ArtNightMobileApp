package ru.cpsmi.artnightmobileapp;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import ru.cpsmi.artnightmobileapp.data.DatabaseHelper;
import ru.cpsmi.artnightmobileapp.data.Event;
import ru.cpsmi.artnightmobileapp.data.Museum;

import java.sql.SQLException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    private GoogleMap mMap;

    // Reference of DatabaseHelper class to access its DAOs and other components
    private DatabaseHelper databaseHelper = null;


    // Declaration of DAO to interact with corresponding table
    private Dao<Museum, Integer> museumDao;
    private Dao<Event, Integer> eventDao;

    // It holds the list of StudentDetails objects fetched from Database
    private List<Museum> museumList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        //DataController dataController = new DataController();
        //dataController.setTestDataToLocalDB(databaseHelper);

        final Museum museum = new Museum("Музей 1", 59.970984, 30.32144);


        try {
            // This is how, a reference of DAO object can be done
            final Dao<Museum, Integer> museumDao = getHelper().getMuseumDao();

            //This is the way to insert data into a database table
            museumDao.create(museum);
            //reset();
            //showDialog();
            Log.i("DB", "Create complete");

        } catch (SQLException e) {
            e.printStackTrace();
            Log.i("DB", "Create FAIL");
        }

        try {
            // This is how, a reference of DAO object can be done
            museumDao = getHelper().getMuseumDao();

            // Query the database. We need all the records so, used queryForAll()
            museumList = museumDao.queryForAll();

            Log.i("DB", "Select complete");


            int lastAdded=museumList.size();
            Log.i("DB", "Число музеев в базе данных: "+String.valueOf(museumList.size()));
            Log.i("DB", "последний добавленный Id: " + String.valueOf(museumList.get(lastAdded).getMuseumId()));
            Log.i("DB", "название последнего: " + museumList.get(lastAdded).getTitle());


        } catch (SQLException e) {
            e.printStackTrace();
            Log.i("DB", "Select FAIL");
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        String[] museumTitles = {
                "Ботанический сад Петра Великого",
                "Планетарий",
                "Лофт Проект ЭТАЖИ",
                "Музей интерактивной науки «ЛабиринтУм»",
                "Ленфильм"};

        // Получаем ссылку на элемент AutoCompleteTextView в разметке
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoComplete);
        // Создаем адаптер для автозаполнения элемента AutoCompleteTextView
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, museumTitles);
        autoCompleteTextView.setAdapter(adapter);
    }

    // This is how, DatabaseHelper can be initialized for future use
    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

		/*
         * You'll need this in your class to release the helper when done.
		 */
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
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
        LatLng museum1 = new LatLng(59.970984, 30.32144);
        mMap.addMarker(new MarkerOptions().position(museum1).title("Ботанический сад Петра Великого"));
        LatLng museum2 = new LatLng(59.955366, 30.311068);
        mMap.addMarker(new MarkerOptions().position(museum2).title("Планетарий"));
        LatLng museum3 = new LatLng(59.921772, 30.35646);
        mMap.addMarker(new MarkerOptions().position(museum3).title("Лофт Проект ЭТАЖИ"));
        LatLng museum4 = new LatLng(59.965376, 30.315603);
        mMap.addMarker(new MarkerOptions().position(museum4).title("Музей интерактивной науки «ЛабиринтУм»"));
        LatLng museum5 = new LatLng(59.958073, 30.317481);
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

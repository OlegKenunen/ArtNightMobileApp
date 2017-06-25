package ru.cpsmi.artnightmobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;
import ru.cpsmi.artnightmobileapp.data.Museum;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DescriptionActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView museumTitle, openTime, address;
    private WebView museumDescription;
    private ImageButton backButton;
    int selectedMuseumId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        backButton = (ImageButton) findViewById(R.id.backButton);
        backButton.setOnClickListener(this);

        museumTitle = (TextView) findViewById(R.id.museumTitle);
        openTime = (TextView) findViewById(R.id.openTime);
        address = (TextView) findViewById(R.id.address);

        museumDescription = (WebView) findViewById(R.id.museumDescription);

        DataController dataController = DataController.getInstance();

        Intent intent = getIntent();

        selectedMuseumId = intent.getIntExtra("museumId", 0);
        Log.i("Art", "MuseumId получено: " + selectedMuseumId);

        Museum selectedMuseum = dataController.getMuseumById(this, selectedMuseumId);


        museumTitle.setText(selectedMuseum.getTitle());
        DateFormat timeFormatter = new SimpleDateFormat("HH:mm");
        openTime.setText(timeFormatter.format(selectedMuseum.getStartTime()) + "-"
                + timeFormatter.format(selectedMuseum.getEndTime()));
        address.setText(selectedMuseum.getAddress());

        museumDescription.loadData(selectedMuseum.getProgramme(), "text/html; charset=UTF-8", null);

    }


    @Override
    public void onClick(View v) {
        if (v == backButton) {
            finish();
        }
    }

}

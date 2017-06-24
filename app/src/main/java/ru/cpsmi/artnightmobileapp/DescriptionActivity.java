package ru.cpsmi.artnightmobileapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DescriptionActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView museumTitle, openTime, address;
    private WebView museumDescription;
    private ImageButton backButton;

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
        //dataController.setTestDataToLocalDB(this);

        museumTitle.setText(dataController.getSelectedMuseum().getTitle());
        DateFormat timeFormatter = new SimpleDateFormat("HH:mm");
        openTime.setText(timeFormatter.format(dataController.getSelectedMuseum().getStartTime()) + "-"
                + timeFormatter.format(dataController.getSelectedMuseum().getEndTime()));
        address.setText(dataController.getSelectedMuseum().getAddress());

        museumDescription.loadData(dataController.getSelectedMuseum().getProgramme(), "text/html; charset=UTF-8", null);

    }


    @Override
    public void onClick(View v) {
        if (v == backButton) {
            finish();
        }
    }

}

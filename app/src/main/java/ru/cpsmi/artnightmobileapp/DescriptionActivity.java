package ru.cpsmi.artnightmobileapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class DescriptionActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView museumTitle, museumDescription;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        backButton = (ImageButton) findViewById(R.id.imageButtonBack);
        backButton.setOnClickListener(this);

        museumTitle = (TextView) findViewById(R.id.museumTitle);
        museumDescription = (TextView) findViewById(R.id.museumDescription);

        DataController dataController = DataController.getInstance();
        dataController.setTestDataToLocalDB(this);

        museumTitle.setText(dataController.getSelectedMuseum().getTitle());
        museumDescription.setText(dataController.getSelectedMuseum().getProgramme());

    }


    @Override
    public void onClick(View v) {
        if (v == backButton) {
            finish();
        }
    }

}

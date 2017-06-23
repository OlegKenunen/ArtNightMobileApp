package ru.cpsmi.artnightmobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;
import ru.cpsmi.artnightmobileapp.data.Museum;

import java.util.List;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private AutoCompleteTextView autoCompleteTextView;


    private List<Museum> museumList;
    private ListView listView;

    private ImageButton backButton;
    private ImageButton searchButton;
    private ImageButton clearButton;

    private int selectedRecordPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        DataController dataController = DataController.getInstance();
        dataController.setTestDataToLocalDB(this);
        String[] museumTitles = dataController.getMuseumTitles(this);

        // Получаем ссылку на элемент AutoCompleteTextView в разметке
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoComplete);
        // Создаем адаптер для автозаполнения элемента AutoCompleteTextView
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, museumTitles);
        autoCompleteTextView.setAdapter(adapter);

        searchButton = (ImageButton) findViewById(R.id.imageButtonSearch);
        searchButton.setOnClickListener(this);

        backButton = (ImageButton) findViewById(R.id.imageButtonBack);
        backButton.setOnClickListener(this);

        clearButton = (ImageButton) findViewById(R.id.imageButtonClear);
        clearButton.setOnClickListener(this);


    }

    private void onSearch() {

        DataController dataController = DataController.getInstance();
        dataController.setTestDataToLocalDB(this);
        String searchString = autoCompleteTextView.getText().toString();

        museumList = dataController.searchMuseums(this, searchString);


        int numberOfMuseums = museumList.size();
        if (numberOfMuseums == 0) {
            return;
        }
        final String[] museumTitles = new String[numberOfMuseums];
        for (int i = 0; i < numberOfMuseums; i++) {
            museumTitles[i] = museumList.get(i).getTitle();
        }

        listView = (ListView) findViewById(R.id.listViewMuseums);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, museumTitles);
        listView.setAdapter(adapter);

        selectedRecordPosition = -1;
        // Attach OnItemLongClickListener and OnItemClickListener to track user action and perform accordingly
        listView.setOnItemLongClickListener(this);
        listView.setOnItemClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v == backButton) {
            finish();
        } else if (v == clearButton) {
            autoCompleteTextView.setText("");
        } else if (v == searchButton) {
            if (autoCompleteTextView.getText().length() > 0) {
                onSearch();
            }
        }


    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("Art", "Short click, position=" + position);
        if (position >= 0) {
            selectedRecordPosition = position;
            Log.i("Art", "Clicked position=" + museumList.get(selectedRecordPosition).getTitle());
            //показать метку на карте


        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("Art", "Long click, position=" + position);
        if (position >= 0) {
            selectedRecordPosition = position;
            Log.i("Art", "Clicked position=" + museumList.get(selectedRecordPosition).getTitle());
            //Показать описание музея
            DataController dataController = DataController.getInstance();
            dataController.setTestDataToLocalDB(this);
            dataController.setSelectedMuseum(museumList.get(selectedRecordPosition));
            startActivity(new Intent(this, DescriptionActivity.class));

        }
        return false;
    }
}

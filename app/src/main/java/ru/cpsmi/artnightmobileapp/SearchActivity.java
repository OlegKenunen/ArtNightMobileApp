package ru.cpsmi.artnightmobileapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        DataController dataController = DataController.getInstance();
        dataController.setTestDataToLocalDB(this);
        String[] museumTitles = dataController.getMuseumTitles(this);

        // Получаем ссылку на элемент AutoCompleteTextView в разметке
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoComplete);
        // Создаем адаптер для автозаполнения элемента AutoCompleteTextView
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, museumTitles);
        autoCompleteTextView.setAdapter(adapter);
    }
}

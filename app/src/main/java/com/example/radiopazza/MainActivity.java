package com.example.radiopazza;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.LinearLayout;
import android.widget.EditText;



public class MainActivity extends AppCompatActivity {
    private RadioManager radioManager;
    private static final int ADD_RADIO_REQUEST_CODE = 1;

    private LinearLayout searchBarLayout;
    private EditText searchEditText;
    private ImageButton searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView radioListView = findViewById(R.id.radioListView);
        radioManager = new RadioManager(this, radioListView);

        ImageButton addButton = findViewById(R.id.menu_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addRadioIntent = new Intent(MainActivity.this, AddRadioActivity.class);
                startActivityForResult(addRadioIntent, ADD_RADIO_REQUEST_CODE);
            }
        });

        // Inizializza le variabili della barra di ricerca
        searchBarLayout = findViewById(R.id.searchBarLayout);
        searchEditText = findViewById(R.id.searchText);
        searchButton = findViewById(R.id.menu_search);

        // Gestisci il clic sul pulsante di ricerca
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchQuery = searchEditText.getText().toString().trim();
                radioManager.getRadioListAdapter().getFilter().filter(searchQuery);
            }
        });

        // Gestisci le modifiche nel campo di ricerca
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchQuery = searchEditText.getText().toString().trim();
                radioManager.getRadioListAdapter().getFilter().filter(searchQuery);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_RADIO_REQUEST_CODE && resultCode == RESULT_OK) {
            String radioName = data.getStringExtra("radioName");
            String radioUrl = data.getStringExtra("radioUrl");

            // Aggiungi la stazione radio a RadioManager
            radioManager.addRadio(radioName, radioUrl);
        }
    }
}




package com.example.radiopazza;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private RadioManager radioManager;
    private static final int ADD_RADIO_REQUEST_CODE = 1;

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_RADIO_REQUEST_CODE && resultCode == RESULT_OK) {
            String radioName = data.getStringExtra("radioName");
            String radioUrl = data.getStringExtra("radioUrl");

            radioManager.addRadio(radioName, radioUrl);
        }
    }
}

package com.example.radiopazza;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class AddRadioActivity extends AppCompatActivity {

    private EditText editRadioName;
    private EditText editRadioUrl;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_radio_activity);

        editRadioName = findViewById(R.id.radioName);
        editRadioUrl = findViewById(R.id.radioUrl);
        addButton = findViewById(R.id.radioButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String radioName = editRadioName.getText().toString();
                String radioUrl = editRadioUrl.getText().toString();

                Intent intent = new Intent();
                intent.putExtra("radioName", radioName);
                intent.putExtra("radioUrl", radioUrl);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        ImageButton home = findViewById(R.id.menu_home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addRadioIntent = new Intent(AddRadioActivity.this, MainActivity.class);
                startActivityForResult(addRadioIntent, 1);
            }
        });
    }
}
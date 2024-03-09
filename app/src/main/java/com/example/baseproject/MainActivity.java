package com.example.baseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.baseproject.model.Animal;
import com.example.baseproject.model.Item;
import com.example.baseproject.model.Park;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Park> parks;
    private TextView textViewLastAnimal;
    private LinearLayout linearParkButtons;
    private Button buttonLastAnimal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewLastAnimal = findViewById(R.id.textViewLastAnimal);
        linearParkButtons = findViewById(R.id.linearParkButtons);
        buttonLastAnimal = findViewById(R.id.buttonLastAnimal);

        loadParksFromJson();
        createButtons();

        buttonLastAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_animal();
            }
        });
    }

    private void loadParksFromJson() {
        // Load data from JSON file and populate 'parks' ArrayList
        String json = null;
        try {
            InputStream inputStream = getAssets().open("parcsCatalunya.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (json != null) {
            Gson gson = new Gson();
            parks = gson.fromJson(json, new TypeToken<ArrayList<Park>>() {}.getType());
        } else {
            Log.e("MainActivity", "Failed to load JSON file");
        }
    }
    private void createButtons() {
        if (parks != null) {
            for (Park park : parks) {
                Button button = new Button(this);
                button.setText(park.getName());
                button.setBackgroundResource(R.color.orange_button);
                button.setTextColor(getResources().getColor(android.R.color.white));

                // Space between buttons
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 8, 0, 8);
                button.setLayoutParams(params);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        displayParkDetails(park);
                    }
                });
                linearParkButtons.addView(button);
            }
        }
    }


    private void button_animal() {
        StringBuilder lastTenAnimals = new StringBuilder();
        Button button = new Button(this);

        if (parks != null && parks.size() > 0) {
            for (Park park : parks) {
                if (park.getAnimals() != null && park.getAnimals().getBirds() != null) {
                    for (Item bird : park.getAnimals().getBirds()) {
                        lastTenAnimals.append(bird.getName()).append("\n");
                    }
                }
                if (park.getAnimals() != null && park.getAnimals().getMammals() != null) {
                    for (Item mammal : park.getAnimals().getMammals()) {
                        lastTenAnimals.append(mammal.getName()).append("\n");
                    }
                }
            }
        }

        // Set the text for the last animal seen
        String lastAnimalSeen = "List of Animals:\n" + lastTenAnimals.toString();
        textViewLastAnimal.setText(lastAnimalSeen);
    }

    private void displayParkDetails(Park park) {
        textViewLastAnimal.setText("Last Animal Seen: ");
    }
}
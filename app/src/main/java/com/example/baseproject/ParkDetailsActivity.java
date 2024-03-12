package com.example.baseproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baseproject.model.Animal;
import com.example.baseproject.model.Item;
import com.example.baseproject.model.Park;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ParkDetailsActivity extends AppCompatActivity {
    private ImageView parkImageView;
    private TextView parkNameTextView;
    private TextView coordinatesTextView;
    private Button descriptionButton;
    private Button floraButton;
    private Button faunaButton;
    private TextView faunaTextView;
    private TextView floraTextView;
    private TextView descriptionTextView;
    private List<Park> parks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_details);

        parkImageView = findViewById(R.id.parkImageView);
        parkNameTextView = findViewById(R.id.parkNameTextView);
        coordinatesTextView = findViewById(R.id.coordinatesTextView);
        descriptionButton = findViewById(R.id.descriptionButton);
        floraButton = findViewById(R.id.floraButton);
        faunaButton = findViewById(R.id.faunaButton);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        floraTextView = findViewById(R.id.floraTextView);
        faunaTextView = findViewById(R.id.faunaTextView);
        setupBackButton();

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("selected_park")) {
            Park selectedPark = (Park) intent.getSerializableExtra("selected_park");

            if (selectedPark != null) {
                setParkImageView(selectedPark);
                int imageResource = getResources().getIdentifier(selectedPark.getImage(), "drawable", getPackageName());
                parkImageView.setImageResource(imageResource);
                parkNameTextView.setText(selectedPark.getName());
                setCoordinatesTextView(selectedPark.getCoordinates());
                setDescriptionButton(selectedPark.getDescription());
                setFloraButton(selectedPark.getFlora());
                floraTextView.setText(selectedPark.getFloraNames());
                setFaunaButton(selectedPark.getAnimals().getBirds(), selectedPark.getAnimals().getMammals());
                faunaTextView.setText(selectedPark.getAnimals().printAnimalNames());

            }
        }

        String json = loadParksFromJson();
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Park>>() {
        }.getType();
        parks = gson.fromJson(json, listType);

        floraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (parks != null && !parks.isEmpty()) {
                    for (Park park : parks) {
                        if (park.getName().equals(parkNameTextView.getText().toString())) {
                            floraTextView.setText(park.getFloraNames());
                            faunaTextView.setText(""); // Clear fauna text
                            descriptionTextView.setText(""); // Clear description text
                            break;
                        }
                    }
                }
            }
        });

        faunaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (parks != null && !parks.isEmpty()) {
                    for (Park park : parks) {
                        if (park.getName().equals(parkNameTextView.getText().toString())) {
                            setFaunaButton(park.getAnimals().getBirds(), park.getAnimals().getMammals());
                            floraTextView.setText("");
                            descriptionTextView.setText("");
                            break;
                        }
                    }
                }
            }
        });

        descriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (parks != null && !parks.isEmpty()) {
                    for (Park park : parks) {
                        if (park.getName().equals(parkNameTextView.getText().toString())) {
                            setDescriptionButton(park.getDescription());
                            floraTextView.setText("");
                            faunaTextView.setText("");
                            break;
                        }
                    }
                }
            }
        });
    }

    private String loadParksFromJson() {
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
            Log.e("ParkDetailsActivity", "Failed to load JSON file");
        }
        return json;
    }

    private void setFloraButton(List<Item> floraList) {
        if (floraList != null && !floraList.isEmpty()) {
            StringBuilder floraText = new StringBuilder("Flora:\n");
            for (Item flora : floraList) {
                floraText.append(flora.getName()).append("\n");
            }

            floraButton.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            floraButton.setTextColor(getResources().getColor(android.R.color.system_on_surface_light));
            floraButton.setAllCaps(false);
            floraButton.setPadding(20, 10, 20, 10);
        }
    }

    private void setFaunaButton(List<Item> birdsList, List<Item> mammalsList) {
        StringBuilder faunaText = new StringBuilder();

        if (birdsList != null && !birdsList.isEmpty()) {
            faunaText.append("Birds:\n");
            for (Item bird : birdsList) {
                faunaText.append(bird.getName()).append("\n");
            }
        }

        if (mammalsList != null && !mammalsList.isEmpty()) {
            faunaText.append("Mammals:\n");
            for (Item mammal : mammalsList) {
                faunaText.append(mammal.getName()).append("\n");
            }
        }

        faunaButton.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        faunaButton.setTextColor(getResources().getColor(android.R.color.system_on_surface_light));
        faunaButton.setAllCaps(false);
        faunaButton.setPadding(20, 10, 20, 10);
    }


    private void setDescriptionButton(String description) {
        descriptionTextView.setText(description);
        descriptionTextView.setVisibility(View.VISIBLE);
        descriptionButton.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        descriptionButton.setTextColor(getResources().getColor(android.R.color.system_on_surface_light));
        descriptionButton.setAllCaps(false);
        descriptionButton.setPadding(20, 10, 20, 10);
    }


    private void setParkImageView(Park park) {
        if (park != null) {
            int imageResource = getResources().getIdentifier(park.getImage(), "drawable", getPackageName());
            parkImageView.setImageResource(imageResource);
        }
    }

    private void setCoordinatesTextView(String coordinates) {
        coordinatesTextView.setText(coordinates);
    }


    private void setupBackButton() {
        Button backButton = findViewById(R.id.backButton);
        backButton.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        backButton.setTextColor(getResources().getColor(android.R.color.white));
        backButton.setAllCaps(false);
        backButton.setPadding(20, 10, 20, 10);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParkDetailsActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}
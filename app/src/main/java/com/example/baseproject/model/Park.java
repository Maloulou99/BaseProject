package com.example.baseproject.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Park implements Serializable {
    private String name;
    private String description;
    private String coordinates;
    private ArrayList<Item> flora;
    private Animal animals;
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public ArrayList<Item> getFlora() {
        return flora;
    }

    public String getFloraNames (){
        String names = "";
        for (int i = 0; i < flora.size(); i++) {
            names += flora.get(i).getName() + "\n";
        }
        return names;
    }

    public void setFlora(ArrayList<Item> flora) {
        this.flora = flora;
    }

    public Animal getAnimals() {
        return animals;
    }

    public void setAnimals(Animal animals) {
        this.animals = animals;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

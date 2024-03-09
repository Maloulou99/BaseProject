package com.example.baseproject.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Animal implements Serializable {
    private ArrayList<Item> birds;
    private ArrayList<Item> mammals;


    public Animal(ArrayList<Item> birds, ArrayList<Item> mammals) {
        this.birds = birds;
        this.mammals = mammals;
    }

    public ArrayList<Item> getBirds() {
        return birds;
    }

    public void setBirds(ArrayList<Item> birds) {
        this.birds = birds;
    }

    public ArrayList<Item> getMammals() {
        return mammals;
    }

    public void setMammals(ArrayList<Item> mammals) {
        this.mammals = mammals;
    }
}

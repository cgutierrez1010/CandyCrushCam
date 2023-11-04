package com.example.game;

import java.util.Random;

public class Candy {
    private int type;  // Candy type
    private int color; // Candy color ID
    private static final int NUM_COLORS = 6; // Number of available candy colors

    public Candy(int type) {
        this.type = type;
        this.color = generateRandomCandyColor();
        // Initialize other properties if needed
    }

    public int getType() {
        return type;
    }

    public int getColor() {
        return color;
    }

    private int generateRandomCandyColor() {
        // Generate a random color ID between 1 and NUM_COLORS
        return new Random().nextInt(NUM_COLORS) + 1;
    }

    // Add getters and setters for other properties if needed
}

package org.example.javafxminefield.models;

public enum Difficulties {
    EASY(9,9,10),
    MEDIUM(13,15,40),
    HARD(16,25,99);

    public final int rows, cols, numBombs;

    Difficulties(int rows, int cols, int numBombs) {
        this.rows = rows;
        this.cols = cols;
        this.numBombs = numBombs;
    }


    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}

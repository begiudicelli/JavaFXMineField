package org.example.javafxminefield.models;

public class Cell {
    private final int row;
    private final int col;

    private boolean revealed = false;
    private boolean marked = false;

    private boolean hasBomb;
    private int adjBombs;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean hasBomb() {
        return hasBomb;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public boolean isMarked() {
        return marked;
    }

    public int getAdjBombs() {
        return adjBombs;
    }

    public void setHasBomb(boolean hasBomb) {
        this.hasBomb = hasBomb;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public void setAdjBombs(int adjBombs) {
        this.adjBombs = adjBombs;
    }
}

package org.example.javafxminefield.models;

public class Board {
    private int rows;
    private int cols;
    private int numBombs;
    private Cell[][] cells;

    public Board(Difficulties difficulties) {
        this.rows = difficulties.rows;
        this.cols = difficulties.cols;
        this.numBombs = difficulties.numBombs;
        this.cells = new Cell[rows][cols];
        initializeCells();
    }

    private void initializeCells() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    public Cell getCell(int row, int col){
        return this.cells[row][col];
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public int getNumBombs() {
        return numBombs;
    }

    public void setNumBombs(int numBombs) {
        this.numBombs = numBombs;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }
}

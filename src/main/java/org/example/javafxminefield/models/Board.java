package org.example.javafxminefield.models;

import java.util.Random;

public class Board {
    private int rows;
    private int cols;
    private int numBombs;
    private Cell[][] cells;
    private boolean gameOver;

    public Board(Difficulties difficulties) {
        this.rows = difficulties.rows;
        this.cols = difficulties.cols;
        this.numBombs = difficulties.numBombs;
        this.cells = new Cell[rows][cols];
        initializeCells();
        placeBombs();
        calculateAdjacentMines();
    }

    private void initializeCells() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    private void placeBombs() {
        Random random = new Random();
        int bombsPlaced = 0;

        while (bombsPlaced < numBombs) {
            int x = random.nextInt(rows);
            int y = random.nextInt(cols);

            if (!cells[x][y].isMine()) {
                cells[x][y].setMine(true);
                bombsPlaced++;
            }
        }
    }

    private void calculateAdjacentMines() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!cells[i][j].isMine()) {
                    int count = 0;
                    for (int x = Math.max(0, i-1); x <= Math.min(rows-1, i+1); x++) {
                        for (int y = Math.max(0, j-1); y <= Math.min(cols-1, j+1); y++) {
                            if (cells[x][y].isMine()) count++;
                        }
                    }
                    cells[i][j].setAdjacentMines(count);
                }
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

    public boolean isGameOver() {
        return this.gameOver;
    }

    public void setGameOver(boolean condition){
        this.gameOver = condition;
    }
}

package org.example.javafxminefield;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.example.javafxminefield.models.*;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MineFieldController {

    @FXML
    private GridPane grid;
    @FXML
    private ComboBox<String> comboBoxDifficulties;
    @FXML
    private Button buttonNewGame;
    @FXML
    private Label labelNumBombs;

    private Board board;
    private final int numberOfAdjacentBombs = 1;

    public void initialize() {
        loadDifficulties();
        buttonNewGame.setOnAction(actionEvent -> startNewGame());
    }

    private void loadDifficulties() {
        ObservableList<String> difficulties = FXCollections.observableArrayList();
        for (Difficulties difficulty : Difficulties.values()) {
            difficulties.add(difficulty.toString());
        }
        comboBoxDifficulties.setItems(difficulties);
        comboBoxDifficulties.getSelectionModel().selectFirst();
    }

    private void startNewGame(){
        String selectedDifficulty = comboBoxDifficulties.getSelectionModel().getSelectedItem();
        Difficulties difficulty = Difficulties.valueOf(selectedDifficulty.toUpperCase());
        board = new Board(difficulty);
        fillBoardWithBombs(board);
        drawBoard();
    }

    private void drawBoard(){
        grid.getChildren().clear();
        String selectedDifficulty = comboBoxDifficulties.getSelectionModel().getSelectedItem();
        Difficulties difficulty = Difficulties.valueOf(selectedDifficulty.toUpperCase());

        int rows = difficulty.rows;
        int cols = difficulty.cols;
        int numBombs = difficulty.numBombs;
        labelNumBombs.setText(String.valueOf(numBombs));

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Button btn = new Button();
                btn.getStyleClass().add("cell");
                btn.setMinSize(40, 40);

                int finalI = i;
                int finalJ = j;
                btn.setOnAction(e -> {
                    //btn.getStyleClass().add("cell-revealed");
                    //btn.getStyleClass().add("cell-" + numberOfAdjacentBombs);
                    btn.setText("X");

                    board.getCell(finalI, finalJ).setMarked(true);
                    if(board.getCell(finalI, finalJ).hasBomb()) {
                        System.out.println("EXPLODIU!");
                    }
                });
                grid.add(btn, j, i);
            }
        }
    }

    private void fillBoardWithBombs(Board board) {
        int rows = board.getRows();
        int cols = board.getCols();
        int numBombs = board.getNumBombs();
        Cell[][] cells = board.getCells();

        Random rand = new Random();
        Set<String> bombPositions = new HashSet<>();

        while (bombPositions.size() < numBombs) {
            int r = rand.nextInt(rows);
            int c = rand.nextInt(cols);
            String pos = r + "," + c;

            if (!bombPositions.contains(pos)) {
                bombPositions.add(pos);
                cells[r][c].setHasBomb(true);
            }
        }
    }


}

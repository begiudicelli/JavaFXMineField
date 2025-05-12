package org.example.javafxminefield;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import org.example.javafxminefield.models.*;

public class MineFieldController {

    @FXML private GridPane grid;
    @FXML private ComboBox<String> comboBoxDifficulties;
    @FXML private Button buttonNewGame;
    @FXML private Label labelNumBombs;
    @FXML private Label labelTime;
    @FXML private Label labelStatus;

    private Timeline timer;
    private final IntegerProperty seconds = new SimpleIntegerProperty(0);
    private boolean firstClick = true;

    private Board board;
    private final boolean flagMode = false;


    public void initialize() {
        loadDifficulties();
        initializeTimer();
        buttonNewGame.setOnAction(_ -> startNewGame());
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
        createBoard();
    }

    private void createBoard(){
        setTimer();
        grid.getChildren().clear();
        String selectedDifficulty = comboBoxDifficulties.getSelectionModel().getSelectedItem();
        Difficulties difficulty = Difficulties.valueOf(selectedDifficulty.toUpperCase());
        labelNumBombs.setText(String.valueOf(difficulty.numBombs));

        board = new Board(difficulty);

        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                Cell cell = board.getCell(i, j);
                Button btn = getButton(cell);

                grid.add(btn, j, i);
            }}
    }

    private Button getButton(Cell cell) {
        Button btn = cell.getButton();

        btn.setText("");
        btn.setStyle("");

        btn.setOnMouseClicked(e -> {
            if (e.getButton() == javafx.scene.input.MouseButton.PRIMARY && !flagMode) {
                revealCell(cell);
            } else if (e.getButton() == javafx.scene.input.MouseButton.SECONDARY || e.getButton() == javafx.scene.input.MouseButton.PRIMARY) {
                toggleFlag(cell);
            }
        });
        return btn;
    }

    private void revealCell(Cell cell) {
        if (firstClick && !cell.isFlagged()) {
            firstClick = false;
            timer.play();
        }
        if (cell.isRevealed() || cell.isFlagged() || board.isGameOver()) return;

        cell.setRevealed(true);
        Button btn = cell.getButton();

        if (cell.isMine()) {
            btn.setText("💣");
            btn.setStyle("-fx-background-color: red;");
            board.setGameOver(true);
            revealAllMines();
            timer.stop();
            return;
        }

        if (cell.getAdjacentMines() > 0) {
            btn.setText(String.valueOf(cell.getAdjacentMines()));
        } else {
            revealAdjacentCells(cell.getRow(), cell.getCol());
        }

        btn.setStyle("-fx-background-color: lightgray;");

        if (checkWinCondition()) {
            timer.stop();
        }
    }

    private void revealAdjacentCells(int x, int y) {
        for (int i = Math.max(0, x-1); i <= Math.min(board.getRows()-1, x+1); i++) {
            for (int j = Math.max(0, y-1); j <= Math.min(board.getCols()-1, y+1); j++) {
                if (i == x && j == y) continue;
                revealCell(board.getCell(i, j));
            }
        }
    }

    private void toggleFlag(Cell cell) {
        if (cell.isRevealed() || board.isGameOver()) return;
        cell.setFlagged(!cell.isFlagged());
        Button btn = cell.getButton();
        if (cell.isFlagged())btn.setText("🚩");
        else btn.setText("");
    }

    private void revealAllMines() {
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                Cell cell = board.getCell(i, j);
                if (cell.isMine()) {
                    cell.getButton().setText("💣");
                }
            }
        }
    }

    private boolean checkWinCondition() {
        int unrevealedSafeCells = 0;

        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                Cell cell = board.getCell(i, j);
                if (!cell.isMine() && !cell.isRevealed()) {
                    unrevealedSafeCells++;
                }
            }
        }

        if (unrevealedSafeCells == 0) {
            board.setGameOver(true);
            labelStatus.setText("You Win!");
            return true;
        }
        return false;
    }

    private void initializeTimer(){
        timer = new Timeline(new KeyFrame(Duration.seconds(1), _ -> seconds.set(seconds.get() + 1)));
        timer.setCycleCount(Timeline.INDEFINITE);

        labelTime.textProperty().bind(seconds.asString("%ds"));
    }

    private void setTimer(){
        timer.stop();
        seconds.set(0);
        firstClick = true;
        grid.getChildren().clear();
        labelStatus.setText("Bom jogo!");
    }

}

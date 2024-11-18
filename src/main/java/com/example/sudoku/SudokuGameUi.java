package com.example.sudoku;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashSet;
import java.util.Random;

public class SudokuGameUi extends Application {
    private static final int SIZE = 9;
    private static final int MIN_REMOVALS = 30;
    private static final int MAX_REMOVALS = 40;

    private TextField[][] cells = new TextField[SIZE][SIZE];
    private GridPane grid;

    @Override
    public void start(Stage stage) throws IOException {
        SudokuSolver algo = new SudokuSolver();
        Random rand = new Random();

        Sudoku generator = new Sudoku(rand.nextInt(MIN_REMOVALS, MAX_REMOVALS));
        int[][] arr = generator.generate();
        grid = createSudokuGrid(arr, algo);
        Scene scene = new Scene(grid);

        stage.setTitle("Sudoku Solver");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private GridPane createSudokuGrid(int[][] arr, SudokuSolver algo) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(11));
        grid.setVgap(5);
        grid.setHgap(5);

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                TextField cell = createCell(arr[row][col]);
                cells[row][col] = cell;
                grid.add(cell, col, row);
                if (((row > 2 && row < 6) || (col > 2 && col < 6)) && ((row) / 3 != 1 || (col) / 3 != 1)) {
                    cell.setStyle(cell.getStyle() + "-fx-border-color: black; -fx-border-width: 2;");
                } else {
                    cell.setStyle(cell.getStyle() + "-fx-border-color: gray; -fx-border-width: 1;");
                }
            }
        }

        Button solve = new Button("Solve");
        Button check = new Button("Check");
        check.setOnAction(e -> checkSolution(arr, algo));
        solve.setOnAction(e -> solveSolution(arr, algo));
        grid.add(check, 5, 11);
        grid.add(solve, 3, 11);

        return grid;
    }

    private TextField createCell(int value) {
        TextField cell = new TextField();
        cell.setPrefSize(50, 50);
        cell.setStyle("-fx-font-size: 20; -fx-alignment: CENTER;");
        cell.setMaxWidth(50);
        cell.setMaxHeight(50);
        cell.setAlignment(javafx.geometry.Pos.CENTER);

        // Add border styles based on position
        if (value != 0) {
            cell.setText(String.valueOf(value));
            cell.setEditable(false);
            cell.setOpacity(0.5);
        } else {
            TextFormatter<String> formatter = new TextFormatter<>(change -> {
                String newText = change.getControlNewText();
                if (newText.matches("[1-9]?")) {
                    return change; // Accept change
                }
                return null; // Reject change
            });
            cell.setTextFormatter(formatter);
        }

        return cell;
    }

    private void solveSolution(int[][] arr, SudokuSolver algo) {
        boolean isCorrect = SudokuSolver.sudokustart(arr);

        if (isCorrect) {
            int[][] userGrid = SudokuSolver.board;
            for (int row = 0; row < SIZE; row++) {
                for (int col = 0; col < SIZE; col++) {
                    cells[row][col].setText(String.valueOf(userGrid[row][col]));
                }
            }
        } else {
            showAlert("The Sudoku can't be solved. Try again.");
        }
    }

    private void checkSolution(int[][] initialGrid, SudokuSolver algo) {
        int[][] userGrid = getUserInputGrid();
        boolean isCorrect = algo.checksolution(userGrid);

        if (isCorrect) {
            showAlert("Congratulations! The solution is correct.");
        } else {
            showMistakes(userGrid);
        }
    }

    private void showMistakes(int[][] board) {
        HashSet<Integer>[] rows = new HashSet[9];
        HashSet<Integer>[] cols = new HashSet[9];
        HashSet<Integer>[] boxes = new HashSet[9];

        for (int i = 0; i < 9; i++) {
            rows[i] = new HashSet<>();
            cols[i] = new HashSet<>();
            boxes[i] = new HashSet<>();
        }

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                int value = board[r][c];
                int boxIndex = (r / 3) * 3 + (c / 3);

                if (value != 0 && (rows[r].contains(value) || cols[c].contains(value) || boxes[boxIndex].contains(value))) {
                    setCellStyle(c, r, "-fx-border-color: red; -fx-border-width: 2;");
                } else {
                    resetCellStyle(c, r);
                }

                if (value != 0) {
                    rows[r].add(value);
                    cols[c].add(value);
                    boxes[boxIndex].add(value);
                }
            }
        }
    }

    private void setCellStyle(int col, int row, String style) {
        TextField cell = getTextFieldAt(grid, col, row);
        if (cell != null) {
            cell.setStyle(cell.getStyle() + style);
        }
    }

    private void resetCellStyle(int col, int row) {
        TextField cell = getTextFieldAt(grid, col, row);
        if (cell != null) {
            String baseStyle = "-fx-font-size: 20; -fx-alignment: CENTER; -fx-min-width: 50; -fx-min-height: 50;";
            if (((row > 2 && row < 6) || (col > 2 && col < 6)) && ((row) / 3 != 1 || (col) / 3 != 1)) {
                cell.setStyle(baseStyle + "-fx-border-color: black; -fx-border-width: 2;");
            } else {
                cell.setStyle(baseStyle + "-fx-border-color: gray; -fx-border-width: 1;");
            }
        }
    }

    private TextField getTextFieldAt(GridPane gridPane, int col, int row) {
        for (var child : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(child) == col && GridPane.getRowIndex(child) == row) {
                return (TextField) child;
            }
        }
        return null; // Return null if not found
    }

    private int[][] getUserInputGrid() {
        int[][] userGrid = new int[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                String text = cells[row][col].getText();
                userGrid[row][col] = text.isEmpty() ? 0 : Integer.parseInt(text);
            }
        }
        return userGrid;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }
}

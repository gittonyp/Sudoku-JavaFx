package com.example.sudoku;

import java.util.HashSet;

public class SudokuSolver {
    private static final int SIZE = 9;
    public static int[][] board;
    static SudokuSolver solver = new SudokuSolver();
    // Main method to solve the Sudoku puzzle
    public boolean solveSudoku(int[][] board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                // Find an empty cell
                if (board[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isValid(board, row, col, num)) {
                            board[row][col] = num; // Place the number

                            // Recursively try to solve the rest of the board
                            if (solveSudoku(board)) {
                                return true;
                            }

                            // Backtrack if the number doesn't lead to a solution
                            board[row][col] = 0; // Remove the number
                        }
                    }
                    return false; // Trigger backtracking
                }
            }
        }
        return true; // Puzzle solved
    }

    // Check if placing num at board[row][col] is valid
    private boolean isValid(int[][] board, int row, int col, int num) {
        // Check the row
        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] == num) {
                return false;
            }
        }

        // Check the column
        for (int i = 0; i < SIZE; i++) {
            if (board[i][col] == num) {
                return false;
            }
        }

        // Check the 3x3 subgrid
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i + startRow][j + startCol] == num) {
                    return false;
                }
            }
        }

        return true; // Valid placement
    }

    public boolean checksolution(int[][] board) {
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
                if (board[r][c] == 0) {
                    return false;
                }

                int value = board[r][c];
                int boxIndex = (r / 3) * 3 + (c / 3);

                if (rows[r].contains(value) || cols[c].contains(value) || boxes[boxIndex].contains(value)) {
                    return false;
                }

                rows[r].add(value);
                cols[c].add(value);
                boxes[boxIndex].add(value);
            }
        }

        return true;
    }

    // Helper method to print the board
    public void printBoard(int[][] board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
    }

    public static boolean sudokustart(int sudokiboard[][]) {
        board=sudokiboard;
        if(!solver.solveSudoku(board)){
            return false;
        }else {
            return true;
        }
    }

}




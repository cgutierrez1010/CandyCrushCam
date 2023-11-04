package com.example.game;

public class GameBoard {
    private Candy[][] candies;
    private int rows;
    private int columns;
    private int score;
    private int targetScore;

    public GameBoard(int rows, int columns, int targetScore) {
        this.rows = rows;
        this.columns = columns;
        this.targetScore = targetScore;
        candies = new Candy[rows][columns];
        score = 0;
        initializeBoard();
    }

    private void initializeBoard() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                candies[row][col] = new Candy(generateRandomCandyType());
            }
        }
    }

    private int generateRandomCandyType() {
        return (int) (Math.random() * 6) + 1; // For example, 6 different candy types
    }

    public boolean swapCandies(int row1, int col1, int row2, int col2) {
        if (isValidSwap(row1, col1, row2, col2)) {
            Candy temp = candies[row1][col1];
            candies[row1][col1] = candies[row2][col2];
            candies[row2][col2] = temp;

            if (checkMatches()) {
                score += calculateMatchScore();
                cascade();
                return true;
            } else {
                candies[row2][col2] = candies[row1][col1];
                candies[row1][col1] = temp;
            }
        }
        return false;
    }

    private boolean isValidSwap(int row1, int col1, int row2, int col2) {
        int rowDiff = Math.abs(row1 - row2);
        int colDiff = Math.abs(col1 - col2);
        return (rowDiff == 1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1);
    }

    public boolean checkMatches() {
        boolean matchFound = false;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns - 2; col++) {
                if (candies[row][col].getType() == candies[row][col + 1].getType() &&
                        candies[row][col].getType() == candies[row][col + 2].getType()) {
                    removeMatch(row, col, row, col + 1, row, col + 2);
                    matchFound = true;
                }
            }
        }

        for (int col = 0; col < columns; col++) {
            for (int row = 0; row < rows - 2; row++) {
                if (candies[row][col].getType() == candies[row + 1][col].getType() &&
                        candies[row][col].getType() == candies[row + 2][col].getType()) {
                    removeMatch(row, col, row + 1, col, row + 2, col);
                    matchFound = true;
                }
            }
        }

        return matchFound;
    }

    private void removeMatch(int row1, int col1, int row2, int col2, int row3, int col3) {
        candies[row1][col1] = null;
        candies[row2][col2] = null;
        candies[row3][col3] = null;
    }

    private int calculateMatchScore() {
        return 100; // Example score for a match
    }

    public void cascade() {
        for (int col = 0; col < columns; col++) {
            int destRow = rows - 1;
            for (int row = rows - 1; row >= 0; row--) {
                if (candies[row][col] != null) {
                    if (destRow != row) {
                        candies[destRow][col] = candies[row][col];
                        candies[row][col] = null;
                    }
                    destRow--;
                }
            }
            for (int i = destRow; i >= 0; i--) {
                candies[i][col] = new Candy(generateRandomCandyType());
            }
        }
    }
}

package org.tictactoe;

import java.util.Arrays;
import java.util.stream.Collectors;

public class GameField {
    public enum GameSide {
        Cross("X"),
        Zero("0"),
        None(" ");

        private final String displayText;

        GameSide(String displayText) {
            this.displayText = displayText;
        }

        @Override
        public String toString() {
            return displayText;
        }

        public GameSide getOppositeSide() {
            return this == GameSide.Cross ? GameSide.Zero : GameSide.Cross;
        }
    }

    public final int size;
    private final GameSide[][] data;

    public GameField(int size) {
        this.size = size;

        data = new GameSide[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                data[i][j] = GameSide.None;
            }
        }
    }

    public boolean isCellEmpty(int x, int y) {
        return GameSide.None.equals(data[y][x]);
    }

    public void setCellValue(int x, int y, GameSide value) {
        data[y][x] = value;
    }

    public void print() {
        System.out.println(formatDelimiterString());

        for (int i = 0; i < size; i++) {
            System.out.println(formatRowString(data[i]));
            System.out.println(formatDelimiterString());
        }
    }

    public boolean isGameEnded() {
        return getWinner() != null;
    }

    public GameSide getWinner() {
        GameSide[] diagonal = new GameSide[size];
        GameSide[] oppositeDiagonal = new GameSide[size];

        for (int i = 0; i < size; i++) {
            if (isLineFilledBySameValue(data[i])) {
                return data[i][0];
            }

            GameSide[] column = new GameSide[size];
            for (int j = 0; j < size; j++) {
                column[j] = data[j][i];
            }

            if (isLineFilledBySameValue(column)) {
                return column[0];
            }

            diagonal[i] = data[i][i];
            oppositeDiagonal[i] = data[i][size - i - 1];
        }

        if (isLineFilledBySameValue(diagonal)) {
            return diagonal[0];
        }

        if (isLineFilledBySameValue(oppositeDiagonal)) {
            return oppositeDiagonal[0];
        }

        boolean hasEmptyCells = Arrays.stream(data).flatMap(Arrays::stream)
                .anyMatch(GameSide.None::equals);


        return hasEmptyCells ? null : GameSide.None;
    }

    private boolean isLineFilledBySameValue(GameSide[] line) {
        if (GameSide.None.equals(line[0])) {
            return false;
        }

        for (int i = 0; i < line.length - 1; i++) {
            if (line[i] != line[i + 1]) {
                return false;
            }
        }

        return true;
    }

    private String formatDelimiterString() {
        StringBuilder rowString = new StringBuilder();

        for (int i = 0; i < size; i++) {
            rowString.append("+---");
        }
        rowString.append("+");

        return rowString.toString();
    }

    private String formatRowString(GameSide[] row) {
        return "| " +
                Arrays.stream(row)
                        .map(GameSide::toString)
                        .collect(Collectors.joining(" | ")) +
                " |";
    }
}

package org.tictactoe;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AI {
    private final GameField.GameSide AIGameSide;

    public AI(GameField.GameSide AIGameSide) {
        this.AIGameSide = AIGameSide;
    }

    public Point getTurn(GameField currentField) {
        Move bestMove = minimax(currentField, AIGameSide);
        return new Point(bestMove.index % currentField.size, bestMove.index / currentField.size);
    }

    private Move minimax(GameField field, GameField.GameSide currentSide) {
        GameField.GameSide winner = field.getWinner();
        if (AIGameSide.equals(winner)) {
            return new Move(-1, 10);
        }

        if (AIGameSide.getOppositeSide().equals(winner)) {
            return new Move(-1, -10);
        }

        if (GameField.GameSide.None.equals(winner)) {
            return new Move(-1, 0);
        }

        List<Move> moves = new ArrayList<>();

        for (int i = 0; i < field.size * field.size; i++) {
            int x = i % field.size;
            int y = i / field.size;
            if (!field.isCellEmpty(x, y)) {
                continue;
            }

            field.setCellValue(x, y, currentSide);
            Move currentMove = new Move(i, minimax(field, currentSide.getOppositeSide()).score);
            moves.add(currentMove);
            field.setCellValue(x, y, GameField.GameSide.None);
        }

        Move bestMove = null;
        if (AIGameSide.equals(currentSide)) {
            bestMove = moves.stream().max(Comparator.comparingInt(o -> o.score)).get();
        }

        if (AIGameSide.getOppositeSide().equals(currentSide)) {
            bestMove = moves.stream().min(Comparator.comparingInt(o -> o.score)).get();
        }

        return bestMove;
    }

    private static class Move {
        public int index;
        public int score;

        public Move(int index, int score) {
            this.index = index;
            this.score = score;
        }
    }
}

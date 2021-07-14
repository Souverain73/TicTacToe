package org.tictactoe;

import java.awt.*;

import java.util.HashMap;
import java.util.Map;

/**
 * вариант игры бот против бота для быстрой прокерки работоспособности
 */
public class AiWar {
    private static GameField gameField;
    private static final Map<GameField.GameSide, AI> aiMap = new HashMap<GameField.GameSide, AI>(){{
        put(GameField.GameSide.Cross, new AI(GameField.GameSide.Cross));
        put(GameField.GameSide.Zero, new AI(GameField.GameSide.Zero));
    }};

    public static void main(String[] args) {
        gameField = new GameField(3);

        GameField.GameSide currentSide = GameField.GameSide.Cross;

        while (!gameField.isGameEnded()) {
            gameField.print();
            handleAiTurn(aiMap.get(currentSide), currentSide);
            currentSide = currentSide.getOppositeSide();
        }

        gameField.print();

        GameField.GameSide winner = gameField.getWinner();

        if (winner == GameField.GameSide.None) {
            System.out.println("Ничья");
        } else {
            System.out.println("Что-то пошло не так. Один из ботов победил.");
        }
    }

    private static void handleAiTurn(AI ai, GameField.GameSide side) {
        long start = System.currentTimeMillis();
        Point aiTurn = ai.getTurn(gameField);
        System.out.printf("Ход компьютера: %d, %d%n", aiTurn.x + 1, aiTurn.y + 1);
        System.out.printf("Время хода: %d%n", System.currentTimeMillis() - start);
        gameField.setCellValue(aiTurn.x, aiTurn.y, side);
    }
}

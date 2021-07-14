package org.tictactoe;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TicTacToe {
    private static GameField gameField;
    private static BufferedReader consoleIn;
    private static AI ai;

    public static void main(String[] args) throws IOException {
        gameField = new GameField(3);
        consoleIn = new BufferedReader(new InputStreamReader(System.in));
        ai = new AI(GameField.GameSide.Zero);
        GameField.GameSide currentSide = GameField.GameSide.Cross;

        while (!gameField.isGameEnded()) {
            gameField.print();

            switch (currentSide) {
                case Zero:
                    handleAiTurn();
                    break;
                case Cross:
                    handleUserInput();
                    break;
            }
            currentSide = currentSide.getOppositeSide();
        }

        gameField.print();

        GameField.GameSide winner = gameField.getWinner();

        switch (winner) {
            case None:
                System.out.println("Ничья");
                break;
            case Cross:
                System.out.println("Победа!");
                break;
            case Zero:
                System.out.println("Поражение");
                break;
        }
    }

    private static void handleAiTurn() {
        long start = System.currentTimeMillis();
        Point aiTurn = ai.getTurn(gameField);
        System.out.printf("Ход компьютера: %d, %d%n", aiTurn.x + 1, aiTurn.y + 1);
        System.out.printf("Время хода: %d%n", System.currentTimeMillis() - start);
        gameField.setCellValue(aiTurn.x, aiTurn.y, GameField.GameSide.Zero);
    }

    private static void handleUserInput() throws IOException {
        System.out.println("Введите номер столбца и строки куда поставить крестик в формате x,y:");
        boolean isInputHandled = false;
        Point point = null;
        while (!isInputHandled) {
            isInputHandled = true;
            String inputLine = consoleIn.readLine();
            point = parseInputCoords(inputLine);

            if (point == null) {
                System.out.println("Не удалось прочитать координаты, попробуйте еше раз");
                isInputHandled = false;
                continue;
            }

            if (!gameField.isCellEmpty(point.x, point.y)) {
                System.out.println("Выбранная клетка уже занята, попробуйте еше раз");
                isInputHandled = false;
            }
        }

        gameField.setCellValue(point.x, point.y, GameField.GameSide.Cross);
    }

    private static Point parseInputCoords(String input) {
        Pattern inputPattern = Pattern.compile("(\\d*)\\s*[,.]\\s*(\\d*)");
        Matcher matcher = inputPattern.matcher(input);
        if (matcher.matches()) {
            return new Point(Integer.parseInt(matcher.group(1)) - 1, Integer.parseInt(matcher.group(2)) - 1);
        }

        return null;
    }
}

package com.jakepf00.chess;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Character.isLowerCase;

class ChessAI {
    static char[][] makeMove(char[][] board, boolean whitesTurn) {
        ArrayList<Move> possibleMoves = new ArrayList<Move>();
        if (whitesTurn) {
            return board;
        }
        else {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (isLowerCase(board[i][j])) {
                        for (int k = 0; k < 8; k++) {
                            for (int l = 0; l < 8; l++) {
                                if (RuleEngine.checkLegal(board, whitesTurn, i, j, k, l)) {
                                    possibleMoves.add(new Move(i, j, k, l));
                                }
                            }
                        }
                    }
                }
            }
            Move move = possibleMoves.get(new Random().nextInt(possibleMoves.size()));
            board[move.x2][move.y2] = board[move.x1][move.y1];
            board[move.x1][move.y1] = ' ';
            return board;
        }
    }
}
package com.jakepf00.chess;

import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Character.isLowerCase;
import static java.lang.Character.isUpperCase;

class ChessAI {
    static ArrayList<Move> possibleMoves(char[][] board, boolean whitesTurn) {
        ArrayList<Move> possibleMoves = new ArrayList<>();
        if (whitesTurn) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (isUpperCase(board[i][j])) {
                        for (int k = 0; k < 8; k++) {
                            for (int l = 0; l < 8; l++) {
                                if (RuleEngine.checkLegal(board, true, new Move(i, j, k, l))) {
                                    Move move = new Move(i, j, k, l);
                                    possibleMoves.add(move);
                                }
                            }
                        }
                    }
                }
            }
            if (possibleMoves.size() > 0) {
                Collections.sort(possibleMoves, new SortByScore());
                return possibleMoves;
            }
            else return possibleMoves;
        }
        else {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (isLowerCase(board[i][j])) {
                        for (int k = 0; k < 8; k++) {
                            for (int l = 0; l < 8; l++) {
                                if (RuleEngine.checkLegal(board, false, new Move(i, j, k, l))) {
                                    Move move = new Move(i, j, k, l);
                                    possibleMoves.add(move);
                                }
                            }
                        }
                    }
                }
            }
            if (possibleMoves.size() > 0) {
                Collections.sort(possibleMoves, new SortByScore());
                return possibleMoves;
            }
            else return possibleMoves;
        }
    }

    private static Move alphaBeta(int depth, int beta, int alpha, Move move, int player) {
        return new Move(0, 0, 0, 0);
    }
}
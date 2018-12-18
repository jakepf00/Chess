package com.jakepf00.chess;

import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Character.isLowerCase;
import static java.lang.Character.isUpperCase;

class ChessAI {
    static char[][] makeMove(char[][] board, boolean whitesTurn) {
        ArrayList<Move> possibleMoves = new ArrayList<>();
        if (whitesTurn) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (isUpperCase(board[i][j])) {
                        for (int k = 0; k < 8; k++) {
                            for (int l = 0; l < 8; l++) {
                                if (RuleEngine.checkLegal(board, true, new Move(i, j, k, l))) {
                                    Move move = new Move(i, j, k, l);
                                    char[][] boardCopy = RuleEngine.copyBoard(board);
                                    boardCopy = RuleEngine.makeMove(boardCopy, move);
                                    move.score -= RuleEngine.countMaterialWhite(boardCopy);
                                    move.score += RuleEngine.countMaterialBlack(boardCopy);
                                    move.score -= RuleEngine.boardPositionWhite(boardCopy);
                                    move.score += RuleEngine.boardPositionBlack(boardCopy);
                                    possibleMoves.add(move);
                                }
                            }
                        }
                    }
                }
            }
            if (possibleMoves.size() > 0) {
                Collections.sort(possibleMoves, new SortByScore());
                Move move = possibleMoves.get(0);
                board = RuleEngine.makeMove(board, move);
                return board;
            }
            else return board;
        }
        else {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (isLowerCase(board[i][j])) {
                        for (int k = 0; k < 8; k++) {
                            for (int l = 0; l < 8; l++) {
                                if (RuleEngine.checkLegal(board, false, new Move(i, j, k, l))) {
                                    Move move = new Move(i, j, k, l);
                                    char[][] boardCopy = RuleEngine.copyBoard(board);
                                    boardCopy = RuleEngine.makeMove(boardCopy, move);
                                    move.score += RuleEngine.countMaterialWhite(boardCopy);
                                    move.score -= RuleEngine.countMaterialBlack(boardCopy);
                                    move.score += RuleEngine.boardPositionWhite(boardCopy);
                                    move.score -= RuleEngine.boardPositionBlack(boardCopy);
                                    possibleMoves.add(move);
                                }
                            }
                        }
                    }
                }
            }
            if (possibleMoves.size() > 0) {
                Collections.sort(possibleMoves, new SortByScore());
                Move move = possibleMoves.get(0);
                board = RuleEngine.makeMove(board, move);
                return board;
            }
            else return board;
        }
    }
}
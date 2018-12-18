package com.jakepf00.chess;

import static java.lang.Character.isLowerCase;
import static java.lang.Character.isUpperCase;

class RuleEngine {
    static boolean checkLegal(char[][] board, boolean whitesTurn, Move move) {
        int x1 = move.x1;
        int y1 = move.y1;
        int x2 = move.x2;
        int y2 = move.y2;

        // Basic checks that apply to all pieces
        if (x1 == x2 && y1 == y2) return false; // Can't move to the same square
        if (x2 >= 8 || y2 >= 8 || x2 < 0 || y2 < 0) return false; // Can't play outside of the board
        char pieceToMove = board[x1][y1];
        char targetSquare = board[x2][y2];
        if (isUpperCase(pieceToMove) && !whitesTurn) return false; // Can only play on your turn
        if (isLowerCase(pieceToMove) && whitesTurn) return false;
        if (isUpperCase(pieceToMove) && isUpperCase(targetSquare)) return false; // Can't capture your own pieces
        if (isLowerCase(pieceToMove) && isLowerCase(targetSquare)) return false;
        if (pieceToMove == ' ') return false; // Can't move a blank square

        // Check for check
        if (!whitesTurn) {
            if (blackInCheck(board, move)) return false;
        } else {
            if (whiteInCheck(board, move)) return false;
        }

        // Piece specific checks
        return pieceMove(board, move);
    }

    static char[][] flipBoard(char[][] board) {
        char[][] newBoard = new char[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                newBoard[i][j] = board[7 - i][7 - j];
            }
        }
        return newBoard;
    }

    static char[][] makeMove(char[][] board, Move move) {
        board[move.x2][move.y2] = board[move.x1][move.y1];
        board[move.x1][move.y1] = ' ';
        return board;
    }

    static char[][] copyBoard(char[][] board) {
        char[][] copy = new char[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                copy[i][j] = board[i][j];
            }
        }
        return copy;
    }

    private static boolean pieceMove(char[][] board, Move move) {
        switch (board[move.x1][move.y1]) {
            case 'P':
            case 'p':
                return pawnMove(board, move);
            case 'K':
            case 'k':
                return kingMove(move);
            case 'R':
            case 'r':
                return rookMove(board, move);
            case 'N':
            case 'n':
                return knightMove(move);
            case 'B':
            case 'b':
                return bishopMove(board, move);
            case 'Q':
            case 'q':
                return queenMove(board, move);
            default:
                return false;
        }
    }
    private static boolean pawnMove(char[][] board, Move move) {
        int x1 = move.x1;
        int y1 = move.y1;
        int x2 = move.x2;
        int y2 = move.y2;
        if (board[x2][y2] != ' ') {
            return ((Math.abs(y2 - y1) == 1) && (x2 - x1 == -1));
        }
        else if (x1 == 6) {
            if (x2 - x1 == -2) {
                return ((y2 == y1) && (board[x1 - 1][y1] == ' '));
            } else {
                return ((y2 == y1) && (x2 - x1 == -1));
            }
        }
        else {
            return ((y2 == y1) && (x2 - x1) == -1);
        }
    }
    private static boolean kingMove(Move move) {
        int x1 = move.x1;
        int y1 = move.y1;
        int x2 = move.x2;
        int y2 = move.y2;
        return (!(Math.abs(x2 - x1) > 1) && !(Math.abs(y2 - y1) > 1));
    }
    private static boolean rookMove(char[][] board, Move move) {
        int x1 = move.x1;
        int y1 = move.y1;
        int x2 = move.x2;
        int y2 = move.y2;
        if (x2 == x1) {
            for (int i = Math.min(y1, y2) + 1; i < Math.max(y1, y2); i++) {
                if (board[x1][i] != ' ') return false;
            }
            return true;
        }
        else if (y2 == y1) {
            for (int i = Math.min(x1, x2) + 1; i < Math.max(x1, x2); i++) {
                if (board[i][y1] != ' ') return false;
            }
            return true;
        }
        else return false;
    }
    private static boolean knightMove(Move move) {
        int x1 = move.x1;
        int y1 = move.y1;
        int x2 = move.x2;
        int y2 = move.y2;
        return (((Math.abs(x2 - x1) == 2) && (Math.abs(y2 - y1) == 1)) || ((Math.abs(x2 - x1) == 1) && (Math.abs(y2 - y1) == 2)));
    }
    private static boolean bishopMove(char[][] board, Move move) {
        int x1 = move.x1;
        int y1 = move.y1;
        int x2 = move.x2;
        int y2 = move.y2;
        if (Math.abs(y2 - y1) == Math.abs(x2 - x1)) {
            if (x1 < x2 && y1 < y2) {
                for (int i = x1 + 1, j = y1 + 1; i < x2; i++, j++) {
                    if (board[i][j] != ' ') return false;
                }
            }
            if (x1 < x2 && y1 > y2) {
                for (int i = x1 + 1, j = y1 - 1; i < x2; i++, j--) {
                    if (board[i][j] != ' ') return false;
                }

            }
            if (x1 > x2 && y1 < y2) {
                for (int i = x1 - 1, j = y1 + 1; i > x2; i--, j++) {
                    if (board[i][j] != ' ') return false;
                }
            }
            if (x1 > x2 && y1 > y2) {
                for (int i = x1 - 1, j = y1 - 1; i > x2; i--, j--) {
                    if (board[i][j] != ' ') return false;
                }

            }
            return true;
        }
        else return false;
    }
    private static boolean queenMove(char[][] board, Move move) {
        return (rookMove(board, move) || bishopMove(board, move));
    }

    private static boolean blackInCheck(char[][] board, Move move) {
        char[][] boardCopy = copyBoard(board);
        boardCopy = makeMove(boardCopy, move);
        boardCopy = flipBoard(boardCopy);
        int kingX = 0;
        int kingY = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (boardCopy[i][j] == 'k') {
                    kingX = i;
                    kingY = j;
                }
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (isUpperCase(boardCopy[i][j])) {
                    if (pieceMove(boardCopy, new Move(i, j, kingX, kingY))) return true;
                }
            }
        }
        return false;
    }
    private static boolean whiteInCheck(char[][] board, Move move) {
        char[][] boardCopy = copyBoard(board);
        boardCopy = makeMove(boardCopy, move);
        boardCopy = flipBoard(boardCopy);
        int kingX = 0;
        int kingY = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (boardCopy[i][j] == 'K') {
                    kingX = i;
                    kingY = j;
                }
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (isLowerCase(boardCopy[i][j])) {
                    if (pieceMove(boardCopy, new Move(i, j, kingX, kingY))) return true;
                }
            }
        }
        return false;
    }

    static int countMaterialWhite(char[][] board) {
        int score = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch (board[i][j]) {
                    case 'P':
                        score += 1;
                        break;
                    case 'K':
                        score += 20;
                        break;
                    case 'R':
                        score += 5;
                        break;
                    case 'N':
                        score += 3;
                        break;
                    case 'B':
                        score += 3;
                        break;
                    case 'Q':
                        score += 9;
                        break;
                }
            }
        }
        return score;
    }
    static int countMaterialBlack(char[][] board) {
        int score = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch (board[i][j]) {
                    case 'p':
                        score += 1;
                        break;
                    case 'k':
                        score += 20;
                        break;
                    case 'r':
                        score += 5;
                        break;
                    case 'n':
                        score += 3;
                        break;
                    case 'b':
                        score += 3;
                        break;
                    case 'q':
                        score += 9;
                        break;
                }
            }
        }
        return score;
    }

    static int boardPositionWhite(char[][] board) {
        int score = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch (board[i][j]) {
                    case 'P':
                        score += pawnPositionScore[i][j];
                        break;
                    case 'K':
                        score += kingPositionScore[i][j];
                        break;
                    case 'R':
                        score += rookPositionScore[i][j];
                        break;
                    case 'N':
                        score += knightPositionScore[i][j];
                        break;
                    case 'B':
                        score += bishopPositionScore[i][j];
                        break;
                    case 'Q':
                        score += queenPositionScore[i][j];
                        break;
                }
            }
        }
        return score;
    }
    static int boardPositionBlack(char[][] board) {
        int score = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch (board[i][j]) {
                    case 'p':
                        score += pawnPositionScore[i][j];
                        break;
                    case 'k':
                        score += kingPositionScore[i][j];
                        break;
                    case 'r':
                        score += rookPositionScore[i][j];
                        break;
                    case 'n':
                        score += knightPositionScore[i][j];
                        break;
                    case 'b':
                        score += bishopPositionScore[i][j];
                        break;
                    case 'q':
                        score += queenPositionScore[i][j];
                        break;
                }
            }
        }
        return score;
    }

    private static int[][] pawnPositionScore = {
            {99, 99, 99, 99, 99, 99, 99, 99},
            {17, 18, 19, 20, 20, 19, 18, 17},
            {15, 17, 18, 19, 19, 18, 17, 15},
            {13, 15, 17, 19, 19, 17, 15, 14},
            {11, 14, 16, 18, 18, 16, 14, 11},
            { 8,  9, 10, 11, 11, 10,  9,  8},
            { 5,  6,  7,  8,  8,  7,  6,  5},
            { 0,  0,  0,  0,  0,  0,  0,  0},
    };
    private static char[][] kingPositionScore = {
            { 0, 20, 25, 15, 15, 25, 20,  0},
            {20, 25, 20, 10, 10, 20, 25, 20},
            {15, 20, 15, 10, 10, 15, 20, 15},
            {10, 15, 10, 12, 12, 10, 15, 10},
            {10, 15, 10, 12, 12, 10, 15, 10},
            {15, 20, 15, 10, 10, 15, 20, 15},
            {20, 25, 20, 10, 10, 20, 25, 20},
            { 0, 20, 25, 15, 15, 25, 20,  0},
    };
    private static char[][] rookPositionScore = {
            { 5, 10, 15, 20, 20, 15, 10,  5},
            {10, 13, 16, 20, 20, 16, 13, 10},
            {15, 19, 23, 27, 27, 23, 19, 15},
            {17, 22, 27, 32, 32, 27, 22, 17},
            {17, 22, 27, 32, 32, 27, 22, 17},
            {15, 19, 23, 27, 27, 23, 19, 15},
            {10, 13, 16, 20, 20, 16, 13, 10},
            { 5, 10, 15, 20, 20, 15, 10,  5},
    };
    private static char[][] knightPositionScore = {
            { 0,  5, 10, 15, 15, 10,  5,  0},
            { 5, 10, 15, 20, 20, 15, 10,  5},
            {10, 15, 20, 25, 25, 20, 15, 10},
            {15, 20, 25, 30, 30, 25, 20, 15},
            {15, 20, 25, 30, 30, 25, 20, 15},
            {10, 15, 20, 25, 25, 20, 15, 10},
            { 5, 10, 15, 20, 20, 15, 10,  5},
            { 0,  5, 10, 15, 15, 10,  5,  0},
    };
    private static char[][] bishopPositionScore = {
            { 0,  5, 10, 15, 15, 10,  5,  0},
            { 5, 10, 15, 20, 20, 15, 10,  5},
            {10, 15, 20, 25, 25, 20, 15, 10},
            {15, 20, 25, 30, 30, 25, 20, 15},
            {15, 20, 25, 30, 30, 25, 20, 15},
            {10, 15, 20, 25, 25, 20, 15, 10},
            { 5, 10, 15, 20, 20, 15, 10,  5},
            { 0,  5, 10, 15, 15, 10,  5,  0},
    };
    private static char[][] queenPositionScore = {
            { 0,  5, 10, 15, 15, 10,  5,  0},
            { 5, 10, 15, 20, 20, 15, 10,  5},
            {10, 15, 20, 25, 25, 20, 15, 10},
            {15, 20, 25, 30, 30, 25, 20, 15},
            {15, 20, 25, 30, 30, 25, 20, 15},
            {10, 15, 20, 25, 25, 20, 15, 10},
            { 5, 10, 15, 20, 20, 15, 10,  5},
            { 0,  5, 10, 15, 15, 10,  5,  0},
    };
}
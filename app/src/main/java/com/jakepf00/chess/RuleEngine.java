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

        // Piece specific checks
        switch (pieceToMove) {
            case 'P': // fall through
            case 'p': // TODO: add en passant
                if (targetSquare != ' ') {
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
            case 'K':
            case 'k': // TODO: add castling
                return (!(Math.abs(x2 - x1) > 1) && !(Math.abs(y2 - y1) > 1));
            case 'R':
            case 'r':
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
            case 'N':
            case 'n':
                return (((Math.abs(x2 - x1) == 2) && (Math.abs(y2 - y1) == 1)) || ((Math.abs(x2 - x1) == 1) && (Math.abs(y2 - y1) == 2)));
            case 'B':
            case 'b':
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
            case 'Q':
            case 'q':
                if (x2 == x1) {
                    for (int i = Math.min(y1, y2) + 1; i < Math.max(y1, y2); i++) {
                        if (board[x1][i] != ' ') return false;
                    }
                }
                else if (y2 == y1) {
                    for (int i = Math.min(x1, x2) + 1; i < Math.max(x1, x2); i++) {
                        if (board[i][y1] != ' ') return false;
                    }
                }
                else if (Math.abs(y2 - y1) == Math.abs(x2 - x1)) {
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
                }
                else return false;
                return true;
            default:
                return true;
        }
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
}

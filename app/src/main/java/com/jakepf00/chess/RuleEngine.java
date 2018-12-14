package com.jakepf00.chess;

import static java.lang.Character.isLowerCase;
import static java.lang.Character.isUpperCase;

class RuleEngine {
    static boolean checkLegal(char[][] board, boolean whitesTurn, int x1, int y1, int x2, int y2) {
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
                    return ((Math.abs(y2 - y1) == 1) && (Math.abs(x2 - x1) == 1));
                }
                else if (x1 == 6) {
                    if (Math.abs(x2 - x1) == 2) {
                        return ((y2 == y1) && (board[y1][x2 - 1] == ' '));
                    } else {
                        return ((y2 == y1) && (Math.abs(x2 - x1) == 1));
                    }
                }
                else {
                    return ((y2 == y1) && (Math.abs(x2 - x1) <= 1));
                }
            case 'K':
            case 'k':
                return (!(Math.abs(x2 - x1) > 1) && !(Math.abs(y2 - y1) > 1));
            case 'R':
            case 'r': // TODO: make sure rooks don't jump pieces
                return !((x2 != x1) && (y2 != y1));
            case 'N':
            case 'n':
                return (((Math.abs(x2 - x1) == 2) && (Math.abs(y2 - y1) == 1)) || ((Math.abs(x2 - x1) == 1) && (Math.abs(y2 - y1) == 2)));
            case 'B':
            case 'b': // TODO: make sure bishops don't jump pieces
                return (Math.abs(y2 - y1) == Math.abs(x2 - x1));
            case 'Q':
            case 'q': // TODO: make sure queens don't jump pieces
                return ((Math.abs(y2 - y1) == Math.abs(x2 - x1)) || !((x2 != x1) && (y2 != y1)));
            default:
                return true;
        }
    }
}

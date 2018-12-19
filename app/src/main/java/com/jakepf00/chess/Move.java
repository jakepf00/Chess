package com.jakepf00.chess;

import java.util.Comparator;

class Move {
    int x1, y1, x2, y2;
    int score = 0;
    char pieceCaptured;

    Move(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        pieceCaptured = RuleEngine.board[x2][y2];
    }
}

class SortByScore implements Comparator<Move> {
    public int compare(Move a, Move b) {
        return a.score - b.score;
    }
}

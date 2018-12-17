package com.jakepf00.chess;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.MotionEvent;

import static java.lang.Math.floor;
import static java.lang.Math.min;

public class GameState {
    private Paint lightPaint = new Paint();
    private Paint darkPaint = new Paint();

    private int screenWidth = 0;
    private int tileSize = screenWidth / 8;

    private Bitmap BmChessPieces;
    private int BmTileSize;

    private int xTilePrevious = 8;
    private int yTilePrevious = 8;
    private int currentX = 0;
    private int currentY = 0;
    private boolean whitesTurn = true;

    private char board[][] = {
            {'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r'},
            {'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
            {'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'}};



    GameState() {
        lightPaint.setARGB(255, 255, 255, 255);
        darkPaint.setARGB(255, 0, 0, 0);

        //whitesTurn = true;
        //board = ChessAI.makeMove(board, whitesTurn);
        //whitesTurn = !whitesTurn;
        //board = flipBoard(board);
    }

    boolean update() {
        return true;
    }

    boolean keyPressed(int keyCode) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            screenWidth += 8;
        }
        else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            screenWidth-= 8;
        }
        tileSize = screenWidth / 8;
        return true;
    }

    boolean screenTouched(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            double yTile = floor((event.getX()) / tileSize);
            double xTile = floor((event.getY()) / tileSize);
            xTilePrevious = (int) xTile;
            yTilePrevious = (int) yTile;
            currentX = (int) event.getX();
            currentY = (int) event.getY();
        }
        else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            currentX = (int) event.getX();
            currentY = (int) event.getY();
        }
        else if (event.getAction() == MotionEvent.ACTION_UP) {
            double yTile = floor((event.getX()) / tileSize);
            double xTile = floor((event.getY()) / tileSize);
            if (RuleEngine.checkLegal(board, whitesTurn, new Move(xTilePrevious, yTilePrevious, (int) xTile, (int) yTile))) {
                board[(int) xTile][(int) yTile] = board[xTilePrevious][yTilePrevious];
                board[xTilePrevious][yTilePrevious] = ' ';
                board = RuleEngine.flipBoard(board);
                whitesTurn = !whitesTurn;
                board = ChessAI.makeMove(board, whitesTurn);
                board = RuleEngine.flipBoard(board);
                whitesTurn = !whitesTurn;
            }
            xTilePrevious = 8;
            yTilePrevious = 8;
        }
        return true;
    }

    public void draw(Canvas canvas) {
        canvas.drawARGB(0, 0, 0, 0);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Paint paint;
                if ((i + j) % 2 == 0) paint = lightPaint;
                else paint = darkPaint;
                Rect currentSquare = new Rect((j * tileSize), (i * tileSize), ((j + 1) * tileSize), ((i + 1) * tileSize));
                canvas.drawRect(currentSquare, paint);
                if ((xTilePrevious == i) && (yTilePrevious == j)) continue;
                switch (board[i][j]) {
                    case 'K':
                        canvas.drawBitmap(BmChessPieces, new Rect(0, 0, BmTileSize, BmTileSize), currentSquare, null);
                        break;
                    case 'Q':
                        canvas.drawBitmap(BmChessPieces, new Rect(BmTileSize, 0, 2 * BmTileSize, BmTileSize), currentSquare, null);
                        break;
                    case 'B':
                        canvas.drawBitmap(BmChessPieces, new Rect(2 * BmTileSize, 0, 3 * BmTileSize, BmTileSize), currentSquare, null);
                        break;
                    case 'N':
                        canvas.drawBitmap(BmChessPieces, new Rect(3 * BmTileSize, 0, 4 * BmTileSize, BmTileSize), currentSquare, null);
                        break;
                    case 'R':
                        canvas.drawBitmap(BmChessPieces, new Rect(4 * BmTileSize, 0, 5 * BmTileSize, BmTileSize), currentSquare, null);
                        break;
                    case 'P':
                        canvas.drawBitmap(BmChessPieces, new Rect(5 * BmTileSize, 0, 6 * BmTileSize, BmTileSize), currentSquare, null);
                        break;
                    case 'k':
                        canvas.drawBitmap(BmChessPieces, new Rect(6 * BmTileSize, 0, 7 * BmTileSize, BmTileSize), currentSquare, null);
                        break;
                    case 'q':
                        canvas.drawBitmap(BmChessPieces, new Rect(7 * BmTileSize, 0, 8 * BmTileSize, BmTileSize), currentSquare, null);
                        break;
                    case 'b':
                        canvas.drawBitmap(BmChessPieces, new Rect(8 * BmTileSize, 0, 9 * BmTileSize, BmTileSize), currentSquare, null);
                        break;
                    case 'n':
                        canvas.drawBitmap(BmChessPieces, new Rect(9 * BmTileSize, 0, 10 * BmTileSize, BmTileSize), currentSquare, null);
                        break;
                    case 'r':
                        canvas.drawBitmap(BmChessPieces, new Rect(10 * BmTileSize, 0, 11 * BmTileSize, BmTileSize), currentSquare, null);
                        break;
                    case 'p':
                        canvas.drawBitmap(BmChessPieces, new Rect(11 * BmTileSize, 0, 12 * BmTileSize, BmTileSize), currentSquare, null);
                        break;
                    default:
                        break;
                }
            }
        }
        if ((xTilePrevious < 8) && (yTilePrevious < 8)) {
            switch (board[xTilePrevious][yTilePrevious]) {
                case 'K':
                    canvas.drawBitmap(BmChessPieces, new Rect(0, 0, BmTileSize, BmTileSize), new Rect(currentX - (tileSize / 2), currentY - (tileSize / 2), currentX + (tileSize / 2), currentY + (tileSize / 2)), null);
                    break;
                case 'Q':
                    canvas.drawBitmap(BmChessPieces, new Rect(BmTileSize, 0, 2 * BmTileSize, BmTileSize), new Rect(currentX - (tileSize / 2), currentY - (tileSize / 2), currentX + (tileSize / 2), currentY + (tileSize / 2)), null);
                    break;
                case 'B':
                    canvas.drawBitmap(BmChessPieces, new Rect(2 * BmTileSize, 0, 3 * BmTileSize, BmTileSize), new Rect(currentX - (tileSize / 2), currentY - (tileSize / 2), currentX + (tileSize / 2), currentY + (tileSize / 2)), null);
                    break;
                case 'N':
                    canvas.drawBitmap(BmChessPieces, new Rect(3 * BmTileSize, 0, 4 * BmTileSize, BmTileSize), new Rect(currentX - (tileSize / 2), currentY - (tileSize / 2), currentX + (tileSize / 2), currentY + (tileSize / 2)), null);
                    break;
                case 'R':
                    canvas.drawBitmap(BmChessPieces, new Rect(4 * BmTileSize, 0, 5 * BmTileSize, BmTileSize), new Rect(currentX - (tileSize / 2), currentY - (tileSize / 2), currentX + (tileSize / 2), currentY + (tileSize / 2)), null);
                    break;
                case 'P':
                    canvas.drawBitmap(BmChessPieces, new Rect(5 * BmTileSize, 0, 6 * BmTileSize, BmTileSize), new Rect(currentX - (tileSize / 2), currentY - (tileSize / 2), currentX + (tileSize / 2), currentY + (tileSize / 2)), null);
                    break;
                case 'k':
                    canvas.drawBitmap(BmChessPieces, new Rect(6 * BmTileSize, 0, 7 * BmTileSize, BmTileSize), new Rect(currentX - (tileSize / 2), currentY - (tileSize / 2), currentX + (tileSize / 2), currentY + (tileSize / 2)), null);
                    break;
                case 'q':
                    canvas.drawBitmap(BmChessPieces, new Rect(7 * BmTileSize, 0, 8 * BmTileSize, BmTileSize), new Rect(currentX - (tileSize / 2), currentY - (tileSize / 2), currentX + (tileSize / 2), currentY + (tileSize / 2)), null);
                    break;
                case 'b':
                    canvas.drawBitmap(BmChessPieces, new Rect(8 * BmTileSize, 0, 9 * BmTileSize, BmTileSize), new Rect(currentX - (tileSize / 2), currentY - (tileSize / 2), currentX + (tileSize / 2), currentY + (tileSize / 2)), null);
                    break;
                case 'n':
                    canvas.drawBitmap(BmChessPieces, new Rect(9 * BmTileSize, 0, 10 * BmTileSize, BmTileSize), new Rect(currentX - (tileSize / 2), currentY - (tileSize / 2), currentX + (tileSize / 2), currentY + (tileSize / 2)), null);
                    break;
                case 'r':
                    canvas.drawBitmap(BmChessPieces, new Rect(10 * BmTileSize, 0, 11 * BmTileSize, BmTileSize), new Rect(currentX - (tileSize / 2), currentY - (tileSize / 2), currentX + (tileSize / 2), currentY + (tileSize / 2)), null);
                    break;
                case 'p':
                    canvas.drawBitmap(BmChessPieces, new Rect(11 * BmTileSize, 0, 12 * BmTileSize, BmTileSize), new Rect(currentX - (tileSize / 2), currentY - (tileSize / 2), currentX + (tileSize / 2), currentY + (tileSize / 2)), null);
                    break;
                default:
                    break;
            }
        }
    }

    void setDimensions(int width, int height) {
        screenWidth = min(width, height);
        tileSize = screenWidth / 8;
    }
    void setChessBitmap(Bitmap bm) {
        BmChessPieces = bm;
        BmTileSize = bm.getHeight();
    }
}
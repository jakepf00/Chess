package com.jakepf00.chess;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private GameState state;

    GameThread(SurfaceHolder surfaceHolder, Context context) {
        this.surfaceHolder = surfaceHolder;
        state = new GameState();

        Bitmap chessPieces = BitmapFactory.decodeResource(context.getResources(), R.drawable.chess_pieces);
        state.setChessBitmap(chessPieces);
    }

    @Override
    public void run() {
        boolean running = true;
        while (running) {
            Canvas canvas = surfaceHolder.lockCanvas();
            running = state.update();
            state.draw(canvas);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    GameState getGameState() {
        return state;
    }
}

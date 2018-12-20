package com.jakepf00.chess;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;

public class GameView extends SurfaceView implements Callback {
    private GameThread thread;

    @SuppressLint("ClickableViewAccessibility")
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        setFocusable(true);

        thread = new GameThread(holder, context);

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return thread.getGameState().screenTouched(event);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent msg) {
        return thread.getGameState().keyPressed(keyCode);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        thread.getGameState().setDimensions(width, height);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) { thread.start(); }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread.stop();
    }

    public void newGame(boolean playAI) {
        thread.getGameState().startNewGame(playAI);
    }
    public void giveHint() {
        thread.getGameState().giveHint();
    }
    public void undoMove() {
        thread.getGameState().undoMove();
    }
    public void redoMove() {
        thread.getGameState().redoMove();
    }
}

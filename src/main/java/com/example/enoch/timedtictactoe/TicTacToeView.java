package com.example.enoch.timedtictactoe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class TicTacToeView extends View {

    private BitmapDrawable mExampleDrawable;

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColIndex() {
        return colIndex;
    }

    private int rowIndex;
    private int colIndex;
    private TicTacToeFragment mParent;

    public TicTacToeView(Context context) {
        super(context);
    }

    public TicTacToeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TicTacToeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setIndices(int row,int col)
    {
        rowIndex=row;
        colIndex=col;
    }

    public void setParent(TicTacToeFragment parent) {
        mParent = parent;
    }

    public void reset()
    {
        mExampleDrawable=null;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mExampleDrawable == null && !mParent.turnFinished()) {
            if(event.getAction()==MotionEvent.ACTION_DOWN) {
                invalidate();
                int imageId=R.drawable.x_icon;
                if(mParent.getGameActivity().getCurrentPlayer()==GameActivity.PLAYER_2)
                    imageId=R.drawable.o_icon;
                mExampleDrawable=(BitmapDrawable) ContextCompat.getDrawable(getContext(),imageId);
                mParent.setGridSquare(rowIndex,colIndex,imageId);
                mParent.finishedTurn();
            }
        }
        return true;
    }

    public void onAIChosen()
    {
        invalidate();
        mExampleDrawable=(BitmapDrawable) ContextCompat.getDrawable(getContext(),R.drawable.o_icon);
        mParent.setGridSquare(rowIndex,colIndex,R.drawable.o_icon);
        mParent.finishedTurn();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mExampleDrawable != null) {
            Rect rect=new Rect();
            rect.set(0,0,getWidth(),getHeight());
            canvas.drawBitmap(mExampleDrawable.getBitmap(),null,rect,null);
        }
    }
}

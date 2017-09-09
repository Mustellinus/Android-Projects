package com.example.enoch.timedtictactoe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import java.io.IOException;


/**
 * Created by Enoch on 6/22/2017.
 */

public class ResultView extends View {

    private BitmapDrawable mIcon;
    public ResultView(Context context) {
        super(context);
    }

    public ResultView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ResultView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mIcon !=null)
        {
            Rect rect=new Rect();
            rect.set(0,0,getWidth(),getHeight());
            canvas.drawBitmap(mIcon.getBitmap(),null,rect,null);
        }
    }

    public void showResult(int winner)
    {
        invalidate();
        int imageId=R.drawable.x_icon;
        if(winner==TicTacToeGrid.O)
            imageId=R.drawable.o_icon;
        else if(winner==TicTacToeGrid.STALEMATE)
            imageId=R.drawable.stalemate;
        mIcon=(BitmapDrawable)ContextCompat.getDrawable(getContext(),imageId);
    }

    public void reset()
    {
        mIcon=null;
        invalidate();
    }
}

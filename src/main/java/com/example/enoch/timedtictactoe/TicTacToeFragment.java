package com.example.enoch.timedtictactoe;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Random;

/**
 * Created by Enoch on 6/20/2017.
 */

public class TicTacToeFragment extends RecyclerView.ViewHolder{

    private TicTacToeView[] mSquares;
    private TicTacToeGrid mGrid;
    private ResultView mResultView;
    private View mView;
    private boolean mFinishedTurn=false;
    private boolean mVictory=false;
    private GameActivity mActivity;

    public TicTacToeFragment(View view,GameActivity activity) {
        super(view);
        mView=view;
        mSquares=new TicTacToeView[9];
        loadSquare(0,R.id.square_00,0,0);
        loadSquare(1,R.id.square_01,0,1);
        loadSquare(2,R.id.square_02,0,2);
        loadSquare(3,R.id.square_10,1,0);
        loadSquare(4,R.id.square_11,1,1);
        loadSquare(5,R.id.square_12,1,2);
        loadSquare(6,R.id.square_20,2,0);
        loadSquare(7,R.id.square_21,2,1);
        loadSquare(8,R.id.square_22,2,2);
        mResultView=(ResultView)view.findViewById(R.id.resultDisplay);

        mGrid=new TicTacToeGrid();
        mActivity=activity;
    }

    public void reset()
    {
        mFinishedTurn=false;
        mVictory=false;
        mGrid.reset();
        for(int i=0;i<9;i++)
            mSquares[i].reset();
        mResultView.reset();
    }
    public void endTurn()
    {
        mFinishedTurn=true;
    }

    public void AIturn()
    {
        if(mGrid.getGridSpace(1,1)==TicTacToeGrid.EMPTY)
            mSquares[4].onAIChosen();
        else
        {
            Random rand=new Random();
            boolean found =false;
            while(found ==false)
            {
                int square=rand.nextInt(9);
                if(mGrid.getGridSpace(mSquares[square].getRowIndex(),mSquares[square].getColIndex())==TicTacToeGrid.EMPTY)
                {
                    mSquares[square].onAIChosen();
                    found=true;
                }
            }
        }
    }

    public void resetTurn()
    {
        mFinishedTurn=false;
    }
    public boolean endGame()
    {
        return mVictory;
    }

    public void finishedTurn() {
        mFinishedTurn = true;
        mActivity.updateTurn();
    }

    public boolean turnFinished()
    {
        return mFinishedTurn;
    }

    public GameActivity getGameActivity()
    {
        return mActivity;
    }

    public void setGridSquare(int row, int col, int type)
    {
        if(type==R.drawable.x_icon)
            mGrid.setGridSpace(row,col,TicTacToeGrid.X);
        else
            mGrid.setGridSpace(row,col,TicTacToeGrid.O);
        int victory=mGrid.checkVictory();
        switch(victory)
        {
            case TicTacToeGrid.X:
            {
                mResultView.showResult(TicTacToeGrid.X);
                mActivity.addVictory(victory);
                break;
            }
            case TicTacToeGrid.O:
            {
                mResultView.showResult(TicTacToeGrid.O);
                mActivity.addVictory(victory);
                break;
            }
            case TicTacToeGrid.STALEMATE:
            {
                mResultView.showResult(TicTacToeGrid.STALEMATE);
                mActivity.addVictory(victory);
                break;
            }
            default:
                break;
        }
        if(victory != TicTacToeGrid.EMPTY)
            mVictory=true;
    }

    private void loadSquare(int index,int id,int row, int col)
    {
        TicTacToeView tView=(TicTacToeView)mView.findViewById(id);
        mSquares[index]=tView;
        tView.setIndices(row,col);
        tView.setParent(this);
    }
}

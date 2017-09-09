package com.example.enoch.timedtictactoe;

/**
 * Created by Enoch on 6/20/2017.
 */

public class TicTacToeGrid {

    public static final int EMPTY=0;
    public static final int X=1;
    public static final int O=2;
    public static final int STALEMATE=-1;

    private boolean mFinished=false;

    private int[][] grid;

    public TicTacToeGrid()
    {
        grid= new int[3][3];
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
                grid [i][j]=EMPTY;
        }
    }

    public int getGridSpace(int row, int col)
    {
        return grid [row][col];
    }

    public void setGridSpace(int row, int col,int mark)
    {
        grid [row][col]=mark;
    }

    public int checkVictory()
    {
        int i;
        if(( i=checkRows())!=STALEMATE)
            return i;
        if(( i=checkCols())!=STALEMATE)
            return i;
        if(( i=checkDiag())!=STALEMATE)
            return i;
        if(mFinished==true)
            return STALEMATE;
        return EMPTY;//play will continue
    }

    public boolean fullRow(int index)
    {
        for(int i=0;i<3;i++)
        {
            if(grid [index][i]==EMPTY)
                return false;
        }
        return true;
    }
    public int checkRows()
    {
        mFinished=true;
        for(int i=0;i<3;i++)
        {
            if(!fullRow(i))
                mFinished=false;
            else if(grid[i][0]==grid[i][1]&& grid[i][1]==grid[i][2] )
                return grid[i][0];
        }
        return STALEMATE;
    }

    public int checkCols()
    {
        for(int i=0;i<3;i++)
        {
            if(grid[0][i]!=EMPTY)
            {
                if (grid[0][i] == grid[1][i] && grid[1][i] == grid[2][i])
                    return grid[0][i];
            }
        }
        return STALEMATE;
    }

    public int checkDiag()
    {
        if(grid[1][1]!=EMPTY)
        {
            if (grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2])
                return grid[0][0];
            if (grid[0][2] == grid[1][1] && grid[1][1] == grid[2][0])
                return grid[0][2];
        }
        return STALEMATE;
    }

    public void reset()
    {
        mFinished=false;
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
                grid [i][j]=EMPTY;
        }
    }
}

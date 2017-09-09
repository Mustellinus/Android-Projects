package com.example.enoch.timedtictactoe;

/**
 * Created by Enoch on 6/20/2017.
 */

public class GameManager {

    public static final int PLAYER_1=0;
    public static final int PLAYER_2=1;
    public static final int STALEMATE=2;

    private static GameManager mGame;
    private int mCurrentPlayer=PLAYER_1;

    private GameManager(int numBoards,boolean twoPlayer)
    {

    }

    public static void createGame(int numBoards,boolean twoPlayer)
    {
        mGame = null;
        mGame=new GameManager(numBoards,twoPlayer);
    }

    public static synchronized GameManager Instance()
    {
        return mGame;
    }

    public int getCurrentPlayer() {
        return mCurrentPlayer;
    }


}

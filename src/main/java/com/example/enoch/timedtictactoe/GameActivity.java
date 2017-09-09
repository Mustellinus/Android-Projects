package com.example.enoch.timedtictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Enoch on 6/21/2017.
 */

public class GameActivity extends AppCompatActivity {

    public static final int PLAYER_1=0;
    public static final int PLAYER_2=1;

    private TextView mPlayer1score;
    private TextView mPlayer2score;
    private TextView mPlayerTurn;
    private TextView mTimeLeft;
    private CountDownTimer mTimer;

    private int mNumGrids;
    private int mGridsNoVictory;
    private int mGridsActive;
    private int mPlayer1points=0;
    private int mPlayer2points=0;
    private int mCurrentPlayer=PLAYER_1;
    private int mTimeInterval;
    private ArrayList<TicTacToeFragment> mGames;
    private boolean mTwoPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlayer1score=(TextView)findViewById(R.id.player1_score);
        mPlayer2score=(TextView)findViewById(R.id.player2_score);
        mPlayerTurn=(TextView)findViewById(R.id.turn);
        mTimeLeft=(TextView)findViewById(R.id.timeLeft);

        Intent intent=getIntent();
        int numPlayers=intent.getIntExtra("Players",MainActivity.ONE_PLAYER);
        mNumGrids=intent.getIntExtra("Grids",1);
        mTimeInterval=intent.getIntExtra("Time",10);
        mTimeLeft.setText(Integer.toString(mTimeInterval));
        mGridsNoVictory=mNumGrids;
        mGridsActive=mNumGrids;
        mTwoPlayer=false;
        if(numPlayers==MainActivity.TWO_PLAYER)
            mTwoPlayer=true;

        mGames=new ArrayList<>();

        RecyclerView rView = (RecyclerView) findViewById(R.id.recycler_view);
        rView.setLayoutManager(new GridLayoutManager(this,3));
        GridAdapter adapter=new GridAdapter(this);
        rView.setAdapter(adapter);

        mTimer=new CountDownTimer(mTimeInterval*1000,1000) {
            @Override
            public void onTick(long l) {
                mTimeLeft.setText(Long.toString(l/1000));
            }

            @Override
            public void onFinish() {
                for(int i=0;i<mGames.size();i++)
                {
                    if(mGames.get(i).endGame()==false)
                        mGames.get(i).resetTurn();
                }
                switchPlayers();
            }
        };
        mTimer.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.newOption:
            {
                reset();
                return true;
            }
            case R.id.settingsOption:
            {
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
            default:
                return false;
        }
    }

    public int getCurrentPlayer() {
        return mCurrentPlayer;
    }

    public void updateTurn()
    {
        if(mGridsActive==1 && mGridsNoVictory>0)
        {
            mTimer.cancel();
            for(int i=0;i<mGames.size();i++)
            {
                if(mGames.get(i).endGame()==false)
                    mGames.get(i).resetTurn();
            }
            mGridsActive=mGridsNoVictory;
            switchPlayers();
        }
        else
            mGridsActive--;
    }

    public void addVictory(int result)
    {
        switch(result)
        {
            case TicTacToeGrid.X:
            {
                mPlayer1points++;
                mPlayer1score.setText(Integer.toString(mPlayer1points));
                break;
            }
            case TicTacToeGrid.O:
            {
                mPlayer2points++;
                mPlayer2score.setText(Integer.toString(mPlayer2points));
                break;
            }
            case TicTacToeGrid.STALEMATE:
            {
                break;
            }
        }
        mGridsNoVictory--;
        if(mGridsNoVictory==0)
        {
            mTimer.cancel();
            mPlayerTurn.setText("Game Over");
        }
    }

    public void switchPlayers()
    {
        if(mCurrentPlayer==PLAYER_1)
        {
            mCurrentPlayer = PLAYER_2;
            mPlayerTurn.setText(R.string.player2turn);
            if(mTwoPlayer==false)
            {
                for(int i=0;i<mGames.size();i++)
                {
                    if (!mGames.get(i).endGame())
                        mGames.get(i).AIturn();
                }
            }
            else
            {
                mTimeLeft.setText(Integer.toString(mTimeInterval));
                mTimer.start();
            }
        }
        else
        {
            mCurrentPlayer=PLAYER_1;
            mPlayerTurn.setText(R.string.player1turn);
            mTimeLeft.setText(Integer.toString(mTimeInterval));
            mTimer.start();
        }
    }

    public void reset()
    {
        mCurrentPlayer=PLAYER_1;
        mPlayerTurn.setText(R.string.player1turn);
        mPlayer1points=0;
        mPlayer2points=0;
        mPlayer1score.setText(Integer.toString(mPlayer1points));
        mPlayer2score.setText(Integer.toString(mPlayer2points));
        mGridsNoVictory=mNumGrids;
        mGridsActive=mNumGrids;
        for(int i=0;i<mGames.size();i++)
            mGames.get(i).reset();
        mTimer.start();
    }

    private class GridAdapter extends RecyclerView.Adapter<TicTacToeFragment>
    {
        private GameActivity mActivity;

        public GridAdapter(GameActivity activity)
        {
            mActivity=activity;
        }

        @Override
        public TicTacToeFragment onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_tic_tac_toe_view, parent, false);
            TicTacToeFragment frag=new TicTacToeFragment(view,mActivity);
            mGames.add(frag);
            return frag;
        }

        @Override
        public void onBindViewHolder(TicTacToeFragment holder, int position) {

        }

        @Override
        public int getItemCount() {
            return mNumGrids;
        }
    }
}

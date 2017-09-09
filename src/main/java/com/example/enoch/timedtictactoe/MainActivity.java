package com.example.enoch.timedtictactoe;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final int ONE_PLAYER=0;
    public static final int TWO_PLAYER=1;

    private TextView mNumGrids;
    private TextView mTimeInterval;
    private Button mAddGrid;
    private Button mSubtractGrid;
    private Button mAddTime;
    private Button mSubtractTime;
    private Button mPlayGame;
    private RadioGroup mNumPlayers;

    private int _grids=1;
    private int _timeInterval=10;
    private int _numPlayers;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Grids",_grids);
        outState.putInt("Time",_timeInterval);

    }

    @Override
    protected void onRestoreInstanceState(Bundle inState)
    {
        _grids=inState.getInt("Grids");
        _timeInterval=inState.getInt("Time");
        mNumGrids.setText(Integer.toString(_grids));
        mTimeInterval.setText(Integer.toString(_timeInterval));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        mNumGrids=(TextView)findViewById(R.id.numGrids);
        mTimeInterval=(TextView)findViewById(R.id.time);
        mAddGrid=(Button)findViewById(R.id.moreGrids);
        mSubtractGrid=(Button)findViewById(R.id.lessGrids);
        mAddTime=(Button)findViewById(R.id.timePlus);
        mSubtractTime=(Button)findViewById(R.id.timeMinus);
        mPlayGame=(Button)findViewById(R.id.playGame);
        mNumPlayers=(RadioGroup) findViewById(R.id.choosePlayers);
        _numPlayers=mNumPlayers.indexOfChild(findViewById(mNumPlayers.getCheckedRadioButtonId()));
        mAddGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(_grids<9)
                {
                    _grids++;
                    mNumGrids.setText(Integer.toString(_grids));
                }
            }
        });
        mSubtractGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(_grids >1)
                {
                    _grids--;
                    mNumGrids.setText(Integer.toString(_grids));
                }
            }
        });
        mAddTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(_timeInterval < 30)
                {
                    _timeInterval +=5;
                    mTimeInterval.setText(Integer.toString(_timeInterval));
                }
            }
        });
        mSubtractTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(_timeInterval >5)
                {
                    _timeInterval -=5;
                    mTimeInterval.setText(Integer.toString(_timeInterval));
                }
            }
        });
        mPlayGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),GameActivity.class);
                intent.putExtra("Grids",_grids);
                intent.putExtra("Players",_numPlayers);
                intent.putExtra("Time",_timeInterval);
                startActivity(intent);
                finish();
            }
        });

        mNumPlayers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                _numPlayers=mNumPlayers.indexOfChild(findViewById(mNumPlayers.getCheckedRadioButtonId()));
            }
        });
    }
}

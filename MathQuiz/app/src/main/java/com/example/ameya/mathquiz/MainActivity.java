package com.example.ameya.mathquiz;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView mTvTimer, mTvScore, mTvQuestion, mTvResult;
    Button mBtn1, mBtn2, mBtn3, mBtn4, mStartBtn, mBtnPlayAgain;
    RelativeLayout mRlQuizPanel;
    int correctAnswerLocation, numberOfQuestions;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTvQuestion = findViewById(R.id.tv_question);
        mTvTimer = findViewById(R.id.tv_timer);
        mTvScore = findViewById(R.id.tv_score);
        mTvResult = findViewById(R.id.tv_result);
        mBtn1 = findViewById(R.id.btn_grid_1);
        mBtn2 = findViewById(R.id.btn_grid_2);
        mBtn3 = findViewById(R.id.btn_grid_3);
        mBtn4 = findViewById(R.id.btn_grid_4);
        mStartBtn = findViewById(R.id.btn_start_quiz);
        mBtnPlayAgain = findViewById(R.id.btn_play_again);
        mRlQuizPanel = findViewById(R.id.rl_quiz);
        mBtn1.setOnClickListener(this);
        mBtn2.setOnClickListener(this);
        mBtn3.setOnClickListener(this);
        mBtn4.setOnClickListener(this);
        mBtnPlayAgain.setOnClickListener(this);
        mStartBtn.setOnClickListener(this);
    }


    private void generateQuestion() {
        Random random = new Random();
        int a = random.nextInt(21);
        int b = random.nextInt(21);
        ArrayList<Integer> answers = new ArrayList<>();
        answers.clear();
        enableAnswerButtons();


        mTvQuestion.setText(Integer.toString(a) + " + " + Integer.toString(b));
        correctAnswerLocation = random.nextInt(4);
        for (int i = 0; i < 4; i++) {
            if (i == correctAnswerLocation) {
                answers.add(a + b);
            } else {
                int wrongAnswer = random.nextInt(41);
                while (wrongAnswer == a + b) {
                    wrongAnswer = random.nextInt(41);
                }
                answers.add(wrongAnswer);
            }
        }
        mBtn1.setText(Integer.toString(answers.get(0)));
        mBtn2.setText(Integer.toString(answers.get(1)));
        mBtn3.setText(Integer.toString(answers.get(2)));
        mBtn4.setText(Integer.toString(answers.get(3)));
    }

    private void enableAnswerButtons() {
        mBtn1.setEnabled(true);
        mBtn2.setEnabled(true);
        mBtn3.setEnabled(true);
        mBtn4.setEnabled(true);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_play_again) {
            playAgain();
        } else if (v.getId() == R.id.btn_start_quiz) {
            mStartBtn.setVisibility(View.GONE);
            mRlQuizPanel.setVisibility(View.VISIBLE);
            playAgain();
        } else if (v.getTag().toString().equals(Integer.toString(correctAnswerLocation))) {
            mTvResult.setText(R.string.correct);
            numberOfQuestions++;
            score++;
        } else {
            mTvResult.setText(R.string.wrong);
            numberOfQuestions++;
        }
        mTvScore.setText(Integer.toString(score) + " / " + Integer.toString(numberOfQuestions));
        generateQuestion();
    }

    private void playAgain() {
        mBtnPlayAgain.setVisibility(View.GONE);
        mTvResult.setText("");
        numberOfQuestions = 0;
        score = 0;
        generateQuestion();
        mTvScore.setText("0/0");
        mTvTimer.setText("30s");
        new CountDownTimer(30100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTvTimer.setText(String.valueOf(millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                mTvTimer.setText("0s");
                mTvResult.setText(Integer.toString(score) + " / " + Integer.toString(numberOfQuestions));
                mBtnPlayAgain.setVisibility(View.VISIBLE);
                disableAnswerButtons();
            }
        }.start();
    }

    private void disableAnswerButtons() {
        mBtn1.setEnabled(false);
        mBtn2.setEnabled(false);
        mBtn3.setEnabled(false);
        mBtn4.setEnabled(false);
    }
}

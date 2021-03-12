package com.mads2202.geoquize

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private val TAG = "QuizActivity"
    private lateinit var mTrueButton: Button
    private lateinit var mFalseButton: Button
    private lateinit var mNextButton: ImageButton
    private lateinit var mPreviousButton: ImageButton
    private lateinit var mQuestionTextView: TextView
    private lateinit var mScoreTextView: TextView
    private lateinit var mCheatButton: Button;
    private var map= mutableMapOf<Int,Question>()
    private var mQuestions = arrayOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_australia_wrong, false),
        Question(R.string.question_france, true),
        Question(R.string.question_germany, true),
        Question(R.string.question_italy, true),
        Question(R.string.question_italy_wrong, false),
        Question(R.string.question_poland, true),
        Question(R.string.question_poland_wrong, false)
    )
    private var currentIndex = 0;
    private val KEY_INDEX = "index"
    private var mScore = 0;
    private val KEY_SCORE = "score"
    private val REQUEST_CODE_CHEAT = 0;
    private val KEY_IS_CHEATER = "score"
    private var mIsCheater = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_INDEX, 0)
            mScore = savedInstanceState.getInt(KEY_SCORE, 0)
            mIsCheater=savedInstanceState.getBoolean(KEY_IS_CHEATER)
        }
        Log.d(TAG, "onCreate(Bundle) called")
        setContentView(R.layout.activity_main)
        mTrueButton = findViewById<Button>(R.id.true_button)
        mTrueButton.setOnClickListener {
            checkAnswer(true)
            updateScore()
            updateQuestion()

        }
        mFalseButton = findViewById(R.id.false_button)
        mFalseButton.setOnClickListener {
            checkAnswer(false)
            updateScore()
            updateQuestion()
        }

        mQuestionTextView = findViewById(R.id.textField)
        mQuestionTextView.setText(mQuestions[currentIndex].textResId)
        mQuestionTextView.setOnClickListener {
            updateQuestion()
        }
        mScoreTextView = findViewById(R.id.scoreTextField)
        mScoreTextView.text = mScore.toString()
        mNextButton = findViewById(R.id.next_button)
        mNextButton.setOnClickListener {
            updateQuestion()
        }
        mPreviousButton = findViewById(R.id.previous_button)
        mPreviousButton.setOnClickListener {
            previousQuestion()
        }
        mCheatButton = findViewById(R.id.cheat_button)
        mCheatButton.setOnClickListener {
            //start cheatActivity
            val intent = CheatActivity.newIntent(
                this@MainActivity,
                mQuestions[currentIndex].answerTrue
            )
            startActivityForResult(intent, REQUEST_CODE_CHEAT)
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK)
            return
        if (REQUEST_CODE_CHEAT == requestCode) {
            if (data == null)
                return
            mIsCheater = CheatActivity.wasAnswerShown(data)
        }
    }

    private fun updateQuestion() {
        saveCheatQuestion()
        if (currentIndex < mQuestions.size - 1) {
            currentIndex++
            mQuestionTextView.setText(mQuestions[currentIndex].textResId)

        } else {
            currentIndex = 0
            mQuestionTextView.setText(mQuestions[currentIndex].textResId)

        }

    }

    private fun previousQuestion() {
        saveCheatQuestion()
        if (currentIndex > 0) {
            currentIndex--
            mQuestionTextView.setText(mQuestions[currentIndex].textResId)
        } else {
            currentIndex = mQuestions.size - 1
            mQuestionTextView.setText(mQuestions[currentIndex].textResId)

        }
    }


    private fun updateScore() {
        mScoreTextView.text = mScore.toString()
    }

    private fun checkAnswer(userAnswer: Boolean) {
        var messageResId: Int?
        if (userAnswer == mQuestions[currentIndex].answerTrue) {
            if(mIsCheater ||(!map.isEmpty() && map.containsKey(currentIndex))){
                messageResId=R.string.cheat_toast
                mIsCheater=false
                map.clear()
            } else{
            mScore++
            messageResId = R.string.correct_toast}
        } else {
            messageResId = R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

    }
    fun saveCheatQuestion(){
        if(mIsCheater){
            map.put(currentIndex,mQuestions[currentIndex])
            mIsCheater=false
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState");
        outState.putInt(KEY_INDEX, currentIndex)
        outState.putInt(KEY_SCORE, mScore)
        outState.putBoolean(KEY_IS_CHEATER,mIsCheater)
    }
}
package com.mads2202.geoquize

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class CheatActivity : AppCompatActivity() {
    private var mAnswerIsTrue = false
    private var mIsClicked=false;
    private lateinit var mShowButton: Button
    private lateinit var mAnswerTextView: TextView
    private val ANSWER_INDEX="answer_index"
    private val IS_CLICKED_INDEX="answer_index"

    companion object {
        private const val EXTRA_ANSWER_IS_TRUE = "com.mads2202.android.geoquize.answer_is_true"
        private const val IS_ANSWER_SHOWN="cam.mads2202.geoquize.is_answer_shown"
        public fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            val intent = Intent(packageContext, CheatActivity::class.java)
            intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            return intent
        }
        public fun wasAnswerShown(result:Intent):Boolean{
            return result.getBooleanExtra(IS_ANSWER_SHOWN,false)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState!=null){
            mIsClicked=savedInstanceState.getBoolean(IS_CLICKED_INDEX)
            mAnswerIsTrue=savedInstanceState.getBoolean(ANSWER_INDEX)
           if (mIsClicked){
               setAnswerShownResult(mIsClicked)
           }
        }
        setContentView(R.layout.activity_cheat)
        mShowButton=findViewById(R.id.show_answer_button)
        mAnswerTextView=findViewById(R.id.answer_text_view)
        if(mIsClicked){
            mAnswerTextView.text = mAnswerIsTrue.toString()
        }
        else{
            mAnswerIsTrue=intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false)
        }
        mShowButton.setOnClickListener {
            mIsClicked=true
            mAnswerTextView.text = mAnswerIsTrue.toString()
            setAnswerShownResult(mIsClicked)
        }
    }
    private fun setAnswerShownResult(isShown:Boolean){
        intent=Intent()
        intent.putExtra(IS_ANSWER_SHOWN,isShown)
        setResult(RESULT_OK,intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(ANSWER_INDEX,mAnswerIsTrue)
        outState.putBoolean(IS_CLICKED_INDEX,mIsClicked)

    }

}
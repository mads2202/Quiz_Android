package com.mads2202.geoquize

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var mTrueButton: Button
    private lateinit var mFalseButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mTrueButton = findViewById<Button>(R.id.true_button)
        mTrueButton.setOnClickListener {
            val toast = Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT)
            toast.setGravity( Gravity.TOP,0,0)
            toast.show()

        }
        mFalseButton = findViewById(R.id.false_button)
        mFalseButton.setOnClickListener {
            val toast = Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.TOP,5,5)
            toast.show()
        }
    }


}
package com.example.quizapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import androidx.core.view.marginTop
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.math.log

class MainActivity : AppCompatActivity()
{

    private lateinit var logicHandler: Quiz
    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var button4: Button
    private lateinit var questionDisplay: TextView
    private lateinit var buttonGroup1: Group
    private lateinit var buttonGroup2: Group

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputStream = resources.openRawResource(R.raw.questions)
        val jsonString = inputStream.bufferedReader().use {
            it.readText()
        }

        val gson = Gson()
        val sType = object : TypeToken<List<Question>>() { }.type
        val questionList = gson.fromJson<List<Question>>(jsonString, sType)

        logicHandler = Quiz(questionList)

        wireWidgets()

        update()
    }

    private fun wireWidgets()
    {
        questionDisplay = findViewById(R.id.TextView_Main_ShowQuestion)
        button1 = findViewById(R.id.Button_Main_Choice1)
        button1.setOnClickListener { answered(0) }
        button2 = findViewById(R.id.Button_Main_Choice2)
        button2.setOnClickListener { answered(1) }
        button3 = findViewById(R.id.Button_Main_Choice3)
        button3.setOnClickListener { answered(2) }
        button4 = findViewById(R.id.Button_Main_Choice4)
        button4.setOnClickListener { answered(3) }
        buttonGroup1 = findViewById(R.id.Choices12)
        buttonGroup2 = findViewById(R.id.Choices34)
    }

    private fun update()
    {
        if (!logicHandler.quizOver())
        {
            questionDisplay.text = logicHandler.getCurrentQuestion()
            val choices = logicHandler.getCurrentChoices()
            button1.text = choices[0]
            button2.text = choices[1]
            if (choices.size == 2)
            {
                buttonGroup2.visibility = View.GONE
            }
            else
            {
                button3.text = choices[2]
                button4.text = choices[3]
                buttonGroup2.visibility = View.VISIBLE
            }
        }
        else
        {
            quizOver()
        }
    }

    private fun answered(choice: Int)
    {
        logicHandler.answerQuestion(choice)
        update()
    }

    private fun quizOver()
    {
        buttonGroup1.visibility = View.GONE
        buttonGroup2.visibility = View.GONE

        questionDisplay.text = "Quiz over, you are ${logicHandler.score}% Leonard"
    }
}
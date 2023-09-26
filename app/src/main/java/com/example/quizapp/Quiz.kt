package com.example.quizapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Quiz (data: List<Question>)
{
    var score = 0
    private var questionIndex = 0
    private val questions = data

    fun answerQuestion(answer: Int)
    {
        score += (questions[questionIndex].values[answer]).toInt()
        questionIndex++
    }

    fun getCurrentQuestion(): String
    {
        return questions[questionIndex].question
    }

    fun getCurrentChoices(): List<String>
    {
        return questions[questionIndex].choices
    }

    fun quizOver(): Boolean
    {
        if (questionIndex == questions.size)
            return true
        return false
    }


}
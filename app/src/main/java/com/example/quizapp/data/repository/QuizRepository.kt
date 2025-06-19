package com.example.quizapp.data.repository

import Question
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class QuizRepository(private val context: Context) {

    private val gson = Gson()

    fun getQuestions(): Flow<List<Question>> = flow {
        val jsonString = context.assets.open("questions.json").bufferedReader().use { it.readText() }
        val questionListType = object : TypeToken<List<Question>>() {}.type
        val questions: List<Question> = gson.fromJson(jsonString, questionListType)
        emit(questions)
    }.flowOn(Dispatchers.IO) 
}

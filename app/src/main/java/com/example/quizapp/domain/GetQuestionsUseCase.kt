package com.example.quizapp.domain

import Question
import com.example.quizapp.data.repository.QuizRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetQuestionsUseCase @Inject constructor(
    private val repository: QuizRepository
) {
    fun execute(): Flow<List<Question>> = repository.getQuestions()
}
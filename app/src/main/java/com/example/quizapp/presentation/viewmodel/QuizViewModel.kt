package com.example.quizapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.domain.GetQuestionsUseCase
import Question
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(private val getQuestionsUseCase: GetQuestionsUseCase) :
    ViewModel() {

    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    private var _currentQuestionIndex = MutableStateFlow(0)

    val currentQuestion: StateFlow<Question?> = MutableStateFlow(null) // Allow null at first

    init {
        viewModelScope.launch {
            getQuestionsUseCase.execute().collectLatest { fetchedQuestions ->
                if (fetchedQuestions.isNotEmpty()) {
                    _questions.value = fetchedQuestions
                    _currentQuestionIndex.value = 1
                    (currentQuestion as MutableStateFlow).value = fetchedQuestions[0] // Assign first question
                }
            }
        }
    }

    fun goToPrevQuestion() {
        _currentQuestionIndex.value = getPrevIndex()
        (currentQuestion as MutableStateFlow).value = _questions.value[getPrevIndex()]
    }

    fun goToNextQuestion() {
        _currentQuestionIndex.value = getNextIndex()
        (currentQuestion as MutableStateFlow).value = _questions.value[getNextIndex()]
    }

    fun getPrevIndex() : Int {
        return _currentQuestionIndex.value - 1
    }

    fun getPrevBool() : Boolean {
        return getPrevIndex() > 0
    }

    fun getPrev() : Boolean {
        return _currentQuestionIndex.value > 0
    }

    fun getNextIndex() : Int {
        return _currentQuestionIndex.value + 1
    }

    fun getNextBool() : Boolean {
        return getNextIndex() < (_questions.value.size-1)
    }

    fun getNext() : Boolean {
        return _currentQuestionIndex.value < (_questions.value.size-1)
    }
}
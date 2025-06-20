package com.example.quizapp.presentation.ui

import Question
import QuestionType
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizapp.presentation.viewmodel.QuizViewModel

private val questionUI = QuestionUI()

@Composable
fun QuizScreen(viewModel: QuizViewModel = hiltViewModel()) {
    val currentQuestion by viewModel.currentQuestion.collectAsState()

    if (currentQuestion != null) {
        QuestionContent(currentQuestion!!, viewModel)
    } else {
        CircularProgressIndicator()
    }
}

@Composable
fun QuestionContent(question: Question, viewModel: QuizViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = question.text,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        key(question.id) {
            when (question.type) {
                QuestionType.TRUE_FALSE -> questionUI.TrueFalseQuestionUI(question)
                QuestionType.SINGLE_CHOICE -> questionUI.SingleChoiceQuestionUI(question)
                QuestionType.MULTI_CHOICE -> questionUI.MultipleChoiceQuestionUI(question)
                QuestionType.TEXT -> questionUI.TextInputQuestionUI(question)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        var prev by rememberSaveable { mutableStateOf(viewModel.getPrevBool()) }
        var next by rememberSaveable { mutableStateOf(viewModel.getNextBool()) }

        Row(horizontalArrangement = Arrangement.spacedBy(25.dp), verticalAlignment = Alignment.Bottom) {
            Button(
                onClick = {
                    if (viewModel.getPrevBool())
                        viewModel.goToPrevQuestion()

                    prev = viewModel.getPrevBool()
                    next = viewModel.getNext()
                          },
                enabled = prev) { Text("Prev") }
            Button(
                onClick = {
                    if (viewModel.getNextBool())
                        viewModel.goToNextQuestion()

                    next = viewModel.getNextBool()
                    prev = viewModel.getPrev()
                             },
                enabled = next) { Text("Next") }
        }
    }
}

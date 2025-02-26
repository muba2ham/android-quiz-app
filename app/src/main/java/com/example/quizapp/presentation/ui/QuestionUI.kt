package com.example.quizapp.presentation.ui

import Question
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

class QuestionUI {

    @Composable
    fun TrueFalseQuestionUI(question: Question) {
        var selectedAnswer by rememberSaveable { mutableStateOf<Boolean?>(null) }
        var answerState by rememberSaveable { mutableStateOf(false) }

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(
                onClick = { answerState = !answerState },
                enabled = !answerState
            ) { Text("True") }
            Button(
                onClick = { answerState = !answerState },
                enabled = !answerState
            ) { Text("False") }
        }

        if (answerState) {
            Spacer(modifier = Modifier.height(30.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(25.dp)) {
                Text(
                    text = "Answer: ",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = question.correctAnswers[0].bitToBoolean(),
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Start
                )
            }
        }
    }

    @Composable
    fun SingleChoiceQuestionUI(question: Question) {
        var selectedOption by rememberSaveable { mutableStateOf<Int?>(null) }
        var answerState by rememberSaveable { mutableStateOf(false) }

        Column {
            question.options.forEachIndexed { index, option ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = selectedOption == index,
                        onClick = { answerState = !answerState },
                        enabled = !answerState
                    )
                    Text(text = option)
                }
            }
        }

        if (answerState) {
            Spacer(modifier = Modifier.height(30.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(25.dp)) {
                Text(
                    text = "Answer: ",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = question.options[question.correctAnswers[0]],
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Start
                )
            }
        }
    }

    @Composable
    fun MultipleChoiceQuestionUI(question: Question) {
        var selectedOptions by rememberSaveable { mutableStateOf(setOf<Int>()) }
        var answerState by rememberSaveable { mutableStateOf(false) }

        Column {
            question.options.forEachIndexed { index, option ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = index in selectedOptions,
                        onCheckedChange = { isChecked ->
                            selectedOptions = if (isChecked) {
                                selectedOptions + index
                            } else {
                                selectedOptions - index
                            }
                        },
                        enabled = !answerState
                    )
                    Text(text = option)
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = { answerState = !answerState },
            enabled = !answerState
        ) { Text("Submit" ) }

        if (answerState) {
            Spacer(modifier = Modifier.height(30.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(25.dp)) {
                Text(
                    text = "Answer: ",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = question.correctAnswers.joinToString(separator = ", ") { question.options[it] },
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Start
                )
            }
        }

    }

    @Composable
    fun TextInputQuestionUI(question: Question) {
        var answer by rememberSaveable { mutableStateOf("") }

        OutlinedTextField(
            value = answer,
            onValueChange = { answer = it },
            label = { Text("Your Answer") }
        )
    }

    private fun Int.bitToBoolean(): String = when(this) {
        0 -> "false"
        1 -> "true"
        else -> throw IllegalArgumentException("$this cannot be converted, expected 0 or 1")
    }
}
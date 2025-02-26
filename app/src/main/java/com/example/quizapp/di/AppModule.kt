package com.example.quizapp.di

import android.content.Context
import com.example.quizapp.data.repository.QuizRepository
import com.example.quizapp.domain.GetQuestionsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideQuizRepository(@ApplicationContext context: Context): QuizRepository {
        return QuizRepository(context)
    }

    @Provides
    fun provideGetQuestionsUseCase(repository: QuizRepository): GetQuestionsUseCase {
        return GetQuestionsUseCase(repository)
    }
}
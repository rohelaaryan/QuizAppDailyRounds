package com.assignment.quizappdailyrounds.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.assignment.quizappdailyrounds.data.repository.QuizRepositoryImpl
import com.assignment.quizappdailyrounds.network.RetrofitClient
import com.assignment.quizappdailyrounds.presentation.splash.SplashScreen
import com.assignment.quizappdailyrounds.presentation.result.ResultScreen
import com.assignment.quizappdailyrounds.presentation.quiz.QuizScreen
import com.assignment.quizappdailyrounds.presentation.quiz.QuizViewModel
import com.assignment.quizappdailyrounds.presentation.quiz.QuizViewModelFactory

@Composable
fun QuizNavGraph() {

    val navController = rememberNavController()
    val repository = remember {
        QuizRepositoryImpl(RetrofitClient.api)
    }

    val factory = remember {
        QuizViewModelFactory(repository)
    }

    val quizViewModel: QuizViewModel = viewModel(
        factory = factory
    )

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {

        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToQuiz = {
                    navController.navigate(Screen.Quiz.route) {
                        popUpTo(Screen.Splash.route) {
                            inclusive = true
                        }
                    }
                }
            )

        }

        composable(Screen.Quiz.route) {
            QuizScreen(
                viewModel = quizViewModel,
                onQuizFinished = {
                    navController.navigate(Screen.Result.route)
                }
            )

        }

        composable(Screen.Result.route) {

            ResultScreen(
                viewModel = quizViewModel,
                onRestart = {

                    quizViewModel.restartQuiz()

                    navController.popBackStack(
                        Screen.Quiz.route,
                        inclusive = false
                    )

                }
            )

        }
    }
}
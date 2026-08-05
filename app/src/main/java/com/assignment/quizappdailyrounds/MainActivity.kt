package com.assignment.quizappdailyrounds

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import com.assignment.quizappdailyrounds.presentation.navigation.QuizNavGraph
import com.assignment.quizappdailyrounds.ui.theme.QuizAppDailyRoundsTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        WindowCompat.getInsetsController(window, window.decorView)
            ?.isAppearanceLightStatusBars = false

        setContent {
            QuizAppDailyRoundsTheme {
                QuizNavGraph()
            }
        }
    }
}
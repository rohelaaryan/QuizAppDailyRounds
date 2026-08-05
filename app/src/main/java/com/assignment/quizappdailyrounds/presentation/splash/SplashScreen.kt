package com.assignment.quizappdailyrounds.presentation.splash

import android.R.attr.showText
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.assignment.quizappdailyrounds.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToQuiz: () -> Unit
) {
    var showText by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(500)
        showText = true
        delay(1300)
        onNavigateToQuiz()
    }
    var startAnimation by remember { mutableStateOf(false) }

    val alpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(900),
        label = ""
    )

    val scale by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.75f,
        animationSpec = tween(900),
        label = ""
    )

    LaunchedEffect(Unit) {
        startAnimation = true
        delay(2000)
        onNavigateToQuiz()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(R.drawable.icon),
                contentDescription = null,
                modifier = Modifier
                    .size(130.dp)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        this.alpha = alpha
                    }
            )

            Spacer(modifier = Modifier.height(20.dp))

            AnimatedVisibility(
                visible = showText,
                enter = fadeIn() + slideInVertically { it }
            ) {
                Text(
                    text = "Quiz App",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
package com.assignment.quizappdailyrounds.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

enum class OptionState {
    NORMAL,
    CORRECT,
    WRONG
}

@Preview(showBackground = true)
@Composable
private fun OptionPreview() {

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        OptionCard(
            text = "Berlin",
            state = OptionState.NORMAL
        ) {}

        OptionCard(
            text = "Paris",
            state = OptionState.CORRECT
        ) {}

        OptionCard(
            text = "Madrid",
            state = OptionState.WRONG
        ) {}
    }
}

@Composable
fun OptionCard(
    text: String,
    state: OptionState = OptionState.NORMAL,
    enabled: Boolean = true,
    onClick: () -> Unit
) {

    val backgroundColor by animateColorAsState(
        targetValue = when (state) {
            OptionState.NORMAL -> MaterialTheme.colorScheme.surfaceVariant
            OptionState.CORRECT -> Color.Green
            OptionState.WRONG -> Color.Red
        },
        label = ""
    )

    val textColor by animateColorAsState(
        targetValue = when (state) {
            OptionState.NORMAL -> MaterialTheme.colorScheme.onSurfaceVariant
            OptionState.CORRECT -> Color.Black
            OptionState.WRONG -> Color.White
        },
        label = ""
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable(enabled = enabled) {
                onClick()
            },
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        )
    ) {

        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            textAlign = TextAlign.Center,
            color = textColor,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold
        )
    }
}
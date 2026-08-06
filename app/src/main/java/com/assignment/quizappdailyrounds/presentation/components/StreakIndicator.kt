package com.assignment.quizappdailyrounds.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
private fun StreakPreview() {
    StreakIndicator(
        streak = 2
    )

}

@Composable
fun StreakIndicator(
    streak: Int
) {

    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        repeat(5) { index ->

            val filledColor =
                if (streak >= 5)
                    Color(0xFFFFB300)
                else
                    MaterialTheme.colorScheme.primary

            val color by animateColorAsState(
                targetValue = if (index < streak)
                    filledColor
                else
                    MaterialTheme.colorScheme.outline,
                label = ""
            )

            Icon(
                imageVector = Icons.Default.LocalFireDepartment,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(28.dp)
            )
        }

    }
}
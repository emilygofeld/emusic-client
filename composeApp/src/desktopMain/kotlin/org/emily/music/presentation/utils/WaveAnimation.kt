package org.emily.music.presentation.utils

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import org.emily.project.primaryColor
import kotlin.math.sin

@Composable
fun WaveAnimation() {
    val transition = rememberInfiniteTransition()

    // animate sine wave
    val phase by transition.animateFloat(
        initialValue = 0f,
        targetValue = 2f * Math.PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height / 2f
        val wavePath = Path()

        val amplitude = 30f
        val frequency = 0.05f

        for (x in 0 until width.toInt() step 5) {
            val y = height + amplitude * sin((x * frequency) + phase)
            if (x == 0) wavePath.moveTo(x.toFloat(), y)
            else wavePath.lineTo(x.toFloat(), y)
        }

        drawPath(
            path = wavePath,
            color = primaryColor,
            style = Stroke(width = 5f)
        )
    }
}

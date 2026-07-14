package com.mmfsin.betweenmindscompose.presentation.menu

import android.R.attr.scaleY
import android.content.Context
import android.util.TypedValue
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.doctoror.particlesdrawable.ParticlesView
import com.mmfsin.betweenmindscompose.R
import com.mmfsin.betweenmindscompose.presentation.core.theme.BlueMedium
import com.mmfsin.betweenmindscompose.presentation.core.theme.OrangeMedium

@Composable
fun ParticlesBackground() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .alpha(0.5f)
    ) {

        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                ParticlesView(context).apply {
                    density = 200
                    lineColor = ContextCompat.getColor(context, R.color.waves_blue)
                    lineLength = 100.dpToPx(context)
                    lineThickness = 2.dpToPx(context)
                    particleColor = ContextCompat.getColor(context, R.color.white)
                    speedFactor = 0.25f
                }
            }
        )

        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleY = -1f
                },
            factory = { context ->
                ParticlesView(context).apply {
                    density = 150
                    lineColor = ContextCompat.getColor(context, R.color.waves_orange)
                    lineLength = 100.dpToPx(context)
                    lineThickness = 2.dpToPx(context)
                    particleColor = ContextCompat.getColor(context, R.color.white)
                    speedFactor = 0.75f
                }
            }
        )
    }
}

fun Int.dpToPx(context: Context): Float =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        context.resources.displayMetrics
    )
package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.SafetyGreen
import com.example.ui.theme.SafetyBlue

/**
 * EHS+ Brand Logo Component matching the official brand asset:
 * Shield with Eco-Green leaf on left, Safety-Blue gear + medical plus on right, and "EHS+" bold label.
 */
@Composable
fun EhsLogo(
    modifier: Modifier = Modifier,
    height: Dp = 32.dp,
    showText: Boolean = true
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Shield Emblem
        Canvas(
            modifier = Modifier.size(height)
        ) {
            val w = size.width
            val h = size.height

            // Left Shield Half (Green - Environment)
            val leftShield = Path().apply {
                moveTo(w * 0.50f, h * 0.12f)
                lineTo(w * 0.14f, h * 0.20f)
                cubicTo(w * 0.14f, h * 0.65f, w * 0.38f, h * 0.88f, w * 0.50f, h * 0.94f)
                close()
            }
            drawPath(path = leftShield, color = Color(0xFF4CAF50))

            // Right Shield Half (Blue - Health & Safety)
            val rightShield = Path().apply {
                moveTo(w * 0.50f, h * 0.12f)
                lineTo(w * 0.86f, h * 0.20f)
                cubicTo(w * 0.86f, h * 0.65f, w * 0.62f, h * 0.88f, w * 0.50f, h * 0.94f)
                close()
            }
            drawPath(path = rightShield, color = Color(0xFF0288D1))

            // White Leaf on Left Side
            val leafPath = Path().apply {
                moveTo(w * 0.24f, h * 0.60f)
                cubicTo(w * 0.22f, h * 0.38f, w * 0.44f, h * 0.32f, w * 0.46f, h * 0.32f)
                cubicTo(w * 0.46f, h * 0.52f, w * 0.38f, h * 0.68f, w * 0.24f, h * 0.60f)
                close()
            }
            drawPath(path = leafPath, color = Color.White)
            drawLine(
                color = Color(0xFF4CAF50),
                start = Offset(w * 0.26f, h * 0.58f),
                end = Offset(w * 0.42f, h * 0.36f),
                strokeWidth = 2f
            )

            // White Gear & Plus on Right Side
            drawCircle(
                color = Color.White,
                radius = w * 0.10f,
                center = Offset(w * 0.64f, h * 0.62f)
            )
            drawCircle(
                color = Color(0xFF0288D1),
                radius = w * 0.05f,
                center = Offset(w * 0.64f, h * 0.62f)
            )

            // Medical Safety Plus Cross (Top right corner of shield)
            drawRect(
                color = Color(0xFF0288D1),
                topLeft = Offset(w * 0.70f, h * 0.04f),
                size = Size(w * 0.22f, h * 0.08f)
            )
            drawRect(
                color = Color(0xFF0288D1),
                topLeft = Offset(w * 0.77f, h * -0.03f),
                size = Size(w * 0.08f, h * 0.22f)
            )
            drawRect(
                color = Color.White,
                topLeft = Offset(w * 0.72f, h * 0.06f),
                size = Size(w * 0.18f, h * 0.04f)
            )
            drawRect(
                color = Color.White,
                topLeft = Offset(w * 0.79f, h * -0.01f),
                size = Size(w * 0.04f, h * 0.18f)
            )
        }

        if (showText) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "EHS+",
                fontSize = (height.value * 0.68f).sp,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onSurface,
                letterSpacing = (-0.5).sp
            )
        }
    }
}

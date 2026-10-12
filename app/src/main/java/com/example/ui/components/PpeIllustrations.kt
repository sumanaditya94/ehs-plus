package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.SafetyAmber
import com.example.ui.theme.SafetyBlue
import com.example.ui.theme.SafetyGreen
import com.example.ui.theme.SafetyOrange
import com.example.ui.theme.SafetyRed
import com.example.ui.theme.SafetyYellow

/**
 * Universal Animated PPE & Hazard Micro-Illustrations for Industrial Safety Companion.
 * Uses lightweight Compose Canvas vectors with subtle, battery-efficient offline animations.
 */

@Composable
fun PpeIllustration(
    type: String,
    modifier: Modifier = Modifier,
    size: Dp = 56.dp,
    animated: Boolean = true
) {
    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        when (type.lowercase()) {
            "helmet", "safety helmet", "hard hat", "head", "head protection" -> {
                AnimatedHelmetIllustration(size = size, animated = animated)
            }
            "goggles", "safety goggles", "eye protection", "safety glasses" -> {
                AnimatedGogglesIllustration(size = size, animated = animated)
            }
            "gloves", "safety gloves", "heat gloves", "cut gloves", "hand protection" -> {
                AnimatedGlovesIllustration(size = size, animated = animated)
            }
            "shoes", "safety shoes", "safety boots", "steel toe shoes", "foot protection" -> {
                AnimatedShoesIllustration(size = size, animated = animated)
            }
            "face shield", "shield", "welding shield" -> {
                AnimatedFaceShieldIllustration(size = size, animated = animated)
            }
            "respirator", "dust mask", "gas mask", "scba", "respiratory protection" -> {
                AnimatedRespiratorIllustration(size = size, animated = animated)
            }
            "harness", "full body harness", "safety belt", "fall arrest" -> {
                AnimatedHarnessIllustration(size = size, animated = animated)
            }
            "ear protection", "earmuffs", "ear plugs", "hearing protection" -> {
                AnimatedEarProtectionIllustration(size = size, animated = animated)
            }
            "vest", "reflective vest", "hi-vis vest", "high visibility vest" -> {
                AnimatedVestIllustration(size = size, animated = animated)
            }
            "chemical suit", "hazmat suit", "protective suit", "coverall" -> {
                AnimatedChemicalSuitIllustration(size = size, animated = animated)
            }
            "fire", "flame", "hot work" -> {
                AnimatedFireIllustration(size = size, animated = animated)
            }
            "electricity", "shock", "electrical" -> {
                AnimatedElectricIllustration(size = size, animated = animated)
            }
            "gas", "vapor", "toxic gas", "gas leak" -> {
                AnimatedGasIllustration(size = size, animated = animated)
            }
            else -> {
                // Default fallback to safety helmet
                AnimatedHelmetIllustration(size = size, animated = animated)
            }
        }
    }
}

// 1. SAFETY HELMET (Subtle floating bounce + light gleam)
@Composable
fun AnimatedHelmetIllustration(size: Dp = 56.dp, animated: Boolean = true) {
    val infiniteTransition = rememberInfiniteTransition(label = "helmet_anim")
    val bounceY by if (animated) {
        infiniteTransition.animateFloat(
            initialValue = -1.5f,
            targetValue = 1.5f,
            animationSpec = infiniteRepeatable(
                animation = tween(1400, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "bounce"
        )
    } else {
        remember { androidx.compose.runtime.mutableFloatStateOf(0f) }
    }

    Canvas(
        modifier = Modifier
            .size(size)
            .graphicsLayer { translationY = bounceY }
    ) {
        val w = this.size.width
        val h = this.size.height

        // Helmet Dome
        val helmetPath = Path().apply {
            moveTo(w * 0.15f, h * 0.65f)
            cubicTo(w * 0.15f, h * 0.22f, w * 0.85f, h * 0.22f, w * 0.85f, h * 0.65f)
            lineTo(w * 0.95f, h * 0.72f)
            cubicTo(w * 0.95f, h * 0.78f, w * 0.90f, h * 0.80f, w * 0.80f, h * 0.78f)
            lineTo(w * 0.20f, h * 0.78f)
            cubicTo(w * 0.10f, h * 0.80f, w * 0.05f, h * 0.78f, w * 0.05f, h * 0.72f)
            close()
        }

        drawPath(
            path = helmetPath,
            brush = Brush.verticalGradient(
                colors = listOf(SafetyYellow, SafetyOrange),
                startY = h * 0.2f,
                endY = h * 0.8f
            )
        )

        // Helmet Ridge (Center reinforcement strip)
        val ridgePath = Path().apply {
            moveTo(w * 0.44f, h * 0.22f)
            lineTo(w * 0.56f, h * 0.22f)
            lineTo(w * 0.54f, h * 0.70f)
            lineTo(w * 0.46f, h * 0.70f)
            close()
        }
        drawPath(path = ridgePath, color = SafetyOrange.copy(alpha = 0.85f))

        // Gloss highlight reflection
        val glossPath = Path().apply {
            moveTo(w * 0.25f, h * 0.38f)
            cubicTo(w * 0.30f, h * 0.30f, w * 0.42f, h * 0.28f, w * 0.50f, h * 0.28f)
        }
        drawPath(
            path = glossPath,
            color = Color.White.copy(alpha = 0.55f),
            style = Stroke(width = w * 0.05f, cap = StrokeCap.Round)
        )

        // Chin Strap Outline
        val strapPath = Path().apply {
            moveTo(w * 0.25f, h * 0.78f)
            cubicTo(w * 0.32f, h * 0.94f, w * 0.68f, h * 0.94f, w * 0.75f, h * 0.78f)
        }
        drawPath(
            path = strapPath,
            color = Color(0xFF334155),
            style = Stroke(width = w * 0.04f, cap = StrokeCap.Round)
        )
    }
}

// 2. SAFETY GOGGLES (Shimmering reflection sweep)
@Composable
fun AnimatedGogglesIllustration(size: Dp = 56.dp, animated: Boolean = true) {
    val infiniteTransition = rememberInfiniteTransition(label = "goggles_anim")
    val shineAlpha by if (animated) {
        infiniteTransition.animateFloat(
            initialValue = 0.2f,
            targetValue = 0.85f,
            animationSpec = infiniteRepeatable(
                animation = tween(1200, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "shine"
        )
    } else {
        remember { androidx.compose.runtime.mutableFloatStateOf(0.4f) }
    }

    Canvas(modifier = Modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height

        // Strap
        drawRoundRect(
            color = Color(0xFF475569),
            topLeft = Offset(w * 0.05f, h * 0.44f),
            size = Size(w * 0.9f, h * 0.12f),
            cornerRadius = CornerRadius(w * 0.06f, w * 0.06f)
        )

        // Frame Base
        drawRoundRect(
            brush = Brush.verticalGradient(listOf(SafetyBlue, Color(0xFF1E3A8A))),
            topLeft = Offset(w * 0.12f, h * 0.30f),
            size = Size(w * 0.76f, h * 0.44f),
            cornerRadius = CornerRadius(w * 0.14f, w * 0.14f)
        )

        // Left Lens
        drawRoundRect(
            brush = Brush.verticalGradient(listOf(Color(0xFF93C5FD), Color(0xFF3B82F6))),
            topLeft = Offset(w * 0.18f, h * 0.36f),
            size = Size(w * 0.28f, h * 0.32f),
            cornerRadius = CornerRadius(w * 0.08f, w * 0.08f)
        )

        // Right Lens
        drawRoundRect(
            brush = Brush.verticalGradient(listOf(Color(0xFF93C5FD), Color(0xFF3B82F6))),
            topLeft = Offset(w * 0.54f, h * 0.36f),
            size = Size(w * 0.28f, h * 0.32f),
            cornerRadius = CornerRadius(w * 0.08f, w * 0.08f)
        )

        // Animated Specular Shine
        drawLine(
            color = Color.White.copy(alpha = shineAlpha),
            start = Offset(w * 0.24f, h * 0.40f),
            end = Offset(w * 0.34f, h * 0.60f),
            strokeWidth = w * 0.04f,
            cap = StrokeCap.Round
        )
        drawLine(
            color = Color.White.copy(alpha = shineAlpha),
            start = Offset(w * 0.60f, h * 0.40f),
            end = Offset(w * 0.70f, h * 0.60f),
            strokeWidth = w * 0.04f,
            cap = StrokeCap.Round
        )
    }
}

// 3. SAFETY GLOVES (Subtle flexing grip movement)
@Composable
fun AnimatedGlovesIllustration(size: Dp = 56.dp, animated: Boolean = true) {
    val infiniteTransition = rememberInfiniteTransition(label = "gloves_anim")
    val flexScale by if (animated) {
        infiniteTransition.animateFloat(
            initialValue = 0.96f,
            targetValue = 1.04f,
            animationSpec = infiniteRepeatable(
                animation = tween(1500, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "flex"
        )
    } else {
        remember { androidx.compose.runtime.mutableFloatStateOf(1.0f) }
    }

    Canvas(
        modifier = Modifier
            .size(size)
            .graphicsLayer {
                scaleX = flexScale
                scaleY = flexScale
            }
    ) {
        val w = this.size.width
        val h = this.size.height

        // Left Glove Palm
        val leftGlove = Path().apply {
            moveTo(w * 0.15f, h * 0.85f)
            lineTo(w * 0.15f, h * 0.50f)
            // Fingers
            lineTo(w * 0.18f, h * 0.25f)
            lineTo(w * 0.26f, h * 0.22f)
            lineTo(w * 0.32f, h * 0.25f)
            lineTo(w * 0.38f, h * 0.22f)
            lineTo(w * 0.44f, h * 0.30f)
            // Thumb
            lineTo(w * 0.46f, h * 0.48f)
            lineTo(w * 0.42f, h * 0.85f)
            close()
        }

        drawPath(
            path = leftGlove,
            brush = Brush.verticalGradient(listOf(SafetyOrange, Color(0xFFC2410C)))
        )

        // Right Glove Palm
        val rightGlove = Path().apply {
            moveTo(w * 0.85f, h * 0.85f)
            lineTo(w * 0.85f, h * 0.50f)
            lineTo(w * 0.82f, h * 0.25f)
            lineTo(w * 0.74f, h * 0.22f)
            lineTo(w * 0.68f, h * 0.25f)
            lineTo(w * 0.62f, h * 0.22f)
            lineTo(w * 0.56f, h * 0.30f)
            lineTo(w * 0.54f, h * 0.48f)
            lineTo(w * 0.58f, h * 0.85f)
            close()
        }

        drawPath(
            path = rightGlove,
            brush = Brush.verticalGradient(listOf(SafetyOrange, Color(0xFFC2410C)))
        )

        // Grip rubber coating on palms
        drawCircle(color = Color(0xFF1E293B), radius = w * 0.08f, center = Offset(w * 0.28f, h * 0.56f))
        drawCircle(color = Color(0xFF1E293B), radius = w * 0.08f, center = Offset(w * 0.72f, h * 0.56f))

        // Wrist cuffs
        drawRoundRect(color = SafetyYellow, topLeft = Offset(w * 0.12f, h * 0.80f), size = Size(w * 0.32f, h * 0.10f), cornerRadius = CornerRadius(4f, 4f))
        drawRoundRect(color = SafetyYellow, topLeft = Offset(w * 0.56f, h * 0.80f), size = Size(w * 0.32f, h * 0.10f), cornerRadius = CornerRadius(4f, 4f))
    }
}

// 4. SAFETY SHOES (Subtle walking step / spring motion + steel cap highlight)
@Composable
fun AnimatedShoesIllustration(size: Dp = 56.dp, animated: Boolean = true) {
    val infiniteTransition = rememberInfiniteTransition(label = "shoes_anim")
    val stepRotation by if (animated) {
        infiniteTransition.animateFloat(
            initialValue = -3f,
            targetValue = 3f,
            animationSpec = infiniteRepeatable(
                animation = tween(1200, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "step"
        )
    } else {
        remember { androidx.compose.runtime.mutableFloatStateOf(0f) }
    }

    Canvas(
        modifier = Modifier
            .size(size)
            .graphicsLayer { rotationZ = stepRotation }
    ) {
        val w = this.size.width
        val h = this.size.height

        // Boot Body Path
        val bootPath = Path().apply {
            moveTo(w * 0.20f, h * 0.25f)
            lineTo(w * 0.44f, h * 0.25f)
            lineTo(w * 0.44f, h * 0.52f)
            lineTo(w * 0.75f, h * 0.52f)
            cubicTo(w * 0.92f, h * 0.55f, w * 0.95f, h * 0.78f, w * 0.85f, h * 0.80f)
            lineTo(w * 0.15f, h * 0.80f)
            lineTo(w * 0.15f, h * 0.45f)
            close()
        }

        drawPath(
            path = bootPath,
            brush = Brush.verticalGradient(listOf(Color(0xFF334155), Color(0xFF0F172A)))
        )

        // Steel Toe Cap Highlight
        val toeCapPath = Path().apply {
            moveTo(w * 0.65f, h * 0.52f)
            lineTo(w * 0.75f, h * 0.52f)
            cubicTo(w * 0.92f, h * 0.55f, w * 0.95f, h * 0.78f, w * 0.85f, h * 0.80f)
            lineTo(w * 0.65f, h * 0.80f)
            close()
        }
        drawPath(path = toeCapPath, color = SafetyOrange.copy(alpha = 0.85f))

        // Puncture resistant sole with treads
        drawRoundRect(
            color = SafetyYellow,
            topLeft = Offset(w * 0.10f, h * 0.80f),
            size = Size(w * 0.80f, h * 0.12f),
            cornerRadius = CornerRadius(4f, 4f)
        )

        // Laces
        drawLine(color = SafetyYellow, start = Offset(w * 0.32f, h * 0.35f), end = Offset(w * 0.42f, h * 0.35f), strokeWidth = 3f)
        drawLine(color = SafetyYellow, start = Offset(w * 0.32f, h * 0.43f), end = Offset(w * 0.42f, h * 0.43f), strokeWidth = 3f)
    }
}

// 5. FACE SHIELD (Protective clear visor with sweep shine)
@Composable
fun AnimatedFaceShieldIllustration(size: Dp = 56.dp, animated: Boolean = true) {
    val infiniteTransition = rememberInfiniteTransition(label = "shield_anim")
    val sweepAlpha by if (animated) {
        infiniteTransition.animateFloat(
            initialValue = 0.3f,
            targetValue = 0.9f,
            animationSpec = infiniteRepeatable(
                animation = tween(1300, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "sweep"
        )
    } else {
        remember { androidx.compose.runtime.mutableFloatStateOf(0.5f) }
    }

    Canvas(modifier = Modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height

        // Headgear Brow Guard
        drawRoundRect(
            brush = Brush.verticalGradient(listOf(SafetyOrange, Color(0xFFC2410C))),
            topLeft = Offset(w * 0.15f, h * 0.16f),
            size = Size(w * 0.70f, h * 0.18f),
            cornerRadius = CornerRadius(w * 0.08f, w * 0.08f)
        )

        // Clear Polycarbonate Visor
        val visorPath = Path().apply {
            moveTo(w * 0.20f, h * 0.30f)
            lineTo(w * 0.80f, h * 0.30f)
            lineTo(w * 0.76f, h * 0.78f)
            cubicTo(w * 0.70f, h * 0.90f, w * 0.30f, h * 0.90f, w * 0.24f, h * 0.78f)
            close()
        }

        drawPath(
            path = visorPath,
            brush = Brush.verticalGradient(listOf(Color(0xFFBAE6FD).copy(alpha = 0.6f), Color(0xFF38BDF8).copy(alpha = 0.4f)))
        )

        // Visor Border
        drawPath(
            path = visorPath,
            color = SafetyBlue,
            style = Stroke(width = w * 0.03f, join = StrokeJoin.Round)
        )

        // Reflection Line
        drawLine(
            color = Color.White.copy(alpha = sweepAlpha),
            start = Offset(w * 0.32f, h * 0.38f),
            end = Offset(w * 0.42f, h * 0.76f),
            strokeWidth = w * 0.04f,
            cap = StrokeCap.Round
        )
    }
}

// 6. RESPIRATOR (Gentle breathing pulse with twin cartridges)
@Composable
fun AnimatedRespiratorIllustration(size: Dp = 56.dp, animated: Boolean = true) {
    val infiniteTransition = rememberInfiniteTransition(label = "respirator_anim")
    val breathScale by if (animated) {
        infiniteTransition.animateFloat(
            initialValue = 0.94f,
            targetValue = 1.05f,
            animationSpec = infiniteRepeatable(
                animation = tween(1800, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "breath"
        )
    } else {
        remember { androidx.compose.runtime.mutableFloatStateOf(1f) }
    }

    Canvas(
        modifier = Modifier
            .size(size)
            .graphicsLayer {
                scaleX = breathScale
                scaleY = breathScale
            }
    ) {
        val w = this.size.width
        val h = this.size.height

        // Straps
        drawLine(color = Color(0xFF64748B), start = Offset(w * 0.10f, h * 0.25f), end = Offset(w * 0.35f, h * 0.45f), strokeWidth = 3f)
        drawLine(color = Color(0xFF64748B), start = Offset(w * 0.90f, h * 0.25f), end = Offset(w * 0.65f, h * 0.45f), strokeWidth = 3f)
        drawLine(color = Color(0xFF64748B), start = Offset(w * 0.10f, h * 0.75f), end = Offset(w * 0.35f, h * 0.65f), strokeWidth = 3f)
        drawLine(color = Color(0xFF64748B), start = Offset(w * 0.90f, h * 0.75f), end = Offset(w * 0.65f, h * 0.65f), strokeWidth = 3f)

        // Center Facepiece
        val facePiece = Path().apply {
            moveTo(w * 0.50f, h * 0.22f)
            lineTo(w * 0.70f, h * 0.48f)
            lineTo(w * 0.64f, h * 0.82f)
            lineTo(w * 0.36f, h * 0.82f)
            lineTo(w * 0.30f, h * 0.48f)
            close()
        }

        drawPath(
            path = facePiece,
            brush = Brush.verticalGradient(listOf(Color(0xFF3B82F6), Color(0xFF1E3A8A)))
        )

        // Left Filter Cartridge
        drawCircle(
            brush = Brush.radialGradient(listOf(SafetyYellow, SafetyOrange)),
            radius = w * 0.14f,
            center = Offset(w * 0.26f, h * 0.62f)
        )
        drawCircle(
            color = Color(0xFF0F172A),
            radius = w * 0.05f,
            center = Offset(w * 0.26f, h * 0.62f)
        )

        // Right Filter Cartridge
        drawCircle(
            brush = Brush.radialGradient(listOf(SafetyYellow, SafetyOrange)),
            radius = w * 0.14f,
            center = Offset(w * 0.74f, h * 0.62f)
        )
        drawCircle(
            color = Color(0xFF0F172A),
            radius = w * 0.05f,
            center = Offset(w * 0.74f, h * 0.62f)
        )

        // Exhalation Valve (Center)
        drawCircle(
            color = Color(0xFFE2E8F0),
            radius = w * 0.08f,
            center = Offset(w * 0.50f, h * 0.65f)
        )
    }
}

// 7. FULL BODY HARNESS (Subtle hanging pendulum swing)
@Composable
fun AnimatedHarnessIllustration(size: Dp = 56.dp, animated: Boolean = true) {
    val infiniteTransition = rememberInfiniteTransition(label = "harness_anim")
    val swingRot by if (animated) {
        infiniteTransition.animateFloat(
            initialValue = -2.5f,
            targetValue = 2.5f,
            animationSpec = infiniteRepeatable(
                animation = tween(1600, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "swing"
        )
    } else {
        remember { androidx.compose.runtime.mutableFloatStateOf(0f) }
    }

    Canvas(
        modifier = Modifier
            .size(size)
            .graphicsLayer { rotationZ = swingRot }
    ) {
        val w = this.size.width
        val h = this.size.height

        // Dorsal D-Ring
        drawCircle(
            color = Color(0xFF94A3B8),
            radius = w * 0.09f,
            center = Offset(w * 0.50f, h * 0.20f),
            style = Stroke(width = w * 0.035f)
        )

        // Shoulder Straps
        drawLine(color = SafetyOrange, start = Offset(w * 0.50f, h * 0.20f), end = Offset(w * 0.28f, h * 0.60f), strokeWidth = w * 0.06f, cap = StrokeCap.Round)
        drawLine(color = SafetyOrange, start = Offset(w * 0.50f, h * 0.20f), end = Offset(w * 0.72f, h * 0.60f), strokeWidth = w * 0.06f, cap = StrokeCap.Round)

        // Chest Strap & Buckle
        drawLine(color = SafetyYellow, start = Offset(w * 0.34f, h * 0.44f), end = Offset(w * 0.66f, h * 0.44f), strokeWidth = w * 0.05f)
        drawRect(color = Color(0xFF0F172A), topLeft = Offset(w * 0.45f, h * 0.41f), size = Size(w * 0.10f, h * 0.06f))

        // Waist Belt
        drawLine(color = SafetyGreen, start = Offset(w * 0.22f, h * 0.62f), end = Offset(w * 0.78f, h * 0.62f), strokeWidth = w * 0.06f, cap = StrokeCap.Round)

        // Leg Loops
        val leftLeg = Path().apply {
            moveTo(w * 0.28f, h * 0.62f)
            cubicTo(w * 0.20f, h * 0.90f, w * 0.45f, h * 0.90f, w * 0.44f, h * 0.64f)
        }
        drawPath(path = leftLeg, color = SafetyOrange, style = Stroke(width = w * 0.05f, cap = StrokeCap.Round))

        val rightLeg = Path().apply {
            moveTo(w * 0.56f, h * 0.64f)
            cubicTo(w * 0.55f, h * 0.90f, w * 0.80f, h * 0.90f, w * 0.72f, h * 0.62f)
        }
        drawPath(path = rightLeg, color = SafetyOrange, style = Stroke(width = w * 0.05f, cap = StrokeCap.Round))
    }
}

// 8. EAR PROTECTION (Gentle acoustic wave vibration)
@Composable
fun AnimatedEarProtectionIllustration(size: Dp = 56.dp, animated: Boolean = true) {
    val infiniteTransition = rememberInfiniteTransition(label = "ear_anim")
    val wavePulse by if (animated) {
        infiniteTransition.animateFloat(
            initialValue = 0.92f,
            targetValue = 1.06f,
            animationSpec = infiniteRepeatable(
                animation = tween(1100, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "pulse"
        )
    } else {
        remember { androidx.compose.runtime.mutableFloatStateOf(1f) }
    }

    Canvas(
        modifier = Modifier
            .size(size)
            .graphicsLayer {
                scaleX = wavePulse
                scaleY = wavePulse
            }
    ) {
        val w = this.size.width
        val h = this.size.height

        // Headband Arc
        val arcPath = Path().apply {
            moveTo(w * 0.20f, h * 0.60f)
            cubicTo(w * 0.20f, h * 0.12f, w * 0.80f, h * 0.12f, w * 0.80f, h * 0.60f)
        }
        drawPath(
            path = arcPath,
            color = Color(0xFF334155),
            style = Stroke(width = w * 0.07f, cap = StrokeCap.Round)
        )

        // Left Earcup
        drawRoundRect(
            brush = Brush.verticalGradient(listOf(SafetyRed, Color(0xFF991B1B))),
            topLeft = Offset(w * 0.10f, h * 0.44f),
            size = Size(w * 0.22f, h * 0.38f),
            cornerRadius = CornerRadius(w * 0.10f, w * 0.10f)
        )
        drawRoundRect(
            color = Color(0xFF0F172A),
            topLeft = Offset(w * 0.24f, h * 0.48f),
            size = Size(w * 0.06f, h * 0.30f),
            cornerRadius = CornerRadius(w * 0.03f, w * 0.03f)
        )

        // Right Earcup
        drawRoundRect(
            brush = Brush.verticalGradient(listOf(SafetyRed, Color(0xFF991B1B))),
            topLeft = Offset(w * 0.68f, h * 0.44f),
            size = Size(w * 0.22f, h * 0.38f),
            cornerRadius = CornerRadius(w * 0.10f, w * 0.10f)
        )
        drawRoundRect(
            color = Color(0xFF0F172A),
            topLeft = Offset(w * 0.70f, h * 0.48f),
            size = Size(w * 0.06f, h * 0.30f),
            cornerRadius = CornerRadius(w * 0.03f, w * 0.03f)
        )
    }
}

// 9. REFLECTIVE VEST (High-vis neon with shimmering reflective bands)
@Composable
fun AnimatedVestIllustration(size: Dp = 56.dp, animated: Boolean = true) {
    val infiniteTransition = rememberInfiniteTransition(label = "vest_anim")
    val stripeShine by if (animated) {
        infiniteTransition.animateFloat(
            initialValue = 0.5f,
            targetValue = 1.0f,
            animationSpec = infiniteRepeatable(
                animation = tween(1200, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "stripe"
        )
    } else {
        remember { androidx.compose.runtime.mutableFloatStateOf(0.8f) }
    }

    Canvas(modifier = Modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height

        // High-Vis Neon Vest Body
        val vestPath = Path().apply {
            moveTo(w * 0.32f, h * 0.18f)
            lineTo(w * 0.68f, h * 0.18f)
            lineTo(w * 0.86f, h * 0.36f)
            lineTo(w * 0.78f, h * 0.86f)
            lineTo(w * 0.22f, h * 0.86f)
            lineTo(w * 0.14f, h * 0.36f)
            close()
        }

        drawPath(
            path = vestPath,
            brush = Brush.verticalGradient(listOf(Color(0xFF84CC16), Color(0xFF65A30D)))
        )

        // V-Neck Cutout
        val neckPath = Path().apply {
            moveTo(w * 0.40f, h * 0.18f)
            lineTo(w * 0.50f, h * 0.45f)
            lineTo(w * 0.60f, h * 0.18f)
            close()
        }
        drawPath(path = neckPath, color = Color(0xFF0F172A))

        // Center Zipper / Velcro
        drawLine(color = Color(0xFF334155), start = Offset(w * 0.50f, h * 0.45f), end = Offset(w * 0.50f, h * 0.86f), strokeWidth = 3f)

        // Horizontal Reflective Silver Stripes
        val stripeColor = Color(0xFFE2E8F0).copy(alpha = stripeShine)
        drawRect(color = stripeColor, topLeft = Offset(w * 0.20f, h * 0.55f), size = Size(w * 0.60f, h * 0.08f))
        drawRect(color = stripeColor, topLeft = Offset(w * 0.22f, h * 0.70f), size = Size(w * 0.56f, h * 0.08f))

        // Vertical Harness Stripes
        drawRect(color = stripeColor, topLeft = Offset(w * 0.30f, h * 0.24f), size = Size(w * 0.07f, h * 0.31f))
        drawRect(color = stripeColor, topLeft = Offset(w * 0.63f, h * 0.24f), size = Size(w * 0.07f, h * 0.31f))
    }
}

// 10. CHEMICAL PROTECTIVE SUIT (Hooded suit with hazard seal)
@Composable
fun AnimatedChemicalSuitIllustration(size: Dp = 56.dp, animated: Boolean = true) {
    val infiniteTransition = rememberInfiniteTransition(label = "suit_anim")
    val pulse by if (animated) {
        infiniteTransition.animateFloat(
            initialValue = 0.97f,
            targetValue = 1.03f,
            animationSpec = infiniteRepeatable(
                animation = tween(1400, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "suit_pulse"
        )
    } else {
        remember { androidx.compose.runtime.mutableFloatStateOf(1f) }
    }

    Canvas(
        modifier = Modifier
            .size(size)
            .graphicsLayer { scaleX = pulse; scaleY = pulse }
    ) {
        val w = this.size.width
        val h = this.size.height

        // Hooded Body
        val suitPath = Path().apply {
            moveTo(w * 0.35f, h * 0.15f)
            cubicTo(w * 0.35f, h * 0.08f, w * 0.65f, h * 0.08f, w * 0.65f, h * 0.15f)
            lineTo(w * 0.85f, h * 0.35f)
            lineTo(w * 0.80f, h * 0.88f)
            lineTo(w * 0.58f, h * 0.88f)
            lineTo(w * 0.58f, h * 0.62f)
            lineTo(w * 0.42f, h * 0.62f)
            lineTo(w * 0.42f, h * 0.88f)
            lineTo(w * 0.20f, h * 0.88f)
            lineTo(w * 0.15f, h * 0.35f)
            close()
        }

        drawPath(
            path = suitPath,
            brush = Brush.verticalGradient(listOf(SafetyYellow, Color(0xFFCA8A04)))
        )

        // Sealed Face Visor Aperture
        drawOval(
            color = Color(0xFF0F172A),
            topLeft = Offset(w * 0.40f, h * 0.18f),
            size = Size(w * 0.20f, h * 0.18f)
        )
        drawOval(
            color = Color(0xFF38BDF8),
            topLeft = Offset(w * 0.43f, h * 0.21f),
            size = Size(w * 0.14f, h * 0.12f)
        )

        // Hazardous Substance Bio/Chemical Badge
        drawCircle(color = SafetyOrange, radius = w * 0.06f, center = Offset(w * 0.35f, h * 0.45f))
    }
}

// 11. FIRE FLAME (Gentle flickering flame)
@Composable
fun AnimatedFireIllustration(size: Dp = 56.dp, animated: Boolean = true) {
    val infiniteTransition = rememberInfiniteTransition(label = "fire_anim")
    val flickerY by if (animated) {
        infiniteTransition.animateFloat(
            initialValue = 0.92f,
            targetValue = 1.08f,
            animationSpec = infiniteRepeatable(
                animation = tween(600, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "flicker"
        )
    } else {
        remember { androidx.compose.runtime.mutableFloatStateOf(1f) }
    }

    Canvas(
        modifier = Modifier
            .size(size)
            .graphicsLayer { scaleY = flickerY }
    ) {
        val w = this.size.width
        val h = this.size.height

        // Outer Red Flame
        val outerFlame = Path().apply {
            moveTo(w * 0.50f, h * 0.10f)
            cubicTo(w * 0.75f, h * 0.30f, w * 0.90f, h * 0.55f, w * 0.85f, h * 0.78f)
            cubicTo(w * 0.80f, h * 0.95f, w * 0.20f, h * 0.95f, w * 0.15f, h * 0.78f)
            cubicTo(w * 0.10f, h * 0.55f, w * 0.35f, h * 0.30f, w * 0.50f, h * 0.10f)
            close()
        }
        drawPath(path = outerFlame, brush = Brush.verticalGradient(listOf(SafetyRed, SafetyOrange)))

        // Inner Yellow Core
        val innerFlame = Path().apply {
            moveTo(w * 0.50f, h * 0.38f)
            cubicTo(w * 0.65f, h * 0.50f, w * 0.75f, h * 0.68f, w * 0.70f, h * 0.84f)
            cubicTo(w * 0.65f, h * 0.94f, w * 0.35f, h * 0.94f, w * 0.30f, h * 0.84f)
            cubicTo(w * 0.25f, h * 0.68f, w * 0.40f, h * 0.50f, w * 0.50f, h * 0.38f)
            close()
        }
        drawPath(path = innerFlame, brush = Brush.verticalGradient(listOf(SafetyYellow, Color.White)))
    }
}

// 12. ELECTRICITY (Shock pulse)
@Composable
fun AnimatedElectricIllustration(size: Dp = 56.dp, animated: Boolean = true) {
    val infiniteTransition = rememberInfiniteTransition(label = "elec_anim")
    val pulseAlpha by if (animated) {
        infiniteTransition.animateFloat(
            initialValue = 0.5f,
            targetValue = 1.0f,
            animationSpec = infiniteRepeatable(
                animation = tween(400, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "elec_pulse"
        )
    } else {
        remember { androidx.compose.runtime.mutableFloatStateOf(0.9f) }
    }

    Canvas(modifier = Modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height

        val boltPath = Path().apply {
            moveTo(w * 0.55f, h * 0.10f)
            lineTo(w * 0.25f, h * 0.52f)
            lineTo(w * 0.52f, h * 0.52f)
            lineTo(w * 0.42f, h * 0.90f)
            lineTo(w * 0.78f, h * 0.42f)
            lineTo(w * 0.50f, h * 0.42f)
            close()
        }

        drawPath(
            path = boltPath,
            brush = Brush.verticalGradient(listOf(SafetyYellow.copy(alpha = pulseAlpha), SafetyOrange))
        )
    }
}

// 13. GAS VAPOR (Rising cloud)
@Composable
fun AnimatedGasIllustration(size: Dp = 56.dp, animated: Boolean = true) {
    val infiniteTransition = rememberInfiniteTransition(label = "gas_anim")
    val driftY by if (animated) {
        infiniteTransition.animateFloat(
            initialValue = 1.5f,
            targetValue = -1.5f,
            animationSpec = infiniteRepeatable(
                animation = tween(1500, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "drift"
        )
    } else {
        remember { androidx.compose.runtime.mutableFloatStateOf(0f) }
    }

    Canvas(
        modifier = Modifier
            .size(size)
            .graphicsLayer { translationY = driftY }
    ) {
        val w = this.size.width
        val h = this.size.height

        drawCircle(color = SafetyGreen.copy(alpha = 0.5f), radius = w * 0.22f, center = Offset(w * 0.36f, h * 0.60f))
        drawCircle(color = SafetyGreen.copy(alpha = 0.6f), radius = w * 0.26f, center = Offset(w * 0.62f, h * 0.54f))
        drawCircle(color = SafetyYellow.copy(alpha = 0.55f), radius = w * 0.20f, center = Offset(w * 0.48f, h * 0.34f))
    }
}

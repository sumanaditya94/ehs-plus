package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.SafetyAmber
import com.example.ui.theme.SafetyBlue
import com.example.ui.theme.SafetyGreen
import com.example.ui.theme.SafetyOrange
import com.example.ui.theme.SafetyRed
import com.example.ui.theme.SafetyYellow
import com.example.ui.viewmodel.SafetyViewModel

@Composable
fun SafetyLimitsScreen(
    viewModel: SafetyViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val limits = viewModel.allSafetyLimits

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onBackground)
            }
            Column {
                Text(
                    text = "CRITICAL INDUSTRIAL THRESHOLDS",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = SafetyOrange
                )
                Text(
                    text = "Safety Limits & Regulatory Standards",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 8.dp, bottom = 96.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(limits) { limit ->
                val categoryColor = when (limit.category) {
                    "Confined Space" -> SafetyRed
                    "Noise" -> SafetyAmber
                    "Thermal Safety" -> SafetyOrange
                    "Rigging & Lifting" -> SafetyBlue
                    "Electrical Safety" -> SafetyYellow
                    else -> SafetyGreen
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = categoryColor.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = limit.category.uppercase(),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = categoryColor,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                )
                            }
                            Text(
                                text = limit.referenceSource,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = limit.parameter,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = SafetyGreen.copy(alpha = 0.1f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "✓ Safe Range: ${limit.safeRange}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = SafetyGreen,
                                modifier = Modifier.padding(8.dp)
                            )
                        }

                        if (limit.warningLimit.isNotBlank()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = SafetyAmber.copy(alpha = 0.1f),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "⚠ Action / Warning Level: ${limit.warningLimit}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = SafetyAmber,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = SafetyRed.copy(alpha = 0.1f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "⛔ Danger / Ceiling Threshold: ${limit.dangerThreshold}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = SafetyRed,
                                modifier = Modifier.padding(8.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Standard Notes: ${limit.notes}",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

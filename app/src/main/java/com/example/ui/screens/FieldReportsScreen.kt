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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.SafetyAmber
import com.example.ui.theme.SafetyGreen
import com.example.ui.theme.SafetyOrange
import com.example.ui.theme.SafetyRed
import com.example.ui.viewmodel.SafetyViewModel

@Composable
fun FieldReportsScreen(
    viewModel: SafetyViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val savedReports by viewModel.savedFieldReports.collectAsState()

    var reportType by remember { mutableStateOf("Near Miss") }
    var titleText by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var potentialConsequence by remember { mutableStateOf("Moderate LTI Risk") }
    var immediateAction by remember { mutableStateOf("") }
    var suggestedControl by remember { mutableStateOf("") }
    var saveSuccess by remember { mutableStateOf(false) }

    val reportTypes = listOf("Near Miss", "Unsafe Condition", "Unsafe Act", "Incident")

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
                    text = "FIELD SAFETY OBSERVATION",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = SafetyOrange
                )
                Text(
                    text = "Log Near Miss / Hazard Observation",
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
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Report Type:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SafetyOrange)
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(vertical = 4.dp)) {
                            reportTypes.forEach { type ->
                                FilterChip(
                                    selected = reportType == type,
                                    onClick = { reportType = type },
                                    label = { Text(type, fontSize = 11.sp) }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                            value = titleText,
                            onValueChange = { titleText = it },
                            label = { Text("Observation Title / Subject") },
                            placeholder = { Text("e.g. Missing safety pin on shackle") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = location,
                            onValueChange = { location = it },
                            label = { Text("Exact Location / Plant Area") },
                            placeholder = { Text("e.g. Compressor House, Skid 4B") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            label = { Text("Description of Hazard / What Happened?") },
                            placeholder = { Text("Describe the unsafe condition or near miss in detail") },
                            modifier = Modifier.fillMaxWidth(),
                            maxLines = 4
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = potentialConsequence,
                            onValueChange = { potentialConsequence = it },
                            label = { Text("Potential Consequence") },
                            placeholder = { Text("e.g. Load drop or crush injury") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = immediateAction,
                            onValueChange = { immediateAction = it },
                            label = { Text("Immediate Action Taken (To Make Area Safe)") },
                            placeholder = { Text("e.g. Barricaded area, tagged out valve") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = suggestedControl,
                            onValueChange = { suggestedControl = it },
                            label = { Text("Suggested Permanent Corrective Action") },
                            placeholder = { Text("e.g. Replace defective rigging gear") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(14.dp))
                        Button(
                            onClick = {
                                if (location.isNotBlank() && description.isNotBlank()) {
                                    val finalTitle = titleText.ifBlank { "$reportType at $location" }
                                    viewModel.saveFieldReport(
                                        type = reportType,
                                        title = finalTitle,
                                        location = location,
                                        description = description,
                                        potential = potentialConsequence,
                                        immediate = immediateAction,
                                        corrective = suggestedControl
                                    )
                                    saveSuccess = true
                                    titleText = ""
                                    location = ""
                                    description = ""
                                    immediateAction = ""
                                    suggestedControl = ""
                                }
                            },
                            enabled = location.isNotBlank() && description.isNotBlank(),
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = SafetyOrange),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Save, contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(if (saveSuccess) "Saved to Local Records!" else "Save Field Observation", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            if (savedReports.isNotEmpty()) {
                item {
                    Text(
                        text = "PAST FIELD OBSERVATIONS (${savedReports.size})",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                items(savedReports) { rep ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = SafetyOrange.copy(alpha = 0.15f)
                                ) {
                                    Text(
                                        text = rep.reportType.uppercase(),
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SafetyOrange,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }

                                IconButton(onClick = { viewModel.deleteFieldReport(rep) }, modifier = Modifier.size(24.dp)) {
                                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = SafetyRed, modifier = Modifier.size(16.dp))
                                }
                            }

                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = rep.title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
                            Text(text = "Location: ${rep.location} • ${rep.dateString}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(text = rep.description, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.padding(top = 4.dp))

                            if (rep.recommendedCorrectiveAction.isNotBlank()) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = "Corrective Action: ${rep.recommendedCorrectiveAction}", fontSize = 11.5.sp, fontWeight = FontWeight.Medium, color = SafetyGreen)
                            }
                        }
                    }
                }
            }
        }
    }
}

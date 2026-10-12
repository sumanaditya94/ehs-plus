package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Elevator
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Radio
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entities.SavedSafetyDocumentEntity
import com.example.data.model.JsaStepModel
import com.example.ui.theme.SafetyAmber
import com.example.ui.theme.SafetyBlue
import com.example.ui.theme.SafetyGreen
import com.example.ui.theme.SafetyOrange
import com.example.ui.theme.SafetyRed
import com.example.ui.theme.SafetyYellow
import com.example.ui.viewmodel.SafetyViewModel
import java.util.Locale
import kotlin.math.pow

@Composable
fun ToolsScreen(
    viewModel: SafetyViewModel,
    modifier: Modifier = Modifier,
    initialToolIndex: Int = 0
) {
    val currentLang by viewModel.currentLanguage.collectAsState()
    val isHindi = currentLang.code == "hi"

    var selectedToolIndex by remember { mutableIntStateOf(initialToolIndex) }

    val toolList = listOf(
        viewModel.getString("tab_risk_matrix"),
        viewModel.getString("tab_jsa_builder"),
        viewModel.getString("tab_hira_builder"),
        viewModel.getString("tab_gas_limits"),
        if (isHindi) "वेतन कैलकुलेटर" else "Salary Calc",
        if (isHindi) "ओटी (OT) कैलकुलेटर" else "Overtime Calc",
        if (isHindi) "हाजिरी व शिफ्ट" else "Attendance Log",
        viewModel.getString("tab_noise_calc"),
        viewModel.getString("tab_heat_stress"),
        viewModel.getString("tab_lifting_sling"),
        viewModel.getString("tab_loto_guide"),
        viewModel.getString("tab_fire_extinguisher"),
        viewModel.getString("tab_document_hub"),
        viewModel.getString("tab_saved_records")
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = viewModel.getString("tools_header_title"),
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = SafetyOrange,
                letterSpacing = 1.2.sp
            )
            Text(
                text = viewModel.getString("tools_header_subtitle"),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        // Scrollable Tool Tabs
        ScrollableTabRow(
            selectedTabIndex = selectedToolIndex,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = SafetyOrange,
            edgePadding = 16.dp
        ) {
            toolList.forEachIndexed { index, title ->
                Tab(
                    selected = selectedToolIndex == index,
                    onClick = { selectedToolIndex = index },
                    modifier = Modifier.testTag("tool_tab_$index"),
                    text = {
                        Text(
                            text = title,
                            fontSize = 12.sp,
                            fontWeight = if (selectedToolIndex == index) FontWeight.Bold else FontWeight.Medium,
                            color = if (selectedToolIndex == index) SafetyOrange else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                )
            }
        }

        // Tool Content
        when (selectedToolIndex) {
            0 -> RiskMatrixTool(viewModel, isHindi)
            1 -> JsaBuilderTool(viewModel, isHindi)
            2 -> HiraBuilderTool(viewModel, isHindi)
            3 -> AtmosphericGasTesterTool(viewModel, isHindi)
            4 -> WorkerSalaryCalculatorTool(viewModel, isHindi)
            5 -> WorkerOvertimeCalculatorTool(viewModel, isHindi)
            6 -> WorkerAttendanceLogTool(viewModel, isHindi)
            7 -> NoiseCalculatorTool(viewModel, isHindi)
            8 -> HeatStressTool(viewModel, isHindi)
            9 -> LiftingSlingTool(viewModel, isHindi)
            10 -> LotoGuideTool(viewModel, isHindi)
            11 -> FireExtinguisherTool(viewModel, isHindi)
            12 -> DocumentHubTool(viewModel, isHindi)
            13 -> SavedRecordsTool(viewModel, isHindi)
        }
    }
}

// -------------------------------------------------------------------------
// 1. 5x5 Risk Calculator Tool
// -------------------------------------------------------------------------
@Composable
fun RiskMatrixTool(viewModel: SafetyViewModel, isHindi: Boolean) {
    val taskName by viewModel.riskTaskName.collectAsState()
    val likelihood by viewModel.riskLikelihood.collectAsState()
    val severity by viewModel.riskSeverity.collectAsState()
    val score = viewModel.calculatedRiskScore
    val level = viewModel.calculatedRiskLevel
    val action = viewModel.calculatedRiskAction

    val levelColor = when (level) {
        "LOW" -> SafetyGreen
        "MEDIUM" -> SafetyYellow
        "HIGH" -> SafetyOrange
        else -> SafetyRed
    }

    var showSaveToast by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 14.dp, bottom = 96.dp),
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
                    Text(
                        text = viewModel.getString("risk_calculator_title"),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SafetyOrange
                    )
                    Text(
                        text = viewModel.getString("risk_calculator_sub"),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = taskName,
                        onValueChange = { viewModel.setRiskTaskName(it) },
                        label = { Text(viewModel.getString("risk_task_label")) },
                        placeholder = { Text(viewModel.getString("risk_task_placeholder")) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("risk_task_input"),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = SafetyOrange,
                            unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                }
            }
        }

        // Likelihood Selector (1 to 5)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "${viewModel.getString("likelihood_heading")} $likelihood / 5",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = SafetyBlue
                    )

                    val likelihoodLabels = listOf(
                        viewModel.getString("like_1"),
                        viewModel.getString("like_2"),
                        viewModel.getString("like_3"),
                        viewModel.getString("like_4"),
                        viewModel.getString("like_5")
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    likelihoodLabels.forEachIndexed { index, label ->
                        val value = index + 1
                        val isSelected = likelihood == value
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSelected) SafetyBlue.copy(alpha = 0.2f) else MaterialTheme.colorScheme.background,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) SafetyBlue else MaterialTheme.colorScheme.surfaceVariant
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp)
                                .clickable { viewModel.setRiskLikelihood(value) }
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(14.dp)
                                        .clip(CircleShape)
                                        .background(if (isSelected) SafetyBlue else MaterialTheme.colorScheme.surfaceVariant)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = label,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }

        // Severity Selector (1 to 5)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "${viewModel.getString("severity_heading")} $severity / 5",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = SafetyRed
                    )

                    val severityLabels = listOf(
                        viewModel.getString("sev_1"),
                        viewModel.getString("sev_2"),
                        viewModel.getString("sev_3"),
                        viewModel.getString("sev_4"),
                        viewModel.getString("sev_5")
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    severityLabels.forEachIndexed { index, label ->
                        val value = index + 1
                        val isSelected = severity == value
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSelected) SafetyRed.copy(alpha = 0.2f) else MaterialTheme.colorScheme.background,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) SafetyRed else MaterialTheme.colorScheme.surfaceVariant
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp)
                                .clickable { viewModel.setRiskSeverity(value) }
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(14.dp)
                                        .clip(CircleShape)
                                        .background(if (isSelected) SafetyRed else MaterialTheme.colorScheme.surfaceVariant)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = label,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }

        // Result Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = levelColor.copy(alpha = 0.15f)),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, levelColor)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = viewModel.getString("risk_result_badge"),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = levelColor
                            )
                            Text(
                                text = "$score / 25 ($level RISK)",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = levelColor
                        ) {
                            Text(
                                text = level,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 13.sp,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = viewModel.getString("risk_action_label"),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = action,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(top = 2.dp)
                    )

                    Spacer(modifier = Modifier.height(14.dp))
                    Button(
                        onClick = {
                            viewModel.saveCurrentRiskAssessment()
                            showSaveToast = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("save_risk_assessment_btn"),
                        colors = ButtonDefaults.buttonColors(containerColor = levelColor)
                    ) {
                        Icon(imageVector = Icons.Default.Save, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (showSaveToast) viewModel.getString("risk_saved_msg") else viewModel.getString("risk_save_btn"),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------------------
// 2. JSA Builder Tool
// -------------------------------------------------------------------------
@Composable
fun JsaBuilderTool(viewModel: SafetyViewModel, isHindi: Boolean) {
    val jobTitle by viewModel.jsaJobTitle.collectAsState()
    val location by viewModel.jsaLocation.collectAsState()
    val activity by viewModel.jsaActivity.collectAsState()
    val steps by viewModel.jsaSteps.collectAsState()
    val isGeneratingAi by viewModel.isGeneratingAiJsa.collectAsState()
    var savedSuccess by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 14.dp, bottom = 96.dp),
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = viewModel.getString("jsa_builder_title"),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = SafetyOrange
                        )

                        Button(
                            onClick = { viewModel.generateAiJsaDraft() },
                            enabled = !isGeneratingAi,
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SafetyOrange.copy(alpha = 0.2f)),
                            modifier = Modifier.testTag("ai_jsa_draft_btn")
                        ) {
                            if (isGeneratingAi) {
                                CircularProgressIndicator(modifier = Modifier.size(14.dp), strokeWidth = 2.dp, color = SafetyOrange)
                            } else {
                                Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = SafetyOrange, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (isHindi) "AI ड्राफ्ट" else "AI Draft",
                                    color = SafetyOrange,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = jobTitle,
                        onValueChange = { viewModel.setJsaJobTitle(it) },
                        label = { Text(viewModel.getString("jsa_job_title_label")) },
                        placeholder = { Text(if (isHindi) "उदा. पाइप स्पूल कटिंग व फिटिंग" else "e.g. Pipe Spool Cutting & Fitting") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = location,
                            onValueChange = { viewModel.setJsaLocation(it) },
                            label = { Text(viewModel.getString("jsa_location_label")) },
                            placeholder = { Text("Bay 3 / Yard") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = activity,
                            onValueChange = { viewModel.setJsaActivity(it) },
                            label = { Text(viewModel.getString("jsa_activity_label")) },
                            placeholder = { Text("Hot cutting") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${viewModel.getString("jsa_steps_heading")} (${steps.size})",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                TextButton(
                    onClick = { viewModel.addJsaStep() },
                    modifier = Modifier.testTag("add_jsa_step_btn")
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = SafetyOrange, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(viewModel.getString("jsa_add_step_btn"), color = SafetyOrange, fontWeight = FontWeight.Bold)
                }
            }
        }

        items(steps.size) { idx ->
            val step = steps[idx]
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
                        Text(
                            text = "${viewModel.getString("jsa_step_card_title")}${step.stepNumber}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = SafetyOrange
                        )

                        if (steps.size > 1) {
                            IconButton(onClick = { viewModel.removeJsaStep(idx) }, modifier = Modifier.size(28.dp)) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = SafetyRed, modifier = Modifier.size(18.dp))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = step.jobStep,
                        onValueChange = { viewModel.updateJsaStep(idx, step.copy(jobStep = it)) },
                        label = { Text(viewModel.getString("jsa_step_desc_label")) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = step.hazard,
                        onValueChange = { viewModel.updateJsaStep(idx, step.copy(hazard = it)) },
                        label = { Text(viewModel.getString("jsa_hazard_label")) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = step.consequence,
                        onValueChange = { viewModel.updateJsaStep(idx, step.copy(consequence = it)) },
                        label = { Text(viewModel.getString("jsa_consequence_label")) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = step.additionalControls,
                        onValueChange = { viewModel.updateJsaStep(idx, step.copy(additionalControls = it)) },
                        label = { Text(viewModel.getString("jsa_controls_label")) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = step.ppe,
                        onValueChange = { viewModel.updateJsaStep(idx, step.copy(ppe = it)) },
                        label = { Text(viewModel.getString("jsa_ppe_label")) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        item {
            Button(
                onClick = {
                    viewModel.saveCurrentJsa()
                    savedSuccess = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("save_jsa_db_btn"),
                colors = ButtonDefaults.buttonColors(containerColor = SafetyGreen),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (savedSuccess) viewModel.getString("jsa_saved_msg") else viewModel.getString("jsa_save_db_btn"),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// -------------------------------------------------------------------------
// 3. HIRA Builder Tool
// -------------------------------------------------------------------------
@Composable
fun HiraBuilderTool(viewModel: SafetyViewModel, isHindi: Boolean) {
    val act by viewModel.hiraActivity.collectAsState()
    val haz by viewModel.hiraHazard.collectAsState()
    val cause by viewModel.hiraCause.collectAsState()
    val cons by viewModel.hiraConsequence.collectAsState()
    val ext by viewModel.hiraExisting.collectAsState()
    val lik by viewModel.hiraLikelihood.collectAsState()
    val sev by viewModel.hiraSeverity.collectAsState()
    val add by viewModel.hiraAdditional.collectAsState()
    val res by viewModel.hiraResidual.collectAsState()
    var savedHiraSuccess by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 14.dp, bottom = 96.dp),
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
                    Text(
                        text = viewModel.getString("hira_title"),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SafetyOrange
                    )
                    Text(
                        text = viewModel.getString("hira_sub"),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = act,
                        onValueChange = { viewModel.setHiraActivity(it) },
                        label = { Text(viewModel.getString("hira_activity")) },
                        placeholder = { Text(if (isHindi) "उदा. रासायनिक टैंक एगिटेटर रखरखाव" else "e.g. Chemical Tank Agitator Maintenance") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = haz,
                        onValueChange = { viewModel.setHiraHazard(it) },
                        label = { Text(viewModel.getString("hira_hazard")) },
                        placeholder = { Text(if (isHindi) "उदा. जहरीली H2S गैस का संचय" else "e.g. Toxic H2S Gas Accumulation") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = cause,
                        onValueChange = { viewModel.setHiraCause(it) },
                        label = { Text(viewModel.getString("hira_cause")) },
                        placeholder = { Text(if (isHindi) "उदा. अपर्याप्त वेंटिलेशन व पर्जिंग" else "e.g. Inadequate tank flushing and forced draft") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = cons,
                        onValueChange = { viewModel.setHiraConsequence(it) },
                        label = { Text(viewModel.getString("hira_consequence")) },
                        placeholder = { Text(if (isHindi) "उदा. दम घुटना व घातक श्वसन विफलता" else "e.g. Asphyxiation and fatal respiratory arrest") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = viewModel.getString("hira_existing_controls"),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SafetyBlue
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = ext,
                        onValueChange = { viewModel.setHiraExisting(it) },
                        label = { Text(viewModel.getString("hira_existing_controls")) },
                        placeholder = { Text(if (isHindi) "उदा. प्रवेश परमिट, निरंतर गैस मॉनिटर" else "e.g. Standard entry permit, gas monitor") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "${viewModel.getString("risk_result_badge")}: $lik × $sev = ${lik * sev}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = SafetyRed
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = add,
                        onValueChange = { viewModel.setHiraAdditional(it) },
                        label = { Text(viewModel.getString("hira_additional_controls")) },
                        placeholder = { Text(if (isHindi) "उदा. निरंतर ब्लोअर पंखा, स्टैंडबाय अटेंडेंट" else "e.g. Continuous blower fan, supplied air SAR, standby attendant") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = res,
                        onValueChange = { viewModel.setHiraResidual(it) },
                        label = { Text(if (isHindi) "अवशिष्ट जोखिम रेटिंग (Residual Risk)" else "Residual Risk Rating") },
                        placeholder = { Text(if (isHindi) "निम्न (स्कोर 3)" else "e.g. Low (Residual Score 3)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(14.dp))
                    Button(
                        onClick = {
                            viewModel.saveHira()
                            savedHiraSuccess = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("save_hira_btn"),
                        colors = ButtonDefaults.buttonColors(containerColor = SafetyOrange),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Save, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (savedHiraSuccess) viewModel.getString("hira_saved_msg") else viewModel.getString("hira_save_btn"),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------------------
// 4. Atmospheric Gas Tester Tool
// -------------------------------------------------------------------------
@Composable
fun AtmosphericGasTesterTool(viewModel: SafetyViewModel, isHindi: Boolean) {
    val o2 by viewModel.gasO2.collectAsState()
    val lel by viewModel.gasLel.collectAsState()
    val h2s by viewModel.gasH2s.collectAsState()
    val co by viewModel.gasCo.collectAsState()

    val o2Val = o2.toDoubleOrNull() ?: 20.9
    val lelVal = lel.toDoubleOrNull() ?: 0.0
    val h2sVal = h2s.toDoubleOrNull() ?: 0.0
    val coVal = co.toDoubleOrNull() ?: 0.0

    val o2Safe = o2Val in 19.5..23.5
    val lelSafe = lelVal < 10.0
    val h2sSafe = h2sVal < 1.0
    val coSafe = coVal < 25.0

    val allSafe = o2Safe && lelSafe && h2sSafe && coSafe

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 14.dp, bottom = 96.dp),
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
                    Text(
                        text = viewModel.getString("gas_tester_title"),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SafetyOrange
                    )
                    Text(
                        text = viewModel.getString("gas_tester_sub"),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(
                            value = o2,
                            onValueChange = { viewModel.setGasO2(it) },
                            label = { Text("Oxygen (O2)") },
                            trailingIcon = { Text("% Vol", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant) },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = lel,
                            onValueChange = { viewModel.setGasLel(it) },
                            label = { Text("Combustible (LEL)") },
                            trailingIcon = { Text("% LEL", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant) },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(
                            value = h2s,
                            onValueChange = { viewModel.setGasH2s(it) },
                            label = { Text("H2S (Toxic)") },
                            trailingIcon = { Text("ppm", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant) },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = co,
                            onValueChange = { viewModel.setGasCo(it) },
                            label = { Text("CO (Carbon Monoxide)") },
                            trailingIcon = { Text("ppm", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant) },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }
                }
            }
        }

        // Live Evaluation Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = if (allSafe) SafetyGreen.copy(alpha = 0.15f) else SafetyRed.copy(alpha = 0.15f)),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, if (allSafe) SafetyGreen else SafetyRed)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = if (allSafe) viewModel.getString("gas_entry_permitted") else viewModel.getString("gas_entry_prohibited"),
                        fontWeight = FontWeight.Black,
                        fontSize = 15.sp,
                        color = if (allSafe) SafetyGreen else SafetyRed
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    GasEvaluationRow(name = "Oxygen (O2)", value = "$o2 %", isSafe = o2Safe, limit = viewModel.getString("gas_o2_range"))
                    GasEvaluationRow(name = "LEL Combustible", value = "$lel %", isSafe = lelSafe, limit = viewModel.getString("gas_lel_range"))
                    GasEvaluationRow(name = "Hydrogen Sulfide (H2S)", value = "$h2s ppm", isSafe = h2sSafe, limit = viewModel.getString("gas_h2s_range"))
                    GasEvaluationRow(name = "Carbon Monoxide (CO)", value = "$co ppm", isSafe = coSafe, limit = viewModel.getString("gas_co_range"))

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = if (isHindi) "महत्वपूर्ण: प्रवेश से पूर्व शीर्ष, मध्य व तल पर परीक्षण करें। निरंतर वेंटिलेशन चालू रखें।" else "IMPORTANT: Test at top, middle, and bottom of space before entry. Keep continuous ventilation active throughout entry.",
                        fontSize = 11.5.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun GasEvaluationRow(name: String, value: String, isSafe: Boolean, limit: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = name, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
            Text(text = limit, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }

        Surface(
            shape = RoundedCornerShape(6.dp),
            color = if (isSafe) SafetyGreen.copy(alpha = 0.2f) else SafetyRed.copy(alpha = 0.2f)
        ) {
            Text(
                text = value,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = if (isSafe) SafetyGreen else SafetyRed,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}

// -------------------------------------------------------------------------
// 5. Noise Exposure Calculator Tool
// -------------------------------------------------------------------------
@Composable
fun NoiseCalculatorTool(viewModel: SafetyViewModel, isHindi: Boolean) {
    val dbaStr by viewModel.noiseDba.collectAsState()
    val hoursStr by viewModel.noiseHours.collectAsState()
    val standard by viewModel.noiseStandard.collectAsState()

    val dba = dbaStr.toDoubleOrNull() ?: 88.0
    val hours = hoursStr.toDoubleOrNull() ?: 4.0

    val oshaAllowed = 8.0 / (2.0.pow((dba - 90.0) / 5.0))
    val oshaDose = (hours / oshaAllowed) * 100.0

    val nioshAllowed = 8.0 / (2.0.pow((dba - 85.0) / 3.0))
    val nioshDose = (hours / nioshAllowed) * 100.0

    val isOsha = standard.contains("OSHA")
    val dose = if (isOsha) oshaDose else nioshDose
    val maxHours = if (isOsha) oshaAllowed else nioshAllowed

    val doseColor = when {
        dose <= 50.0 -> SafetyGreen
        dose <= 100.0 -> SafetyYellow
        dose <= 200.0 -> SafetyOrange
        else -> SafetyRed
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 14.dp, bottom = 96.dp),
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
                    Text(
                        text = viewModel.getString("noise_calc_title"),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SafetyOrange
                    )
                    Text(
                        text = viewModel.getString("noise_calc_sub"),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(
                            value = dbaStr,
                            onValueChange = { viewModel.setNoiseDba(it) },
                            label = { Text(viewModel.getString("noise_level_input")) },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = hoursStr,
                            onValueChange = { viewModel.setNoiseHours(it) },
                            label = { Text(viewModel.getString("noise_duration_input")) },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = doseColor.copy(alpha = 0.15f)),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, doseColor)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = viewModel.getString("noise_dose_result"),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = doseColor
                    )
                    Text(
                        text = "${String.format(Locale.US, "%.1f", dose)}% ${if (dose > 100) "(LIMIT EXCEEDED!)" else "(Acceptable)"}",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${viewModel.getString("noise_twa_result")} ${String.format(Locale.US, "%.2f", maxHours)} hrs at $dba dBA",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = viewModel.getString("noise_protection_required"),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    val ppeText = when {
                        dba < 85.0 -> viewModel.getString("noise_safe_no_ppe")
                        dba < 90.0 -> viewModel.getString("noise_action_plugs")
                        dba < 100.0 -> viewModel.getString("noise_pel_plugs_muffs")
                        else -> viewModel.getString("noise_extreme_dual")
                    }
                    Text(
                        text = ppeText,
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = doseColor,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------------------
// 6. Heat Stress Tool
// -------------------------------------------------------------------------
@Composable
fun HeatStressTool(viewModel: SafetyViewModel, isHindi: Boolean) {
    var tempStr by remember { mutableStateOf("38") }
    var humidityStr by remember { mutableStateOf("60") }

    val temp = tempStr.toDoubleOrNull() ?: 38.0
    val rh = humidityStr.toDoubleOrNull() ?: 60.0

    // Rothfusz Heat Index Approximation
    val heatIndex = -8.78469475556 + 1.61139411 * temp + 2.33854883889 * rh +
            -0.14611605 * temp * rh + -0.012308094 * temp * temp +
            -0.0164248277778 * rh * rh + 0.002211732 * temp * temp * rh +
            0.00072546 * temp * rh * rh + -0.000003582 * temp * temp * rh * rh

    val (riskText, workRest, hydration, cardColor) = when {
        heatIndex < 27.0 -> listOf(viewModel.getString("heat_normal"), "Continuous work, standard breaks", "250 ml / hr", SafetyGreen)
        heatIndex < 32.0 -> listOf(viewModel.getString("heat_caution"), "50 min work / 10 min shade rest", "500 ml / hr (electrolytes)", SafetyYellow)
        heatIndex < 41.0 -> listOf(viewModel.getString("heat_extreme_caution"), "45 min work / 15 min shade rest", "750 ml / hr", SafetyOrange)
        heatIndex < 54.0 -> listOf(viewModel.getString("heat_danger"), "30 min work / 30 min cool rest", "1000 ml / hr (mandatory buddy check)", SafetyRed)
        else -> listOf(viewModel.getString("heat_extreme_danger"), "HALT HEAVY PHYSICAL WORK!", "1000+ ml / hr (Heat stroke risk)", SafetyRed)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 14.dp, bottom = 96.dp),
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
                    Text(
                        text = viewModel.getString("heat_tool_title"),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SafetyOrange
                    )
                    Text(
                        text = viewModel.getString("heat_tool_sub"),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(
                            value = tempStr,
                            onValueChange = { tempStr = it },
                            label = { Text(viewModel.getString("temp_input")) },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = humidityStr,
                            onValueChange = { humidityStr = it },
                            label = { Text(viewModel.getString("humidity_input")) },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = (cardColor as Color).copy(alpha = 0.15f)),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, cardColor)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = viewModel.getString("heat_index_result"),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = cardColor
                    )
                    Text(
                        text = "${String.format(Locale.US, "%.1f", heatIndex)} °C (${riskText as String})",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = viewModel.getString("heat_work_rest"), fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface)
                    Text(text = workRest as String, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = viewModel.getString("heat_hydration"), fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface)
                    Text(text = hydration as String, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

// -------------------------------------------------------------------------
// 7. Lifting & Rigging Tool
// -------------------------------------------------------------------------
@Composable
fun LiftingSlingTool(viewModel: SafetyViewModel, isHindi: Boolean) {
    var loadWeightStr by remember { mutableStateOf("5000") }
    var legsStr by remember { mutableStateOf("2") }
    var selectedAngle by remember { mutableStateOf(60.0) }

    val load = loadWeightStr.toDoubleOrNull() ?: 5000.0
    val legs = legsStr.toDoubleOrNull() ?: 2.0

    val multiplier = when (selectedAngle) {
        90.0 -> 1.0
        60.0 -> 1.155
        45.0 -> 1.414
        30.0 -> 2.0
        else -> 1.155
    }

    val tensionPerLeg = (load / legs) * multiplier

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 14.dp, bottom = 96.dp),
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
                    Text(
                        text = viewModel.getString("lifting_tool_title"),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SafetyOrange
                    )
                    Text(
                        text = viewModel.getString("lifting_tool_sub"),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(
                            value = loadWeightStr,
                            onValueChange = { loadWeightStr = it },
                            label = { Text(viewModel.getString("load_weight_input")) },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = legsStr,
                            onValueChange = { legsStr = it },
                            label = { Text(viewModel.getString("sling_legs_input")) },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = viewModel.getString("sling_angle_input"),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf(90.0, 60.0, 45.0, 30.0).forEach { angle ->
                            val isSelected = selectedAngle == angle
                            FilterChip(
                                selected = isSelected,
                                onClick = { selectedAngle = angle },
                                label = { Text("${angle.toInt()}°") },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = SafetyOrange,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = SafetyBlue.copy(alpha = 0.15f)),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, SafetyBlue)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = viewModel.getString("tension_per_leg"),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SafetyBlue
                    )
                    Text(
                        text = "${String.format(Locale.US, "%.1f", tensionPerLeg)} kg / leg",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${viewModel.getString("safety_factor_label")} (Multiplier: ${String.format(Locale.US, "%.3f", multiplier)})",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 2.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = viewModel.getString("rigging_rules_heading"),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "• ${viewModel.getString("rigging_rule_1")}\n• ${viewModel.getString("rigging_rule_2")}\n• ${viewModel.getString("rigging_rule_3")}",
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------------------
// 8. LOTO 9-Step Protocol Tool
// -------------------------------------------------------------------------
@Composable
fun LotoGuideTool(viewModel: SafetyViewModel, isHindi: Boolean) {
    var checkedSteps by remember { mutableStateOf(setOf<Int>()) }

    val steps = listOf(
        viewModel.getString("loto_step_1"),
        viewModel.getString("loto_step_2"),
        viewModel.getString("loto_step_3"),
        viewModel.getString("loto_step_4"),
        viewModel.getString("loto_step_5"),
        viewModel.getString("loto_step_6"),
        viewModel.getString("loto_step_7"),
        viewModel.getString("loto_step_8"),
        viewModel.getString("loto_step_9")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 14.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = viewModel.getString("loto_tool_title"),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SafetyOrange
                    )
                    Text(
                        text = viewModel.getString("loto_tool_sub"),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${viewModel.getString("loto_progress")} ${checkedSteps.size} / 9",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (checkedSteps.size == 9) SafetyGreen else SafetyOrange
                    )
                }
            }
        }

        items(steps.size) { idx ->
            val isDone = checkedSteps.contains(idx)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        checkedSteps = if (isDone) checkedSteps - idx else checkedSteps + idx
                    },
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = if (isDone) SafetyGreen.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, if (isDone) SafetyGreen else MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (isDone) Icons.Default.CheckCircle else Icons.Default.Lock,
                        contentDescription = null,
                        tint = if (isDone) SafetyGreen else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = steps[idx],
                        fontSize = 13.sp,
                        fontWeight = if (isDone) FontWeight.Bold else FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------------------
// 9. Fire Extinguisher Tool
// -------------------------------------------------------------------------
@Composable
fun FireExtinguisherTool(viewModel: SafetyViewModel, isHindi: Boolean) {
    var selectedClassIndex by remember { mutableIntStateOf(0) }

    val classes = listOf(
        viewModel.getString("fire_class_a"),
        viewModel.getString("fire_class_b"),
        viewModel.getString("fire_class_c"),
        viewModel.getString("fire_class_d"),
        viewModel.getString("fire_class_k")
    )

    val (extinguisher, passGuide) = when (selectedClassIndex) {
        0 -> Pair(
            if (isHindi) "जल (Water), फोम (AFFF), या ABC ड्राई केमिकल" else "Water, Foam (AFFF), or ABC Dry Chemical",
            if (isHindi) "लकड़ी, कपड़ा, रबर, कागज" else "Solid combustibles (wood, paper, textiles, rubber)."
        )
        1 -> Pair(
            if (isHindi) "फोम (AFFF), CO2, या ABC पाउडर (पानी कभी न डालें!)" else "Foam (AFFF), CO2, or ABC Dry Powder (NEVER USE WATER!)",
            if (isHindi) "पेट्रोल, डीजल, सॉल्वेंट, पेंट" else "Flammable liquids & gases (diesel, gasoline, solvents, oils)."
        )
        2 -> Pair(
            if (isHindi) "CO2 या ड्राई केमिकल (बिजली का झटका रोकने हेतु)" else "CO2 or Non-Conductive Dry Chemical (NEVER USE WATER!)",
            if (isHindi) "विद्युत पैनल, मोटर, ट्रांसफार्मर" else "Energized electrical equipment (distribution panels, motors)."
        )
        3 -> Pair(
            if (isHindi) "विशेष ड्राई पाउडर (सोडियम क्लोराइड/कॉपर पाउडर)" else "Specialized Dry Powder Agent (Sodium Chloride / Copper)",
            if (isHindi) "मैग्नीशियम, सोडियम, टाइटेनियम" else "Combustible metals (Magnesium, Titanium, Aluminum dust)."
        )
        else -> Pair(
            if (isHindi) "वेट केमिकल (पोटैशियम एसीटेट) सैपोनिफिकेशन" else "Wet Chemical (Potassium Acetate) Saponification",
            if (isHindi) "किचन फ्रायर, कुकिंग ऑयल व वसा" else "Commercial kitchen deep fat fryers & cooking oils."
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 14.dp, bottom = 96.dp),
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
                    Text(
                        text = viewModel.getString("fire_tool_title"),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SafetyOrange
                    )
                    Text(
                        text = viewModel.getString("fire_tool_sub"),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    classes.forEachIndexed { idx, fc ->
                        val isSelected = selectedClassIndex == idx
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSelected) SafetyOrange.copy(alpha = 0.2f) else MaterialTheme.colorScheme.background,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) SafetyOrange else MaterialTheme.colorScheme.surfaceVariant
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp)
                                .clickable { selectedClassIndex = idx }
                        ) {
                            Text(
                                text = fc,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = SafetyOrange.copy(alpha = 0.15f)),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, SafetyOrange)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = if (isHindi) "अनुशंसित अग्निशामक:" else "RECOMMENDED EXTINGUISHER:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SafetyOrange
                    )
                    Text(
                        text = extinguisher,
                        fontWeight = FontWeight.Black,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = passGuide,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = viewModel.getString("pass_title"),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = SafetyOrange
                    )
                    Text(
                        text = "• ${viewModel.getString("pass_p")}\n• ${viewModel.getString("pass_a")}\n• ${viewModel.getString("pass_s1")}\n• ${viewModel.getString("pass_s2")}",
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------------------
// 10. PDF / PPT / SOP Manual Hub (Data Ingestion)
// -------------------------------------------------------------------------
@Composable
fun DocumentHubTool(viewModel: SafetyViewModel, isHindi: Boolean) {
    val documents by viewModel.savedSafetyDocuments.collectAsState()

    var docTitle by remember { mutableStateOf("") }
    var docType by remember { mutableStateOf("Plant Safety SOP") }
    var rawText by remember { mutableStateOf("") }
    var isAnalyzing by remember { mutableStateOf(false) }
    var ingestSuccessMsg by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 14.dp, bottom = 96.dp),
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = viewModel.getString("doc_hub_title"),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = SafetyOrange
                            )
                            Text(
                                text = viewModel.getString("doc_hub_sub"),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = viewModel.getString("doc_upload_title"),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = SafetyBlue
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = docTitle,
                        onValueChange = { docTitle = it },
                        label = { Text(viewModel.getString("doc_title_label")) },
                        placeholder = { Text(if (isHindi) "उदा. बॉयलर हाइड्रो-टेस्टिंग SOP 2026" else "e.g. Boiler Hydro-testing SOP 2026") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("doc_title_input"),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = docType,
                        onValueChange = { docType = it },
                        label = { Text(viewModel.getString("doc_type_label")) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = rawText,
                        onValueChange = { rawText = it },
                        label = { Text(viewModel.getString("doc_content_label")) },
                        placeholder = { Text(if (isHindi) "यहाँ अपने PPT, PDF या मैनुअल से टेक्स्ट कॉपी करके पेस्ट करें..." else "Paste text extracted from your PDF manuals, PPT slides, or SOPs...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .testTag("doc_content_input"),
                        maxLines = 10
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                if (rawText.isBlank() && docTitle.isBlank()) {
                                    docTitle = "Plant High-Voltage Electrical Isolation SOP"
                                    rawText = "All 11kV busbar work requires dual lock LOTO, calibrated discharge grounding rod, arc flash suit category 4 (40 cal/cm²), and written approval from Shift Incharge. Before rack-out, verify zero current with digital clamp meter."
                                }
                                isAnalyzing = true
                                viewModel.saveParsedDocument(
                                    title = if (docTitle.isNotBlank()) docTitle else "Custom Plant SOP",
                                    documentType = docType,
                                    summary = "Extracted plant safety protocol containing procedural rules and PPE mandates.",
                                    hazards = listOf("High Voltage Arc Flash", "Stored Electrical Charge", "Unauthorized Energization"),
                                    ppe = listOf("Arc Flash Suit 40 cal/cm²", "1000V Insulated Gloves", "Dielectric Boots", "Safety Glasses"),
                                    checklist = listOf("Isolate primary breaker", "Apply personal padlock & tag", "Discharge with grounding rod", "Verify zero voltage with calibrated tester"),
                                    rules = listOf("Never work alone on high voltage systems", "Keep minimum approach boundary of 1.5m", "Notify control room before racking in/out"),
                                    rawContent = rawText
                                )
                                isAnalyzing = false
                                ingestSuccessMsg = if (isHindi) "✓ दस्तावेज़ स्थानीय डेटाबेस में सफलतापूर्वक सुरक्षित सहेजा गया!" else "✓ Document successfully ingested and saved to Local Database!"
                                docTitle = ""
                                rawText = ""
                            },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("doc_ingest_btn"),
                            colors = ButtonDefaults.buttonColors(containerColor = SafetyGreen),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Save, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(viewModel.getString("doc_analyze_btn"), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }

                        Button(
                            onClick = {
                                docTitle = "Ammonia Refrigeration Emergency SOP"
                                rawText = "In case of NH3 release > 25 ppm: sound site alarm, initiate water curtain scrubbers, evacuate upwind, don positive pressure SCBA and chemical splash suit. Do not use open electrical switches."
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SafetyBlue.copy(alpha = 0.2f))
                        ) {
                            Text(viewModel.getString("doc_quick_load_sample"), color = SafetyBlue, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    if (ingestSuccessMsg.isNotBlank()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = ingestSuccessMsg,
                            color = SafetyGreen,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        item {
            Text(
                text = "${if (isHindi) "डेटाबेस में सहेजे गए दस्तावेज़" else "Saved SOPs & Manuals"} (${documents.size})",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        items(documents) { doc ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.PictureAsPdf, contentDescription = null, tint = SafetyOrange, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(text = doc.title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
                                Text(text = "${doc.documentType} • ${doc.createdAt}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }

                        IconButton(onClick = { viewModel.deleteSafetyDocument(doc) }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = SafetyRed, modifier = Modifier.size(18.dp))
                        }
                    }

                    if (doc.summary.isNotBlank()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = doc.summary, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface)
                    }

                    if (doc.rawContent.isNotBlank()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.background,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = doc.rawContent,
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(8.dp),
                                maxLines = 4
                            )
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------------------
// 11. Saved Records Tool (Local SQLite Room DB)
// -------------------------------------------------------------------------
@Composable
fun SavedRecordsTool(viewModel: SafetyViewModel, isHindi: Boolean) {
    val jsas by viewModel.savedJsas.collectAsState()
    val hiras by viewModel.savedHiras.collectAsState()
    val risks by viewModel.savedRiskAssessments.collectAsState()
    val docs by viewModel.savedSafetyDocuments.collectAsState()

    var activeFilter by remember { mutableIntStateOf(0) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 14.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = viewModel.getString("saved_records_title"),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = SafetyOrange,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = viewModel.getString("saved_records_sub"),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf(
                            "${viewModel.getString("filter_jsa")} (${jsas.size})",
                            "${viewModel.getString("filter_hira")} (${hiras.size})",
                            "${viewModel.getString("filter_risk")} (${risks.size})",
                            "${viewModel.getString("filter_docs")} (${docs.size})"
                        ).forEachIndexed { idx, label ->
                            FilterChip(
                                selected = activeFilter == idx,
                                onClick = { activeFilter = idx },
                                label = { Text(label, fontSize = 10.5.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = SafetyOrange,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }
                }
            }
        }

        when (activeFilter) {
            0 -> {
                if (jsas.isEmpty()) {
                    item { Text(text = viewModel.getString("empty_records_msg"), fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant) }
                }
                items(jsas) { jsa ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = jsa.jobTitle, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
                                Text(text = "${jsa.location} • ${jsa.totalSteps} steps • Max Risk ${jsa.highestRiskScore}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            IconButton(onClick = { viewModel.deleteJsa(jsa) }) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = SafetyRed, modifier = Modifier.size(18.dp))
                            }
                        }
                    }
                }
            }
            1 -> {
                if (hiras.isEmpty()) {
                    item { Text(text = viewModel.getString("empty_records_msg"), fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant) }
                }
                items(hiras) { hira ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = hira.activity, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
                                Text(text = "Hazard: ${hira.hazard} • Initial Risk: ${hira.riskScore}", fontSize = 11.sp, color = SafetyOrange)
                            }
                            IconButton(onClick = { viewModel.deleteHira(hira) }) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = SafetyRed, modifier = Modifier.size(18.dp))
                            }
                        }
                    }
                }
            }
            2 -> {
                if (risks.isEmpty()) {
                    item { Text(text = viewModel.getString("empty_records_msg"), fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant) }
                }
                items(risks) { risk ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = risk.taskTitle, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
                                Text(text = "Score ${risk.riskScore} (${risk.riskLevel})", fontSize = 11.sp, color = SafetyOrange)
                            }
                            IconButton(onClick = { viewModel.deleteRiskAssessment(risk) }) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = SafetyRed, modifier = Modifier.size(18.dp))
                            }
                        }
                    }
                }
            }
            3 -> {
                if (docs.isEmpty()) {
                    item { Text(text = viewModel.getString("empty_records_msg"), fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant) }
                }
                items(docs) { doc ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = doc.title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
                                Text(text = "${doc.documentType} • ${doc.createdAt}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            IconButton(onClick = { viewModel.deleteSafetyDocument(doc) }) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = SafetyRed, modifier = Modifier.size(18.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

package com.example.ui.screens

import android.content.Intent
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Preview
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.language.AppLanguage
import com.example.data.model.ActiveChecklistItem
import com.example.data.model.CheckItemStatus
import com.example.data.model.ChecklistTemplate
import com.example.data.model.DocInspectionItem
import com.example.ui.theme.SafetyAmber
import com.example.ui.theme.SafetyBlue
import com.example.ui.theme.SafetyGreen
import com.example.ui.theme.SafetyOrange
import com.example.ui.theme.SafetyRed
import com.example.ui.theme.SafetyYellow
import com.example.ui.viewmodel.SafetyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChecklistsScreen(
    viewModel: SafetyViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentLang by viewModel.currentLanguage.collectAsState()
    val isHindi = currentLang == AppLanguage.HINDI

    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf(
        if (isHindi) "दैनिक सुरक्षा निरीक्षण (SOP)" else "Daily Inspection (SOP)",
        if (isHindi) "अन्य चेकलिस्ट" else "Standard Checklists",
        if (isHindi) "सहेजे गए रिकॉर्ड्स" else "Saved Reports"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top Header
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
                    text = if (isHindi) "दस्तावेज़ से निर्मित प्रपत्र" else "DOCUMENT TO TEMPLATE ENGINE",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = SafetyOrange
                )
                Text(
                    text = if (isHindi) "दैनिक सुरक्षा निरीक्षण व चेकलिस्ट" else "Daily Safety Inspection & Checklists",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = SafetyOrange
        ) {
            tabs.forEachIndexed { idx, title ->
                Tab(
                    selected = selectedTab == idx,
                    onClick = { selectedTab = idx },
                    modifier = Modifier.testTag("checklist_tab_$idx"),
                    text = {
                        Text(
                            text = title,
                            fontSize = 11.sp,
                            fontWeight = if (selectedTab == idx) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                )
            }
        }

        when (selectedTab) {
            0 -> DailyInspectionSopTab(viewModel = viewModel, isHindi = isHindi)
            1 -> StandardChecklistsTab(viewModel = viewModel, isHindi = isHindi)
            2 -> SavedChecklistReportsTab(viewModel = viewModel, isHindi = isHindi)
        }
    }
}

// -------------------------------------------------------------
// Tab 0: Document To Template Engine: Daily Safety Inspection Form
// -------------------------------------------------------------
@Composable
fun DailyInspectionSopTab(viewModel: SafetyViewModel, isHindi: Boolean) {
    val template = viewModel.dailyInspectionTemplate
    val area by viewModel.inspArea.collectAsState()
    val inspector by viewModel.inspInspector.collectAsState()
    val shift by viewModel.inspShift.collectAsState()
    val date by viewModel.inspDate.collectAsState()
    val statuses by viewModel.inspItemStatuses.collectAsState()
    val remarks by viewModel.inspItemRemarks.collectAsState()
    val photos by viewModel.inspItemPhotos.collectAsState()
    val generatedReport by viewModel.generatedReportText.collectAsState()

    val context = LocalContext.current
    var showPreviewDialog by remember { mutableStateOf(false) }
    var reportSaveMessage by remember { mutableStateOf(false) }

    // Group items by category (PPE, Housekeeping, Electrical, Fire Safety, Work at Height)
    val groupedItems = template.items.groupBy { if (isHindi) it.categoryHi else it.category }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 14.dp),
        contentPadding = PaddingValues(top = 10.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Document Source Badge Card
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, SafetyOrange.copy(alpha = 0.35f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(shape = RoundedCornerShape(6.dp), color = SafetyGreen.copy(alpha = 0.15f)) {
                            Text(
                                text = if (isHindi) "अधिकृत स्रोत प्रपत्र" else "Authoritative Ingested SOP",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = SafetyGreen,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                        Text(
                            text = template.version,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (isHindi) template.titleHi else template.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = if (isHindi) template.citation.displayHindi() else template.citation.displayEnglish(),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SafetyOrange
                    )
                }
            }
        }

        // General Information Card
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = viewModel.getString("gen_info_title"),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = SafetyOrange
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = date,
                            onValueChange = { viewModel.setInspDate(it) },
                            label = { Text(viewModel.getString("field_date"), fontSize = 11.sp) },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp)
                        )
                        OutlinedTextField(
                            value = shift,
                            onValueChange = { viewModel.setInspShift(it) },
                            label = { Text(viewModel.getString("field_shift"), fontSize = 11.sp) },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = area,
                            onValueChange = { viewModel.setInspArea(it) },
                            label = { Text(viewModel.getString("field_area"), fontSize = 11.sp) },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp)
                        )
                        OutlinedTextField(
                            value = inspector,
                            onValueChange = { viewModel.setInspInspector(it) },
                            label = { Text(viewModel.getString("field_inspector"), fontSize = 11.sp) },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp)
                        )
                    }
                }
            }
        }

        // Section Title: Inspection Items
        item {
            Text(
                text = viewModel.getString("inspection_items_title"),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Inspection Categories
        groupedItems.forEach { (categoryName, items) ->
            item {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = SafetyOrange.copy(alpha = 0.12f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "● $categoryName",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = SafetyOrange,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }

            items(items) { item ->
                val currentStatus = statuses[item.id] ?: CheckItemStatus.PASS
                val currentRemark = remarks[item.id] ?: ""
                val hasPhoto = photos[item.id] ?: false

                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = if (isHindi) item.questionHi else item.question,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = if (isHindi) item.criteriaHi else item.criteria,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 2.dp, bottom = 8.dp)
                        )

                        // PASS / FAIL / N/A Toggle Buttons
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                            // PASS
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (currentStatus == CheckItemStatus.PASS) SafetyGreen else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { viewModel.setInspItemStatus(item.id, CheckItemStatus.PASS) }
                            ) {
                                Box(modifier = Modifier.padding(vertical = 8.dp), contentAlignment = Alignment.Center) {
                                    Text(
                                        text = viewModel.getString("status_pass"),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (currentStatus == CheckItemStatus.PASS) Color.White else MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }

                            // FAIL
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (currentStatus == CheckItemStatus.FAIL) SafetyRed else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { viewModel.setInspItemStatus(item.id, CheckItemStatus.FAIL) }
                            ) {
                                Box(modifier = Modifier.padding(vertical = 8.dp), contentAlignment = Alignment.Center) {
                                    Text(
                                        text = viewModel.getString("status_fail"),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (currentStatus == CheckItemStatus.FAIL) Color.White else MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }

                            // N/A
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (currentStatus == CheckItemStatus.NA) SafetyYellow else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { viewModel.setInspItemStatus(item.id, CheckItemStatus.NA) }
                            ) {
                                Box(modifier = Modifier.padding(vertical = 8.dp), contentAlignment = Alignment.Center) {
                                    Text(
                                        text = viewModel.getString("status_na"),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (currentStatus == CheckItemStatus.NA) Color.Black else MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Remarks and Photo
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                            OutlinedTextField(
                                value = currentRemark,
                                onValueChange = { viewModel.setInspItemRemark(item.id, it) },
                                placeholder = { Text(viewModel.getString("field_remarks"), fontSize = 11.sp) },
                                modifier = Modifier.weight(1f),
                                singleLine = true,
                                shape = RoundedCornerShape(8.dp)
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            // Photo Attach Button
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (hasPhoto) SafetyGreen.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                border = androidx.compose.foundation.BorderStroke(1.dp, if (hasPhoto) SafetyGreen else MaterialTheme.colorScheme.surfaceVariant),
                                modifier = Modifier
                                    .clickable { viewModel.toggleInspItemPhoto(item.id) }
                                    .padding(vertical = 4.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = if (hasPhoto) Icons.Default.CheckCircle else Icons.Default.CameraAlt,
                                        contentDescription = "Photo",
                                        tint = if (hasPhoto) SafetyGreen else MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = if (hasPhoto) (if (isHindi) "फोटो ✓" else "Photo ✓") else (if (isHindi) "+ फोटो" else "+ Photo"),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (hasPhoto) SafetyGreen else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Completion Actions (Generate Report, Preview, Share PDF)
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, SafetyOrange),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = if (isHindi) "निरीक्षण समाप्ति कार्य (Completion Actions)" else "Completion & Report Actions",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // 1. Generate Report
                    Button(
                        onClick = {
                            viewModel.generateAndSaveInspectionReport()
                            reportSaveMessage = true
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SafetyGreen),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("generate_report_btn")
                    ) {
                        Icon(imageVector = Icons.Default.Checklist, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(viewModel.getString("btn_generate_report"), fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                        // 2. Preview Report
                        Button(
                            onClick = {
                                if (generatedReport == null) {
                                    viewModel.generateAndSaveInspectionReport()
                                }
                                showPreviewDialog = true
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SafetyOrange),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("preview_report_btn")
                        ) {
                            Icon(imageVector = Icons.Default.Preview, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(viewModel.getString("btn_preview_report"), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        // 3. Share Report
                        Button(
                            onClick = {
                                val textToShare = generatedReport ?: viewModel.generateAndSaveInspectionReport()
                                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_SUBJECT, "Daily Safety Inspection Report - $area")
                                    putExtra(Intent.EXTRA_TEXT, textToShare)
                                }
                                context.startActivity(Intent.createChooser(shareIntent, "Share Inspection Report"))
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SafetyBlue),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("share_report_btn")
                        ) {
                            Icon(imageVector = Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(viewModel.getString("btn_share_report"), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    if (reportSaveMessage) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "✓ " + viewModel.getString("report_generated_success"),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = SafetyGreen
                        )
                    }
                }
            }
        }
    }

    // Preview Report Dialog
    if (showPreviewDialog) {
        AlertDialog(
            onDismissRequest = { showPreviewDialog = false },
            title = {
                Text(
                    text = if (isHindi) "निरीक्षण रिपोर्ट पूर्वावलोकन" else "Inspection Report Preview",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            },
            text = {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = generatedReport ?: "No report content.",
                        fontSize = 11.sp,
                        lineHeight = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showPreviewDialog = false }) {
                    Text(if (isHindi) "बंद करें" else "Close", color = SafetyOrange)
                }
            }
        )
    }
}

// -------------------------------------------------------------
// Tab 1: Standard Checklists
// -------------------------------------------------------------
@Composable
fun StandardChecklistsTab(viewModel: SafetyViewModel, isHindi: Boolean) {
    val templates = viewModel.allChecklistTemplates
    val activeChecklist by viewModel.activeChecklist.collectAsState()
    val activeItems by viewModel.activeChecklistItems.collectAsState()
    var notes by remember { mutableStateOf("") }

    if (activeChecklist == null) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp),
            contentPadding = PaddingValues(top = 10.dp, bottom = 96.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(templates) { template ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.startChecklistSession(template) },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Surface(shape = RoundedCornerShape(4.dp), color = SafetyGreen.copy(alpha = 0.15f)) {
                                Text(
                                    text = template.category,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SafetyGreen,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                            Text(
                                text = "${template.items.size} ${if (isHindi) "बिंदु" else "Items"}",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = template.title,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    } else {
        // Active Session
        val total = activeItems.size
        val passed = activeItems.count { it.status == CheckItemStatus.PASS }
        val failed = activeItems.count { it.status == CheckItemStatus.FAIL }
        val na = activeItems.count { it.status == CheckItemStatus.NA }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp),
            contentPadding = PaddingValues(top = 8.dp, bottom = 96.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Passed: $passed", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SafetyGreen)
                        Text(text = "Failed: $failed", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SafetyRed)
                        Text(text = "N/A: $na", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(text = "Total: $total", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SafetyOrange)
                    }
                }
            }

            items(activeItems) { item ->
                ChecklistExecutionRow(
                    item = item,
                    onStatusChange = { status -> viewModel.updateChecklistItemStatus(item.id, status) },
                    onCommentChange = { c -> viewModel.updateChecklistItemComment(item.id, c) }
                )
            }

            item {
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text(if (isHindi) "निरीक्षण नोट्स / सुधारात्मक कार्रवाई" else "Overall Inspection Notes") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Button(
                    onClick = { viewModel.saveActiveChecklistReport(notes) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = SafetyGreen),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(imageVector = Icons.Default.Save, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(if (isHindi) "सहेजें व पूर्ण करें" else "Save & Complete", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// Tab 2: Saved Checklist Reports
// -------------------------------------------------------------
@Composable
fun SavedChecklistReportsTab(viewModel: SafetyViewModel, isHindi: Boolean) {
    val savedReports by viewModel.savedChecklistReports.collectAsState()

    if (savedReports.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = if (isHindi) "कोई सहेजी गई निरीक्षण रिपोर्ट नहीं है।" else "No saved inspection reports found.",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp),
            contentPadding = PaddingValues(top = 10.dp, bottom = 96.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(savedReports) { report ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = report.checklistTitle, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
                            Text(
                                text = "${report.dateString} • Passed: ${report.passedCount}/${report.totalItems} (${report.scorePercentage}%)",
                                fontSize = 11.sp,
                                color = if (report.scorePercentage >= 80) SafetyGreen else SafetyRed
                            )
                        }
                        IconButton(onClick = { viewModel.deleteChecklistReport(report) }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = SafetyRed, modifier = Modifier.size(18.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChecklistExecutionRow(
    item: ActiveChecklistItem,
    onStatusChange: (CheckItemStatus) -> Unit,
    onCommentChange: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "${item.id + 1}. ${item.text}",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { onStatusChange(CheckItemStatus.PASS) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (item.status == CheckItemStatus.PASS) SafetyGreen else MaterialTheme.colorScheme.surfaceVariant
                    ),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text("PASS", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = if (item.status == CheckItemStatus.PASS) Color.White else MaterialTheme.colorScheme.onSurface)
                }

                Button(
                    onClick = { onStatusChange(CheckItemStatus.FAIL) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (item.status == CheckItemStatus.FAIL) SafetyRed else MaterialTheme.colorScheme.surfaceVariant
                    ),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text("FAIL", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = if (item.status == CheckItemStatus.FAIL) Color.White else MaterialTheme.colorScheme.onSurface)
                }

                Button(
                    onClick = { onStatusChange(CheckItemStatus.NA) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (item.status == CheckItemStatus.NA) SafetyYellow else MaterialTheme.colorScheme.surfaceVariant
                    ),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text("N/A", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = if (item.status == CheckItemStatus.NA) Color.Black else MaterialTheme.colorScheme.onSurface)
                }
            }
        }
    }
}

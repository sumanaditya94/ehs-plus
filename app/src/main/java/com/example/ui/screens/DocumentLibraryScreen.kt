package com.example.ui.screens

import android.content.Intent
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.SafetyCheck
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.language.AppLanguage
import com.example.data.model.AuthoritativeDocument
import com.example.data.model.DocumentPage
import com.example.data.model.QuizQuestion
import com.example.ui.theme.SafetyAmber
import com.example.ui.theme.SafetyBlue
import com.example.ui.theme.SafetyGreen
import com.example.ui.theme.SafetyOrange
import com.example.ui.theme.SafetyRed
import com.example.ui.viewmodel.SafetyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocumentLibraryScreen(
    viewModel: SafetyViewModel,
    onNavigateToChecklists: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val currentLang by viewModel.currentLanguage.collectAsState()
    val isHindi = currentLang == AppLanguage.HINDI

    val docs = viewModel.authoritativeDocs
    val selectedDoc by viewModel.selectedDoc.collectAsState()
    val selectedTab by viewModel.docExplorerTab.collectAsState()

    var showDocSelectorDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Document Header & Selector
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = androidx.compose.foundation.BorderStroke(1.dp, SafetyOrange.copy(alpha = 0.35f))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = SafetyGreen.copy(alpha = 0.15f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, SafetyGreen.copy(alpha = 0.4f))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = null,
                                tint = SafetyGreen,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (isHindi) selectedDoc.statusHi else selectedDoc.status,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = SafetyGreen
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = SafetyBlue.copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = "${selectedDoc.fileType} • ${selectedDoc.totalPagesOrSlides} ${if (isHindi) "पृष्ठ" else "Pages"}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = SafetyBlue,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = if (isHindi) selectedDoc.titleHi else selectedDoc.title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = "${if (isHindi) "फ़ाइल:" else "File:"} ${selectedDoc.fileName} • ${selectedDoc.version} (${selectedDoc.approvedDate})",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = if (isHindi) selectedDoc.summaryHi else selectedDoc.summary,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Switch Document Button
                Button(
                    onClick = { showDocSelectorDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("switch_doc_btn"),
                    colors = ButtonDefaults.buttonColors(containerColor = SafetyOrange.copy(alpha = 0.15f)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Description,
                        contentDescription = null,
                        tint = SafetyOrange,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isHindi) "अन्य अनुमोदित दस्तावेज़ चुनें (${docs.size} उपलब्ध)" else "Switch Approved Document (${docs.size} available)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = SafetyOrange
                    )
                }
            }
        }

        // Multi-View Explorer Tabs (6 views from the same source document)
        val viewTabs = listOf(
            viewModel.getString("doc_view_original"),   // 0: Original Document
            viewModel.getString("doc_view_learning"),   // 1: Simplified Learning
            viewModel.getString("doc_view_checklist"),  // 2: Checklist
            viewModel.getString("doc_view_template"),   // 3: Template
            viewModel.getString("doc_view_quiz"),       // 4: Quiz
            viewModel.getString("doc_view_reference")   // 5: Reference
        )

        ScrollableTabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = SafetyOrange,
            edgePadding = 12.dp
        ) {
            viewTabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { viewModel.setDocExplorerTab(index) },
                    modifier = Modifier.testTag("doc_subtab_$index"),
                    text = {
                        Text(
                            text = title,
                            fontSize = 12.sp,
                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }
        }

        // Sub-View Content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            when (selectedTab) {
                0 -> OriginalDocumentView(selectedDoc, isHindi)
                1 -> SimplifiedLearningView(viewModel, selectedDoc, isHindi)
                2 -> DocumentChecklistView(viewModel, selectedDoc, isHindi, onNavigateToChecklists)
                3 -> DocumentTemplateView(viewModel, selectedDoc, isHindi, onNavigateToChecklists)
                4 -> DocumentQuizView(viewModel, selectedDoc, isHindi)
                5 -> DocumentReferenceView(viewModel, selectedDoc, isHindi)
            }
        }
    }

    // Document Selector Modal Dialog
    if (showDocSelectorDialog) {
        AlertDialog(
            onDismissRequest = { showDocSelectorDialog = false },
            title = {
                Text(
                    text = if (isHindi) "अधिकृत दस्तावेज़ चुनें" else "Select Authoritative Document",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            },
            text = {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(docs) { doc ->
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (doc.id == selectedDoc.id) SafetyOrange.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (doc.id == selectedDoc.id) SafetyOrange else Color.Transparent
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.selectDocument(doc)
                                    showDocSelectorDialog = false
                                }
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = doc.fileType,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SafetyOrange
                                    )
                                    Text(
                                        text = doc.version,
                                        fontSize = 10.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Text(
                                    text = if (isHindi) doc.titleHi else doc.title,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = doc.fileName,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showDocSelectorDialog = false }) {
                    Text(if (isHindi) "बंद करें" else "Close", color = SafetyOrange)
                }
            }
        )
    }
}

// -------------------------------------------------------------
// 1. Original Document View (Page / Slide browser)
// -------------------------------------------------------------
@Composable
fun OriginalDocumentView(doc: AuthoritativeDocument, isHindi: Boolean) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        item {
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = SafetyBlue.copy(alpha = 0.1f),
                border = androidx.compose.foundation.BorderStroke(1.dp, SafetyBlue.copy(alpha = 0.3f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Description, contentDescription = null, tint = SafetyBlue, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isHindi)
                            "यह दृश्य मूल ${doc.fileName} के अधिकृत पृष्ठों व तालिकाओं का सीधा संदर्भ प्रदान करता है।"
                        else
                            "Authoritative extract directly mapped to pages/slides of ${doc.fileName}.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        items(doc.pages) { page ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = SafetyOrange.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = if (doc.fileType == "PPT") "Slide ${page.pageNumber}" else "Page ${page.pageNumber}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = SafetyOrange,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }

                        Text(
                            text = doc.version,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = if (isHindi) page.titleHi else page.title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = if (isHindi) page.summaryHi else page.summary,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    val points = if (isHindi) page.keyPointsHi else page.keyPoints
                    points.forEach { pt ->
                        Row(modifier = Modifier.padding(vertical = 2.dp)) {
                            Text("• ", color = SafetyOrange, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text(pt, fontSize = 12.sp, lineHeight = 16.sp, color = MaterialTheme.colorScheme.onSurface)
                        }
                    }

                    // Extracted Tables
                    val tables = if (isHindi) page.extractedTablesHi else page.extractedTables
                    if (tables.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(imageVector = Icons.Default.TableChart, contentDescription = null, tint = SafetyBlue, modifier = Modifier.size(13.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = if (isHindi) "दस्तावेज़ तालिका (Table Extract)" else "Document Table Extract",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SafetyBlue
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                tables.forEach { row ->
                                    Text(row, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.padding(vertical = 1.dp))
                                }
                            }
                        }
                    }

                    // Warning
                    val warn = if (isHindi) page.safetyWarningHi else page.safetyWarning
                    if (warn.isNotBlank()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = SafetyRed.copy(alpha = 0.12f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.Warning, contentDescription = null, tint = SafetyRed, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(warn, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = SafetyRed)
                            }
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 2. Simplified Learning View
// -------------------------------------------------------------
@Composable
fun SimplifiedLearningView(viewModel: SafetyViewModel, doc: AuthoritativeDocument, isHindi: Boolean) {
    val modules = viewModel.microTrainingModules.filter { it.citation.fileName == doc.fileName }
    val displayModules = if (modules.isEmpty()) viewModel.microTrainingModules.take(1) else modules

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        items(displayModules) { mod ->
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, SafetyOrange.copy(alpha = 0.3f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = if (isHindi) mod.titleHi else mod.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = if (isHindi) mod.citation.displayHindi() else mod.citation.displayEnglish(),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SafetyOrange,
                        modifier = Modifier.padding(top = 2.dp, bottom = 8.dp)
                    )

                    // Objective
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = SafetyBlue.copy(alpha = 0.1f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = viewModel.getString("learning_objective_label"),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = SafetyBlue
                            )
                            Text(
                                text = if (isHindi) mod.learningObjectiveHi else mod.learningObjective,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Explanation
                    Text(
                        text = if (isHindi) mod.shortExplanationHi else mod.shortExplanation,
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // DOs & DONTs side by side or stacked
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        // DOs
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = SafetyGreen.copy(alpha = 0.1f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, SafetyGreen.copy(alpha = 0.3f)),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = viewModel.getString("dos_label"),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SafetyGreen
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                val dos = if (isHindi) mod.doListHi else mod.doList
                                dos.forEach { d ->
                                    Text("✓ $d", fontSize = 11.sp, lineHeight = 15.sp, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.padding(vertical = 1.dp))
                                }
                            }
                        }

                        // DONTs
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = SafetyRed.copy(alpha = 0.1f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, SafetyRed.copy(alpha = 0.3f)),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = viewModel.getString("donts_label"),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SafetyRed
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                val donts = if (isHindi) mod.dontListHi else mod.dontList
                                donts.forEach { d ->
                                    Text("✗ $d", fontSize = 11.sp, lineHeight = 15.sp, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.padding(vertical = 1.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 3. Document Checklist & Template Engine View
// -------------------------------------------------------------
@Composable
fun DocumentChecklistView(
    viewModel: SafetyViewModel,
    doc: AuthoritativeDocument,
    isHindi: Boolean,
    onNavigateToChecklists: () -> Unit
) {
    val template = viewModel.dailyInspectionTemplate

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, SafetyOrange.copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = if (isHindi) template.titleHi else template.title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = if (isHindi) template.citation.displayHindi() else template.citation.displayEnglish(),
                        fontSize = 11.sp,
                        color = SafetyOrange,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = if (isHindi) template.descriptionHi else template.description,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = onNavigateToChecklists,
                        colors = ButtonDefaults.buttonColors(containerColor = SafetyOrange),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(if (isHindi) "पूर्ण निरीक्षण प्रपत्र खोलें (11 बिंदु)" else "Open Full Inspection Form (11 Items)", fontSize = 12.sp)
                    }
                }
            }
        }

        items(template.items) { item ->
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.surface,
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Surface(shape = RoundedCornerShape(4.dp), color = SafetyOrange.copy(alpha = 0.12f)) {
                            Text(
                                text = if (isHindi) item.categoryHi else item.category,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = SafetyOrange,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (isHindi) item.questionHi else item.question,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = if (isHindi) item.criteriaHi else item.criteria,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 4. Document Template View (JSA / HIRA / Permitting)
// -------------------------------------------------------------
@Composable
fun DocumentTemplateView(
    viewModel: SafetyViewModel,
    doc: AuthoritativeDocument,
    isHindi: Boolean,
    onNavigateToChecklists: () -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = if (isHindi) "अधिकृत प्रपत्र एवं JSA संरचना" else "Structured Work Form Templates",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = if (isHindi) "स्रोत: ${doc.fileName} • ${doc.version}" else "Source: ${doc.fileName} • ${doc.version}",
                        fontSize = 11.sp,
                        color = SafetyOrange,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (isHindi)
                            "यह दस्तावेज़ फ़ील्ड उपयोग हेतु मानकीकृत मोबाइल निरीक्षण प्रपत्र, जेएसए (JSA) एवं परमिट प्रारूप तैयार करता है।"
                        else
                            "This document establishes standardized mobile reporting forms, JSAs, and site hazard templates.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = onNavigateToChecklists,
                        colors = ButtonDefaults.buttonColors(containerColor = SafetyOrange),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(if (isHindi) "दैनिक निरीक्षण शुरू करें" else "Launch Daily Inspection", fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 5. Document Quiz View
// -------------------------------------------------------------
@Composable
fun DocumentQuizView(viewModel: SafetyViewModel, doc: AuthoritativeDocument, isHindi: Boolean) {
    val modules = viewModel.microTrainingModules.filter { it.citation.fileName == doc.fileName }
    val questions = if (modules.isNotEmpty()) {
        modules.map { if (isHindi) it.quickQuestionHi else it.quickQuestion }
    } else {
        listOf(viewModel.todayContent.dailyQuiz)
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        items(questions) { q ->
            var selectedOption by remember { mutableIntStateOf(-1) }
            val isSubmitted = selectedOption != -1

            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = q.question,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    q.options.forEachIndexed { idx, opt ->
                        val isSelected = selectedOption == idx
                        val isCorrect = idx == q.correctAnswerIndex
                        val btnColor = when {
                            !isSubmitted -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
                            isSelected && isCorrect -> SafetyGreen.copy(alpha = 0.2f)
                            isSelected && !isCorrect -> SafetyRed.copy(alpha = 0.2f)
                            isCorrect -> SafetyGreen.copy(alpha = 0.15f)
                            else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f)
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = btnColor,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp)
                                .clickable { selectedOption = idx }
                        ) {
                            Text(
                                text = opt,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
                            )
                        }
                    }

                    if (isSubmitted) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = q.explanation,
                            fontSize = 11.sp,
                            color = if (selectedOption == q.correctAnswerIndex) SafetyGreen else SafetyRed
                        )
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 6. Document Reference Limits View
// -------------------------------------------------------------
@Composable
fun DocumentReferenceView(viewModel: SafetyViewModel, doc: AuthoritativeDocument, isHindi: Boolean) {
    val cards = viewModel.docReferenceCards.filter { it.citation.fileName == doc.fileName }
    val displayCards = if (cards.isEmpty()) viewModel.docReferenceCards else cards

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        items(displayCards) { card ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, SafetyOrange.copy(alpha = 0.3f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isHindi) card.parameterHi else card.parameter,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = if (isHindi) card.categoryHi else card.category,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = SafetyOrange
                        )
                    }

                    Text(
                        text = if (isHindi) card.citation.displayHindi() else card.citation.displayEnglish(),
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Surface(shape = RoundedCornerShape(8.dp), color = SafetyGreen.copy(alpha = 0.12f), modifier = Modifier.weight(1f)) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(if (isHindi) "सुरक्षित सीमा" else "Safe Range", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = SafetyGreen)
                                Text(if (isHindi) card.safeRangeHi else card.safeRange, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface)
                            }
                        }
                        Surface(shape = RoundedCornerShape(8.dp), color = SafetyRed.copy(alpha = 0.12f), modifier = Modifier.weight(1f)) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(if (isHindi) "खतरनाक सीमा" else "Danger Threshold", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = SafetyRed)
                                Text(if (isHindi) card.dangerThresholdHi else card.dangerThreshold, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "⚠️ " + (if (isHindi) card.criticalWarningHi else card.criticalWarning),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = SafetyAmber
                    )
                }
            }
        }
    }
}

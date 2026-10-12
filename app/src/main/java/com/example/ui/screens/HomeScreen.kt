package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Elevator
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.filled.SafetyCheck
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.language.AppLanguage
import com.example.data.model.SearchResultItem
import com.example.data.model.SearchResultType
import com.example.ui.theme.SafetyAmber
import com.example.ui.theme.SafetyBlue
import com.example.ui.theme.SafetyGreen
import com.example.ui.theme.SafetyOrange
import com.example.ui.theme.SafetyRed
import com.example.ui.theme.SafetyYellow
import com.example.ui.viewmodel.SafetyViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: SafetyViewModel,
    onNavigateToLearn: () -> Unit,
    onNavigateToTools: () -> Unit,
    onNavigateToPpe: () -> Unit,
    onNavigateToAi: () -> Unit,
    onNavigateToChecklists: () -> Unit,
    onNavigateToChemicals: () -> Unit,
    onNavigateToSafetyLimits: () -> Unit,
    onNavigateToLoto: () -> Unit,
    onNavigateToRiskCalculator: () -> Unit,
    onNavigateToAiScanner: () -> Unit,
    onNavigateToTopicDetail: (String) -> Unit = {}
) {
    val currentLang by viewModel.currentLanguage.collectAsState()
    val isHindi = currentLang == AppLanguage.HINDI

    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()
    val today = viewModel.todayContent

    val savedJsas by viewModel.savedJsas.collectAsState()
    val savedChecklists by viewModel.savedChecklistReports.collectAsState()
    val savedReports by viewModel.savedFieldReports.collectAsState()
    val savedQuizzes by viewModel.savedQuizResults.collectAsState()

    var showDailyQuizDialog by remember { mutableStateOf(false) }
    var showPdfDataModal by remember { mutableStateOf(false) }
    var customPdfText by remember { mutableStateOf("") }
    var customDataSavedMsg by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // App Header with Logo & Language Selector Button
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // App Logo Image
                        Box(
                            modifier = Modifier
                                .size(46.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .border(1.5.dp, SafetyOrange, RoundedCornerShape(12.dp))
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.safety_app_logo_1787325966616),
                                contentDescription = "Safety App Logo",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(SafetyOrange)
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = viewModel.getString("app_title"),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = SafetyOrange,
                                    letterSpacing = 1.1.sp
                                )
                            }
                            Text(
                                text = viewModel.getString("app_tagline"),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Language Toggle Button (English / हिन्दी)
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = SafetyOrange.copy(alpha = 0.15f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, SafetyOrange.copy(alpha = 0.4f)),
                        modifier = Modifier
                            .clickable { viewModel.toggleLanguage() }
                            .testTag("language_toggle_btn")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Translate,
                                contentDescription = "Change Language",
                                tint = SafetyOrange,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isHindi) "English" else "हिन्दी",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = SafetyOrange
                            )
                        }
                    }
                }


                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${viewModel.getString("handbook_updated")} • ${viewModel.lastUpdated}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                    )

                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = SafetyGreen.copy(alpha = 0.15f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, SafetyGreen.copy(alpha = 0.3f))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.CloudDone,
                                contentDescription = "Offline Cache",
                                tint = SafetyGreen,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = viewModel.getString("offline_ready"),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = SafetyGreen
                            )
                        }
                    }
                }
            }
        }

        // Global Search Bar
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { viewModel.updateSearchQuery(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("global_search_input"),
                    placeholder = {
                        Text(
                            viewModel.getString("search_placeholder"),
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = SafetyOrange
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { viewModel.updateSearchQuery("") }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Clear",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedBorderColor = SafetyOrange,
                        unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    singleLine = true
                )

                // Grouped Knowledge Search Results Across Approved Documents
                val docSearchResults by viewModel.docSearchResults.collectAsState()

                if (searchQuery.isNotBlank() && (searchResults.isNotEmpty() || docSearchResults.isNotEmpty())) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 6.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = androidx.compose.foundation.BorderStroke(1.dp, SafetyOrange.copy(alpha = 0.4f))
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (isHindi) "अनुमोदित ज्ञानकोष खोज परिणाम" else "APPROVED KNOWLEDGE SEARCH",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SafetyOrange
                                )
                                Surface(shape = RoundedCornerShape(4.dp), color = SafetyGreen.copy(alpha = 0.15f)) {
                                    Text(
                                        text = if (isHindi) "सत्यापित स्रोत" else "Verified Sources",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SafetyGreen,
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            // Categorized Sections (Learn, PPE, Checklist, Tools, Templates, Reference, Original Documents)
                            if (docSearchResults.isNotEmpty()) {
                                docSearchResults.forEach { (cat, items) ->
                                    val catHeader = when (cat) {
                                        com.example.data.model.DocSearchCategory.LEARN -> viewModel.getString("search_cat_learn")
                                        com.example.data.model.DocSearchCategory.PPE -> viewModel.getString("search_cat_ppe")
                                        com.example.data.model.DocSearchCategory.CHECKLIST -> viewModel.getString("search_cat_checklist")
                                        com.example.data.model.DocSearchCategory.TOOLS -> viewModel.getString("search_cat_tools")
                                        com.example.data.model.DocSearchCategory.TEMPLATES -> viewModel.getString("search_cat_templates")
                                        com.example.data.model.DocSearchCategory.REFERENCE -> viewModel.getString("search_cat_reference")
                                        com.example.data.model.DocSearchCategory.ORIGINAL_DOCUMENTS -> viewModel.getString("search_cat_documents")
                                    }

                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = SafetyOrange.copy(alpha = 0.08f),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 3.dp)
                                    ) {
                                        Text(
                                            text = "● $catHeader (${items.size})",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = SafetyOrange,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }

                                    items.forEach { docResult ->
                                        Surface(
                                            shape = RoundedCornerShape(8.dp),
                                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 3.dp)
                                                .clickable {
                                                    when (cat) {
                                                        com.example.data.model.DocSearchCategory.LEARN -> onNavigateToLearn()
                                                        com.example.data.model.DocSearchCategory.PPE -> onNavigateToPpe()
                                                        com.example.data.model.DocSearchCategory.CHECKLIST -> onNavigateToChecklists()
                                                        com.example.data.model.DocSearchCategory.TOOLS -> onNavigateToTools()
                                                        com.example.data.model.DocSearchCategory.TEMPLATES -> onNavigateToChecklists()
                                                        com.example.data.model.DocSearchCategory.REFERENCE -> onNavigateToSafetyLimits()
                                                        com.example.data.model.DocSearchCategory.ORIGINAL_DOCUMENTS -> onNavigateToAi()
                                                    }
                                                }
                                        ) {
                                            Column(modifier = Modifier.padding(8.dp)) {
                                                Text(
                                                    text = if (isHindi) docResult.titleHi else docResult.title,
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = MaterialTheme.colorScheme.onSurface
                                                )
                                                Text(
                                                    text = if (isHindi) docResult.snippetHi else docResult.snippet,
                                                    fontSize = 11.sp,
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                                // Small Source Section
                                                Text(
                                                    text = if (isHindi) docResult.citation.displayHindi() else docResult.citation.displayEnglish(),
                                                    fontSize = 9.sp,
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = SafetyOrange,
                                                    modifier = Modifier.padding(top = 2.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            } else {
                                searchResults.forEach { result ->
                                    SearchResultRow(
                                        result = result,
                                        onClick = {
                                            when (result.type) {
                                                SearchResultType.TOPIC -> onNavigateToLearn()
                                                SearchResultType.TOOL -> onNavigateToTools()
                                                SearchResultType.PPE -> onNavigateToPpe()
                                                SearchResultType.CHEMICAL -> onNavigateToChemicals()
                                                SearchResultType.CHECKLIST -> onNavigateToChecklists()
                                                SearchResultType.LIMIT -> onNavigateToSafetyLimits()
                                                SearchResultType.TALK -> onNavigateToLearn()
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Today's Golden Safety Rule Hero Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("golden_rule_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, SafetyOrange.copy(alpha = 0.6f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = SafetyOrange.copy(alpha = 0.2f)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    contentDescription = null,
                                    tint = SafetyOrange,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = viewModel.getString("golden_rule_badge"),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SafetyOrange
                                )
                            }
                        }

                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = SafetyOrange,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = viewModel.getString("golden_rule_title"),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = viewModel.getString("golden_rule_body"),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.background,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = viewModel.getString("golden_rule_sub"),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = SafetyAmber,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }

        // PRIMARY QUICK ACCESS INDUSTRIAL TOOLS (Grid)
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = viewModel.getString("quick_tools_title"),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = if (isHindi) "फील्ड उपकरण" else "10 Fast Utilities",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Row 1: Risk Matrix & JSA Builder
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    QuickToolCard(
                        title = viewModel.getString("tool_risk_matrix"),
                        subtitle = viewModel.getString("tool_risk_matrix_desc"),
                        icon = Icons.Default.Warning,
                        accentColor = SafetyRed,
                        modifier = Modifier.weight(1f),
                        testTag = "home_tool_risk_matrix",
                        onClick = onNavigateToRiskCalculator
                    )
                    QuickToolCard(
                        title = viewModel.getString("tool_jsa"),
                        subtitle = viewModel.getString("tool_jsa_desc"),
                        icon = Icons.Default.Checklist,
                        accentColor = SafetyAmber,
                        modifier = Modifier.weight(1f),
                        testTag = "home_tool_jsa",
                        onClick = onNavigateToTools
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Row 2: 4-Gas Detector & LOTO Protocol
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    QuickToolCard(
                        title = viewModel.getString("tool_gas_tester"),
                        subtitle = viewModel.getString("tool_gas_tester_desc"),
                        icon = Icons.Default.Straighten,
                        accentColor = SafetyBlue,
                        modifier = Modifier.weight(1f),
                        testTag = "home_tool_gas_tester",
                        onClick = onNavigateToTools
                    )
                    QuickToolCard(
                        title = viewModel.getString("tool_loto"),
                        subtitle = viewModel.getString("tool_loto_desc"),
                        icon = Icons.Default.Lock,
                        accentColor = SafetyOrange,
                        modifier = Modifier.weight(1f),
                        testTag = "home_tool_loto",
                        onClick = onNavigateToLoto
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Row 3: Checklists & Chemicals
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    QuickToolCard(
                        title = viewModel.getString("tool_checklists"),
                        subtitle = viewModel.getString("tool_checklists_desc"),
                        icon = Icons.Default.CheckCircle,
                        accentColor = SafetyGreen,
                        modifier = Modifier.weight(1f),
                        testTag = "home_tool_checklists",
                        onClick = onNavigateToChecklists
                    )
                    QuickToolCard(
                        title = viewModel.getString("tool_chemicals"),
                        subtitle = viewModel.getString("tool_chemicals_desc"),
                        icon = Icons.Default.Science,
                        accentColor = Color(0xFF9C27B0),
                        modifier = Modifier.weight(1f),
                        testTag = "home_tool_chemicals",
                        onClick = onNavigateToChemicals
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Row 4: Critical Limits & Field Observation Report
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    QuickToolCard(
                        title = viewModel.getString("tool_limits"),
                        subtitle = viewModel.getString("tool_limits_desc"),
                        icon = Icons.Default.Straighten,
                        accentColor = SafetyYellow,
                        modifier = Modifier.weight(1f),
                        testTag = "home_tool_limits",
                        onClick = onNavigateToSafetyLimits
                    )
                    QuickToolCard(
                        title = viewModel.getString("tool_reports"),
                        subtitle = viewModel.getString("tool_reports_desc"),
                        icon = Icons.Default.ReportProblem,
                        accentColor = SafetyRed,
                        modifier = Modifier.weight(1f),
                        testTag = "home_tool_reports",
                        onClick = { onNavigateToTopicDetail("reports") }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Row 5: AI Hazard Scanner & PPE Selector
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    QuickToolCard(
                        title = viewModel.getString("tool_ai_scanner"),
                        subtitle = viewModel.getString("tool_ai_scanner_desc"),
                        icon = Icons.Default.PhotoCamera,
                        accentColor = SafetyOrange,
                        modifier = Modifier.weight(1f),
                        testTag = "home_tool_ai_scanner",
                        onClick = onNavigateToAiScanner
                    )
                    QuickToolCard(
                        title = viewModel.getString("tool_ppe_selector"),
                        subtitle = viewModel.getString("tool_ppe_selector_desc"),
                        icon = Icons.Default.SafetyCheck,
                        accentColor = SafetyBlue,
                        modifier = Modifier.weight(1f),
                        testTag = "home_tool_ppe_selector",
                        onClick = onNavigateToPpe
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Worker Daily Utility Quick Access
                Text(
                    text = if (isHindi) "दैनिक श्रमिक उपयोगिता उपकरण" else "DAILY WORKER UTILITIES",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = SafetyGreen,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    QuickToolCard(
                        title = if (isHindi) "वेतन कैलकुलेटर" else "Salary Calc",
                        subtitle = if (isHindi) "दैनिक व मासिक मजदूरी" else "Gross & Net Pay",
                        icon = Icons.Default.Payments,
                        accentColor = SafetyGreen,
                        modifier = Modifier.weight(1f),
                        testTag = "home_tool_salary_calc",
                        onClick = onNavigateToTools
                    )
                    QuickToolCard(
                        title = if (isHindi) "ओटी (OT) कैलकुलेटर" else "Overtime Calc",
                        subtitle = if (isHindi) "डबल ओटी दर गणना" else "Factory Act OT",
                        icon = Icons.Default.Schedule,
                        accentColor = SafetyOrange,
                        modifier = Modifier.weight(1f),
                        testTag = "home_tool_ot_calc",
                        onClick = onNavigateToTools
                    )
                    QuickToolCard(
                        title = if (isHindi) "हाजिरी डायरी" else "Attendance Log",
                        subtitle = if (isHindi) "शिफ्ट व देय दिन" else "Shift & Days",
                        icon = Icons.Default.CalendarToday,
                        accentColor = SafetyBlue,
                        modifier = Modifier.weight(1f),
                        testTag = "home_tool_attendance_calc",
                        onClick = onNavigateToTools
                    )
                }
            }
        }

        // 4-GAS ATMOSPHERIC SAFETY SNAPSHOT WIDGET
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigateToTools() },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, SafetyBlue.copy(alpha = 0.4f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = viewModel.getString("atm_widget_title"),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = SafetyBlue,
                                letterSpacing = 0.8.sp
                            )
                            Text(
                                text = viewModel.getString("atm_widget_sub"),
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.Straighten,
                            contentDescription = null,
                            tint = SafetyBlue,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // 4 Gas Status Boxes
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        GasSnapshotPill("O₂", "20.9%", "19.5-23.5%", SafetyGreen, Modifier.weight(1f))
                        GasSnapshotPill("LEL", "0 %", "< 10%", SafetyGreen, Modifier.weight(1f))
                        GasSnapshotPill("H₂S", "0 ppm", "< 10 ppm", SafetyGreen, Modifier.weight(1f))
                        GasSnapshotPill("CO", "0 ppm", "< 35 ppm", SafetyGreen, Modifier.weight(1f))
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.background,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = SafetyGreen,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isHindi) "मानदंड: सभी 4 गैसें सुरक्षित सीमा में होने पर ही प्रवेश संभव" else "Rule: Enter only if continuous testing verifies all 4 gases in safe zone",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }

        // DAILY SAFETY SPOTLIGHT & QUIZ
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = SafetyAmber.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = viewModel.getString("daily_spotlight"),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = SafetyAmber,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                        Text(
                            text = today.dayOfWeek,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = today.topicTitle,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = today.topicSummary,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colorScheme.background,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "${viewModel.getString("daily_tip_label")} ${today.safetyTip}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${viewModel.getString("hazard_day_label")} ${today.hazardOfTheDay}",
                                fontSize = 11.5.sp,
                                color = SafetyAmber
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = onNavigateToLearn,
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SafetyOrange)
                        ) {
                            Icon(imageVector = Icons.Default.MenuBook, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(if (isHindi) "अध्याय पढ़ें" else "Learn Topic", fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = { showDailyQuizDialog = true },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Icon(imageVector = Icons.Default.QuestionAnswer, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(viewModel.getString("daily_quiz_btn"), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        // EMERGENCY SOS & EVACUATION PROCEDURES
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SafetyRed.copy(alpha = 0.08f)),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, SafetyRed.copy(alpha = 0.4f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(SafetyRed.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Emergency,
                                contentDescription = null,
                                tint = SafetyRed,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = viewModel.getString("sos_title"),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = SafetyRed
                            )
                            Text(
                                text = viewModel.getString("sos_sub"),
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(text = viewModel.getString("sos_step1"), fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Medium)
                        Text(text = viewModel.getString("sos_step2"), fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Medium)
                        Text(text = viewModel.getString("sos_step3"), fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Medium)
                        Text(text = viewModel.getString("sos_step4"), fontSize = 12.sp, color = SafetyRed, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // HIGH-RISK WORK PERMITS (PTW) GUIDE
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = viewModel.getString("ptw_title"),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    letterSpacing = 0.8.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(end = 8.dp)
                ) {
                    item {
                        PtwGuideCard(
                            title = viewModel.getString("ptw_hot_work"),
                            detail = viewModel.getString("ptw_hot_work_sub"),
                            icon = Icons.Default.LocalFireDepartment,
                            color = SafetyOrange
                        )
                    }
                    item {
                        PtwGuideCard(
                            title = viewModel.getString("ptw_height"),
                            detail = viewModel.getString("ptw_height_sub"),
                            icon = Icons.Default.Elevator,
                            color = SafetyBlue
                        )
                    }
                    item {
                        PtwGuideCard(
                            title = viewModel.getString("ptw_confined"),
                            detail = viewModel.getString("ptw_confined_sub"),
                            icon = Icons.Default.Straighten,
                            color = SafetyRed
                        )
                    }
                    item {
                        PtwGuideCard(
                            title = viewModel.getString("ptw_loto"),
                            detail = viewModel.getString("ptw_loto_sub"),
                            icon = Icons.Default.Lock,
                            color = SafetyAmber
                        )
                    }
                }
            }
        }

        // CUSTOM SAFETY PDF & SITE SOP INGESTION CARD
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showPdfDataModal = true }
                    .testTag("home_pdf_ingest_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, SafetyOrange.copy(alpha = 0.5f))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(SafetyOrange.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Description,
                            contentDescription = "PDF / SOP Ingest",
                            tint = SafetyOrange,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = viewModel.getString("custom_data_title"),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = viewModel.getString("custom_data_sub"),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
            }
        }

        // LOCAL SAVED DATA STATS
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = viewModel.getString("local_summary_title"),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SafetyGreen,
                        letterSpacing = 0.8.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        LocalStatPill(count = savedJsas.size, label = viewModel.getString("saved_jsas"))
                        LocalStatPill(count = savedChecklists.size, label = viewModel.getString("saved_checks"))
                        LocalStatPill(count = savedReports.size, label = viewModel.getString("saved_reports"))
                        LocalStatPill(count = savedQuizzes.size, label = viewModel.getString("saved_quizzes"))
                    }
                }
            }
        }

        // Safety Disclaimer
        item {
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isHindi)
                            "यह ऐप औद्योगिक सुरक्षा शिक्षा व फील्ड मार्गदर्शन के लिए है। कार्यस्थल पर हमेशा कंपनी की स्वीकृत एसओपी (SOP), सुरक्षा नियमों और अधिकृत अधिकारियों के निर्देशों का पालन करें।"
                        else
                            "This app is an educational & practical field-support tool. Always follow your site's approved procedures, applicable laws/standards, and competent-person guidance.",
                        fontSize = 11.sp,
                        lineHeight = 15.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                    )
                }
            }
        }
    }

    // Daily Quiz Dialog
    if (showDailyQuizDialog) {
        DailyQuizModal(
            quiz = today.dailyQuiz,
            isHindi = isHindi,
            viewModel = viewModel,
            onDismiss = { showDailyQuizDialog = false },
            onRecordScore = { score, total ->
                viewModel.recordQuizResult("Daily Safety", score, total)
            }
        )
    }

    // Custom PDF / SOP Ingestion Dialog
    if (showPdfDataModal) {
        CustomPdfDataDialog(
            isHindi = isHindi,
            viewModel = viewModel,
            textValue = customPdfText,
            onTextChange = { customPdfText = it },
            showSaved = customDataSavedMsg,
            onSave = {
                if (customPdfText.isNotBlank()) {
                    viewModel.saveParsedDocument(
                        title = if (isHindi) "कस्टम प्लांट SOP / PDF संदर्भ" else "Custom Plant SOP / PDF Reference",
                        documentType = "User Ingested Manual",
                        summary = "Custom safety protocols extracted from user manual/PPT.",
                        hazards = listOf("Operational & Field Hazards", "Site-specific risks"),
                        ppe = listOf("Mandatory site PPE per SOP"),
                        checklist = listOf("Pre-work verification", "Follow custom site protocol"),
                        rules = listOf(customPdfText),
                        rawContent = customPdfText
                    )
                    viewModel.saveFieldReport(
                        type = "Site SOP / PDF Reference",
                        title = "Custom Ingested Site Standard",
                        location = "Custom Site Manual",
                        description = customPdfText,
                        potential = "Procedural reference for safety compliance",
                        immediate = "Reference saved to local app database",
                        corrective = "Follow during JSA, toolboxes and work execution"
                    )
                    customDataSavedMsg = true
                }
            },
            onDismiss = {
                showPdfDataModal = false
                customDataSavedMsg = false
            }
        )
    }
}

@Composable
fun CustomPdfDataDialog(
    isHindi: Boolean,
    viewModel: SafetyViewModel,
    textValue: String,
    onTextChange: (String) -> Unit,
    showSaved: Boolean,
    onSave: () -> Unit,
    onDismiss: () -> Unit
) {
    androidx.compose.ui.window.Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = viewModel.getString("custom_data_how_title"),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = SafetyOrange
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = viewModel.getString("custom_data_step1"),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = viewModel.getString("custom_data_step2"),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = viewModel.getString("custom_data_step3"),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = textValue,
                    onValueChange = onTextChange,
                    placeholder = {
                        Text(
                            text = viewModel.getString("custom_data_paste_placeholder"),
                            fontSize = 12.sp
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp),
                    shape = RoundedCornerShape(12.dp)
                )

                if (showSaved) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (isHindi) "✓ डेटा सफलतापूर्वक स्थानीय डेटाबेस में सहेजा गया!" else "✓ Custom reference safely saved to on-device database!",
                        color = SafetyGreen,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(text = viewModel.getString("quiz_close"))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = onSave,
                        colors = ButtonDefaults.buttonColors(containerColor = SafetyOrange),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = viewModel.getString("custom_data_save_btn"),
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun QuickToolCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    accentColor: Color,
    modifier: Modifier = Modifier,
    testTag: String = "",
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(96.dp)
            .clickable { onClick() }
            .testTag(testTag),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(accentColor.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = accentColor,
                        modifier = Modifier.size(17.dp)
                    )
                }
            }

            Column {
                Text(
                    text = title,
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = subtitle,
                    fontSize = 10.5.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun GasSnapshotPill(
    gasName: String,
    currentVal: String,
    safeThreshold: String,
    statusColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.background,
        border = androidx.compose.foundation.BorderStroke(1.dp, statusColor.copy(alpha = 0.3f)),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = gasName, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SafetyOrange)
            Text(text = currentVal, fontSize = 12.sp, fontWeight = FontWeight.Black, color = statusColor)
            Text(text = safeThreshold, fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun PtwGuideCard(
    title: String,
    detail: String,
    icon: ImageVector,
    color: Color
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface,
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.width(180.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = color,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = detail,
                fontSize = 10.5.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 14.sp
            )
        }
    }
}

@Composable
fun LocalStatPill(
    count: Int,
    label: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "$count",
            fontSize = 18.sp,
            fontWeight = FontWeight.Black,
            color = SafetyOrange
        )
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun SearchResultRow(
    result: SearchResultItem,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp, horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val (badgeColor, label) = when (result.type) {
            SearchResultType.TOPIC -> Pair(SafetyOrange, "TOPIC")
            SearchResultType.TOOL -> Pair(SafetyBlue, "TOOL")
            SearchResultType.PPE -> Pair(SafetyGreen, "PPE")
            SearchResultType.CHEMICAL -> Pair(Color(0xFF9C27B0), "CHEMICAL")
            SearchResultType.CHECKLIST -> Pair(SafetyYellow, "CHECKLIST")
            SearchResultType.LIMIT -> Pair(SafetyAmber, "LIMIT")
            SearchResultType.TALK -> Pair(SafetyBlue, "TALK")
        }

        Surface(
            shape = RoundedCornerShape(4.dp),
            color = badgeColor.copy(alpha = 0.15f)
        ) {
            Text(
                text = label,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = badgeColor,
                modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = result.title,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = result.subtitle,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun DailyQuizModal(
    quiz: com.example.data.model.QuizQuestion,
    isHindi: Boolean,
    viewModel: SafetyViewModel,
    onDismiss: () -> Unit,
    onRecordScore: (Int, Int) -> Unit
) {
    var selectedOption by remember { mutableStateOf<Int?>(null) }
    var submitted by remember { mutableStateOf(false) }

    androidx.compose.ui.window.Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = viewModel.getString("quiz_dialog_title").uppercase(),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = SafetyOrange,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = quiz.question,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(14.dp))

                quiz.options.forEachIndexed { index, option ->
                    val isSelected = selectedOption == index
                    val isCorrect = index == quiz.correctAnswerIndex
                    val optionBg = when {
                        !submitted && isSelected -> SafetyOrange.copy(alpha = 0.2f)
                        submitted && isCorrect -> SafetyGreen.copy(alpha = 0.25f)
                        submitted && isSelected && !isCorrect -> SafetyRed.copy(alpha = 0.25f)
                        else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    }

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = optionBg,
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isSelected) SafetyOrange else Color.Transparent
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable(enabled = !submitted) { selectedOption = index }
                    ) {
                        Text(
                            text = option,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }

                if (submitted) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.background,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = if (selectedOption == quiz.correctAnswerIndex) viewModel.getString("quiz_correct") else viewModel.getString("quiz_incorrect"),
                                fontWeight = FontWeight.Bold,
                                color = if (selectedOption == quiz.correctAnswerIndex) SafetyGreen else SafetyRed,
                                fontSize = 13.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = quiz.explanation,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(viewModel.getString("quiz_close"), color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }

                    if (!submitted) {
                        Button(
                            onClick = {
                                if (selectedOption != null) {
                                    submitted = true
                                    val score = if (selectedOption == quiz.correctAnswerIndex) 1 else 0
                                    onRecordScore(score, 1)
                                }
                            },
                            enabled = selectedOption != null,
                            colors = ButtonDefaults.buttonColors(containerColor = SafetyOrange)
                        ) {
                            Text(viewModel.getString("quiz_submit"), fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

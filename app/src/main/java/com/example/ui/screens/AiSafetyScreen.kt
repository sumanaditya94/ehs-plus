package com.example.ui.screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ai.HazardFinding
import com.example.data.ai.ScanAnalysisResult
import com.example.ui.theme.SafetyAmber
import com.example.ui.theme.SafetyBlue
import com.example.ui.theme.SafetyGreen
import com.example.ui.theme.SafetyOrange
import com.example.ui.theme.SafetyRed
import com.example.ui.viewmodel.SafetyViewModel

@Composable
fun AiSafetyScreen(
    viewModel: SafetyViewModel,
    modifier: Modifier = Modifier,
    initialTab: Int = 0
) {
    val currentLang by viewModel.currentLanguage.collectAsState()
    val isHindi = currentLang.code == "hi"

    var selectedTab by remember { mutableIntStateOf(initialTab) }
    val tabs = listOf(
        if (isHindi) "आस्क सेफ्टी (Ask Safety)" else "Ask Safety Advisor",
        if (isHindi) "मूल दस्तावेज़ पुस्तकालय" else "Document Library",
        if (isHindi) "AI खतरा विजुअल स्कैनर" else "AI Hazard Scanner"
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
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isHindi) "सत्यापित सुरक्षा ज्ञानकोष" else "VERIFIED INDUSTRIAL KNOWLEDGE",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = SafetyOrange,
                    letterSpacing = 1.2.sp
                )

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = SafetyGreen.copy(alpha = 0.15f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, SafetyGreen.copy(alpha = 0.4f))
                ) {
                    Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = SafetyGreen, modifier = Modifier.size(11.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isHindi) "सत्यापित स्रोत" else "Grounded Mode",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = SafetyGreen
                        )
                    }
                }
            }

            Text(
                text = if (isHindi) "आस्क सेफ्टी सलाहकार व दस्तावेज़" else "Ask Safety Advisor & Documents",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        // Tab Row
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = SafetyOrange
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    modifier = Modifier.testTag("ai_tab_$index"),
                    text = {
                        Text(
                            text = title,
                            fontSize = 11.sp,
                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium,
                            color = if (selectedTab == index) SafetyOrange else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                )
            }
        }

        // Tab Content
        when (selectedTab) {
            0 -> AiChatTab(viewModel, isHindi)
            1 -> DocumentLibraryScreen(viewModel = viewModel)
            2 -> AiScannerTab(viewModel, isHindi)
        }
    }
}

// -------------------------------------------------------------------------
// 1. AI Safety Chat Tab
// -------------------------------------------------------------------------
@Composable
fun AiChatTab(viewModel: SafetyViewModel, isHindi: Boolean) {
    val messages by viewModel.chatMessages.collectAsState()
    val isReplying by viewModel.isAiReplying.collectAsState()
    var inputText by remember { mutableStateOf("") }

    val promptSuggestions = if (isHindi) {
        listOf(
            "एंगल ग्राइंडिंग के लिए आवश्यक PPE?",
            "LOTO शून्य-ऊर्जा अलगाव कैसे करें?",
            "टैंक में सुरक्षित O2 और LEL सीमा?",
            "पिंच पॉइंट पर 5 मिनट का टूलबॉक्स टॉक",
            "सल्फ्यूरिक एसिड को सुरक्षित रूप से कैसे पतला करें?"
        )
    } else {
        listOf(
            "PPE needed for angle grinding",
            "How to execute zero-energy LOTO?",
            "Safe O2 and LEL limits in tanks",
            "5-min toolbox talk on pinch points",
            "How to dilute sulfuric acid safely?"
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // Chat History List
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(top = 12.dp, bottom = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(messages) { msg ->
                ChatBubble(message = msg, isHindi = isHindi)
            }

            if (isReplying) {
                item {
                    Row(
                        modifier = Modifier.padding(start = 12.dp, top = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp,
                            color = SafetyOrange
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isHindi) "AI सुरक्षा सलाहकार मानकों की समीक्षा कर रहा है..." else "AI Safety Advisor is reviewing guidelines...",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Prompt Suggestions Chips
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 4.dp)
        ) {
            items(promptSuggestions) { prompt ->
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = androidx.compose.foundation.BorderStroke(1.dp, SafetyOrange.copy(alpha = 0.4f)),
                    modifier = Modifier.clickable {
                        viewModel.sendChatMessage(prompt)
                    }
                ) {
                    Text(
                        text = prompt,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
        }

        // Input Field & Send Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 96.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                placeholder = {
                    Text(
                        text = if (isHindi) "सुरक्षा प्रश्न पूछें या JSA ड्राफ्ट करें..." else "Ask safety question or draft JSA...",
                        fontSize = 13.sp
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .testTag("ai_chat_input"),
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SafetyOrange,
                    unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                maxLines = 3
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = {
                    if (inputText.isNotBlank()) {
                        viewModel.sendChatMessage(inputText)
                        inputText = ""
                    }
                },
                enabled = inputText.isNotBlank() && !isReplying,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(if (inputText.isNotBlank()) SafetyOrange else MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun ChatBubble(message: SafetyViewModel.ChatMessage, isHindi: Boolean = false) {
    val isUser = message.isUser
    val bubbleColor = if (isUser) SafetyOrange else MaterialTheme.colorScheme.surface
    val textColor = if (isUser) Color.White else MaterialTheme.colorScheme.onSurface
    val align = if (isUser) Alignment.End else Alignment.Start

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = align
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (!isUser) {
                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .clip(CircleShape)
                        .background(SafetyOrange),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(11.dp)
                    )
                }
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (isHindi) "आस्क सेफ्टी (Ask Safety)" else "Ask Safety Advisor",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = SafetyOrange
                )

                if (message.isVerified) {
                    Spacer(modifier = Modifier.width(6.dp))
                    Surface(shape = RoundedCornerShape(4.dp), color = SafetyGreen.copy(alpha = 0.15f)) {
                        Text(
                            text = if (isHindi) "✓ अनुमोदित स्रोत" else "✓ Approved Source",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = SafetyGreen,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }
                }
            } else {
                Text(
                    text = if (isHindi) "आप" else "You",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Surface(
            shape = RoundedCornerShape(
                topStart = 14.dp,
                topEnd = 14.dp,
                bottomStart = if (isUser) 14.dp else 2.dp,
                bottomEnd = if (isUser) 2.dp else 14.dp
            ),
            color = bubbleColor,
            border = if (!isUser) androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant) else null,
            modifier = Modifier.fillMaxWidth(0.94f)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = message.text,
                    fontSize = 13.sp,
                    lineHeight = 19.sp,
                    color = textColor
                )

                // Hazards Highlight
                if (message.hazards.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (isHindi) "पहचाने गए खतरे (Hazards):" else "Identified Hazards:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SafetyRed
                    )
                    message.hazards.forEach { h ->
                        Text("• $h", fontSize = 11.sp, color = textColor, modifier = Modifier.padding(start = 4.dp, top = 1.dp))
                    }
                }

                // Controls Highlight
                if (message.controls.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = if (isHindi) "नियंत्रण उपाय (Controls):" else "Required Controls:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SafetyGreen
                    )
                    message.controls.forEach { c ->
                        Text("• $c", fontSize = 11.sp, color = textColor, modifier = Modifier.padding(start = 4.dp, top = 1.dp))
                    }
                }

                // PPE Highlight
                if (message.ppe.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = if (isHindi) "अनिवार्य पीपीई (PPE):" else "Mandatory PPE:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SafetyBlue
                    )
                    message.ppe.forEach { p ->
                        Text("• $p", fontSize = 11.sp, color = textColor, modifier = Modifier.padding(start = 4.dp, top = 1.dp))
                    }
                }

                // Source Traceability Section
                if (message.citation != null) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, SafetyOrange.copy(alpha = 0.35f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = null,
                                    tint = SafetyOrange,
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (isHindi) "स्रोत (Source):" else "Source:",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SafetyOrange
                                )
                            }
                            Text(
                                text = message.citation.fileName,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "${message.citation.pageOrSlide} • ${message.citation.documentVersion} • ${if (isHindi) "सत्यापित एवं प्रकाशित" else "Approved & Published"}",
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------------------
// 2. AI Hazard Scanner Tab
// -------------------------------------------------------------------------
@Composable
fun AiScannerTab(viewModel: SafetyViewModel, isHindi: Boolean) {
    val scanResult by viewModel.scanResult.collectAsState()
    val isScanning by viewModel.isScanning.collectAsState()
    var selectedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var notesText by remember { mutableStateOf("") }
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            try {
                val inputStream = context.contentResolver.openInputStream(it)
                selectedBitmap = BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) {
                // handle error
            }
        }
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
                        text = if (isHindi) "AI असुरक्षित स्थिति व खतरा स्कैनर" else "AI UNSAFE CONDITION & HAZARD SCANNER",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SafetyOrange
                    )
                    Text(
                        text = if (isHindi) "विजुअल कार्यस्थल स्क्रीनिंग" else "Visual Workplace Screening",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = if (isHindi) "कार्यस्थल, उपकरण सेटअप या मचान की फोटो अपलोड करके सुरक्षा उल्लंघनों की पहचान करें।" else "Upload or photograph a workplace scene, tool setup, or scaffolding to detect possible safety violations.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 2.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    if (selectedBitmap != null) {
                        Image(
                            bitmap = selectedBitmap!!.asImageBitmap(),
                            contentDescription = "Selected Workplace Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .clip(RoundedCornerShape(10.dp))
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = { imagePickerLauncher.launch("image/*") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Icon(imageVector = Icons.Default.PhotoLibrary, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isHindi) "फोटो चुनें" else "Select Photo",
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 12.sp
                            )
                        }

                        Button(
                            onClick = {
                                viewModel.scanWorkplaceImage(selectedBitmap, notesText)
                            },
                            enabled = !isScanning,
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SafetyOrange)
                        ) {
                            if (isScanning) {
                                CircularProgressIndicator(modifier = Modifier.size(16.dp), color = Color.White, strokeWidth = 2.dp)
                            } else {
                                Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = Color.White)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (isHindi) "स्कैन करें" else "Scan Scene",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = notesText,
                        onValueChange = { notesText = it },
                        placeholder = {
                            Text(
                                text = if (isHindi) "वैकल्पिक विवरण (उदा. 'बे 2 में ग्राइंडिंग कार्य')" else "Optional context (e.g. 'Grinding pipe in bay 2')",
                                fontSize = 12.sp
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            }
        }

        // Scan Results Section
        scanResult?.let { result ->
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, SafetyOrange)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = if (isHindi) "AI विजुअल स्क्रीनिंग परिणाम" else "AI VISUAL SCREENING FINDINGS",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = SafetyOrange
                        )
                        Text(
                            text = result.summary,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(top = 4.dp)
                        )

                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = if (isHindi) "पहचाने गए संभावित खतरे (${result.hazards.size}):" else "POSSIBLE HAZARDS DETECTED (${result.hazards.size}):",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = SafetyRed
                        )

                        result.hazards.forEach { finding ->
                            HazardFindingCard(finding, isHindi)
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = if (isHindi) "मानव सत्यापन चेकलिस्ट:" else "HUMAN VERIFICATION CHECKLIST:",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = SafetyGreen
                        )

                        result.humanChecklist.forEach { item ->
                            Row(
                                modifier = Modifier.padding(vertical = 3.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = SafetyGreen,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = item,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.background,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = result.disclaimer,
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HazardFindingCard(finding: HazardFinding, isHindi: Boolean = false) {
    val priorityColor = when (finding.priority.uppercase()) {
        "HIGH" -> SafetyRed
        "MEDIUM" -> SafetyOrange
        else -> SafetyBlue
    }

    Surface(
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.background,
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = finding.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = priorityColor.copy(alpha = 0.2f)
                ) {
                    Text(
                        text = finding.priority.uppercase(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = priorityColor,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${if (isHindi) "असुरक्षित कारण: " else "Why Unsafe: "}${finding.whyUnsafe}",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${if (isHindi) "सुझाया गया नियंत्रण: " else "Suggested Control: "}${finding.suggestedControl}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = SafetyGreen
            )
        }
    }
}

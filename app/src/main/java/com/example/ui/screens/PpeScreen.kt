package com.example.ui.screens

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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.SafetyCheck
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import com.example.data.language.AppLanguage
import com.example.data.model.PpeLibraryItem
import com.example.data.model.PpeRecommendation
import com.example.ui.components.PpeIllustration
import com.example.ui.theme.SafetyAmber
import com.example.ui.theme.SafetyBlue
import com.example.ui.theme.SafetyGreen
import com.example.ui.theme.SafetyOrange
import com.example.ui.theme.SafetyRed
import com.example.ui.theme.SafetyYellow
import com.example.ui.viewmodel.SafetyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PpeScreen(
    viewModel: SafetyViewModel,
    modifier: Modifier = Modifier
) {
    val currentLang by viewModel.currentLanguage.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }

    val tabs = listOf(
        viewModel.getString("tab_ppe_visual"),
        viewModel.getString("tab_ppe_selector"),
        viewModel.getString("tab_ppe_library")
    )

    var selectedRecommendation by remember { mutableStateOf<PpeRecommendation?>(null) }
    var selectedLibraryItem by remember { mutableStateOf<PpeLibraryItem?>(null) }

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
                text = viewModel.getString("ppe_header_title"),
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = SafetyOrange,
                letterSpacing = 1.2.sp
            )
            Text(
                text = viewModel.getString("ppe_header_subtitle"),
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
                    text = {
                        Text(
                            text = title,
                            fontSize = 12.sp,
                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium,
                            color = if (selectedTab == index) SafetyOrange else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                )
            }
        }

        // Tab Content
        when (selectedTab) {
            0 -> VisualPpeGuideTab(viewModel = viewModel)
            1 -> PpeSelectorTab(
                viewModel = viewModel,
                recommendations = viewModel.allPpeRecommendations,
                onSelectRecommendation = { selectedRecommendation = it }
            )
            2 -> PpeLibraryTab(
                viewModel = viewModel,
                library = viewModel.allPpeLibrary,
                onSelectItem = { selectedLibraryItem = it }
            )
        }
    }

    // Recommendation Detail Bottom Sheet
    selectedRecommendation?.let { rec ->
        PpeRecommendationDetailSheet(
            viewModel = viewModel,
            recommendation = rec,
            onDismiss = { selectedRecommendation = null }
        )
    }

    // Library Item Detail Bottom Sheet
    selectedLibraryItem?.let { item ->
        PpeLibraryDetailSheet(
            viewModel = viewModel,
            item = item,
            onDismiss = { selectedLibraryItem = null }
        )
    }
}

@Composable
fun VisualPpeGuideTab(
    viewModel: SafetyViewModel
) {
    val currentLang by viewModel.currentLanguage.collectAsState()
    val isHindi = currentLang == AppLanguage.HINDI

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 14.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // Notice banner
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = SafetyOrange.copy(alpha = 0.12f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = null,
                        tint = SafetyOrange,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = viewModel.getString("ppe_day_label"),
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = SafetyOrange
                        )
                        Text(
                            text = "Standard: EN 397 (Head) • EN 166 (Eye) • EN 149 (Respiratory) • EN 361 (Fall)",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }

        // Animated Interactive PPE Micro-Sticker Grid
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = if (isHindi) "अनिवार्य व्यक्तिगत सुरक्षा उपकरण (PPE)" else "MANDATORY INDUSTRIAL PPE GEAR",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SafetyOrange,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        AnimatedPpeItemBadge("helmet", if (isHindi) "हेलमेट" else "Hard Hat", "EN 397")
                        AnimatedPpeItemBadge("goggles", if (isHindi) "गॉगल्स" else "Goggles", "EN 166")
                        AnimatedPpeItemBadge("gloves", if (isHindi) "दस्ताने" else "Gloves", "EN 388")
                        AnimatedPpeItemBadge("shoes", if (isHindi) "सुरक्षा जूते" else "Boots", "EN 20345")
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        AnimatedPpeItemBadge("face shield", if (isHindi) "फेस शील्ड" else "Face Shield", "EN 166 B")
                        AnimatedPpeItemBadge("respirator", if (isHindi) "रेस्पिरेटर" else "Respirator", "EN 149")
                        AnimatedPpeItemBadge("harness", if (isHindi) "हार्नेस" else "Harness", "EN 361")
                        AnimatedPpeItemBadge("ear protection", if (isHindi) "इयर मफ" else "Ear Muffs", "EN 352")
                    }
                }
            }
        }

        // Section 1: Head & Eye Protection
        item {
            VisualPpeSectionCard(
                type = "helmet",
                title = viewModel.getString("ppe_head_eye_title"),
                description = viewModel.getString("ppe_head_eye_desc"),
                badgeText = "EN 397 / EN 166",
                badgeColor = SafetyBlue,
                items = if (isHindi) listOf(
                    "4-पॉइंट चिन स्ट्रैप युक्त औद्योगिक सुरक्षा हेलमेट (EN 397)",
                    "UV व इम्पैक्ट-प्रतिरोधी पॉलीकार्बोनेट सेफ्टी गॉगल्स (EN 166)",
                    "हाई-एनर्जी ग्राइंडिंग फेस शील्ड (EN 166 B)",
                    "ध्वनि-रोधी इयर मफ्स एवं इयर प्लग्स (NRR 28dB+ / SNR 32dB)"
                ) else listOf(
                    "Industrial Safety Helmet with 4-point chin strap (EN 397)",
                    "UV / Impact-Resistant Polycarbonate Safety Goggles (EN 166)",
                    "Flip-up Grinding Face Shield (EN 166 B / High energy impact)",
                    "Acoustic Ear Defenders (NRR 28dB+ / SNR 32dB)"
                )
            )
        }

        // Section 2: Respiratory Protection
        item {
            VisualPpeSectionCard(
                type = "respirator",
                title = viewModel.getString("ppe_respiratory_title"),
                description = viewModel.getString("ppe_respiratory_desc"),
                badgeText = "EN 149 / NIOSH N95",
                badgeColor = SafetyGreen,
                items = if (isHindi) listOf(
                    "धूल व कण फिल्टर हाफ-मास्क (N95 / FFP2 / FFP3)",
                    "ड्यूल केमिकल कार्ट्रिज रेस्पिरेटर (A2P3 कार्बनिक वाष्प व एसिड गैस)",
                    "प्रवेश से पहले नेगेटिव प्रेशर यूजर सील टेस्ट (Fit Check)",
                    "ऑक्सीजन-कमी क्षेत्र के लिए SCBA सेट (300 Bar ब्रीदिंग एपेरेटस)"
                ) else listOf(
                    "Particulate Filtering Half-Mask (N95 / FFP2 / FFP3)",
                    "Dual Chemical Cartridge Respirator (A2P3 Organic Vapours & Acid Gases)",
                    "Negative Pressure User Seal Check before entry",
                    "Self-Contained Breathing Apparatus (SCBA 300 Bar) for Oxygen-deficient IDLH"
                )
            )
        }

        // Section 3: Fall Protection & Body Gear
        item {
            VisualPpeSectionCard(
                type = "harness",
                title = viewModel.getString("ppe_fall_body_title"),
                description = viewModel.getString("ppe_fall_body_desc"),
                badgeText = "EN 361 / EN 355",
                badgeColor = SafetyOrange,
                items = if (isHindi) listOf(
                    "डॉर्सल डी-रिंग युक्त फुल बॉडी हार्नेस (EN 361)",
                    "शॉक एब्जॉर्बिंग ट्विन-लेग लेन्यार्ड (100% टाई-ऑफ क्षमता)",
                    "केवलर/नाइट्राइल कट लेवल 5 हाई-ग्रिप सेफ्टी ग्लव्स",
                    "स्टील टो व एंटी-पंक्चर मिडसोल सेफ्टी बूट्स (EN ISO 20345 S3)"
                ) else listOf(
                    "Full Body Safety Harness with dorsal D-ring (EN 361)",
                    "Shock Absorbing Twin-leg Lanyard (100% tie-off capability)",
                    "Kevlar / Nitrile Cut Level 5 High-Grip Safety Gloves",
                    "Steel Toe & Anti-Perforation Midsole Safety Boots (EN ISO 20345 S3)"
                )
            )
        }
    }
}

@Composable
fun AnimatedPpeItemBadge(type: String, name: String, standard: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(72.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.background,
            border = androidx.compose.foundation.BorderStroke(1.dp, SafetyOrange.copy(alpha = 0.3f)),
            modifier = Modifier.size(56.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                PpeIllustration(type = type, size = 42.dp)
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = name,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1
        )
        Text(
            text = standard,
            fontSize = 9.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun VisualPpeSectionCard(
    type: String,
    title: String,
    description: String,
    badgeText: String,
    badgeColor: Color,
    items: List<String>
) {
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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(badgeColor.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        PpeIllustration(type = type, size = 38.dp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = badgeText,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = badgeColor
                        )
                    }
                }
            }

            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 10.dp, bottom = 12.dp)
            )

            // Item bullets
            items.forEach { item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 3.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(badgeColor)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = item,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
fun PpeSelectorTab(
    viewModel: SafetyViewModel,
    recommendations: List<PpeRecommendation>,
    onSelectRecommendation: (PpeRecommendation) -> Unit
) {
    var selectedCategoryFilter by remember { mutableStateOf("ALL") }
    val currentLang by viewModel.currentLanguage.collectAsState()
    val isHindi = currentLang == AppLanguage.HINDI

    val categories = listOf("ALL", "HOT WORK", "HEIGHT", "CONFINED", "ELECTRICAL", "CHEMICAL")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 14.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = SafetyBlue.copy(alpha = 0.1f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.SafetyCheck,
                        contentDescription = null,
                        tint = SafetyBlue,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = viewModel.getString("tool_ppe_selector_desc"),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // Filter Pills
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(categories) { cat ->
                    val isSelected = selectedCategoryFilter == cat
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (isSelected) SafetyOrange else MaterialTheme.colorScheme.surface,
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isSelected) SafetyOrange else MaterialTheme.colorScheme.surfaceVariant
                        ),
                        modifier = Modifier.clickable { selectedCategoryFilter = cat }
                    ) {
                        Text(
                            text = cat,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }

        val filteredRecs = recommendations.filter { rec ->
            if (selectedCategoryFilter == "ALL") true
            else when (selectedCategoryFilter) {
                "HOT WORK" -> rec.jobName.contains("Welding", true) || rec.jobName.contains("Grinding", true) || rec.jobName.contains("Hot", true)
                "HEIGHT" -> rec.jobName.contains("Height", true) || rec.jobName.contains("Scaffolding", true)
                "CONFINED" -> rec.jobName.contains("Confined", true) || rec.jobName.contains("Vessel", true)
                "ELECTRICAL" -> rec.jobName.contains("Electrical", true) || rec.jobName.contains("Voltage", true)
                "CHEMICAL" -> rec.jobName.contains("Chemical", true) || rec.jobName.contains("Acid", true)
                else -> true
            }
        }

        items(filteredRecs) { rec ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelectRecommendation(rec) }
                    .testTag("ppe_card_${rec.jobId}"),
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
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                            val illustrationType = when {
                                rec.jobName.contains("Welding", true) || rec.jobName.contains("Grinding", true) -> "face shield"
                                rec.jobName.contains("Height", true) -> "harness"
                                rec.jobName.contains("Confined", true) -> "respirator"
                                rec.jobName.contains("Electrical", true) -> "gloves"
                                rec.jobName.contains("Chemical", true) -> "chemical suit"
                                else -> "helmet"
                            }
                            PpeIllustration(type = illustrationType, size = 36.dp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = rec.jobName,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.SafetyCheck,
                            contentDescription = null,
                            tint = SafetyOrange,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${viewModel.getString("required_gear_label")} (${rec.requiredPpe.size}):",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SafetyBlue
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    rec.requiredPpe.take(3).forEach { ppe ->
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 1.dp)) {
                            Box(modifier = Modifier.size(5.dp).clip(CircleShape).background(SafetyOrange))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = ppe, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }

                    if (rec.requiredPpe.size > 3) {
                        Text(
                            text = "+ ${rec.requiredPpe.size - 3} more items",
                            fontSize = 11.sp,
                            color = SafetyOrange,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PpeRecommendationDetailSheet(
    viewModel: SafetyViewModel,
    recommendation: PpeRecommendation,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(top = 10.dp, bottom = 48.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "TASK PPE SPECIFICATION",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SafetyOrange
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Text(
                    text = recommendation.jobName,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            // Required PPE List
            item {
                SectionCard(title = viewModel.getString("required_gear_label"), accentColor = SafetyGreen) {
                    recommendation.requiredPpe.forEach { ppe ->
                        BulletItem(text = ppe, bulletColor = SafetyGreen)
                    }
                }
            }

            // Why Each Item
            if (recommendation.whyItems.isNotEmpty()) {
                item {
                    SectionCard(title = viewModel.getString("why_required_label"), accentColor = SafetyBlue) {
                        recommendation.whyItems.forEach { (item, why) ->
                            Column(modifier = Modifier.padding(vertical = 4.dp)) {
                                Text(text = item, fontWeight = FontWeight.Bold, fontSize = 12.5.sp, color = MaterialTheme.colorScheme.onSurface)
                                Text(text = why, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            }

            // Site Warnings
            if (recommendation.siteWarnings.isNotBlank()) {
                item {
                    SectionCard(title = viewModel.getString("critical_warning_label"), accentColor = SafetyRed) {
                        Text(text = recommendation.siteWarnings, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface)
                    }
                }
            }

            // Inspection Checklist
            if (recommendation.inspectionChecklist.isNotEmpty()) {
                item {
                    SectionCard(title = viewModel.getString("pre_use_inspect_label"), accentColor = SafetyAmber) {
                        recommendation.inspectionChecklist.forEach { chk ->
                            BulletItem(text = chk, bulletColor = SafetyAmber)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PpeLibraryTab(
    viewModel: SafetyViewModel,
    library: List<PpeLibraryItem>,
    onSelectItem: (PpeLibraryItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 14.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(library) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelectItem(item) },
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
                            color = SafetyBlue.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = item.category.uppercase(),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = SafetyBlue,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = item.purpose,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PpeLibraryDetailSheet(
    viewModel: SafetyViewModel,
    item: PpeLibraryItem,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(top = 10.dp, bottom = 48.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.category.uppercase(),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SafetyBlue
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Text(
                    text = item.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            item {
                SectionCard(title = "Primary Purpose", accentColor = SafetyBlue) {
                    Text(text = item.purpose, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
                }
            }

            item {
                SectionCard(title = "Usage Guidelines") {
                    Text(text = viewModel.getString("when_to_use_label"), fontWeight = FontWeight.Bold, fontSize = 12.sp, color = SafetyGreen)
                    item.whenToUse.forEach { u -> BulletItem(text = u, bulletColor = SafetyGreen) }
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = viewModel.getString("when_not_to_use_label"), fontWeight = FontWeight.Bold, fontSize = 12.sp, color = SafetyRed)
                    item.whenNotToUse.forEach { nu -> BulletItem(text = nu, bulletColor = SafetyRed) }
                }
            }

            item {
                SectionCard(title = "Inspection & Replacement", accentColor = SafetyAmber) {
                    Text(text = "INSPECTION POINTS:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = SafetyAmber)
                    item.inspectionPoints.forEach { ip -> BulletItem(text = ip, bulletColor = SafetyAmber) }
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "REPLACEMENT CRITERIA:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = SafetyRed)
                    item.replacementCriteria.forEach { rc -> BulletItem(text = rc, bulletColor = SafetyRed) }
                }
            }

            item {
                SectionCard(title = viewModel.getString("fitting_instructions_label"), accentColor = SafetyGreen) {
                    item.correctUsage.forEach { cu -> BulletItem(text = cu, bulletColor = SafetyGreen) }
                }
            }
        }
    }
}

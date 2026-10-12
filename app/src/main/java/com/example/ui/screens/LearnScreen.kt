package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Elevator
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Hardware
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.language.AppLanguage
import com.example.data.model.QuizQuestion
import com.example.data.model.SafetySignItem
import com.example.data.model.SafetyTopic
import com.example.data.model.ToolboxTalk
import com.example.ui.theme.SafetyAmber
import com.example.ui.theme.SafetyBlue
import com.example.ui.theme.SafetyGreen
import com.example.ui.theme.SafetyOrange
import com.example.ui.theme.SafetyRed
import com.example.ui.theme.SafetyYellow
import com.example.ui.viewmodel.SafetyViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearnScreen(
    viewModel: SafetyViewModel,
    modifier: Modifier = Modifier
) {
    val currentLanguage by viewModel.currentLanguage.collectAsStateWithLifecycle()
    val isHindi = currentLanguage == AppLanguage.HINDI

    var selectedMainTab by remember { mutableIntStateOf(0) }
    val mainTabs = listOf(
        viewModel.getString("tab_topics"),
        viewModel.getString("tab_toolbox"),
        viewModel.getString("tab_signs"),
        viewModel.getString("saved_quizzes")
    )

    var selectedTopicForDetail by remember { mutableStateOf<SafetyTopic?>(null) }
    var selectedTalkForDetail by remember { mutableStateOf<ToolboxTalk?>(null) }

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
                text = viewModel.getString("learn_header_title"),
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = SafetyOrange,
                letterSpacing = 1.2.sp
            )
            Text(
                text = viewModel.getString("learn_header_subtitle"),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onBackground
            )
        }


        // Main Tab Row
        TabRow(
            selectedTabIndex = selectedMainTab,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = SafetyOrange
        ) {
            mainTabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedMainTab == index,
                    onClick = { selectedMainTab = index },
                    text = {
                        Text(
                            text = title,
                            fontSize = 12.sp,
                            fontWeight = if (selectedMainTab == index) FontWeight.Bold else FontWeight.Medium,
                            color = if (selectedMainTab == index) SafetyOrange else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                )
            }
        }

        // Sub-Tab Content
        when (selectedMainTab) {
            0 -> SafetyTopicsTab(
                topics = viewModel.allTopics,
                isHindi = isHindi,
                onSelectTopic = { selectedTopicForDetail = it }
            )
            1 -> ToolboxTalksTab(
                talks = viewModel.allToolboxTalks,
                isHindi = isHindi,
                onSelectTalk = { selectedTalkForDetail = it }
            )
            2 -> SafetySignsTab(signs = viewModel.allSafetySigns, isHindi = isHindi)
            3 -> SafetyQuizzesTab(
                topics = viewModel.allTopics,
                onRecordScore = { cat, score, total -> viewModel.recordQuizResult(cat, score, total) }
            )
        }
    }

    // Safety Topic Detailed Sheet
    selectedTopicForDetail?.let { topic ->
        SafetyTopicDetailSheet(
            topic = topic,
            isHindi = isHindi,
            onDismiss = { selectedTopicForDetail = null },
            onRecordScore = { score, total ->
                viewModel.recordQuizResult(topic.title, score, total)
            }
        )
    }

    // Toolbox Talk Detailed Sheet
    selectedTalkForDetail?.let { talk ->
        ToolboxTalkDetailSheet(
            talk = talk,
            isHindi = isHindi,
            onDismiss = { selectedTalkForDetail = null }
        )
    }
}

@Composable
fun SafetyTopicsTab(
    topics: List<SafetyTopic>,
    isHindi: Boolean,
    onSelectTopic: (SafetyTopic) -> Unit
) {
    var selectedCategoryIndex by remember { mutableStateOf(0) }
    val categoriesEng = listOf("All", "Basic Safety", "High-Risk Work", "Industrial Safety", "Emergency")
    val categoriesHin = listOf("सभी", "बुनियादी सुरक्षा", "उच्च जोखिम कार्य", "औद्योगिक सुरक्षा", "आपातकालीन")
    val categories = if (isHindi) categoriesHin else categoriesEng

    val filtered = if (selectedCategoryIndex == 0) {
        topics
    } else {
        val engCategory = categoriesEng[selectedCategoryIndex]
        val hinCategory = categoriesHin[selectedCategoryIndex]
        topics.filter { 
            it.category.equals(engCategory, ignoreCase = true) || 
            it.category.contains(hinCategory, ignoreCase = true) ||
            it.category.contains(engCategory.take(4), ignoreCase = true)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Category Filter Chips
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 4.dp)
            ) {
                items(categories.size) { idx ->
                    val cat = categories[idx]
                    FilterChip(
                        selected = selectedCategoryIndex == idx,
                        onClick = { selectedCategoryIndex = idx },
                        label = { Text(cat, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = SafetyOrange,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }

        items(filtered) { topic ->
            SafetyTopicCard(topic = topic, isHindi = isHindi, onClick = { onSelectTopic(topic) })
        }
    }
}

@Composable
fun SafetyTopicCard(
    topic: SafetyTopic,
    isHindi: Boolean,
    onClick: () -> Unit
) {
    val categoryColor = when {
        topic.category.contains("High-Risk", true) || topic.category.contains("उच्च जोखिम", true) -> SafetyRed
        topic.category.contains("Industrial", true) || topic.category.contains("औद्योगिक", true) -> SafetyAmber
        topic.category.contains("Emergency", true) || topic.category.contains("आपातकालीन", true) -> SafetyOrange
        else -> SafetyBlue
    }

    val iconVector = when (topic.iconName) {
        "Elevator" -> Icons.Default.Elevator
        "Lock" -> Icons.Default.Lock
        "Hardware" -> Icons.Default.Hardware
        "Science" -> Icons.Default.Science
        "Tune" -> Icons.Default.Tune
        else -> Icons.Default.School
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("topic_card_${topic.id}"),
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
                        text = topic.category.uppercase(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = categoryColor,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                    )
                }

                Icon(
                    imageVector = iconVector,
                    contentDescription = null,
                    tint = categoryColor,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = topic.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = topic.summary,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isHindi) "${topic.quiz.size} क्विज प्रश्न शामिल" else "${topic.quiz.size} quiz questions included",
                    fontSize = 11.sp,
                    color = SafetyGreen,
                    fontWeight = FontWeight.Medium
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = if (isHindi) "अध्याय पढ़ें" else "Read Guide",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = SafetyOrange
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        tint = SafetyOrange,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SafetyTopicDetailSheet(
    topic: SafetyTopic,
    isHindi: Boolean,
    onDismiss: () -> Unit,
    onRecordScore: (Int, Int) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showTopicQuiz by remember { mutableStateOf(false) }

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
                        text = topic.category.uppercase(),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SafetyOrange
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Text(
                    text = topic.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            // What is it
            item {
                SectionCard(title = if (isHindi) "1. यह क्या है?" else "1. What is it?") {
                    Text(text = topic.whatIsIt, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
                }
            }

            // Why Dangerous
            item {
                SectionCard(title = if (isHindi) "2. यह खतरनाक क्यों है?" else "2. Why is it Dangerous?", accentColor = SafetyRed) {
                    Text(text = topic.whyDangerous, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
                }
            }

            // Common Hazards & Consequences
            item {
                SectionCard(title = if (isHindi) "3. सामान्य खतरे व संभावित विफलताएं" else "3. Common Hazards & What Can Go Wrong", accentColor = SafetyAmber) {
                    topic.commonHazards.forEach { hazard ->
                        BulletItem(text = hazard, bulletColor = SafetyAmber)
                    }
                    if (topic.consequences.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = if (isHindi) "संभावित परिणाम:" else "Potential Consequences:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = SafetyRed)
                        topic.consequences.forEach { consequence ->
                            BulletItem(text = consequence, bulletColor = SafetyRed)
                        }
                    }
                }
            }

            // How to Prevent (Hierarchy of Controls)
            item {
                SectionCard(title = if (isHindi) "4. रोकथाम के उपाय (नियंत्रण पदानुक्रम)" else "4. How to Prevent (Controls Hierarchy)", accentColor = SafetyGreen) {
                    topic.howToPrevent.forEach { control ->
                        BulletItem(text = control, bulletColor = SafetyGreen)
                    }
                }
            }

            // Required PPE
            item {
                SectionCard(title = if (isHindi) "5. आवश्यक व्यक्तिगत सुरक्षा उपकरण (PPE)" else "5. Required Personal Protective Equipment", accentColor = SafetyBlue) {
                    topic.requiredPpe.forEach { ppe ->
                        BulletItem(text = ppe, bulletColor = SafetyBlue)
                    }
                }
            }

            // Work Checklists: Before, During, After
            item {
                SectionCard(title = if (isHindi) "6. मानक कार्य संचालन सावधानियां (SOP)" else "6. Standard Operating Precautions") {
                    Text(text = if (isHindi) "कार्य शुरू करने से पहले:" else "BEFORE STARTING WORK:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = SafetyOrange)
                    topic.beforeWorkChecklist.forEach { item ->
                        BulletItem(text = item, bulletColor = SafetyOrange)
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = if (isHindi) "कार्य के दौरान:" else "DURING WORK:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = SafetyBlue)
                    topic.duringWorkPrecautions.forEach { item ->
                        BulletItem(text = item, bulletColor = SafetyBlue)
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = if (isHindi) "कार्य समाप्ति व सफाई के बाद:" else "AFTER WORK SHUTDOWN & CLEANUP:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = SafetyGreen)
                    topic.afterWorkPoints.forEach { item ->
                        BulletItem(text = item, bulletColor = SafetyGreen)
                    }
                }
            }

            // DOs and DONTs
            item {
                SectionCard(title = if (isHindi) "7. क्या करें और क्या न करें (DOs & DON'Ts)" else "7. DOs and DON'Ts Summary") {
                    Text(text = if (isHindi) "✓ हमेशा करें:" else "✓ ALWAYS DO:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = SafetyGreen)
                    topic.doList.forEach { d ->
                        BulletItem(text = d, bulletColor = SafetyGreen)
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = if (isHindi) "✗ कभी न करें:" else "✗ NEVER DO:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = SafetyRed)
                    topic.dontList.forEach { dont ->
                        BulletItem(text = dont, bulletColor = SafetyRed)
                    }
                }
            }

            // Topic Quiz Section
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, SafetyOrange.copy(alpha = 0.4f))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = if (isHindi) "अपना ज्ञान परखें" else "TEST YOUR KNOWLEDGE",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = SafetyOrange
                        )
                        Text(
                            text = if (isHindi) "${topic.quiz.size} अभ्यास प्रश्न इस विषय पर उपलब्ध" else "${topic.quiz.size} Practice Questions on this topic",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(top = 2.dp)
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = { showTopicQuiz = !showTopicQuiz },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = SafetyOrange),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                if (showTopicQuiz) (if (isHindi) "क्विज छुपाएं" else "Hide Quiz")
                                else (if (isHindi) "अभ्यास क्विज शुरू करें" else "Start Practice Quiz"),
                                fontWeight = FontWeight.Bold
                            )
                        }

                        if (showTopicQuiz) {
                            Spacer(modifier = Modifier.height(12.dp))
                            TopicQuizRunner(
                                questions = topic.quiz,
                                isHindi = isHindi,
                                onFinishQuiz = { score, total ->
                                    onRecordScore(score, total)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SectionCard(
    title: String,
    accentColor: Color = SafetyOrange,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = accentColor,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            content()
        }
    }
}

@Composable
fun BulletItem(text: String, bulletColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .padding(top = 6.dp)
                .size(6.dp)
                .clip(CircleShape)
                .background(bulletColor)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontSize = 12.5.sp,
            lineHeight = 17.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun TopicQuizRunner(
    questions: List<QuizQuestion>,
    isHindi: Boolean = false,
    onFinishQuiz: (Int, Int) -> Unit
) {
    var answers by remember { mutableStateOf<Map<Int, Int>>(emptyMap()) }
    var submitted by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        questions.forEachIndexed { qIdx, question ->
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "${if (isHindi) "प्रश्न" else "Q"}${qIdx + 1}: ${question.question}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    question.options.forEachIndexed { oIdx, opt ->
                        val isSelected = answers[qIdx] == oIdx
                        val isCorrect = oIdx == question.correctAnswerIndex
                        val optBg = when {
                            !submitted && isSelected -> SafetyOrange.copy(alpha = 0.2f)
                            submitted && isCorrect -> SafetyGreen.copy(alpha = 0.25f)
                            submitted && isSelected && !isCorrect -> SafetyRed.copy(alpha = 0.25f)
                            else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = optBg,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) SafetyOrange else Color.Transparent
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp)
                                .clickable(enabled = !submitted) {
                                    val map = answers.toMutableMap()
                                    map[qIdx] = oIdx
                                    answers = map
                                }
                        ) {
                            Text(
                                text = opt,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }

                    if (submitted) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "${if (isHindi) "व्याख्या: " else "Explanation: "}${question.explanation}",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        if (!submitted) {
            Button(
                onClick = {
                    submitted = true
                    var correct = 0
                    questions.forEachIndexed { i, q ->
                        if (answers[i] == q.correctAnswerIndex) correct++
                    }
                    onFinishQuiz(correct, questions.size)
                },
                enabled = answers.size == questions.size,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = SafetyGreen)
            ) {
                Text(if (isHindi) "उत्तर सबमिट करें" else "Submit Practice Answers", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun ToolboxTalksTab(
    talks: List<ToolboxTalk>,
    isHindi: Boolean,
    onSelectTalk: (ToolboxTalk) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 96.dp),
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
                        imageVector = Icons.Default.Forum,
                        contentDescription = null,
                        tint = SafetyBlue,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = if (isHindi) "टूलबॉक्स टॉक्स कार्य शुरू करने से पहले 5-10 मिनट की संक्षिप्त सुरक्षा वार्ताएं हैं जो श्रमिकों को दैनिक खतरों के प्रति सचेत करती हैं।" else "Toolbox talks are short 5-10 minute pre-shift safety conversations designed to focus teams on daily hazards before starting work.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        items(talks) { talk ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelectTalk(talk) },
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
                                text = talk.duration.uppercase(),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = SafetyBlue,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            )
                        }

                        Text(
                            text = talk.category,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = talk.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = talk.objective,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isHindi) "${talk.workerQuestions.size} चर्चा के प्रश्न" else "${talk.workerQuestions.size} discussion questions",
                            fontSize = 11.sp,
                            color = SafetyOrange,
                            fontWeight = FontWeight.Medium
                        )

                        Text(
                            text = if (isHindi) "टॉक खोलें →" else "Open Talk →",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = SafetyBlue
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolboxTalkDetailSheet(
    talk: ToolboxTalk,
    isHindi: Boolean,
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
                        text = if (isHindi) "टूलबॉक्स टॉक • ${talk.duration}" else "TOOLBOX TALK • ${talk.duration}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SafetyBlue
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Text(
                    text = talk.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            item {
                SectionCard(title = if (isHindi) "उद्देश्य" else "Objective", accentColor = SafetyBlue) {
                    Text(text = talk.objective, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
                }
            }

            item {
                SectionCard(title = if (isHindi) "प्रमुख खतरे" else "Key Hazards", accentColor = SafetyRed) {
                    talk.keyHazards.forEach { h ->
                        BulletItem(text = h, bulletColor = SafetyRed)
                    }
                }
            }

            item {
                SectionCard(title = if (isHindi) "चर्चा के बिंदु" else "Discussion Points", accentColor = SafetyAmber) {
                    talk.discussionPoints.forEach { p ->
                        BulletItem(text = p, bulletColor = SafetyAmber)
                    }
                }
            }

            item {
                SectionCard(title = if (isHindi) "सुरक्षित बनाम असुरक्षित कार्यप्रणाली" else "Safe vs Unsafe Practices") {
                    Text(text = if (isHindi) "सुरक्षित कार्य पद्धतियां:" else "SAFE WORK PRACTICES:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = SafetyGreen)
                    talk.safePractices.forEach { sp ->
                        BulletItem(text = sp, bulletColor = SafetyGreen)
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = if (isHindi) "वर्जित असुरक्षित व्यवहार:" else "UNSAFE BEHAVIORS TO STOP:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = SafetyRed)
                    talk.unsafePractices.forEach { up ->
                        BulletItem(text = up, bulletColor = SafetyRed)
                    }
                }
            }

            item {
                SectionCard(title = if (isHindi) "श्रमिकों से पूछने योग्य प्रश्न" else "Questions for Workers", accentColor = SafetyOrange) {
                    talk.workerQuestions.forEach { q ->
                        BulletItem(text = q, bulletColor = SafetyOrange)
                    }
                }
            }

            item {
                SectionCard(title = if (isHindi) "सुपरवाइजर फील्ड नोट्स" else "Supervisor Field Notes", accentColor = MaterialTheme.colorScheme.primary) {
                    Text(text = talk.supervisorNotes, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

@Composable
fun SafetySignsTab(signs: List<SafetySignItem>, isHindi: Boolean = false) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = if (isHindi) "मानक सुरक्षा संकेत एवं प्रतीक" else "STANDARD SAFETY SIGNS & SYMBOLS",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = SafetyOrange,
                letterSpacing = 1.sp
            )
        }

        items(signs) { sign ->
            val signColor = when (sign.category) {
                "Mandatory Action" -> SafetyBlue
                "Warning" -> SafetyAmber
                "Prohibition" -> SafetyRed
                "Emergency / First Aid" -> SafetyGreen
                else -> MaterialTheme.colorScheme.primary
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
                            color = signColor.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = sign.category.uppercase(),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = signColor,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            )
                        }

                        Text(
                            text = sign.colorCode,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = sign.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "${if (isHindi) "अर्थ: " else "Meaning: "}${sign.meaning}",
                        fontSize = 12.5.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${if (isHindi) "उपयोग: " else "Where Used: "}${sign.whereUsed}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(6.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.background,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "${if (isHindi) "आवश्यक कार्रवाई: " else "Required Action: "}${sign.workerAction}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = SafetyOrange,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SafetyQuizzesTab(
    topics: List<SafetyTopic>,
    onRecordScore: (String, Int, Int) -> Unit
) {
    val allQuizzes = topics.flatMap { topic ->
        topic.quiz.map { q -> Pair(topic.title, q) }
    }

    var activeQuizQuestions by remember { mutableStateOf<List<Pair<String, QuizQuestion>>>(emptyList()) }
    var selectedAnswers by remember { mutableStateOf<Map<Int, Int>>(emptyMap()) }
    var quizSubmitted by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        if (activeQuizQuestions.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, SafetyOrange.copy(alpha = 0.5f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "KNOWLEDGE EVALUATION QUIZ",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = SafetyOrange
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Take a 5-Question Industrial Safety Test",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Questions are drawn from LOTO, Working at Height, Confined Space, Chemical Safety, and Hierarchy of Controls.",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 4.dp)
                        )

                        Spacer(modifier = Modifier.height(14.dp))
                        Button(
                            onClick = {
                                activeQuizQuestions = allQuizzes.shuffled().take(5)
                                selectedAnswers = emptyMap()
                                quizSubmitted = false
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = SafetyOrange),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Start 5-Question Test", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        } else {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "SAFETY TEST IN PROGRESS (${activeQuizQuestions.size} Questions)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SafetyOrange
                    )

                    TextButton(onClick = {
                        activeQuizQuestions = emptyList()
                        selectedAnswers = emptyMap()
                        quizSubmitted = false
                    }) {
                        Text("Reset Test", color = SafetyRed, fontSize = 12.sp)
                    }
                }
            }

            items(activeQuizQuestions.size) { idx ->
                val (category, q) = activeQuizQuestions[idx]
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Question ${idx + 1}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = SafetyOrange
                            )
                            Text(
                                text = category,
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = q.question,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        q.options.forEachIndexed { oIdx, opt ->
                            val isSelected = selectedAnswers[idx] == oIdx
                            val isCorrect = oIdx == q.correctAnswerIndex
                            val optBg = when {
                                !quizSubmitted && isSelected -> SafetyOrange.copy(alpha = 0.2f)
                                quizSubmitted && isCorrect -> SafetyGreen.copy(alpha = 0.25f)
                                quizSubmitted && isSelected && !isCorrect -> SafetyRed.copy(alpha = 0.25f)
                                else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                            }

                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = optBg,
                                border = androidx.compose.foundation.BorderStroke(
                                    1.dp,
                                    if (isSelected) SafetyOrange else Color.Transparent
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 3.dp)
                                    .clickable(enabled = !quizSubmitted) {
                                        val map = selectedAnswers.toMutableMap()
                                        map[idx] = oIdx
                                        selectedAnswers = map
                                    }
                            ) {
                                Text(
                                    text = opt,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                        }

                        if (quizSubmitted) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = q.explanation,
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            item {
                if (!quizSubmitted) {
                    Button(
                        onClick = {
                            quizSubmitted = true
                            var score = 0
                            activeQuizQuestions.forEachIndexed { i, pair ->
                                if (selectedAnswers[i] == pair.second.correctAnswerIndex) score++
                            }
                            onRecordScore("General Safety Assessment", score, activeQuizQuestions.size)
                        },
                        enabled = selectedAnswers.size == activeQuizQuestions.size,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = SafetyGreen),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Submit Evaluation", fontWeight = FontWeight.Bold)
                    }
                } else {
                    var score = 0
                    activeQuizQuestions.forEachIndexed { i, pair ->
                        if (selectedAnswers[i] == pair.second.correctAnswerIndex) score++
                    }
                    val pct = (score.toDouble() / activeQuizQuestions.size * 100).toInt()

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (pct >= 80) SafetyGreen.copy(alpha = 0.2f) else SafetyAmber.copy(alpha = 0.2f)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Your Score: $score / ${activeQuizQuestions.size} ($pct%)",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = if (pct >= 80) "Great job! Strong industrial safety awareness." else "Review the safety handbook topics above and retry.",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

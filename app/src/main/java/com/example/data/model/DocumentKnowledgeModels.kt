package com.example.data.model

/**
 * Citation representing the exact authoritative document origin.
 */
data class SourceCitation(
    val documentTitle: String,
    val fileName: String,
    val pageOrSlide: String,
    val documentVersion: String = "Rev 2026.1",
    val lastUpdatedDate: String = "2026-08-15",
    val contentStatus: String = "Approved & Published"
) {
    fun displayEnglish(): String = "Source: $fileName • $pageOrSlide ($documentVersion)"
    fun displayHindi(): String = "स्रोत: $fileName • $pageOrSlide ($documentVersion)"
}

/**
 * Document Page / Slide representation inside the Authoritative Document Library.
 */
data class DocumentPage(
    val pageNumber: Int,
    val title: String,
    val titleHi: String,
    val summary: String,
    val summaryHi: String,
    val keyPoints: List<String>,
    val keyPointsHi: List<String>,
    val extractedTables: List<String> = emptyList(),
    val extractedTablesHi: List<String> = emptyList(),
    val safetyWarning: String = "",
    val safetyWarningHi: String = ""
)

/**
 * Authoritative Safety Document ingested by the backend system.
 */
data class AuthoritativeDocument(
    val id: String,
    val title: String,
    val titleHi: String,
    val fileName: String,
    val fileType: String, // "PDF" or "PPT"
    val version: String,
    val approvedDate: String,
    val status: String = "Approved & Published",
    val statusHi: String = "सत्यापित एवं प्रकाशित",
    val totalPagesOrSlides: Int,
    val category: String,
    val categoryHi: String,
    val summary: String,
    val summaryHi: String,
    val pages: List<DocumentPage>
)

/**
 * Content Type A: Learning Article extracted from documents
 */
data class DocLearningArticle(
    val id: String,
    val title: String,
    val titleHi: String,
    val category: String,
    val categoryHi: String,
    val summary: String,
    val summaryHi: String,
    val whatIsIt: String,
    val whatIsItHi: String,
    val whyDangerous: String,
    val whyDangerousHi: String,
    val commonHazards: List<String>,
    val commonHazardsHi: List<String>,
    val controls: List<String>,
    val controlsHi: List<String>,
    val ppe: List<String>,
    val ppeHi: List<String>,
    val citation: SourceCitation
)

/**
 * Content Type B: Micro Training Module
 */
data class DocMicroTrainingModule(
    val id: String,
    val title: String,
    val titleHi: String,
    val learningObjective: String,
    val learningObjectiveHi: String,
    val shortExplanation: String,
    val shortExplanationHi: String,
    val hazards: List<String>,
    val hazardsHi: List<String>,
    val controls: List<String>,
    val controlsHi: List<String>,
    val ppe: List<String>,
    val ppeHi: List<String>,
    val doList: List<String>,
    val doListHi: List<String>,
    val dontList: List<String>,
    val dontListHi: List<String>,
    val visualIllustration: String, // icon identifier
    val quickQuestion: QuizQuestion,
    val quickQuestionHi: QuizQuestion,
    val citation: SourceCitation
)

/**
 * Content Type C & Reference: Safety Reference Card
 */
data class DocReferenceCard(
    val id: String,
    val parameter: String,
    val parameterHi: String,
    val category: String,
    val categoryHi: String,
    val safeRange: String,
    val safeRangeHi: String,
    val warningLimit: String,
    val warningLimitHi: String,
    val dangerThreshold: String,
    val dangerThresholdHi: String,
    val unit: String,
    val criticalWarning: String,
    val criticalWarningHi: String,
    val citation: SourceCitation
)

/**
 * Content Type D: PPE Guidance
 */
data class DocPpeGuidance(
    val id: String,
    val jobName: String,
    val jobNameHi: String,
    val gearList: List<String>,
    val gearListHi: List<String>,
    val rationale: String,
    val rationaleHi: String,
    val limitations: List<String>,
    val limitationsHi: List<String>,
    val inspectionPoints: List<String>,
    val inspectionPointsHi: List<String>,
    val citation: SourceCitation
)

/**
 * Content Type E & I: Structured Inspection & Checklist Template
 */
data class DocInspectionItem(
    val id: String,
    val category: String, // e.g. "PPE", "Housekeeping", "Electrical", "Fire Safety", "Work at Height"
    val categoryHi: String,
    val question: String,
    val questionHi: String,
    val criteria: String,
    val criteriaHi: String
)

data class DocInspectionTemplate(
    val id: String,
    val title: String,
    val titleHi: String,
    val documentSource: String,
    val version: String,
    val description: String,
    val descriptionHi: String,
    val items: List<DocInspectionItem>,
    val citation: SourceCitation
)

/**
 * Content Type F & G: JSA / HIRA Template
 */
data class DocJobRiskTemplate(
    val id: String,
    val title: String,
    val titleHi: String,
    val type: String, // "JSA" or "HIRA"
    val activity: String,
    val activityHi: String,
    val initialRiskScore: Int,
    val keyHazards: List<String>,
    val keyHazardsHi: List<String>,
    val requiredControls: List<String>,
    val requiredControlsHi: List<String>,
    val requiredPpe: List<String>,
    val requiredPpeHi: List<String>,
    val citation: SourceCitation
)

/**
 * Content Type H: Toolbox Talk Template
 */
data class DocToolboxTalkTemplate(
    val id: String,
    val title: String,
    val titleHi: String,
    val duration: String,
    val durationHi: String,
    val objective: String,
    val objectiveHi: String,
    val hazards: List<String>,
    val hazardsHi: List<String>,
    val discussionPoints: List<String>,
    val discussionPointsHi: List<String>,
    val safePractices: List<String>,
    val safePracticesHi: List<String>,
    val unsafePractices: List<String>,
    val unsafePracticesHi: List<String>,
    val citation: SourceCitation
)

/**
 * Content Type M: Safety FAQ
 */
data class DocSafetyFaq(
    val id: String,
    val question: String,
    val questionHi: String,
    val answer: String,
    val answerHi: String,
    val hazards: List<String>,
    val hazardsHi: List<String>,
    val controls: List<String>,
    val controlsHi: List<String>,
    val ppe: List<String>,
    val ppeHi: List<String>,
    val citation: SourceCitation
)

/**
 * Content Type N: Knowledge Search Grouped Result
 */
enum class DocSearchCategory {
    LEARN, PPE, CHECKLIST, TOOLS, TEMPLATES, REFERENCE, ORIGINAL_DOCUMENTS
}

data class DocSearchResult(
    val id: String,
    val title: String,
    val titleHi: String,
    val snippet: String,
    val snippetHi: String,
    val category: DocSearchCategory,
    val categoryLabel: String,
    val categoryLabelHi: String,
    val citation: SourceCitation,
    val targetType: String,
    val targetId: String
)

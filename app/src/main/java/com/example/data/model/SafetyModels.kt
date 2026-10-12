package com.example.data.model

data class DailySafetyContent(
    val dayOfWeek: String,
    val topicTitle: String,
    val topicSummary: String,
    val safetyTip: String,
    val hazardOfTheDay: String,
    val ppeOfTheDay: String,
    val toolboxTalkTitle: String,
    val dailyQuiz: QuizQuestion
)

data class QuizQuestion(
    val id: String = "",
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val explanation: String
)

data class SafetyTopic(
    val id: String,
    val title: String,
    val category: String, // Basic Safety, Industrial Safety, High-Risk Work, Emergency
    val iconName: String,
    val summary: String,
    val whatIsIt: String,
    val whyDangerous: String,
    val commonHazards: List<String>,
    val consequences: List<String>,
    val howToPrevent: List<String>,
    val requiredPpe: List<String>,
    val beforeWorkChecklist: List<String>,
    val duringWorkPrecautions: List<String>,
    val afterWorkPoints: List<String>,
    val doList: List<String>,
    val dontList: List<String>,
    val quiz: List<QuizQuestion>
)

data class ToolboxTalk(
    val id: String,
    val title: String,
    val category: String,
    val duration: String, // "5 Minutes" or "10 Minutes"
    val objective: String,
    val keyHazards: List<String>,
    val discussionPoints: List<String>,
    val requiredPpe: List<String>,
    val safePractices: List<String>,
    val unsafePractices: List<String>,
    val workerQuestions: List<String>,
    val supervisorNotes: String
)

data class PpeRecommendation(
    val jobId: String,
    val jobName: String,
    val iconName: String,
    val requiredPpe: List<String>,
    val whyItems: Map<String, String>,
    val siteWarnings: String,
    val inspectionChecklist: List<String>
)

data class PpeLibraryItem(
    val id: String,
    val name: String,
    val category: String,
    val iconName: String,
    val purpose: String,
    val hazardsControlled: List<String>,
    val whenToUse: List<String>,
    val whenNotToUse: List<String>,
    val inspectionPoints: List<String>,
    val limitations: List<String>,
    val replacementCriteria: List<String>,
    val correctUsage: List<String>
)

data class SafetyLimitItem(
    val category: String, // Confined Space, Noise, Heat, Electrical, Working at Height, Lifting
    val parameter: String,
    val safeRange: String,
    val warningLimit: String,
    val dangerThreshold: String,
    val unit: String,
    val referenceSource: String,
    val referenceDate: String,
    val notes: String
)

data class ChemicalItem(
    val id: String,
    val name: String,
    val formula: String,
    val cas: String,
    val ghsPictograms: List<String>,
    val hazards: List<String>,
    val requiredPpe: List<String>,
    val storage: String,
    val incompatibilities: List<String>,
    val firstAid: String,
    val spillResponse: String,
    val fireResponse: String,
    val sdsNote: String
)

data class ChemicalCompatibilityResult(
    val chemicalA: String,
    val chemicalB: String,
    val status: String, // "COMPATIBLE", "CAUTION / REVIEW", "INCOMPATIBLE - DANGER"
    val severityLevel: String, // "Safe", "Warning", "Critical"
    val rationale: String,
    val reactionHazard: String,
    val requiredSeparation: String
)

data class ChecklistTemplate(
    val id: String,
    val title: String,
    val category: String,
    val iconName: String,
    val items: List<String>
)

enum class CheckItemStatus {
    UNCHECKED, PASS, FAIL, NA
}

data class ActiveChecklistItem(
    val id: Int,
    val text: String,
    val status: CheckItemStatus = CheckItemStatus.UNCHECKED,
    val comment: String = ""
)

data class SafetySignItem(
    val id: String,
    val name: String,
    val category: String, // Mandatory, Warning, Prohibition, Emergency, Fire, GHS
    val colorCode: String,
    val meaning: String,
    val whereUsed: String,
    val workerAction: String
)

data class JsaStepModel(
    val stepNumber: Int,
    val jobStep: String,
    val hazard: String,
    val consequence: String,
    val existingControls: String,
    val additionalControls: String,
    val ppe: String,
    val likelihood: Int = 2,
    val severity: Int = 3,
    val riskScore: Int = likelihood * severity
)

enum class SearchResultType {
    TOPIC, TOOL, CHECKLIST, PPE, CHEMICAL, LIMIT, TALK
}

data class SearchResultItem(
    val title: String,
    val subtitle: String,
    val category: String,
    val type: SearchResultType,
    val targetId: String
)

package com.example.data.repository

import com.example.data.local.dao.SafetyDao
import com.example.data.local.entities.SavedChecklistReportEntity
import com.example.data.local.entities.SavedFieldReportEntity
import com.example.data.local.entities.SavedHiraEntity
import com.example.data.local.entities.SavedJsaEntity
import com.example.data.local.entities.SavedQuizResultEntity
import com.example.data.local.entities.SavedRiskAssessmentEntity
import com.example.data.local.entities.SavedSafetyDocumentEntity
import com.example.data.model.ChecklistTemplate
import com.example.data.model.ChemicalCompatibilityResult
import com.example.data.model.ChemicalItem
import com.example.data.model.DailySafetyContent
import com.example.data.model.PpeLibraryItem
import com.example.data.model.PpeRecommendation
import com.example.data.model.QuizQuestion
import com.example.data.model.SafetyLimitItem
import com.example.data.model.SafetySignItem
import com.example.data.model.SafetyTopic
import com.example.data.model.SearchResultItem
import com.example.data.model.SearchResultType
import com.example.data.model.ToolboxTalk
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class SafetyRepository(private val safetyDao: SafetyDao) {

    // Daily Safety Logic
    fun getTodaySafetyContent(): DailySafetyContent {
        val calendar = Calendar.getInstance()
        val dayOfWeek = when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> "MONDAY"
            Calendar.TUESDAY -> "TUESDAY"
            Calendar.WEDNESDAY -> "WEDNESDAY"
            Calendar.THURSDAY -> "THURSDAY"
            Calendar.FRIDAY -> "FRIDAY"
            Calendar.SATURDAY -> "SATURDAY"
            Calendar.SUNDAY -> "SUNDAY"
            else -> "MONDAY"
        }
        return SafetyDataCatalog.dailyTopics[dayOfWeek] ?: SafetyDataCatalog.dailyTopics.values.first()
    }

    fun getLastUpdatedString(): String {
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return sdf.format(Date())
    }

    // Safety Topics
    fun getAllSafetyTopics(): List<SafetyTopic> = SafetyDataCatalog.safetyTopics

    fun getTopicsByCategory(category: String): List<SafetyTopic> =
        SafetyDataCatalog.safetyTopics.filter { it.category.equals(category, ignoreCase = true) }

    fun getTopicById(id: String): SafetyTopic? =
        SafetyDataCatalog.safetyTopics.find { it.id == id }

    // Toolbox Talks
    fun getAllToolboxTalks(): List<ToolboxTalk> = SafetyDataCatalog.toolboxTalks

    fun getToolboxTalkById(id: String): ToolboxTalk? =
        SafetyDataCatalog.toolboxTalks.find { it.id == id }

    // PPE
    fun getAllPpeRecommendations(): List<PpeRecommendation> = SafetyDataCatalog.ppeRecommendations

    fun getPpeRecommendationForJob(jobId: String): PpeRecommendation? =
        SafetyDataCatalog.ppeRecommendations.find { it.jobId.equals(jobId, ignoreCase = true) }

    fun getAllPpeLibraryItems(): List<PpeLibraryItem> = SafetyDataCatalog.ppeLibrary

    // Safety Limits
    fun getAllSafetyLimits(): List<SafetyLimitItem> = SafetyDataCatalog.safetyLimits

    fun getSafetyLimitsByCategory(category: String): List<SafetyLimitItem> =
        SafetyDataCatalog.safetyLimits.filter { it.category.equals(category, ignoreCase = true) }

    // Chemicals
    fun getAllChemicals(): List<ChemicalItem> = SafetyDataCatalog.chemicals

    fun getChemicalById(id: String): ChemicalItem? =
        SafetyDataCatalog.chemicals.find { it.id == id }

    fun checkChemicalCompatibility(chemA: String, chemB: String): ChemicalCompatibilityResult {
        val match = SafetyDataCatalog.chemicalCompatibilities.find {
            (it.chemicalA.contains(chemA, ignoreCase = true) && it.chemicalB.contains(chemB, ignoreCase = true)) ||
            (it.chemicalA.contains(chemB, ignoreCase = true) && it.chemicalB.contains(chemA, ignoreCase = true))
        }

        return match ?: ChemicalCompatibilityResult(
            chemicalA = chemA,
            chemicalB = chemB,
            status = "CAUTION / REVIEW",
            severityLevel = "Warning",
            rationale = "Insufficient specific pairwise test data in offline catalog. Consult the current manufacturer SDS (Section 10 - Stability & Reactivity) before storing together.",
            reactionHazard = "Potential unexpected reactivity or incompatible chemical reaction.",
            requiredSeparation = "Maintain standard chemical class secondary segregation until confirmed by competent chemist/SDS."
        )
    }

    // Checklists
    fun getAllChecklistTemplates(): List<ChecklistTemplate> = SafetyDataCatalog.checklists

    fun getChecklistTemplateById(id: String): ChecklistTemplate? =
        SafetyDataCatalog.checklists.find { it.id == id }

    // Safety Signs
    fun getAllSafetySigns(): List<SafetySignItem> = SafetyDataCatalog.safetySigns

    // Global Search
    fun searchAll(query: String): List<SearchResultItem> {
        if (query.isBlank()) return emptyList()
        val q = query.trim().lowercase()
        val results = mutableListOf<SearchResultItem>()

        // Search Topics
        SafetyDataCatalog.safetyTopics.forEach { topic ->
            if (topic.title.lowercase().contains(q) || topic.summary.lowercase().contains(q) || topic.commonHazards.any { it.lowercase().contains(q) }) {
                results.add(SearchResultItem(topic.title, topic.summary, topic.category, SearchResultType.TOPIC, topic.id))
            }
        }

        // Search Tools
        if ("risk matrix calculator score".contains(q)) {
            results.add(SearchResultItem("5×5 Risk Matrix Calculator", "Calculate Likelihood × Severity and determine controls", "Field Tools", SearchResultType.TOOL, "tool_risk_matrix"))
        }
        if ("gas test atmospheric oxygen lel h2s confined".contains(q)) {
            results.add(SearchResultItem("Atmospheric Gas Tester Tool", "Evaluate O2, LEL, H2S, and CO against safe limits", "Field Tools", SearchResultType.TOOL, "tool_gas_tester"))
        }
        if ("noise calculator dba dose decibel".contains(q)) {
            results.add(SearchResultItem("Noise Exposure Calculator", "Calculate 8-hour TWA and dose percentage", "Field Tools", SearchResultType.TOOL, "tool_noise"))
        }
        if ("heat stress humidity wbgt temperature".contains(q)) {
            results.add(SearchResultItem("Heat Stress Screening Tool", "Determine heat risk category and work-rest cycles", "Field Tools", SearchResultType.TOOL, "tool_heat"))
        }
        if ("loto lockout tagout isolation energy".contains(q)) {
            results.add(SearchResultItem("Interactive 9-Step LOTO Guide", "Step-by-step zero energy isolation protocol", "Field Tools", SearchResultType.TOOL, "tool_loto"))
        }
        if ("lifting sling angle crane load wll".contains(q)) {
            results.add(SearchResultItem("Lifting & Sling Safety Calculator", "Calculate sling tension factor and safe working load", "Field Tools", SearchResultType.TOOL, "tool_lifting"))
        }
        if ("fire extinguisher pass foam co2 powder".contains(q)) {
            results.add(SearchResultItem("Fire Extinguisher Selector", "Choose the right extinguisher agent for fire class", "Field Tools", SearchResultType.TOOL, "tool_fire_extinguisher"))
        }

        // Search PPE
        SafetyDataCatalog.ppeRecommendations.forEach { ppe ->
            if (ppe.jobName.lowercase().contains(q) || ppe.requiredPpe.any { it.lowercase().contains(q) }) {
                results.add(SearchResultItem("PPE: ${ppe.jobName}", ppe.requiredPpe.take(3).joinToString(", "), "PPE Selector", SearchResultType.PPE, ppe.jobId))
            }
        }

        // Search Chemicals
        SafetyDataCatalog.chemicals.forEach { chem ->
            if (chem.name.lowercase().contains(q) || chem.formula.lowercase().contains(q) || chem.hazards.any { it.lowercase().contains(q) }) {
                results.add(SearchResultItem("Chemical: ${chem.name} (${chem.formula})", chem.hazards.firstOrNull() ?: "", "Chemical Safety", SearchResultType.CHEMICAL, chem.id))
            }
        }

        // Search Checklists
        SafetyDataCatalog.checklists.forEach { chk ->
            if (chk.title.lowercase().contains(q) || chk.items.any { it.lowercase().contains(q) }) {
                results.add(SearchResultItem("Checklist: ${chk.title}", "${chk.items.size} verification items", chk.category, SearchResultType.CHECKLIST, chk.id))
            }
        }

        // Search Toolbox Talks
        SafetyDataCatalog.toolboxTalks.forEach { talk ->
            if (talk.title.lowercase().contains(q) || talk.objective.lowercase().contains(q) || talk.keyHazards.any { it.lowercase().contains(q) }) {
                results.add(SearchResultItem("Toolbox Talk: ${talk.title}", talk.objective, talk.duration, SearchResultType.TALK, talk.id))
            }
        }

        return results.take(15)
    }

    // Room Persistence Methods
    fun getAllSavedJsas(): Flow<List<SavedJsaEntity>> = safetyDao.getAllSavedJsas()
    suspend fun saveJsa(jsa: SavedJsaEntity): Long = safetyDao.insertJsa(jsa)
    suspend fun deleteJsa(jsa: SavedJsaEntity) = safetyDao.deleteJsa(jsa)

    fun getAllSavedHiras(): Flow<List<SavedHiraEntity>> = safetyDao.getAllSavedHiras()
    suspend fun saveHira(hira: SavedHiraEntity): Long = safetyDao.insertHira(hira)
    suspend fun deleteHira(hira: SavedHiraEntity) = safetyDao.deleteHira(hira)

    fun getAllChecklistReports(): Flow<List<SavedChecklistReportEntity>> = safetyDao.getAllChecklistReports()
    suspend fun saveChecklistReport(report: SavedChecklistReportEntity): Long = safetyDao.insertChecklistReport(report)
    suspend fun deleteChecklistReport(report: SavedChecklistReportEntity) = safetyDao.deleteChecklistReport(report)

    fun getAllFieldReports(): Flow<List<SavedFieldReportEntity>> = safetyDao.getAllFieldReports()
    suspend fun saveFieldReport(report: SavedFieldReportEntity): Long = safetyDao.insertFieldReport(report)
    suspend fun deleteFieldReport(report: SavedFieldReportEntity) = safetyDao.deleteFieldReport(report)

    fun getAllRiskAssessments(): Flow<List<SavedRiskAssessmentEntity>> = safetyDao.getAllSavedRiskAssessments()
    suspend fun saveRiskAssessment(assessment: SavedRiskAssessmentEntity): Long = safetyDao.insertRiskAssessment(assessment)
    suspend fun deleteRiskAssessment(assessment: SavedRiskAssessmentEntity) = safetyDao.deleteRiskAssessment(assessment)

    fun getAllQuizResults(): Flow<List<SavedQuizResultEntity>> = safetyDao.getAllQuizResults()
    suspend fun saveQuizResult(result: SavedQuizResultEntity): Long = safetyDao.insertQuizResult(result)

    // Safety Documents (Uploaded PDFs, PPTs, SOPs)
    fun getAllSafetyDocuments(): Flow<List<SavedSafetyDocumentEntity>> = safetyDao.getAllSafetyDocuments()
    suspend fun getSafetyDocumentById(id: Long): SavedSafetyDocumentEntity? = safetyDao.getSafetyDocumentById(id)
    suspend fun saveSafetyDocument(doc: SavedSafetyDocumentEntity): Long = safetyDao.insertSafetyDocument(doc)
    suspend fun deleteSafetyDocument(doc: SavedSafetyDocumentEntity) = safetyDao.deleteSafetyDocument(doc)

    // Salary Calculations
    fun getAllSalaryCalculations(): Flow<List<com.example.data.local.entities.SavedSalaryCalcEntity>> = safetyDao.getAllSalaryCalculations()
    suspend fun saveSalaryCalculation(salary: com.example.data.local.entities.SavedSalaryCalcEntity): Long = safetyDao.insertSalaryCalculation(salary)
    suspend fun deleteSalaryCalculation(salary: com.example.data.local.entities.SavedSalaryCalcEntity) = safetyDao.deleteSalaryCalculation(salary)

    // OT Calculations
    fun getAllOtCalculations(): Flow<List<com.example.data.local.entities.SavedOtCalcEntity>> = safetyDao.getAllOtCalculations()
    suspend fun saveOtCalculation(ot: com.example.data.local.entities.SavedOtCalcEntity): Long = safetyDao.insertOtCalculation(ot)
    suspend fun deleteOtCalculation(ot: com.example.data.local.entities.SavedOtCalcEntity) = safetyDao.deleteOtCalculation(ot)

    // Attendance Records
    fun getAllAttendanceRecords(): Flow<List<com.example.data.local.entities.SavedAttendanceEntity>> = safetyDao.getAllAttendanceRecords()
    suspend fun saveAttendanceRecord(attendance: com.example.data.local.entities.SavedAttendanceEntity): Long = safetyDao.insertAttendanceRecord(attendance)
    suspend fun deleteAttendanceRecord(attendance: com.example.data.local.entities.SavedAttendanceEntity) = safetyDao.deleteAttendanceRecord(attendance)

    // Bookmarks
    fun getAllBookmarks(): Flow<List<com.example.data.local.entities.SavedBookmarkEntity>> = safetyDao.getAllBookmarks()
    suspend fun isBookmarked(itemId: String): Boolean = safetyDao.getBookmarkByItemId(itemId) != null
    suspend fun saveBookmark(bookmark: com.example.data.local.entities.SavedBookmarkEntity): Long = safetyDao.insertBookmark(bookmark)
    suspend fun removeBookmark(itemId: String) = safetyDao.deleteBookmarkByItemId(itemId)
    suspend fun deleteBookmark(bookmark: com.example.data.local.entities.SavedBookmarkEntity) = safetyDao.deleteBookmark(bookmark)
}

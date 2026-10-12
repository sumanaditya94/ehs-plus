package com.example.ui.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.ai.GeminiSafetyService
import com.example.data.ai.ScanAnalysisResult
import com.example.data.local.entities.SavedChecklistReportEntity
import com.example.data.local.entities.SavedFieldReportEntity
import com.example.data.local.entities.SavedHiraEntity
import com.example.data.local.entities.SavedJsaEntity
import com.example.data.local.entities.SavedQuizResultEntity
import com.example.data.local.entities.SavedRiskAssessmentEntity
import com.example.data.local.entities.SavedSafetyDocumentEntity
import com.example.data.model.ActiveChecklistItem
import com.example.data.model.CheckItemStatus
import com.example.data.model.ChecklistTemplate
import com.example.data.model.ChemicalCompatibilityResult
import com.example.data.model.ChemicalItem
import com.example.data.model.DailySafetyContent
import com.example.data.model.JsaStepModel
import com.example.data.model.PpeLibraryItem
import com.example.data.model.PpeRecommendation
import com.example.data.model.QuizQuestion
import com.example.data.model.SafetyLimitItem
import com.example.data.model.SafetySignItem
import com.example.data.model.SafetyTopic
import com.example.data.model.SearchResultItem
import com.example.data.model.ToolboxTalk
import com.example.data.repository.SafetyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import com.example.data.language.AppLanguage
import com.example.data.language.SafetyLocalizer
import com.example.data.language.SafetyStrings
import com.example.data.ai.AskSafetyResponse
import com.example.data.ai.SafetyKnowledgeRetriever
import com.example.data.model.AuthoritativeDocument
import com.example.data.model.DocInspectionItem
import com.example.data.model.DocInspectionTemplate
import com.example.data.model.DocMicroTrainingModule
import com.example.data.model.DocPpeGuidance
import com.example.data.model.DocReferenceCard
import com.example.data.model.DocSearchCategory
import com.example.data.model.DocSearchResult
import com.example.data.model.SourceCitation
import com.example.data.repository.DocumentKnowledgeCatalog
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.sin

class SafetyViewModel(private val repository: SafetyRepository) : ViewModel() {

    // App Language State (English & Hindi)
    private val _currentLanguage = MutableStateFlow(AppLanguage.ENGLISH)
    val currentLanguage: StateFlow<AppLanguage> = _currentLanguage.asStateFlow()

    fun setLanguage(language: AppLanguage) {
        _currentLanguage.value = language
    }

    fun toggleLanguage() {
        _currentLanguage.value = if (_currentLanguage.value == AppLanguage.ENGLISH) {
            AppLanguage.HINDI
        } else {
            AppLanguage.ENGLISH
        }
    }

    fun getString(key: String): String {
        return SafetyStrings.get(key, _currentLanguage.value)
    }

    // Daily & Handbook Content
    val todayContent: DailySafetyContent
        get() = SafetyLocalizer.localizeDailyContent(repository.getTodaySafetyContent(), _currentLanguage.value)

    val lastUpdated: String = repository.getLastUpdatedString()

    val allTopics: List<SafetyTopic>
        get() = repository.getAllSafetyTopics().map { SafetyLocalizer.localizeTopic(it, _currentLanguage.value) }

    val allToolboxTalks: List<ToolboxTalk>
        get() = repository.getAllToolboxTalks().map { SafetyLocalizer.localizeToolboxTalk(it, _currentLanguage.value) }

    fun getLocalizedTopic(topic: SafetyTopic): SafetyTopic =
        SafetyLocalizer.localizeTopic(topic, _currentLanguage.value)

    val allPpeRecommendations: List<PpeRecommendation> = repository.getAllPpeRecommendations()
    val allPpeLibrary: List<PpeLibraryItem> = repository.getAllPpeLibraryItems()
    val allSafetyLimits: List<SafetyLimitItem> = repository.getAllSafetyLimits()
    val allChemicals: List<ChemicalItem> = repository.getAllChemicals()
    val allChecklistTemplates: List<ChecklistTemplate> = repository.getAllChecklistTemplates()
    val allSafetySigns: List<SafetySignItem> = repository.getAllSafetySigns()

    // Authoritative Document Knowledge Library (Approved & Published)
    val authoritativeDocs: List<AuthoritativeDocument> = DocumentKnowledgeCatalog.authoritativeDocuments
    private val _selectedDoc = MutableStateFlow(DocumentKnowledgeCatalog.authoritativeDocuments.first())
    val selectedDoc: StateFlow<AuthoritativeDocument> = _selectedDoc.asStateFlow()
    fun selectDocument(doc: AuthoritativeDocument) { _selectedDoc.value = doc }

    private val _docExplorerTab = MutableStateFlow(0) // 0: Original Doc, 1: Simplified Learn, 2: Checklist, 3: Template, 4: Quiz, 5: Reference
    val docExplorerTab: StateFlow<Int> = _docExplorerTab.asStateFlow()
    fun setDocExplorerTab(tab: Int) { _docExplorerTab.value = tab }

    val microTrainingModules: List<DocMicroTrainingModule> = DocumentKnowledgeCatalog.microTrainingModules
    val docReferenceCards: List<DocReferenceCard> = DocumentKnowledgeCatalog.referenceCards
    val docPpeGuidance: List<DocPpeGuidance> = DocumentKnowledgeCatalog.ppeGuidanceList
    val dailyInspectionTemplate: DocInspectionTemplate = DocumentKnowledgeCatalog.dailyInspectionTemplate

    // Document to Template Engine: Daily Safety Inspection Form State
    private val _inspArea = MutableStateFlow("Unit 4 - Fabrication Yard")
    val inspArea: StateFlow<String> = _inspArea.asStateFlow()
    fun setInspArea(v: String) { _inspArea.value = v }

    private val _inspInspector = MutableStateFlow("Safety Supervisor")
    val inspInspector: StateFlow<String> = _inspInspector.asStateFlow()
    fun setInspInspector(v: String) { _inspInspector.value = v }

    private val _inspShift = MutableStateFlow("Day Shift (A)")
    val inspShift: StateFlow<String> = _inspShift.asStateFlow()
    fun setInspShift(v: String) { _inspShift.value = v }

    private val _inspDate = MutableStateFlow(SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date()))
    val inspDate: StateFlow<String> = _inspDate.asStateFlow()
    fun setInspDate(v: String) { _inspDate.value = v }

    private val _inspItemStatuses = MutableStateFlow<Map<String, CheckItemStatus>>(emptyMap())
    val inspItemStatuses: StateFlow<Map<String, CheckItemStatus>> = _inspItemStatuses.asStateFlow()

    private val _inspItemRemarks = MutableStateFlow<Map<String, String>>(emptyMap())
    val inspItemRemarks: StateFlow<Map<String, String>> = _inspItemRemarks.asStateFlow()

    private val _inspItemPhotos = MutableStateFlow<Map<String, Boolean>>(emptyMap())
    val inspItemPhotos: StateFlow<Map<String, Boolean>> = _inspItemPhotos.asStateFlow()

    fun setInspItemStatus(itemId: String, status: CheckItemStatus) {
        val map = _inspItemStatuses.value.toMutableMap()
        map[itemId] = status
        _inspItemStatuses.value = map
    }

    fun setInspItemRemark(itemId: String, remark: String) {
        val map = _inspItemRemarks.value.toMutableMap()
        map[itemId] = remark
        _inspItemRemarks.value = map
    }

    fun toggleInspItemPhoto(itemId: String) {
        val map = _inspItemPhotos.value.toMutableMap()
        map[itemId] = !(map[itemId] ?: false)
        _inspItemPhotos.value = map
    }

    private val _generatedReportText = MutableStateFlow<String?>(null)
    val generatedReportText: StateFlow<String?> = _generatedReportText.asStateFlow()
    fun clearGeneratedReport() { _generatedReportText.value = null }

    fun generateAndSaveInspectionReport(): String {
        val total = dailyInspectionTemplate.items.size
        val statuses = _inspItemStatuses.value
        val passCount = statuses.values.count { it == CheckItemStatus.PASS }
        val failCount = statuses.values.count { it == CheckItemStatus.FAIL }
        val naCount = statuses.values.count { it == CheckItemStatus.NA }
        val score = if (total - naCount > 0) ((passCount.toDouble() / (total - naCount)) * 100).toInt() else 100

        val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        val dateStr = sdf.format(Date())

        val reportBuilder = StringBuilder()
        val isHi = _currentLanguage.value == AppLanguage.HINDI
        reportBuilder.append(if (isHi) "=== दैनिक सुरक्षा निरीक्षण रिपोर्ट ===\n" else "=== DAILY SAFETY INSPECTION REPORT ===\n")
        reportBuilder.append(if (isHi) "स्रोत: ${dailyInspectionTemplate.documentSource} (${dailyInspectionTemplate.version})\n" else "Source: ${dailyInspectionTemplate.documentSource} (${dailyInspectionTemplate.version})\n")
        reportBuilder.append(if (isHi) "दिनांक: ${_inspDate.value} | शिफ्ट: ${_inspShift.value}\n" else "Date: ${_inspDate.value} | Shift: ${_inspShift.value}\n")
        reportBuilder.append(if (isHi) "क्षेत्र: ${_inspArea.value}\n" else "Area: ${_inspArea.value}\n")
        reportBuilder.append(if (isHi) "निरीक्षक: ${_inspInspector.value}\n" else "Inspector: ${_inspInspector.value}\n")
        reportBuilder.append(if (isHi) "अनुपालन स्कोर: $score% (पास: $passCount, फेल: $failCount, N/A: $naCount)\n\n" else "Compliance Score: $score% (Pass: $passCount, Fail: $failCount, N/A: $naCount)\n\n")

        dailyInspectionTemplate.items.forEachIndexed { idx, item ->
            val st = statuses[item.id] ?: CheckItemStatus.PASS
            val rem = _inspItemRemarks.value[item.id] ?: ""
            val hasPhoto = _inspItemPhotos.value[item.id] ?: false
            val photoTag = if (hasPhoto) " [📷 Photo Attached]" else ""
            val qText = if (isHi) item.questionHi else item.question
            val catText = if (isHi) item.categoryHi else item.category
            reportBuilder.append("${idx + 1}. [$catText] $qText\n   Status: $st$photoTag")
            if (rem.isNotBlank()) reportBuilder.append(" | Note: $rem")
            reportBuilder.append("\n")
        }

        val reportStr = reportBuilder.toString()
        _generatedReportText.value = reportStr

        // Persist to Room
        viewModelScope.launch {
            repository.saveChecklistReport(
                SavedChecklistReportEntity(
                    checklistTitle = dailyInspectionTemplate.title,
                    category = "Site Inspection",
                    dateString = dateStr,
                    totalItems = total,
                    passedCount = passCount,
                    failedCount = failCount,
                    naCount = naCount,
                    scorePercentage = score,
                    summaryNotes = "Area: ${_inspArea.value} | Inspector: ${_inspInspector.value}\n$reportStr"
                )
            )
        }
        return reportStr
    }

    // Global & Semantic Search across Approved Documents
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow<List<SearchResultItem>>(emptyList())
    val searchResults: StateFlow<List<SearchResultItem>> = _searchResults.asStateFlow()

    private val _docSearchResults = MutableStateFlow<Map<DocSearchCategory, List<DocSearchResult>>>(emptyMap())
    val docSearchResults: StateFlow<Map<DocSearchCategory, List<DocSearchResult>>> = _docSearchResults.asStateFlow()

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        _searchResults.value = repository.searchAll(query)
        _docSearchResults.value = DocumentKnowledgeCatalog.searchAuthoritativeKnowledge(query)
    }

    // Room Persistent Flows
    val savedJsas: StateFlow<List<SavedJsaEntity>> = repository.getAllSavedJsas()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val savedHiras: StateFlow<List<SavedHiraEntity>> = repository.getAllSavedHiras()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val savedChecklistReports: StateFlow<List<SavedChecklistReportEntity>> = repository.getAllChecklistReports()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val savedFieldReports: StateFlow<List<SavedFieldReportEntity>> = repository.getAllFieldReports()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val savedRiskAssessments: StateFlow<List<SavedRiskAssessmentEntity>> = repository.getAllRiskAssessments()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val savedQuizResults: StateFlow<List<SavedQuizResultEntity>> = repository.getAllQuizResults()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val savedSafetyDocuments: StateFlow<List<SavedSafetyDocumentEntity>> = repository.getAllSafetyDocuments()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val savedSalaryCalculations: StateFlow<List<com.example.data.local.entities.SavedSalaryCalcEntity>> = repository.getAllSalaryCalculations()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val savedOtCalculations: StateFlow<List<com.example.data.local.entities.SavedOtCalcEntity>> = repository.getAllOtCalculations()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val savedAttendanceRecords: StateFlow<List<com.example.data.local.entities.SavedAttendanceEntity>> = repository.getAllAttendanceRecords()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val savedBookmarks: StateFlow<List<com.example.data.local.entities.SavedBookmarkEntity>> = repository.getAllBookmarks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // --- Bookmarks Management ---
    fun toggleBookmark(type: String, id: String, title: String, subtitle: String, category: String = "") {
        viewModelScope.launch {
            if (repository.isBookmarked(id)) {
                repository.removeBookmark(id)
            } else {
                repository.saveBookmark(
                    com.example.data.local.entities.SavedBookmarkEntity(
                        itemType = type,
                        itemId = id,
                        title = title,
                        subtitle = subtitle,
                        category = category
                    )
                )
            }
        }
    }

    fun deleteBookmark(bookmark: com.example.data.local.entities.SavedBookmarkEntity) {
        viewModelScope.launch { repository.deleteBookmark(bookmark) }
    }

    // --- Worker Salary Calculator State ---
    private val _salMonth = MutableStateFlow("October 2026")
    val salMonth: StateFlow<String> = _salMonth.asStateFlow()
    fun setSalMonth(value: String) { _salMonth.value = value }

    private val _salBasic = MutableStateFlow("18000")
    val salBasic: StateFlow<String> = _salBasic.asStateFlow()
    fun setSalBasic(value: String) { _salBasic.value = value }

    private val _salDa = MutableStateFlow("4000")
    val salDa: StateFlow<String> = _salDa.asStateFlow()
    fun setSalDa(value: String) { _salDa.value = value }

    private val _salHra = MutableStateFlow("3500")
    val salHra: StateFlow<String> = _salHra.asStateFlow()
    fun setSalHra(value: String) { _salHra.value = value }

    private val _salAllowances = MutableStateFlow("1500")
    val salAllowances: StateFlow<String> = _salAllowances.asStateFlow()
    fun setSalAllowances(value: String) { _salAllowances.value = value }

    private val _salWorkingDays = MutableStateFlow("26")
    val salWorkingDays: StateFlow<String> = _salWorkingDays.asStateFlow()
    fun setSalWorkingDays(value: String) { _salWorkingDays.value = value }

    private val _salPresentDays = MutableStateFlow("24")
    val salPresentDays: StateFlow<String> = _salPresentDays.asStateFlow()
    fun setSalPresentDays(value: String) { _salPresentDays.value = value }

    private val _salLeaveDays = MutableStateFlow("2")
    val salLeaveDays: StateFlow<String> = _salLeaveDays.asStateFlow()
    fun setSalLeaveDays(value: String) { _salLeaveDays.value = value }

    private val _salOtHours = MutableStateFlow("16")
    val salOtHours: StateFlow<String> = _salOtHours.asStateFlow()
    fun setSalOtHours(value: String) { _salOtHours.value = value }

    private val _salOtRate = MutableStateFlow("180")
    val salOtRate: StateFlow<String> = _salOtRate.asStateFlow()
    fun setSalOtRate(value: String) { _salOtRate.value = value }

    private val _salPfDeduction = MutableStateFlow("2160") // 12% of basic
    val salPfDeduction: StateFlow<String> = _salPfDeduction.asStateFlow()
    fun setSalPfDeduction(value: String) { _salPfDeduction.value = value }

    private val _salEsiDeduction = MutableStateFlow("200")
    val salEsiDeduction: StateFlow<String> = _salEsiDeduction.asStateFlow()
    fun setSalEsiDeduction(value: String) { _salEsiDeduction.value = value }

    private val _salOtherDeduction = MutableStateFlow("0")
    val salOtherDeduction: StateFlow<String> = _salOtherDeduction.asStateFlow()
    fun setSalOtherDeduction(value: String) { _salOtherDeduction.value = value }

    fun calculateSalaryResults(): Map<String, Double> {
        val basic = _salBasic.value.toDoubleOrNull() ?: 0.0
        val da = _salDa.value.toDoubleOrNull() ?: 0.0
        val hra = _salHra.value.toDoubleOrNull() ?: 0.0
        val allowances = _salAllowances.value.toDoubleOrNull() ?: 0.0
        val totalWorkingDays = _salWorkingDays.value.toDoubleOrNull() ?: 26.0
        val presentDays = _salPresentDays.value.toDoubleOrNull() ?: 26.0
        val leaveDays = _salLeaveDays.value.toDoubleOrNull() ?: 0.0
        val otHours = _salOtHours.value.toDoubleOrNull() ?: 0.0
        val otRate = _salOtRate.value.toDoubleOrNull() ?: 0.0

        val pf = _salPfDeduction.value.toDoubleOrNull() ?: 0.0
        val esi = _salEsiDeduction.value.toDoubleOrNull() ?: 0.0
        val otherDed = _salOtherDeduction.value.toDoubleOrNull() ?: 0.0

        val baseMonthly = basic + da + hra + allowances
        val effectiveDays = (presentDays + leaveDays).coerceAtMost(totalWorkingDays)
        val earnedBase = if (totalWorkingDays > 0) (baseMonthly / totalWorkingDays) * effectiveDays else baseMonthly
        val otEarnings = otHours * otRate
        val gross = earnedBase + otEarnings
        val totalDeductions = pf + esi + otherDed
        val net = (gross - totalDeductions).coerceAtLeast(0.0)

        return mapOf(
            "baseMonthly" to baseMonthly,
            "earnedBase" to earnedBase,
            "otEarnings" to otEarnings,
            "gross" to gross,
            "totalDeductions" to totalDeductions,
            "net" to net
        )
    }

    fun saveCurrentSalaryCalculation() {
        val results = calculateSalaryResults()
        viewModelScope.launch {
            repository.saveSalaryCalculation(
                com.example.data.local.entities.SavedSalaryCalcEntity(
                    monthYear = _salMonth.value.ifBlank { "Current Month" },
                    basic = _salBasic.value.toDoubleOrNull() ?: 0.0,
                    da = _salDa.value.toDoubleOrNull() ?: 0.0,
                    hra = _salHra.value.toDoubleOrNull() ?: 0.0,
                    allowances = _salAllowances.value.toDoubleOrNull() ?: 0.0,
                    workingDays = _salWorkingDays.value.toIntOrNull() ?: 26,
                    presentDays = _salPresentDays.value.toIntOrNull() ?: 24,
                    leaveDays = _salLeaveDays.value.toIntOrNull() ?: 2,
                    otHours = _salOtHours.value.toDoubleOrNull() ?: 0.0,
                    otRate = _salOtRate.value.toDoubleOrNull() ?: 0.0,
                    otEarnings = results["otEarnings"] ?: 0.0,
                    grossSalary = results["gross"] ?: 0.0,
                    totalDeductions = results["totalDeductions"] ?: 0.0,
                    netSalary = results["net"] ?: 0.0,
                    note = "Saved salary calculation"
                )
            )
        }
    }

    fun deleteSalaryCalculation(item: com.example.data.local.entities.SavedSalaryCalcEntity) {
        viewModelScope.launch { repository.deleteSalaryCalculation(item) }
    }

    // --- Worker Overtime (OT) Shift Calculator State ---
    private val _otTitle = MutableStateFlow("Overtime Shift")
    val otTitle: StateFlow<String> = _otTitle.asStateFlow()
    fun setOtTitle(value: String) { _otTitle.value = value }

    private val _otShiftStart = MutableStateFlow("08:00")
    val otShiftStart: StateFlow<String> = _otShiftStart.asStateFlow()
    fun setOtShiftStart(value: String) { _otShiftStart.value = value }

    private val _otShiftEnd = MutableStateFlow("20:00")
    val otShiftEnd: StateFlow<String> = _otShiftEnd.asStateFlow()
    fun setOtShiftEnd(value: String) { _otShiftEnd.value = value }

    private val _otBreakMins = MutableStateFlow("60")
    val otBreakMins: StateFlow<String> = _otBreakMins.asStateFlow()
    fun setOtBreakMins(value: String) { _otBreakMins.value = value }

    private val _otNormalShiftHours = MutableStateFlow("8.0")
    val otNormalShiftHours: StateFlow<String> = _otNormalShiftHours.asStateFlow()
    fun setOtNormalShiftHours(value: String) { _otNormalShiftHours.value = value }

    private val _otRatePerHour = MutableStateFlow("150.0")
    val otRatePerHour: StateFlow<String> = _otRatePerHour.asStateFlow()
    fun setOtRatePerHour(value: String) { _otRatePerHour.value = value }

    fun calculateOtResults(): Map<String, Double> {
        val startParts = _otShiftStart.value.split(":").mapNotNull { it.toIntOrNull() }
        val endParts = _otShiftEnd.value.split(":").mapNotNull { it.toIntOrNull() }
        val startMins = if (startParts.size == 2) startParts[0] * 60 + startParts[1] else 8 * 60
        var endMins = if (endParts.size == 2) endParts[0] * 60 + endParts[1] else 20 * 60
        if (endMins < startMins) endMins += 24 * 60 // Overnight shift

        val breakM = _otBreakMins.value.toIntOrNull() ?: 60
        val totalWorkedMins = (endMins - startMins - breakM).coerceAtLeast(0)
        val totalWorkedHours = totalWorkedMins / 60.0

        val normalHours = _otNormalShiftHours.value.toDoubleOrNull() ?: 8.0
        val otHours = (totalWorkedHours - normalHours).coerceAtLeast(0.0)
        val rate = _otRatePerHour.value.toDoubleOrNull() ?: 150.0
        val otAmount = otHours * rate

        return mapOf(
            "totalWorkedHours" to totalWorkedHours,
            "normalHours" to normalHours.coerceAtMost(totalWorkedHours),
            "otHours" to otHours,
            "otAmount" to otAmount
        )
    }

    fun saveCurrentOtCalculation() {
        val results = calculateOtResults()
        viewModelScope.launch {
            repository.saveOtCalculation(
                com.example.data.local.entities.SavedOtCalcEntity(
                    title = _otTitle.value.ifBlank { "Daily OT Log" },
                    shiftStart = _otShiftStart.value,
                    shiftEnd = _otShiftEnd.value,
                    breakMinutes = _otBreakMins.value.toIntOrNull() ?: 60,
                    normalHours = results["normalHours"] ?: 8.0,
                    otHours = results["otHours"] ?: 0.0,
                    otRatePerHour = _otRatePerHour.value.toDoubleOrNull() ?: 150.0,
                    otAmount = results["otAmount"] ?: 0.0
                )
            )
        }
    }

    fun deleteOtCalculation(item: com.example.data.local.entities.SavedOtCalcEntity) {
        viewModelScope.launch { repository.deleteOtCalculation(item) }
    }

    // --- Worker Attendance Tracker State ---
    private val _attMonthYear = MutableStateFlow("October 2026")
    val attMonthYear: StateFlow<String> = _attMonthYear.asStateFlow()
    fun setAttMonthYear(value: String) { _attMonthYear.value = value }

    private val _attTotalDays = MutableStateFlow("31")
    val attTotalDays: StateFlow<String> = _attTotalDays.asStateFlow()
    fun setAttTotalDays(value: String) { _attTotalDays.value = value }

    private val _attPresent = MutableStateFlow("24")
    val attPresent: StateFlow<String> = _attPresent.asStateFlow()
    fun setAttPresent(value: String) { _attPresent.value = value }

    private val _attAbsent = MutableStateFlow("1")
    val attAbsent: StateFlow<String> = _attAbsent.asStateFlow()
    fun setAttAbsent(value: String) { _attAbsent.value = value }

    private val _attHalfDays = MutableStateFlow("2")
    val attHalfDays: StateFlow<String> = _attHalfDays.asStateFlow()
    fun setAttHalfDays(value: String) { _attHalfDays.value = value }

    private val _attLeaves = MutableStateFlow("1")
    val attLeaves: StateFlow<String> = _attLeaves.asStateFlow()
    fun setAttLeaves(value: String) { _attLeaves.value = value }

    private val _attWeeklyOff = MutableStateFlow("4")
    val attWeeklyOff: StateFlow<String> = _attWeeklyOff.asStateFlow()
    fun setAttWeeklyOff(value: String) { _attWeeklyOff.value = value }

    private val _attHolidays = MutableStateFlow("1")
    val attHolidays: StateFlow<String> = _attHolidays.asStateFlow()
    fun setAttHolidays(value: String) { _attHolidays.value = value }

    private val _attTotalOtHours = MutableStateFlow("24.0")
    val attTotalOtHours: StateFlow<String> = _attTotalOtHours.asStateFlow()
    fun setAttTotalOtHours(value: String) { _attTotalOtHours.value = value }

    fun calculateAttendancePayableDays(): Double {
        val pres = _attPresent.value.toDoubleOrNull() ?: 0.0
        val half = _attHalfDays.value.toDoubleOrNull() ?: 0.0
        val leaves = _attLeaves.value.toDoubleOrNull() ?: 0.0
        val off = _attWeeklyOff.value.toDoubleOrNull() ?: 0.0
        val hol = _attHolidays.value.toDoubleOrNull() ?: 0.0
        return pres + (half * 0.5) + leaves + off + hol
    }

    fun saveCurrentAttendanceRecord() {
        val payable = calculateAttendancePayableDays()
        viewModelScope.launch {
            repository.saveAttendanceRecord(
                com.example.data.local.entities.SavedAttendanceEntity(
                    monthYear = _attMonthYear.value.ifBlank { "Current Month" },
                    totalDays = _attTotalDays.value.toIntOrNull() ?: 31,
                    presentDays = _attPresent.value.toIntOrNull() ?: 0,
                    absentDays = _attAbsent.value.toIntOrNull() ?: 0,
                    halfDays = _attHalfDays.value.toIntOrNull() ?: 0,
                    leaveDays = _attLeaves.value.toIntOrNull() ?: 0,
                    weeklyOffDays = _attWeeklyOff.value.toIntOrNull() ?: 0,
                    holidays = _attHolidays.value.toIntOrNull() ?: 0,
                    payableDays = payable,
                    totalOtHours = _attTotalOtHours.value.toDoubleOrNull() ?: 0.0
                )
            )
        }
    }

    fun deleteAttendanceRecord(item: com.example.data.local.entities.SavedAttendanceEntity) {
        viewModelScope.launch { repository.deleteAttendanceRecord(item) }
    }

    // --- Interactive 5x5 Risk Calculator State ---
    private val _riskTaskName = MutableStateFlow("")
    val riskTaskName: StateFlow<String> = _riskTaskName.asStateFlow()
    fun setRiskTaskName(name: String) { _riskTaskName.value = name }

    private val _riskLikelihood = MutableStateFlow(2)
    val riskLikelihood: StateFlow<Int> = _riskLikelihood.asStateFlow()
    fun setRiskLikelihood(value: Int) { _riskLikelihood.value = value.coerceIn(1, 5) }

    private val _riskSeverity = MutableStateFlow(3)
    val riskSeverity: StateFlow<Int> = _riskSeverity.asStateFlow()
    fun setRiskSeverity(value: Int) { _riskSeverity.value = value.coerceIn(1, 5) }

    val calculatedRiskScore: Int get() = _riskLikelihood.value * _riskSeverity.value

    val calculatedRiskLevel: String get() = when (calculatedRiskScore) {
        in 1..4 -> "LOW"
        in 5..9 -> "MEDIUM"
        in 10..16 -> "HIGH"
        else -> "CRITICAL"
    }

    val calculatedRiskAction: String get() = when (calculatedRiskLevel) {
        "LOW" -> "Acceptable with standard controls. Monitor and maintain existing safeguards."
        "MEDIUM" -> "Tolerable. Implement additional engineering or administrative controls where reasonably practicable."
        "HIGH" -> "High Risk! Responsible person review required before work starts. Additional specific controls mandatory."
        else -> "CRITICAL / UNACCEPTABLE! DO NOT PROCEED. Work must stop until risk is significantly reduced with senior authorization."
    }

    fun saveCurrentRiskAssessment() {
        viewModelScope.launch {
            repository.saveRiskAssessment(
                SavedRiskAssessmentEntity(
                    taskTitle = if (_riskTaskName.value.isBlank()) "Task Risk Assessment" else _riskTaskName.value,
                    likelihood = _riskLikelihood.value,
                    severity = _riskSeverity.value,
                    riskScore = calculatedRiskScore,
                    riskLevel = calculatedRiskLevel,
                    recommendedAction = calculatedRiskAction
                )
            )
            _riskTaskName.value = ""
        }
    }

    fun deleteRiskAssessment(assessment: SavedRiskAssessmentEntity) {
        viewModelScope.launch { repository.deleteRiskAssessment(assessment) }
    }

    // --- Atmospheric Gas Tester State ---
    private val _gasO2 = MutableStateFlow("20.9")
    val gasO2: StateFlow<String> = _gasO2.asStateFlow()
    fun setGasO2(value: String) { _gasO2.value = value }

    private val _gasLel = MutableStateFlow("0.0")
    val gasLel: StateFlow<String> = _gasLel.asStateFlow()
    fun setGasLel(value: String) { _gasLel.value = value }

    private val _gasH2s = MutableStateFlow("0.0")
    val gasH2s: StateFlow<String> = _gasH2s.asStateFlow()
    fun setGasH2s(value: String) { _gasH2s.value = value }

    private val _gasCo = MutableStateFlow("0.0")
    val gasCo: StateFlow<String> = _gasCo.asStateFlow()
    fun setGasCo(value: String) { _gasCo.value = value }

    // --- Noise Exposure Calculator State ---
    private val _noiseDba = MutableStateFlow("88")
    val noiseDba: StateFlow<String> = _noiseDba.asStateFlow()
    fun setNoiseDba(value: String) { _noiseDba.value = value }

    private val _noiseHours = MutableStateFlow("4.0")
    val noiseHours: StateFlow<String> = _noiseHours.asStateFlow()
    fun setNoiseHours(value: String) { _noiseHours.value = value }

    private val _noiseStandard = MutableStateFlow("OSHA (90 dBA / 5dB exchange)")
    val noiseStandard: StateFlow<String> = _noiseStandard.asStateFlow()
    fun setNoiseStandard(value: String) { _noiseStandard.value = value }

    // --- Heat Stress Screening Tool ---
    private val _heatTempC = MutableStateFlow("34")
    val heatTempC: StateFlow<String> = _heatTempC.asStateFlow()
    fun setHeatTempC(value: String) { _heatTempC.value = value }

    private val _heatHumidity = MutableStateFlow("65")
    val heatHumidity: StateFlow<String> = _heatHumidity.asStateFlow()
    fun setHeatHumidity(value: String) { _heatHumidity.value = value }

    private val _heatWorkIntensity = MutableStateFlow("Moderate (Walking, lifting, pipefitting)")
    val heatWorkIntensity: StateFlow<String> = _heatWorkIntensity.asStateFlow()
    fun setHeatWorkIntensity(value: String) { _heatWorkIntensity.value = value }

    // --- Lifting & Sling Safety Calculator ---
    private val _liftWeightKg = MutableStateFlow("2000")
    val liftWeightKg: StateFlow<String> = _liftWeightKg.asStateFlow()
    fun setLiftWeightKg(value: String) { _liftWeightKg.value = value }

    private val _slingAngleDeg = MutableStateFlow("60") // 60 deg to horizontal
    val slingAngleDeg: StateFlow<String> = _slingAngleDeg.asStateFlow()
    fun setSlingAngleDeg(value: String) { _slingAngleDeg.value = value }

    private val _slingLegsCount = MutableStateFlow(2)
    val slingLegsCount: StateFlow<Int> = _slingLegsCount.asStateFlow()
    fun setSlingLegsCount(value: Int) { _slingLegsCount.value = value.coerceIn(1, 4) }

    fun calculateSlingTension(): Double {
        val w = _liftWeightKg.value.toDoubleOrNull() ?: 1000.0
        val angleDeg = _slingAngleDeg.value.toDoubleOrNull() ?: 60.0
        val legs = _slingLegsCount.value.coerceAtLeast(1)
        val angleRad = Math.toRadians(angleDeg.coerceIn(15.0, 90.0))
        val sinA = sin(angleRad)
        return (w / legs) / sinA
    }

    // --- Interactive LOTO 9-Step Guide State ---
    private val _completedLotoSteps = MutableStateFlow<Set<Int>>(emptySet())
    val completedLotoSteps: StateFlow<Set<Int>> = _completedLotoSteps.asStateFlow()

    fun toggleLotoStep(step: Int) {
        val current = _completedLotoSteps.value.toMutableSet()
        if (current.contains(step)) current.remove(step) else current.add(step)
        _completedLotoSteps.value = current
    }

    fun resetLotoGuide() {
        _completedLotoSteps.value = emptySet()
    }

    // --- Chemical Compatibility Checker ---
    private val _selectedChemA = MutableStateFlow("Hydrochloric Acid (HCl)")
    val selectedChemA: StateFlow<String> = _selectedChemA.asStateFlow()
    fun setSelectedChemA(chem: String) {
        _selectedChemA.value = chem
        evaluateChemicalCompatibility()
    }

    private val _selectedChemB = MutableStateFlow("Sodium Hypochlorite (Bleach)")
    val selectedChemB: StateFlow<String> = _selectedChemB.asStateFlow()
    fun setSelectedChemB(chem: String) {
        _selectedChemB.value = chem
        evaluateChemicalCompatibility()
    }

    private val _compatibilityResult = MutableStateFlow<ChemicalCompatibilityResult?>(null)
    val compatibilityResult: StateFlow<ChemicalCompatibilityResult?> = _compatibilityResult.asStateFlow()

    init {
        evaluateChemicalCompatibility()
    }

    private fun evaluateChemicalCompatibility() {
        _compatibilityResult.value = repository.checkChemicalCompatibility(_selectedChemA.value, _selectedChemB.value)
    }

    // --- Active Checklist Session ---
    private val _activeChecklist = MutableStateFlow<ChecklistTemplate?>(null)
    val activeChecklist: StateFlow<ChecklistTemplate?> = _activeChecklist.asStateFlow()

    private val _activeChecklistItems = MutableStateFlow<List<ActiveChecklistItem>>(emptyList())
    val activeChecklistItems: StateFlow<List<ActiveChecklistItem>> = _activeChecklistItems.asStateFlow()

    fun startChecklistSession(template: ChecklistTemplate) {
        _activeChecklist.value = template
        _activeChecklistItems.value = template.items.mapIndexed { index, text ->
            ActiveChecklistItem(id = index, text = text, status = CheckItemStatus.UNCHECKED)
        }
    }

    fun updateChecklistItemStatus(itemId: Int, status: CheckItemStatus) {
        _activeChecklistItems.value = _activeChecklistItems.value.map { item ->
            if (item.id == itemId) item.copy(status = status) else item
        }
    }

    fun updateChecklistItemComment(itemId: Int, comment: String) {
        _activeChecklistItems.value = _activeChecklistItems.value.map { item ->
            if (item.id == itemId) item.copy(comment = comment) else item
        }
    }

    fun saveActiveChecklistReport(notes: String = "") {
        val template = _activeChecklist.value ?: return
        val items = _activeChecklistItems.value
        val total = items.size
        val passed = items.count { it.status == CheckItemStatus.PASS }
        val failed = items.count { it.status == CheckItemStatus.FAIL }
        val na = items.count { it.status == CheckItemStatus.NA }
        val scorePercent = if (total - na > 0) ((passed.toDouble() / (total - na)) * 100).toInt() else 100

        val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        val dateStr = sdf.format(Date())

        viewModelScope.launch {
            repository.saveChecklistReport(
                SavedChecklistReportEntity(
                    checklistTitle = template.title,
                    category = template.category,
                    dateString = dateStr,
                    totalItems = total,
                    passedCount = passed,
                    failedCount = failed,
                    naCount = na,
                    scorePercentage = scorePercent,
                    summaryNotes = notes
                )
            )
            _activeChecklist.value = null
            _activeChecklistItems.value = emptyList()
        }
    }

    fun deleteChecklistReport(report: SavedChecklistReportEntity) {
        viewModelScope.launch { repository.deleteChecklistReport(report) }
    }

    // --- Active JSA Builder State ---
    private val _jsaJobTitle = MutableStateFlow("")
    val jsaJobTitle: StateFlow<String> = _jsaJobTitle.asStateFlow()
    fun setJsaJobTitle(title: String) { _jsaJobTitle.value = title }

    private val _jsaLocation = MutableStateFlow("")
    val jsaLocation: StateFlow<String> = _jsaLocation.asStateFlow()
    fun setJsaLocation(loc: String) { _jsaLocation.value = loc }

    private val _jsaActivity = MutableStateFlow("")
    val jsaActivity: StateFlow<String> = _jsaActivity.asStateFlow()
    fun setJsaActivity(act: String) { _jsaActivity.value = act }

    private val _jsaSteps = MutableStateFlow<List<JsaStepModel>>(
        listOf(
            JsaStepModel(
                stepNumber = 1,
                jobStep = "Pre-job inspection & area barricading",
                hazard = "Damaged tools, unauthorized personnel entering",
                consequence = "Laceration or dropped object impact",
                existingControls = "Visual tool check, warning signs",
                additionalControls = "Inspect calibration tag; erect physical barrier",
                ppe = "Safety helmet, safety shoes, eye protection",
                likelihood = 2,
                severity = 3,
                riskScore = 6
            )
        )
    )
    val jsaSteps: StateFlow<List<JsaStepModel>> = _jsaSteps.asStateFlow()

    fun addJsaStep() {
        val nextNum = _jsaSteps.value.size + 1
        _jsaSteps.value = _jsaSteps.value + JsaStepModel(
            stepNumber = nextNum,
            jobStep = "Step $nextNum activity",
            hazard = "Identify primary hazard",
            consequence = "Potential injury / damage",
            existingControls = "Standard procedure",
            additionalControls = "Hierarchy control",
            ppe = "Appropriate PPE",
            likelihood = 2,
            severity = 3,
            riskScore = 6
        )
    }

    fun removeJsaStep(index: Int) {
        if (_jsaSteps.value.size > 1 && index in _jsaSteps.value.indices) {
            val updated = _jsaSteps.value.toMutableList()
            updated.removeAt(index)
            _jsaSteps.value = updated.mapIndexed { i, step -> step.copy(stepNumber = i + 1) }
        }
    }

    fun updateJsaStep(index: Int, step: JsaStepModel) {
        if (index in _jsaSteps.value.indices) {
            val updated = _jsaSteps.value.toMutableList()
            updated[index] = step
            _jsaSteps.value = updated
        }
    }

    private val _isGeneratingAiJsa = MutableStateFlow(false)
    val isGeneratingAiJsa: StateFlow<Boolean> = _isGeneratingAiJsa.asStateFlow()

    fun generateAiJsaDraft() {
        val title = _jsaJobTitle.value.ifBlank { "Industrial Maintenance Task" }
        val loc = _jsaLocation.value.ifBlank { "Plant Yard" }
        val act = _jsaActivity.value.ifBlank { "Mechanical Piping Service" }

        _isGeneratingAiJsa.value = true
        viewModelScope.launch {
            try {
                val jsonStr = GeminiSafetyService.generateJsaDraft(title, loc, act)
                val jsonArray = JSONArray(jsonStr)
                val newSteps = mutableListOf<JsaStepModel>()
                for (i in 0 until jsonArray.length()) {
                    val obj = jsonArray.optJSONObject(i)
                    if (obj != null) {
                        val lik = obj.optInt("likelihood", 2)
                        val sev = obj.optInt("severity", 3)
                        newSteps.add(
                            JsaStepModel(
                                stepNumber = i + 1,
                                jobStep = obj.optString("jobStep", "Step ${i + 1}"),
                                hazard = obj.optString("hazard", "Identified Hazard"),
                                consequence = obj.optString("consequence", "Potential Consequence"),
                                existingControls = obj.optString("existingControls", "Standard Control"),
                                additionalControls = obj.optString("additionalControls", "Additional Precaution"),
                                ppe = obj.optString("ppe", "Required PPE"),
                                likelihood = lik,
                                severity = sev,
                                riskScore = lik * sev
                            )
                        )
                    }
                }
                if (newSteps.isNotEmpty()) {
                    _jsaSteps.value = newSteps
                }
            } catch (e: Exception) {
                // Keep existing steps
            } finally {
                _isGeneratingAiJsa.value = false
            }
        }
    }

    fun saveCurrentJsa() {
        val title = _jsaJobTitle.value.ifBlank { "Job Safety Analysis Draft" }
        val loc = _jsaLocation.value.ifBlank { "General Plant" }
        val act = _jsaActivity.value.ifBlank { "Standard Activity" }
        val steps = _jsaSteps.value
        val maxRisk = steps.maxOfOrNull { it.riskScore } ?: 6

        val jsonArray = JSONArray()
        steps.forEach { step ->
            val obj = JSONObject().apply {
                put("stepNumber", step.stepNumber)
                put("jobStep", step.jobStep)
                put("hazard", step.hazard)
                put("consequence", step.consequence)
                put("existingControls", step.existingControls)
                put("additionalControls", step.additionalControls)
                put("ppe", step.ppe)
                put("likelihood", step.likelihood)
                put("severity", step.severity)
                put("riskScore", step.riskScore)
            }
            jsonArray.put(obj)
        }

        viewModelScope.launch {
            repository.saveJsa(
                SavedJsaEntity(
                    jobTitle = title,
                    location = loc,
                    activity = act,
                    stepsJson = jsonArray.toString(),
                    totalSteps = steps.size,
                    highestRiskScore = maxRisk
                )
            )
            _jsaJobTitle.value = ""
            _jsaLocation.value = ""
            _jsaActivity.value = ""
        }
    }

    fun deleteJsa(jsa: SavedJsaEntity) {
        viewModelScope.launch { repository.deleteJsa(jsa) }
    }

    // --- Active HIRA Builder State ---
    private val _hiraActivity = MutableStateFlow("")
    val hiraActivity: StateFlow<String> = _hiraActivity.asStateFlow()
    fun setHiraActivity(act: String) { _hiraActivity.value = act }

    private val _hiraHazard = MutableStateFlow("")
    val hiraHazard: StateFlow<String> = _hiraHazard.asStateFlow()
    fun setHiraHazard(haz: String) { _hiraHazard.value = haz }

    private val _hiraCause = MutableStateFlow("")
    val hiraCause: StateFlow<String> = _hiraCause.asStateFlow()
    fun setHiraCause(cause: String) { _hiraCause.value = cause }

    private val _hiraConsequence = MutableStateFlow("")
    val hiraConsequence: StateFlow<String> = _hiraConsequence.asStateFlow()
    fun setHiraConsequence(c: String) { _hiraConsequence.value = c }

    private val _hiraExisting = MutableStateFlow("")
    val hiraExisting: StateFlow<String> = _hiraExisting.asStateFlow()
    fun setHiraExisting(ext: String) { _hiraExisting.value = ext }

    private val _hiraLikelihood = MutableStateFlow(3)
    val hiraLikelihood: StateFlow<Int> = _hiraLikelihood.asStateFlow()
    fun setHiraLikelihood(v: Int) { _hiraLikelihood.value = v.coerceIn(1, 5) }

    private val _hiraSeverity = MutableStateFlow(3)
    val hiraSeverity: StateFlow<Int> = _hiraSeverity.asStateFlow()
    fun setHiraSeverity(v: Int) { _hiraSeverity.value = v.coerceIn(1, 5) }

    private val _hiraAdditional = MutableStateFlow("")
    val hiraAdditional: StateFlow<String> = _hiraAdditional.asStateFlow()
    fun setHiraAdditional(add: String) { _hiraAdditional.value = add }

    private val _hiraResidual = MutableStateFlow("Low (Score 3)")
    val hiraResidual: StateFlow<String> = _hiraResidual.asStateFlow()
    fun setHiraResidual(res: String) { _hiraResidual.value = res }

    fun saveHira() {
        viewModelScope.launch {
            repository.saveHira(
                SavedHiraEntity(
                    activity = _hiraActivity.value.ifBlank { "Field Activity" },
                    hazard = _hiraHazard.value.ifBlank { "General Hazard" },
                    cause = _hiraCause.value.ifBlank { "Direct Cause" },
                    consequence = _hiraConsequence.value.ifBlank { "Injury Risk" },
                    existingControls = _hiraExisting.value.ifBlank { "Standard Site Controls" },
                    likelihood = _hiraLikelihood.value,
                    severity = _hiraSeverity.value,
                    additionalControls = _hiraAdditional.value.ifBlank { "Enhanced Hierarchy Controls" },
                    residualRisk = _hiraResidual.value
                )
            )
            _hiraActivity.value = ""
            _hiraHazard.value = ""
            _hiraCause.value = ""
            _hiraConsequence.value = ""
            _hiraExisting.value = ""
            _hiraAdditional.value = ""
        }
    }

    fun deleteHira(hira: SavedHiraEntity) {
        viewModelScope.launch { repository.deleteHira(hira) }
    }

    // --- Field Reports (Near Miss / Unsafe Condition / Unsafe Act / Incident) ---
    fun saveFieldReport(
        type: String,
        title: String,
        location: String,
        description: String,
        potential: String,
        immediate: String,
        corrective: String
    ) {
        val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        val dateStr = sdf.format(Date())
        viewModelScope.launch {
            repository.saveFieldReport(
                SavedFieldReportEntity(
                    reportType = type,
                    title = title,
                    location = location,
                    dateString = dateStr,
                    description = description,
                    potentialConsequence = potential,
                    immediateAction = immediate,
                    recommendedCorrectiveAction = corrective
                )
            )
        }
    }

    fun deleteFieldReport(report: SavedFieldReportEntity) {
        viewModelScope.launch { repository.deleteFieldReport(report) }
    }

    // --- AI Assistant Chat State (Grounded "Ask Safety" Mode) ---
    data class ChatMessage(
        val isUser: Boolean,
        val text: String,
        val timestamp: String,
        val hazards: List<String> = emptyList(),
        val controls: List<String> = emptyList(),
        val ppe: List<String> = emptyList(),
        val citation: SourceCitation? = null,
        val isVerified: Boolean = true
    )

    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(
        listOf(
            ChatMessage(
                isUser = false,
                text = "Hello! I am your Ask Safety Field Advisor. I answer strictly from approved industrial safety documents and standard operating procedures (e.g. Daily Safety Inspection, Working at Height, Confined Space Entry, Hot Work, LOTO).",
                timestamp = "Now",
                citation = SourceCitation("Approved Safety Library", "Authoritative SOPs", "Index", "Rev 2026.1", "15 Aug 2026"),
                isVerified = true
            )
        )
    )
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _isAiReplying = MutableStateFlow(false)
    val isAiReplying: StateFlow<Boolean> = _isAiReplying.asStateFlow()

    fun sendChatMessage(prompt: String) {
        if (prompt.isBlank()) return
        val userMsg = ChatMessage(isUser = true, text = prompt, timestamp = "Just now")
        _chatMessages.value = _chatMessages.value + userMsg
        _isAiReplying.value = true

        viewModelScope.launch {
            // Retrieve grounded authoritative knowledge
            val grounded = SafetyKnowledgeRetriever.retrieveGroundedSafetyAnswer(prompt, _currentLanguage.value)
            val aiMsg = if (grounded.isVerified) {
                ChatMessage(
                    isUser = false,
                    text = grounded.answer,
                    timestamp = "Just now",
                    hazards = grounded.hazards,
                    controls = grounded.controls,
                    ppe = grounded.ppe,
                    citation = grounded.citation,
                    isVerified = true
                )
            } else {
                // If not directly present, check online service while maintaining guardrail disclaimer
                val onlineReply = GeminiSafetyService.askSafetyAssistant(prompt)
                ChatMessage(
                    isUser = false,
                    text = "$onlineReply\n\n${grounded.answer}",
                    timestamp = "Just now",
                    hazards = grounded.hazards,
                    controls = grounded.controls,
                    ppe = grounded.ppe,
                    citation = null,
                    isVerified = false
                )
            }
            _chatMessages.value = _chatMessages.value + aiMsg
            _isAiReplying.value = false
        }
    }

    // --- AI Unsafe Condition Scanner State ---
    private val _scanResult = MutableStateFlow<ScanAnalysisResult?>(null)
    val scanResult: StateFlow<ScanAnalysisResult?> = _scanResult.asStateFlow()

    private val _isScanning = MutableStateFlow(false)
    val isScanning: StateFlow<Boolean> = _isScanning.asStateFlow()

    fun scanWorkplaceImage(bitmap: Bitmap?, userNotes: String = "") {
        _isScanning.value = true
        viewModelScope.launch {
            val result = GeminiSafetyService.analyzeWorkplaceImage(bitmap, userNotes)
            _scanResult.value = result
            _isScanning.value = false
        }
    }

    fun clearScanResult() {
        _scanResult.value = null
    }

    // --- Quizzes ---
    fun recordQuizResult(category: String, score: Int, total: Int) {
        viewModelScope.launch {
            repository.saveQuizResult(
                SavedQuizResultEntity(
                    quizCategory = category,
                    score = score,
                    totalQuestions = total
                )
            )
        }
    }

    // --- Safety Document / Manual Ingestion ---
    fun deleteSafetyDocument(doc: SavedSafetyDocumentEntity) {
        viewModelScope.launch {
            repository.deleteSafetyDocument(doc)
        }
    }

    fun saveParsedDocument(
        title: String,
        documentType: String,
        summary: String,
        hazards: List<String>,
        ppe: List<String>,
        checklist: List<String>,
        rules: List<String>,
        rawContent: String
    ) {
        viewModelScope.launch {
            val hazardsJson = JSONArray(hazards).toString()
            val ppeJson = JSONArray(ppe).toString()
            val chkJson = JSONArray(checklist).toString()
            val rulesJson = JSONArray(rules).toString()

            repository.saveSafetyDocument(
                SavedSafetyDocumentEntity(
                    title = title,
                    documentType = documentType,
                    summary = summary,
                    hazardsJson = hazardsJson,
                    ppeJson = ppeJson,
                    checklistJson = chkJson,
                    rulesJson = rulesJson,
                    rawContent = rawContent
                )
            )
        }
    }
}

class SafetyViewModelFactory(private val repository: SafetyRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SafetyViewModel::class.java)) {
            return SafetyViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

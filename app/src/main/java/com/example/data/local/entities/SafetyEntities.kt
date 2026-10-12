package com.example.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_jsa")
data class SavedJsaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val jobTitle: String,
    val location: String,
    val activity: String,
    val stepsJson: String, // Serialized list of steps
    val totalSteps: Int,
    val highestRiskScore: Int,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "saved_hira")
data class SavedHiraEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val activity: String,
    val hazard: String,
    val cause: String,
    val consequence: String,
    val existingControls: String,
    val likelihood: Int,
    val severity: Int,
    val riskScore: Int = likelihood * severity,
    val additionalControls: String,
    val residualRisk: String = "Low",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "saved_checklist_reports")
data class SavedChecklistReportEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val checklistTitle: String,
    val category: String,
    val dateString: String,
    val totalItems: Int,
    val passedCount: Int,
    val failedCount: Int,
    val naCount: Int,
    val scorePercentage: Int,
    val summaryNotes: String = "",
    val itemsJson: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "saved_field_reports")
data class SavedFieldReportEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val reportType: String, // "Near Miss", "Unsafe Condition", "Unsafe Act", "Incident"
    val title: String,
    val location: String,
    val dateString: String,
    val description: String,
    val potentialConsequence: String = "",
    val immediateAction: String = "",
    val recommendedCorrectiveAction: String = "",
    val photoNote: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "saved_risk_assessments")
data class SavedRiskAssessmentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val taskTitle: String,
    val likelihood: Int,
    val severity: Int,
    val riskScore: Int,
    val riskLevel: String, // LOW, MEDIUM, HIGH, CRITICAL
    val recommendedAction: String,
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "saved_quiz_results")
data class SavedQuizResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val quizCategory: String,
    val score: Int,
    val totalQuestions: Int,
    val completedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "saved_safety_documents")
data class SavedSafetyDocumentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val documentType: String, // "Site SOP", "PPT Training Deck", "Safety Manual PDF", "Permit Guideline"
    val summary: String,
    val hazardsJson: String,
    val ppeJson: String,
    val checklistJson: String,
    val rulesJson: String,
    val rawContent: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "saved_salary_calculations")
data class SavedSalaryCalcEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val monthYear: String,
    val basic: Double,
    val da: Double,
    val hra: Double,
    val allowances: Double,
    val workingDays: Int,
    val presentDays: Int,
    val leaveDays: Int,
    val otHours: Double,
    val otRate: Double,
    val otEarnings: Double,
    val grossSalary: Double,
    val totalDeductions: Double,
    val netSalary: Double,
    val note: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "saved_ot_calculations")
data class SavedOtCalcEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val shiftStart: String,
    val shiftEnd: String,
    val breakMinutes: Int,
    val normalHours: Double,
    val otHours: Double,
    val otRatePerHour: Double,
    val otAmount: Double,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "saved_attendance_records")
data class SavedAttendanceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val monthYear: String,
    val totalDays: Int,
    val presentDays: Int,
    val absentDays: Int,
    val halfDays: Int,
    val leaveDays: Int,
    val weeklyOffDays: Int,
    val holidays: Int,
    val payableDays: Double,
    val totalOtHours: Double,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "saved_bookmarks")
data class SavedBookmarkEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val itemType: String, // "TOPIC", "TOOL", "CHECKLIST", "PPE", "CHEMICAL", "LIMIT", "TEMPLATE"
    val itemId: String,
    val title: String,
    val subtitle: String,
    val category: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

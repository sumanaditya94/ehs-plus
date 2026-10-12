package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.local.entities.SavedAttendanceEntity
import com.example.data.local.entities.SavedBookmarkEntity
import com.example.data.local.entities.SavedChecklistReportEntity
import com.example.data.local.entities.SavedFieldReportEntity
import com.example.data.local.entities.SavedHiraEntity
import com.example.data.local.entities.SavedJsaEntity
import com.example.data.local.entities.SavedOtCalcEntity
import com.example.data.local.entities.SavedQuizResultEntity
import com.example.data.local.entities.SavedRiskAssessmentEntity
import com.example.data.local.entities.SavedSafetyDocumentEntity
import com.example.data.local.entities.SavedSalaryCalcEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SafetyDao {

    // JSA
    @Query("SELECT * FROM saved_jsa ORDER BY createdAt DESC")
    fun getAllSavedJsas(): Flow<List<SavedJsaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJsa(jsa: SavedJsaEntity): Long

    @Delete
    suspend fun deleteJsa(jsa: SavedJsaEntity)

    // HIRA
    @Query("SELECT * FROM saved_hira ORDER BY createdAt DESC")
    fun getAllSavedHiras(): Flow<List<SavedHiraEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHira(hira: SavedHiraEntity): Long

    @Delete
    suspend fun deleteHira(hira: SavedHiraEntity)

    // Checklist Reports
    @Query("SELECT * FROM saved_checklist_reports ORDER BY createdAt DESC")
    fun getAllChecklistReports(): Flow<List<SavedChecklistReportEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChecklistReport(report: SavedChecklistReportEntity): Long

    @Delete
    suspend fun deleteChecklistReport(report: SavedChecklistReportEntity)

    // Field Reports (Near Miss, Unsafe Condition, Unsafe Act, Incident)
    @Query("SELECT * FROM saved_field_reports ORDER BY createdAt DESC")
    fun getAllFieldReports(): Flow<List<SavedFieldReportEntity>>

    @Query("SELECT * FROM saved_field_reports WHERE reportType = :type ORDER BY createdAt DESC")
    fun getFieldReportsByType(type: String): Flow<List<SavedFieldReportEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFieldReport(report: SavedFieldReportEntity): Long

    @Delete
    suspend fun deleteFieldReport(report: SavedFieldReportEntity)

    // Risk Assessments
    @Query("SELECT * FROM saved_risk_assessments ORDER BY createdAt DESC")
    fun getAllSavedRiskAssessments(): Flow<List<SavedRiskAssessmentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRiskAssessment(assessment: SavedRiskAssessmentEntity): Long

    @Delete
    suspend fun deleteRiskAssessment(assessment: SavedRiskAssessmentEntity)

    // Quiz Results
    @Query("SELECT * FROM saved_quiz_results ORDER BY completedAt DESC")
    fun getAllQuizResults(): Flow<List<SavedQuizResultEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizResult(result: SavedQuizResultEntity): Long

    // Safety Documents (Uploaded PDFs, PPTs, SOPs)
    @Query("SELECT * FROM saved_safety_documents ORDER BY createdAt DESC")
    fun getAllSafetyDocuments(): Flow<List<SavedSafetyDocumentEntity>>

    @Query("SELECT * FROM saved_safety_documents WHERE id = :id")
    suspend fun getSafetyDocumentById(id: Long): SavedSafetyDocumentEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSafetyDocument(doc: SavedSafetyDocumentEntity): Long

    @Delete
    suspend fun deleteSafetyDocument(doc: SavedSafetyDocumentEntity)

    // Salary Calculations
    @Query("SELECT * FROM saved_salary_calculations ORDER BY createdAt DESC")
    fun getAllSalaryCalculations(): Flow<List<SavedSalaryCalcEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSalaryCalculation(salary: SavedSalaryCalcEntity): Long

    @Delete
    suspend fun deleteSalaryCalculation(salary: SavedSalaryCalcEntity)

    // OT Calculations
    @Query("SELECT * FROM saved_ot_calculations ORDER BY createdAt DESC")
    fun getAllOtCalculations(): Flow<List<SavedOtCalcEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOtCalculation(ot: SavedOtCalcEntity): Long

    @Delete
    suspend fun deleteOtCalculation(ot: SavedOtCalcEntity)

    // Attendance Records
    @Query("SELECT * FROM saved_attendance_records ORDER BY createdAt DESC")
    fun getAllAttendanceRecords(): Flow<List<SavedAttendanceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendanceRecord(attendance: SavedAttendanceEntity): Long

    @Delete
    suspend fun deleteAttendanceRecord(attendance: SavedAttendanceEntity)

    // Bookmarks
    @Query("SELECT * FROM saved_bookmarks ORDER BY createdAt DESC")
    fun getAllBookmarks(): Flow<List<SavedBookmarkEntity>>

    @Query("SELECT * FROM saved_bookmarks WHERE itemId = :itemId LIMIT 1")
    suspend fun getBookmarkByItemId(itemId: String): SavedBookmarkEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: SavedBookmarkEntity): Long

    @Query("DELETE FROM saved_bookmarks WHERE itemId = :itemId")
    suspend fun deleteBookmarkByItemId(itemId: String)

    @Delete
    suspend fun deleteBookmark(bookmark: SavedBookmarkEntity)
}

package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.local.dao.SafetyDao
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        SavedJsaEntity::class,
        SavedHiraEntity::class,
        SavedChecklistReportEntity::class,
        SavedFieldReportEntity::class,
        SavedRiskAssessmentEntity::class,
        SavedQuizResultEntity::class,
        SavedSafetyDocumentEntity::class,
        SavedSalaryCalcEntity::class,
        SavedOtCalcEntity::class,
        SavedAttendanceEntity::class,
        SavedBookmarkEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun safetyDao(): SafetyDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "industrial_safety_toolkit_db"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                populateInitialSampleData(getDatabase(context).safetyDao())
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private suspend fun populateInitialSampleData(dao: SafetyDao) {
            // Seed generic sample JSA
            dao.insertJsa(
                SavedJsaEntity(
                    jobTitle = "Angle Grinder Pipe Cutting [SAMPLE]",
                    location = "Fabrication Yard",
                    activity = "Cutting 4-inch carbon steel pipe with 7-inch grinder",
                    stepsJson = """[
                        {"stepNumber":1,"jobStep":"Inspect angle grinder, wheel RPM rating, and disc condition","hazard":"Damaged disc, over-speed disc burst","consequence":"Severe lacerations, eye injury","existingControls":"Check wheel expiry and max RPM exceeds tool RPM","additionalControls":"Ensure guard is at 180° between wheel and operator","ppe":"Safety glasses + Face shield, Cut-resistant gloves, Leather apron","likelihood":2,"severity":4,"riskScore":8},
                        {"stepNumber":2,"jobStep":"Secure pipe in bench vise or pipe stand","hazard":"Workpiece spinning or shifting during cut","consequence":"Disc jamming, tool kickback","existingControls":"Clamp securely with two clamping points","additionalControls":"Clear sparks trajectory away from flammable items","ppe":"Safety shoes, Ear plugs","likelihood":2,"severity":3,"riskScore":6},
                        {"stepNumber":3,"jobStep":"Execute cutting operation with steady two-handed grip","hazard":"Hot sparks, wheel binding, noise","consequence":"Burns, hearing loss, kickback","existingControls":"Maintain firm two-handed grip, do not force cut","additionalControls":"Fire blanket placed beneath sparks zone; fire extinguisher within 5m","ppe":"Face shield, Hearing protection, Safety boots","likelihood":2,"severity":3,"riskScore":6}
                    ]""".trimIndent(),
                    totalSteps = 3,
                    highestRiskScore = 8
                )
            )

            // Seed generic sample HIRA
            dao.insertHira(
                SavedHiraEntity(
                    activity = "Working at Height on Modular Scaffolding [SAMPLE]",
                    hazard = "Fall of personnel from unprotected scaffold edge",
                    cause = "Missing intermediate guardrail or improper harness anchoring",
                    consequence = "Fatal fall or severe trauma",
                    existingControls = "Standard guardrails (top rail 42in, mid rail 21in, toe board)",
                    likelihood = 2,
                    severity = 5,
                    additionalControls = "100% tie-off with twin lanyards to certified overhead anchorage; daily scaffold inspection tag",
                    residualRisk = "Low (4)"
                )
            )

            // Seed sample field near miss report
            dao.insertFieldReport(
                SavedFieldReportEntity(
                    reportType = "Near Miss",
                    title = "Wrench Slipped on Grating [SAMPLE]",
                    location = "Compressor Deck Elevation +6m",
                    dateString = "Today, 08:30 AM",
                    description = "A 19mm open-end spanner slipped from technician's hand and dropped onto lower mezzanine grating. Area was barricaded below.",
                    potentialConsequence = "Struck-by injury to workers below if drop zone was unbarricaded.",
                    immediateAction = "Work paused, tool lanyard attached to technician's wrist.",
                    recommendedCorrectiveAction = "Enforce tool tethering policy for all hand tools used above 1.8m."
                )
            )

            // Seed sample standard site safety SOP document
            dao.insertSafetyDocument(
                SavedSafetyDocumentEntity(
                    title = "Plant Standard: Hot Work & Spark Containment Protocol",
                    documentType = "Site SOP",
                    summary = "Comprehensive requirements for all cutting, welding, and grinding operations within 15 meters of flammable storage areas.",
                    hazardsJson = """["Flammable vapors ignition", "Flying hot slag", "Toxic welding fumes", "Oxygen cylinder explosion"]""",
                    ppeJson = """["Auto-darkening welding helmet (EN 175)", "Split cowhide leather welding jacket", "Heavy-duty Kevlar gloves", "Steel toe boots (S3)"]""",
                    checklistJson = """["Continuous combustible gas monitoring (LEL < 1%)", "Pressurized 50L foam/water extinguisher within 5m", "Fire blanket spark containment up to 11m", "Trained standby fire watch positioned"]""",
                    rulesJson = """["1. Obtain valid Hot Work Permit (PTW) signed by Area Authority", "2. Clear 15m radius of all combustibles or seal with certified wet fire blankets", "3. Conduct 4-gas test before ignition and every 2 hours", "4. Maintain fire watch during work and for 60 minutes after work completion"]""",
                    rawContent = "Standard Operating Procedure for Hot Work & Fire Prevention. All welding, torch cutting, and grinding activities must adhere to OSHA 29 CFR 1910.252 and site Golden Safety Rules."
                )
            )
            dao.insertSafetyDocument(
                SavedSafetyDocumentEntity(
                    title = "Confined Space Entry & Rescue Protocol (SOP-CS-04)",
                    documentType = "Safety Manual PDF",
                    summary = "Mandatory safety sequence for entry into storage tanks, sumps, vessels, boilers, and underground pits.",
                    hazardsJson = """["Oxygen deficiency (<19.5%)", "Toxic gas accumulation (H2S, CO)", "Engulfment in granular solids/liquids", "Thermal heat stress"]""",
                    ppeJson = """["Full body retrieval harness with dorsal D-ring", "4-Gas personal detector with calibration", "Supplied air / SCBA for non-ventilated spaces", "Intrinsically safe headlamp (ATEX Zone 0)"]""",
                    checklistJson = """["Multi-depth atmospheric gas testing (Top, Middle, Bottom)", "Mechanical positive forced ventilation active", "Calibrated tripod winch and rescue retrieval kit stationed", "Authorized standby entrant logkeeper at entry manhole"]""",
                    rulesJson = """["1. Never enter without Confined Space Entry Permit and certified gas clearance", "2. Continuous ventilation must be maintained at all times during occupancy", "3. Attendant must maintain constant visual/radio contact with entrants", "4. Never attempt rescue without self-contained breathing apparatus (SCBA)"]""",
                    rawContent = "Confined Space Entry Procedure standard. Compliance with OSHA 1910.146 and IS 11972 is mandatory across all facilities."
                )
            )
        }
    }
}

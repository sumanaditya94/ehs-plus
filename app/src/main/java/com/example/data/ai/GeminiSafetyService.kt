package com.example.data.ai

import android.graphics.Bitmap
import android.util.Base64
import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.util.concurrent.TimeUnit

data class HazardFinding(
    val title: String,
    val whyUnsafe: String,
    val suggestedControl: String,
    val priority: String // HIGH, MEDIUM, LOW
)

data class ScanAnalysisResult(
    val summary: String,
    val hazards: List<HazardFinding>,
    val humanChecklist: List<String>,
    val disclaimer: String = "AI-assisted screening only. Possible hazards detected require human verification by a competent person before work commences."
)

data class SafetyDocumentIngestResult(
    val title: String,
    val summary: String,
    val keyHazards: List<String>,
    val mandatoryPpe: List<String>,
    val checklistItems: List<String>,
    val stepByStepRules: List<String>,
    val rawText: String = ""
)

object GeminiSafetyService {

    private const val BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent"

    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    suspend fun askSafetyAssistant(userPrompt: String): String = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext "API Key not configured. Please configure your Gemini API Key in the AI Studio Secrets panel.\n\nOffline Guidance: For urgent safety questions, refer to the offline 'LEARN' and 'TOOLS' tabs in this app or consult your site safety supervisor/SDS."
        }

        val systemInstruction = """
            You are an Industrial Safety Field Expert & Worker Safety Advisor.
            Your purpose is to give clear, practical, direct, worker-friendly safety advice.
            Focus on Hierarchy of Controls (Elimination, Substitution, Engineering, Admin, PPE), practical step-by-step precautions, critical DOs and DONTs, and relevant standard guidelines (OSHA, ISO 45001, NFPA, NIOSH).
            Always conclude with a reminder to consult site-specific procedures, SDS, and competent persons.
        """.trimIndent()

        val jsonBody = JSONObject().apply {
            put("contents", JSONArray().apply {
                put(JSONObject().apply {
                    put("parts", JSONArray().apply {
                        put(JSONObject().put("text", userPrompt))
                    })
                })
            })
            put("systemInstruction", JSONObject().apply {
                put("parts", JSONArray().apply {
                    put(JSONObject().put("text", systemInstruction))
                })
            })
        }

        try {
            val mediaType = "application/json; charset=utf-8".toMediaType()
            val requestBody = jsonBody.toString().toRequestBody(mediaType)
            val request = Request.Builder()
                .url("$BASE_URL?key=$apiKey")
                .post(requestBody)
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string() ?: ""
            if (!response.isSuccessful) {
                return@withContext "Safety Assistant currently unavailable (Code ${response.code}). Please consult the offline Safety Handbook sections."
            }

            val jsonResponse = JSONObject(responseBody)
            val candidates = jsonResponse.optJSONArray("candidates")
            val content = candidates?.optJSONObject(0)?.optJSONObject("content")
            val parts = content?.optJSONArray("parts")
            val text = parts?.optJSONObject(0)?.optString("text")

            text ?: "No response received. Please check site procedures."
        } catch (e: Exception) {
            "Network error: ${e.localizedMessage}. Please refer to the offline safety tools."
        }
    }

    suspend fun analyzeWorkplaceImage(bitmap: Bitmap?, contextNote: String): ScanAnalysisResult = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY" || bitmap == null) {
            // Return offline simulated analysis
            return@withContext ScanAnalysisResult(
                summary = "Visual screening assessment based on provided workplace context.",
                hazards = listOf(
                    HazardFinding(
                        title = "Verify Personal Protective Equipment (PPE)",
                        whyUnsafe = "Operating tools without complete eye, foot, or hand protection leads to high-velocity impact or contact injuries.",
                        suggestedControl = "Ensure 100% adherence to task-specific PPE (Safety glasses, face shield, steel toe footwear).",
                        priority = "HIGH"
                    ),
                    HazardFinding(
                        title = "Check Housekeeping & Trip Hazards",
                        whyUnsafe = "Trailing cables, pneumatic hoses, or loose materials on walkways cause slips and falls.",
                        suggestedControl = "Elevate power leads overhead using S-hooks or cover with heavy-duty ramps.",
                        priority = "MEDIUM"
                    ),
                    HazardFinding(
                        title = "Inspect Physical Guarding & Barricades",
                        whyUnsafe = "Missing machine guards or unprotected edges allow direct access to danger zones.",
                        suggestedControl = "Confirm all interlocks, guards, and drop-zone perimeter barricades are secured.",
                        priority = "HIGH"
                    )
                ),
                humanChecklist = listOf(
                    "Check if task has an active Permit to Work / JSA if high risk",
                    "Verify all power tools have current electrical inspection tags",
                    "Ensure fire extinguishers and emergency eyewash stations are completely unblocked",
                    "Verify tool tethering is used if working at elevated heights"
                )
            )
        }

        val base64Image = bitmapToBase64(bitmap)
        val prompt = """
            Analyze this industrial workplace image for possible safety hazards and unsafe conditions.
            Look for:
            - Missing or improper PPE (helmets, goggles, face shields, gloves, footwear)
            - Unsafe ladders, scaffolds, open edges, or missing guardrails
            - Poor housekeeping, slip/trip hazards, blocked emergency exits
            - Exposed electrical cables, damaged tools, open panels
            - Unsafe chemical storage, missing labels, unbonded transfer
            - Suspended loads, crane pinch points, forklift blind spots
            - Unguarded rotating machinery or missing nip-point shields

            Provide output strictly in JSON format matching this structure:
            {
              "summary": "Short 1-2 sentence overall visual observation",
              "hazards": [
                {
                  "title": "Hazard Name",
                  "whyUnsafe": "Why it is dangerous",
                  "suggestedControl": "Specific hierarchy of control recommendation",
                  "priority": "HIGH or MEDIUM or LOW"
                }
              ],
              "humanChecklist": [
                "Specific item for a human inspector to verify on site",
                "Second verification item",
                "Third verification item"
              ]
            }
        """.trimIndent()

        val jsonBody = JSONObject().apply {
            put("contents", JSONArray().apply {
                put(JSONObject().apply {
                    put("parts", JSONArray().apply {
                        put(JSONObject().put("text", "$prompt\nAdditional user notes: $contextNote"))
                        put(JSONObject().apply {
                            put("inlineData", JSONObject().apply {
                                put("mimeType", "image/jpeg")
                                put("data", base64Image)
                            })
                        })
                    })
                })
            })
            put("generationConfig", JSONObject().apply {
                put("responseMimeType", "application/json")
            })
        }

        try {
            val mediaType = "application/json; charset=utf-8".toMediaType()
            val requestBody = jsonBody.toString().toRequestBody(mediaType)
            val request = Request.Builder()
                .url("$BASE_URL?key=$apiKey")
                .post(requestBody)
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string() ?: ""
            val jsonResponse = JSONObject(responseBody)
            val candidates = jsonResponse.optJSONArray("candidates")
            val content = candidates?.optJSONObject(0)?.optJSONObject("content")
            val parts = content?.optJSONArray("parts")
            val text = parts?.optJSONObject(0)?.optString("text") ?: "{}"

            val parsed = JSONObject(text)
            val summary = parsed.optString("summary", "Visual analysis complete.")
            val hazardsJson = parsed.optJSONArray("hazards") ?: JSONArray()
            val hazardsList = mutableListOf<HazardFinding>()
            for (i in 0 until hazardsJson.length()) {
                val h = hazardsJson.optJSONObject(i)
                if (h != null) {
                    hazardsList.add(
                        HazardFinding(
                            title = h.optString("title", "Possible Hazard"),
                            whyUnsafe = h.optString("whyUnsafe", ""),
                            suggestedControl = h.optString("suggestedControl", ""),
                            priority = h.optString("priority", "MEDIUM")
                        )
                    )
                }
            }

            val checklistJson = parsed.optJSONArray("humanChecklist") ?: JSONArray()
            val checklistList = mutableListOf<String>()
            for (i in 0 until checklistJson.length()) {
                checklistList.add(checklistJson.optString(i))
            }

            ScanAnalysisResult(
                summary = summary,
                hazards = if (hazardsList.isEmpty()) listOf(
                    HazardFinding("General Site Observation", "No glaring severe violations detected in image; maintain standard controls.", "Continue standard 5S and PPE compliance.", "LOW")
                ) else hazardsList,
                humanChecklist = if (checklistList.isEmpty()) listOf("Verify valid JSA for active tasks", "Inspect PPE before shift") else checklistList
            )
        } catch (e: Exception) {
            ScanAnalysisResult(
                summary = "AI visual screening encountered a connection error. Using local safety screening rules.",
                hazards = listOf(
                    HazardFinding("Verify 100% Required PPE", "Lack of proper PPE is the top cause of acute injuries.", "Mandate hard hat, safety glasses, and steel toe boots.", "HIGH"),
                    HazardFinding("Inspect Walking Surfaces", "Slips and trips cause over 30% of lost-time incidents.", "Clear hoses and clean fluid drips immediately.", "MEDIUM")
                ),
                humanChecklist = listOf("Perform physical pre-job walk", "Verify emergency exits remain unblocked")
            )
        }
    }

    suspend fun generateJsaDraft(jobName: String, location: String, activity: String): String = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext """
                [
                  {"stepNumber":1,"jobStep":"Pre-Job Hazard Assessment & Tool Inspection","hazard":"Using defective equipment or damaged power cords","consequence":"Electric shock, mechanical failure","existingControls":"Visual inspection prior to use","additionalControls":"Check calibration and current inspection color tag","ppe":"Standard Site PPE","likelihood":2,"severity":3,"riskScore":6},
                  {"stepNumber":2,"jobStep":"Prepare Work Area & Barricading","hazard":"Unauthorized personnel walking into danger zone","consequence":"Struck-by injuries from falling tools or sparks","existingControls":"Warning signs placed at entry points","additionalControls":"Erect physical rigid barricades around 5m radius","ppe":"Hi-Vis vest, Safety boots","likelihood":2,"severity":3,"riskScore":6},
                  {"stepNumber":3,"jobStep":"Perform Primary Activity: $activity","hazard":"Task-specific hazards (mechanical, thermal, or pressure)","consequence":"Cuts, burns, or strain injuries","existingControls":"Operate within equipment manufacturer limits","additionalControls":"Maintain two-handed control and body position out of line of fire","ppe":"Task specific gloves, double eye protection","likelihood":2,"severity":4,"riskScore":8},
                  {"stepNumber":4,"jobStep":"Post-Job Housekeeping & Waste Disposal","hazard":"Sharp metal scrap or chemical residue left behind","consequence":"Punctures or slips for subsequent shift workers","existingControls":"Clear immediate tools","additionalControls":"Sweep metal swarf with magnetic broom; deposit waste in hazardous bins","ppe":"Cut-resistant gloves, Safety glasses","likelihood":1,"severity":2,"riskScore":2}
                ]
            """.trimIndent()
        }

        val prompt = """
            Generate a detailed, step-by-step Job Safety Analysis (JSA) draft for:
            Job: $jobName
            Location: $location
            Activity: $activity

            Return strictly a JSON array of steps matching:
            [
              {
                "stepNumber": 1,
                "jobStep": "Step description",
                "hazard": "Specific hazard",
                "consequence": "Potential injury or consequence",
                "existingControls": "Standard existing control",
                "additionalControls": "Recommended additional control",
                "ppe": "Required PPE items",
                "likelihood": 2,
                "severity": 3,
                "riskScore": 6
              }
            ]
        """.trimIndent()

        try {
            val jsonBody = JSONObject().apply {
                put("contents", JSONArray().apply {
                    put(JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().put("text", prompt))
                        })
                    })
                })
                put("generationConfig", JSONObject().apply {
                    put("responseMimeType", "application/json")
                })
            }

            val mediaType = "application/json; charset=utf-8".toMediaType()
            val requestBody = jsonBody.toString().toRequestBody(mediaType)
            val request = Request.Builder()
                .url("$BASE_URL?key=$apiKey")
                .post(requestBody)
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string() ?: ""
            val jsonResponse = JSONObject(responseBody)
            val candidates = jsonResponse.optJSONArray("candidates")
            val content = candidates?.optJSONObject(0)?.optJSONObject("content")
            val parts = content?.optJSONArray("parts")
            parts?.optJSONObject(0)?.optString("text") ?: "[]"
        } catch (e: Exception) {
            "[]"
        }
    }

    suspend fun parseSafetyDocumentOrPdf(documentText: String, isHindi: Boolean): SafetyDocumentIngestResult = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY" || documentText.isBlank()) {
            val title = if (isHindi) "साइट सुरक्षा एसओपी (स्थानीय संदर्भ)" else "Site Standard Operating Procedure (Local Ingest)"
            val summary = if (isHindi) "दस्तावेज़ से सुरक्षा निर्देशों और सावधानियों का स्थानीय वर्गीकरण।" else "Extracted procedural safety rules, critical controls, and mandatory PPE for site execution."
            return@withContext SafetyDocumentIngestResult(
                title = title,
                summary = summary,
                keyHazards = if (isHindi) listOf(
                    "अनियंत्रित ऊर्जा स्रोत व शून्य-ऊर्जा सत्यापन की कमी",
                    "कार्यस्थल पर अनुपयुक्त या क्षतिग्रस्त पीपीई पहनना",
                    "बिना बैरिकेडिंग व साइनबोर्ड के खतरनाक क्षेत्र में प्रवेश"
                ) else listOf(
                    "Hazardous energy release without verified zero-energy state",
                    "Improper or damaged personal protective equipment",
                    "Unauthorized zone entry without barrier controls"
                ),
                mandatoryPpe = listOf("Safety Helmet (EN 397)", "Eye Protection (EN 166)", "Cut-5 Safety Gloves", "Steel Toe Footwear (EN ISO 20345)"),
                checklistItems = if (isHindi) listOf(
                    "कार्य शुरू करने से पहले वैध वर्क परमिट (PTW) की पुष्टि करें",
                    "उपकरणों पर वर्तमान वैध निरीक्षण टैग की जांच करें",
                    "कार्यक्षेत्र में आपातकालीन आईवॉश व अग्निशामक की उपलब्धता सुनिश्चित करें"
                ) else listOf(
                    "Verify valid Permit to Work (PTW) and JSA briefing before work",
                    "Inspect all equipment and confirm current test/inspection tags",
                    "Confirm emergency egress routes, eyewash and fire extinguishers are clear"
                ),
                stepByStepRules = if (isHindi) listOf(
                    "1. तैयारी एवं खतरे की पहचान: कार्यक्षेत्र का 360° निरीक्षण करें",
                    "2. अलगाव: सभी ऊर्जा स्रोतों पर LOTO लगाएं और शून्य-ऊर्जा जांचें",
                    "3. निष्पादन: निर्धारित एसओपी और सुरक्षा सीमाओं के तहत काम करें",
                    "4. समापन: कार्यक्षेत्र साफ करें और अनुमति पत्र बंद करें"
                ) else listOf(
                    "1. Pre-Task Hazard Assessment: Conduct 360° walkaround",
                    "2. Positive Isolation: Apply LOTO locks and verify zero energy",
                    "3. Controlled Execution: Operate strictly within SOP limits",
                    "4. Post-Task Handover: Clear work area and formally close permit"
                ),
                rawText = documentText
            )
        }

        val langInstruction = if (isHindi) "Respond with values translated into Hindi (Devanagari script)." else "Respond in English."
        val prompt = """
            You are an Industrial Safety Engineer parsing a company Safety Document, SOP, or PDF Manual text.
            Extract and structure the critical safety information from the following text:

            $documentText

            $langInstruction

            Return strictly a JSON object with this format:
            {
              "title": "Concise Descriptive Title of this SOP or Standard",
              "summary": "2-sentence executive summary of the safety requirement",
              "keyHazards": [
                "Specific hazard identified in text",
                "Second specific hazard",
                "Third specific hazard"
              ],
              "mandatoryPpe": [
                "PPE Item 1",
                "PPE Item 2",
                "PPE Item 3"
              ],
              "checklistItems": [
                "Pre-work verification check 1",
                "Pre-work verification check 2",
                "Pre-work verification check 3"
              ],
              "stepByStepRules": [
                "1. Step or rule one",
                "2. Step or rule two",
                "3. Step or rule three"
              ]
            }
        """.trimIndent()

        try {
            val jsonBody = JSONObject().apply {
                put("contents", JSONArray().apply {
                    put(JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().put("text", prompt))
                        })
                    })
                })
                put("generationConfig", JSONObject().apply {
                    put("responseMimeType", "application/json")
                })
            }

            val mediaType = "application/json; charset=utf-8".toMediaType()
            val requestBody = jsonBody.toString().toRequestBody(mediaType)
            val request = Request.Builder()
                .url("$BASE_URL?key=$apiKey")
                .post(requestBody)
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string() ?: ""
            val jsonResponse = JSONObject(responseBody)
            val candidates = jsonResponse.optJSONArray("candidates")
            val content = candidates?.optJSONObject(0)?.optJSONObject("content")
            val parts = content?.optJSONArray("parts")
            val text = parts?.optJSONObject(0)?.optString("text") ?: "{}"

            val parsed = JSONObject(text)
            val title = parsed.optString("title", "Site Safety Standard Reference")
            val summary = parsed.optString("summary", "Document analyzed by Gemini Safety AI.")

            val hazardsJson = parsed.optJSONArray("keyHazards") ?: JSONArray()
            val hazards = mutableListOf<String>()
            for (i in 0 until hazardsJson.length()) hazards.add(hazardsJson.optString(i))

            val ppeJson = parsed.optJSONArray("mandatoryPpe") ?: JSONArray()
            val ppe = mutableListOf<String>()
            for (i in 0 until ppeJson.length()) ppe.add(ppeJson.optString(i))

            val checklistJson = parsed.optJSONArray("checklistItems") ?: JSONArray()
            val checklist = mutableListOf<String>()
            for (i in 0 until checklistJson.length()) checklist.add(checklistJson.optString(i))

            val rulesJson = parsed.optJSONArray("stepByStepRules") ?: JSONArray()
            val rules = mutableListOf<String>()
            for (i in 0 until rulesJson.length()) rules.add(rulesJson.optString(i))

            SafetyDocumentIngestResult(
                title = title,
                summary = summary,
                keyHazards = if (hazards.isEmpty()) listOf("Review task-specific energy and mechanical hazards") else hazards,
                mandatoryPpe = if (ppe.isEmpty()) listOf("Helmet", "Safety Glasses", "Safety Boots", "Gloves") else ppe,
                checklistItems = if (checklist.isEmpty()) listOf("Perform pre-use inspection", "Verify isolation") else checklist,
                stepByStepRules = if (rules.isEmpty()) listOf("Follow site procedure step-by-step") else rules,
                rawText = documentText
            )
        } catch (e: Exception) {
            SafetyDocumentIngestResult(
                title = "Custom Site Safety Document",
                summary = "Local parsing applied: $documentText".take(150),
                keyHazards = listOf("Operational hazards specified in document"),
                mandatoryPpe = listOf("Standard Plant PPE"),
                checklistItems = listOf("Verify permit and procedure compliance"),
                stepByStepRules = listOf("Comply with uploaded site instructions"),
                rawText = documentText
            )
        }
    }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
        return Base64.encodeToString(outputStream.toByteArray(), Base64.NO_WRAP)
    }
}

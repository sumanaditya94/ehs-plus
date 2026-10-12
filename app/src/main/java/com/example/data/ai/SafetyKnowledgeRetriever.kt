package com.example.data.ai

import com.example.data.language.AppLanguage
import com.example.data.model.SourceCitation
import com.example.data.repository.DocumentKnowledgeCatalog

data class AskSafetyResponse(
    val answer: String,
    val hazards: List<String>,
    val controls: List<String>,
    val ppe: List<String>,
    val citation: SourceCitation?,
    val isVerified: Boolean
)

object SafetyKnowledgeRetriever {

    /**
     * Answers queries ONLY from approved authoritative safety knowledge in the backend.
     * Enforces strict guardrail: never invent safety limits, never fabricate sources.
     * If information is not present, clearly states verified information is unavailable.
     */
    fun retrieveGroundedSafetyAnswer(query: String, language: AppLanguage): AskSafetyResponse {
        val q = query.trim().lowercase()
        val isHindi = language == AppLanguage.HINDI

        // 1. Direct match with Authoritative FAQs
        DocumentKnowledgeCatalog.safetyFaqs.forEach { faq ->
            val match = if (isHindi) {
                faq.questionHi.lowercase().contains(q) ||
                        q.split(" ").any { word -> word.length > 2 && faq.questionHi.lowercase().contains(word) }
            } else {
                faq.question.lowercase().contains(q) ||
                        q.split(" ").any { word -> word.length > 3 && faq.question.lowercase().contains(word) }
            }

            // Keyword semantic overrides
            val semanticHit = when {
                (q.contains("grind") || q.contains("ग्राइंड") || q.contains("चिंगारी")) && faq.id == "faq_grinding_ppe" -> true
                (q.contains("height") || q.contains("ऊंचाई") || q.contains("harness") || q.contains("हार्नेस") || q.contains("tie-off") || q.contains("टाई")) && faq.id == "faq_height_tieoff" -> true
                (q.contains("confined") || q.contains("सीमित") || q.contains("tank") || q.contains("टैंक") || q.contains("gas limit") || q.contains("गैस सीमा") || q.contains("o2") || q.contains("lel") || q.contains("h2s")) && faq.id == "faq_confined_gas_limits" -> true
                (q.contains("loto") || q.contains("लोटो") || q.contains("lockout") || q.contains("ताला") || q.contains("zero energy") || q.contains("शून्य ऊर्जा")) && faq.id == "faq_loto_steps" -> true
                else -> false
            }

            if (match || semanticHit) {
                return AskSafetyResponse(
                    answer = if (isHindi) faq.answerHi else faq.answer,
                    hazards = if (isHindi) faq.hazardsHi else faq.hazards,
                    controls = if (isHindi) faq.controlsHi else faq.controls,
                    ppe = if (isHindi) faq.ppeHi else faq.ppe,
                    citation = faq.citation,
                    isVerified = true
                )
            }
        }

        // 2. Match with Reference Cards (Safety Limits)
        DocumentKnowledgeCatalog.referenceCards.forEach { card ->
            if (q.contains(card.parameter.lowercase()) ||
                q.contains(card.parameterHi.lowercase()) ||
                (q.contains("oxygen") || q.contains("o2") || q.contains("ऑक्सीजन")) && card.id == "ref_o2" ||
                (q.contains("lel") || q.contains("explosive") || q.contains("ज्वलनशील")) && card.id == "ref_lel" ||
                (q.contains("h2s") || q.contains("hydrogen sulfide") || q.contains("सल्फाइड")) && card.id == "ref_h2s" ||
                (q.contains("clearance") || q.contains("11m") || q.contains("fire distance") || q.contains("दूरी")) && card.id == "ref_hot_work_radius"
            ) {
                val ans = if (isHindi) {
                    """
                    • पैरामीटर: ${card.parameterHi}
                    • सुरक्षित कार्य सीमा: ${card.safeRangeHi}
                    • चेतावनी सीमा: ${card.warningLimitHi}
                    • खतरनाक सीमा: ${card.dangerThresholdHi}
                    • महत्वपूर्ण चेतावनी: ${card.criticalWarningHi}
                    """.trimIndent()
                } else {
                    """
                    • Parameter: ${card.parameter}
                    • Safe Working Range: ${card.safeRange}
                    • Warning Limit: ${card.warningLimit}
                    • Danger Threshold: ${card.dangerThreshold}
                    • Critical Rule: ${card.criticalWarning}
                    """.trimIndent()
                }

                return AskSafetyResponse(
                    answer = ans,
                    hazards = if (isHindi) listOf(card.criticalWarningHi) else listOf(card.criticalWarning),
                    controls = if (isHindi) listOf("निरंतर मॉनिटरिंग", "कार्यस्थल अलार्म का पालन") else listOf("Continuous monitoring", "Adhere to site alarm threshold"),
                    ppe = if (isHindi) listOf("4-गैस पर्सनल मॉनिटर") else listOf("Calibrated multi-gas detector"),
                    citation = card.citation,
                    isVerified = true
                )
            }
        }

        // 3. Match with PPE Guidance
        DocumentKnowledgeCatalog.ppeGuidanceList.forEach { ppeItem ->
            if (q.contains(ppeItem.jobName.lowercase()) || q.contains(ppeItem.jobNameHi.lowercase())) {
                val ans = if (isHindi) {
                    "• अनिवार्य पीपीई:\n" + ppeItem.gearListHi.joinToString("\n") { "  - $it" } +
                            "\n• कारण: ${ppeItem.rationaleHi}\n• सीमाएं: ${ppeItem.limitationsHi.joinToString("; ")}"
                } else {
                    "• Mandatory PPE:\n" + ppeItem.gearList.joinToString("\n") { "  - $it" } +
                            "\n• Purpose: ${ppeItem.rationale}\n• Limitations: ${ppeItem.limitations.joinToString("; ")}"
                }

                return AskSafetyResponse(
                    answer = ans,
                    hazards = if (isHindi) listOf("कार्य विशिष्ट खतरे") else listOf("Task-specific hazards"),
                    controls = if (isHindi) listOf("100% पीपीई अनुपालन", "उपयोग-पूर्व निरीक्षण") else listOf("100% PPE adherence", "Pre-use inspection"),
                    ppe = if (isHindi) ppeItem.gearListHi else ppeItem.gearList,
                    citation = ppeItem.citation,
                    isVerified = true
                )
            }
        }

        // 4. Match with Inspection Template
        if (q.contains("inspection") || q.contains("निरीक्षण") || q.contains("daily checklist") || q.contains("दैनिक चेकलिस्ट")) {
            val template = DocumentKnowledgeCatalog.dailyInspectionTemplate
            val ans = if (isHindi) {
                """
                • प्रपत्र: ${template.titleHi} (${template.version})
                • स्रोत: ${template.documentSource}
                • मुख्य निरीक्षण श्रेणियां:
                  - पीपीई (PPE): 100% हेलमेट, चिनस्ट्रैप, जूते, चश्मा
                  - हाउसकीपिंग: रास्ते साफ, कोई तेल रिसाव नहीं
                  - विद्युत सुरक्षा: 30mA ELCB सक्रिय, केबल सुरक्षित
                  - अग्नि सुरक्षा: अग्निशामक यंत्र पिन सुरक्षित, 11m क्षेत्र साफ
                  - ऊंचाई पर कार्य: 1.8m से ऊपर 100% टाई-ऑफ, ग्रीन टैग मचान
                """.trimIndent()
            } else {
                """
                • Form: ${template.title} (${template.version})
                • Source: ${template.documentSource}
                • Core Inspection Areas:
                  - PPE: 100% Hard hat with chinstrap, safety shoes, eye protection
                  - Housekeeping: Walkways clear, no slip/oil hazards
                  - Electrical: 30mA ELCB operational, protected industrial cables
                  - Fire Safety: Inspected extinguisher in green, 11m hot work clearance
                  - Work at Height: Green tagged scaffolding, 100% tie-off above 1.8m
                """.trimIndent()
            }

            return AskSafetyResponse(
                answer = ans,
                hazards = if (isHindi) listOf("उल्लंघन से दुर्घटना का खतरा") else listOf("Non-compliance incident risk"),
                controls = if (isHindi) listOf("दैनिक प्री-शिफ्ट वॉकथ्रू", "FAIL मिलने पर तुरंत कार्य रोकना") else listOf("Daily pre-shift walkthrough", "Stop-work on FAIL"),
                ppe = if (isHindi) listOf("पूर्ण साइट पीपीई") else listOf("Full Site PPE"),
                citation = template.citation,
                isVerified = true
            )
        }

        // 5. If NOT in approved knowledge base -> STRICT UNCERTAINTY GUARDRAIL!
        // AI MUST NOT invent safety limits, regulatory requirements, chemical properties, engineering values or procedures.
        val unverifiedMessage = if (isHindi) {
            """
            ⚠️ सत्यापित जानकारी अनुपलब्ध है (Information Not in Approved Knowledge Base)

            • यह जानकारी वर्तमान में अनुमोदित औद्योगिक सुरक्षा पुस्तकालय (Approved Safety Documents) में उपलब्ध नहीं है।
            • सुरक्षा नियमों के तहत, प्रणाली द्वारा कोई भी काल्पनिक सुरक्षा सीमा, नियम या रासायनिक गुण नहीं बनाया जा सकता।
            • आवश्यक कदम:
              - किसी भी अप्रमाणित तरीके से कार्य न करें।
              - अपने साइट ईएचएस अधिकारी (Safety Officer) या प्रमाणित निर्माता SDS से परामर्श लें।
              - कार्य से पहले अधिकृत परमिट (PTW) प्राप्त करें।
            """.trimIndent()
        } else {
            """
            ⚠️ Verified information is unavailable in the approved knowledge base.

            • This specific query is not present in the approved industrial safety documents repository.
            • Under industrial safety protocols, the AI is strictly prohibited from inventing safety limits, regulations, chemical values, or unverified procedures.
            • Recommended Action:
              - Do NOT proceed on assumptions or unverified advice.
              - Consult your site Safety Officer / EHS Department directly.
              - Refer to the manufacturer's certified Safety Data Sheet (SDS) or approved site SOP.
              - Ensure an approved Permit to Work (PTW) is active before commencing work.
            """.trimIndent()
        }

        return AskSafetyResponse(
            answer = unverifiedMessage,
            hazards = emptyList(),
            controls = if (isHindi) listOf("कार्य रोकें और ईएचएस अधिकारी से परामर्श लें") else listOf("Stop work and consult EHS Officer"),
            ppe = emptyList(),
            citation = null,
            isVerified = false
        )
    }
}

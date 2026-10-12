package com.example.data.language

enum class AppLanguage(val code: String, val displayName: String, val nativeName: String) {
    ENGLISH("en", "English", "English"),
    HINDI("hi", "Hindi", "हिन्दी")
}

object SafetyStrings {

    fun get(key: String, language: AppLanguage): String {
        val hindi = language == AppLanguage.HINDI
        return when (key) {
            // Navigation
            "nav_home" -> if (hindi) "होम" else "Home"
            "nav_learn" -> if (hindi) "सीखें" else "Learn"
            "nav_tools" -> if (hindi) "टूल्स" else "Tools"
            "nav_ppe" -> if (hindi) "पीपीई" else "PPE"
            "nav_ai" -> if (hindi) "एआई सलाहकार" else "AI Advisor"

            // Header & App Info
            "app_title" -> if (hindi) "औद्योगिक सुरक्षा फील्ड गाइड" else "INDUSTRIAL SAFETY"
            "app_tagline" -> if (hindi) "सुरक्षा सीखें • जांचें • सुरक्षित काम करें" else "Learn. Check. Work Safe."
            "offline_ready" -> if (hindi) "ऑफलाइन तैयार" else "Offline Ready"
            "handbook_updated" -> if (hindi) "हैंडबुक एवं फील्ड टूल्स" else "Handbook & Field Tools"
            "language_switch" -> if (hindi) "English" else "हिन्दी"

            // Search
            "search_placeholder" -> if (hindi) "खोजें: विषय, पीपीई, टूल्स, रसायन, गैस सीमा..." else "Search topics, PPE, tools, chemicals, limits..."
            "search_results" -> if (hindi) "खोज परिणाम" else "SEARCH RESULTS"
            "no_results" -> if (hindi) "कोई परिणाम नहीं मिला" else "No matching results found"

            // Quick Actions
            "quick_tools_title" -> if (hindi) "त्वरित सुरक्षा उपकरण" else "QUICK SAFETY TOOLS"
            "tool_risk_matrix" -> if (hindi) "5×5 जोखिम मैट्रिक्स" else "5×5 Risk Matrix"
            "tool_risk_matrix_desc" -> if (hindi) "संभावना × गंभीरता गणना" else "Likelihood × Severity calculation"
            "tool_jsa" -> if (hindi) "JSA कार्य सुरक्षा विश्लेषण" else "JSA / Job Safety"
            "tool_jsa_desc" -> if (hindi) "चरणबद्ध खतरा नियंत्रण" else "Step-by-step hazard control"
            "tool_gas_tester" -> if (hindi) "4-गैस परीक्षक" else "4-Gas Atmospheric"
            "tool_gas_tester_desc" -> if (hindi) "O2, LEL, H2S, CO सीमाएं" else "O2, LEL, H2S, CO limits"
            "tool_loto" -> if (hindi) "लोटो 9-स्टेप" else "LOTO 9-Step"
            "tool_loto_desc" -> if (hindi) "शून्य ऊर्जा पृथक्करण" else "Zero-energy isolation"
            "tool_checklists" -> if (hindi) "सुरक्षा चेकलिस्ट" else "Field Checklists"
            "tool_checklists_desc" -> if (hindi) "ग्राइंडर, मचान, विद्युत" else "Grinder, Scaffold, Electrical"
            "tool_chemicals" -> if (hindi) "रसायन एवं SDS" else "Chemicals & SDS"
            "tool_chemicals_desc" -> if (hindi) "रासायनिक अनुकूलता व खतरे" else "Chemical compatibility & hazards"
            "tool_limits" -> if (hindi) "सुरक्षित सीमाएं" else "Critical Limits"
            "tool_limits_desc" -> if (hindi) "ध्वनि, गर्मी, विद्युत मानक" else "Noise, Heat, Electric standards"
            "tool_reports" -> if (hindi) "फील्ड रिपोर्ट / नियर मिस" else "Field Observation"
            "tool_reports_desc" -> if (hindi) "खतरे व नियर मिस दर्ज करें" else "Log hazard or near-miss"
            "tool_ai_scanner" -> if (hindi) "एआई खतरा स्कैनर" else "AI Hazard Scanner"
            "tool_ai_scanner_desc" -> if (hindi) "फोटो से संभावित खतरा पहचान" else "Screen photos for site hazards"
            "tool_ppe_selector" -> if (hindi) "पीपीई चयनकर्ता" else "PPE Selector"
            "tool_ppe_selector_desc" -> if (hindi) "कार्य अनुसार सही पीपीई" else "Job-specific protective gear"

            // Golden Rule Card
            "golden_rule_badge" -> if (hindi) "आज का सुरक्षा स्वर्ण नियम" else "TODAY'S SAFETY GOLDEN RULE"
            "golden_rule_title" -> if (hindi) "ऊर्जा पृथक्करण के बिना कभी काम न करें" else "Never Work Without Zero Energy Verification"
            "golden_rule_body" -> if (hindi) "किसी भी मशीन या पाइपलाइन को खोलने से पहले विद्युत, वायवीय, और हाइड्रोलिक ऊर्जा को पूरी तरह से डिस्चार्ज और लॉक करें।" else "Always verify zero energy (electrical, pneumatic, hydraulic, and thermal) before entering or servicing any equipment."
            "golden_rule_sub" -> if (hindi) "नियम 1: अपनी जान को जोखिम में न डालें • जीवन पहले" else "Rule #1: Take 5 seconds to think before you touch"

            // Daily Spotlight
            "daily_spotlight" -> if (hindi) "दैनिक सुरक्षा विचार एवं टॉक" else "DAILY SAFETY SPOTLIGHT"
            "daily_quiz_btn" -> if (hindi) "दैनिक सुरक्षा क्विज खेलें" else "Take Daily Safety Quiz"
            "daily_tip_label" -> if (hindi) "सुरक्षा टिप:" else "Daily Tip:"
            "hazard_day_label" -> if (hindi) "आज का प्रमुख खतरा:" else "Hazard of the Day:"
            "ppe_day_label" -> if (hindi) "अनिवार्य पीपीई:" else "Mandatory PPE:"

            // Atmospheric widget
            "atm_widget_title" -> if (hindi) "4-गैस वायुमंडलीय सुरक्षा स्थिति" else "4-GAS ATMOSPHERIC SAFETY SNAPSHOT"
            "atm_widget_sub" -> if (hindi) "सीमित स्थान (Confined Space) पूर्व-प्रवेश मानक" else "Confined Space Pre-Entry Thresholds"
            "gas_o2" -> if (hindi) "ऑक्सीजन (O₂)" else "Oxygen (O₂)"
            "gas_lel" -> if (hindi) "ज्वलनशीलता (LEL)" else "Flammability (LEL)"
            "gas_h2s" -> if (hindi) "हाइड्रोजन सल्फाइड (H₂S)" else "Hydrogen Sulfide (H₂S)"
            "gas_co" -> if (hindi) "कार्बन मोनोऑक्साइड (CO)" else "Carbon Monoxide (CO)"
            "safe_status" -> if (hindi) "सुरक्षित" else "SAFE"
            "danger_status" -> if (hindi) "खतरा" else "DANGER"

            // Emergency SOS
            "sos_title" -> if (hindi) "आपातकालीन कार्यप्रणाली एवं सहायता" else "EMERGENCY SOS PROCEDURES"
            "sos_sub" -> if (hindi) "घटना या गैस रिसाव पर तुरंत यह कदम उठाएं" else "Immediate actions on incident or alarm"
            "sos_step1" -> if (hindi) "1. काम तुरंत रोकें और उपकरण बंद करें" else "1. Stop work immediately & isolate equipment"
            "sos_step2" -> if (hindi) "2. हवा की दिशा (Wind Sock) देखकर ऊपर की ओर जाएं" else "2. Evacuate cross-wind / up-wind to Assembly Point"
            "sos_step3" -> if (hindi) "3. कंट्रोल रूम या आपातकालीन नंबर पर कॉल करें" else "3. Raise Alarm & call Emergency Control Room"
            "sos_step4" -> if (hindi) "4. अप्रशिक्षित होने पर सीमित स्थान में न कूदें" else "4. Never enter a confined space for rescue without BA kit"

            // Permits
            "ptw_title" -> if (hindi) "उच्च जोखिम कार्य परमिट (PTW) संदर्भ" else "HIGH-RISK WORK PERMITS (PTW)"
            "ptw_hot_work" -> if (hindi) "हॉट वर्क (वेल्डिंग/कटिंग)" else "Hot Work Permit"
            "ptw_hot_work_sub" -> if (hindi) "अग्निशामक यंत्र, 11m क्षेत्र साफ, फायर वॉच आवश्यक" else "Fire watch, 11m radius cleared, extinguisher ready"
            "ptw_height" -> if (hindi) "ऊंचाई पर कार्य (1.8m+)" else "Working at Height Permit"
            "ptw_height_sub" -> if (hindi) "फुल बॉडी हार्नेस, 100% टाई-ऑफ, मजबूत एंकर" else "Full body harness, 100% tie-off, certified anchor"
            "ptw_confined" -> if (hindi) "सीमित स्थान प्रवेश" else "Confined Space Entry"
            "ptw_confined_sub" -> if (hindi) "गैस जांच, वेंटिलेशन, स्टैंडबाय व्यक्ति और बचाव योजना" else "Gas test, continuous ventilation, standby rescuer"
            "ptw_loto" -> if (hindi) "ऊर्जा पृथक्करण (LOTO)" else "Electrical & LOTO Permit"
            "ptw_loto_sub" -> if (hindi) "व्यक्तिगत ताला, टैग और शून्य ऊर्जा परीक्षण" else "Personal lock, tag & zero-energy test before work"

            // Local Data Summary
            "local_summary_title" -> if (hindi) "आपके स्थानीय फील्ड रिकॉर्ड्स" else "YOUR ON-DEVICE FIELD RECORDS"
            "saved_jsas" -> if (hindi) "सहेजे गए JSA" else "Saved JSAs"
            "saved_checks" -> if (hindi) "निरीक्षण रिपोर्ट" else "Checklists Done"
            "saved_reports" -> if (hindi) "फील्ड ऑब्जर्वेशन" else "Field Observations"
            "saved_quizzes" -> if (hindi) "क्विज पूर्ण" else "Quizzes Completed"

            // Quiz Modal
            "quiz_dialog_title" -> if (hindi) "दैनिक सुरक्षा ज्ञान मूल्यांकन" else "Daily Safety Knowledge Check"
            "quiz_correct" -> if (hindi) "✓ बिल्कुल सही जवाब!" else "✓ Correct Answer!"
            "quiz_incorrect" -> if (hindi) "✗ गलत जवाब" else "✗ Incorrect"
            "quiz_close" -> if (hindi) "बंद करें" else "Close"
            "quiz_submit" -> if (hindi) "जांचें" else "Submit"

            // ==================== LEARN SCREEN ====================
            "learn_header_title" -> if (hindi) "सुरक्षा ज्ञान एवं प्रशिक्षण हैंडबुक" else "Safety Knowledge & Handbook"
            "learn_header_subtitle" -> if (hindi) "अध्याय, टूलबॉक्स टॉक्स और सुरक्षा संकेत" else "Chapters, Toolbox Talks & Safety Signs"
            "tab_topics" -> if (hindi) "सुरक्षा विषय" else "Safety Topics"
            "tab_toolbox" -> if (hindi) "टूलबॉक्स टॉक्स" else "Toolbox Talks"
            "tab_signs" -> if (hindi) "सुरक्षा संकेत (Signs)" else "Safety Signs"
            "filter_all" -> if (hindi) "सभी" else "All"
            "cat_basic" -> if (hindi) "बुनियादी सुरक्षा" else "Basic Safety"
            "cat_industrial" -> if (hindi) "औद्योगिक सुरक्षा" else "Industrial Safety"
            "cat_high_risk" -> if (hindi) "उच्च जोखिम कार्य" else "High-Risk Work"
            "cat_emergency" -> if (hindi) "आपातकालीन प्रतिक्रिया" else "Emergency"
            "read_guide" -> if (hindi) "मार्गदर्शिका पढ़ें" else "Read Guide"
            "start_quiz" -> if (hindi) "विषय क्विज खेलें" else "Take Topic Quiz"
            "duration_label" -> if (hindi) "समय अवधि:" else "Duration:"
            "objective_label" -> if (hindi) "उद्देश्य:" else "Objective:"
            "hazards_label" -> if (hindi) "प्रमुख खतरे:" else "Key Hazards:"
            "discussion_label" -> if (hindi) "चर्चा के बिंदु:" else "Discussion Points:"
            "safe_practices" -> if (hindi) "सुरक्षित तरीके:" else "Safe Practices:"
            "unsafe_practices" -> if (hindi) "असुरक्षित तरीके (वर्जित):" else "Unsafe Practices (Forbidden):"
            "worker_questions" -> if (hindi) "श्रमिकों से पूछने योग्य प्रश्न:" else "Questions to Ask Workers:"
            "supervisor_notes" -> if (hindi) "सुपरवाइजर नोट्स:" else "Supervisor Notes:"
            "sign_meaning" -> if (hindi) "अर्थ व महत्व:" else "Meaning:"
            "sign_where" -> if (hindi) "कहाँ लगाया जाता है:" else "Where Applied:"
            "sign_action" -> if (hindi) "श्रमिक को क्या करना है:" else "Worker Action:"

            // ==================== TOOLS SCREEN ====================
            "tools_header_title" -> if (hindi) "औद्योगिक सुरक्षा फील्ड टूल्स" else "Industrial Field Utilities"
            "tools_header_subtitle" -> if (hindi) "जोखिम कैलकुलेटर, JSA निर्माता व LOTO गाइड" else "Risk Calculators, JSA Builder & LOTO Guide"
            "tab_risk_matrix" -> if (hindi) "जोखिम मैट्रिक्स" else "Risk Matrix"
            "tab_jsa_builder" -> if (hindi) "JSA निर्माता" else "JSA Builder"
            "tab_hira_builder" -> if (hindi) "HIRA / खतरा पहचान" else "HIRA / Hazard ID"
            "tab_gas_limits" -> if (hindi) "गैस परीक्षण" else "Gas Tester"
            "tab_noise_calc" -> if (hindi) "ध्वनि कैलकुलेटर" else "Noise Calculator"
            "tab_heat_stress" -> if (hindi) "गर्मी तनाव सूचकांक" else "Heat Stress"
            "tab_lifting_sling" -> if (hindi) "क्रेन व लिफ्टिंग स्लिंग" else "Lifting & Rigging"
            "tab_loto_guide" -> if (hindi) "LOTO 9-स्टेप" else "LOTO Protocol"
            "tab_fire_extinguisher" -> if (hindi) "अग्निशामक चयन" else "Fire Extinguisher"
            "tab_document_hub" -> if (hindi) "दस्तावेज़ व मैनुअल हब" else "PDF & SOP Hub"
            "tab_saved_records" -> if (hindi) "सहेजे गए रिकॉर्ड्स" else "Saved Records"

            // Risk Matrix Tool
            "risk_calculator_title" -> if (hindi) "5×5 जोखिम मैट्रिक्स कैलकुलेटर" else "5×5 RISK MATRIX CALCULATOR"
            "risk_calculator_sub" -> if (hindi) "संभावना × गंभीरता = कुल जोखिम स्कोर की गणना करें" else "Calculate Likelihood × Severity = Risk Score"
            "risk_task_label" -> if (hindi) "कार्य या गतिविधि का विवरण" else "Task or Activity Description"
            "risk_task_placeholder" -> if (hindi) "उदा. 11kV लाइन के पास मचान लगाना" else "e.g. Scaffolding erection near 11kV line"
            "likelihood_heading" -> if (hindi) "1. दुर्घटना की संभावना (Probability):" else "1. LIKELIHOOD (PROBABILITY):"
            "severity_heading" -> if (hindi) "2. संभावित परिणाम व गंभीरता (Consequence):" else "2. SEVERITY (CONSEQUENCE):"
            "like_1" -> if (hindi) "1: दुर्लभ (लगभग असंभव)" else "1: Rare (Almost impossible)"
            "like_2" -> if (hindi) "2: असंभाव्य (शायद ही कभी हो सकता है)" else "2: Unlikely (Could happen rarely)"
            "like_3" -> if (hindi) "3: संभव (कभी-कभार हो सकता है)" else "3: Possible (Could happen occasionally)"
            "like_4" -> if (hindi) "4: संभावित (होने की प्रबल संभावना)" else "4: Likely (Will probably occur)"
            "like_5" -> if (hindi) "5: लगभग निश्चित (बार-बार होने की आशंका)" else "5: Almost Certain (Expected frequently)"
            "sev_1" -> if (hindi) "1: नगण्य (प्राथमिक उपचार, खरोंच)" else "1: Negligible (First aid only, minor bruise)"
            "sev_2" -> if (hindi) "2: मामूली (हल्का चिकित्सा उपचार, प्रतिवर्ती)" else "2: Minor (Minor medical treatment, reversible)"
            "sev_3" -> if (hindi) "3: मध्यम (गुम समय चोट, फ्रैक्चर, 3+ दिन अनुपस्थिति)" else "3: Moderate (Lost time injury, fracture)"
            "sev_4" -> if (hindi) "4: गंभीर (स्थायी विकलांगता, अंग हानि)" else "4: Major (Permanent disability, limb loss)"
            "sev_5" -> if (hindi) "5: विनाशकारी (घातक दुर्घटना, कई लोगों की मृत्यु)" else "5: Catastrophic (Fatality or multi-casualty)"
            "risk_result_badge" -> if (hindi) "जोखिम स्कोर परिणाम" else "RISK SCORE RESULT"
            "risk_action_label" -> if (hindi) "आवश्यक सुरक्षा कार्रवाई:" else "Action Required:"
            "risk_save_btn" -> if (hindi) "जोखिम मूल्यांकन को फोन डेटाबेस में सहेजें" else "Save Risk Assessment to DB"
            "risk_saved_msg" -> if (hindi) "✓ जोखिम मूल्यांकन डेटाबेस में सुरक्षित सहेजा गया!" else "✓ Risk Assessment Saved to Database!"

            // JSA Builder
            "jsa_builder_title" -> if (hindi) "कार्य सुरक्षा विश्लेषण (JSA) निर्माता" else "JOB SAFETY ANALYSIS (JSA) BUILDER"
            "jsa_builder_sub" -> if (hindi) "चरणबद्ध खतरा पहचान एवं नियंत्रण पदानुक्रम" else "Step-by-step Hazard Identification & Control Hierarchy"
            "jsa_job_title_label" -> if (hindi) "कार्य का नाम (Job Title)" else "Job / Task Title"
            "jsa_location_label" -> if (hindi) "कार्यस्थल का स्थान (Location)" else "Plant Location / Area"
            "jsa_activity_label" -> if (hindi) "विस्तृत गतिविधि विवरण" else "Activity Scope & Details"
            "jsa_steps_heading" -> if (hindi) "कार्य के चरण (Job Steps):" else "Sequential Job Steps:"
            "jsa_step_card_title" -> if (hindi) "चरण #" else "Step #"
            "jsa_step_desc_label" -> if (hindi) "कार्य चरण का विवरण" else "Job Step Description"
            "jsa_hazard_label" -> if (hindi) "पहचाना गया खतरा" else "Identified Hazard"
            "jsa_consequence_label" -> if (hindi) "संभावित दुर्घटना व नुकसान" else "Potential Consequence"
            "jsa_controls_label" -> if (hindi) "नियंत्रण उपाय (इंजीनियरिंग, कार्यविधि, पीपीई)" else "Control Measures (Hierarchy of Controls)"
            "jsa_ppe_label" -> if (hindi) "आवश्यक सुरक्षा उपकरण (PPE)" else "Mandatory PPE for this step"
            "jsa_add_step_btn" -> if (hindi) "+ नया कार्य चरण जोड़ें" else "+ Add New Step"
            "jsa_ai_generate_btn" -> if (hindi) "✨ AI से JSA स्वतः उत्पन्न करें" else "✨ Auto-Generate JSA with AI"
            "jsa_save_db_btn" -> if (hindi) "JSA को डेटाबेस में सहेजें" else "Save JSA to Database"
            "jsa_saved_msg" -> if (hindi) "✓ JSA सफलतापूर्वक सहेजा गया!" else "✓ JSA Saved to Database!"

            // HIRA Builder
            "hira_title" -> if (hindi) "खतरा पहचान एवं जोखिम मूल्यांकन (HIRA)" else "HAZARD IDENTIFICATION & RISK ASSESSMENT (HIRA)"
            "hira_sub" -> if (hindi) "ISO 45001 अनुपालक विस्तृत खतरा रजिस्टर" else "ISO 45001 Standard Comprehensive Hazard Register"
            "hira_activity" -> if (hindi) "गतिविधि / प्रक्रिया" else "Activity / Process"
            "hira_hazard" -> if (hindi) "खतरे का प्रकार व विवरण" else "Hazard Description"
            "hira_cause" -> if (hindi) "मूल कारण (Root Cause)" else "Root Cause"
            "hira_consequence" -> if (hindi) "संभावित परिणाम / चोट" else "Potential Consequence"
            "hira_existing_controls" -> if (hindi) "मौजूदा नियंत्रण उपाय" else "Existing Controls in Place"
            "hira_additional_controls" -> if (hindi) "अतिरिक्त आवश्यक नियंत्रण" else "Additional Controls Needed"
            "hira_save_btn" -> if (hindi) "HIRA रिकॉर्ड डेटाबेस में सहेजें" else "Save HIRA Record to DB"
            "hira_saved_msg" -> if (hindi) "✓ HIRA रिकॉर्ड डेटाबेस में सुरक्षित सहेजा गया!" else "✓ HIRA Record Saved to Database!"

            // Atmospheric Gas Tester
            "gas_tester_title" -> if (hindi) "4-गैस वायुमंडलीय सुरक्षा परीक्षक" else "4-GAS ATMOSPHERIC SAFETY TESTER"
            "gas_tester_sub" -> if (hindi) "सीमित स्थान (Confined Space) में वास्तविक गैस मान दर्ज करें" else "Enter live atmospheric gas readings before entry"
            "gas_safe_limits_badge" -> if (hindi) "मानक सुरक्षित सीमाएं:" else "Permissible Safe Entry Ranges:"
            "gas_o2_range" -> if (hindi) "O₂: 19.5% - 23.5% (सामान्य 20.9%)" else "O₂: 19.5% - 23.5% (Normal 20.9%)"
            "gas_lel_range" -> if (hindi) "LEL: 0% - 10% से कम (< 10% LEL)" else "LEL: < 10% (0% target)"
            "gas_h2s_range" -> if (hindi) "H₂S: 0 ppm - 10 ppm से कम" else "H₂S: < 10 ppm (0 ppm target)"
            "gas_co_range" -> if (hindi) "CO: 0 ppm - 25 ppm से कम" else "CO: < 25 ppm (0 ppm target)"
            "gas_entry_status" -> if (hindi) "प्रवेश स्थिति मूल्यांकन:" else "Confined Space Entry Decision:"
            "gas_entry_permitted" -> if (hindi) "✓ प्रवेश की अनुमति है (सभी गैसें सुरक्षित सीमा में)" else "✓ ENTRY PERMITTED (All parameters within safe limits)"
            "gas_entry_prohibited" -> if (hindi) "⛔ प्रवेश पूर्णतः वर्जित! खतरनाक वातावरण!" else "⛔ ENTRY PROHIBITED! HAZARDOUS ATMOSPHERE!"

            // Noise Calculator
            "noise_calc_title" -> if (hindi) "औद्योगिक ध्वनि व शोर एक्सपोजर कैलकुलेटर" else "INDUSTRIAL NOISE EXPOSURE CALCULATOR"
            "noise_calc_sub" -> if (hindi) "OSHA / ISO 8-घंटे TWA व दैनिक शोर खुराक की गणना" else "OSHA 8-Hour TWA and Noise Dose Calculation"
            "noise_level_input" -> if (hindi) "ध्वनि दबाव स्तर (dBA)" else "Measured Sound Pressure Level (dBA)"
            "noise_duration_input" -> if (hindi) "एक्सपोजर की अवधि (घंटे)" else "Exposure Duration (Hours)"
            "noise_twa_result" -> if (hindi) "गणना की गई 8-घंटे TWA:" else "Calculated 8-Hour TWA:"
            "noise_dose_result" -> if (hindi) "दैनिक शोर खुराक (Dose %):" else "Daily Noise Dose (%):"
            "noise_protection_required" -> if (hindi) "अनुशंसित श्रवण सुरक्षा (Hearing PPE):" else "Required Hearing Protection:"
            "noise_safe_no_ppe" -> if (hindi) "सुरक्षित स्तर (85 dBA से कम - पीपीई वैकल्पिक)" else "Safe Level (< 85 dBA - PPE Optional)"
            "noise_action_plugs" -> if (hindi) "एक्शन लेवल पार (85-89 dBA) - इयर प्लग्स अनिवार्य (NRR 25+)" else "Action Level Exceeded (85-89 dBA) - Earplugs Mandatory"
            "noise_pel_plugs_muffs" -> if (hindi) "PEL सीमा पार (90-100 dBA) - इयर मफ्स / ड्यूल प्रोटेक्शन आवश्यक" else "PEL Exceeded (90-100 dBA) - Earmuffs / Dual Protection Mandatory"
            "noise_extreme_dual" -> if (hindi) "अत्यधिक शोर (100+ dBA) - ड्यूल सुरक्षा (प्लग्स + मफ्स) व समय सीमित करें" else "Extreme Noise (100+ dBA) - Dual Protection & Strict Time Limit"

            // Heat Stress Tool
            "heat_tool_title" -> if (hindi) "गर्मी तनाव व कार्य-विश्राम सूचकांक" else "HEAT STRESS & WORK-REST REGIMEN"
            "heat_tool_sub" -> if (hindi) "तापमान और आर्द्रता के आधार पर हीट रिस्क श्रेणी" else "Determine heat strain category, rest cycles & water intake"
            "temp_input" -> if (hindi) "तापमान (°C)" else "Ambient Temperature (°C)"
            "humidity_input" -> if (hindi) "सापेक्षिक आर्द्रता (%)" else "Relative Humidity (%)"
            "heat_index_result" -> if (hindi) "हीट इंडेक्स (अनुभूत तापमान):" else "Calculated Heat Index:"
            "heat_risk_cat" -> if (hindi) "गर्मी जोखिम श्रेणी:" else "Heat Risk Category:"
            "heat_work_rest" -> if (hindi) "अनुशंसित कार्य/विश्राम अनुपात:" else "Recommended Work/Rest Cycle:"
            "heat_hydration" -> if (hindi) "प्रति घंटे आवश्यक जल सेवन:" else "Mandatory Hydration Schedule:"
            "heat_normal" -> if (hindi) "सामान्य (सतर्क रहें, सामान्य पानी पिएं)" else "Normal (Stay hydrated, standard rest)"
            "heat_caution" -> if (hindi) "सावधानी (प्रति घंटे 500ml पानी, छायादार विश्राम)" else "Caution (500ml water/hr, shaded rest)"
            "heat_extreme_caution" -> if (hindi) "अत्यधिक सावधानी (45 मिनट कार्य / 15 मिनट विश्राम, 750ml पानी)" else "Extreme Caution (45 min work / 15 min rest, 750ml water/hr)"
            "heat_danger" -> if (hindi) "खतरा! (30 मिनट कार्य / 30 मिनट विश्राम, 1 लीटर पानी)" else "Danger! (30 min work / 30 min rest, 1L water/hr)"
            "heat_extreme_danger" -> if (hindi) "अत्यंत गंभीर खतरा! भारी शारीरिक कार्य तुरंत रोकें!" else "Extreme Danger! Halt heavy physical work immediately!"

            // Lifting & Rigging Tool
            "lifting_tool_title" -> if (hindi) "लिफ्टिंग व क्रेन स्लिंग एंगल कैलकुलेटर" else "LIFTING & RIGGING SLING CALCULATOR"
            "lifting_tool_sub" -> if (hindi) "स्लिंग कोण, प्रत्येक लेग पर तनाव व सुरक्षित भार क्षमता (SWL)" else "Calculate sling tension multiplier and safe working load"
            "load_weight_input" -> if (hindi) "कुल भार का वजन (kg / टन)" else "Total Load Weight (kg / Tonnes)"
            "sling_legs_input" -> if (hindi) "स्लिंग लेग्स की संख्या (1, 2, 3, 4)" else "Number of Sling Legs (1, 2, 3, 4)"
            "sling_angle_input" -> if (hindi) "स्लिंग कोण क्षैतिज से (Horizontal Angle: 30°, 45°, 60°, 90°)" else "Sling Horizontal Angle (30°, 45°, 60°, 90°)"
            "tension_per_leg" -> if (hindi) "प्रत्येक स्लिंग लेग पर वास्तविक तनाव:" else "Calculated Tension Per Leg:"
            "safety_factor_label" -> if (hindi) "न्यूनतम सुरक्षा गुणांक (Safety Factor): 5:1" else "Minimum Rigging Safety Factor: 5:1"
            "rigging_rules_heading" -> if (hindi) "अनिवार्य लिफ्टिंग सुरक्षा नियम:" else "Mandatory Rigging Safety Rules:"
            "rigging_rule_1" -> if (hindi) "1. स्लिंग का क्षैतिज कोण कभी भी 30° से कम न रखें (तनाव दोगुना हो जाता है)।" else "1. Never rig below 30° horizontal sling angle (tension doubles)."
            "rigging_rule_2" -> if (hindi) "2. तेज किनारों पर सॉफ्टनर / कॉर्नर प्रोटेक्टर का अनिवार्य उपयोग करें।" else "2. Always use softeners or corner protectors over sharp edges."
            "rigging_rule_3" -> if (hindi) "3. लिफ्टिंग ज़ोन को 100% बैरिकेड करें; लटके हुए भार के नीचे कोई व्यक्ति न हो।" else "3. Never allow personnel to walk or stand under suspended loads."

            // LOTO Protocol
            "loto_tool_title" -> if (hindi) "ऊर्जा पृथक्करण 9-स्टेप LOTO चेकलिस्ट" else "LOCKOUT / TAGOUT (LOTO) 9-STEP PROTOCOL"
            "loto_tool_sub" -> if (hindi) "शून्य ऊर्जा अवस्था (Zero Energy State) का चरणबद्ध सत्यापन" else "Step-by-Step Zero Energy Isolation & Restoration Verification"
            "loto_progress" -> if (hindi) "LOTO चरण पूर्ण:" else "LOTO Protocol Progress:"
            "loto_step_1" -> if (hindi) "1. तैयारी व सूचना (Notify affected workers)" else "1. Preparation & Notification"
            "loto_step_2" -> if (hindi) "2. उपकरण बंद करना (Orderly shutdown)" else "2. Machine Shutdown"
            "loto_step_3" -> if (hindi) "3. ऊर्जा पृथक्करण (Isolate all energy sources)" else "3. Energy Isolation"
            "loto_step_4" -> if (hindi) "4. लॉक एवं टैग लगाना (Apply personal lock & tag)" else "4. Apply LOTO Device"
            "loto_step_5" -> if (hindi) "5. संचित ऊर्जा डिस्चार्ज (Release stored energy)" else "5. Stored Energy Dissipation"
            "loto_step_6" -> if (hindi) "6. शून्य ऊर्जा सत्यापन (Verify zero energy / Try step)" else "6. Zero Energy Verification"
            "loto_step_7" -> if (hindi) "7. सुरक्षित कार्य निष्पादन (Perform service)" else "7. Perform Work Safely"
            "loto_step_8" -> if (hindi) "8. कार्यक्षेत्र निरीक्षण व कर्मी हटाना (Inspect area)" else "8. Area Inspection & Personnel Clear"
            "loto_step_9" -> if (hindi) "9. लॉक हटाना व पुनः चालू करना (Remove locks & notify)" else "9. Lock Removal & Restoration"

            // Fire Extinguisher Tool
            "fire_tool_title" -> if (hindi) "अग्निशामक चयन एवं PASS तकनीक" else "FIRE EXTINGUISHER SELECTOR & PASS GUIDE"
            "fire_tool_sub" -> if (hindi) "आग की श्रेणी के अनुसार सही अग्निशामक का चयन करें" else "Match extinguisher agent to fire class and operate safely"
            "fire_class_a" -> if (hindi) "क्लास A: ठोस पदार्थ (लकड़ी, कागज, कपड़ा, प्लास्टिक)" else "Class A: Ordinary Combustibles (Wood, paper, trash, plastics)"
            "fire_class_b" -> if (hindi) "क्लास B: ज्वलनशील तरल पदार्थ (पेट्रोल, डीजल, पेंट, सॉल्वेंट)" else "Class B: Flammable Liquids (Gasoline, oil, paints, solvents)"
            "fire_class_c" -> if (hindi) "क्लास C: ज्वलनशील गैसें व विद्युतीय उपकरण (लाइव वायर, पैनल)" else "Class C: Flammable Gases & Energized Electrical Equipment"
            "fire_class_d" -> if (hindi) "क्लास D: ज्वलनशील धातुएं (मैग्नीशियम, टाइटेनियम, सोडियम)" else "Class D: Combustible Metals (Magnesium, titanium, sodium)"
            "fire_class_k" -> if (hindi) "क्लास K/F: खाना पकाने का तेल व वसा (कमर्शियल किचन)" else "Class K: Cooking Oils & Deep-Fat Fryers"
            "pass_title" -> if (hindi) "PASS अग्निशामक संचालन विधि:" else "The P.A.S.S. Operation Method:"
            "pass_p" -> if (hindi) "P - Pull the pin (पिन खींचें और सील तोड़ें)" else "P - Pull the pin and break the tamper seal"
            "pass_a" -> if (hindi) "A - Aim at base (आग के आधार पर नोजल केंद्रित करें)" else "A - Aim nozzle low at the base of the fire"
            "pass_s1" -> if (hindi) "S - Squeeze lever (लीवर को धीरे-धीरे दबाएं)" else "S - Squeeze the discharge handle slowly and evenly"
            "pass_s2" -> if (hindi) "S - Sweep side to side (एक छोर से दूसरे छोर झाड़ू की तरह घुमाएं)" else "S - Sweep nozzle from side to side covering the base"

            // Saved Records Tool
            "saved_records_title" -> if (hindi) "स्थानीय डेटाबेस में सहेजे गए रिकॉर्ड्स" else "ON-DEVICE STORED SAFETY RECORDS"
            "saved_records_sub" -> if (hindi) "आपके द्वारा बनाए गए JSA, HIRA, रिस्क असेसमेंट व रिपोर्ट" else "All local JSAs, HIRAs, Risk Assessments & Audit Reports"
            "filter_jsa" -> if (hindi) "JSA रिकॉर्ड्स" else "Saved JSAs"
            "filter_hira" -> if (hindi) "HIRA रिकॉर्ड्स" else "Saved HIRAs"
            "filter_risk" -> if (hindi) "जोखिम आकलन" else "Risk Scores"
            "filter_docs" -> if (hindi) "दस्तावेज़ / PDF" else "PDF & Manuals"
            "delete_btn" -> if (hindi) "हटाएं" else "Delete"
            "empty_records_msg" -> if (hindi) "इस श्रेणी में कोई रिकॉर्ड नहीं मिला। टूल्स से नया रिकॉर्ड बनाएं।" else "No records found in this category. Generate new from Tools."

            // Document Hub (PDF / PPT Ingestion)
            "doc_hub_title" -> if (hindi) "कंपनी SOP, PDF एवं PPT ट्रेनिंग मैनुअल हब" else "COMPANY SOP, PDF & PPT TRAINING MANUAL HUB"
            "doc_hub_sub" -> if (hindi) "अपनी साइट के सुरक्षा दस्तावेज़, पीपीटी व एसओपी जोड़ें और AI से जांचें" else "Store, ingest, analyze and search site-specific safety manuals"
            "doc_upload_title" -> if (hindi) "+ नया सुरक्षा दस्तावेज़ या PPT टेक्स्ट जोड़ें" else "+ Ingest New Safety Manual / PPT / PDF"
            "doc_title_label" -> if (hindi) "दस्तावेज़ का नाम (Document Title)" else "Document / SOP Title"
            "doc_type_label" -> if (hindi) "दस्तावेज़ का प्रकार (Document Type)" else "Document Type"
            "doc_content_label" -> if (hindi) "दस्तावेज़ / PPT / SOP का टेक्स्ट यहाँ पेस्ट करें" else "Paste PDF / PPT / SOP Content Here"
            "doc_analyze_btn" -> if (hindi) "✨ AI से विश्लेषण करें व डेटाबेस में सहेजें" else "✨ Analyze with AI & Save to Database"
            "doc_quick_load_sample" -> if (hindi) "नमूना प्लांट SOP लोड करें" else "Load Standard Sample SOP"
            "doc_hazards_extracted" -> if (hindi) "निकाले गए प्रमुख खतरे:" else "Extracted Critical Hazards:"
            "doc_ppe_extracted" -> if (hindi) "अनिवार्य पीपीई:" else "Mandatory PPE:"
            "doc_checklist_extracted" -> if (hindi) "पूर्व-कार्य चेकलिस्ट:" else "Pre-Task Checklist:"
            "doc_rules_extracted" -> if (hindi) "चरणबद्ध सुरक्षा नियम:" else "Step-by-Step Operating Rules:"

            // ==================== PPE SCREEN ====================
            "ppe_header_title" -> if (hindi) "व्यक्तिगत सुरक्षा उपकरण (PPE)" else "PERSONAL PROTECTIVE EQUIPMENT"
            "ppe_header_subtitle" -> if (hindi) "पीपीई चयनकर्ता, सचित्र गाइड व मानक" else "PPE Selector, Visual Guide & Standards"
            "tab_ppe_selector" -> if (hindi) "कार्य अनुसार पीपीई चयन" else "What PPE Do I Need?"
            "tab_ppe_library" -> if (hindi) "पीपीई ज्ञान पुस्तकालय" else "PPE Knowledge Library"
            "tab_ppe_visual" -> if (hindi) "सचित्र पीपीई गाइड (Visuals)" else "Visual PPE Guide"
            "ppe_head_eye_title" -> if (hindi) "सिर, चेहरा एवं नेत्र सुरक्षा" else "Head, Eye & Face Protection"
            "ppe_head_eye_desc" -> if (hindi) "सुरक्षा हेलमेट (EN 397), सेफ्टी गॉगल्स (EN 166), फुल फेस शील्ड व इयर मफ्स" else "Hard hats (EN 397), safety goggles (EN 166), face shields & ear muffs"
            "ppe_respiratory_title" -> if (hindi) "श्वसन सुरक्षा उपकरण (Respirators)" else "Respiratory Protection"
            "ppe_respiratory_desc" -> if (hindi) "N95 डस्ट मास्क, ड्यूल केमिकल कार्ट्रिज हाफ मास्क, फुल फेस एवं SCBA सेट" else "N95 particulate respirators, chemical cartridge half masks & SCBA"
            "ppe_fall_body_title" -> if (hindi) "फॉल प्रोटेक्शन व बॉडी गियर" else "Fall Protection & Body Gear"
            "ppe_fall_body_desc" -> if (hindi) "फुल बॉडी हार्नेस (EN 361), शॉक एब्जॉर्बर लेन्यार्ड, हाई-ग्रिप ग्लव्स, स्टील टो बूट्स" else "Full body harness (EN 361), energy absorber lanyard, heavy gloves & safety boots"
            "required_gear_label" -> if (hindi) "अनिवार्य सुरक्षा उपकरण:" else "Required Equipment:"
            "why_required_label" -> if (hindi) "यह पीपीई क्यों आवश्यक है (खतरा नियंत्रण):" else "Why Required (Hazard Control):"
            "critical_warning_label" -> if (hindi) "महत्वपूर्ण चेतावनी (घूमती मशीनरी):" else "Critical Safety & Entanglement Warnings:"
            "pre_use_inspect_label" -> if (hindi) "उपयोग से पहले जांच (Checklist):" else "Pre-Use Inspection Checklist:"
            "when_to_use_label" -> if (hindi) "✓ कब उपयोग करें:" else "✓ WHEN TO USE:"
            "when_not_to_use_label" -> if (hindi) "✗ कब उपयोग न करें (खतरा):" else "✗ WHEN NOT TO USE:"
            "fitting_instructions_label" -> if (hindi) "सही पहनने व फिटिंग का तरीका:" else "Correct Wearing & Fitting Guide:"

            // ==================== CHECKLISTS SCREEN ====================
            "checklist_header_title" -> if (hindi) "फील्ड सुरक्षा चेकलिस्ट" else "PRE-USE FIELD CHECKLISTS"
            "checklist_header_subtitle" -> if (hindi) "उपकरण व कार्य शुरू करने से पूर्व निरीक्षण" else "Equipment Pre-Start & Structural Audits"
            "chk_pass" -> if (hindi) "पास / सुरक्षित" else "PASS"
            "chk_fail" -> if (hindi) "फेल / खतरा" else "FAIL"
            "chk_na" -> if (hindi) "लागू नहीं" else "N/A"
            "chk_complete_btn" -> if (hindi) "निरीक्षण पूरा करें व सहेजें" else "Complete & Save Inspection"
            "chk_saved_success" -> if (hindi) "चेकलिस्ट सफलतापूर्वक सहेजी गई!" else "Inspection Report Saved Locally!"

            // ==================== CHEMICALS SCREEN ====================
            "chem_header_title" -> if (hindi) "रासायनिक सुरक्षा एवं SDS गाइड" else "Chemical Safety & SDS Guide"
            "chem_header_subtitle" -> if (hindi) "रासायनिक अनुकूलता, GHS संकेत व प्राथमिक उपचार" else "Compatibility Matrix, GHS Symbols & First Aid"
            "chem_select_a" -> if (hindi) "पहला रसायन चुनें (Chemical A)" else "Select Chemical A"
            "chem_select_b" -> if (hindi) "दूसरा रसायन चुनें (Chemical B)" else "Select Chemical B"
            "chem_check_compat_btn" -> if (hindi) "अनुकूलता जांचें" else "Check Compatibility"
            "chem_compat_safe" -> if (hindi) "सुरक्षित अनुकूल (Compatible)" else "COMPATIBLE / SAFE"
            "chem_compat_danger" -> if (hindi) "अत्यंत खतरनाक - कभी न मिलाएं (INCOMPATIBLE DANGER)" else "INCOMPATIBLE - DANGER"
            "chem_spill_label" -> if (hindi) "रिसाव नियंत्रण (Spill Response):" else "Spill Response:"
            "chem_firstaid_label" -> if (hindi) "प्राथमिक उपचार (First Aid):" else "First Aid Measures:"
            "chem_storage_label" -> if (hindi) "भंडारण नियम (Storage):" else "Storage Requirements:"

            // ==================== SAFETY LIMITS SCREEN ====================
            "limits_header_title" -> if (hindi) "सुरक्षित सीमाएं एवं मानक" else "CRITICAL SAFETY LIMITS & THRESHOLDS"
            "limits_header_subtitle" -> if (hindi) "ध्वनि, तापमान, विद्युत व ऊंचाई मानक" else "OSHA/ISO Noise, Heat Index, Electrical & Height"
            "limits_noise" -> if (hindi) "ध्वनि सीमा (Noise dB)" else "Noise Exposure (dB)"
            "limits_heat" -> if (hindi) "गर्मी सूचकांक (Heat Index)" else "Heat Stress Index"
            "limits_electrical" -> if (hindi) "विद्युत करंट प्रभाव (mA)" else "Electrical Current (mA)"
            "limits_height" -> if (hindi) "ऊंचाई पर कार्य (Fall Clearance)" else "Fall Clearance (Meters)"

            // ==================== AI ADVISOR SCREEN ====================
            "ai_header_title" -> if (hindi) "एआई सुरक्षा सलाहकार" else "AI INDUSTRIAL SAFETY ADVISOR"
            "ai_header_subtitle" -> if (hindi) "मानक, SOP, खतरे व JSA पर तत्काल सलाह लें" else "Instant expert guidance for OSHA, ISO & Site SOPs"
            "ai_input_placeholder" -> if (hindi) "सुरक्षा प्रश्न पूछें या SOP का टेक्स्ट पेस्ट करें..." else "Ask safety questions or paste SOP text..."
            "ai_send_btn" -> if (hindi) "पूछें" else "Send"
            "ai_suggestion_1" -> if (hindi) "सीमित स्थान में प्रवेश से पहले क्या कदम उठाने चाहिए?" else "What are the 5 critical checks before confined space entry?"
            "ai_suggestion_2" -> if (hindi) "अमोनिया गैस रिसाव पर क्या प्राथमिक उपचार करें?" else "How to handle anhydrous ammonia gas leak emergency?"
            "ai_suggestion_3" -> if (hindi) "ग्राइंडर व्हील फटने से रोकने के क्या नियम हैं?" else "What are the pre-use checks for high-speed abrasive wheels?"

            // ==================== DOCUMENT KNOWLEDGE INGESTION SYSTEM ====================
            "doc_ingest_title" -> if (hindi) "दस्तावेज़ ज्ञान प्रणाली" else "DOCUMENT KNOWLEDGE SYSTEM"
            "doc_ingest_sub" -> if (hindi) "अनुमोदित PDF व PPT से निकाला गया आधिकारिक ज्ञानकोष" else "Structured Knowledge from Approved Safety Documents"
            "doc_lib_title" -> if (hindi) "मूल दस्तावेज़ पुस्तकालय" else "Approved Document Library"
            "doc_lib_sub" -> if (hindi) "साइट सुरक्षा मैनुअल, SOP एवं अधिकृत प्रशिक्षण PPT" else "Site Manuals, SOPs & Authorized Training PPTs"
            "doc_status_approved" -> if (hindi) "सत्यापित एवं प्रकाशित" else "Approved & Published"
            "doc_source_label" -> if (hindi) "स्रोत दस्तावेज़:" else "Source:"
            "doc_page_label" -> if (hindi) "पृष्ठ / स्लाइड:" else "Page / Slide:"
            "doc_version_label" -> if (hindi) "संस्करण:" else "Version:"
            "doc_updated_label" -> if (hindi) "अंतिम अद्यतन:" else "Updated:"
            
            // Multi-View Explorer Tabs
            "doc_view_original" -> if (hindi) "मूल दस्तावेज़" else "Original Document"
            "doc_view_learning" -> if (hindi) "सरल सीख" else "Simplified Learning"
            "doc_view_checklist" -> if (hindi) "चेकलिस्ट" else "Checklist"
            "doc_view_template" -> if (hindi) "प्रपत्र (Template)" else "Template"
            "doc_view_quiz" -> if (hindi) "क्विज" else "Quiz"
            "doc_view_reference" -> if (hindi) "संदर्भ मानक" else "Reference"

            // Document to Template Engine (Inspection Form)
            "template_engine_title" -> if (hindi) "निरीक्षण प्रपत्र इंजन" else "Inspection Template Engine"
            "gen_info_title" -> if (hindi) "सामान्य जानकारी (General Information)" else "General Information"
            "field_date" -> if (hindi) "निरीक्षण तिथि" else "Inspection Date"
            "field_area" -> if (hindi) "कार्य क्षेत्र / स्थान" else "Area / Location"
            "field_inspector" -> if (hindi) "निरीक्षक का नाम" else "Inspector Name"
            "field_shift" -> if (hindi) "शिफ्ट" else "Shift"
            "inspection_items_title" -> if (hindi) "निरीक्षण बिंदु (Inspection Items)" else "Inspection Items"
            "status_pass" -> if (hindi) "पास (PASS)" else "PASS"
            "status_fail" -> if (hindi) "फेल (FAIL)" else "FAIL"
            "status_na" -> if (hindi) "लागू नहीं (N/A)" else "N/A"
            "field_remarks" -> if (hindi) "टिप्पणी / रिमार्क्स" else "Remarks"
            "field_photo" -> if (hindi) "फोटो संलग्न करें" else "Attach Photo"
            "photo_attached" -> if (hindi) "फोटो संलग्न है" else "Photo Attached"
            "btn_generate_report" -> if (hindi) "रिपोर्ट तैयार करें" else "Generate Report"
            "btn_preview_report" -> if (hindi) "पूर्वावलोकन (Preview)" else "Preview Report"
            "btn_share_report" -> if (hindi) "शेयर करें (Share)" else "Share Report"
            "report_generated_success" -> if (hindi) "निरीक्षण रिपोर्ट सफलतापूर्वक तैयार हुई!" else "Inspection Report Successfully Generated!"

            // Document to Training Engine (Micro Learning)
            "micro_train_title" -> if (hindi) "माइक्रो-ट्रेनिंग मॉड्यूल" else "Micro-Training Modules"
            "learning_objective_label" -> if (hindi) "सीखने का उद्देश्य:" else "Learning Objective:"
            "explanation_label" -> if (hindi) "संक्षिप्त विवरण:" else "Short Explanation:"
            "dos_label" -> if (hindi) "क्या करें (DOs):" else "DO (Safe Practice):"
            "donts_label" -> if (hindi) "क्या न करें (DONTs):" else "DON'T (Unsafe):"
            "quick_question_label" -> if (hindi) "त्वरित प्रश्न (Quick Check):" else "Quick Check Question:"

            // Ask Safety Mode
            "ask_safety_title" -> if (hindi) "आस्क सेफ्टी (Ask Safety)" else "Ask Safety AI Advisor"
            "ask_safety_subtitle" -> if (hindi) "केवल अनुमोदित सुरक्षा ज्ञानकोष से सत्यापित उत्तर" else "Answers ONLY from Approved Document Knowledge Base"
            "ask_safety_badge" -> if (hindi) "सत्यापित ज्ञान मोड • जीरो हॉलुसिनेशन" else "VERIFIED KNOWLEDGE MODE • ZERO HALLUCINATION"
            "hazards_identified" -> if (hindi) "पहचाने गए खतरे:" else "Hazards Mentioned:"
            "controls_required" -> if (hindi) "नियंत्रण उपाय:" else "Required Controls:"
            "mandatory_ppe" -> if (hindi) "अनिवार्य पीपीई:" else "Mandatory PPE:"
            "uncertainty_warning" -> if (hindi) "⚠️ सत्यापित जानकारी अनुपलब्ध" else "⚠️ Verified Info Unavailable"

            // Knowledge Search Categories
            "search_cat_learn" -> if (hindi) "सीखें (Learn)" else "Learn"
            "search_cat_ppe" -> if (hindi) "पीपीई (PPE)" else "PPE"
            "search_cat_checklist" -> if (hindi) "चेकलिस्ट (Checklist)" else "Checklist"
            "search_cat_tools" -> if (hindi) "टूल्स (Tools)" else "Tools"
            "search_cat_templates" -> if (hindi) "प्रपत्र (Templates)" else "Templates"
            "search_cat_reference" -> if (hindi) "संदर्भ (Reference)" else "Reference"
            "search_cat_documents" -> if (hindi) "मूल दस्तावेज़ (Docs)" else "Original Documents"

            else -> key
        }
    }
}


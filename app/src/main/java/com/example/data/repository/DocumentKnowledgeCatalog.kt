package com.example.data.repository

import com.example.data.model.AuthoritativeDocument
import com.example.data.model.DocInspectionItem
import com.example.data.model.DocInspectionTemplate
import com.example.data.model.DocJobRiskTemplate
import com.example.data.model.DocLearningArticle
import com.example.data.model.DocMicroTrainingModule
import com.example.data.model.DocPpeGuidance
import com.example.data.model.DocReferenceCard
import com.example.data.model.DocSafetyFaq
import com.example.data.model.DocSearchCategory
import com.example.data.model.DocSearchResult
import com.example.data.model.DocToolboxTalkTemplate
import com.example.data.model.DocumentPage
import com.example.data.model.QuizQuestion
import com.example.data.model.SourceCitation

object DocumentKnowledgeCatalog {

    // -------------------------------------------------------------
    // 1. Authoritative Document Library (Approved & Published)
    // -------------------------------------------------------------
    val authoritativeDocuments = listOf(
        AuthoritativeDocument(
            id = "doc_daily_inspection",
            title = "Daily Site Safety Inspection SOP",
            titleHi = "दैनिक कार्यस्थल सुरक्षा निरीक्षण मानक संचालन प्रक्रिया",
            fileName = "Daily Safety Inspection.pdf",
            fileType = "PDF",
            version = "Rev 2026.1",
            approvedDate = "15 Aug 2026",
            totalPagesOrSlides = 16,
            category = "Site Inspection",
            categoryHi = "कार्यस्थल निरीक्षण",
            summary = "Authoritative procedure for pre-shift and daily walk-through audits covering PPE, Housekeeping, Electrical cords, Fire protection, and Work at Height.",
            summaryHi = "पीपीई, हाउसकीपिंग, विद्युत केबल, अग्निसुरक्षा एवं ऊंचाई कार्य के पूर्व-शिफ्ट एवं दैनिक निरीक्षण हेतु अधिकृत प्रक्रिया।",
            pages = listOf(
                DocumentPage(
                    pageNumber = 1,
                    title = "General Scope & Regulatory Framework",
                    titleHi = "सामान्य उद्देश्य एवं विनियामक ढांचा",
                    summary = "Mandates daily visual inspection by site safety officers and area supervisors before hazardous work commencement.",
                    summaryHi = "खतरनाक कार्य आरंभ होने से पूर्व साइट सुरक्षा अधिकारी और सुपरवाइजर द्वारा अनिवार्य दैनिक निरीक्षण।",
                    keyPoints = listOf(
                        "Applies to all operating shop floors, fabrication yards, and construction zones",
                        "Any item marked 'FAIL' requires immediate work-stoppage until mitigated",
                        "Signed daily inspection records must be archived for statutory audit compliance"
                    ),
                    keyPointsHi = listOf(
                        "सभी ऑपरेटिंग शॉप फ्लोर, फैब्रिकेशन यार्ड और निर्माण क्षेत्रों पर लागू",
                        "'FAIL' चिह्नित किसी भी बिंदु पर तुरंत काम रोकना अनिवार्य है जब तक सुधार न हो जाए",
                        "हस्ताक्षरित दैनिक निरीक्षण रिकॉर्ड ऑडिट अनुपालन के लिए सुरक्षित रखना आवश्यक है"
                    )
                ),
                DocumentPage(
                    pageNumber = 4,
                    title = "Daily Safety Inspection Checklist Matrix",
                    titleHi = "दैनिक सुरक्षा निरीक्षण चेकलिस्ट मैट्रिक्स",
                    summary = "Structured checklist matrix broken into five critical operational domains.",
                    summaryHi = "पांच महत्वपूर्ण परिचालन क्षेत्रों में संरचित चेकलिस्ट मैट्रिक्स।",
                    keyPoints = listOf(
                        "PPE: 100% hard hat, safety glasses, steel-toe shoes, and task-specific gloves in use",
                        "Housekeeping: Gangways clear of scrap, oil spills cleaned, cables elevated or ramped",
                        "Electrical: Portable tools have current color code tag, 30mA ELCB/GFCI in circuit",
                        "Fire Safety: Extinguishers unobstructed, pressure in green, 11m clear radius for hot work",
                        "Work at Height: Scaffolding green-tagged, full body harness 100% tied-off above 1.8m"
                    ),
                    keyPointsHi = listOf(
                        "पीपीई: 100% हेलमेट, सेफ्टी ग्लास, स्टील-टो जूते और कार्य-विशिष्ट दस्तानों का अनिवार्य उपयोग",
                        "हाउसकीपिंग: रास्ते मलबे से मुक्त, तेल रिसाव तुरंत साफ, केबल ओवरहेड या रैंप से ढके हों",
                        "विद्युत: पोर्टेबल उपकरणों पर चालू माह का कलर टैग, सर्किट में 30mA ELCB/GFCI सक्रिय हो",
                        "अग्निसुरक्षा: अग्निशामक यंत्र अवरोध मुक्त, प्रेशर गेज हरा, हॉट वर्क के लिए 11 मीटर क्षेत्र साफ",
                        "ऊंचाई पर कार्य: मचान ग्रीन-टैग युक्त, 1.8 मीटर से ऊपर फुल बॉडी हार्नेस 100% टाइ-ऑफ"
                    ),
                    extractedTables = listOf(
                        "Domain | Mandatory Criteria | Pass/Fail Consequence",
                        "PPE | 100% Compliant | Stoppage if uncorrected",
                        "Housekeeping | Gangways Clear | Trip hazard mitigation within 15 min",
                        "Electrical | ELCB 30mA Active | Immediate power disconnection if tripped",
                        "Fire Safety | Extinguisher Verified | Hot work permit invalid without inspection",
                        "Work at Height | Green Tag Scaffold | Red Tag strictly prohibits climbing"
                    ),
                    extractedTablesHi = listOf(
                        "क्षेत्र | अनिवार्य मानक | पास/फेल परिणाम",
                        "पीपीई | 100% अनुपालन | सुधार न होने पर कार्य निलंबन",
                        "हाउसकीपिंग | रास्ते साफ | 15 मिनट के भीतर ट्रिप खतरा दूर करना",
                        "विद्युत | ELCB 30mA सक्रिय | ट्रिप होने पर तुरंत बिजली विच्छेद",
                        "अग्निसुरक्षा | अग्निशामक सत्यापित | बिना जांच हॉट वर्क परमिट अमान्य",
                        "ऊंचाई कार्य | ग्रीन टैग मचान | रेड टैग पर चढ़ना सख्त मना"
                    ),
                    safetyWarning = "CRITICAL: A 'FAIL' on Work at Height or Electrical requires an immediate Stop Work Notice.",
                    safetyWarningHi = "गंभीर चेतावनी: ऊंचाई पर कार्य या विद्युत में 'FAIL' मिलने पर तुरंत 'स्टॉप वर्क नोटिस' जारी करें।"
                ),
                DocumentPage(
                    pageNumber = 12,
                    title = "Photo Evidence & Corrective Action Protocol",
                    titleHi = "फोटो साक्ष्य एवं सुधारात्मक कार्रवाई प्रोटोकॉल",
                    summary = "Rules for digital photographic capture of unsafe conditions and post-closure verification.",
                    summaryHi = "असुरक्षित स्थितियों की डिजिटल फोटो लेने एवं सुधार सत्यापन के नियम।",
                    keyPoints = listOf(
                        "Photographs must clearly show the hazard context and location identification marker",
                        "Re-inspection is mandatory within 24 hours of reported non-compliance",
                        "All reports must be shared with department head and archived in EHS register"
                    ),
                    keyPointsHi = listOf(
                        "तस्वीरों में खतरे का स्पष्ट संदर्भ और स्थान पहचान मार्कर दिखना चाहिए",
                        "सूचित उल्लंघन के 24 घंटे के भीतर पुन: निरीक्षण अनिवार्य है",
                        "सभी रिपोर्ट विभाग प्रमुख के साथ साझा कर EHS रजिस्टर में दर्ज की जाएं"
                    )
                )
            )
        ),
        AuthoritativeDocument(
            id = "doc_height_safety",
            title = "Working at Height & Fall Protection Manual",
            titleHi = "ऊंचाई पर कार्य एवं फॉल प्रोटेक्शन मैनुअल",
            fileName = "Working at Height Training.pdf",
            fileType = "PDF",
            version = "Rev 2026.2",
            approvedDate = "10 Jul 2026",
            totalPagesOrSlides = 28,
            category = "High-Risk Safety",
            categoryHi = "उच्च जोखिम सुरक्षा",
            summary = "Approved engineering manual detailing 100% tie-off rules, full body harness inspection, fall clearance calculation, scaffolding protocols, and suspension trauma rescue.",
            summaryHi = "100% टाई-ऑफ नियम, फुल बॉडी हार्नेस जांच, फॉल क्लीयरेंस गणना, मचान नियम एवं रेस्क्यू योजना से युक्त स्वीकृत तकनीकी मैनुअल।",
            pages = listOf(
                DocumentPage(
                    pageNumber = 4,
                    title = "The 1.8 Meter Hierarchy of Fall Protection",
                    titleHi = "1.8 मीटर ऊंचाई पर फॉल प्रोटेक्शन पदानुक्रम",
                    summary = "Statutory requirement for collective fall prevention over individual fall arrest.",
                    summaryHi = "व्यक्तिगत फॉल अरेस्ट की तुलना में सामूहिक फॉल रोकथाम को प्राथमिकता देने का वैधानिक नियम।",
                    keyPoints = listOf(
                        "Any work at or above 1.8 meters (6 feet) triggers mandatory fall protection",
                        "Priority 1: Guardrails (Top rail 42 inches / 1.1m, Mid-rail 21 inches / 0.53m, Toeboard 4 inches)",
                        "Priority 2: Full Body Harness with dual shock-absorbing lanyard and certified anchor"
                    ),
                    keyPointsHi = listOf(
                        "1.8 मीटर (6 फीट) या उससे अधिक ऊंचाई पर फॉल प्रोटेक्शन अनिवार्य है",
                        "प्राथमिकता 1: गार्डरेल्स (टॉप रेल 1.1 मीटर, मिड रेल 0.53 मीटर, टो-बोर्ड 4 इंच)",
                        "प्राथमिकता 2: ड्यूल शॉक-एब्जॉर्बिंग लैनयार्ड और प्रमाणित एंकर युक्त फुल बॉडी हार्नेस"
                    )
                ),
                DocumentPage(
                    pageNumber = 14,
                    title = "Full Body Harness Inspection & Suspension Trauma",
                    titleHi = "फुल बॉडी हार्नेस निरीक्षण एवं सस्पेंशन ट्रॉमा",
                    summary = "Pre-use inspection protocol for webbing, D-rings, buckles and suspension trauma relief straps.",
                    summaryHi = "वेबिंग, डी-रिंग, बकल एवं सस्पेंशन ट्रॉमा रिलीफ स्ट्रैप्स के उपयोग-पूर्व निरीक्षण का प्रोटोकॉल।",
                    keyPoints = listOf(
                        "Reject harness if webbing has cuts, frays, chemical burns, or stitches are unraveled",
                        "D-ring must be centered between shoulder blades in the middle of the back",
                        "Suspension trauma relief straps must be equipped; rescue must be effected within 15 minutes",
                        "Anchor point must withstand minimum 5,000 lbs (22.2 kN) per worker attached"
                    ),
                    keyPointsHi = listOf(
                        "यदि बेल्ट कटी-फटी हो, रासायनिक धब्बे हों या सिलाई उधड़ी हो तो हार्नेस तुरंत रिजेक्ट करें",
                        "डी-रिंग पीठ के बीच दोनों कंधों के ठीक मध्य में स्थित होनी चाहिए",
                        "सस्पेंशन ट्रॉमा रिलीफ स्ट्रैप्स अनिवार्य हैं; 15 मिनट के भीतर रेस्क्यू पूरा होना चाहिए",
                        "प्रत्येक कार्यकर्ता के लिए एंकर पॉइंट कम से कम 5,000 पाउंड (22.2 kN) क्षमता का होना चाहिए"
                    ),
                    extractedTables = listOf(
                        "Fall Clearance Component | Distance Required",
                        "Lanyard Length | 6.0 ft (1.8 m)",
                        "Deceleration Device Expansion | 3.5 ft (1.05 m)",
                        "Worker Height Margin | 6.0 ft (1.8 m)",
                        "Safety Margin Buffer | 3.0 ft (0.9 m)",
                        "Total Required Free Fall Clearance | 18.5 ft (5.6 m)"
                    ),
                    extractedTablesHi = listOf(
                        "फॉल क्लीयरेंस घटक | आवश्यक दूरी",
                        "लैनयार्ड की लंबाई | 6.0 फीट (1.8 मीटर)",
                        "शॉक एब्जॉर्बर विस्तार | 3.5 फीट (1.05 मीटर)",
                        "कार्यकर्ता की ऊंचाई | 6.0 फीट (1.8 मीटर)",
                        "सुरक्षा मार्जिन बफर | 3.0 फीट (0.9 मीटर)",
                        "कुल आवश्यक फॉल क्लीयरेंस | 18.5 फीट (5.6 मीटर)"
                    ),
                    safetyWarning = "NEVER tie back lanyard onto itself unless it is a rated tie-back model.",
                    safetyWarningHi = "लैनयार्ड को कभी भी अपने ऊपर लपेटकर न बांधें जब तक कि वह विशेष टाई-बैक मॉडल न हो।"
                )
            )
        ),
        AuthoritativeDocument(
            id = "doc_confined_space",
            title = "Confined Space Entry & Gas Testing Standard",
            titleHi = "सीमित स्थान (कन्फाइंड स्पेस) प्रवेश एवं गैस जांच मानक",
            fileName = "Confined Space Entry Standard.pdf",
            fileType = "PDF",
            version = "Rev 2026.1",
            approvedDate = "02 Jun 2026",
            totalPagesOrSlides = 22,
            category = "Atmospheric Safety",
            categoryHi = "वायुमंडलीय सुरक्षा",
            summary = "Approved standard for atmospheric testing, continuous mechanical ventilation, standby rescue person (hole watcher), and lock-out isolation before entering tanks or pits.",
            summaryHi = "टैंक या गड्ढों में प्रवेश से पहले वायुमंडलीय गैस जांच, निरंतर वेंटिलेशन, स्टैंडबाय व्यक्ति और लोटो आइसोलेशन का अधिकृत मानक।",
            pages = listOf(
                DocumentPage(
                    pageNumber = 6,
                    title = "Atmospheric Testing Criteria & Calibration",
                    titleHi = "वायुमंडलीय परीक्षण मानक एवं कैलिब्रेशन",
                    summary = "Testing order: Oxygen first, Flammables (LEL) second, Toxics (H2S, CO) third.",
                    summaryHi = "गैस जांच का क्रम: सबसे पहले ऑक्सीजन, फिर ज्वलनशील गैस (LEL), अंत में जहरीली गैस (H2S, CO)।",
                    keyPoints = listOf(
                        "Oxygen (O2): 19.5% to 23.5% safe envelope. Below 19.5% is oxygen-deficient; above 23.5% is oxygen-enriched",
                        "Combustible Gas (LEL): Strictly less than 10% LEL. Entry prohibited if 10% or greater",
                        "Hydrogen Sulfide (H2S): Alarm at 10 ppm; maximum permissible limit is 10 ppm (8-hr TWA)",
                        "Carbon Monoxide (CO): Alarm at 25 ppm; maximum permissible limit is 25 ppm (8-hr TWA)"
                    ),
                    keyPointsHi = listOf(
                        "ऑक्सीजन (O2): 19.5% से 23.5% सुरक्षित सीमा। 19.5% से कम होने पर दम घुटने का खतरा, 23.5% से अधिक पर आग का भारी खतरा",
                        "ज्वलनशील गैस (LEL): 10% से सख्ती से कम। 10% या अधिक होने पर प्रवेश पूर्णतः वर्जित",
                        "हाइड्रोजन सल्फाइड (H2S): 10 ppm पर अलार्म; 8 घंटे की अधिकतम अनुमेय सीमा 10 ppm",
                        "कार्बन मोनोऑक्साइड (CO): 25 ppm पर अलार्म; 8 घंटे की अधिकतम अनुमेय सीमा 25 ppm"
                    ),
                    extractedTables = listOf(
                        "Parameter | Safe Working Range | Warning Alarm | Danger Threshold",
                        "O2 | 19.5% – 23.5% | < 19.5% or > 23.5% | < 16% (Imminent asphyxiation)",
                        "LEL | 0% – 5% | 10% LEL | >= 10% (Explosion hazard)",
                        "H2S | 0 ppm | 10 ppm | >= 100 ppm (Olfactory paralysis)",
                        "CO | 0 – 15 ppm | 25 ppm | >= 200 ppm (Severe poisoning)"
                    ),
                    extractedTablesHi = listOf(
                        "पैरामीटर | सुरक्षित कार्य सीमा | चेतावनी अलार्म | खतरनाक सीमा",
                        "O2 | 19.5% – 23.5% | < 19.5% या > 23.5% | < 16% (तत्काल दम घुटने का खतरा)",
                        "LEL | 0% – 5% | 10% LEL | >= 10% (विस्फोट का खतरा)",
                        "H2S | 0 ppm | 10 ppm | >= 100 ppm (सूंघने की शक्ति खत्म)",
                        "CO | 0 – 15 ppm | 25 ppm | >= 200 ppm (गंभीर विषाक्तता)"
                    ),
                    safetyWarning = "NEVER enter a confined space to rescue an unconscious coworker without SCBA / BA breathing apparatus.",
                    safetyWarningHi = "बिना ब्रीदिंग उपकरण (SCBA/BA सेट) के बेहोश साथी को बचाने के लिए सीमित स्थान में कभी न कूदें।"
                )
            )
        ),
        AuthoritativeDocument(
            id = "doc_hot_work",
            title = "Hot Work & Grinding Fire Prevention Guide",
            titleHi = "हॉट वर्क एवं ग्राइंडिंग अग्नि सुरक्षा गाइड",
            fileName = "Hot Work & Grinding Fire Guide.pdf",
            fileType = "PDF",
            version = "Rev 2026.3",
            approvedDate = "22 Jul 2026",
            totalPagesOrSlides = 18,
            category = "Fire & Tool Safety",
            categoryHi = "अग्नि एवं उपकरण सुरक्षा",
            summary = "Approved standard for angle grinder guards, spark management, 11-meter combustible clearance, and post-work 30-minute fire watch.",
            summaryHi = "एंगल ग्राइंडर गार्ड, चिंगारी नियंत्रण, 11 मीटर ज्वलनशील सामग्री दूरी एवं कार्य के बाद 30 मिनट फायर वॉच का स्वीकृत मानक।",
            pages = listOf(
                DocumentPage(
                    pageNumber = 3,
                    title = "Angle Grinder Safeguarding & Disc Selection",
                    titleHi = "एंगल ग्राइंडर सुरक्षा गार्ड एवं डिस्क चयन",
                    summary = "Mandatory physical wheel guards, rpm verification, and eye/face protection.",
                    summaryHi = "अनिवार्य व्हील गार्ड, आरपीएम मिलान और आंख/चेहरा सुरक्षा कवच।",
                    keyPoints = listOf(
                        "Wheel guard must cover minimum 180 degrees of the abrasive wheel and positioned between wheel and operator",
                        "Wheel maximum rated RPM must be EQUAL TO OR GREATER THAN tool spindle RPM",
                        "Double eye protection is MANDATORY: Safety goggles PLUS full-face impact shield",
                        "Do not wear loose clothing, neckties, rings, or dangling gloves near rotating spindle"
                    ),
                    keyPointsHi = listOf(
                        "व्हील गार्ड पहिये के कम से कम 180 डिग्री हिस्से को ढके और ऑपरेटर तथा पहिये के बीच स्थित हो",
                        "ग्राइंडिंग डिस्क का रेटेड RPM मशीन के स्पिंडल RPM के बराबर या उससे अधिक होना चाहिए",
                        "डबल आई प्रोटेक्शन अनिवार्य है: सेफ्टी गॉगल्स के ऊपर फुल-फेस शील्ड पहनें",
                        "घूमते हुए स्पिंडल के पास ढीले कपड़े, अंगूठी, गले की चेन या ढीले दस्ताने कभी न पहनें"
                    )
                ),
                DocumentPage(
                    pageNumber = 8,
                    title = "The 11-Meter Hot Work Fire Clearance Rule",
                    titleHi = "11-मीटर हॉट वर्क अग्नि सुरक्षा दूरी नियम",
                    summary = "NFPA 51B 35-foot (11m) boundary rule for flammable materials and spark blankets.",
                    summaryHi = "NFPA 51B मानक के अनुसार 35 फीट (11 मीटर) ज्वलनशील पदार्थ दूरी नियम।",
                    keyPoints = listOf(
                        "All combustible materials within 11 meters (35 ft) must be removed or covered with certified fire-retardant blankets",
                        "Floor openings, cracks, or drains within 11m must be sealed tight against spark ingress",
                        "A dedicated Fire Watcher equipped with a charged fire extinguisher (ABC/CO2) must remain for minimum 30 minutes after completion"
                    ),
                    keyPointsHi = listOf(
                        "11 मीटर (35 फीट) के दायरे में सभी ज्वलनशील सामग्री हटा दी जाए या फायर ब्लैंकेट से ढक दी जाए",
                        "11 मीटर के भीतर फर्श की दरारें या ड्रेन चिंगारी रोकने के लिए सील होने चाहिए",
                        "काम पूरा होने के बाद कम से कम 30 मिनट तक अग्निशामक यंत्र के साथ समर्पित 'फायर वॉचर' तैनात रहना चाहिए"
                    )
                )
            )
        ),
        AuthoritativeDocument(
            id = "doc_loto_protocol",
            title = "Lockout Tagout (LOTO) Zero Energy Protocol",
            titleHi = "लॉकआउट टैगआउट (LOTO) शून्य ऊर्जा अलगाव प्रोटोकॉल",
            fileName = "LOTO Zero Energy Protocol.ppt",
            fileType = "PPT",
            version = "Rev 2026.2",
            approvedDate = "18 Aug 2026",
            totalPagesOrSlides = 24,
            category = "Energy Isolation",
            categoryHi = "ऊर्जा पृथक्करण",
            summary = "Approved engineering slides detailing 9-step zero energy verification across electrical, pneumatic, hydraulic, thermal, and stored mechanical springs.",
            summaryHi = "विद्युत, वायवीय, हाइड्रोलिक और थर्मल ऊर्जा को पूरी तरह समाप्त करने के 9-चरणीय शून्य ऊर्जा सत्यापन की अधिकृत स्लाइड प्रस्तुति।",
            pages = listOf(
                DocumentPage(
                    pageNumber = 5,
                    title = "The 9-Step Zero Energy Isolation Sequence",
                    titleHi = "9-चरणीय शून्य ऊर्जा पृथक्करण क्रम",
                    summary = "Step-by-step procedure: Notify, Shutdown, Isolate, Lock/Tag, Bleed/Dissipate, Verify Zero Energy, Perform Work, Restore, De-isolate.",
                    summaryHi = "चरणबद्ध प्रक्रिया: सूचना दें, शटडाउन करें, अलग करें, ताला/टैग लगाएं, बची हुई ऊर्जा डिस्चार्ज करें, शून्य ऊर्जा परखें, काम करें, बहाल करें।",
                    keyPoints = listOf(
                        "Step 1: Notify affected employees of equipment shutdown and servicing",
                        "Step 2: Orderly normal equipment shutdown following operating sequence",
                        "Step 3: Physically isolate energy isolating devices (breakers, valves, switches)",
                        "Step 4: Attach personal red lockout padlock and danger tag (One worker = One lock)",
                        "Step 5: Bleed, ground, or vent stored energy (capacitors, pressure lines, raised counterweights)",
                        "Step 6: TEST BEFORE TOUCH - Attempt to restart to physically verify zero energy state"
                    ),
                    keyPointsHi = listOf(
                        "चरण 1: प्रभावित कर्मचारियों को शटडाउन और मेंटेनेंस की अग्रिम सूचना दें",
                        "चरण 2: ऑपरेटिंग क्रम का पालन करते हुए उपकरण को सामान्य तरीके से बंद करें",
                        "चरण 3: ऊर्जा पृथक्करण उपकरणों (ब्रेकर, वाल्व, स्विच) को भौतिक रूप से डिस्कनेक्ट करें",
                        "चरण 4: अपना व्यक्तिगत लाल ताला और डेंजर टैग लगाएं (एक कार्यकर्ता = एक ताला)",
                        "चरण 5: बची हुई ऊर्जा को डिस्चार्ज करें (कैपेसिटर, प्रेशर पाइप, हवा के दबाव को बाहर निकालें)",
                        "चरण 6: टेस्ट-बिफोर-टच: स्टार्ट बटन दबाकर भौतिक रूप से परखें कि मशीन पूरी तरह बंद है"
                    )
                )
            )
        )
    )

    // -------------------------------------------------------------
    // 2. Content Type E & I: Structured Mobile Inspection Template
    // (Generated directly from "Daily Safety Inspection.pdf")
    // -------------------------------------------------------------
    val dailyInspectionTemplate = DocInspectionTemplate(
        id = "template_daily_safety_inspection",
        title = "Daily Safety Inspection",
        titleHi = "दैनिक सुरक्षा निरीक्षण प्रपत्र",
        documentSource = "Daily Safety Inspection.pdf",
        version = "Rev 2026.1",
        description = "Standard operating mobile form for site supervisors. Evaluate PPE, Housekeeping, Electrical, Fire Safety, and Work at Height with PASS, FAIL, N/A, remarks, photo attach, and PDF report export.",
        descriptionHi = "साइट सुपरवाइजरों हेतु मानक मोबाइल प्रपत्र। पीपीई, हाउसकीपिंग, विद्युत, अग्निसुरक्षा एवं ऊंचाई कार्य को पास/फेल/लागू नहीं, टिप्पणी, फोटो एवं पीडीएफ रिपोर्ट सहित दर्ज करें।",
        citation = SourceCitation(
            documentTitle = "Daily Site Safety Inspection SOP",
            fileName = "Daily Safety Inspection.pdf",
            pageOrSlide = "Page 4",
            documentVersion = "Rev 2026.1",
            lastUpdatedDate = "15 Aug 2026"
        ),
        items = listOf(
            // PPE Items
            DocInspectionItem(
                id = "chk_ppe_1",
                category = "PPE",
                categoryHi = "पीपीई (PPE)",
                question = "All workers wearing certified safety helmets with chinstraps fastened?",
                questionHi = "क्या सभी श्रमिकों ने चिनस्ट्रैप बंधा हुआ प्रमाणित सेफ्टी हेलमेट पहना है?",
                criteria = "Helmet within 5-year shell expiry date, chin strap buckled tight.",
                criteriaHi = "हेलमेट 5 साल की वैधता के भीतर हो और चिनस्ट्रैप कसा हुआ हो।"
            ),
            DocInspectionItem(
                id = "chk_ppe_2",
                category = "PPE",
                categoryHi = "पीपीई (PPE)",
                question = "Steel-toe safety footwear and appropriate task gloves in active use?",
                questionHi = "क्या स्टील-टो सुरक्षा जूते एवं कार्य-विशिष्ट दस्ताने पहने हुए हैं?",
                criteria = "No open slippers or canvas shoes. Cut/heat/chemical gloves per task.",
                criteriaHi = "खुली चप्पल या कपड़े के जूते सख्त मना। कार्य अनुसार कट/हीट/केमिकल दस्ताने।"
            ),
            DocInspectionItem(
                id = "chk_ppe_3",
                category = "PPE",
                categoryHi = "पीपीई (PPE)",
                question = "Eye and face protection compliant (safety glasses or full shield)?",
                questionHi = "क्या आंख और चेहरे की सुरक्षा (सेफ्टी ग्लास या फेस शील्ड) लागू है?",
                criteria = "ANSI Z87.1 approved eye protection in all active grinding/drilling zones.",
                criteriaHi = "ग्राइंडिंग/ड्रिलिंग क्षेत्र में अनिवार्य ANSI Z87.1 प्रमाणित आई प्रोटेक्शन।"
            ),
            // Housekeeping Items
            DocInspectionItem(
                id = "chk_hk_1",
                category = "Housekeeping",
                categoryHi = "हाउसकीपिंग (स्वच्छता)",
                question = "Gangways, walkways, and emergency escape routes 100% unobstructed?",
                questionHi = "क्या रास्ते, गलियारे और आपातकालीन निकास मार्ग 100% अवरोध मुक्त हैं?",
                criteria = "Minimum 1.0 meter width maintained without tools, cables, or pallets.",
                criteriaHi = "कम से कम 1.0 मीटर चौड़ाई बिना किसी औजार, केबल या मलबे के साफ हो।"
            ),
            DocInspectionItem(
                id = "chk_hk_2",
                category = "Housekeeping",
                categoryHi = "हाउसकीपिंग (स्वच्छता)",
                question = "Floor surfaces dry and free of oil, grease, or chemical spills?",
                questionHi = "क्या फर्श सूखा है और तेल, ग्रीस या रासायनिक रिसाव से मुक्त है?",
                criteria = "Drip trays in place; spill kits accessible within 30 meters.",
                criteriaHi = "ड्रिप ट्रे लगी हो; स्पिल किट 30 मीटर के दायरे में उपलब्ध हो।"
            ),
            // Electrical Items
            DocInspectionItem(
                id = "chk_elec_1",
                category = "Electrical",
                categoryHi = "विद्युत सुरक्षा (Electrical)",
                question = "Portable power cables industrial grade and elevated or protected?",
                questionHi = "क्या बिजली के केबल औद्योगिक ग्रेड के हैं और जमीन से ऊपर या सुरक्षित हैं?",
                criteria = "No twisted joints, cut insulation, or unprotected cables crossing roads.",
                criteriaHi = "कटे-फटे तार, जोड़ों पर साधारण टेप या सड़क पर खुले तार न हों।"
            ),
            DocInspectionItem(
                id = "chk_elec_2",
                category = "Electrical",
                categoryHi = "विद्युत सुरक्षा (Electrical)",
                question = "Distribution boards equipped with functional 30mA ELCB/RCCB and tagged?",
                questionHi = "क्या डिस्ट्रीब्यूशन बोर्ड 30mA ELCB/RCCB से लैस एवं मासिक टेस्टेड हैं?",
                criteria = "Test button tripped and reset successfully within past 30 days.",
                criteriaHi = "टेस्ट बटन दबाने पर 30mA ELCB तुरंत ट्रिप होकर काम कर रहा हो।"
            ),
            // Fire Safety Items
            DocInspectionItem(
                id = "chk_fire_1",
                category = "Fire Safety",
                categoryHi = "अग्नि सुरक्षा (Fire Safety)",
                question = "Fire extinguishers inspected, pin intact, and pressure gauge in green?",
                questionHi = "क्या अग्निशामक यंत्रों की पिन सुरक्षित है और प्रेशर कांटा हरे निशान पर है?",
                criteria = "Monthly inspection tag signed; clear access maintained without blockage.",
                criteriaHi = "मासिक निरीक्षण टैग हस्ताक्षरित हो और आगे कोई सामान न रखा हो।"
            ),
            DocInspectionItem(
                id = "chk_fire_2",
                category = "Fire Safety",
                categoryHi = "अग्नि सुरक्षा (Fire Safety)",
                question = "Hot work permits active with 11-meter clearance and fire watcher present?",
                questionHi = "क्या हॉट वर्क परमिट चालू है, 11m क्षेत्र साफ है और फायर वॉचर तैनात है?",
                criteria = "Fire retardant blankets deployed and continuous fire watch assigned.",
                criteriaHi = "फायर ब्लैंकेट बिछी हो और समर्पित फायर वॉचर मौके पर मौजूद हो।"
            ),
            // Work at Height Items
            DocInspectionItem(
                id = "chk_height_1",
                category = "Work at Height",
                categoryHi = "ऊंचाई पर कार्य (Height Safety)",
                question = "Scaffolding inspected with valid green tag and full guardrails?",
                questionHi = "क्या मचान (स्कैफोल्ड) पर वैध ग्रीन टैग और पूरे गार्डरेल्स लगे हैं?",
                criteria = "Top rail, mid rail, toe board, and base jacks secured on sole boards.",
                criteriaHi = "टॉप रेल, मिड रेल, टो-बोर्ड और बेस जैक मजबूत तख्तों पर टिके हों।"
            ),
            DocInspectionItem(
                id = "chk_height_2",
                category = "Work at Height",
                categoryHi = "ऊंचाई पर कार्य (Height Safety)",
                question = "Full body harness 100% tied-off above 1.8m with inspected lanyard?",
                questionHi = "क्या 1.8m से ऊपर काम करने वाले सभी कर्मियों ने 100% टाई-ऑफ किया है?",
                criteria = "Anchored overhead to certified anchor or life-line, not electrical conduit.",
                criteriaHi = "प्रमाणित लाइफलाइन या एंकर पर सुरक्षित रूप से हुक फंसाया गया हो।"
            )
        )
    )

    // -------------------------------------------------------------
    // 3. Content Type B: Micro Training Modules
    // (Generated from training PDFs/PPTs)
    // -------------------------------------------------------------
    val microTrainingModules = listOf(
        DocMicroTrainingModule(
            id = "train_height_101",
            title = "100% Tie-Off & Harness Safety",
            titleHi = "100% टाई-ऑफ एवं हार्नेस सुरक्षा",
            learningObjective = "Master correct full-body harness wearing, dual-lanyard 100% tie-off, and suspension trauma survival.",
            learningObjectiveHi = "फुल बॉडी हार्नेस सही ढंग से पहनना, 100% टाई-ऑफ और सस्पेंशन ट्रॉमा से बचाव में दक्षता हासिल करना।",
            shortExplanation = "A fall from just 2 meters can be fatal. Modern fall protection requires continuous connection to an overhead anchor point using a dual shock-absorbing lanyard.",
            shortExplanationHi = "सिर्फ 2 मीटर की ऊंचाई से गिरना भी जानलेवा हो सकता है। फॉल प्रोटेक्शन में ड्यूल शॉक-एब्जॉर्बिंग लैनयार्ड से हमेशा सुरक्षित एंकर से जुड़े रहना अनिवार्य है।",
            hazards = listOf("Unguarded platform edges", "Tripping over loose grating", "Swing fall into structural beams"),
            hazardsHi = listOf("बिना रेलिंग वाले खुले किनारे", "ढीली जाली पर पैर फिसलना", "गिरते समय झूले की तरह बीम से टकराना"),
            controls = listOf("Guardrails first", "Static lifeline certified for 22.2 kN", "100% continuous tie-off while transitioning"),
            controlsHi = listOf("पहले गार्डरेल लगाएं", "22.2 kN प्रमाणित स्थिर लाइफलाइन", "एक स्थान से दूसरे स्थान जाते समय भी लगातार हुक लगा रखें"),
            ppe = listOf("Full body safety harness", "Twin shock-absorbing lanyards", "Chin-strap safety helmet"),
            ppeHi = listOf("फुल बॉडी सेफ्टी हार्नेस", "दोहरे शॉक-एब्जॉर्बिंग लैनयार्ड", "चिनस्ट्रैप युक्त सेफ्टी हेलमेट"),
            doList = listOf(
                "Inspect webbing and D-ring stitches before putting on",
                "Ensure dorsal D-ring sits square between your shoulder blades",
                "Anchor at or above shoulder height whenever possible"
            ),
            doListHi = listOf(
                "पहनने से पहले हार्नेस की सिलाई और डी-रिंग की अच्छी तरह जांच करें",
                "पीठ की डी-रिंग दोनों कंधों के ठीक बीच में होनी चाहिए",
                "जहां तक संभव हो कंधे की ऊंचाई या उससे ऊपर एंकर करें"
            ),
            dontList = listOf(
                "Never tie off to electrical conduit or sprinkler pipes",
                "Never wrap lanyard around sharp steel I-beam edges",
                "Never leave both hooks detached while walking on high beams"
            ),
            dontListHi = listOf(
                "बिजली के पाइप या पानी की पाइपलाइनों पर कभी हुक न फंसाएं",
                "लैनयार्ड को धारदार लोहे के कोनों पर लपेटकर कभी न बांधें",
                "ऊंची बीम पर चलते समय दोनों हुक कभी एक साथ खुले न छोड़ें"
            ),
            visualIllustration = "ic_height_safety",
            quickQuestion = QuizQuestion(
                id = "qq_height",
                question = "At what height does industrial work strictly mandate fall protection?",
                options = listOf("1.0 Meter", "1.8 Meters (6 ft)", "3.5 Meters", "5.0 Meters"),
                correctAnswerIndex = 1,
                explanation = "OSHA and industrial safety standards mandate fall protection at 1.8 meters (6 feet) or higher."
            ),
            quickQuestionHi = QuizQuestion(
                id = "qq_height_hi",
                question = "औद्योगिक मानकों के अनुसार किस न्यूनतम ऊंचाई पर फॉल प्रोटेक्शन अनिवार्य है?",
                options = listOf("1.0 मीटर", "1.8 मीटर (6 फीट)", "3.5 मीटर", "5.0 मीटर"),
                correctAnswerIndex = 1,
                explanation = "औद्योगिक सुरक्षा मानकों के तहत 1.8 मीटर (6 फीट) या उससे अधिक ऊंचाई पर फॉल प्रोटेक्शन अनिवार्य है।"
            ),
            citation = SourceCitation(
                documentTitle = "Working at Height & Fall Protection Manual",
                fileName = "Working at Height Training.pdf",
                pageOrSlide = "Page 14",
                documentVersion = "Rev 2026.2",
                lastUpdatedDate = "10 Jul 2026"
            )
        ),
        DocMicroTrainingModule(
            id = "train_loto_101",
            title = "Zero Energy LOTO Protocol",
            titleHi = "शून्य ऊर्जा लोटो (LOTO) प्रोटोकॉल",
            learningObjective = "Understand the critical steps of Lockout-Tagout to guarantee zero stored energy before maintenance.",
            learningObjectiveHi = "मेंटेनेंस से पहले शून्य ऊर्जा सुनिश्चित करने के लिए लॉकआउट-टैगआउट के महत्वपूर्ण चरणों को समझना।",
            shortExplanation = "Unexpected machine startup causes crushing, amputation, and fatal shocks. LOTO locks all power sources with a physical personal padlock and releases residual pressure.",
            shortExplanationHi = "मशीन के अचानक चालू होने से हाथ कटने या जानलेवा झटके लग सकते हैं। लोटो के तहत सभी ऊर्जा स्रोतों पर व्यक्तिगत ताला लगाया जाता है।",
            hazards = listOf("Unexpected electrical energization", "Pneumatic/hydraulic cylinder drift", "Thermal burns from hot pipe steam"),
            hazardsHi = listOf("बिजली का अचानक चालू हो जाना", "सिलेंडर का दबाव अचानक रिलीज होना", "गर्म पाइप या भाप से जलना"),
            controls = listOf("Breaker lockouts", "Padlocks with individual keys", "Test-before-touch verification"),
            controlsHi = listOf("ब्रेकर लॉकआउट क्लैंप", "व्यक्तिगत चाबी वाला लाल ताला", "टेस्ट-बिफोर-टच शून्य ऊर्जा सत्यापन"),
            ppe = listOf("Dielectric safety shoes", "Arc flash face shield (if electrical)", "Leather handling gloves"),
            ppeHi = listOf("डाइइलेक्ट्रिक सुरक्षा जूते", "आर्क फ्लैश फेस शील्ड", "चमड़े के सुरक्षा दस्ताने"),
            doList = listOf(
                "Place your own personal red lock on the isolation hasp",
                "Bleed trapped pressure from pneumatic and hydraulic lines",
                "Always try pressing the START button to verify the machine will not turn on"
            ),
            doListHi = listOf(
                "हैस्प पर हमेशा अपना व्यक्तिगत लाल ताला लगाएं",
                "हवा और हाइड्रोलिक लाइनों में फंसा दबाव पूरी तरह बाहर निकालें",
                "शून्य ऊर्जा जांचने के लिए हमेशा स्टार्ट बटन दबाकर देखें कि मशीन नहीं चलती"
            ),
            dontList = listOf(
                "Never share your padlock key with anyone",
                "Never rely only on an emergency stop push-button for isolation",
                "Never remove another worker's lockout padlock"
            ),
            dontListHi = listOf(
                "अपने ताले की चाबी कभी किसी दूसरे को न दें",
                "आइसोलेशन के लिए सिर्फ इमरजेंसी स्टॉप बटन पर कभी भरोसा न करें",
                "किसी दूसरे साथी का लगा हुआ ताला कभी खुद न खोलें"
            ),
            visualIllustration = "ic_loto_lock",
            quickQuestion = QuizQuestion(
                id = "qq_loto",
                question = "What must you always do AFTER locking out and BEFORE beginning maintenance?",
                options = listOf(
                    "Call your manager",
                    "Attempt to start the machine to verify zero energy",
                    "Turn on the air valve",
                    "Take a 10-minute break"
                ),
                correctAnswerIndex = 1,
                explanation = "You must always attempt to start/energize the equipment to physically verify that zero-energy state has been achieved."
            ),
            quickQuestionHi = QuizQuestion(
                id = "qq_loto_hi",
                question = "ताला लगाने के बाद और मरम्मत शुरू करने से पहले क्या करना अनिवार्य है?",
                options = listOf(
                    "मैनेजर को फोन करना",
                    "मशीन चालू करके शून्य ऊर्जा (Zero Energy) का सत्यापन करना",
                    "एयर वाल्व खोलना",
                    "10 मिनट आराम करना"
                ),
                correctAnswerIndex = 1,
                explanation = "शून्य ऊर्जा की पुष्टि करने के लिए मशीन को चालू करने का प्रयास करें ताकि साबित हो सके कि बिजली/दबाव शून्य है।"
            ),
            citation = SourceCitation(
                documentTitle = "Lockout Tagout (LOTO) Zero Energy Protocol",
                fileName = "LOTO Zero Energy Protocol.ppt",
                pageOrSlide = "Slide 5",
                documentVersion = "Rev 2026.2",
                lastUpdatedDate = "18 Aug 2026"
            )
        )
    )

    // -------------------------------------------------------------
    // 4. Content Type C & Reference: Safety Reference Cards
    // (Authoritative limits directly from approved standard documents)
    // -------------------------------------------------------------
    val referenceCards = listOf(
        DocReferenceCard(
            id = "ref_o2",
            parameter = "Oxygen (O₂) Atmospheric Level",
            parameterHi = "ऑक्सीजन (O₂) वायुमंडलीय स्तर",
            category = "Confined Space",
            categoryHi = "सीमित स्थान (कन्फाइंड स्पेस)",
            safeRange = "19.5% to 23.5% Vol",
            safeRangeHi = "19.5% से 23.5% आयतन",
            warningLimit = "< 19.5% (Oxygen Deficient) or > 23.5% (Enriched)",
            warningLimitHi = "< 19.5% (ऑक्सीजन कमी) या > 23.5% (अत्यधिक ऑक्सीजन)",
            dangerThreshold = "< 16.0% (Impaired judgment / asphyxiation risk)",
            dangerThresholdHi = "< 16.0% (होश खोना / तुरंत दम घुटने का खतरा)",
            unit = "% Volume in Air",
            criticalWarning = "Never enter space under 19.5% without continuous forced air ventilation and calibrated multi-gas monitor.",
            criticalWarningHi = "19.5% से कम ऑक्सीजन में बिना निरंतर वेंटिलेशन और गैस मॉनिटर के कभी प्रवेश न करें।",
            citation = SourceCitation(
                documentTitle = "Confined Space Entry & Gas Testing Standard",
                fileName = "Confined Space Entry Standard.pdf",
                pageOrSlide = "Page 6",
                documentVersion = "Rev 2026.1",
                lastUpdatedDate = "02 Jun 2026"
            )
        ),
        DocReferenceCard(
            id = "ref_lel",
            parameter = "Combustible Gas (LEL)",
            parameterHi = "ज्वलनशील गैस (LEL सीमा)",
            category = "Flammability",
            categoryHi = "ज्वलनशीलता",
            safeRange = "0.0% to 5.0% LEL",
            safeRangeHi = "0.0% से 5.0% LEL",
            warningLimit = ">= 5.0% LEL (Alert & Vent)",
            warningLimitHi = ">= 5.0% LEL (चेतावनी व वेंटिलेशन)",
            dangerThreshold = ">= 10.0% LEL (STRICT ENTRY PROHIBITION)",
            dangerThresholdHi = ">= 10.0% LEL (प्रवेश सख्त वर्जित)",
            unit = "% Lower Explosive Limit",
            criticalWarning = "All hot work and personnel entry must immediately stop if LEL reaches or exceeds 10%.",
            criticalWarningHi = "यदि LEL 10% या उससे अधिक पहुंच जाए तो सभी हॉट वर्क और प्रवेश तुरंत रोकें।",
            citation = SourceCitation(
                documentTitle = "Confined Space Entry & Gas Testing Standard",
                fileName = "Confined Space Entry Standard.pdf",
                pageOrSlide = "Page 6",
                documentVersion = "Rev 2026.1",
                lastUpdatedDate = "02 Jun 2026"
            )
        ),
        DocReferenceCard(
            id = "ref_h2s",
            parameter = "Hydrogen Sulfide (H₂S)",
            parameterHi = "हाइड्रोजन सल्फाइड (H₂S गैस)",
            category = "Toxic Atmospheric",
            categoryHi = "विषाक्त गैस",
            safeRange = "0.0 ppm",
            safeRangeHi = "0.0 ppm (शून्य)",
            warningLimit = "10.0 ppm (Low Alarm / 8-hr TWA limit)",
            warningLimitHi = "10.0 ppm (लो अलार्म / 8 घंटे TWA सीमा)",
            dangerThreshold = "15.0 ppm STEL / 100 ppm (Olfactory paralysis)",
            dangerThresholdHi = "15.0 ppm STEL / 100 ppm (सूंघने की क्षमता समाप्त)",
            unit = "Parts Per Million (ppm)",
            criticalWarning = "At high concentrations H2S deadens the sense of smell instantly. Never rely on smell.",
            criticalWarningHi = "अधिक सांद्रता पर H2S सूंघने की शक्ति तुरंत खत्म कर देती है। कभी गंध पर भरोसा न करें।",
            citation = SourceCitation(
                documentTitle = "Confined Space Entry & Gas Testing Standard",
                fileName = "Confined Space Entry Standard.pdf",
                pageOrSlide = "Page 6",
                documentVersion = "Rev 2026.1",
                lastUpdatedDate = "02 Jun 2026"
            )
        ),
        DocReferenceCard(
            id = "ref_hot_work_radius",
            parameter = "Hot Work Combustible Clearance",
            parameterHi = "हॉट वर्क ज्वलनशील सामग्री सुरक्षित दूरी",
            category = "Fire Protection",
            categoryHi = "अग्निसुरक्षा",
            safeRange = "> 11.0 Meters (35 Feet) Clear Zone",
            safeRangeHi = "> 11.0 मीटर (35 फीट) सुरक्षित क्षेत्र",
            warningLimit = "< 11.0 Meters requires certified fire-retardant blankets",
            warningLimitHi = "< 11.0 मीटर होने पर प्रमाणित फायर ब्लैंकेट से ढकना अनिवार्य",
            dangerThreshold = "< 5.0 Meters with open solvents or explosive vapors",
            dangerThresholdHi = "< 5.0 मीटर पर खुले सॉल्वेंट या वाष्प होना (अत्यंत खतरनाक)",
            unit = "Meters / Feet",
            criticalWarning = "A dedicated Fire Watcher with fire extinguisher must monitor area for 30 minutes after hot work ends.",
            criticalWarningHi = "कार्य समाप्त होने के बाद कम से कम 30 मिनट तक फायर वॉचर द्वारा निगरानी अनिवार्य है।",
            citation = SourceCitation(
                documentTitle = "Hot Work & Grinding Fire Prevention Guide",
                fileName = "Hot Work & Grinding Fire Guide.pdf",
                pageOrSlide = "Page 8",
                documentVersion = "Rev 2026.3",
                lastUpdatedDate = "22 Jul 2026"
            )
        )
    )

    // -------------------------------------------------------------
    // 5. Content Type D: PPE Guidance
    // -------------------------------------------------------------
    val ppeGuidanceList = listOf(
        DocPpeGuidance(
            id = "ppe_grinding",
            jobName = "Angle Grinding & Metal Cutting",
            jobNameHi = "एंगल ग्राइंडिंग एवं धातु कटिंग कार्य",
            gearList = listOf(
                "Double Eye Protection: Safety Spectacles (ANSI Z87.1) + Full Face Shield",
                "Heavy-duty Leather Welding/Rigging Gloves (EN 388 4X43)",
                "Flame-retardant Cotton Boiler Suit (100% Cotton)",
                "Steel-Toe Safety Boots with Metatarsal Guard",
                "Hearing Protection: Earmuffs or Earplugs (NRR 25+ dB)"
            ),
            gearListHi = listOf(
                "डबल आई प्रोटेक्शन: सेफ्टी चश्मा (ANSI Z87.1) के ऊपर फुल फेस शील्ड",
                "मजबूत चमड़े के वेल्डिंग/रिगिंग दस्ताने (EN 388 4X43)",
                "आग प्रतिरोधी 100% सूती बॉयलर सूट / डांगरी",
                "मेटाटार्सल गार्ड युक्त स्टील-टो सुरक्षा जूते",
                "कानों की सुरक्षा: ईयरमफ या ईयरप्लग (NRR 25+ dB)"
            ),
            rationale = "High-velocity wheel fragments and shower of sparks at 12,000 RPM require dual impact shield and non-synthetic garments.",
            rationaleHi = "12,000 आरपीएम पर तेज गति से उड़ने वाली चिंगारी और डिस्क टूटने के जोखिम से बचने के लिए डबल शील्ड और सूती कपड़े जरूरी हैं।",
            limitations = listOf("Face shield alone without safety glasses underneath is strictly prohibited"),
            limitationsHi = listOf("बिना अंदर सेफ्टी चश्मा पहने सिर्फ फेस शील्ड लगाना सख्त मना है"),
            inspectionPoints = listOf("Check face shield for cracks or deep scratches", "Inspect leather gloves for oil saturation"),
            inspectionPointsHi = listOf("फेस शील्ड में कोई दरार या गहरा खरोंच न हो", "चमड़े के दस्तानों पर तेल या ग्रीस न लगा हो"),
            citation = SourceCitation(
                documentTitle = "Hot Work & Grinding Fire Prevention Guide",
                fileName = "Hot Work & Grinding Fire Guide.pdf",
                pageOrSlide = "Page 3",
                documentVersion = "Rev 2026.3",
                lastUpdatedDate = "22 Jul 2026"
            )
        ),
        DocPpeGuidance(
            id = "ppe_confined_space",
            jobName = "Confined Space Tank Entry",
            jobNameHi = "सीमित स्थान (टैंक/पिट) प्रवेश कार्य",
            gearList = listOf(
                "Full Body Safety Harness with Dorsal & Shoulder Retrieval D-rings",
                "Calibrated 4-Gas Personal Monitor (worn in breathing zone)",
                "Continuous Forced Ventilation Blower Hose",
                "Chemical/Splash Impervious Coverall (if chemical residue)",
                "Emergency Escape Breathing Apparatus (EEBA 10-15 min set)"
            ),
            gearListHi = listOf(
                "कंधों पर रिट्रीवल डी-रिंग युक्त फुल बॉडी सेफ्टी हार्नेस",
                "सांस लेने के क्षेत्र में लगा कैलिब्रेटेड 4-गैस पर्सनल मॉनिटर",
                "निरंतर ताजी हवा भेजने वाला वेंटिलेशन ब्लोअर पाइप",
                "रासायनिक प्रतिरोधी कवरऑल (यदि केमिकल अवशेष हों)",
                "इमरजेंसी एस्केप ब्रीदिंग उपकरण (EEBA 10-15 मिनट सेट)"
            ),
            rationale = "Guarantees rapid tripod hoist extraction without rescuer entry if atmospheric levels deteriorate.",
            rationaleHi = "गैस का स्तर बिगड़ने पर बिना बचाव दल के अंदर कूदे ट्राइपॉड द्वारा बाहर खींचने की सुविधा देता है।",
            limitations = listOf("Never enter if O2 is below 19.5% without airline respirator"),
            limitationsHi = listOf("19.5% से कम ऑक्सीजन होने पर एयरलाइन रेस्पिरेटर के बिना प्रवेश न करें"),
            inspectionPoints = listOf("Inspect tripod retrieval cable for kinks", "Bump test 4-gas monitor on known gas"),
            inspectionPointsHi = listOf("ट्राइपॉड तार में कोई मुड़ाव न हो", "गैस मॉनिटर का बंप टेस्ट सत्यापित करें"),
            citation = SourceCitation(
                documentTitle = "Confined Space Entry & Gas Testing Standard",
                fileName = "Confined Space Entry Standard.pdf",
                pageOrSlide = "Page 8",
                documentVersion = "Rev 2026.1",
                lastUpdatedDate = "02 Jun 2026"
            )
        )
    )

    // -------------------------------------------------------------
    // 6. Content Type M: Authoritative Safety FAQs
    // (Used by "Ask Safety" AI mode as verified source answers)
    // -------------------------------------------------------------
    val safetyFaqs = listOf(
        DocSafetyFaq(
            id = "faq_grinding_ppe",
            question = "What PPE is mandatory for angle grinding?",
            questionHi = "एंगल ग्राइंडिंग के लिए कौन सा पीपीई अनिवार्य है?",
            answer = "• Double eye protection is MANDATORY: ANSI Z87.1 safety glasses PLUS full-face impact shield.\n• Heavy-duty leather rigging gloves (EN 388).\n• Flame-retardant 100% cotton clothing (no synthetic fibers that melt).\n• Steel-toe safety shoes.\n• Hearing protection (NRR 25+ dB).\n• Wheel guard must be locked in position covering at least 180° of the wheel.",
            answerHi = "• डबल आई प्रोटेक्शन अनिवार्य है: ANSI Z87.1 सेफ्टी चश्मे के ऊपर फुल-फेस शील्ड।\n• मजबूत चमड़े के रिगिंग दस्ताने (EN 388)।\n• 100% सूती आग-प्रतिरोधी कपड़े (सिंथेटिक कपड़े बिल्कुल न पहनें जो पिघल जाते हैं)।\n• स्टील-टो सुरक्षा जूते।\n• कानों की सुरक्षा ईयरमफ (NRR 25+ dB)।\n• ग्राइंडर व्हील गार्ड पहिये के कम से कम 180° हिस्से को ढके होना चाहिए।",
            hazards = listOf("High-velocity abrasive disc fragments", "Sparks causing burns or fires", "Entanglement in spindle"),
            hazardsHi = listOf("तेज गति से उड़ते डिस्क के टुकड़े", "चिंगारी से जलने या आग लगने का खतरा", "घूमते स्पिंडल में कपड़े फंसना"),
            controls = listOf("Guard mandatory", "RPM match check", "11m combustible clearance"),
            controlsHi = listOf("व्हील गार्ड अनिवार्य", "डिस्क आरपीएम मिलान", "11 मीटर ज्वलनशील सामग्री दूरी"),
            ppe = listOf("Double eye shield", "Leather gloves", "Cotton overalls", "Steel-toe boots"),
            ppeHi = listOf("डबल आई शील्ड", "चमड़े के दस्ताने", "सूती कपड़े", "सेफ्टी जूते"),
            citation = SourceCitation(
                documentTitle = "Hot Work & Grinding Fire Prevention Guide",
                fileName = "Hot Work & Grinding Fire Guide.pdf",
                pageOrSlide = "Page 3",
                documentVersion = "Rev 2026.3",
                lastUpdatedDate = "22 Jul 2026"
            )
        ),
        DocSafetyFaq(
            id = "faq_height_tieoff",
            question = "What are the rules for 100% tie-off at height?",
            questionHi = "ऊंचाई पर 100% टाई-ऑफ के क्या नियम हैं?",
            answer = "• Mandatory whenever working at or above 1.8 meters (6 feet).\n• Full body harness with dorsal D-ring positioned between shoulder blades.\n• Dual shock-absorbing lanyards: One hook must always stay attached to an anchor while moving.\n• Anchor point must support at least 5,000 lbs (22.2 kN) per worker.\n• Suspension trauma relief straps must be attached to harness.",
            answerHi = "• 1.8 मीटर (6 फीट) या उससे अधिक ऊंचाई पर 100% टाई-ऑफ अनिवार्य है।\n• दोनों कंधों के बीच डी-रिंग युक्त फुल बॉडी हार्नेस पहनें।\n• दोहरे शॉक-एब्जॉर्बिंग लैनयार्ड का उपयोग करें: चलते समय एक हुक हमेशा लाइफलाइन में फंसा रहना चाहिए।\n• एंकर पॉइंट कम से कम 5,000 पाउंड (22.2 kN) वजन सहन करने में सक्षम होना चाहिए।\n• सस्पेंशन ट्रॉमा रिलीफ स्ट्रैप्स हार्नेस में जुड़ी होनी चाहिए।",
            hazards = listOf("Fatal fall from elevation", "Suspension trauma after arrest", "Swing-fall collision"),
            hazardsHi = listOf("ऊंचाई से गिरकर जानलेवा चोट", "गिरने के बाद लटके रहने से सस्पेंशन ट्रॉमा", "झूले की तरह टकराना"),
            controls = listOf("Certified lifeline", "Daily pre-use harness inspection", "Rescue plan in 15 min"),
            controlsHi = listOf("प्रमाणित लाइफलाइन", "दैनिक उपयोग-पूर्व हार्नेस जांच", "15 मिनट में रेस्क्यू योजना"),
            ppe = listOf("Full body harness", "Twin lanyard with scaff-hooks", "Chin-strap helmet"),
            ppeHi = listOf("फुल बॉडी हार्नेस", "ट्विन लैनयार्ड व हुक", "चिनस्ट्रैप हेलमेट"),
            citation = SourceCitation(
                documentTitle = "Working at Height & Fall Protection Manual",
                fileName = "Working at Height Training.pdf",
                pageOrSlide = "Page 14",
                documentVersion = "Rev 2026.2",
                lastUpdatedDate = "10 Jul 2026"
            )
        ),
        DocSafetyFaq(
            id = "faq_confined_gas_limits",
            question = "What are safe gas limits for confined space entry?",
            questionHi = "सीमित स्थान (Confined Space) में सुरक्षित गैस सीमाएं क्या हैं?",
            answer = "• Oxygen (O₂): 19.5% to 23.5% Vol (Below 19.5% is deficient; above 23.5% is enriched).\n• Combustible Gas (LEL): Strictly LESS THAN 10% LEL. Entry prohibited at 10% or above.\n• Hydrogen Sulfide (H₂S): Less than 10 ppm (Alarm set at 10 ppm).\n• Carbon Monoxide (CO): Less than 25 ppm (Alarm set at 25 ppm).\n• Continuous mechanical ventilation and continuous gas monitoring are mandatory throughout occupancy.",
            answerHi = "• ऑक्सीजन (O₂): 19.5% से 23.5% आयतन (19.5% से कम होने पर दम घुटता है, 23.5% से अधिक पर आग का भारी खतरा)।\n• ज्वलनशील गैस (LEL): 10% से सख्ती से कम होना चाहिए। 10% या अधिक पर प्रवेश वर्जित है।\n• हाइड्रोजन सल्फाइड (H₂S): 10 ppm से कम (10 ppm पर अलार्म)।\n• कार्बन मोनोऑक्साइड (CO): 25 ppm से कम (25 ppm पर अलार्म)।\n• कार्य के दौरान निरंतर ताजी हवा का वेंटिलेशन और गैस मॉनिटरिंग चालू रहना अनिवार्य है।",
            hazards = listOf("Toxic asphyxiation", "Atmospheric explosion", "Trapped personnel"),
            hazardsHi = listOf("दम घुटना", "विस्फोट", "अंदर फंस जाना"),
            controls = listOf("Continuous ventilation", "Pre-entry test", "Dedicated standby hole watcher"),
            controlsHi = listOf("निरंतर वेंटिलेशन", "प्रवेश-पूर्व परीक्षण", "बाहर स्टैंडबाय सुरक्षाकर्मी"),
            ppe = listOf("4-gas monitor", "Retrieval harness", "Emergency escape set"),
            ppeHi = listOf("4-गैस मॉनिटर", "रिट्रीवल हार्नेस", "इमरजेंसी एस्केप सेट"),
            citation = SourceCitation(
                documentTitle = "Confined Space Entry & Gas Testing Standard",
                fileName = "Confined Space Entry Standard.pdf",
                pageOrSlide = "Page 6",
                documentVersion = "Rev 2026.1",
                lastUpdatedDate = "02 Jun 2026"
            )
        ),
        DocSafetyFaq(
            id = "faq_loto_steps",
            question = "How to perform zero energy Lockout Tagout (LOTO)?",
            questionHi = "शून्य ऊर्जा लॉकआउट टैगआउट (LOTO) कैसे करें?",
            answer = "• Notify affected staff of shutdown.\n• Orderly shutdown via standard controls.\n• Disconnect energy isolating devices (breakers, switches, valves).\n• Apply personal red lock and danger tag (One worker = One lock).\n• Bleed trapped pressure from pneumatic, hydraulic, and steam lines.\n• TEST BEFORE TOUCH: Press start button to verify zero energy before beginning work.",
            answerHi = "• प्रभावित कर्मचारियों को सूचित करें।\n• ऑपरेटिंग स्विच से मशीन बंद करें।\n• मुख्य ऊर्जा स्रोतों (ब्रेकर, वाल्व) को अलग करें।\n• अपना व्यक्तिगत लाल ताला और डेंजर टैग लगाएं (एक कर्मचारी = एक ताला)।\n• हवा, हाइड्रोलिक और भाप का बचा हुआ दबाव बाहर निकालें।\n• टेस्ट-बिफोर-टच: काम शुरू करने से पहले स्टार्ट बटन दबाकर पुष्टि करें कि मशीन पूरी तरह बंद है।",
            hazards = listOf("Unexpected energization", "Stored pressure burst", "Severe electric shock"),
            hazardsHi = listOf("मशीन का अचानक चालू होना", "दबाव का फूटना", "गंभीर बिजली का झटका"),
            controls = listOf("Personal padlocks", "Bleed valves", "3-step zero verification"),
            controlsHi = listOf("व्यक्तिगत ताले", "ब्लीड वाल्व", "शून्य ऊर्जा सत्यापन"),
            ppe = listOf("Dielectric footwear", "Arc flash gear", "Insulated gloves"),
            ppeHi = listOf("डाइइलेक्ट्रिक जूते", "आर्क फ्लैश सूट", "इंसुलेटेड दस्ताने"),
            citation = SourceCitation(
                documentTitle = "Lockout Tagout (LOTO) Zero Energy Protocol",
                fileName = "LOTO Zero Energy Protocol.ppt",
                pageOrSlide = "Slide 5",
                documentVersion = "Rev 2026.2",
                lastUpdatedDate = "18 Aug 2026"
            )
        )
    )

    // -------------------------------------------------------------
    // 7. Content Type N: Semantic Knowledge Search across All Documents
    // (Categorized into: Learn, PPE, Checklist, Tools, Templates, Reference, Original Documents)
    // -------------------------------------------------------------
    fun searchAuthoritativeKnowledge(rawQuery: String): Map<DocSearchCategory, List<DocSearchResult>> {
        val q = rawQuery.trim().lowercase()
        if (q.isBlank()) return emptyMap()

        val results = mutableListOf<DocSearchResult>()

        // 1. Original Documents
        authoritativeDocuments.forEach { doc ->
            if (doc.title.lowercase().contains(q) ||
                doc.titleHi.lowercase().contains(q) ||
                doc.fileName.lowercase().contains(q) ||
                doc.summary.lowercase().contains(q) ||
                doc.summaryHi.lowercase().contains(q) ||
                doc.category.lowercase().contains(q) ||
                doc.categoryHi.lowercase().contains(q)
            ) {
                results.add(
                    DocSearchResult(
                        id = doc.id,
                        title = doc.title,
                        titleHi = doc.titleHi,
                        snippet = "${doc.fileName} • ${doc.totalPagesOrSlides} Pages • ${doc.status}",
                        snippetHi = "${doc.fileName} • ${doc.totalPagesOrSlides} पृष्ठ • ${doc.statusHi}",
                        category = DocSearchCategory.ORIGINAL_DOCUMENTS,
                        categoryLabel = "Original Document",
                        categoryLabelHi = "मूल दस्तावेज़",
                        citation = SourceCitation(
                            documentTitle = doc.title,
                            fileName = doc.fileName,
                            pageOrSlide = "Cover",
                            documentVersion = doc.version,
                            lastUpdatedDate = doc.approvedDate
                        ),
                        targetType = "DOCUMENT",
                        targetId = doc.id
                    )
                )
            }
        }

        // 2. Learn (Micro training & Articles)
        microTrainingModules.forEach { mod ->
            if (mod.title.lowercase().contains(q) ||
                mod.titleHi.lowercase().contains(q) ||
                mod.learningObjective.lowercase().contains(q) ||
                mod.learningObjectiveHi.lowercase().contains(q) ||
                mod.hazards.any { it.lowercase().contains(q) } ||
                mod.hazardsHi.any { it.lowercase().contains(q) }
            ) {
                results.add(
                    DocSearchResult(
                        id = mod.id,
                        title = mod.title,
                        titleHi = mod.titleHi,
                        snippet = mod.learningObjective,
                        snippetHi = mod.learningObjectiveHi,
                        category = DocSearchCategory.LEARN,
                        categoryLabel = "Micro Training",
                        categoryLabelHi = "सीखें (माइक्रो ट्रेनिंग)",
                        citation = mod.citation,
                        targetType = "TRAINING",
                        targetId = mod.id
                    )
                )
            }
        }

        // 3. PPE Guidance
        ppeGuidanceList.forEach { ppe ->
            if (ppe.jobName.lowercase().contains(q) ||
                ppe.jobNameHi.lowercase().contains(q) ||
                ppe.gearList.any { it.lowercase().contains(q) } ||
                ppe.gearListHi.any { it.lowercase().contains(q) } ||
                ppe.rationale.lowercase().contains(q) ||
                ppe.rationaleHi.lowercase().contains(q)
            ) {
                results.add(
                    DocSearchResult(
                        id = ppe.id,
                        title = "PPE: ${ppe.jobName}",
                        titleHi = "पीपीई: ${ppe.jobNameHi}",
                        snippet = ppe.gearList.take(2).joinToString(", "),
                        snippetHi = ppe.gearListHi.take(2).joinToString(", "),
                        category = DocSearchCategory.PPE,
                        categoryLabel = "PPE Guidance",
                        categoryLabelHi = "पीपीई मार्गदर्शन",
                        citation = ppe.citation,
                        targetType = "PPE",
                        targetId = ppe.id
                    )
                )
            }
        }

        // 4. Checklist & Inspection Templates
        if (dailyInspectionTemplate.title.lowercase().contains(q) ||
            dailyInspectionTemplate.titleHi.lowercase().contains(q) ||
            dailyInspectionTemplate.items.any { it.question.lowercase().contains(q) || it.questionHi.lowercase().contains(q) || it.category.lowercase().contains(q) || it.categoryHi.lowercase().contains(q) } ||
            "inspection checklist audit daily ppe housekeeping electrical fire height".contains(q) ||
            "निरीक्षण चेकलिस्ट दैनिक हाउसकीपिंग विद्युत आग ऊंचाई".contains(q)
        ) {
            results.add(
                DocSearchResult(
                    id = dailyInspectionTemplate.id,
                    title = dailyInspectionTemplate.title,
                    titleHi = dailyInspectionTemplate.titleHi,
                    snippet = "${dailyInspectionTemplate.items.size} verified items covering PPE, Housekeeping, Electrical, Fire, Height",
                    snippetHi = "पीपीई, हाउसकीपिंग, विद्युत, अग्नि, ऊंचाई पर 11 प्रमाणित निरीक्षण बिंदु",
                    category = DocSearchCategory.CHECKLIST,
                    categoryLabel = "Field Inspection",
                    categoryLabelHi = "फील्ड चेकलिस्ट",
                    citation = dailyInspectionTemplate.citation,
                    targetType = "CHECKLIST",
                    targetId = dailyInspectionTemplate.id
                )
            )
        }

        // 5. Reference Cards
        referenceCards.forEach { ref ->
            if (ref.parameter.lowercase().contains(q) ||
                ref.parameterHi.lowercase().contains(q) ||
                ref.category.lowercase().contains(q) ||
                ref.categoryHi.lowercase().contains(q) ||
                ref.safeRange.lowercase().contains(q) ||
                ref.safeRangeHi.lowercase().contains(q) ||
                ref.dangerThreshold.lowercase().contains(q) ||
                ref.dangerThresholdHi.lowercase().contains(q)
            ) {
                results.add(
                    DocSearchResult(
                        id = ref.id,
                        title = ref.parameter,
                        titleHi = ref.parameterHi,
                        snippet = "Safe: ${ref.safeRange} • Danger: ${ref.dangerThreshold}",
                        snippetHi = "सुरक्षित: ${ref.safeRangeHi} • खतरा: ${ref.dangerThresholdHi}",
                        category = DocSearchCategory.REFERENCE,
                        categoryLabel = "Safety Limits",
                        categoryLabelHi = "सुरक्षा मानक व सीमाएं",
                        citation = ref.citation,
                        targetType = "REFERENCE",
                        targetId = ref.id
                    )
                )
            }
        }

        // 6. Templates (JSA / HIRA / Reports)
        if ("jsa hira risk assessment template hazard job".contains(q) || "जोखिम विश्लेषण जेएसए टेम्प्लेट प्रपत्र".contains(q)) {
            results.add(
                DocSearchResult(
                    id = "template_jsa_general",
                    title = "Job Safety Analysis (JSA) Template",
                    titleHi = "कार्य सुरक्षा विश्लेषण (JSA) प्रपत्र",
                    snippet = "Structured 4-step job hazard breakdown with hierarchy of controls",
                    snippetHi = "खतरा नियंत्रण पदानुक्रम के साथ संरचित 4-चरणीय कार्य सुरक्षा प्रपत्र",
                    category = DocSearchCategory.TEMPLATES,
                    categoryLabel = "JSA Template",
                    categoryLabelHi = "JSA प्रपत्र",
                    citation = SourceCitation(
                        documentTitle = "Daily Site Safety Inspection SOP",
                        fileName = "Daily Safety Inspection.pdf",
                        pageOrSlide = "Appendix B",
                        documentVersion = "Rev 2026.1",
                        lastUpdatedDate = "15 Aug 2026"
                    ),
                    targetType = "TEMPLATE",
                    targetId = "jsa_builder"
                )
            )
        }

        // Group by category
        return results.groupBy { it.category }
    }
}

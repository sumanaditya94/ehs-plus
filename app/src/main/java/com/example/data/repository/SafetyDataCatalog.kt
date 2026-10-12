package com.example.data.repository

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
import com.example.data.model.ToolboxTalk

object SafetyDataCatalog {

    val dailyTopics = mapOf(
        "MONDAY" to DailySafetyContent(
            dayOfWeek = "MONDAY",
            topicTitle = "Hand & Pinch-Point Safety",
            topicSummary = "Protect your hands: Never place hands between moving equipment and fixed structures.",
            safetyTip = "Check for pinch points before rotating, lowering, or coupling heavy pipe spools.",
            hazardOfTheDay = "Rotating machine pinch point / unguarded drive belt",
            ppeOfTheDay = "Heavy duty impact & cut-resistant mechanics gloves (EN 388 Level 4/5)",
            toolboxTalkTitle = "Pinch Points & Hand Injury Prevention",
            dailyQuiz = QuizQuestion(
                id = "dq_mon",
                question = "When should you NOT wear loose gloves around machinery?",
                options = listOf(
                    "Near rotating spindles, drill presses, and lathe chucks",
                    "When handling chemical drums",
                    "When carrying heavy structural steel beams",
                    "When inspecting electrical panels"
                ),
                correctAnswerIndex = 0,
                explanation = "Wearing loose gloves near rotating machinery poses an extreme risk of glove entanglement and amputation."
            )
        ),
        "TUESDAY" to DailySafetyContent(
            dayOfWeek = "TUESDAY",
            topicTitle = "Electrical Safety & Test-Before-Touch",
            topicSummary = "Treat every conductor as live until physically tested and proven dead.",
            safetyTip = "Always inspect insulated power cables for cuts, abrasions, or exposed copper prior to plug-in.",
            hazardOfTheDay = "Unverified de-energization in multi-source distribution panel",
            ppeOfTheDay = "Class 0 (1000V rated) Dielectric insulating rubber gloves with leather protectors",
            toolboxTalkTitle = "Electrical Shock & Arc Flash Boundary Protocols",
            dailyQuiz = QuizQuestion(
                id = "dq_tue",
                question = "What is the very first step in Test-Before-Touch electrical verification?",
                options = listOf(
                    "Touch the cable with the back of your hand",
                    "Verify the voltage tester on a known live source before and after testing the target circuit",
                    "Turn the main breaker back on to hear if it hums",
                    "Ask a coworker if they switched it off"
                ),
                correctAnswerIndex = 1,
                explanation = "The 3-point test method: Test multimeter on known live source, test target circuit, re-test on known live source."
            )
        ),
        "WEDNESDAY" to DailySafetyContent(
            dayOfWeek = "WEDNESDAY",
            topicTitle = "Working at Height & Fall Arrest",
            topicSummary = "100% tie-off mandatory whenever working at heights where fall hazards exist.",
            safetyTip = "Inspect full body harness stitching, D-rings, and buckles every single morning before wearing.",
            hazardOfTheDay = "Unguarded platform edges and loose floor grating panels",
            ppeOfTheDay = "Full body harness with twin shock-absorbing lanyards & scaff-hooks",
            toolboxTalkTitle = "Fall Prevention vs Fall Arrest & Tool Tethering",
            dailyQuiz = QuizQuestion(
                id = "dq_wed",
                question = "At what minimum height is fall protection required in standard general industrial settings?",
                options = listOf(
                    "10 meters (33 ft)",
                    "1.2 to 1.8 meters (4 to 6 ft) depending on standard",
                    "Only above 15 meters",
                    "Fall protection is only required on roofs"
                ),
                correctAnswerIndex = 1,
                explanation = "OSHA general industry threshold is 4 ft (1.2m), construction is 6 ft (1.8m). Always adhere to your site specific rules."
            )
        ),
        "THURSDAY" to DailySafetyContent(
            dayOfWeek = "THURSDAY",
            topicTitle = "Chemical Handling & Spill Containment",
            topicSummary = "Know your Safety Data Sheet (SDS) before breaking open any chemical line or container.",
            safetyTip = "Always add acid to water slowly, NEVER add water to concentrated acid.",
            hazardOfTheDay = "Exothermic violent splatter during concentrated acid dilution",
            ppeOfTheDay = "Full chemical splash goggles + Neoprene face shield + Nitrile/Butyl heavy gloves",
            toolboxTalkTitle = "Chemical Splash Emergency Shower & Eyewash Reaction",
            dailyQuiz = QuizQuestion(
                id = "dq_thu",
                question = "If concentrated corrosive chemical splashes on your arm, how long must you flush at the emergency shower?",
                options = listOf(
                    "30 seconds",
                    "2 minutes",
                    "At least 15 to 20 minutes with continuous flushing",
                    "Just wipe with a dry cloth"
                ),
                correctAnswerIndex = 2,
                explanation = "Continuous copious water flushing for minimum 15-20 minutes while simultaneously removing contaminated clothing is essential."
            )
        ),
        "FRIDAY" to DailySafetyContent(
            dayOfWeek = "FRIDAY",
            topicTitle = "Fire Safety & Hot Work Precautions",
            topicSummary = "Clear 10m (35ft) radius of all combustible materials before starting hot work.",
            safetyTip = "A designated fire watch must remain on site during hot work and for at least 30-60 minutes after.",
            hazardOfTheDay = "Hot slag falling through open deck grating onto stored lubricants below",
            ppeOfTheDay = "Flame-retardant welding hood (Auto-darkening shade 10-12) and split-leather welding jacket",
            toolboxTalkTitle = "The P.A.S.S. Fire Extinguisher Discharge Technique",
            dailyQuiz = QuizQuestion(
                id = "dq_fri",
                question = "What does the P.A.S.S. acronym stand for when operating a fire extinguisher?",
                options = listOf(
                    "Push, Aim, Spray, Stop",
                    "Pull the pin, Aim at the base of fire, Squeeze lever, Sweep side-to-side",
                    "Press, Alert, Shield, Standby",
                    "Protect, Activate, Smother, Secure"
                ),
                correctAnswerIndex = 1,
                explanation = "P.A.S.S. = Pull pin, Aim at base of flames, Squeeze handle, Sweep side-to-side."
            )
        ),
        "SATURDAY" to DailySafetyContent(
            dayOfWeek = "SATURDAY",
            topicTitle = "Lockout / Tagout (LOTO) Zero Energy",
            topicSummary = "Your lock is your life. Never allow someone else to apply or remove your personal safety lock.",
            safetyTip = "Always dissipate stored energy (bleed hydraulic pressure, discharge capacitors, block gravity loads).",
            hazardOfTheDay = "Trapped air in pneumatic actuator moving unexpectedly after electrical isolation",
            ppeOfTheDay = "Standard site PPE plus personal red padlock, Danger tag, and hasp lockout clamp",
            toolboxTalkTitle = "The 9 Steps of Comprehensive Energy Isolation",
            dailyQuiz = QuizQuestion(
                id = "dq_sat",
                question = "Which step must immediately follow applying locks and tags before work begins?",
                options = listOf(
                    "Start work immediately",
                    "Zero-energy verification (try to start equipment and verify zero stored energy)",
                    "Hand your key to your supervisor",
                    "Remove the tag"
                ),
                correctAnswerIndex = 1,
                explanation = "Zero-energy verification ('Try' step) confirms that isolation is complete and that all stored energy is dissipated."
            )
        ),
        "SUNDAY" to DailySafetyContent(
            dayOfWeek = "SUNDAY",
            topicTitle = "Housekeeping & 5S Industrial Order",
            topicSummary = "Good housekeeping is the foundation of safe operations. Keep walkways clear.",
            safetyTip = "Coil welding cables and air hoses immediately after use or elevate them across walkways.",
            hazardOfTheDay = "Tripping over trailing extension cords in dim corridor",
            ppeOfTheDay = "Steel-toe puncture resistant safety boots (ASTM F2413 / EN ISO 20345)",
            toolboxTalkTitle = "Slip, Trip, and Fall Hazard Mitigation & 5S",
            dailyQuiz = QuizQuestion(
                id = "dq_sun",
                question = "Why are slip and trip hazards considered serious industrial hazards?",
                options = listOf(
                    "They cause only minor embarrassment",
                    "Slips and trips frequently lead to severe secondary injuries (falling onto machinery or off elevated edges)",
                    "Housekeeping has no relation to safety",
                    "They only affect office workers"
                ),
                correctAnswerIndex = 1,
                explanation = "Trips often cause workers to fall against sharp metal edges, hot surfaces, rotating equipment, or through handrails."
            )
        )
    )

    val safetyTopics: List<SafetyTopic> = listOf(
        // Basic Safety
        SafetyTopic(
            id = "topic_safety_induction",
            title = "Safety Induction & Core Mindset",
            category = "Basic Safety",
            iconName = "School",
            summary = "Fundamental principles of personal accountability, site safety rules, and your legal right to stop unsafe work.",
            whatIsIt = "Safety induction is the foundational orientation that introduces workers to workplace hazard identification, reporting systems, emergency alarms, and essential safety conduct.",
            whyDangerous = "Entering an industrial facility without orientation leaves a worker blind to invisible chemical hazards, high-voltage substations, mobile machinery blind spots, and evacuation routes.",
            commonHazards = listOf(
                "Unfamiliarity with plant evacuation alarms and wind socks",
                "Entering unauthorized high-hazard zones without permits",
                "Failing to recognize site-specific hazardous chemicals",
                "Lack of knowledge regarding stop-work authority"
            ),
            consequences = listOf(
                "Accidental entry into toxic gas dispersion zones",
                "Delayed response during plant emergencies",
                "Severe injury or fatality from unperceived equipment movements"
            ),
            howToPrevent = listOf(
                "Complete full mandatory safety induction before setting foot on site",
                "Always obey warning signs, barricades, and color-coded zones",
                "Exercise Stop-Work Authority whenever an unsafe condition or act is observed",
                "Report all near misses and hazards immediately to your team lead"
            ),
            requiredPpe = listOf("Safety Helmet", "Safety Glasses", "Hi-Vis Vest", "Steel Toe Boots"),
            beforeWorkChecklist = listOf(
                "Confirm valid site gate pass & safety induction badge",
                "Identify the nearest emergency exit and muster point",
                "Know the location of the nearest eyewash and safety shower"
            ),
            duringWorkPrecautions = listOf(
                "Stay within authorized operational areas",
                "Do not use mobile phones or earphones while walking in plant areas",
                "Keep clear of crane lifting zones and forklift drive paths"
            ),
            afterWorkPoints = listOf(
                "Sign out of active work areas",
                "Return borrowed tools and PPE to clean designated storage"
            ),
            doList = listOf(
                "Stop work immediately if you feel unsafe or lack clear instruction",
                "Ask your supervisor if any procedure is ambiguous",
                "Keep emergency pathways and exits 100% unobstructed"
            ),
            dontList = listOf(
                "Never walk into operating chemical or electrical units without authorization",
                "Never run in plant corridors or stairs (walk and use handrails)",
                "Never tamper with or bypass any safety interlock or guard"
            ),
            quiz = listOf(
                QuizQuestion(
                    question = "What should you do if you encounter a work condition that seems dangerous?",
                    options = listOf(
                        "Ignore it if it is not your job",
                        "Exercise Stop-Work Authority, inform coworkers, and notify your supervisor",
                        "Wait until the end of the shift to report",
                        "Take a photo and post to social media"
                    ),
                    correctAnswerIndex = 1,
                    explanation = "Every worker has the right and duty to invoke Stop-Work Authority when safety is compromised."
                ),
                QuizQuestion(
                    question = "Why must you check wind socks in chemical plants?",
                    options = listOf(
                        "To estimate tomorrow's weather",
                        "To evacuate upwind and crosswind in the event of a toxic gas release",
                        "To check if crane operations can exceed max load",
                        "For aesthetic site decoration"
                    ),
                    correctAnswerIndex = 1,
                    explanation = "During chemical gas release, evacuation must always be upwind (facing against the wind) and crosswind."
                )
            )
        ),
        SafetyTopic(
            id = "topic_hazard_vs_risk",
            title = "Hazard vs. Risk & Hierarchy of Controls",
            category = "Basic Safety",
            iconName = "Tune",
            summary = "Understanding the difference between a hazard (source of harm) and risk (likelihood × severity), and applying the 5 levels of control.",
            whatIsIt = "A hazard is anything with the potential to cause harm (e.g., electricity, sulfuric acid). Risk is the combination of the likelihood that the hazard will cause harm and the severity of that harm. The Hierarchy of Controls is the systematic order of effectiveness to reduce risk.",
            whyDangerous = "Relying on PPE as the first line of defense often fails because PPE does not eliminate the hazard; if PPE tears, leaks, or is worn incorrectly, the worker receives the full brunt of the injury.",
            commonHazards = listOf(
                "Confusing hazard with risk",
                "Jumping straight to PPE without considering elimination or substitution",
                "Underestimating likelihood due to long periods without recorded incidents",
                "Implementing administrative controls that are ignored during rushed work"
            ),
            consequences = listOf(
                "Ineffective control measures leading to repeat accidents",
                "Catastrophic failures when PPE is solely relied upon for high-risk operations"
            ),
            howToPrevent = listOf(
                "Apply Elimination: Physically remove the hazard entirely (e.g., cancel height work by assembling on the ground)",
                "Apply Substitution: Replace the hazard with a safer alternative (e.g., switch toxic solvent to water-based cleaner)",
                "Apply Engineering Controls: Isolate people from the hazard (e.g., machine guards, ventilation hoods, physical barricades)",
                "Apply Administrative Controls: Change the way people work (e.g., procedures, training, rotation, warning signs)",
                "Apply PPE: Protect the worker with personal gear as the final secondary defense"
            ),
            requiredPpe = listOf("Site Standard PPE suited to residual risk"),
            beforeWorkChecklist = listOf(
                "Review the Job Safety Analysis (JSA)",
                "Identify all primary hazards in the task",
                "Verify whether higher-level controls (Elimination/Engineering) can be implemented"
            ),
            duringWorkPrecautions = listOf(
                "Ensure engineering controls (guards, exhaust fans) remain operational throughout",
                "Do not bypass or disable interlocks"
            ),
            afterWorkPoints = listOf(
                "Evaluate whether controls were effective",
                "Suggest engineering improvements during post-job debrief"
            ),
            doList = listOf(
                "Prioritize eliminating hazards at the design and planning phase",
                "Maintain physical barriers and ventilation systems in top condition",
                "Inspect all PPE before each use"
            ),
            dontList = listOf(
                "Never consider PPE as a substitute for proper machine guarding",
                "Never accept high risk without applying hierarchy controls"
            ),
            quiz = listOf(
                QuizQuestion(
                    question = "Which level in the Hierarchy of Controls is the MOST effective?",
                    options = listOf("Personal Protective Equipment (PPE)", "Administrative Controls", "Elimination", "Warning Signs"),
                    correctAnswerIndex = 2,
                    explanation = "Elimination physically removes the hazard from the workplace, making injury impossible."
                ),
                QuizQuestion(
                    question = "Installing an acoustic enclosure around a noisy compressor is an example of what control type?",
                    options = listOf("Elimination", "Engineering Control", "PPE", "Substitution"),
                    correctAnswerIndex = 1,
                    explanation = "Enclosing noise at the equipment source isolates workers from acoustic energy, making it an Engineering Control."
                )
            )
        ),
        SafetyTopic(
            id = "topic_work_at_height",
            title = "Working at Height & Fall Prevention",
            category = "High-Risk Work",
            iconName = "Elevator",
            summary = "Essential safety requirements for scaffolds, ladders, aerial lifts, harnesses, 100% tie-off, and dropped object prevention.",
            whatIsIt = "Work at height includes any work where a person could fall a distance liable to cause personal injury (scaffolding, ladders, roofs, open pits, mobile elevated work platforms).",
            whyDangerous = "Falls from height remain the leading cause of industrial fatalities. A fall from just 2 meters can cause fatal head trauma or permanent paralysis.",
            commonHazards = listOf(
                "Unprotected platform edges, missing guardrails, or open floor grating",
                "Inadequate or uncertified anchorage points",
                "Scaffold collapse due to missing base plates, bracing, or overloading",
                "Tools falling through grating onto personnel working below (dropped objects)",
                "Using damaged ladders or overreaching beyond ladder side rails"
            ),
            consequences = listOf(
                "Fatal head injuries and spine fractures",
                "Suspension trauma (orthostatic intolerance) while hanging in harness",
                "Severe injury to ground personnel from dropped hand tools"
            ),
            howToPrevent = listOf(
                "Follow the Height Decision Hierarchy: Avoid working at height -> Use collective protection (guardrails) -> Use fall arrest with certified anchorage",
                "Inspect full body harness, D-rings, webbing, and shock absorbers before every shift",
                "Maintain 100% tie-off: Never unclip both lanyard hooks simultaneously",
                "Anchor only to certified anchorage points capable of supporting at least 5,000 lbs (22.2 kN) per worker or engineered systems",
                "Tether all hand tools with elasticized wrist/belt lanyards; install toe boards and drop-zone netting",
                "Verify green scaffold inspection tag signed within the last 7 days before stepping onto any scaffold"
            ),
            requiredPpe = listOf("Full Body Harness with Twin Shock-Absorbing Lanyards", "Chin-Strap Safety Helmet", "Steel Toe Boots with Ankle Support", "Tool Tethering Wrist Lanyards"),
            beforeWorkChecklist = listOf(
                "Check for valid green scaffold tag or completed MEWP pre-use inspection",
                "Verify overhead anchor point is solid and above head height where possible",
                "Barricade drop zone below with danger tape and warning signs",
                "Confirm rescue plan is in place to recover a fallen worker in under 15 minutes to avoid suspension trauma"
            ),
            duringWorkPrecautions = listOf(
                "Never stand on intermediate guardrails or toe boards",
                "Keep both feet on ladder rungs; maintain 3 points of contact on ladders",
                "Do not work outside during high winds (>35 km/h) or lightning storms"
            ),
            afterWorkPoints = listOf(
                "Lower all tools and materials safely using lifting buckets/ropes; do not throw items down",
                "Close trapdoors and restore guardrails on ladder access openings"
            ),
            doList = listOf(
                "Always use double lanyard for continuous connection when traversing",
                "Secure all hand tools, sockets, and radios with tool lanyards",
                "Report any yellow or red tagged scaffolding to the scaffold supervisor immediately"
            ),
            dontList = listOf(
                "Never attach fall arrest lanyards to conduit pipes, cable trays, or sprinkler pipes",
                "Never use a harness that has already arrested a fall or has cut webbing fibers",
                "Never throw tools or scrap metal from height"
            ),
            quiz = listOf(
                QuizQuestion(
                    question = "What does 100% tie-off mean when moving across elevated steel beams?",
                    options = listOf(
                        "You tie off 100% of your tools only",
                        "One lanyard hook must always remain securely anchored while moving the second hook",
                        "You tie off only when you feel dizzy",
                        "You attach 100% of your body weight to a handrail"
                    ),
                    correctAnswerIndex = 1,
                    explanation = "100% tie-off requires at least one connector to be attached to an approved anchorage point at all times."
                ),
                QuizQuestion(
                    question = "Why is suspension trauma dangerous for a worker hanging in a fall arrest harness?",
                    options = listOf(
                        "Blood pools in the lower legs due to gravity, depriving the brain of oxygen and causing unconsciousness within minutes",
                        "It causes immediate electric shock",
                        "It ruins the harness material",
                        "It makes the radio batteries drain"
                    ),
                    correctAnswerIndex = 0,
                    explanation = "Harness leg straps constrict veins; venous pooling can lead to cardiac arrest if rescue is not executed promptly."
                )
            )
        ),
        SafetyTopic(
            id = "topic_confined_space",
            title = "Confined Space Entry & Atmospheric Testing",
            category = "High-Risk Work",
            iconName = "MeetingRoom",
            summary = "Rules for entry into tanks, vessels, pits, and silos: Continuous gas testing, standby attendant, forced ventilation, and rescue plans.",
            whatIsIt = "A confined space is any space large enough for a person to bodily enter, has limited or restricted means for entry or exit, and is not designed for continuous human occupancy (e.g., storage tanks, boilers, manholes, degreasers, silos, underground vaults).",
            whyDangerous = "Atmospheres can be silently lethal. Oxygen deficiency, flammable vapors, or toxic gases (H2S, CO) cannot always be smelled or seen, killing workers and would-be rescuers within seconds.",
            commonHazards = listOf(
                "Oxygen deficiency (<19.5% O2) or oxygen enrichment (>23.5% O2)",
                "Toxic gas buildup: Hydrogen Sulfide (H2S), Carbon Monoxide (CO), Volatile Organic Compounds",
                "Flammable gases / vapors exceeding 10% of Lower Explosive Limit (LEL)",
                "Mechanical hazards: Rotating agitator blades, sludge engulfment, extreme thermal heat"
            ),
            consequences = listOf(
                "Rapid asphyxiation and sudden collapse upon inhaling 1-2 breaths of concentrated toxic gas",
                "Multiple fatalities when untrained coworkers rush in without breathing apparatus to rescue fallen workers"
            ),
            howToPrevent = listOf(
                "Perform positive mechanical energy isolation (LOTO + physical pipe blinding/blanking)",
                "Continuously purge and ventilate with fresh air (forced draft blower fans)",
                "Perform atmospheric testing at TOP, MIDDLE, and BOTTOM of space using calibrated 4-gas detector before entry",
                "Post a dedicated, trained Standby Attendant outside the hatch whose sole duty is monitoring entrants",
                "Equip all entrants with full body harness, retrieval cable connected to rescue tripod winch",
                "Continuous atmospheric monitoring inside the space throughout occupancy"
            ),
            requiredPpe = listOf("4-Gas Atmospheric Detector (worn in breathing zone)", "Retrieval Harness & Tripod Cable", "Supplied Air Respirator (SAR) / SCBA where atmospheric risk warrants", "Intrinsically Safe Lighting (Class 1 Div 1)"),
            beforeWorkChecklist = listOf(
                "Verify confined space entry permit is signed and posted at the hatch",
                "Confirm atmospheric test: O2 (19.5% - 23.5%), LEL (<10%), H2S (<10 ppm / 1 ppm TWA), CO (<25 ppm)",
                "Confirm mechanical agitators are locked out and lines are physically blinded",
                "Confirm standby attendant is stationed at the entrance with a dedicated communication radio"
            ),
            duringWorkPrecautions = listOf(
                "Maintain continuous mechanical ventilation; never turn off the blower fan while personnel are inside",
                "Evacuate immediately if personal gas monitor alarms or ventilation fails",
                "Never take cylinders (oxygen/acetylene) inside a confined space"
            ),
            afterWorkPoints = listOf(
                "Account for all entrants; verify head count with standby attendant log",
                "Remove all tools, temporary lighting, and ventilation ducts",
                "Close and secure the hatch; sign off and close the entry permit"
            ),
            doList = listOf(
                "Sample at multiple vertical elevations because gases stratify (H2S settles at bottom, CO disperses evenly, Methane rises to top)",
                "Calibrate / bump-test the 4-gas monitor on the same day before testing",
                "Standby attendant must remain outside at all times and call rescue team in an emergency"
            ),
            dontList = listOf(
                "NEVER enter a confined space to rescue someone without supplied air apparatus and emergency rescue team backup",
                "Never use pure oxygen to ventilate a confined space (creates extreme fire/explosion hazard)",
                "Never leave open confined space hatches unguarded"
            ),
            quiz = listOf(
                QuizQuestion(
                    question = "Why must gas testing be conducted at the top, middle, and bottom of a confined space?",
                    options = listOf(
                        "To check if the tank is clean",
                        "Because different gases have different vapor densities (some sink like H2S, some float like Methane)",
                        "To save battery power",
                        "It is only done if requested by management"
                    ),
                    correctAnswerIndex = 1,
                    explanation = "Methane is lighter than air (rises), CO has roughly same density as air, and H2S is heavier than air (settles at the bottom)."
                ),
                QuizQuestion(
                    question = "What is the primary role of the Standby Attendant stationed outside the hatch?",
                    options = listOf(
                        "To assist with welding inside",
                        "To leave and fetch coffee for the crew",
                        "To maintain continuous entrant log, monitor conditions, and summon emergency rescue without entering",
                        "To perform maintenance on tools"
                    ),
                    correctAnswerIndex = 2,
                    explanation = "The attendant must never enter the space; their role is communication, entrant tracking, and triggering rescue."
                )
            )
        ),
        SafetyTopic(
            id = "topic_loto",
            title = "Lockout / Tagout (LOTO) & Zero Energy",
            category = "High-Risk Work",
            iconName = "Lock",
            summary = "De-energizing, locking, tagging, bleeding stored energy, and verifying zero mechanical/electrical potential before maintenance.",
            whatIsIt = "Lockout/Tagout (LOTO) is a specific safety procedure used in industry to ensure that dangerous machines are properly shut off and not able to be started up again prior to the completion of maintenance or repair work.",
            whyDangerous = "Unexpected energization, startup, or release of stored mechanical/thermal energy during machine servicing can crush, sever limbs, electrocute, or fatally scald workers.",
            commonHazards = listOf(
                "Working under equipment relying solely on pushbutton or software stop (not physical disconnection)",
                "Unreleased stored energy: Trapped hydraulic pressure, compressed springs, elevated heavy rams, electrical capacitors",
                "Using someone else's padlock or sharing keys",
                "Failing to perform the 'TRY' step (zero-energy verification)"
            ),
            consequences = listOf(
                "Amputations from sudden machine indexing",
                "High-pressure hydraulic injection injuries into tissues",
                "Fatal electrocution from backfed circuits"
            ),
            howToPrevent = listOf(
                "Step 1: Preparation (Identify all energy sources: electrical, pneumatic, hydraulic, chemical, thermal, gravity)",
                "Step 2: Notification (Notify all affected operators in the work area)",
                "Step 3: Shutdown (Execute normal controlled machine stop sequence)",
                "Step 4: Isolation (Operate main disconnect switches, circuit breakers, and block valves)",
                "Step 5: Lockout/Tagout (Apply individual red personal padlock and danger tag to each isolation point)",
                "Step 6: Stored Energy Dissipation (Bleed air/fluid lines, lower weights to ground, block mechanical rams, discharge capacitors)",
                "Step 7: Zero-Energy Verification (Attempt to restart machine using local start button to verify zero movement; check with voltmeter)",
                "Step 8: Perform Maintenance Safely",
                "Step 9: Safe Restoration (Inspect area, replace all guards, notify crew, remove personal locks, restart)"
            ),
            requiredPpe = listOf("Standard PPE", "Personal Red Safety Padlock (Keyed Different)", "Danger Do-Not-Operate Tag", "Steel Lockout Hasp", "Calibrated Voltage Detector"),
            beforeWorkChecklist = listOf(
                "Review equipment specific LOTO placard",
                "Verify every team member has their own lock on the multi-lock hasp or group lockbox",
                "Perform physical verification test ('Try' button) before putting hands inside machinery"
            ),
            duringWorkPrecautions = listOf(
                "Keep personal padlock key in your own pocket at all times",
                "Never lend your key to anyone; never bypass a lockout"
            ),
            afterWorkPoints = listOf(
                "Ensure all tools and spare parts are removed from the machine interior",
                "Reinstall and bolt down all protective guards and interlocks",
                "Personally remove your own padlock after notifying affected workers"
            ),
            doList = listOf(
                "One worker, one lock, one key: Every person on the job must apply their own lock",
                "Always dissipate residual and stored energy (air pressure, gravity blocks)",
                "Verify zero energy before placing hands or tools into danger zones"
            ),
            dontList = listOf(
                "Never rely on a tag alone when a lock can be applied",
                "Never cut off or remove another worker's padlock without formal emergency procedure and authorization",
                "Never use emergency stop buttons or interlock switches as isolation points"
            ),
            quiz = listOf(
                QuizQuestion(
                    question = "Why is an Emergency Stop pushbutton NOT acceptable as a LOTO isolation point?",
                    options = listOf(
                        "It is too small",
                        "It relies on control circuitry and software, which can fail, short-circuit, or be reset accidentally",
                        "It is only used for fires",
                        "It damages the machine motor"
                    ),
                    correctAnswerIndex = 1,
                    explanation = "Control circuits can fail or short-circuit. LOTO requires physical disconnection of the primary power feed (breakers, disconnects)."
                ),
                QuizQuestion(
                    question = "If 4 workers are servicing a conveyor, how many personal padlocks must be on the lockout hasp?",
                    options = listOf(
                        "1 lock for the supervisor",
                        "2 locks",
                        "4 locks — one individual lock for each worker on the crew",
                        "No locks are needed if a sign is posted"
                    ),
                    correctAnswerIndex = 2,
                    explanation = "Every exposed worker must have their own personal padlock applied so the equipment cannot be re-energized until all workers are safe."
                )
            )
        ),
        SafetyTopic(
            id = "topic_grinding_safety",
            title = "Grinding & Abrasive Wheel Safety",
            category = "Industrial Safety",
            iconName = "Hardware",
            summary = "Pre-use wheel inspection, RPM rating matching, ring test, 180° guarding, spark containment, and PPE rules.",
            whatIsIt = "Grinding involves abrasive wheels and cutting discs spinning at 6,000 to 12,000+ RPM to cut, shape, or polish metals.",
            whyDangerous = "An abrasive wheel disc failure causes catastrophic disintegration, expelling razor-sharp shrapnel at bullet velocities (>150 km/h) directly into the operator's face, neck, and chest.",
            commonHazards = listOf(
                "Fitting a disc with a lower max RPM rating than the angle grinder's spindle speed",
                "Operating a grinder with the protective guard removed or adjusted facing the operator",
                "Using the flat side of a cutting disc for heavy grinding (causing wheel fracture)",
                "Using chipped, dropped, water-damaged, or expired resinoid wheels",
                "Wearing loose gloves or loose clothing that can catch in the spinning spindle",
                "Hot sparks igniting flammable solvents, rags, or wooden pallets in vicinity"
            ),
            consequences = listOf(
                "Severe facial fractures and loss of vision",
                "Catastrophic hand/finger amputations and tendon lacerations",
                "Flash fires ignited by hot grinding spark showers"
            ),
            howToPrevent = listOf(
                "Match RPM: Ensure the MAX RPM on the grinding disc is EQUAL TO OR GREATER THAN the grinder's no-load RPM",
                "Perform the Ring Test on vitrified wheels (tap gently with non-metallic handle; clear bell sound = sound wheel, dull thud = cracked)",
                "Keep the 180° wheel guard positioned between the abrasive wheel and the operator at all times",
                "Maintain two-handed firm control with dead-man safety trigger",
                "Never force the wheel into the cut; allow the abrasive disc speed to do the work",
                "Clear flammable materials within 10 meters; erect spark blankets"
            ),
            requiredPpe = listOf("Safety Glasses (EN 166B / ANSI Z87.1+) WORN UNDER a Full Face Shield", "Hearing Protection (Ear Plugs or Muffs)", "Safety Footwear with Steel Toe", "Snug-fitting Cut-Resistant Gloves (Avoid loose-cuff gloves)", "Flame-retardant Cotton / Leather Apron"),
            beforeWorkChecklist = listOf(
                "Check disc expiry date and visual integrity (no chips, cracks, or oil saturation)",
                "Verify grinder spindle RPM <= Wheel Max RPM rating",
                "Ensure guard is securely clamped in correct angular orientation",
                "Inspect power cable for cracks; verify dead-man switch releases smoothly"
            ),
            duringWorkPrecautions = listOf(
                "Run grinder in a safe direction at full speed for 1 minute before touching workpiece",
                "Stand slightly to the side of the wheel rotation plane, never directly in line",
                "Do not apply excessive side pressure on thin cutting discs"
            ),
            afterWorkPoints = listOf(
                "Wait for wheel to come to a complete dead stop before setting grinder on the floor or bench",
                "Unplug power cord before changing discs; use the correct pin spanner (never hammer the flange)"
            ),
            doList = listOf(
                "Always use double eye protection: Safety goggles AND a full face shield",
                "Use the correct tool-flange and locknut matched to the wheel thickness",
                "Secure workpieces firmly in a vise or heavy clamp"
            ),
            dontList = listOf(
                "NEVER remove or alter the grinder's protective wheel guard",
                "Never use an oversized wheel by forcing it onto a smaller grinder spindle",
                "Never use the side of a thin cutting wheel for grinding operations"
            ),
            quiz = listOf(
                QuizQuestion(
                    question = "If an angle grinder has a rated speed of 11,000 RPM, which abrasive disc is safe to mount?",
                    options = listOf(
                        "A disc rated for Max 8,500 RPM",
                        "A disc rated for Max 10,000 RPM",
                        "A disc rated for Max 12,200 RPM",
                        "Any disc that fits the arbor hole"
                    ),
                    correctAnswerIndex = 2,
                    explanation = "The wheel's rated maximum RPM must always equal or exceed the machine's maximum operating spindle RPM to prevent disc explosion."
                ),
                QuizQuestion(
                    question = "Why must a face shield be worn WITH safety glasses underneath during grinding?",
                    options = listOf(
                        "Because high-velocity particles and shattered fragments can bounce under the rim of a face shield",
                        "To look professional",
                        "Only because of dust",
                        "It is not necessary to wear both"
                    ),
                    correctAnswerIndex = 0,
                    explanation = "A face shield protects the facial skin, but secondary safety spectacles are required to seal the eyes against bouncing high-speed swarf."
                )
            )
        ),
        SafetyTopic(
            id = "topic_chemical_spills",
            title = "Chemical Safety & Corrosive Spill Response",
            category = "Emergency",
            iconName = "Science",
            summary = "GHS pictograms, SDS sheets, acid/base dilution rules, emergency eyewash stations, and spill kit deployment.",
            whatIsIt = "Industrial chemical safety encompasses safe storage, transfer, compatibility segregation, PPE selection, and rapid spill response for acids, bases, flammables, and toxic reagents.",
            whyDangerous = "Corrosives (HCl, Sulfuric Acid, NaOH) cause immediate irreversible skin and eye destruction; toxic vapors incapacitate lungs, and incompatible mixing generates lethal gas clouds (e.g., mixing acid with bleach generates toxic chlorine gas).",
            commonHazards = listOf(
                "Mixing incompatible chemicals (e.g., strong acids with cyanides, hypochlorites, or bases)",
                "Adding water directly into concentrated sulfuric acid (violent boiling eruption)",
                "Inadequate chemical-resistant glove selection (permeation through thin latex)",
                "Blocked emergency eyewash stations or disconnected water supplies",
                "Unlabelled secondary transfer bottles and sprayers"
            ),
            consequences = listOf(
                "Permanent chemical blindness in less than 5 seconds",
                "Severe 3rd-degree chemical burns requiring skin grafts",
                "Toxic pulmonary edema and asphyxiation"
            ),
            howToPrevent = listOf(
                "Consult the current Safety Data Sheet (SDS) Section 8 (PPE) and Section 10 (Stability/Reactivity)",
                "Always add acid to water slowly with constant stirring (A&W rule), never the reverse",
                "Segregate chemicals according to compatibility storage groups (separate acids from bases, oxidizers from organics)",
                "Deploy universal chemical absorbent spill socks and neutralizer pads from the spill kit",
                "Know the exact 10-second pathway to the nearest emergency shower/eyewash station"
            ),
            requiredPpe = listOf("Chemical Splash Goggles (indirectly vented)", "Full Neoprene / Polycarbonate Face Shield", "Butyl / Nitrile Chemical Gauntlet Gloves", "PVC Chemical Apron or Full Coverall", "Chemical Resistant Rubber Safety Boots"),
            beforeWorkChecklist = listOf(
                "Test the emergency eyewash station to ensure clean, flowing water at comfortable temperature",
                "Check that secondary chemical containers have GHS compliant hazard labels",
                "Verify chemical spill kit is fully stocked with neutralizer and absorbent booms"
            ),
            duringWorkPrecautions = listOf(
                "Work inside a certified chemical fume hood or well-ventilated outdoor pad",
                "Keep chemical containers closed and sealed when not in active transfer",
                "Never smell, taste, or siphon chemicals using mouth suction"
            ),
            afterWorkPoints = listOf(
                "Decontaminate tools and wash gloves thoroughly with clean water before removal",
                "Dispose of contaminated rags and absorbent in hazardous waste sealed drums"
            ),
            doList = listOf(
                "Flush eyes with clean water for a full 15-20 minutes holding eyelids wide open if splashed",
                "Keep acids and bases stored in separate spill-containment secondary bunds",
                "Wear the specific glove material certified against the chemical's breakthrough time"
            ),
            dontList = listOf(
                "NEVER mix bleach (Sodium Hypochlorite) with any acid or ammonia cleaner",
                "Never store chemicals in food or beverage containers under any circumstances",
                "Never block emergency showers with empty pallets or drums"
            ),
            quiz = listOf(
                QuizQuestion(
                    question = "What happens if someone accidentally mixes hydrochloric acid with sodium hypochlorite (bleach)?",
                    options = listOf(
                        "It creates a safe cleaning mixture",
                        "It releases deadly toxic Chlorine Gas (Cl2), which can cause fatal lung damage",
                        "It cools down into water",
                        "Nothing happens"
                    ),
                    correctAnswerIndex = 1,
                    explanation = "Mixing acid with hypochlorite immediately liberates highly toxic green-yellow chlorine gas."
                ),
                QuizQuestion(
                    question = "What is the proper procedure for diluting concentrated acid with water?",
                    options = listOf(
                        "Pour water rapidly into the acid container",
                        "Always add the acid slowly into the water (Acid to Water - A&W)",
                        "Mix both together in equal volumes at once",
                        "Heat the acid first"
                    ),
                    correctAnswerIndex = 1,
                    explanation = "Adding acid into water allows the large volume of water to absorb the exothermic heat. Adding water to acid causes violent boiling and acid splatter."
                )
            )
        )
    )

    val toolboxTalks: List<ToolboxTalk> = listOf(
        ToolboxTalk(
            id = "tb_hand_safety",
            title = "Hand Safety & Pinch-Point Awareness",
            category = "Hand Safety",
            duration = "5 Minutes",
            objective = "Eliminate hand and finger crush, pinch, and laceration injuries during material handling and tool operation.",
            keyHazards = listOf(
                "Pinch points between moving machinery and fixed guards",
                "Sharp metal burrs, sheet metal edges, and steel swarf",
                "Glove entanglement in drill spindles and rotating lathes",
                "Crush injuries while placing pipe flanges or positioning loads with cranes"
            ),
            discussionPoints = listOf(
                "Never place hands where you cannot clearly see them or where machinery can shift suddenly",
                "Use tag lines and push-sticks instead of placing hands directly on suspended crane loads",
                "Inspect hand tools for mushroomed chisel heads, cracked wooden handles, and loose hammer heads",
                "Remove rings, watches, and loose wristbands before working with industrial machinery"
            ),
            requiredPpe = listOf("Impact-resistant mechanics gloves", "Cut-resistant Level 5 gloves", "Safety glasses"),
            safePractices = listOf(
                "Position hands away from nip points when lowering or setting down heavy items",
                "De-burr freshly cut pipes and sheet metal edges before handling",
                "Use magnetic lifters or push-sticks when feeding workpieces to saws"
            ),
            unsafePractices = listOf(
                "Placing fingers inside bolt holes of pipe flanges to check alignment (use alignment pins instead)",
                "Wearing loose knit gloves near rotating spindles",
                "Bypassing machine interlock switches to clear jams with bare hands"
            ),
            workerQuestions = listOf(
                "What tools do we have today that can keep our hands away from the point of operation?",
                "Are there any pinch points on this specific machine that lack adequate guarding?"
            ),
            supervisorNotes = "Inspect the crew's gloves before work starts. Replace worn, oil-soaked, or torn gloves immediately. Emphasize alignment pins for pipe fitters."
        ),
        ToolboxTalk(
            id = "tb_fire_prevention",
            title = "Hot Work Fire Prevention & Fire Watch Duties",
            category = "Fire Safety",
            duration = "10 Minutes",
            objective = "Ensure zero hot work fire incidents through proper area preparation, spark shielding, and dedicated fire watch protocol.",
            keyHazards = listOf(
                "Hot welding slag falling through floor grating into lower levels",
                "Flammable solvent vapors, grease buildup, or combustible packaging within 10 meters",
                "Smoldering sparks inside insulation or wall cavities going unnoticed until after shift end"
            ),
            discussionPoints = listOf(
                "A 10-meter (35-ft) radius around hot work must be cleared of all combustible materials or covered with FM-approved fire blankets",
                "Floor openings, drains, and grating must be tightly sealed with fire-resistant covers",
                "A charged 10kg ABC dry chemical or CO2 extinguisher must be located immediately at hand",
                "The designated Fire Watch person must have no other job duties while hot work is active"
            ),
            requiredPpe = listOf("Flame-retardant coveralls", "Welding leather gloves and jacket", "Safety glasses + welding shield"),
            safePractices = listOf(
                "Continuous fire watch during all cutting, grinding, and welding operations",
                "Maintain fire watch surveillance for a minimum of 30 to 60 minutes after hot work ceases",
                "Check the reverse side of walls and floors when welding on pipes or bulkheads"
            ),
            unsafePractices = listOf(
                "Allowing the fire watch person to perform grinding, cleaning, or cell phone browsing while on watch",
                "Starting welding without testing drains and manholes for flammable vapors with a gas detector",
                "Leaving hot electrode stubs on combustible scaffolding boards"
            ),
            workerQuestions = listOf(
                "Where is the nearest fire alarm pull station and emergency fire water hose reel located?",
                "Who is our designated Fire Watch for this hot work permit?"
            ),
            supervisorNotes = "Verify the Hot Work Permit is signed on site. Ensure the fire extinguisher pressure gauge is in the green zone before granting permission to strike an arc."
        ),
        ToolboxTalk(
            id = "tb_heat_stress",
            title = "Heat Stress Prevention & Hydration Protocols",
            category = "Heat Stress",
            duration = "5 Minutes",
            objective = "Recognize early signs of heat exhaustion and heat stroke, and implement structured work-rest and hydration cycles.",
            keyHazards = listOf(
                "High ambient temperatures combined with high relative humidity and radiant heat from equipment",
                "Heavy physical exertion while wearing impermeable chemical or fire-retardant suits",
                "Dehydration leading to dizziness, slowed reaction time, heat cramps, and fatal heat stroke"
            ),
            discussionPoints = listOf(
                "Drink water or electrolyte solutions every 15-20 minutes before feeling thirsty",
                "Differentiate Heat Exhaustion (heavy sweating, clammy skin, weakness, dizziness) from Heat Stroke (hot dry skin or profuse sweating, confusion, seizure - MEDICAL EMERGENCY)",
                "Implement structured rest breaks in shaded, ventilated rest shelters according to heat index",
                "Utilize the buddy system: Watch your coworker for slurred speech, confusion, or staggering"
            ),
            requiredPpe = listOf("Lightweight breathable cotton undergarments", "UV-protective safety glasses", "Neck shade attachments on hard hats"),
            safePractices = listOf(
                "Consume 1 cup (250ml) of cool water every 15-20 minutes in high heat environments",
                "Gradually acclimatize new or returning workers over a 7-to-14 day period",
                "Move any worker feeling faint into deep shade, loosen clothing, apply cool damp cloths, and provide water if conscious"
            ),
            unsafePractices = listOf(
                "Consuming energy drinks or excessive caffeine which accelerates dehydration",
                "Skipping scheduled hydration breaks to 'finish the job faster'",
                "Ignoring a coworker who stops sweating and displays confusion"
            ),
            workerQuestions = listOf(
                "Where is our shaded rest area and fresh drinking water cooler located today?",
                "What immediate action should you take if your buddy becomes disoriented in the sun?"
            ),
            supervisorNotes = "Monitor the Heat Index/WBGT hourly. In high heat (Heat Index > 38°C/100°F), mandate 15 minutes of rest for every 45 minutes of heavy physical labor."
        ),
        ToolboxTalk(
            id = "tb_slips_trips",
            title = "Slip, Trip, and Fall Prevention & 5S",
            category = "Housekeeping",
            duration = "5 Minutes",
            objective = "Maintain pristine walking and working surfaces to eliminate slip and trip hazards in industrial areas.",
            keyHazards = listOf(
                "Oil, grease, chemical, or water leaks on steel grating or smooth concrete floors",
                "Cables, pneumatic hoses, and welding leads trailing across main walkways",
                "Unsecured transition plates, raised grating lips, and open floor penetrations",
                "Tools, discarded scrap metal, and wooden pallets cluttering work areas"
            ),
            discussionPoints = listOf(
                "Housekeeping is an ongoing part of the job, not something done only at the end of the shift",
                "Route all cables and hoses overhead using S-hooks or cover with heavy-duty cable ramps",
                "Clean up fluid spills immediately or deploy barricades and absorbent if large",
                "Always keep one hand free to use stair handrails (3-point contact on stairs)"
            ),
            requiredPpe = listOf("Slip-resistant oil-resistant safety footwear with defined heel", "Safety glasses"),
            safePractices = listOf(
                "Elevate all temporary power cables at least 2.1m (7ft) above walkways",
                "Report burned-out light fixtures in stairwells and dark corridors promptly",
                "Maintain 'Clean-as-you-go' discipline throughout the entire work shift"
            ),
            unsafePractices = listOf(
                "Carrying tall loads that block forward vision while walking down stairs",
                "Leaving tools on top of ladders or elevated grating where they can be kicked off",
                "Walking while looking down at a mobile phone or clipboard"
            ),
            workerQuestions = listOf(
                "Are there any cables crossing pedestrian walkways in our bay right now?",
                "Do we have absorbent pads available if an oil line drips?"
            ),
            supervisorNotes = "Walk the work route with the crew. Remind everyone that 35% of all lost-time industrial injuries originate from slips, trips, and level falls."
        )
    )

    val ppeRecommendations: List<PpeRecommendation> = listOf(
        PpeRecommendation(
            jobId = "grinding",
            jobName = "Grinding & Abrasive Cutting",
            iconName = "Hardware",
            requiredPpe = listOf(
                "Safety Goggles (EN 166B / ANSI Z87.1+ High Impact)",
                "Full Polycarbonate Face Shield (Worn over goggles)",
                "Hearing Protection (Ear Plugs or Ear Muffs - NRR >= 25dB)",
                "Safety Shoes with Steel Toe & Puncture Resistant Midsole",
                "Snug-fitting Cut-Resistant Gloves (Avoid loose gauntlets)",
                "Flame-Retardant Heavy Cotton or Leather Apron / Sleeves"
            ),
            whyItems = mapOf(
                "Full Polycarbonate Face Shield" to "Protects the face from high-velocity hot spark showers and shrapnel if the abrasive wheel fractures.",
                "Safety Goggles" to "Prevents microscopic fine metal swarf and dust from bouncing underneath the face shield into the eyes.",
                "Snug-fitting Cut-Resistant Gloves" to "Protects hands from sharp burrs and workpiece edges while minimizing entanglement risk.",
                "Hearing Protection" to "Angle grinders routinely generate 95 to 105 dBA, causing irreversible hearing damage without protection."
            ),
            siteWarnings = "IMPORTANT ROTATING MACHINERY WARNING: Follow your site-specific risk assessment regarding glove usage. Never wear loose, floppy gloves around rotating spindles where glove snagging can pull hands into the wheel.",
            inspectionChecklist = listOf(
                "Check face shield visor for deep scratches, cracks, or heat warping",
                "Inspect safety goggle straps for elasticity and airtight seal",
                "Verify safety footwear leather is free of chemical saturation and steel cap is intact"
            )
        ),
        PpeRecommendation(
            jobId = "welding",
            jobName = "Arc / MIG / TIG Welding",
            iconName = "Flame",
            requiredPpe = listOf(
                "Welding Helmet with Auto-Darkening Filter (Shade 10 - 13)",
                "Split-Leather Welding Gauntlet Gloves",
                "Flame-Retardant Welding Jacket / Leather Sleeves",
                "Safety Spectacles with Side Shields (Worn under helmet)",
                "Steel Toe Safety Boots with Leather Metatarsal Guards",
                "Welding Fume Respirator (N95 / FFP2 or Powered Air Hood)"
            ),
            whyItems = mapOf(
                "Welding Helmet Shade 10-13" to "Filters extreme Ultraviolet (UV) and Infrared (IR) radiation preventing painful 'Arc Eye' corneal burns and retinal damage.",
                "Split-Leather Gauntlets" to "Shields hands and forearms from molten spatter, radiant heat, and electric shock from electrode holders.",
                "Welding Fume Respirator" to "Captures toxic metal oxide fumes (hexavalent chromium, manganese, ozone) that cause metal fume fever and lung disease."
            ),
            siteWarnings = "Ensure secondary flash screens are erected around the welding bay to protect passing coworkers from arc flash burns.",
            inspectionChecklist = listOf(
                "Inspect auto-darkening helmet cover lens for spatter pits and battery charge",
                "Check leather welding gloves for holes, burnt seams, or moisture",
                "Ensure welding boots have no exposed synthetic laces that can melt"
            )
        ),
        PpeRecommendation(
            jobId = "chemical_handling",
            jobName = "Corrosive Chemical Handling (HCl, NaOH, H2SO4)",
            iconName = "Science",
            requiredPpe = listOf(
                "Indirect-Vent Chemical Splash Goggles",
                "Full Face Shield (Polycarbonate/Acetate)",
                "Heavy-Duty Chemical Gauntlet Gloves (Nitrile / Butyl / Neoprene)",
                "Full PVC / Neoprene Chemical Splash Apron or Suit",
                "Chemical-Resistant Rubber Safety Boots with Steel Toe"
            ),
            whyItems = mapOf(
                "Indirect-Vent Goggles" to "Indirect vents allow air flow while blocking direct liquid chemical splash penetration into the eyes.",
                "Chemical Gauntlet Gloves" to "Resists chemical permeation, degradation, and breakthrough of strong acids and caustic alkalis.",
                "PVC Chemical Apron" to "Prevents corrosive liquids from soaking through clothing onto the chest, abdomen, and legs."
            ),
            siteWarnings = "Glove selection must match the specific chemical: Standard nitrile breaks down quickly against concentrated nitric acid; use butyl or Viton for specialized solvents.",
            inspectionChecklist = listOf(
                "Perform air-inflation leak test on chemical gloves: Roll the cuff towards fingers to check for pinhole leaks",
                "Verify goggles create a complete flush seal around facial contours",
                "Confirm chemical suit has no brittle creases, tears, or degraded seams"
            )
        ),
        PpeRecommendation(
            jobId = "height_work",
            jobName = "Working at Height (> 1.8m)",
            iconName = "Elevator",
            requiredPpe = listOf(
                "Full Body Fall Arrest Harness (EN 361 / ANSI Z359.11)",
                "Twin Shock-Absorbing Lanyards with Scaffold Hooks",
                "Safety Helmet with Y-Style 4-Point Chin Strap",
                "High-Traction Safety Boots with Ankle Support",
                "Tool Tethering Lanyards and Wrist Straps"
            ),
            whyItems = mapOf(
                "Full Body Harness" to "Distributes fall impact forces evenly across the pelvis, thighs, chest, and shoulders, keeping the worker upright.",
                "Shock-Absorbing Lanyard" to "Tears progressively during a fall to limit arresting peak force on the human body to under 6 kN (1,350 lbs).",
                "Chin-Strap Helmet" to "Prevents the hard hat from flying off during a sudden slip or head-first pendulum fall."
            ),
            siteWarnings = "Ensure fall clearance calculation is verified before work: Total fall clearance = Lanyard length (1.8m) + Deceleration distance (1.2m) + Worker height (1.8m) + Safety factor (1.0m) = 5.8m minimum clearance required.",
            inspectionChecklist = listOf(
                "Inspect harness webbing for fraying, cuts, chemical burns, or UV discoloration",
                "Check D-rings and metal buckles for distortion, cracks, or rust",
                "Verify shock absorber pack has not deployed or torn open",
                "Check inspection tag date to ensure annual competent-person certification is current"
            )
        ),
        PpeRecommendation(
            jobId = "confined_space",
            jobName = "Confined Space Entry",
            iconName = "MeetingRoom",
            requiredPpe = listOf(
                "Personal 4-Gas Detector (Worn on chest in breathing zone)",
                "Full Body Retrieval Harness with Overhead D-Ring / Shoulder Straps",
                "Rescue Tripod Winch Cable Attachment",
                "Intrinsically Safe Cap Lamp (ATEX / IECEx certified)",
                "Supplied Air Respirator (SAR) or SCBA if required by atmosphere",
                "Standard Helmet, Steel Toe Boots, and Hi-Vis Coverall"
            ),
            whyItems = mapOf(
                "Personal 4-Gas Detector" to "Continuously measures O2, LEL, H2S, and CO levels in real-time right at the entrant's mouth/nose level.",
                "Retrieval Harness" to "Allows the outside standby attendant to winch an unconscious entrant to safety vertically without entering the hazardous space.",
                "Intrinsically Safe Lighting" to "Guaranteed not to generate electrical sparks that could ignite flammable vapors inside the tank."
            ),
            siteWarnings = "Never enter a confined space based on gas tests conducted outside; testing must verify all levels of the interior prior to and during occupancy.",
            inspectionChecklist = listOf(
                "Bump-test 4-gas monitor against certified calibration gas cylinder",
                "Inspect retrieval harness D-rings and rescue winch cable for kinking or wire frays",
                "Verify flashlight has valid explosion-proof / intrinsically safe marking"
            )
        )
    )

    val ppeLibrary: List<PpeLibraryItem> = listOf(
        PpeLibraryItem(
            id = "ppe_helmet",
            name = "Industrial Safety Helmet (Hard Hat)",
            category = "Head Protection",
            iconName = "Security",
            purpose = "Protects the skull from falling objects, overhead structural collisions, and electrical shock.",
            hazardsControlled = listOf("Falling tools / debris", "Swinging crane loads", "Low pipe overhead impacts", "High-voltage contact (Class E/G)"),
            whenToUse = listOf("Mandatory across all plant operational bays, construction zones, and scaffolding areas"),
            whenNotToUse = listOf("Never use a cracked, heat-damaged, or painted helmet"),
            inspectionPoints = listOf("Check outer shell for dents, gouges, or sun fade", "Check internal cradle suspension webbing for tears", "Ensure chin strap tensioner locks tightly"),
            limitations = listOf("Does not protect face or eyes; must be paired with safety glasses"),
            replacementCriteria = listOf("Replace immediately after receiving a significant impact", "Replace shell every 3 to 5 years per manufacturer date stamp"),
            correctUsage = listOf("Wear with peak facing forward; adjust headband ratchet so helmet remains snug without falling when bending forward")
        ),
        PpeLibraryItem(
            id = "ppe_goggles",
            name = "Safety Goggles (Chemical & Impact)",
            category = "Eye Protection",
            iconName = "Visibility",
            purpose = "Forms an airtight or liquid-tight seal around the eye orbits to shield against chemical splashes, liquid sprays, and fine airborne dust.",
            hazardsControlled = listOf("Acid/alkali chemical splashes", "Heavy dust clouds", "Molten metal droplets", "High-velocity particles"),
            whenToUse = listOf("Handling hazardous liquids, opening chemical flanges, working in severe dust environments"),
            whenNotToUse = listOf("Direct-vented goggles must NEVER be used for corrosive chemicals (use indirect-vent only)"),
            inspectionPoints = listOf("Check lens for deep scratches and cloudiness", "Verify elastic headband maintains firm seal against face", "Ensure vent caps are clean and intact"),
            limitations = listOf("Can fog up in humid climates; select anti-fog coated models"),
            replacementCriteria = listOf("Replace when lens clarity is compromised or elastic strap loses elasticity"),
            correctUsage = listOf("Ensure the rubber flange sits flat against skin without gaps over the bridge of the nose")
        ),
        PpeLibraryItem(
            id = "ppe_face_shield",
            name = "Full Face Shield (Clear Polycarbonate)",
            category = "Face Protection",
            iconName = "Face",
            purpose = "Provides full-facial coverage from forehead to below the chin against flying swarf, grinding sparks, and chemical splashes.",
            hazardsControlled = listOf("Grinding wheel debris", "High-pressure fluid jet mist", "Molten metal splashes", "Arc flash thermal blast"),
            whenToUse = listOf("Angle grinding, lathe operation, high-pressure washing, chemical line breaking"),
            whenNotToUse = listOf("Never use as standalone eye protection (must always wear safety glasses underneath)"),
            inspectionPoints = listOf("Check visor transparency, impact cracks near mounting brackets, and ratchet crown tightness"),
            limitations = listOf("Gaps beneath chin allow bouncing debris; primary safety glasses are mandatory underneath"),
            replacementCriteria = listOf("Replace when pitted with heavy spark burns or scratched beyond clear visibility"),
            correctUsage = listOf("Adjust crown ratchet so visor drops smoothly and extends below the chin")
        ),
        PpeLibraryItem(
            id = "ppe_harness",
            name = "Full Body Fall Arrest Harness",
            category = "Fall Protection",
            iconName = "Elevator",
            purpose = "Distributes dynamic deceleration forces safely across pelvic girdle, thighs, and shoulders during an arrested fall.",
            hazardsControlled = listOf("Fatal falls from scaffolding, ladders, towers, and roofs"),
            whenToUse = listOf("Any elevated work above site fall protection trigger height (>1.8m) without complete guardrails"),
            whenNotToUse = listOf("Never use a harness that has already arrested a fall or has cut/burned webbing"),
            inspectionPoints = listOf("Inspect every inch of webbing by bending in an inverted U to reveal cut fibers", "Check dorsal D-ring for distortion or cracks", "Verify all pass-through buckles click and latch"),
            limitations = listOf("Will cause suspension trauma if worker hangs motionless for >10 minutes; prompt rescue plan required"),
            replacementCriteria = listOf("Replace after arresting any fall, after failing annual inspection, or after 5 years from date of first use"),
            correctUsage = listOf("Dorsal D-ring must sit centered between shoulder blades; thigh straps must be snug enough to fit only a flat hand beneath")
        )
    )

    val safetyLimits: List<SafetyLimitItem> = listOf(
        SafetyLimitItem(
            category = "Confined Space",
            parameter = "Oxygen (O2) Concentration",
            safeRange = "19.5% to 23.5% O2 by volume",
            warningLimit = "< 19.5% (Deficient) or > 21.5% (Enriched)",
            dangerThreshold = "< 16.0% (Impaired judgment) or > 23.5% (Extreme fire risk)",
            unit = "% Vol",
            referenceSource = "OSHA 29 CFR 1910.146 / NIOSH Standards",
            referenceDate = "2024 Reference Update",
            notes = "Oxygen below 19.5% causes rapid mental impairment and loss of consciousness. Oxygen above 23.5% causes materials to burn explosively."
        ),
        SafetyLimitItem(
            category = "Confined Space",
            parameter = "Flammable Gas / Lower Explosive Limit (LEL)",
            safeRange = "0% to 5% LEL",
            warningLimit = ">= 5% LEL (Investigate source & ventilate)",
            dangerThreshold = ">= 10% LEL (Mandatory immediate evacuation, no hot work)",
            unit = "% LEL",
            referenceSource = "OSHA 1910.146 Confined Space Standard",
            referenceDate = "2024 Reference Update",
            notes = "Hot work is strictly prohibited if combustible gas exceeds 5% LEL. General entry forbidden above 10% LEL."
        ),
        SafetyLimitItem(
            category = "Confined Space",
            parameter = "Hydrogen Sulfide (H2S)",
            safeRange = "0.0 ppm",
            warningLimit = ">= 1.0 ppm (ACGIH TLV-TWA 8-hr)",
            dangerThreshold = ">= 10.0 ppm (Ceiling) / 100 ppm (IDLH - Instant fatality)",
            unit = "ppm",
            referenceSource = "ACGIH TLV / OSHA PEL",
            referenceDate = "2024 Reference Update",
            notes = "Rotten egg odor is NOT a reliable warning because H2S rapidly paralyzes the olfactory nerve (loss of smell) within seconds."
        ),
        SafetyLimitItem(
            category = "Confined Space",
            parameter = "Carbon Monoxide (CO)",
            safeRange = "0.0 to 9.0 ppm",
            warningLimit = ">= 25 ppm (ACGIH TLV-TWA 8-hr)",
            dangerThreshold = ">= 50 ppm (OSHA PEL) / 1,200 ppm (IDLH)",
            unit = "ppm",
            referenceSource = "OSHA 1910.1000 / ACGIH Guidelines",
            referenceDate = "2024 Reference Update",
            notes = "Colorless, odorless asphyxiant gas produced by incomplete combustion of engines, heaters, and burners."
        ),
        SafetyLimitItem(
            category = "Noise",
            parameter = "8-Hour Time-Weighted Average (TWA)",
            safeRange = "< 80 dBA",
            warningLimit = ">= 85 dBA (Action level: Hearing conservation program mandatory)",
            dangerThreshold = ">= 90 dBA (OSHA PEL: Mandatory hearing protection required)",
            unit = "dBA",
            referenceSource = "OSHA 1910.95 / NIOSH REL Criteria",
            referenceDate = "2024 Reference Update",
            notes = "NIOSH uses a 3dB exchange rate (85 dBA for 8h, 88 dBA for 4h). OSHA uses a 5dB exchange rate (90 dBA for 8h, 95 dBA for 4h)."
        ),
        SafetyLimitItem(
            category = "Lifting",
            parameter = "Sling Leg Angle Factor to Horizontal",
            safeRange = "60° to 90° (Load factor 1.000 to 1.155)",
            warningLimit = "45° to 59° (Load factor 1.414 - 41% tension increase)",
            dangerThreshold = "< 30° (Load factor >= 2.000 - Extreme tension multiplier, NEVER lift below 30°)",
            unit = "Degrees (°)",
            referenceSource = "ASME B30.9 Slings / OSHA 1910.184",
            referenceDate = "2024 Reference Update",
            notes = "As sling angle flattens towards horizontal, tension inside each sling leg skyrockets geometrically. At 30°, each leg carries the entire weight of the load."
        )
    )

    val chemicals: List<ChemicalItem> = listOf(
        ChemicalItem(
            id = "chem_hcl",
            name = "Hydrochloric Acid (37% Muriatic Acid)",
            formula = "HCl",
            cas = "7647-01-0",
            ghsPictograms = listOf("Corrosive", "Toxic / Acute Toxicity", "Irritant"),
            hazards = listOf(
                "Causes severe skin burns and serious eye damage",
                "Corrosive to respiratory tract; produces pungent fumes",
                "Reacts violently with strong bases liberating extreme heat",
                "Reacts with hypochlorite bleach producing deadly Chlorine gas"
            ),
            requiredPpe = listOf("Chemical Splash Goggles + Face Shield", "Butyl / Neoprene Gauntlet Gloves", "PVC Acid Apron", "Acid Gas Cartridge Respirator"),
            storage = "Store in dedicated corrosion-proof poly cabinet with secondary containment; keep separated from bases, cyanides, and hypochlorites.",
            incompatibilities = listOf("Sodium Hydroxide (Caustic soda)", "Sodium Hypochlorite (Bleach)", "Strong Oxidizers", "Metals (generates flammable Hydrogen gas)"),
            firstAid = "Skin/Eyes: Flush with copious water for minimum 15-20 minutes. Remove contaminated clothes. Inhalation: Move to fresh air. Call emergency medical services immediately.",
            spillResponse = "Evacuate area. Neutralize slowly with sodium bicarbonate (baking soda) or lime slurry before absorbing with inert vermiculite. Do not use metal shovels.",
            fireResponse = "Non-combustible. Use water spray to cool exposed drums and knock down acidic vapor clouds.",
            sdsNote = "Always verify with the manufacturer-specific Safety Data Sheet (SDS) on site."
        ),
        ChemicalItem(
            id = "chem_naoh",
            name = "Sodium Hydroxide (Caustic Soda / Lye 50%)",
            formula = "NaOH",
            cas = "1310-73-2",
            ghsPictograms = listOf("Corrosive", "Skin Corrosion Grade 1A"),
            hazards = listOf(
                "Extremely corrosive: Causes immediate saponification of skin fats and rapid deep tissue destruction",
                "Permanent irreversible blindness upon eye contact in seconds",
                "Highly exothermic when diluted with water; can boil and splatter"
            ),
            requiredPpe = listOf("Indirect-vent Chemical Goggles", "Polycarbonate Face Shield", "Nitrile / Neoprene Heavy Gauntlets", "PVC Chemical Apron"),
            storage = "Store in dry, well-ventilated dedicated caustic area in polyethylene or lined steel tanks; separate from strong acids and reactive metals (Aluminum, Zinc).",
            incompatibilities = listOf("Hydrochloric Acid", "Sulfuric Acid", "Aluminum and Zinc (generates Hydrogen gas)", "Chlorinated Solvents"),
            firstAid = "Skin: Flush immediately with continuous water for 20+ minutes until slippery soapy feeling is gone. Eyes: Flush holding eyelids open for 20 minutes. Seek urgent medical care.",
            spillResponse = "Contain with universal absorbent booms. Neutralize carefully with dilute weak acid (acetic acid or citric acid) before recovery. Wear full chemical suit.",
            fireResponse = "Non-combustible. Extinguish surrounding fire with suitable media. Water spray can be used to cool drums.",
            sdsNote = "Always verify with the manufacturer-specific Safety Data Sheet (SDS) on site."
        ),
        ChemicalItem(
            id = "chem_naclo",
            name = "Sodium Hypochlorite (Industrial Bleach 12-15%)",
            formula = "NaClO",
            cas = "7681-52-9",
            ghsPictograms = listOf("Corrosive", "Environmental Hazard", "Irritant"),
            hazards = listOf(
                "Causes severe skin and eye irritation/burns",
                "Contact with acids liberates highly toxic Chlorine gas (Cl2)",
                "Contact with ammonia produces explosive/toxic chloramines",
                "Strong oxidizer that decomposes in sunlight releasing oxygen"
            ),
            requiredPpe = listOf("Chemical Splash Goggles", "Nitrile or PVC Gloves", "Face Shield", "Rubber Safety Boots"),
            storage = "Store in cool, dark, well-ventilated area in opaque vented plastic drums. Keep away from direct sunlight, acids, and organics.",
            incompatibilities = listOf("Hydrochloric Acid (Releases Chlorine Gas)", "Sulfuric Acid", "Ammonia cleaners", "Organic Solvents", "Reducing Agents"),
            firstAid = "Flush skin and eyes immediately with water for 15 minutes. If toxic fumes inhaled, move to fresh air and provide oxygen if trained.",
            spillResponse = "Absorb with inert absorbent (vermiculite/sand). Do NOT use sawdust or organic material. Dilute and neutralize with sodium thiosulfate.",
            fireResponse = "Non-combustible. Use water fog to cool drums. Decomposes to release oxygen supporting nearby fires.",
            sdsNote = "Always verify with the manufacturer-specific Safety Data Sheet (SDS) on site."
        ),
        ChemicalItem(
            id = "chem_h2so4",
            name = "Sulfuric Acid (98% Concentrated)",
            formula = "H2SO4",
            cas = "7664-93-9",
            ghsPictograms = listOf("Corrosive", "Severe Toxicity", "Skin Corrosion 1A"),
            hazards = listOf(
                "Extremely aggressive dehydrating corrosive acid",
                "Violent, explosive exothermic boiling if water is added to concentrated acid",
                "Severe 3rd-degree charring burns to all biological tissues"
            ),
            requiredPpe = listOf("Heavy Duty PVC/Teflon Acid Suit", "Neoprene Gauntlet Gloves", "Full Face Shield + Goggles", "Acid Gas Respirator"),
            storage = "Store in dedicated acid storage with acid-resistant epoxy bunds. Always keep separated from organics, bases, and water sources.",
            incompatibilities = listOf("Water (when added improperly)", "Bases (NaOH, KOH)", "Organic Materials", "Nitric Acid", "Chlorates"),
            firstAid = "Brush dry chemical off skin before flushing with large volume of water for 20 minutes. Immediate medical emergency response mandatory.",
            spillResponse = "Dike with dry sand or acid-compatible absorbent. Neutralize very slowly with soda ash. Avoid adding plain water directly.",
            fireResponse = "Use dry chemical or CO2. Avoid spraying direct water jets into open acid containers.",
            sdsNote = "Always verify with the manufacturer-specific Safety Data Sheet (SDS) on site."
        ),
        ChemicalItem(
            id = "chem_acetone",
            name = "Acetone (Industrial Solvent)",
            formula = "C3H6O",
            cas = "67-64-1",
            ghsPictograms = listOf("Flammable Liquid Category 2", "Eye Irritation", "Target Organ Toxicity (Dizziness)"),
            hazards = listOf(
                "Highly flammable liquid and vapor (Flash point -20°C / -4°F)",
                "Vapors form explosive mixtures with air and travel to distant ignition sources",
                "Causes severe eye irritation and drowsiness/dizziness (CNS depression)"
            ),
            requiredPpe = listOf("Safety Glasses with Side Shields", "Butyl Rubber Gloves (Standard Nitrile degrades quickly)", "Flame-retardant Cotton Clothing", "Organic Vapor Respirator"),
            storage = "Store in certified flammable liquids safety cabinet with grounded drums and flame arrestors. Keep away from heat, sparks, and open flame.",
            incompatibilities = listOf("Concentrated Nitric Acid (Explosion Risk)", "Sulfuric Acid", "Strong Oxidizers", "Hydrogen Peroxide"),
            firstAid = "Eyes: Flush with water for 15 minutes. Inhalation: Move to fresh air. Skin: Wash with soap and water.",
            spillResponse = "Eliminate all ignition sources. Use non-sparking explosion-proof tools. Absorb with non-combustible activated carbon or clay.",
            fireResponse = "Use Alcohol-Resistant Foam, Dry Chemical, or CO2. Water spray may be ineffective due to low flashpoint.",
            sdsNote = "Always verify with the manufacturer-specific Safety Data Sheet (SDS) on site."
        )
    )

    val chemicalCompatibilities: List<ChemicalCompatibilityResult> = listOf(
        ChemicalCompatibilityResult(
            chemicalA = "Hydrochloric Acid (HCl)",
            chemicalB = "Sodium Hypochlorite (Bleach)",
            status = "INCOMPATIBLE - DANGER",
            severityLevel = "Critical",
            rationale = "Mixing strong acids with hypochlorite salts immediately releases dense, lethal Chlorine Gas (Cl2).",
            reactionHazard = "Acute pulmonary edema, chemical asphyxiation, toxic gas release.",
            requiredSeparation = "Store in physically separated containment bunds with separate ventilation."
        ),
        ChemicalCompatibilityResult(
            chemicalA = "Hydrochloric Acid (HCl)",
            chemicalB = "Sodium Hydroxide (NaOH)",
            status = "INCOMPATIBLE - DANGER",
            severityLevel = "Critical",
            rationale = "Strong acid and strong base neutralization is violently exothermic, causing boiling, steam explosion, and hot corrosive liquid ejection.",
            reactionHazard = "Violent boiling splatter and thermal-chemical burn hazard.",
            requiredSeparation = "Separate dedicated storage rooms or partitioned acid/base cabinets."
        ),
        ChemicalCompatibilityResult(
            chemicalA = "Sulfuric Acid (H2SO4)",
            chemicalB = "Organic Solvents (Acetone / Toluene)",
            status = "INCOMPATIBLE - DANGER",
            severityLevel = "Critical",
            rationale = "Concentrated sulfuric acid acts as a powerful oxidizer and dehydrating agent, reacting vigorously with organic solvents with ignition and explosion risk.",
            reactionHazard = "Spontaneous ignition, vessel rupture, and flash fire.",
            requiredSeparation = "Never store oxidizers or concentrated mineral acids in flammable solvent cabinets."
        ),
        ChemicalCompatibilityResult(
            chemicalA = "Sodium Hydroxide (NaOH)",
            chemicalB = "Water (Controlled Dilution)",
            status = "CAUTION / REVIEW",
            severityLevel = "Warning",
            rationale = "Dissolving solid NaOH in water generates intense heat (exothermic). Must be added slowly to cold water with mechanical stirring.",
            reactionHazard = "Localized boiling and corrosive splatter if added too quickly.",
            requiredSeparation = "Follow controlled engineering dilution protocols with PPE."
        )
    )

    val checklists: List<ChecklistTemplate> = listOf(
        ChecklistTemplate(
            id = "chk_daily_walk",
            title = "Daily Site Safety Walk",
            category = "General Field",
            iconName = "DirectionsWalk",
            items = listOf(
                "Are all pedestrian walkways and stairways clean, dry, and free of trip hazards?",
                "Are workers wearing required PPE for their specific work area (Helmet, Boots, Glasses, Hi-Vis)?",
                "Are emergency exits, fire extinguishers, and eyewash stations 100% unobstructed?",
                "Are electrical extension cords elevated or protected with heavy duty cable ramps?",
                "Are machine guards in place and firmly secured on all rotating equipment?",
                "Is housekeeping maintained with waste scrap deposited in designated bins?",
                "Are chemical containers properly labeled with GHS hazard diamonds and closed?",
                "Are active work permits (PTW/Hot Work) displayed and signed at the work locations?"
            )
        ),
        ChecklistTemplate(
            id = "chk_grinding",
            title = "Angle Grinder Pre-Use Inspection",
            category = "Power Tools",
            iconName = "Hardware",
            items = listOf(
                "Is the 180° protective wheel guard installed and positioned between the disc and the operator?",
                "Is the abrasive wheel marked with a Maximum RPM EQUAL TO OR GREATER THAN the tool spindle RPM?",
                "Is the grinding wheel free of cracks, chips, gouges, oil contamination, or expiration?",
                "Is the dead-man power switch functioning smoothly and returning to OFF when released?",
                "Is the electrical cable free of cuts, exposed insulation, or taped joins?",
                "Are the tool flanges and locknut correct for the disc thickness and tightened with proper spanner?",
                "Is the operator equipped with Double Eye Protection (Safety Glasses + Face Shield)?",
                "Is the work area cleared of flammable items within a 10m radius?"
            )
        ),
        ChecklistTemplate(
            id = "chk_scaffold",
            title = "Scaffolding Pre-Use Verification",
            category = "Working at Height",
            iconName = "Elevator",
            items = listOf(
                "Is there a valid, green scaffold inspection tag signed within the past 7 days displayed at access ladder?",
                "Are all sole boards, base plates, and adjustable screw jacks securely resting on solid level ground?",
                "Are top guardrails (0.95 - 1.15m), mid-rails (0.45m), and toe boards (150mm) fully installed on all open sides?",
                "Are all scaffold deck planks tightly butted without gaps exceeding 25mm or overlap tripping lips?",
                "Is ladder access secured at the top, extending at least 1.0m (3 rungs) above the landing platform?",
                "Are tie-in anchors and cross-braces securely clamped to the permanent structure?",
                "Is the scaffold free of overload, excess scrap, or debris?",
                "Is the ground area below barricaded with warning signs for dropped objects?"
            )
        ),
        ChecklistTemplate(
            id = "chk_loto",
            title = "LOTO Zero Energy Verification Checklist",
            category = "Isolation",
            iconName = "Lock",
            items = listOf(
                "Have all primary and secondary energy sources been identified on the equipment LOTO placard?",
                "Have all affected machine operators and area personnel been formally notified of the shutdown?",
                "Has the main electrical disconnect or circuit breaker been physically switched to OFF and locked?",
                "Has each individual worker placed their own personal red padlock and Danger Tag on the hasp?",
                "Have hydraulic, pneumatic, or steam block valves been closed, locked, and depressurized?",
                "Have mechanical counterweights been lowered or physically blocked with locking pins?",
                "Has the 'TRY' step been executed (attempting to start machine via local controls to prove zero movement)?",
                "Has a calibrated voltmeter or test-before-touch meter confirmed zero voltage on conductors?"
            )
        ),
        ChecklistTemplate(
            id = "chk_fire_extinguisher",
            title = "Portable Fire Extinguisher Inspection",
            category = "Fire Protection",
            iconName = "Flame",
            items = listOf(
                "Is the fire extinguisher mounted in its designated location with high visibility signage?",
                "Is access to the extinguisher completely unobstructed by boxes, equipment, or pallets?",
                "Is the pressure gauge needle firmly in the operable green zone?",
                "Is the safety pull-pin in place with an unbroken plastic tamper seal intact?",
                "Is the discharge nozzle and hose clean, flexible, and free of cracks, clogs, or dry rot?",
                "Is the cylinder body free of significant dents, severe corrosion, or mechanical damage?",
                "Is the annual inspection tag signed and dated within the required annual interval?",
                "Is the operating instruction label on the cylinder face legible and facing outward?"
            )
        )
    )

    val safetySigns: List<SafetySignItem> = listOf(
        SafetySignItem(
            id = "sign_mand_glasses",
            name = "Eye Protection Mandatory",
            category = "Mandatory Action",
            colorCode = "Blue (Circle)",
            meaning = "Workers must wear approved safety glasses or goggles before entering this zone.",
            whereUsed = "Machine shops, chemical yards, grinding bays, fabrication areas.",
            workerAction = "Put on certified safety spectacles with side shields before crossing the blue boundary line."
        ),
        SafetySignItem(
            id = "sign_mand_harness",
            name = "Safety Harness Mandatory",
            category = "Mandatory Action",
            colorCode = "Blue (Circle)",
            meaning = "Fall arrest full body harness must be worn and connected to certified anchor points.",
            whereUsed = "Elevated platforms, scaffolding, telecom towers, roof access hatches.",
            workerAction = "Inspect harness, put on with snug leg straps, and maintain 100% tie-off."
        ),
        SafetySignItem(
            id = "sign_warn_elec",
            name = "Danger: High Voltage",
            category = "Warning",
            colorCode = "Yellow / Amber (Triangle)",
            meaning = "Warns of lethal high electrical potential capable of arc flash and shock.",
            whereUsed = "Substations, transformer enclosures, motor control centers (MCC).",
            workerAction = "Do not open panels without electrical authorization, arc flash PPE, and LOTO permit."
        ),
        SafetySignItem(
            id = "sign_prohib_smoke",
            name = "No Smoking / No Naked Flames",
            category = "Prohibition",
            colorCode = "Red (Circle with Slash)",
            meaning = "Smoking, matches, lighters, and open flame sparks are strictly forbidden.",
            whereUsed = "Tank farms, solvent stores, battery charging rooms, gas cylinder bays.",
            workerAction = "Extinguish all smoking materials; do not introduce cell phones or spark sources."
        ),
        SafetySignItem(
            id = "sign_emerg_eyewash",
            name = "Emergency Eyewash & Safety Shower",
            category = "Emergency / First Aid",
            colorCode = "Green (Square / Rectangle)",
            meaning = "Indicates location of emergency decontamination water washing station.",
            whereUsed = "Battery rooms, chemical loading racks, laboratory benches.",
            workerAction = "Keep this area clear of all obstructions at all times. Flush eyes for 15-20 min in emergencies."
        )
    )
}

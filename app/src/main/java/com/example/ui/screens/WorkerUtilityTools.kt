package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.SafetyAmber
import com.example.ui.theme.SafetyBlue
import com.example.ui.theme.SafetyGreen
import com.example.ui.theme.SafetyOrange
import com.example.ui.theme.SafetyRed
import com.example.ui.viewmodel.SafetyViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType

// -------------------------------------------------------------------------
// 1. Worker Salary & Wage Calculator
// -------------------------------------------------------------------------
@Composable
fun WorkerSalaryCalculatorTool(
    viewModel: SafetyViewModel,
    isHindi: Boolean
) {
    val monthYear by viewModel.salMonth.collectAsState()
    val basic by viewModel.salBasic.collectAsState()
    val da by viewModel.salDa.collectAsState()
    val hra by viewModel.salHra.collectAsState()
    val allowances by viewModel.salAllowances.collectAsState()
    val workingDays by viewModel.salWorkingDays.collectAsState()
    val presentDays by viewModel.salPresentDays.collectAsState()
    val leaveDays by viewModel.salLeaveDays.collectAsState()
    val otHours by viewModel.salOtHours.collectAsState()
    val otRate by viewModel.salOtRate.collectAsState()
    val pf by viewModel.salPfDeduction.collectAsState()
    val esi by viewModel.salEsiDeduction.collectAsState()
    val otherDed by viewModel.salOtherDeduction.collectAsState()

    val savedCalcs by viewModel.savedSalaryCalculations.collectAsState()
    val calcMap = viewModel.calculateSalaryResults()
    val netSalary = calcMap["net"] ?: 0.0
    val grossSalary = calcMap["gross"] ?: 0.0
    val otEarnings = calcMap["otEarnings"] ?: 0.0
    val totalDeductions = calcMap["totalDeductions"] ?: 0.0

    var showSavedMessage by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 14.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Calculator Input Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = if (isHindi) "श्रमिक वेतन व दैनिक मजदूरी कैलकुलेटर" else "WORKER SALARY & WAGE CALCULATOR",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = SafetyOrange
                            )
                            Text(
                                text = if (isHindi) "मासिक व दैनिक मजदूरी, भत्ते व शुद्ध वेतन गणना" else "Calculate Gross Pay, Allowances & Net Take-Home",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.Payments,
                            contentDescription = null,
                            tint = SafetyGreen,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedTextField(
                        value = monthYear,
                        onValueChange = { viewModel.setSalMonth(it) },
                        label = { Text(if (isHindi) "माह व वर्ष (Month & Year)" else "Month & Year") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("salary_month_input"),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(
                            value = basic,
                            onValueChange = { viewModel.setSalBasic(it) },
                            label = { Text(if (isHindi) "मूल वेतन Basic (₹)" else "Basic Pay (₹)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("salary_basic_input"),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = da,
                            onValueChange = { viewModel.setSalDa(it) },
                            label = { Text(if (isHindi) "महंगाई भत्ता DA (₹)" else "DA Allowance (₹)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(
                            value = hra,
                            onValueChange = { viewModel.setSalHra(it) },
                            label = { Text(if (isHindi) "मकान किराया HRA (₹)" else "HRA (₹)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = allowances,
                            onValueChange = { viewModel.setSalAllowances(it) },
                            label = { Text(if (isHindi) "साइट / अन्य भत्ते (₹)" else "Other Allowances (₹)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(
                            value = workingDays,
                            onValueChange = { viewModel.setSalWorkingDays(it) },
                            label = { Text(if (isHindi) "कुल कार्य दिवस" else "Month Total Days") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = presentDays,
                            onValueChange = { viewModel.setSalPresentDays(it) },
                            label = { Text(if (isHindi) "उपस्थित दिन" else "Days Present") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(
                            value = otHours,
                            onValueChange = { viewModel.setSalOtHours(it) },
                            label = { Text(if (isHindi) "कुल ओटी घंटे (OT Hrs)" else "Total OT Hours") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = otRate,
                            onValueChange = { viewModel.setSalOtRate(it) },
                            label = { Text(if (isHindi) "ओटी दर / घंटा (₹)" else "OT Rate/Hour (₹)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(
                            value = pf,
                            onValueChange = { viewModel.setSalPfDeduction(it) },
                            label = { Text(if (isHindi) "पीएफ कटौती (PF ₹)" else "PF Deduction (₹)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = esi,
                            onValueChange = { viewModel.setSalEsiDeduction(it) },
                            label = { Text(if (isHindi) "ईएसआई कटौती (ESI ₹)" else "ESI Deduction (₹)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }
                }
            }
        }

        // Live Calculation Summary Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = SafetyGreen.copy(alpha = 0.12f)),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, SafetyGreen.copy(alpha = 0.6f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = if (isHindi) "शुद्ध देय वेतन (NET TAKE HOME SALARY)" else "NET TAKE HOME SALARY",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SafetyGreen
                    )
                    Text(
                        text = String.format(Locale.getDefault(), "₹ %,.2f", netSalary),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(text = if (isHindi) "सकल वेतन (Gross)" else "Gross Earnings", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(text = String.format(Locale.getDefault(), "₹ %,.2f", grossSalary), fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        }
                        Column {
                            Text(text = if (isHindi) "ओटी कमाई (OT Pay)" else "Overtime Pay", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(text = String.format(Locale.getDefault(), "₹ %,.2f", otEarnings), fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SafetyOrange)
                        }
                        Column {
                            Text(text = if (isHindi) "कुल कटौती (Deductions)" else "Total Deductions", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(text = String.format(Locale.getDefault(), "-₹ %,.2f", totalDeductions), fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SafetyRed)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = {
                            viewModel.saveCurrentSalaryCalculation()
                            showSavedMessage = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("save_salary_calc_btn"),
                        colors = ButtonDefaults.buttonColors(containerColor = SafetyGreen),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Save, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (showSavedMessage) (if (isHindi) "✓ वेतन गणना सहेजी गई" else "✓ Salary Record Saved") else (if (isHindi) "वेतन रिकॉर्ड सहेजें (Save)" else "Save Salary Calculation"),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Saved Salary Records History
        if (savedCalcs.isNotEmpty()) {
            item {
                Text(
                    text = "${if (isHindi) "सहेजे गए वेतन रिकॉर्ड" else "Saved Salary Calculations"} (${savedCalcs.size})",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            items(savedCalcs) { record ->
                val dateStr = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(record.createdAt))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = String.format(Locale.getDefault(), "₹ %,.2f", record.netSalary),
                                fontWeight = FontWeight.Black,
                                fontSize = 15.sp,
                                color = SafetyGreen
                            )
                            Text(
                                text = "${record.monthYear} • ${if (isHindi) "मूल:" else "Basic:"} ₹${record.basic.toInt()} • ${record.presentDays} ${if (isHindi) "दिन" else "days"} • OT: ${record.otHours}h (₹${record.otEarnings.toInt()})",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = dateStr,
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                            )
                        }

                        IconButton(onClick = { viewModel.deleteSalaryCalculation(record) }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = SafetyRed, modifier = Modifier.size(18.dp))
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------------------
// 2. Worker Overtime (OT) Calculator
// -------------------------------------------------------------------------
@Composable
fun WorkerOvertimeCalculatorTool(
    viewModel: SafetyViewModel,
    isHindi: Boolean
) {
    val title by viewModel.otTitle.collectAsState()
    val shiftStart by viewModel.otShiftStart.collectAsState()
    val shiftEnd by viewModel.otShiftEnd.collectAsState()
    val breakMins by viewModel.otBreakMins.collectAsState()
    val normalHours by viewModel.otNormalShiftHours.collectAsState()
    val ratePerHour by viewModel.otRatePerHour.collectAsState()
    val savedOtCalcs by viewModel.savedOtCalculations.collectAsState()

    val otMap = viewModel.calculateOtResults()
    val totalWorkedHours = otMap["totalWorkedHours"] ?: 0.0
    val actualOtHours = otMap["otHours"] ?: 0.0
    val totalOtPay = otMap["otAmount"] ?: 0.0

    var showSavedMessage by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 14.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = if (isHindi) "ओवरटाइम (OT) शिफ्ट कैलकुलेटर" else "FACTORY ACT OVERTIME CALCULATOR",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = SafetyOrange
                            )
                            Text(
                                text = if (isHindi) "शिफ्ट समय, ब्रेक व अतिरिक्त कार्य दर गणना" else "Calculate Shift OT Hours & Extra Earnings",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = null,
                            tint = SafetyOrange,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedTextField(
                        value = title,
                        onValueChange = { viewModel.setOtTitle(it) },
                        label = { Text(if (isHindi) "कार्य / शिफ्ट विवरण (Shift Title)" else "Shift / Job Title") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("ot_title_input"),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(
                            value = shiftStart,
                            onValueChange = { viewModel.setOtShiftStart(it) },
                            label = { Text(if (isHindi) "शुरू समय (HH:MM)" else "Start (HH:MM)") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("ot_start_input"),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = shiftEnd,
                            onValueChange = { viewModel.setOtShiftEnd(it) },
                            label = { Text(if (isHindi) "समाप्त समय (HH:MM)" else "End (HH:MM)") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("ot_end_input"),
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(
                            value = breakMins,
                            onValueChange = { viewModel.setOtBreakMins(it) },
                            label = { Text(if (isHindi) "ब्रेक समय (Min)" else "Break (Mins)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = normalHours,
                            onValueChange = { viewModel.setOtNormalShiftHours(it) },
                            label = { Text(if (isHindi) "सामान्य शिफ्ट (Hrs)" else "Normal Shift (Hrs)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = ratePerHour,
                        onValueChange = { viewModel.setOtRatePerHour(it) },
                        label = { Text(if (isHindi) "ओटी दर / घंटा (₹/hr)" else "OT Rate Per Hour (₹/hr)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            }
        }

        // Live Overtime Result
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = SafetyOrange.copy(alpha = 0.12f)),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, SafetyOrange.copy(alpha = 0.6f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = if (isHindi) "कुल अतिरिक्त ओटी कमाई (TOTAL OT EARNINGS)" else "TOTAL OVERTIME EARNINGS",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SafetyOrange
                    )
                    Text(
                        text = String.format(Locale.getDefault(), "₹ %,.2f", totalOtPay),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${if (isHindi) "कुल कार्य समय:" else "Total Time:"} ${String.format(Locale.getDefault(), "%.1f", totalWorkedHours)} hrs • ${if (isHindi) "अतिरिक्त ओटी घंटे:" else "OT Hours:"} ${String.format(Locale.getDefault(), "%.1f", actualOtHours)} hrs @ ₹$ratePerHour/hr",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(14.dp))
                    Button(
                        onClick = {
                            viewModel.saveCurrentOtCalculation()
                            showSavedMessage = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("save_ot_calc_btn"),
                        colors = ButtonDefaults.buttonColors(containerColor = SafetyOrange),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Save, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (showSavedMessage) (if (isHindi) "✓ ओटी रिकॉर्ड सहेजा गया" else "✓ OT Record Saved") else (if (isHindi) "ओटी रिकॉर्ड सहेजें (Save)" else "Save OT Record"),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Saved OT History
        if (savedOtCalcs.isNotEmpty()) {
            item {
                Text(
                    text = "${if (isHindi) "सहेजे गए ओटी रिकॉर्ड" else "Saved Overtime Records"} (${savedOtCalcs.size})",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            items(savedOtCalcs) { record ->
                val dateStr = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(record.createdAt))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = String.format(Locale.getDefault(), "₹ %,.2f", record.otAmount),
                                fontWeight = FontWeight.Black,
                                fontSize = 15.sp,
                                color = SafetyOrange
                            )
                            Text(
                                text = "${record.title} (${record.shiftStart} - ${record.shiftEnd}) • OT: ${record.otHours}h @ ₹${record.otRatePerHour}/hr",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = dateStr,
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                            )
                        }

                        IconButton(onClick = { viewModel.deleteOtCalculation(record) }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = SafetyRed, modifier = Modifier.size(18.dp))
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------------------
// 3. Worker Monthly Attendance & Shift Summary
// -------------------------------------------------------------------------
@Composable
fun WorkerAttendanceLogTool(
    viewModel: SafetyViewModel,
    isHindi: Boolean
) {
    val monthYear by viewModel.attMonthYear.collectAsState()
    val totalDays by viewModel.attTotalDays.collectAsState()
    val presentDays by viewModel.attPresent.collectAsState()
    val absentDays by viewModel.attAbsent.collectAsState()
    val halfDays by viewModel.attHalfDays.collectAsState()
    val leaves by viewModel.attLeaves.collectAsState()
    val weeklyOff by viewModel.attWeeklyOff.collectAsState()
    val holidays by viewModel.attHolidays.collectAsState()
    val totalOtHours by viewModel.attTotalOtHours.collectAsState()
    val savedLogs by viewModel.savedAttendanceRecords.collectAsState()

    val payableDays = viewModel.calculateAttendancePayableDays()
    var showSavedMessage by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 14.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Attendance Form Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = if (isHindi) "मासिक हाजिरी व शिफ्ट डायरी" else "MONTHLY ATTENDANCE & SHIFT LOG",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = SafetyOrange
                            )
                            Text(
                                text = if (isHindi) "उपस्थिति, छुट्टियां, साप्ताहिक अवकाश व देय दिन" else "Log Presence, Leaves, Weekly Offs & Payable Days",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = null,
                            tint = SafetyBlue,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedTextField(
                        value = monthYear,
                        onValueChange = { viewModel.setAttMonthYear(it) },
                        label = { Text(if (isHindi) "माह व वर्ष (Month & Year)" else "Month & Year") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("attendance_month_input"),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(
                            value = totalDays,
                            onValueChange = { viewModel.setAttTotalDays(it) },
                            label = { Text(if (isHindi) "माह कुल दिन" else "Month Days") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = presentDays,
                            onValueChange = { viewModel.setAttPresent(it) },
                            label = { Text(if (isHindi) "उपस्थित दिन" else "Present Days") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(
                            value = absentDays,
                            onValueChange = { viewModel.setAttAbsent(it) },
                            label = { Text(if (isHindi) "अनुपस्थित (Absent)" else "Absent Days") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = halfDays,
                            onValueChange = { viewModel.setAttHalfDays(it) },
                            label = { Text(if (isHindi) "आधा दिन (Half Day)" else "Half Days") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(
                            value = leaves,
                            onValueChange = { viewModel.setAttLeaves(it) },
                            label = { Text(if (isHindi) "वेतनयुक्त छुट्टी (PL/CL)" else "Paid Leaves") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = weeklyOff,
                            onValueChange = { viewModel.setAttWeeklyOff(it) },
                            label = { Text(if (isHindi) "साप्ताहिक छुट्टी (Off)" else "Weekly Off") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(
                            value = holidays,
                            onValueChange = { viewModel.setAttHolidays(it) },
                            label = { Text(if (isHindi) "त्योहार अवकाश (Holiday)" else "Festival Holidays") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = totalOtHours,
                            onValueChange = { viewModel.setAttTotalOtHours(it) },
                            label = { Text(if (isHindi) "कुल ओटी घंटे" else "Total OT Hours") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = {
                            viewModel.saveCurrentAttendanceRecord()
                            showSavedMessage = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("save_attendance_btn"),
                        colors = ButtonDefaults.buttonColors(containerColor = SafetyBlue),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Save, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (showSavedMessage) (if (isHindi) "✓ हाजिरी सहेजी गई" else "✓ Attendance Logged") else (if (isHindi) "हाजिरी दर्ज करें (Log Attendance)" else "Log Attendance Summary"),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Summary Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "$payableDays", fontSize = 18.sp, fontWeight = FontWeight.Black, color = SafetyGreen)
                        Text(text = if (isHindi) "वेतन योग्य दिन" else "Payable Days", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = presentDays, fontSize = 18.sp, fontWeight = FontWeight.Black, color = SafetyBlue)
                        Text(text = if (isHindi) "उपस्थित दिन" else "Days Present", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "${totalOtHours}h", fontSize = 18.sp, fontWeight = FontWeight.Black, color = SafetyOrange)
                        Text(text = if (isHindi) "कुल ओटी" else "Total OT", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }

        // Attendance History List
        if (savedLogs.isNotEmpty()) {
            item {
                Text(
                    text = "${if (isHindi) "हाजिरी रिकॉर्ड इतिहास" else "Attendance Log History"} (${savedLogs.size})",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            items(savedLogs) { log ->
                val dateStr = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(log.createdAt))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = SafetyGreen.copy(alpha = 0.2f)
                                ) {
                                    Text(
                                        text = "${log.payableDays} PAYABLE DAYS",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SafetyGreen,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = log.monthYear,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }

                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Present: ${log.presentDays}d • Off: ${log.weeklyOffDays}d • Leave: ${log.leaveDays}d • OT: ${log.totalOtHours}h",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = dateStr,
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                            )
                        }

                        IconButton(onClick = { viewModel.deleteAttendanceRecord(log) }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = SafetyRed, modifier = Modifier.size(18.dp))
                        }
                    }
                }
            }
        }
    }
}

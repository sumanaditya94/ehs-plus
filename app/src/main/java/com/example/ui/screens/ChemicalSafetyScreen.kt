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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.data.model.ChemicalItem
import com.example.ui.theme.SafetyAmber
import com.example.ui.theme.SafetyBlue
import com.example.ui.theme.SafetyGreen
import com.example.ui.theme.SafetyOrange
import com.example.ui.theme.SafetyRed
import com.example.ui.viewmodel.SafetyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChemicalSafetyScreen(
    viewModel: SafetyViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Chemical Database", "Compatibility Matrix")
    var selectedChemicalDetail by remember { mutableStateOf<ChemicalItem?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onBackground)
            }
            Column {
                Text(
                    text = "CHEMICAL SAFETY & SDS",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF9C27B0)
                )
                Text(
                    text = "Hazard Data & Chemical Segregation",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = Color(0xFF9C27B0)
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            text = title,
                            fontSize = 12.sp,
                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium,
                            color = if (selectedTab == index) Color(0xFF9C27B0) else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                )
            }
        }

        when (selectedTab) {
            0 -> ChemicalDatabaseTab(chemicals = viewModel.allChemicals, onSelect = { selectedChemicalDetail = it })
            1 -> ChemicalCompatibilityTab(viewModel)
        }
    }

    selectedChemicalDetail?.let { chem ->
        ChemicalDetailSheet(chem = chem, onDismiss = { selectedChemicalDetail = null })
    }
}

@Composable
fun ChemicalDatabaseTab(
    chemicals: List<ChemicalItem>,
    onSelect: (ChemicalItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 14.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(chemicals) { chem ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelect(chem) }
                    .testTag("chemical_card_${chem.id}"),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = chem.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFF9C27B0).copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = chem.formula,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF9C27B0),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "CAS: ${chem.cas}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

                    Spacer(modifier = Modifier.height(6.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        chem.ghsPictograms.take(3).forEach { picto ->
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = SafetyRed.copy(alpha = 0.12f)
                            ) {
                                Text(
                                    text = picto,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SafetyRed,
                                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChemicalCompatibilityTab(viewModel: SafetyViewModel) {
    val chemA by viewModel.selectedChemA.collectAsState()
    val chemB by viewModel.selectedChemB.collectAsState()
    val result by viewModel.compatibilityResult.collectAsState()

    val chemicalsList = listOf(
        "Hydrochloric Acid (HCl)",
        "Sodium Hydroxide (NaOH)",
        "Sodium Hypochlorite (Bleach)",
        "Sulfuric Acid (H2SO4)",
        "Organic Solvents (Acetone / Toluene)",
        "Water (Controlled Dilution)"
    )

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
                    Text(
                        text = "CHEMICAL SEGREGATION MATRIX",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF9C27B0)
                    )
                    Text(
                        text = "Check Pairwise Storage Compatibility",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "Select Chemical A:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    chemicalsList.forEach { c ->
                        FilterChip(
                            selected = chemA == c,
                            onClick = { viewModel.setSelectedChemA(c) },
                            label = { Text(c, fontSize = 11.sp) },
                            modifier = Modifier.padding(vertical = 1.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "Select Chemical B:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    chemicalsList.forEach { c ->
                        FilterChip(
                            selected = chemB == c,
                            onClick = { viewModel.setSelectedChemB(c) },
                            label = { Text(c, fontSize = 11.sp) },
                            modifier = Modifier.padding(vertical = 1.dp)
                        )
                    }
                }
            }
        }

        result?.let { comp ->
            item {
                val isDanger = comp.status.contains("INCOMPATIBLE")
                val compColor = if (isDanger) SafetyRed else SafetyAmber
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = compColor.copy(alpha = 0.15f)),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, compColor)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = comp.status,
                            fontWeight = FontWeight.Black,
                            fontSize = 16.sp,
                            color = compColor
                        )

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = comp.rationale, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Reaction Hazard: ${comp.reactionHazard}", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = SafetyRed)

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "Required Segregation: ${comp.requiredSeparation}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChemicalDetailSheet(chem: ChemicalItem, onDismiss: () -> Unit) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(top = 10.dp, bottom = 48.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "CHEMICAL SAFETY DATA", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF9C27B0))
                    IconButton(onClick = onDismiss) { Icon(imageVector = Icons.Default.Close, contentDescription = "Close") }
                }
                Text(text = chem.name, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.onSurface)
                Text(text = "Formula: ${chem.formula} • CAS: ${chem.cas}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            item {
                SectionCard(title = "Primary Hazards", accentColor = SafetyRed) {
                    chem.hazards.forEach { h -> BulletItem(text = h, bulletColor = SafetyRed) }
                }
            }

            item {
                SectionCard(title = "Required PPE", accentColor = SafetyBlue) {
                    chem.requiredPpe.forEach { p -> BulletItem(text = p, bulletColor = SafetyBlue) }
                }
            }

            item {
                SectionCard(title = "Storage & Incompatibilities", accentColor = SafetyAmber) {
                    Text(text = "STORAGE:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = SafetyAmber)
                    Text(text = chem.storage, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "INCOMPATIBLE WITH:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = SafetyRed)
                    chem.incompatibilities.forEach { inc -> BulletItem(text = inc, bulletColor = SafetyRed) }
                }
            }

            item {
                SectionCard(title = "First Aid & Emergency Spill Response", accentColor = SafetyGreen) {
                    Text(text = "FIRST AID:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = SafetyGreen)
                    Text(text = chem.firstAid, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "SPILL RESPONSE:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = SafetyOrange)
                    Text(text = chem.spillResponse, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface)
                }
            }
        }
    }
}

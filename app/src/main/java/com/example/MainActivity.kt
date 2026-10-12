package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.SafetyCheck
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.data.language.AppLanguage
import com.example.data.local.AppDatabase
import com.example.data.repository.SafetyRepository
import com.example.ui.screens.AiSafetyScreen
import com.example.ui.screens.ChecklistsScreen
import com.example.ui.screens.ChemicalSafetyScreen
import com.example.ui.screens.FieldReportsScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LearnScreen
import com.example.ui.screens.PpeScreen
import com.example.ui.screens.SafetyLimitsScreen
import com.example.ui.screens.ToolsScreen
import com.example.ui.theme.SafetyIndustrialTheme
import com.example.ui.theme.SafetyOrange
import com.example.ui.viewmodel.SafetyViewModel
import com.example.ui.viewmodel.SafetyViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(applicationContext)
        val repository = SafetyRepository(safetyDao = database.safetyDao())
        val factory = SafetyViewModelFactory(repository)

        setContent {
            SafetyIndustrialTheme {
                val safetyViewModel: SafetyViewModel = viewModel(factory = factory)
                MainAppContent(safetyViewModel)
            }
        }
    }
}

sealed class Screen(val route: String, val titleKey: String, val icon: ImageVector) {
    object Home : Screen("home", "nav_home", Icons.Default.Home)
    object Learn : Screen("learn", "nav_learn", Icons.Default.MenuBook)
    object Tools : Screen("tools", "nav_tools", Icons.Default.Build)
    object Ppe : Screen("ppe", "nav_ppe", Icons.Default.SafetyCheck)
    object Ai : Screen("ai", "nav_ai", Icons.Default.AutoAwesome)

    // Sub-screens
    object Checklists : Screen("checklists", "tool_checklists", Icons.Default.Build)
    object ChemicalSafety : Screen("chemical_safety", "tool_chemicals", Icons.Default.Build)
    object SafetyLimits : Screen("safety_limits", "tool_limits", Icons.Default.Build)
    object FieldReports : Screen("field_reports", "tool_reports", Icons.Default.Build)
}

@Composable
fun MainAppContent(viewModel: SafetyViewModel) {
    val navController = rememberNavController()
    val language by viewModel.currentLanguage.collectAsState()

    val bottomNavItems = listOf(
        Screen.Home,
        Screen.Learn,
        Screen.Tools,
        Screen.Ppe,
        Screen.Ai
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (bottomNavItems.any { it.route == currentRoute }) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 8.dp,
                    modifier = Modifier.testTag("bottom_nav_bar")
                ) {
                    bottomNavItems.forEach { screen ->
                        val selected = currentRoute == screen.route
                        val labelText = viewModel.getString(screen.titleKey)
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = screen.icon,
                                    contentDescription = labelText
                                )
                            },
                            label = {
                                Text(
                                    text = labelText,
                                    fontSize = 11.sp,
                                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            selected = selected,
                            onClick = {
                                if (currentRoute != screen.route) {
                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = SafetyOrange,
                                selectedTextColor = SafetyOrange,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                indicatorColor = SafetyOrange.copy(alpha = 0.15f)
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    viewModel = viewModel,
                    onNavigateToLearn = {
                        navController.navigate(Screen.Learn.route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                        }
                    },
                    onNavigateToTools = {
                        navController.navigate(Screen.Tools.route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                        }
                    },
                    onNavigateToPpe = {
                        navController.navigate(Screen.Ppe.route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                        }
                    },
                    onNavigateToAi = {
                        navController.navigate(Screen.Ai.route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                        }
                    },
                    onNavigateToChecklists = {
                        navController.navigate(Screen.Checklists.route)
                    },
                    onNavigateToChemicals = {
                        navController.navigate(Screen.ChemicalSafety.route)
                    },
                    onNavigateToSafetyLimits = {
                        navController.navigate(Screen.SafetyLimits.route)
                    },
                    onNavigateToLoto = {
                        navController.navigate(Screen.Tools.route)
                    },
                    onNavigateToRiskCalculator = {
                        navController.navigate(Screen.Tools.route)
                    },
                    onNavigateToAiScanner = {
                        navController.navigate(Screen.Ai.route)
                    },
                    onNavigateToTopicDetail = {
                        navController.navigate(Screen.Learn.route)
                    }
                )
            }

            composable(Screen.Learn.route) {
                LearnScreen(viewModel = viewModel)
            }

            composable(Screen.Tools.route) {
                ToolsScreen(viewModel = viewModel)
            }

            composable(Screen.Ppe.route) {
                PpeScreen(viewModel = viewModel)
            }

            composable(Screen.Ai.route) {
                AiSafetyScreen(viewModel = viewModel)
            }

            composable(Screen.Checklists.route) {
                ChecklistsScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(Screen.ChemicalSafety.route) {
                ChemicalSafetyScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(Screen.SafetyLimits.route) {
                SafetyLimitsScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(Screen.FieldReports.route) {
                FieldReportsScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}

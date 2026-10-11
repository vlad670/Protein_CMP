package band.effective.education.crossplatform

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import band.effective.education.crossplatform.data.mockProteins
import band.effective.education.crossplatform.list.ProteinListState
import band.effective.education.crossplatform.ui.ProteinDetailScreen
import band.effective.education.crossplatform.ui.ProteinListScreen
import band.effective.education.crossplatform.ui.isDarkTheme
import band.effective.education.crossplatform.ui.locale.AppEnvironment

@Composable
fun App() {
    val backStack = remember { mutableStateListOf<Route>(Route.ProteinList) }

    AppEnvironment {
        MaterialTheme(
            colorScheme = if (isDarkTheme) darkColorScheme() else lightColorScheme()
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                NavDisplay(
                    backStack = backStack,
                    onBack = { if (backStack.size > 1) backStack.removeLastOrNull() },
                    transitionSpec = { slideInHorizontally(initialOffsetX = { it }) togetherWith slideOutHorizontally(targetOffsetX = { -it }) },
                    popTransitionSpec = { slideInHorizontally(initialOffsetX = { -it }) togetherWith slideOutHorizontally(targetOffsetX = { it }) },
                    entryProvider = entryProvider {
                        entry<Route.ProteinList> {
                            var searchQuery by remember { mutableStateOf("") }

                            val filteredItems = remember(searchQuery) {
                                if (searchQuery.isBlank()) {
                                    mockProteins
                                } else {
                                    mockProteins.filter { protein ->
                                        protein.proteinName.contains(searchQuery, ignoreCase = true) ||
                                        protein.primaryAccession.contains(searchQuery, ignoreCase = true) ||
                                        protein.organismName.contains(searchQuery, ignoreCase = true) ||
                                        (protein.geneName?.contains(searchQuery, ignoreCase = true) == true)
                                    }
                                }
                            }

                            val state = ProteinListState(
                                searchQuery = searchQuery,
                                items = filteredItems,
                                totalFound = filteredItems.size
                            )

                            ProteinListScreen(
                                state = state,
                                onSearchQueryChange = { searchQuery = it },
                                onProteinClick = { accession ->
                                    backStack.add(Route.ProteinDetails(accession))
                                }
                            )
                        }
                        entry<Route.ProteinDetails> { key ->
                            ProteinDetailScreen(
                                accession = key.accession,
                                onProteinClick = { backStack.add(Route.ProteinDetails(it)) },
                                onBackClick = { if (backStack.size > 1) backStack.removeLastOrNull() }
                            )
                        }
                    }
                )
            }
        }
    }
}
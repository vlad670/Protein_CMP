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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import band.effective.education.crossplatform.ui.isDarkTheme
import band.effective.education.crossplatform.ui.locale.AppEnvironment
import band.effective.education.crossplatform.ui.screens.detail.ProteinDetailScreen
import band.effective.education.crossplatform.ui.screens.detail.ProteinDetailViewModel
import band.effective.education.crossplatform.ui.screens.list.ProteinListScreen
import band.effective.education.crossplatform.ui.screens.list.ProteinListViewModel

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
                    transitionSpec = {
                        slideInHorizontally(initialOffsetX = { it }) togetherWith
                            slideOutHorizontally(targetOffsetX = { -it })
                    },
                    popTransitionSpec = {
                        slideInHorizontally(initialOffsetX = { -it }) togetherWith
                            slideOutHorizontally(targetOffsetX = { it })
                    },
                    entryProvider = entryProvider {
                        entry<Route.ProteinList> {
                            val viewModel = viewModel { ProteinListViewModel() }
                            val state by viewModel.state.collectAsStateWithLifecycle()

                            ProteinListScreen(
                                state = state,
                                onSearchQueryChange = viewModel::onSearchQueryChange,
                                onProteinClick = { accession ->
                                    backStack.add(Route.ProteinDetails(accession))
                                },
                                onRetry = viewModel::retry
                            )
                        }
                        entry<Route.ProteinDetails> { key ->
                            val viewModel = viewModel(key = key.accession) {
                                ProteinDetailViewModel(key.accession)
                            }
                            val state by viewModel.state.collectAsStateWithLifecycle()

                            ProteinDetailScreen(
                                state = state,
                                onProteinClick = { backStack.add(Route.ProteinDetails(it)) },
                                onBackClick = { if (backStack.size > 1) backStack.removeLastOrNull() },
                                onRetry = viewModel::retry
                            )
                        }
                    }
                )
            }
        }
    }
}


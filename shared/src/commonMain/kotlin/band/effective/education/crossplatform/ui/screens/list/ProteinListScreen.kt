package band.effective.education.crossplatform.ui.screens.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.education.crossplatform.domain.Protein
import band.effective.education.crossplatform.resources.Res
import band.effective.education.crossplatform.resources.error_loading
import band.effective.education.crossplatform.resources.found
import band.effective.education.crossplatform.resources.retry
import band.effective.education.crossplatform.ui.components.AppScaffold
import band.effective.education.crossplatform.ui.components.CardSurface
import band.effective.education.crossplatform.ui.components.SearchBar
import org.jetbrains.compose.resources.stringResource

@Composable
fun ProteinListScreen(
    state: ProteinListState,
    onSearchQueryChange: (String) -> Unit,
    onProteinClick: (String) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val currentQuery = when (state) {
        is ProteinListState.Loading -> state.searchQuery
        is ProteinListState.Success -> state.searchQuery
        is ProteinListState.Error -> state.searchQuery
    }

    AppScaffold(modifier = modifier) {
        Column(modifier = Modifier.fillMaxSize()) {
            SearchBar(
                query = currentQuery,
                onQueryChange = onSearchQueryChange,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            when (state) {
                is ProteinListState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is ProteinListState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = stringResource(Res.string.error_loading),
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Button(onClick = onRetry) {
                                Text(stringResource(Res.string.retry))
                            }
                        }
                    }
                }

                is ProteinListState.Success -> {
                    Text(
                        text = "${stringResource(Res.string.found)}: ${state.totalFound}",
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.bodySmall
                    )

                    LazyColumn(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(state.items, key = { it.primaryAccession }) { item ->
                            ProteinRow(
                                protein = item,
                                onClick = { onProteinClick(item.primaryAccession) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProteinRow(protein: Protein, onClick: () -> Unit) {
    CardSurface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = protein.primaryAccession,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = protein.proteinName, style = MaterialTheme.typography.titleLarge)
            Text(text = protein.organismName, style = MaterialTheme.typography.bodyMedium)
        }
    }
}


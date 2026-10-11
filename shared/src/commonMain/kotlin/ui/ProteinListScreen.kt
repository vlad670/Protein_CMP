package band.effective.education.crossplatform.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.education.crossplatform.data.Protein
import band.effective.education.crossplatform.list.ProteinListState
import band.effective.education.crossplatform.resources.Res
import band.effective.education.crossplatform.resources.found
import org.jetbrains.compose.resources.stringResource

@Composable
fun ProteinListScreen(
    state: ProteinListState,
    onSearchQueryChange: (String) -> Unit,
    onProteinClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    AppScaffold(modifier = modifier) {
        Column {
            SearchBar(
                query = state.searchQuery,
                onQueryChange = onSearchQueryChange,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            Text(
                text = "${stringResource(Res.string.found)}: ${state.totalFound}",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                style = MaterialTheme.typography.bodySmall
            )

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
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
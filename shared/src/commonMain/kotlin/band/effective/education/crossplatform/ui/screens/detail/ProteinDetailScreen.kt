package band.effective.education.crossplatform.ui.screens.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import band.effective.education.crossplatform.domain.Protein
import band.effective.education.crossplatform.resources.Res
import band.effective.education.crossplatform.resources.amino_acid_sequence
import band.effective.education.crossplatform.resources.back
import band.effective.education.crossplatform.resources.error_loading
import band.effective.education.crossplatform.resources.full_name
import band.effective.education.crossplatform.resources.gene
import band.effective.education.crossplatform.resources.not_find
import band.effective.education.crossplatform.resources.organism
import band.effective.education.crossplatform.resources.related_proteins
import band.effective.education.crossplatform.resources.retry
import band.effective.education.crossplatform.ui.components.AppScaffold
import org.jetbrains.compose.resources.stringResource

@Composable
fun ProteinDetailScreen(
    state: ProteinDetailState,
    onProteinClick: (String) -> Unit,
    onBackClick: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppScaffold(
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(Res.string.back)
                )
            }
        }
    ) {
        when (state) {
            is ProteinDetailState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is ProteinDetailState.Error -> {
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

            is ProteinDetailState.Success -> {
                ProteinDetailContent(
                    protein = state.protein,
                    onProteinClick = onProteinClick
                )
            }
        }
    }
}

@Composable
fun ProteinDetailContent(
    protein: Protein,
    onProteinClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = protein.entryName,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(Res.string.full_name),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(text = protein.proteinName, style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(Res.string.organism),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(text = protein.organismName, style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(Res.string.gene),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = protein.geneName ?: stringResource(Res.string.not_find),
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(Res.string.amino_acid_sequence),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = protein.sequence ?: stringResource(Res.string.not_find),
            style = MaterialTheme.typography.bodyMedium,
            fontFamily = FontFamily.Monospace
        )

        if (protein.related.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(Res.string.related_proteins),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
            protein.related.forEach { related ->
                TextButton(onClick = { onProteinClick(related.accession) }) {
                    Text(related.accession + (related.geneName?.let { " · $it" } ?: ""))
                }
            }
        }
    }
}


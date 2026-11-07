package band.effective.education.crossplatform.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import band.effective.education.crossplatform.data.mockProteins
import band.effective.education.crossplatform.resources.Res
import band.effective.education.crossplatform.resources.amino_acid_sequence
import band.effective.education.crossplatform.resources.back
import band.effective.education.crossplatform.resources.full_name
import band.effective.education.crossplatform.resources.gene
import band.effective.education.crossplatform.resources.not_find
import band.effective.education.crossplatform.resources.organism
import band.effective.education.crossplatform.resources.related_proteins
import org.jetbrains.compose.resources.stringResource
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon

@Composable
fun ProteinDetailScreen(
    accession: String,
    onProteinClick: (String) -> Unit,
    onBackClick: () -> Unit,
) {
    val protein = mockProteins.firstOrNull { it.primaryAccession == accession }

    AppScaffold(
        navigationIcon = {
        IconButton(onClick = onBackClick) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
        }
    }
    ) {
        if (protein == null) {
            Text(stringResource(Res.string.not_find), modifier = Modifier.padding(16.dp))
            return@AppScaffold
        }

        Column(
            modifier = Modifier
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

            Text(text = stringResource(Res.string.full_name), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
            Text(text = protein.proteinName, style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = stringResource(Res.string.organism), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
            Text(text = protein.organismName, style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = stringResource(Res.string.gene), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
            Text(text = protein.geneName ?: stringResource(Res.string.not_find), style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = stringResource(Res.string.amino_acid_sequence), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
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
}
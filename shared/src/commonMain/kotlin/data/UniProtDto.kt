package band.effective.education.crossplatform.data

import kotlinx.serialization.Serializable

@Serializable
data class UniProtSearchResponse(
    val results: List<UniProtEntryDto> = emptyList()
)
@Serializable
data class UniProtEntryDto(
    val primaryAccession: String,
    val uniProtkbId: String? = null,
    val proteinDescription: ProteinDescriptionDto? = null,
    val genes: List<GeneDto>? = null,
    val organism: OrganismDto? = null,
    val sequence: SequenceDto? = null,
)

@Serializable
data class ProteinDescriptionDto(val recommendedName: RecommendedNameDto? = null)

@Serializable
data class RecommendedNameDto(val fullName: ValueDto? = null)

@Serializable
data class ValueDto(val value: String)

@Serializable
data class GeneDto(val geneName: ValueDto? = null)

@Serializable
data class OrganismDto(val scientificName: String? = null, val commonName: String? = null)

@Serializable
data class SequenceDto(val value: String? = null, val length: Int? = null)


val comments: List<CommentDto>? = null

@Serializable
data class CommentDto(val commentType: String? = null, val interactions: List<InteractionDto>? = null)

@Serializable
data class InteractionDto(val interactantOne: InteractantDto? = null, val interactantTwo: InteractantDto? = null)

@Serializable
data class InteractantDto(val uniProtKBAccession: String? = null, val geneName: String? = null)

fun UniProtEntryDto.toProtein(): Protein = Protein(
    primaryAccession = primaryAccession,
    entryName = uniProtkbId ?: primaryAccession,
    proteinName = proteinDescription?.recommendedName?.fullName?.value ?: "Unknown",
    organismName = buildString {
        append(organism?.scientificName ?: "Unknown")
        organism?.commonName?.let { append(" ($it)") }
    },
    geneName = genes?.firstOrNull()?.geneName?.value,
    sequence = sequence?.value,
    related = relatedProteins()
)

private fun UniProtEntryDto.relatedProteins(): List<RelatedProtein> =
    comments.orEmpty()
        .filter { it.commentType == "INTERACTION" }
        .flatMap { it.interactions.orEmpty() }
        .mapNotNull { interaction ->
            listOfNotNull(interaction.interactantOne, interaction.interactantTwo)
                .mapNotNull { p ->
                    p.uniProtKBAccession?.substringBefore('-')  // отбрасываем изоформу P12345-2
                        ?.let { RelatedProtein(it, p.geneName) }
                }
                .firstOrNull { it.accession != primaryAccession }  // берём «второго» участника
        }
        .distinctBy { it.accession }
        .take(10)
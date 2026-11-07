package band.effective.education.crossplatform.data


import kotlinx.serialization.Serializable

@Serializable
data class Protein (
    val primaryAccession: String,
    val entryName: String,
    val proteinName: String,
    val organismName: String,
    val geneName: String? = null,
    val sequence: String? = null,
    val related: List<RelatedProtein> = emptyList()
)

@Serializable
data class RelatedProtein(val accession: String, val geneName: String? = null)
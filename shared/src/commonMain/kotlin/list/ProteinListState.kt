package band.effective.education.crossplatform.list

import band.effective.education.crossplatform.data.Protein


data class ProteinListState(
    val searchQuery: String = "",
    val items: List<Protein> = emptyList(),
    val totalFound: Int = 0
)
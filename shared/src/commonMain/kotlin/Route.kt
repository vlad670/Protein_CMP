package band.effective.education.crossplatform

sealed interface Route {
    data object ProteinList : Route
    data class ProteinDetails(val accession: String) : Route
}
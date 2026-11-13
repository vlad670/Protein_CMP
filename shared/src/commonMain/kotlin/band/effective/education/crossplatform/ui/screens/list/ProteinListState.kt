package band.effective.education.crossplatform.ui.screens.list

import band.effective.education.crossplatform.domain.Protein

sealed interface ProteinListState {
    data class Loading(val searchQuery: String = "") : ProteinListState
    data class Success(
        val searchQuery: String,
        val items: List<Protein>,
        val totalFound: Int
    ) : ProteinListState
    data class Error(val searchQuery: String = "", val message: String? = null) : ProteinListState
}


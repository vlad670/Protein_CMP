package band.effective.education.crossplatform.ui.screens.detail

import band.effective.education.crossplatform.domain.Protein

sealed interface ProteinDetailState {
    data object Loading : ProteinDetailState
    data class Success(val protein: Protein) : ProteinDetailState
    data class Error(val message: String? = null) : ProteinDetailState
}


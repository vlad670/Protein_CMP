package band.effective.education.crossplatform.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.education.crossplatform.data.ProteinRepositoryImpl
import band.effective.education.crossplatform.domain.ProteinRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProteinDetailViewModel(
    private val accession: String,
    private val repository: ProteinRepository = ProteinRepositoryImpl()
) : ViewModel() {

    private val _state = MutableStateFlow<ProteinDetailState>(ProteinDetailState.Loading)
    val state: StateFlow<ProteinDetailState> = _state.asStateFlow()

    init {
        loadProtein()
    }

    fun retry() {
        loadProtein()
    }

    private fun loadProtein() {
        viewModelScope.launch {
            _state.value = ProteinDetailState.Loading
            repository.getProtein(accession).fold(
                onSuccess = { protein ->
                    _state.value = ProteinDetailState.Success(protein)
                },
                onFailure = { throwable ->
                    if (throwable is CancellationException) {
                        throw throwable
                    }
                    _state.value = ProteinDetailState.Error(throwable.message)
                }
            )
        }
    }
}


package band.effective.education.crossplatform.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.education.crossplatform.data.ProteinRepositoryImpl
import band.effective.education.crossplatform.domain.ProteinRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProteinListViewModel(
    private val repository: ProteinRepository = ProteinRepositoryImpl()
) : ViewModel() {

    private val _state = MutableStateFlow<ProteinListState>(ProteinListState.Loading())
    val state: StateFlow<ProteinListState> = _state.asStateFlow()

    private var searchJob: Job? = null

    init {
        loadProteins("")
    }

    fun onSearchQueryChange(newQuery: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _state.value = ProteinListState.Loading(searchQuery = newQuery)
            delay(350)
            loadProteins(newQuery)
        }
    }

    fun retry() {
        val currentQuery = when (val s = _state.value) {
            is ProteinListState.Loading -> s.searchQuery
            is ProteinListState.Success -> s.searchQuery
            is ProteinListState.Error -> s.searchQuery
        }
        loadProteins(currentQuery)
    }

    private fun loadProteins(query: String) {
        viewModelScope.launch {
            _state.value = ProteinListState.Loading(searchQuery = query)
            repository.getProteins(query).fold(
                onSuccess = { (items, total) ->
                    _state.value = ProteinListState.Success(
                        searchQuery = query,
                        items = items,
                        totalFound = total
                    )
                },
                onFailure = { throwable ->
                    if (throwable is CancellationException) {
                        throw throwable
                    }
                    _state.value = ProteinListState.Error(
                        searchQuery = query,
                        message = throwable.message
                    )
                }
            )
        }
    }
}


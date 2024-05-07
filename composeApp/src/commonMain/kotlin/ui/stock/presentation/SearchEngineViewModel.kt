package ui.stock.presentation

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import core.data.Resource
import core.data.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ui.stock.domain.model.ItemEngine
import ui.stock.domain.repository.SearchEngineRepository

data class SearchEngineState(
    var status: Status? = null,
    var isLoading: Boolean? = null,
    var data: ItemEngine? = null
)

class SearchEngineViewModel(
    private val repository: SearchEngineRepository
): ScreenModel {

    private val _state = MutableStateFlow(SearchEngineState())
    val state: StateFlow<SearchEngineState> = _state.asStateFlow()

    fun onSearchClick(keyword: String) {
        screenModelScope.launch(Dispatchers.Default) {
            repository.fetchSearchEngine(keyword).collect { result ->
                when(result) {
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                status = result.status,
                                data = result.data,
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                status = result.status,
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Loading -> {
                        _state.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }
                }
            }
        }
    }

}
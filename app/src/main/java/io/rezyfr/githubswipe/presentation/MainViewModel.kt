package io.rezyfr.githubswipe.presentation

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.rezyfr.githubswipe.domain.model.UserItem
import io.rezyfr.githubswipe.domain.usecase.GithubSearchUseCase
import io.rezyfr.githubswipe.domain.usecase.GithubSearchUseCaseParam
import io.rezyfr.githubswipe.domain.utils.FetchData
import io.rezyfr.githubswipe.utils.Direction
import io.rezyfr.githubswipe.utils.SwipeableCardState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val githubSearchUseCase: GithubSearchUseCase
) : ViewModel() {

    companion object {
        const val perPage = 15
    }

    private val _uiState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState())
    val uiState = _uiState.asStateFlow()

    private val _effect: Channel<ViewEffect> = Channel()
    val effect = _effect.receiveAsFlow()

    val currentState: ViewState
        get() = uiState.value

    private var page = 1
    private var query = ""

    fun processEvent(event: ViewEvent) {
        when (event) {
            is ViewEvent.Search -> {
                query = event.query
                page = 1
                searchUsers(false)
            }
            is ViewEvent.LoadNextPage -> {
                searchUsers(true)
            }
            is ViewEvent.OnSwipeButtonClick -> {
                viewModelScope.launch {
                    val lastItem = event.snapshot.firstOrNull {
                        it.second.offset.value == Offset.Zero // Offset Zero -> Top of the stack
                    }?.second
                    lastItem?.let {
                        _effect.send(ViewEffect.SwipeCard(lastItem, event.direction))
                    }
                }
            }
            is ViewEvent.ShowEmptyState -> {
                viewModelScope.launch {
                    _uiState.emit(currentState.copy(showEmptyState = true, isLoading = false))
                }
            }
        }
    }

    private fun searchUsers(isAppend: Boolean) {
        viewModelScope.launch {
            _uiState.emit(currentState.copy(isLoading = true))
            val result = githubSearchUseCase.invoke(
                GithubSearchUseCaseParam(
                    query = query,
                    perPage = perPage,
                    page = page
                )
            )
            _uiState.emit(
                currentState.copy(
                    showErrorState = result is FetchData.Error,
                    showEmptyState = result is FetchData.Empty,
                    isLoading = false
                )
            )
            if (result is FetchData.Success) {
                val appendedResult = if (isAppend) currentState.users + result.data
                else result.data
                _uiState.emit(currentState.copy(users = appendedResult))
                page++
            } else if(result is FetchData.Error){
                _effect.send(ViewEffect.ShowErrorMessage(result.message))
            }
        }
    }

    sealed interface ViewEffect {
        data class SwipeCard(val state: SwipeableCardState, val direction: Direction) : ViewEffect
        data class ShowErrorMessage(val message: String)  : ViewEffect
    }

    sealed interface ViewEvent {
        data class Search(val query: String) : ViewEvent
        data class OnSwipeButtonClick(
            val snapshot: List<Pair<UserItem, SwipeableCardState>>,
            val direction: Direction
        ) : ViewEvent

        object LoadNextPage : ViewEvent
        object ShowEmptyState : ViewEvent
    }

    data class ViewState(
        val users: List<UserItem> = listOf(),
        val isLoading: Boolean = false,
        val showEmptyState: Boolean = false,
        val showErrorState: Boolean = false
    )
}
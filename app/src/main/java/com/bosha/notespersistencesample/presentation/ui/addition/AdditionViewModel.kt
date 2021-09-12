package com.bosha.notespersistencesample.presentation.ui.addition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bosha.notespersistencesample.data.utils.logError
import com.bosha.notespersistencesample.domain.entities.Note
import com.bosha.notespersistencesample.domain.interactors.AddEditNotesInteractor
import com.practice.domain.interactors.DeleteNotesInteractor
import com.bosha.notespersistencesample.domain.interactors.GetCachedNotesUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AdditionViewModel(
    private val addEditNotesInteractor: AddEditNotesInteractor,
    private val deleteNotesInteractor: DeleteNotesInteractor,
    private val getCachedNotesUseCase: GetCachedNotesUseCase
) : ViewModel() {

    private val handler = CoroutineExceptionHandler(::logError)

    private val _actionStateFlow: MutableStateFlow<ActionState> =
        MutableStateFlow(ActionState.EMPTY)
    val actionStateFlow get() = _actionStateFlow.asStateFlow()

    fun addNote(note: Note) =
        viewModelScope.launch(handler) {
            _actionStateFlow.update { ActionState.LOADING }

            addEditNotesInteractor.insertNoteCache(note)

            _actionStateFlow.value = ActionState.COMPLETE
        }

    fun delete(noteId: String) = viewModelScope.launch(handler) {
        _actionStateFlow.value = ActionState.LOADING
        deleteNotesInteractor.deleteNote(noteId)
        _actionStateFlow.value = ActionState.COMPLETE
    }

    enum class ActionState {
        LOADING,
        COMPLETE,
        EMPTY
    }
}
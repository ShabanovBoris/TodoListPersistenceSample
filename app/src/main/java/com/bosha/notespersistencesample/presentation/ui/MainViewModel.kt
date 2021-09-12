package com.bosha.notespersistencesample.presentation.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bosha.notespersistencesample.data.utils.logError
import com.bosha.notespersistencesample.domain.common.NotesResult
import com.practice.domain.interactors.DeleteNotesInteractor
import com.bosha.notespersistencesample.domain.interactors.GetCachedNotesUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val deleteNotesInteractor: DeleteNotesInteractor,
    private val getCachedNotesUseCase: GetCachedNotesUseCase
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler(::logError)

    private val _notesList =
        MutableSharedFlow<NotesResult>(1, 0, BufferOverflow.DROP_OLDEST)
    val notesList get() = _notesList.asSharedFlow()

    // region search parameters
    var mOnlyNotCompleted = false
    var mColorSearchFilter: Int? = null
    var mSearchByTitle = ""
    // endregion

    init {
        initList()
    }

    private fun initList() = viewModelScope.launch(exceptionHandler) {
        launch {
            getNotesCache()
        }
    }

    private suspend fun getNotesCache() = viewModelScope.launch(exceptionHandler) {
        getCachedNotesUseCase.getNotesCache()
            .combine(filter(mSearchByTitle)) { origin, afterFilter ->
                if (afterFilter is NotesResult.ValidResult || afterFilter is NotesResult.EmptyResult) {
                    return@combine afterFilter
                } else origin
            }.collect {
                _notesList.emit(it)
            }
    }

    fun search(
        searchByTitle: String = mSearchByTitle,
        onlyNotCompleted: Boolean = mOnlyNotCompleted,
        colorSearchFilter: Int? = mColorSearchFilter
    ) = viewModelScope.launch(exceptionHandler) {
        mOnlyNotCompleted = onlyNotCompleted
        mColorSearchFilter = colorSearchFilter
        mSearchByTitle = searchByTitle
        getNotesCache()
    }

    fun clearFilter() =
        viewModelScope.launch(exceptionHandler) {
            mOnlyNotCompleted = false
            mColorSearchFilter = null
            mSearchByTitle = ""
            getNotesCache()
        }

    fun clearData() = viewModelScope.launch(exceptionHandler) {
        deleteNotesInteractor.clearCache()
    }

    private suspend fun filter(noteTitle: String): Flow<NotesResult> = getCachedNotesUseCase.getNotesCache()
        .onEach { mSearchByTitle = noteTitle }
        .map { noteResult ->
            if (noteResult is NotesResult.ValidResult) {
                if (mSearchByTitle.isEmpty() && mColorSearchFilter == null && !mOnlyNotCompleted)
                    return@map NotesResult.EmptySearch

                var newNotesResult =
                    if (mSearchByTitle.isNotEmpty()) NotesResult.ValidResult(
                        doList = noteResult.doList?.filter { note ->
                            note.title.contains(mSearchByTitle, true)
                        },
                        doingList = noteResult.doingList?.filter { note ->
                            note.title.contains(mSearchByTitle, true)
                        },
                        doneList = noteResult.doneList?.filter { note ->
                            note.title.contains(mSearchByTitle, true)
                        }
                    ) else noteResult

                if (mColorSearchFilter != null) newNotesResult = newNotesResult.copy(
                    doList = newNotesResult.doList?.filter { note -> note.colorId == mColorSearchFilter },
                    doingList = newNotesResult.doingList?.filter { note -> note.colorId == mColorSearchFilter },
                    doneList = newNotesResult.doneList?.filter { note -> note.colorId == mColorSearchFilter },
                )

                if (newNotesResult == NotesResult.ValidResult(emptyList(), emptyList(), emptyList()))
                    return@map NotesResult.EmptyResult
                else newNotesResult

            } else noteResult
        }
}






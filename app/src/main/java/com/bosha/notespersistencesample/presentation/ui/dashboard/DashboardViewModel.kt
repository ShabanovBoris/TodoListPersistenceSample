package com.bosha.notespersistencesample.presentation.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bosha.notespersistencesample.data.utils.logError
import com.bosha.notespersistencesample.domain.common.NotesResult
import com.bosha.notespersistencesample.domain.interactors.GetCachedNotesInteractor
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.*

class DashboardViewModel(
    private val getCachedNotesInteractor: GetCachedNotesInteractor
) : ViewModel() {


    /**
     * Just returns type of latest changed item
     */
    val changesAlertFlow: SharedFlow<Int> by lazy(LazyThreadSafetyMode.NONE) {
        flow { emitAll(getCachedNotesInteractor.getNotesCache()) }
            .distinctUntilChanged()
            //drop initial emit
            .drop(1)
            .map {
               return@map when(it){
                    is NotesResult.ValidResult -> {
                        (it.doList.orEmpty() + it.doingList.orEmpty() + it.doneList.orEmpty()).run {
                            maxByOrNull { listItem -> listItem.createDate }?.type ?: INVALID_VALUE
                        }
                    }
                    else ->  INVALID_VALUE
                }
            }
            .catch { logError(currentCoroutineContext(), it, this@DashboardViewModel, 26) }
            .shareIn(viewModelScope, SharingStarted.Lazily, 0)
    }


   private companion object{
        const val INVALID_VALUE = -1
    }
}

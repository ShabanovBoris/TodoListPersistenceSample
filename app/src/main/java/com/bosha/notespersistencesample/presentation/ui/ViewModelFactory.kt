package com.bosha.notespersistencesample.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bosha.notespersistencesample.domain.interactors.AddEditNotesInteractor
import com.practice.domain.interactors.DeleteNotesInteractor
import com.bosha.notespersistencesample.domain.interactors.GetCachedNotesInteractor
import com.bosha.notespersistencesample.di.scopes.ScreenScope
import com.bosha.notespersistencesample.presentation.ui.addition.AdditionViewModel
import com.bosha.notespersistencesample.presentation.ui.dashboard.DashboardViewModel
import javax.inject.Inject

@ScreenScope
class ViewModelFactory @Inject constructor(
    private val addEditNotesInteractor: AddEditNotesInteractor,
    private val deleteNotesInteractor: DeleteNotesInteractor,
    private val getNotesInteractor: GetCachedNotesInteractor
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {

        MainViewModel::class.java -> MainViewModel(
            deleteNotesInteractor,
            getNotesInteractor
        )

        AdditionViewModel::class.java -> AdditionViewModel(
            addEditNotesInteractor,
            deleteNotesInteractor,
            getNotesInteractor
        )

        DashboardViewModel::class.java -> DashboardViewModel(
            getNotesInteractor
        )


        else -> error("$modelClass is not registered ViewModel")
    } as T
}

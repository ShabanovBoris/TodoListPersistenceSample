package com.bosha.notespersistencesample.presentation.di

import androidx.lifecycle.LifecycleOwner
import com.bosha.notespersistencesample.di.DataModule
import com.bosha.notespersistencesample.di.InteractorModule
import com.bosha.notespersistencesample.di.DataBaseModule
import com.bosha.notespersistencesample.di.scopes.ScreenScope
import com.bosha.notespersistencesample.presentation.ui.MainActivity
import com.bosha.notespersistencesample.presentation.ui.addition.AdditionFragment
import com.bosha.notespersistencesample.presentation.ui.dashboard.DashboardFragment
import com.bosha.notespersistencesample.presentation.ui.dashboard.filter.FilterBottomSheet
import com.bosha.notespersistencesample.presentation.ui.dashboard.pager.PagerFragment
import dagger.BindsInstance
import dagger.Subcomponent

@ScreenScope
@Subcomponent(modules = [InteractorModule::class, DataModule::class, DataBaseModule::class])
interface MainScreenComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(@BindsInstance screenLifecycleOwner: LifecycleOwner): MainScreenComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: AdditionFragment)
    fun inject(fragment: DashboardFragment)
    fun inject(fragment: FilterBottomSheet)
    fun inject(fragment: PagerFragment)
}
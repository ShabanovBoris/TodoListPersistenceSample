package com.bosha.notespersistencesample.di

import android.content.Context
import com.bosha.notespersistencesample.presentation.di.MainScreenComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [Subcomponents::class, DispatcherModule::class]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance appContext: Context): AppComponent
    }

    fun plusMainScreenComponent(): MainScreenComponent.Factory
}
package com.bosha.notespersistencesample

import android.app.Application
import com.bosha.notespersistencesample.di.AppComponent
import com.bosha.notespersistencesample.di.DaggerAppComponent
import com.bosha.notespersistencesample.presentation.utils.NightModeHelper

class NotesApp: Application() {

    lateinit var appComponent: AppComponent private set

    override fun onCreate() {
        super.onCreate()
        NightModeHelper(this).setUpNightModePreference()
        appComponent = DaggerAppComponent.factory().create(this)
    }
}
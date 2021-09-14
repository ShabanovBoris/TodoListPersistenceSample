package com.bosha.notespersistencesample.data.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.preference.PreferenceManager
import com.bosha.notespersistencesample.data.reposetories.NotesRepositoryMediator
import com.bosha.notespersistencesample.domain.common.KEY_DATASTORE
import com.bosha.notespersistencesample.domain.common.ROOM_DATASTORE

/**
 * Class responsible for work with Data store preferences
 */
class DataStorePreference(
    appContext: Context,
    lifecycleOwner: LifecycleOwner,
    private val onPrefsChangedListener: (value: String) -> Unit
) : LifecycleObserver {

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    private val preference =
        PreferenceManager.getDefaultSharedPreferences(appContext)

    private var preferenceChangeListener: SharedPreferences.OnSharedPreferenceChangeListener? = null

    //return Room by default
    val dataPref: String
        get() {
            return if (preference.contains(KEY_DATASTORE)) {
                preference.getString(KEY_DATASTORE, null) ?: error("Illegal argument")
            } else {
                preference.edit {
                    putString(KEY_DATASTORE, ROOM_DATASTORE)
                }
                ROOM_DATASTORE
            }
        }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun registerListener() {
        preferenceChangeListener =
            SharedPreferences.OnSharedPreferenceChangeListener { sharedprefs, key ->
                if (key == KEY_DATASTORE) {
                    val newValue = sharedprefs.getString(key, ROOM_DATASTORE) ?: ROOM_DATASTORE
                    onPrefsChangedListener(newValue)
                }
            }
        preference.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
        Log.e(
            NotesRepositoryMediator::class.java.simpleName,
            "registerListener()",
        )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun unregisterListener() {
        preference.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
        Log.e(
            NotesRepositoryMediator::class.java.simpleName,
            "unregisterListener()",
        )
    }
}
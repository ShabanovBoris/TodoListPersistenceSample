package com.bosha.notespersistencesample.presentation.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.bosha.notespersistencesample.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }


}
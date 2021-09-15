package com.bosha.notespersistencesample.presentation.utils

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import com.bosha.notespersistencesample.R

/**
 * handle night mode user preferences
 * @param activity for [SharedPreferences] access
 * @param toolBar with [icon] for switch modes
 * @see
 * Icon id will searching by
 *  findItem(R.id.night_mode_menu_icon)
 */
class NightModeHelper(
    private val context: Context
) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(KEY_NIGHT_MODE_PREFS, Context.MODE_PRIVATE)
    private val nightModeFlags = context.resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK

    fun setUpNightModePreference() {

        if (prefs.contains(KEY_MODE)) {
            when (prefs.getInt(KEY_MODE, UNDEFINED)) {

                NIGHT_YES -> {
                    if (nightModeFlags != NIGHT_YES)
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                NIGHT_NO -> {
                    if (nightModeFlags != NIGHT_NO)
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                else -> {
                    Log.e(NightModeHelper::javaClass.name, "Night mode UI_MODE_NIGHT_UNDEFINED", )
                }
            }
        } else {
            when (nightModeFlags) {
                NIGHT_YES -> {
                    prefs.edit {
                        putInt(KEY_MODE, NIGHT_YES)
                    }
                }
                NIGHT_NO -> {
                    prefs.edit {
                        putInt(KEY_MODE, NIGHT_NO)
                    }
                }
                UNDEFINED -> {
                    prefs.edit {
                        putInt(KEY_MODE, UNDEFINED)
                    }
                }
            }
        }


    }

    /**
     * recommended call this method in [onCreate] or [onCreateView] callback
     * because after change theme, activity will recreate yourself
     * method will calling one more time and setup new icon to the toolbar
     */
    fun setUpNightSwitcher(toolBar: androidx.appcompat.widget.Toolbar) {
        val icon = toolBar.menu.findItem(R.id.night_mode_menu_icon)
        val mode = prefs.getInt(KEY_MODE, UNDEFINED)
        //setup icons
        when (mode) {
            NIGHT_NO -> icon.setIcon(R.drawable.ic_dark_mode)
            NIGHT_YES -> icon.setIcon(R.drawable.ic_day_mode)
        }
        toolBar.setOnMenuItemClickListener {
            if (it.itemId == R.id.night_mode_menu_icon)
                when (mode) {
                    NIGHT_NO -> {
                        prefs.edit {
                            putInt(KEY_MODE, NIGHT_YES)
                        }
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                    NIGHT_YES -> {
                        prefs.edit {
                            putInt(KEY_MODE, NIGHT_NO)
                        }
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                    else -> {
                        Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
                    }
                }

            return@setOnMenuItemClickListener true
        }
    }

    companion object{
        private const val KEY_NIGHT_MODE_PREFS = "ModeNightDay"
        private const val KEY_MODE = "mode"
        private const val NIGHT_YES = Configuration.UI_MODE_NIGHT_YES
        private const val NIGHT_NO = Configuration.UI_MODE_NIGHT_NO
        private const val UNDEFINED = Configuration.UI_MODE_NIGHT_UNDEFINED
    }
}
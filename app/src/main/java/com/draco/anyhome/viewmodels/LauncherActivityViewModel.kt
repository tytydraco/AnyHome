package com.draco.anyhome.viewmodels

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.preference.PreferenceManager

class LauncherActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
    private val packageManager = application.packageManager

    /**
     * App ID for home activity
     */
    private val homeApp: String
        get() = sharedPrefs.getString("home_app", "")!!

    /**
     * App launcher intent for home activity
     */
    val homeAppIntent: Intent?
        get() = packageManager.getLaunchIntentForPackage(homeApp)

    /**
     * Has the user selected a valid home application yet?
     */
    fun isHomeAppSet(): Boolean {
        if (homeApp.isNotBlank())
            return true

        return false
    }
}
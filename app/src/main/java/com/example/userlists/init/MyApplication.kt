package com.example.userlists.init

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.userlists.theme.ThemeManager
import com.example.userlists.utils.PreferencesManager

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        val preferencesManager = PreferencesManager(this)
        ThemeManager.applyTheme(preferencesManager.themeMode)
    }
}
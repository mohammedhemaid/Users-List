package com.example.userlists.settings

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.userlists.databinding.ActivitySettingsBinding
import com.example.userlists.theme.ThemeManager
import com.example.userlists.theme.ThemeMode
import com.example.userlists.utils.PreferencesManager

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferencesManager = PreferencesManager(this)

        val toggle = binding.toggle
        toggle.isChecked = preferencesManager.themeMode == ThemeMode.DARK ||
                (preferencesManager.themeMode == ThemeMode.FOLLOW_SYSTEM &&
                        resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
                        == Configuration.UI_MODE_NIGHT_YES)

        toggle.setOnCheckedChangeListener{ _,isChecked ->
            val themeMode = if (isChecked) ThemeMode.DARK else ThemeMode.LIGHT
            preferencesManager.themeMode = themeMode
            ThemeManager.applyTheme(themeMode)
        }
    }
}
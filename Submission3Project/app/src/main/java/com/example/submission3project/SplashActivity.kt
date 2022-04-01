package com.example.submission3project
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.submission3project.viewmodel.SettingPrefViewModel
import com.example.submission3project.viewmodel.VMFactorySetting

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        var timeSplash: Long = 3000
        Handler().postDelayed({
            val transisi = Intent(this, MainActivity::class.java)
            startActivity(transisi)
            finish()
        }, timeSplash)

        val pref = SettingsPreference.getInstance(dataStore)
        val settingPrefViewModel = ViewModelProvider(this, VMFactorySetting(pref)).get(
            SettingPrefViewModel::class.java)
        settingPrefViewModel.getThemeSettings().observe(this, { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        })
    }
}
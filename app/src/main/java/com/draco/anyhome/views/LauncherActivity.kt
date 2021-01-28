package com.draco.anyhome.views

import android.content.Intent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.draco.anyhome.viewmodels.LauncherActivityViewModel

class LauncherActivity : AppCompatActivity() {
    private val viewModel: LauncherActivityViewModel by viewModels()

    override fun onResume() {
        super.onResume()

        if (!viewModel.isHomeAppSet()) {
            val intent = Intent(this, SelectActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)

            return
        }

        startActivity(viewModel.homeAppIntent)
        overridePendingTransition(0, 0)
    }
}
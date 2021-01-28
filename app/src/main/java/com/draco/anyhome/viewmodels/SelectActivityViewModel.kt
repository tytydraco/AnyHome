package com.draco.anyhome.viewmodels

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.draco.anyhome.models.AppInfo
import java.util.*

class SelectActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext

    private val _appList = MutableLiveData<List<AppInfo>>()
    val appList: LiveData<List<AppInfo>> = _appList

    init {
        updateList()
    }

    fun updateList() {
        val launcherIntent = Intent(Intent.ACTION_MAIN, null).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }

        val activities = context.packageManager.queryIntentActivities(launcherIntent, 0)
        val newAppList = arrayListOf<AppInfo>()

        for (app in activities) {
            if (app.activityInfo.packageName == context.packageName)
                continue

            newAppList.add(
                AppInfo(
                    app.activityInfo.loadLabel(context.packageManager).toString(),
                    app.activityInfo.packageName
                )
            )
        }

        newAppList.sortBy {
            it.label.toLowerCase(Locale.getDefault())
        }

        if (!_appList.value?.toTypedArray().contentEquals(newAppList.toTypedArray()))
            _appList.value = newAppList.toList()
    }
}
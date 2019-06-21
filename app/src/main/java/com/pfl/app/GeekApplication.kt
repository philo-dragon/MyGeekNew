package com.pfl.app

import android.app.Application
import com.pfl.lib_common.utils.BaseUrlManager

class GeekApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        BaseUrlManager.init("https://data.moxinga.com111/",false)
    }
}

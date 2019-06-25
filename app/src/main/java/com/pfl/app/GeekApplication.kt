package com.pfl.app

import com.pfl.lib_common.base.BaseApplication
import com.pfl.lib_common.utils.BaseUrlManager

class GeekApplication : BaseApplication(){
    override fun onCreate() {
        super.onCreate()
        BaseUrlManager.init("https://data.moxinga.com/",true)
    }
}

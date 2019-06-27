package com.pfl.app

import com.pfl.geeknews.BuildConfig
import com.pfl.lib_common.base.BaseApplication
import com.pfl.lib_common.utils.BaseUrlManager

class GeekApplication : BaseApplication(){
    override fun onCreate() {
        super.onCreate()
        BaseUrlManager.init(BuildConfig.SERVER_URL, BuildConfig.IS_DEBUG)
    }
}

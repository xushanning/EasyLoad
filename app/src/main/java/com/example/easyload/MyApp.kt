package com.example.easyload

import android.app.Application
import com.example.easyload.states.EmptyState
import com.example.easyload.states.ErrorState
import com.example.easyload.states.PlaceHolderState
import com.xu.easyload.EasyLoad

/**
 * @author 言吾許
 */
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        //初始化EasyLoad
        EasyLoad.initGlobal()
                .addGlobalState(ErrorState())
                .addGlobalState(EmptyState())
                .addGlobalState(PlaceHolderState())
                .setGlobalDefaultState(PlaceHolderState::class.java)
    }


}
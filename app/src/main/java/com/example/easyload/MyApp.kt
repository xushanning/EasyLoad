package com.example.easyload

import android.app.Application
import com.example.easyload.states.EmptyState
import com.example.easyload.states.ErrorState
import com.example.easyload.states.LoadingState
import com.example.easyload.states.NoInternetState
import com.xu.easyload.EasyLoad

/**
 * @author 言吾許
 */
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        EasyLoad.initGlobal()
                .addGlobalState(ErrorState())
                .addGlobalState(EmptyState())
                .addGlobalState(LoadingState())
                .addGlobalState(NoInternetState())
                .setGlobalDefaultState(LoadingState::class.java)
    }
}
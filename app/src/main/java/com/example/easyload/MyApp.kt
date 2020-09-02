package com.example.easyload

import android.app.Application
import com.example.easyload.states.EmptyState
import com.example.easyload.states.ErrorState
import com.example.easyload.states.LoadingState
import com.example.easyload.states.NoInternetState
import com.xu.easyload.ext.initEasyLoad

/**
 * @author 言吾許
 */
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        //初始化方法①
//        EasyLoad.initGlobal()
//                .addGlobalState(ErrorState())
//                .addGlobalState(EmptyState())
//                .addGlobalState(LoadingState())
//                .addGlobalState(NoInternetState())
//                .setGlobalDefaultState(LoadingState::class.java)

        //初始化方法②
//        EasyLoad.initGlobal()
//                .init {
//                    addGlobalState(ErrorState())
//                }

        //初始化方法③
        initEasyLoad {
            addGlobalState(ErrorState())
            addGlobalState(EmptyState())
            addGlobalState(LoadingState())
            addGlobalState(NoInternetState())
            setGlobalDefaultState(LoadingState::class.java)
        }
    }
}
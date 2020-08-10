package com.example.easyload.ui

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.easyload.DelayUtil
import com.example.easyload.R
import com.example.easyload.states.*
import com.xu.easyload.EasyLoad
import com.xu.easyload.service.ILoadService
import com.xu.easyload.state.SuccessState

/**
 * @author 言吾許
 */
class FragmentB : Fragment() {
    lateinit var service: ILoadService
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_b, container, false)
        service = EasyLoad.initLocal()
                .addLocalState(LoadingState2())
                .setLocalDefaultState(LoadingState2::class.java)
                .inject(view) {
                    setOnReloadListener { iLoadService, clickState, view ->
                        iLoadService.showState(LoadingState::class.java)
                        Handler().postDelayed({
                            iLoadService.showState(SuccessState::class.java)
                        }, 3000)
                    }
                }
        return service.getParentView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DelayUtil.delay(service, NoInternetState::class.java, 3000)
    }
}
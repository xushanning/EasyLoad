package com.example.easyload.ui

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.easyload.DelayUtil
import com.example.easyload.R
import com.example.easyload.states.ErrorState
import com.example.easyload.states.PlaceHolderState
import com.xu.easyload.EasyLoad
import com.xu.easyload.service.ILoadService
import com.xu.easyload.state.SuccessState

/**
 * @author 言吾許
 */
class FragmentA : Fragment() {
    lateinit var service: ILoadService
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_a, container, false)
        service = EasyLoad.initLocal().inject(view) {
            setOnReloadListener { iLoadService, clickState, view ->
                //todo 这里有bug
                iLoadService.showState(PlaceHolderState::class.java)
                SystemClock.sleep(3000)
                iLoadService.showState(SuccessState::class.java)
            }
        }
        return service.getParentView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DelayUtil.delay(service, ErrorState::class.java, 3000)
    }
}
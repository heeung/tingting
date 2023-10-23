package com.alsif.tingting.ui.home

import androidx.lifecycle.ViewModel
import com.alsif.tingting.data.model.PagerDataDto
import com.alsif.tingting.data.throwable.DataThrowable
import com.alsif.tingting.ui.home.dummy.adList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(

)  : ViewModel() {

    private val _error = MutableSharedFlow<DataThrowable>()
    var error = _error.asSharedFlow()

    ////// for ads banner //////
    lateinit var job: Job
    var bannerPosition = 0

    // TODO 더미 데이터 변경
    val pagerList: ArrayList<PagerDataDto> = ArrayList<PagerDataDto>().let {
        it.apply {
            add(PagerDataDto(adList[0]))
            add(PagerDataDto(adList[1]))
            add(PagerDataDto(adList[2]))
            add(PagerDataDto(adList[3]))
            add(PagerDataDto(adList[4]))
        }
    }
    ///////////////////////////
}
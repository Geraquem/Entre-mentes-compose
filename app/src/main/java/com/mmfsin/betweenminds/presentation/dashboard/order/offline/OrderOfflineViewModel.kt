package com.mmfsin.betweenminds.presentation.dashboard.order.offline

import com.mmfsin.betweenminds.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderOfflineViewModel @Inject constructor(

) : BaseViewModel<OrderOfflineStates>(OrderOfflineStates()) {

    init {

    }
}
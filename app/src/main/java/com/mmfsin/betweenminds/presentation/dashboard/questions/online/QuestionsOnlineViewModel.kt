package com.mmfsin.betweenminds.presentation.dashboard.questions.online

import com.mmfsin.betweenminds.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuestionsOnlineViewModel @Inject constructor(

) : BaseViewModel<QuestionsOnlineStates>(QuestionsOnlineStates()) {

    init {

    }
}
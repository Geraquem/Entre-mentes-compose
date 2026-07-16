package com.mmfsin.betweenmindscompose.presentation.bedrock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mmfsin.betweenmindscompose.presentation.core.navigation.NavigationQuestionsOffline
import com.mmfsin.betweenmindscompose.utils.BEDROCK_NAV_GRAPH
import com.mmfsin.betweenmindscompose.utils.BEDROCK_STR_ARGS
import com.mmfsin.betweenmindscompose.utils.NAV_QUESTIONS_OFFLINE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BedRockActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navGraph = intent?.getStringExtra(BEDROCK_NAV_GRAPH)
        val strArgs = intent?.getStringExtra(BEDROCK_STR_ARGS)

        enableEdgeToEdge()
        setContent {
            when (navGraph) {
                NAV_QUESTIONS_OFFLINE -> NavigationQuestionsOffline()
                else -> finish()
            }
        }
    }
}
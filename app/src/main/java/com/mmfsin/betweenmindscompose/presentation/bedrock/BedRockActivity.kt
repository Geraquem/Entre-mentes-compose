package com.mmfsin.betweenmindscompose.presentation.bedrock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mmfsin.betweenmindscompose.domain.models.GameType.QUESTIONS
import com.mmfsin.betweenmindscompose.domain.models.GameType.RANGES
import com.mmfsin.betweenmindscompose.presentation.core.navigation.NavigationQuestionsOffline
import com.mmfsin.betweenmindscompose.presentation.instructions.InstructionsScreen
import com.mmfsin.betweenmindscompose.utils.BEDROCK_NAV_GRAPH
import com.mmfsin.betweenmindscompose.utils.BEDROCK_STR_ARGS
import com.mmfsin.betweenmindscompose.utils.NAV_INSTR_QUESTIONS_OFFLINE
import com.mmfsin.betweenmindscompose.utils.NAV_INSTR_QUESTIONS_ONLINE
import com.mmfsin.betweenmindscompose.utils.NAV_INSTR_RANGES_OFFLINE
import com.mmfsin.betweenmindscompose.utils.NAV_INSTR_RANGES_ONLINE
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

                /** Instructions */
                NAV_INSTR_QUESTIONS_ONLINE -> InstructionsScreen(gameType = QUESTIONS, onlineMode = true)
                NAV_INSTR_QUESTIONS_OFFLINE -> InstructionsScreen(gameType = QUESTIONS, onlineMode = false)
                NAV_INSTR_RANGES_ONLINE -> InstructionsScreen(gameType = RANGES, onlineMode = true)
                NAV_INSTR_RANGES_OFFLINE -> InstructionsScreen(gameType = RANGES, onlineMode = false)
                else -> finish()
            }
        }
    }
}
package com.mmfsin.betweenminds.presentation.bedrock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mmfsin.betweenminds.domain.models.GameType.QUESTIONS
import com.mmfsin.betweenminds.domain.models.GameType.RANGES
import com.mmfsin.betweenminds.domain.models.GameType.RANKING
import com.mmfsin.betweenminds.presentation.core.navigation.NavigationQuestionsOffline
import com.mmfsin.betweenminds.presentation.core.navigation.NavigationQuestionsOnlineCreator
import com.mmfsin.betweenminds.presentation.core.navigation.NavigationQuestionsOnlineJoin
import com.mmfsin.betweenminds.presentation.core.navigation.NavigationRangesOffline
import com.mmfsin.betweenminds.presentation.core.navigation.NavigationRangesOnline
import com.mmfsin.betweenminds.presentation.core.navigation.NavigationRankingOffline
import com.mmfsin.betweenminds.presentation.instructions.InstructionsScreen
import com.mmfsin.betweenminds.utils.BEDROCK_BOOL_ARGS
import com.mmfsin.betweenminds.utils.BEDROCK_NAV_GRAPH
import com.mmfsin.betweenminds.utils.BEDROCK_STR_ARGS
import com.mmfsin.betweenminds.utils.NAV_INSTR_QUESTIONS_OFFLINE
import com.mmfsin.betweenminds.utils.NAV_INSTR_QUESTIONS_ONLINE
import com.mmfsin.betweenminds.utils.NAV_INSTR_RANGES_OFFLINE
import com.mmfsin.betweenminds.utils.NAV_INSTR_RANGES_ONLINE
import com.mmfsin.betweenminds.utils.NAV_INSTR_RANKING_OFFLINE
import com.mmfsin.betweenminds.utils.NAV_INSTR_RANKING_ONLINE
import com.mmfsin.betweenminds.utils.NAV_QUESTIONS_OFFLINE
import com.mmfsin.betweenminds.utils.NAV_QUESTIONS_ONLINE_CREATOR
import com.mmfsin.betweenminds.utils.NAV_QUESTIONS_ONLINE_JOINED
import com.mmfsin.betweenminds.utils.NAV_RANGES_OFFLINE
import com.mmfsin.betweenminds.utils.NAV_RANGES_ONLINE
import com.mmfsin.betweenminds.utils.NAV_RANKING_OFFLINE
import com.mmfsin.betweenminds.utils.NAV_RANKING_ONLINE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BedRockActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navGraph = intent?.getStringExtra(BEDROCK_NAV_GRAPH)
        val strArgs = intent?.getStringExtra(BEDROCK_STR_ARGS)
        val boolArgs = intent?.getBooleanExtra(BEDROCK_BOOL_ARGS, false)

        enableEdgeToEdge()
        setContent {
            when (navGraph) {
                /** Dashboard */
                NAV_QUESTIONS_ONLINE_CREATOR -> NavigationQuestionsOnlineCreator(roomCode = strArgs)
                NAV_QUESTIONS_ONLINE_JOINED -> NavigationQuestionsOnlineJoin(roomCode = strArgs)
                NAV_QUESTIONS_OFFLINE -> NavigationQuestionsOffline()

                NAV_RANGES_ONLINE -> NavigationRangesOnline(roomCode = strArgs, isCreator = boolArgs)
                NAV_RANGES_OFFLINE -> NavigationRangesOffline()

                NAV_RANKING_ONLINE -> {}
                NAV_RANKING_OFFLINE -> NavigationRankingOffline()

                /** Instructions */
                NAV_INSTR_QUESTIONS_ONLINE -> InstructionsScreen(gameType = QUESTIONS, onlineMode = true)
                NAV_INSTR_QUESTIONS_OFFLINE -> InstructionsScreen(gameType = QUESTIONS, onlineMode = false)
                NAV_INSTR_RANGES_ONLINE -> InstructionsScreen(gameType = RANGES, onlineMode = true)
                NAV_INSTR_RANGES_OFFLINE -> InstructionsScreen(gameType = RANGES, onlineMode = false)
                NAV_INSTR_RANKING_ONLINE -> InstructionsScreen(gameType = RANKING, onlineMode = true)
                NAV_INSTR_RANKING_OFFLINE -> InstructionsScreen(gameType = RANKING, onlineMode = false)

                else -> finish()
            }
        }
    }
}
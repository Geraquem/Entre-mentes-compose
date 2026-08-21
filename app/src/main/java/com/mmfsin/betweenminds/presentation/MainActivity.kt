package com.mmfsin.betweenminds.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mmfsin.betweenminds.presentation.core.navigation.NavigationMain
import com.mmfsin.betweenminds.presentation.core.theme.BMCTheme
import com.mmfsin.betweenminds.utils.NAV_QUESTIONS_OFFLINE
import com.mmfsin.betweenminds.utils.openBedRockActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { BMCTheme { NavigationMain() } }

        //        this.openBedRockActivity(NAV_QUESTIONS_ONLINE)
//                this.openBedRockActivity(NAV_QUESTIONS_OFFLINE)
    }
}
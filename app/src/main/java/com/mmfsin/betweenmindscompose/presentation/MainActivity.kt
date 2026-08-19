package com.mmfsin.betweenmindscompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.mmfsin.betweenmindscompose.utils.NAV_QUESTIONS_OFFLINE
import com.mmfsin.betweenmindscompose.utils.openBedRockActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContent { BMCTheme { NavigationMain() } }
//        setContent { BMCTheme { InstructionsScreen() } }
        this.openBedRockActivity(NAV_QUESTIONS_OFFLINE)
    }
}
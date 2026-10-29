package com.alifwanoon.v72
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity: ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContent{
            MaterialTheme{
                Column(Modifier.fillMaxSize().padding(24.dp)){
                    Text("ألف ونون 7.2", style=MaterialTheme.typography.headlineLarge)
                    Text("Native Kotlin - 230 ميزة")
                    Text("ADMIN-ELITE-2024")
                    Text("bmntjat81-dotcom/AlifWaNoon-v7.2")
                }
            }
        }
    }
}

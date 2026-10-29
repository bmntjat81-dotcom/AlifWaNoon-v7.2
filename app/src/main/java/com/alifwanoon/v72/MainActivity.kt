package com.alifwanoon.v72

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alifwanoon.v72.core.AlertSystem
import com.alifwanoon.v72.core.CodeGen
import com.alifwanoon.v72.core.K
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val codeGen = CodeGen()
    private val alertSystem by lazy { AlertSystem(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Validate admin token on start
        if (K.ADMIN_TOKEN != "ADMIN-ELITE-2024") {
            finish(); return
        }

        setContent {
            AlifTheme {
                var currentCode by remember { mutableStateOf<String?>(null) }
                val scope = rememberCoroutineScope()
                
                Scaffold { padding ->
                    Column(Modifier.padding(padding).padding(16.dp)) {
                        Text("ألف و نون v7.2", style = MaterialTheme.typography.headlineLarge)
                        Spacer(Modifier.height(16.dp))
                        
                        Button(onClick = {
                            scope.launch {
                                val paired = codeGen.generatePaired()
                                currentCode = paired
                                alertSystem.triggerWithTone(paired)
                            }
                        }) {
                            Text("توليد كود مقترن جديد")
                        }
                        
                        currentCode?.let {
                            Card(Modifier.padding(top = 12.dp)) {
                                Text(it, Modifier.padding(16.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AlifTheme(content: @Composable () -> Unit) {
    MaterialTheme(content = content)
}

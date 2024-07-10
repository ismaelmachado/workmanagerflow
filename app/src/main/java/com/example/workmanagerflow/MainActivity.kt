package com.example.workmanagerflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.workmanagerflow.ui.theme.WorkManagerFlowTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val workViewModel: WorkViewModel = viewModel(factory = WorkViewModel.factory)
            val uiState by workViewModel.uiState.collectAsState()

            WorkManagerFlowTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Row(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        StatusView(
                            status = uiState,
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxWidth()
                                .align(Alignment.CenterVertically)
                        )
                    }
                }
            }
        }
    }
}

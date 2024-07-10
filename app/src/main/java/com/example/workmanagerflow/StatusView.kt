package com.example.workmanagerflow

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.workmanagerflow.ui.theme.WorkManagerFlowTheme

@Composable
fun StatusView(
    status: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = "Status = $status",
        modifier = modifier,
        textAlign = TextAlign.Center
    )
}

@Preview(showBackground = true)
@Composable
fun StatusViewPreview() {
    WorkManagerFlowTheme {
        StatusView("Android")
    }
}
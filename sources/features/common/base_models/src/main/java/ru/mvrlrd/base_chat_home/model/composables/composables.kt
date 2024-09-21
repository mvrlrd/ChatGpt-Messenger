package ru.mvrlrd.base_chat_home.model.composables

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun ShowToast(flow: Flow<String>) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    coroutineScope.launch {
        flow.collectLatest {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }
}
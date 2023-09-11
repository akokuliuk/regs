package com.example.regsample.registration.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class RegistrationScreenViewModel : ViewModel() {
    val currentScreen = MutableStateFlow<@Composable () -> Unit> {}

    enum class RegistrationScreen {
        NAME,
        EMAIL,
        OPTIONAL
    }
}

package com.example.regsample.registration.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.regsample.registration.model.RegistrationRequestValidator
import com.example.regsample.registration.service.RegistrationService


@Composable
fun RegistrationScreen(
    registrationService: RegistrationService = RegistrationService(RegistrationRequestValidator())
) {
    val navController = rememberNavController()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        NavHost(navController = navController, startDestination = "register/name") {
            composable("register/name") {
                
            }
        }
    }
}

@Composable
@Preview(
    device = "id:Nexus One",
    uiMode = Configuration.UI_MODE_TYPE_NORMAL
)
internal fun RegistrationScreenPreview() {
    RegistrationScreen()
}
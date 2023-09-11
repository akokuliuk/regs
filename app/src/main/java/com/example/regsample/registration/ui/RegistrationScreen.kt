package com.example.regsample.registration.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.regsample.registration.model.EmailForm
import com.example.regsample.registration.model.FirstLastNameForm
import com.example.regsample.registration.model.OptionalForm
import com.example.regsample.registration.model.RegistrationRequestValidator
import com.example.regsample.registration.service.RegistrationFlowService
import com.example.regsample.registration.ui.forms.EmailForm
import com.example.regsample.registration.ui.forms.FirstLastNameForm
import com.example.regsample.registration.ui.forms.OptionalForm


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    registrationFlowService: RegistrationFlowService = RegistrationFlowService(
        RegistrationRequestValidator()
    )
) {
    val navController = rememberNavController()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val isRegistrationFormValid =
            registrationFlowService.isRegistrationFormValid.collectAsState(
                initial = false
            )

        NavHost(navController = navController, startDestination = "register/name") {
            composable("register/name") {
                FirstLastNameFormScreen(form = registrationFlowService) {
                    // TODO: Move navigation logic somewhere
                    navController.navigate("register/email")
                }
            }

            composable("register/email") {
                EmailFormScreen(form = registrationFlowService) {
                    navController.navigate("register/optional")
                }
            }

            composable("register/optional") {
                OptionalFormScreen(form = registrationFlowService)
            }
        }
        Button(onClick = { /*TODO()*/ }, enabled = isRegistrationFormValid.value) {
            Text(text = "Register")
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun FirstLastNameFormScreen(form: FirstLastNameForm, onNext: () -> Unit) {
    val isFormValid = form.isFirstLastNameFormValid().collectAsState(initial = false)
    FirstLastNameForm(form = form) {
        Button(onClick = onNext, enabled = isFormValid.value) {
            Text(text = "Next")
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun EmailFormScreen(form: EmailForm, onNext: () -> Unit) {
    val isFormValid = form.isEmailFormValid().collectAsState(initial = false)
    EmailForm(form = form) {
        Button(onClick = onNext, enabled = isFormValid.value) {
            Text(text = "Next")
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun OptionalFormScreen(form: OptionalForm) {
    val isFormValid = form.isOptionalFormValid().collectAsState(initial = false)
    OptionalForm(form = form) {}
}

@Composable
@Preview(
    device = "id:Nexus One", uiMode = Configuration.UI_MODE_TYPE_NORMAL
)
internal fun RegistrationScreenPreview() {
    RegistrationScreen()
}
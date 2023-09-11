package com.example.regsample.registration.ui.forms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import arrow.core.Either
import com.example.regsample.registration.model.EmailForm
import com.example.regsample.registration.model.FirstLastNameForm
import com.example.regsample.registration.model.InvalidFieldError
import com.example.regsample.registration.model.OptionalForm
import kotlinx.coroutines.flow.Flow

@ExperimentalMaterial3Api
@Composable
fun FirstLastNameForm(form: FirstLastNameForm, next: @Composable () -> Unit) {
    Column {
        FormField(onSubmit = { form.submitFirstName(it) }, hint = "First name")
        Spacer(modifier = Modifier.height(8.dp))
        FormField(onSubmit = { form.submitLastName(it) }, hint = "Last name")
        Spacer(modifier = Modifier.height(8.dp))
        next()
    }
}

@ExperimentalMaterial3Api
@Composable
fun EmailForm(form: EmailForm, next: @Composable () -> Unit) {
    Column {
        FormField(onSubmit = { form.submitEmail(it) }, hint = "Email")
        Spacer(modifier = Modifier.height(8.dp))
        FormField(onSubmit = { form.submitEmailCopy(it) }, hint = "Repeat your email")
        Spacer(modifier = Modifier.height(8.dp))
        next()
    }
}

@ExperimentalMaterial3Api
@Composable
fun OptionalForm(form: OptionalForm, next: @Composable () -> Unit) {
    Column {
        FormField(onSubmit = { form.submitOptional0(it) }, hint = "Optional 0")
        Spacer(modifier = Modifier.height(8.dp))
        FormField(onSubmit = { form.submitOptional1(it) }, hint = "Optional 1")
        Spacer(modifier = Modifier.height(8.dp))
        FormField(onSubmit = { form.submitOptional2(it) }, hint = "Optional 2")
        Spacer(modifier = Modifier.height(8.dp))
        next()
    }
}

@Composable
private fun NextButton(text: String, isEnabled: Flow<Boolean>, onClick: () -> Unit) {
    val enabled = isEnabled.collectAsState(initial = false)
    Button(onClick = onClick, enabled = enabled.value) { Text(text = text) }
}

@ExperimentalMaterial3Api
@Composable
private fun FormField(onSubmit: (String) -> Either<InvalidFieldError, Unit>, hint: String) {
    val fieldValue = remember { mutableStateOf("") }
    val fieldError = remember { mutableStateOf<String?>(null) }

    TextField(
        value = fieldValue.value, onValueChange = {
            fieldError.value = onSubmit(it).leftOrNull()?.message
            fieldValue.value = it
        },
        isError = fieldError.value != null,
        supportingText = {
            if (fieldError.value != null) {
                Text(text = fieldError.value ?: "")
            } else {
                Text(text = hint)
            }
        }
    )
}

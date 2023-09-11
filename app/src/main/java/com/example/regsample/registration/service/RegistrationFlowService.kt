package com.example.regsample.registration.service

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.raise.either
import arrow.core.right
import com.example.regsample.registration.model.EmailForm
import com.example.regsample.registration.model.FirstLastNameForm
import com.example.regsample.registration.model.InvalidFieldError
import com.example.regsample.registration.model.OptionalForm
import com.example.regsample.registration.model.RegistrationRequest
import com.example.regsample.registration.model.RegistrationRequestValidator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine


class RegistrationFlowService(
    private val validator: RegistrationRequestValidator
) : FirstLastNameForm, EmailForm, OptionalForm {

    private val builder = RegistrationRequest.ValidatedBuilder(validator)

    private val firstLastNameFormValid = MutableStateFlow(false)
    private val emailFormValid = MutableStateFlow(false)
    private val optionalFormValid = MutableStateFlow(false)

    val isRegistrationFormValid: Flow<Boolean>
        get() {
            return firstLastNameFormValid
                .combine(emailFormValid) { it, it1 -> it and it1 }
                .combine(optionalFormValid) { it, it1 -> it and it1 }
        }

    private fun validateFirstLastNameForm() {
        val isFormValid = either {
            validator.validateFirstName(builder.firstName).bind()
            validator.validateLastName(builder.lastName).bind()
        }.fold({
            false
        }, {
            true
        })
        firstLastNameFormValid.tryEmit(isFormValid)
    }

    override fun submitFirstName(firstName: String): Either<InvalidFieldError, Unit> {
        return builder.withFirstName(firstName).flatMap { validateFirstLastNameForm().right() }
    }

    override fun submitLastName(lastName: String): Either<InvalidFieldError, Unit> {
        return builder.withLastName(lastName).flatMap { validateFirstLastNameForm().right() }
    }

    override fun isFirstLastNameFormValid(): Flow<Boolean> =
        firstLastNameFormValid


    private fun validateEmailForm() {
        val isFormValid = either {
            validator.validateEmail(builder.email).bind()
            validator.validateEmailCopy(builder.emailCopy, builder.email).bind()
        }.fold({
            false
        }, {
            true
        })
        emailFormValid.tryEmit(isFormValid)
    }

    override fun submitEmail(email: String): Either<InvalidFieldError, Unit> {
        return builder.withEmail(email).flatMap { validateEmailForm().right() }
    }

    override fun submitEmailCopy(emailCopy: String): Either<InvalidFieldError, Unit> {
        return builder.withEmailCopy(emailCopy).flatMap { validateEmailForm().right() }
    }

    override fun isEmailFormValid(): Flow<Boolean> = emailFormValid

    private fun validateOptionalForm() {
        val isFormValid = either {
            validator.validateOptional0(builder.optional0).bind()
            validator.validateOptional1(builder.optional1).bind()
            validator.validateOptional2(builder.optional2).bind()
        }.fold({
            false
        }, {
            true
        })
        optionalFormValid.tryEmit(isFormValid)
    }

    override fun submitOptional0(optional0: String): Either<InvalidFieldError, Unit> {
        return builder.withOptional0(optional0).flatMap { validateOptionalForm().right() }
    }

    override fun submitOptional1(optional1: String): Either<InvalidFieldError, Unit> {
        return builder.withOptional1(optional1).flatMap { validateOptionalForm().right() }
    }

    override fun submitOptional2(optional2: String): Either<InvalidFieldError, Unit> {
        validateOptionalForm()
        return builder.withOptional2(optional2).flatMap { validateOptionalForm().right() }
    }

    override fun isOptionalFormValid(): Flow<Boolean> = optionalFormValid
}

package com.example.regsample.registration.service

import arrow.core.Either
import arrow.core.raise.either
import com.example.regsample.registration.model.EmailForm
import com.example.regsample.registration.model.FirstLastNameForm
import com.example.regsample.registration.model.InvalidFieldError
import com.example.regsample.registration.model.OptionalForm
import com.example.regsample.registration.model.RegistrationRequest
import com.example.regsample.registration.model.RegistrationRequestValidator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow


class RegistrationService(
    private val validator: RegistrationRequestValidator
) : FirstLastNameForm, EmailForm, OptionalForm {

    private val builder = RegistrationRequest.ValidatedBuilder(validator)

    private val firstLastNameFormValid = MutableStateFlow(false)
    private val emailFormValid = MutableStateFlow(false)
    private val optionalFormValid = MutableStateFlow(false)

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
        validateFirstLastNameForm()
        return builder.withFirstName(firstName).map { }
    }

    override fun submitLastName(lastName: String): Either<InvalidFieldError, Unit> {
        validateFirstLastNameForm()
        return builder.withLastName(lastName).map { }
    }

    override fun isFirstLastNameFormValid(): Flow<Boolean> =
        firstLastNameFormValid

    private fun validateEmailForm() {
        val isFormValid = either {
            validator.validateEmail(builder.email).bind()
            validator.validateEmailCopy(builder.emailCopy, builder.email)
        }.fold({
            false
        }, {
            true
        })
        emailFormValid.tryEmit(isFormValid)
    }

    override fun submitEmail(email: String): Either<InvalidFieldError, Unit> {
        validateEmailForm()
        return builder.withEmail(email).map { }
    }

    override fun submitEmailCopy(emailCopy: String): Either<InvalidFieldError, Unit> {
        validateEmailForm()
        return builder.withEmailCopy(emailCopy).map { }
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
        validateOptionalForm()
        return builder.withOptional0(optional0).map {}
    }

    override fun submitOptional1(optional1: String): Either<InvalidFieldError, Unit> {
        validateOptionalForm()
        return builder.withOptional1(optional1).map {}
    }

    override fun submitOptional2(optional2: String): Either<InvalidFieldError, Unit> {
        validateOptionalForm()
        return builder.withOptional2(optional2).map {}
    }

    override fun isOptionalFormValid(): Flow<Boolean> = isOptionalFormValid()
}

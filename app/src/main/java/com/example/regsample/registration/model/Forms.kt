package com.example.regsample.registration.model

import arrow.core.Either
import kotlinx.coroutines.flow.Flow

// TODO: Split to multiple files
interface FirstLastNameForm {
    fun submitFirstName(firstName: String): Either<InvalidFieldError, Unit>
    fun submitLastName(lastName: String): Either<InvalidFieldError, Unit>
    fun isFirstLastNameFormValid(): Flow<Boolean>
}

interface EmailForm {
    fun submitEmail(email: String): Either<InvalidFieldError, Unit>
    fun submitEmailCopy(emailCopy: String): Either<InvalidFieldError, Unit>
    fun isEmailFormValid(): Flow<Boolean>
}

interface OptionalForm {
    fun submitOptional0(optional0: String): Either<InvalidFieldError, Unit>
    fun submitOptional1(optional1: String): Either<InvalidFieldError, Unit>
    fun submitOptional2(optional2: String): Either<InvalidFieldError, Unit>
    fun isOptionalFormValid(): Flow<Boolean>
}

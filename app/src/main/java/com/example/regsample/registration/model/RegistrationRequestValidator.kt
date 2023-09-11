package com.example.regsample.registration.model

import android.util.Patterns
import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.right

class RegistrationRequestValidator {
    fun validateFirstName(firstName: String?): Either<InvalidFieldError, Unit> = either {
        ensure(firstName.orEmpty().length >= 2) {
            InvalidFieldError("First name must contain >1 symbols")
        }
    }

    fun validateLastName(lastName: String?): Either<InvalidFieldError, Unit> = either {
        ensure(lastName.orEmpty().length >= 2) {
            InvalidFieldError("Last name must contain >1 symbols")
        }
    }

    fun validateEmail(email: String?): Either<InvalidFieldError, Unit> = either {
        ensure(email?.isNotEmpty() == true) { InvalidFieldError("Email cannot be empty") }
        ensure(Patterns.EMAIL_ADDRESS.matcher(email.orEmpty()).matches()) {
            InvalidFieldError(
                "Invalid email format"
            )
        }
    }

    fun validateEmailCopy(emailCopy: String?, email: String?) = either<InvalidFieldError, Unit> {
        validateEmail(email).bind()
        validateEmail(emailCopy).bind()
        ensure(email == emailCopy) { InvalidFieldError("Emails do not match") }
    }

    fun validateOptional0(optional0: String?): Either<InvalidFieldError, Unit> = either {
        ensure(optional0.orEmpty().length >= 20) {
            InvalidFieldError("Optional0 must contain >1 symbols")
        }
    }

    fun validateOptional1(optional1: String?): Either<InvalidFieldError, Unit> = either {
        ensure(optional1.orEmpty().length >= 20) {
            InvalidFieldError("Optional1 must contain >1 symbols")
        }
    }

    fun validateOptional2(optional2: String?): Either<InvalidFieldError, Unit> = Unit.right()
}

package com.example.regsample.registration.model

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure

data class RegistrationRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val optional0: String,
    val optional1: String,
    val optional2: String?
) {
    class ValidatedBuilder(
        private val validator: RegistrationRequestValidator
    ) {
        var firstName: String? = null
            private set
        var lastName: String? = null
            private set
        var email: String? = null
            private set
        var emailCopy: String? = null
            private set
        var optional0: String? = null
            private set
        var optional1: String? = null
            private set
        var optional2: String? = null
            private set

        fun withFirstName(firstName: String?): Either<InvalidFieldError, ValidatedBuilder> =
            either {
                this@ValidatedBuilder.firstName = firstName
                validator.validateFirstName(firstName).bind()
                this@ValidatedBuilder
            }

        fun withLastName(lastName: String?): Either<InvalidFieldError, ValidatedBuilder> = either {
            this@ValidatedBuilder.lastName = lastName
            validator.validateLastName(lastName).bind()
            this@ValidatedBuilder
        }

        fun withEmail(email: String?): Either<InvalidFieldError, ValidatedBuilder> = either {
            this@ValidatedBuilder.email = email
            validator.validateEmail(email).bind()
            this@ValidatedBuilder
        }

        fun withEmailCopy(emailCopy: String?): Either<InvalidFieldError, ValidatedBuilder> =
            either {
                this@ValidatedBuilder.emailCopy = emailCopy
                validator.validateEmailCopy(emailCopy, email).bind()
                this@ValidatedBuilder
            }

        fun withOptional0(optional0: String?): Either<InvalidFieldError, ValidatedBuilder> =
            either {
                this@ValidatedBuilder.optional0 = optional0
                validator.validateOptional0(optional0).bind()
                this@ValidatedBuilder
            }

        fun withOptional1(optional1: String?): Either<InvalidFieldError, ValidatedBuilder> =
            either {
                this@ValidatedBuilder.optional1 = optional1
                validator.validateOptional1(optional1).bind()
                this@ValidatedBuilder
            }

        fun withOptional2(optional2: String?): Either<InvalidFieldError, ValidatedBuilder> =
            either {
                this@ValidatedBuilder.optional2 = optional2
                validator.validateOptional2(optional2).bind()
                this@ValidatedBuilder
            }

        fun build(): Either<List<InvalidFieldError>, RegistrationRequest> = either {
            val errors = mutableListOf<InvalidFieldError>()

            fun InvalidFieldError.appendToErrors() {
                errors.add(this)
            }

            validator.validateFirstName(firstName).leftOrNull()?.appendToErrors()
            validator.validateLastName(lastName).leftOrNull()?.appendToErrors()
            validator.validateEmail(email).leftOrNull()?.appendToErrors()
            validator.validateOptional0(optional0).leftOrNull()?.appendToErrors()
            validator.validateOptional1(optional1).leftOrNull()?.appendToErrors()
            validator.validateOptional2(optional2).leftOrNull()?.appendToErrors()
            validator.validateEmailCopy(emailCopy, email).leftOrNull()?.appendToErrors()

            ensure(errors.isEmpty()) { errors }

            RegistrationRequest(
                firstName = firstName!!,
                lastName = lastName!!,
                email = email!!,
                optional0 = optional0!!,
                optional1 = optional1!!,
                optional2 = optional2
            )
        }
    }
}

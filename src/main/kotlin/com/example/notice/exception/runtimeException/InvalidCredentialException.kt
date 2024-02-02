package com.example.notice.exception.runtimeException

data class InvalidCredentialException(
    override val message: String? = "The credential is invalid"
): RuntimeException(message)
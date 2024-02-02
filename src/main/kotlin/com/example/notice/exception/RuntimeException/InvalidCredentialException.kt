package com.example.notice.exception.RuntimeException

data class InvalidCredentialException(
    override val message: String? = "The credential is invalid"
): RuntimeException(message)
package com.example.notice.exception.runtimeException

data class DuplicateValueException(
    override val message: String
): RuntimeException(message)
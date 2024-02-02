package com.example.notice.exception.RuntimeException

data class DuplicateValueException(
    override val message: String
): RuntimeException(message)
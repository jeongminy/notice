package com.example.notice.domain.exception

data class DuplicateValueException(
    override val message: String
): RuntimeException(message)
package com.example.notice.domain.exception

data class DuplicatedPropertyException(val property: String, val value: String) :
    RuntimeException("해당 $value 은 이미 존재하는 $property 입니다.")
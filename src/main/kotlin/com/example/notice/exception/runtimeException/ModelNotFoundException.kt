package com.example.notice.exception.runtimeException

data class ModelNotFoundException(val modelName: String, val id: Long?) :
    RuntimeException("Model $modelName not found with given id: $id")
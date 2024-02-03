package com.example.notice.domain.post.model

import com.fasterxml.jackson.annotation.JsonCreator
import org.apache.commons.lang3.EnumUtils

enum class PostStatus {
    UNCOMPLETE,
    COMPLETE;

    companion object {
        @JvmStatic
        @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
        fun parse(name: String?): PostStatus? =
            name?.let { EnumUtils.getEnumIgnoreCase(PostStatus::class.java, it.trim())}
    }
}

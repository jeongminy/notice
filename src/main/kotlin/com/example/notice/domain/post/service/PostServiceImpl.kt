package com.example.notice.domain.post.service

import com.example.notice.domain.post.dto.PostResponse
import com.example.notice.domain.post.dto.toResponse
import com.example.notice.domain.post.model.toResponse
import com.example.notice.domain.post.repository.PostRepository
import org.springframework.stereotype.Service

@Service
class PostServiceImpl(
    private val postRepository: PostRepository,
): PostService {

    override fun getPosts(): List<PostResponse> {
        return postRepository.findAll().map { it.toResponse() }
    }
}
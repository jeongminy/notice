package com.example.notice.domain.post.controller

import com.example.notice.domain.post.dto.PostResponse
import com.example.notice.domain.post.service.PostService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/posts")
@RestController
class PostController(
    private val postService: PostService
) {

    @GetMapping
    fun getPosts(): ResponseEntity<List<PostResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getPosts())
    }

    @GetMapping("/{postId}")
    fun getPost(
        @PathVariable postId: Long
    ): ResponseEntity<PostResponse>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getPostById(postId))
    }
}
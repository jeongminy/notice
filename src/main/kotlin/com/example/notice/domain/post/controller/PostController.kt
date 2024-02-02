package com.example.notice.domain.post.controller

import com.example.notice.domain.post.dto.request.AddPostRequest
import com.example.notice.domain.post.dto.request.UpdatePostRequest
import com.example.notice.domain.post.dto.response.PostDetailResponse
import com.example.notice.domain.post.dto.response.PostResponse
import com.example.notice.domain.post.service.PostService
import com.example.notice.infra.security.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@Tag(name = "post", description = "글")
@RequestMapping("/api/posts")
@RestController
class PostController(
    private val postService: PostService,
) {

    @Operation(summary = "글 전체 조회")
    @GetMapping
    fun getAllPosts(): ResponseEntity<List<PostResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getAllPosts())
    }

    @Operation(summary = "글 단건 조회")
    @GetMapping("/{postId}")
    fun getPost(
        @PathVariable postId: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
    ): ResponseEntity<PostDetailResponse>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getPostById(postId, userPrincipal))
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "글 생성")
    @PostMapping
    fun addPost(
        @Valid @RequestBody addPostRequest: AddPostRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
    ): ResponseEntity<PostResponse>{
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(postService.addPost(addPostRequest, userPrincipal))
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "글 수정")
    @PutMapping("/{postId}")
    fun updatePost(
        @PathVariable postId: Long,
        @RequestBody updatePostRequest: UpdatePostRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
    ): ResponseEntity<PostResponse>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.updatePost(postId, updatePostRequest, userPrincipal))
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "글 삭제")
    @DeleteMapping("/{postId}")
    fun deletePost(
        @PathVariable postId: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
    ): ResponseEntity<Unit> {
        postService.deletePost(postId, userPrincipal)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }
}
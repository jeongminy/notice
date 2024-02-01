package com.example.notice.domain.comment.controller

import com.example.notice.domain.comment.dto.request.AddCommentRequest
import com.example.notice.domain.comment.dto.request.UpdateCommentRequest
import com.example.notice.domain.comment.dto.response.CommentResponse
import com.example.notice.domain.comment.service.CommentService
import com.example.notice.infra.security.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@Tag(name = "comment", description = "댓글")
@RequestMapping("/api/post/{postId}/comments")
@RestController
class CommentController(
    private val commentService: CommentService,
) {

    @Operation(summary = "댓글 생성")
    @PostMapping
    fun addComment(
        @PathVariable postId: Long,
        @RequestBody addCommentRequest: AddCommentRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(commentService.addComment(postId, addCommentRequest, userPrincipal))
    }

    @Operation(summary = "댓글 수정")
    @PutMapping("/{commentId}")
    fun updateComment(
        @PathVariable postId: Long, commentId: Long,
        @RequestBody updateCommentRequest: UpdateCommentRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.updateComment(postId, commentId, updateCommentRequest, userPrincipal))
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @PathVariable postId: Long, commentId: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
    ): ResponseEntity<Unit>{
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(commentService.deleteComment(postId,commentId,userPrincipal))
    }
}
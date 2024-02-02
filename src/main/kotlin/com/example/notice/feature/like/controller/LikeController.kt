package com.example.notice.feature.like.controller

import com.example.notice.feature.like.service.LikeService
import com.example.notice.infra.security.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@Tag(name = "like", description = "좋아요")
@RequestMapping("/api/{postId}/likes")
@RestController
class LikeController(
    private val likeService: LikeService,
) {

    @Operation(summary = "좋아요 추가 및 취소")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping
    fun like(
        @PathVariable postId: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
    ): ResponseEntity<String>{

        val isLiked = likeService.like(userPrincipal, postId)
        val message = if(isLiked) "좋아요를 눌렀습니다" else "좋아요가 취소되었습니다."

        return ResponseEntity.ok(message)
    }

    @Operation(summary = "좋아요 갯수 조회")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping
    fun countLikes(@PathVariable postId: Long): ResponseEntity<Int> {
        val likesCount = likeService.countLikes(postId)
        return ResponseEntity.ok(likesCount)
    }
}
package com.example.notice.domain.comment.model

import com.example.notice.domain.comment.dto.response.CommentResponse
import com.example.notice.domain.post.model.PostEntity
import com.example.notice.domain.user.model.UserEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "comment")
class CommentEntity (

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    val post: PostEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: UserEntity,

    @Column(name = "comment", nullable = false)
    var comment: String,

){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "created_at")
    val createdAt: LocalDateTime? = null

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null
}

fun CommentEntity.toResponse(): CommentResponse{
    return CommentResponse(
        nickname = user.profile.nickname,
        comment = comment,
        )
}
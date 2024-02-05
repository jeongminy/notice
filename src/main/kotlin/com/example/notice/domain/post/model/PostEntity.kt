package com.example.notice.domain.post.model

import com.example.notice.domain.comment.model.CommentEntity
import com.example.notice.domain.comment.model.toResponse
import com.example.notice.domain.post.dto.response.PostResponse
import com.example.notice.domain.user.model.UserEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "post")
class PostEntity (

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "description", nullable = false)
    var description: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    var status: PostStatus,

    @Column(name = "post_image_url", nullable = false)
    var postImageUrl: String,

    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL])
    var comments: MutableList<CommentEntity> = mutableListOf(),

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserEntity

    ){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "created_at")
    val createdAt: LocalDateTime? = LocalDateTime.now()

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = LocalDateTime.now()
}

fun PostEntity.toResponse(): PostResponse {
    return PostResponse(
        id = id!!,
        nickname = user.profile.nickname,
        title = title,
        description = description,
        status = status,
        postImageUrl = postImageUrl,
    )
}


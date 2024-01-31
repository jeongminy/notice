package com.example.notice.domain.post.model

import com.example.notice.domain.comment.model.CommentEntity
import com.example.notice.domain.user.model.UserEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "post")
class PostEntity (

    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL])
    var comments: MutableList<CommentEntity> = mutableListOf(),

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserEntity,

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "description", nullable = false)
    var description: String,

    @Column(name = "status", nullable = false)
    var status: PostStatusEntity,

    @Column(name = "post_image_url", nullable = false)
    var postImageUrl: String = "https://imgur.com/a/tBAKHUn",

){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "created_at")
    val createdAt: LocalDateTime? = null

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null
}
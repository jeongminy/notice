package com.example.notice.domain.like.model

import com.example.notice.domain.post.model.PostEntity
import com.example.notice.domain.user.model.UserEntity
import jakarta.persistence.*

@Entity
@Table(name = "likes")
data class LikeEntity(

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserEntity,

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    var post: PostEntity

){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
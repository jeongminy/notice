package com.example.notice.domain.post.service

import com.example.notice.domain.comment.model.toResponse
import com.example.notice.exception.runtimeException.InvalidCredentialException
import com.example.notice.exception.runtimeException.ModelNotFoundException
import com.example.notice.domain.post.dto.request.AddPostRequest
import com.example.notice.domain.post.dto.request.UpdatePostRequest
import com.example.notice.domain.post.dto.response.PostByIdResponse
import com.example.notice.domain.post.dto.response.PostResponse
import com.example.notice.domain.post.like.service.LikeService
import com.example.notice.domain.post.model.PostEntity
import com.example.notice.domain.post.model.PostStatus
import com.example.notice.domain.post.model.toResponse
import com.example.notice.domain.post.repository.PostRepository
import com.example.notice.domain.user.repository.UserRepository
import com.example.notice.infra.security.UserPrincipal
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class PostServiceImpl(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val likeService: LikeService,
): PostService {

    override fun getAllPosts(): List<PostResponse> {
        return postRepository.findAll().map { it.toResponse() }
    }

    override fun getPostById(postId: Long, userPrincipal:UserPrincipal): PostByIdResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("PostEntity", postId)
        val countLikes = likeService.countLikes(postId)
        val isLiked = likeService.isLiked(userPrincipal.id, postId)

        return PostByIdResponse(
            id = post.id!!,
            nickname = post.user.profile.nickname,
            title = post.title,
            description = post.description,
            status = post.status,
            postImageUrl = post.postImageUrl,
            likeCount = countLikes,
            likedByCurrentUser = isLiked,
            createdAt = post.createdAt!!,
            updatedAt = post.updatedAt!!,
            comments = post.comments.map { it.toResponse() }
        )
    }

    override fun addPost(
        request: AddPostRequest,
        userPrincipal: UserPrincipal
    ): PostResponse {
        val user = userRepository.findByIdOrNull(userPrincipal.id) ?: throw ModelNotFoundException("UserEntity", userPrincipal.id)

        return postRepository.save(
            PostEntity(
                title = request.title,
                description = request.description,
                status = PostStatus.UNCOMPLETE,
                postImageUrl = request.postImageUrl,
                user = user
            )
        ).toResponse()
    }

    override fun updatePost(
        postId: Long,
        request: UpdatePostRequest,
        userPrincipal: UserPrincipal
    ): PostResponse {
        val post = postRepository.findByIdOrNull(postId)
            ?: throw ModelNotFoundException("PostEntity", postId)

        //ADMIN이거나 본인인 경우에만 수정 가능하도록 확인
        if ((userPrincipal.id != post.user.id) && (userPrincipal.authorities.first().toString() == "ROLE_USER"))
            throw InvalidCredentialException("본인의 글이 아니므로 수정할 권한이 없습니다.")

            post.title = request.title
            post.description = request.description
            post.status = request.status
            post.postImageUrl = request.postImageUrl
            post.updatedAt = LocalDateTime.now()

        return post.toResponse()
    }

    override fun deletePost(
        postId: Long,
        userPrincipal: UserPrincipal) {
        val post = postRepository.findByIdOrNull(postId)
            ?: throw ModelNotFoundException("PostEntity", postId)

        //ADMIN이거나 본인인 경우에만 삭제 가능하도록 확인
        if ((userPrincipal.id != post.user.id) && (userPrincipal.authorities.first().toString() == "ROLE_USER"))
            throw InvalidCredentialException("본인의 글이 아니므로 삭제할 권한이 없습니다.")

        postRepository.delete(post)
    }
}
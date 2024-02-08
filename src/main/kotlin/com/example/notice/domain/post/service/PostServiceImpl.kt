package com.example.notice.domain.post.service

import com.example.notice.domain.comment.model.toResponse
import com.example.notice.exception.runtimeException.InvalidCredentialException
import com.example.notice.exception.runtimeException.ModelNotFoundException
import com.example.notice.domain.post.dto.request.AddPostRequest
import com.example.notice.domain.post.dto.request.UpdatePostRequest
import com.example.notice.domain.post.dto.response.PostByIdResponse
import com.example.notice.domain.post.dto.response.PostResponse
import com.example.notice.domain.post.model.PostEntity
import com.example.notice.domain.post.model.PostStatus
import com.example.notice.domain.post.model.QPostEntity.postEntity
import com.example.notice.domain.post.model.toResponse
import com.example.notice.domain.post.repository.PostRepository
import com.example.notice.domain.user.repository.UserRepository
import com.example.notice.feature.like.service.LikeService
import com.example.notice.infra.security.UserPrincipal
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime


@Service
class PostServiceImpl(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val likeService: LikeService,
): PostService {

    override fun getPostList(): List<PostResponse> {
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
            createdAt = post.createdAt,
            updatedAt = post.updatedAt,
            comments = post.comments.map { it.toResponse() }
        )
    }

    @Transactional
    override fun addPost(
        image: String,
        request: AddPostRequest,
        userPrincipal: UserPrincipal
    ): PostResponse {
        val user = userRepository.findByIdOrNull(userPrincipal.id) ?: throw ModelNotFoundException("UserEntity", userPrincipal.id)

        return postRepository.save(
            PostEntity(
                title = request.title,
                description = request.description,
                status = PostStatus.UNCOMPLETE,
                postImageUrl = image,
                user = user
            )
        ).toResponse()
    }

    @Transactional
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

    @Transactional
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

    //글 목록 페이지네이션 - 페이징 + 커스텀 정렬 (id, title, nickname, description), N일전 게시글 조회
    override fun getPostListPaginated(pageable: Pageable, status: String?, daysAgo: Long?): Page<PostResponse> {
        val postStatus = when (status) {
            "UNCOMPLETE" -> PostStatus.UNCOMPLETE
            "COMPLETE" -> PostStatus.COMPLETE
            null -> null
            else -> throw IllegalArgumentException("The status is invalid")
        }

        return postRepository.findByPageableAndStatus(pageable, postStatus, daysAgo).map { it.toResponse() }
    }

    //글 목록 조회 - 제목 기준
    override fun getPostListByTitle(title: String): List<PostResponse> {
        return postRepository.searchPostListByTitle(title).map { it.toResponse() }
    }

    //글 목록 조회 - 생성날짜 기준
    override fun getPostListByCreatedAt(): List<PostResponse> {
        return postRepository.searchPostListByCreatedAt().map { it.toResponse() }
    }
}
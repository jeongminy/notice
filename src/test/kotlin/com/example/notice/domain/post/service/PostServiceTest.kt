package com.example.notice.domain.post.service

import com.example.notice.domain.like.service.LikeServiceImpl
import com.example.notice.domain.post.model.PostEntity
import com.example.notice.domain.post.model.PostStatus
import com.example.notice.domain.post.repository.PostRepository
import com.example.notice.domain.user.model.UserEntity
import com.example.notice.domain.user.repository.UserRepository
import com.example.notice.exception.runtimeException.ModelNotFoundException
import com.example.notice.infra.security.UserPrincipal
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import org.springframework.data.repository.findByIdOrNull

class PostServiceTest: DescribeSpec({

    describe("getPost(postId) 요청을 했을 때"){
        val userPrincipal = UserPrincipal(2, "test@email.com", setOf("USER"))
        val postRepository = mockk<PostRepository>()
        val userRepository = mockk<UserRepository>()
        val likeService = mockk<LikeServiceImpl>()
        val postService = PostServiceImpl(
            postRepository = postRepository,
            userRepository = userRepository,
            likeService = likeService
        )

        context("특정 postId 가 존재하면") {

            val userEntity = mockk<UserEntity>()
            val postId = 2L
            val userId = 2L
            val postEntity = spyk<PostEntity>(
                PostEntity(
                    title = "제목",
                    description = "설명",
                    status = PostStatus.UNCOMPLETE,
                    postImageUrl = "http://image.com",
                    comments = mutableListOf(),
                    user = userEntity
                )
            )

            every { userEntity.profile.nickname } returns "test"
            every { postEntity.id } returns postId
            every { postRepository.findByIdOrNull(postId) } returns postEntity
            every { likeService.countLikes(postId) } returns 1
            every { likeService.isLiked(userId,postId)} returns false

                it("PostId는 ${postId}가 있어야한다.") {
                val result = postService.getPostById(postId, userPrincipal)

                result.title shouldBe "제목"
            }
        }
        context("특정 postId 가 존재하지 않다면"){
            val postId = 1L

            every { postRepository.findByIdOrNull(any()) } returns null

            it("ModelNotFoundException 이 발생되어야 한다."){
                val exception = shouldThrow<ModelNotFoundException> { postService.getPostById(postId, userPrincipal) }

                exception.modelName shouldBe "PostEntity"
            }
        }
    }
})
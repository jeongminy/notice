package com.example.notice.domain.post.controller

import com.example.notice.domain.post.dto.response.PostByIdResponse
import com.example.notice.domain.post.model.PostStatus
import com.example.notice.domain.post.service.PostService
import com.example.notice.domain.post.service.PostServiceImpl
import com.example.notice.infra.security.jwt.JwtPlugin
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import org.json.JSONObject
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.time.LocalDateTime

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockKExtension::class)
class PostControllerTest @Autowired constructor(
    private val mockMvc: MockMvc, private val jwtPlugin: JwtPlugin,
) : DescribeSpec(){

    @MockkBean
    private lateinit var postService: PostServiceImpl

    init {
        extension(SpringExtension)

        afterContainer {
            clearAllMocks()
        }

        describe("GET /courses/{id}는") {
            context("존재하는 ID를 요청을 보낼 때") {
                it("200 status code를 응답해야한다.") {
                    val postId = 2L

                    // Mock의 동작 정의
                    every { postService.getPostById(any(),any()) } returns PostByIdResponse(
                        id = postId,
                        title = "test_title",
                        description = "abc",
                        status = PostStatus.UNCOMPLETE,
                        nickname = "nickname",
                        postImageUrl = "https://img.com",
                        likeCount = 1,
                        likedByCurrentUser = false,
                        createdAt = LocalDateTime.now(),
                        updatedAt = LocalDateTime.now(),
                        comments = mutableListOf()
                    )

                    // AccessToken 생성
                    val jwtToken = jwtPlugin.generateAccessToken(
                        subject = "2",
                        email = "test2@gmail.com",
                        role = "USER"
                    )

                    // 요청 수행
                    val result = mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/posts/$postId")
                            .header("Authorization", "Bearer $jwtToken")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andReturn()

                    // status code 확인
                    result.response.status shouldBe 200

                    val content = JSONObject(result.response.contentAsString)

                    // title 동일한지 확인
                    content.get("title") shouldBe "test_title"
                }
            }
        }
    }
}
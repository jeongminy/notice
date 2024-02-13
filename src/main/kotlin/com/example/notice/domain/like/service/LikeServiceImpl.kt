package com.example.notice.domain.like.service

import com.example.notice.domain.like.model.LikeEntity
import com.example.notice.domain.like.repository.LikeRepository
import com.example.notice.exception.runtimeException.ModelNotFoundException
import com.example.notice.exception.runtimeException.UnauthorizedOperationException
import com.example.notice.domain.post.repository.PostRepository
import com.example.notice.domain.user.repository.UserRepository
import com.example.notice.infra.security.UserPrincipal
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LikeServiceImpl(
    private val likeRepository: LikeRepository,
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
): LikeService {

    @Transactional
    override fun like(userPrincipal: UserPrincipal, postId: Long): Boolean {

        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("PostEntity", postId)
        val user = userRepository.findByIdOrNull(userPrincipal.id) ?: throw ModelNotFoundException("UserEntity", userPrincipal.id)

        //좋아요 본인만 추가 할 수 있음
        if(post.user.id == userPrincipal.id) {
            throw UnauthorizedOperationException("자신의 글에는 좋아요를 누를 수 없습니다.")
        }

        //이미 좋아요를 눌렀었는지 확인하고, 이미 눌렀었다면 삭제
        val confirmLike = likeRepository.existsByUserIdAndPostId(userPrincipal.id, postId)
        if (confirmLike) {
            likeRepository.deleteByUserIdAndPostId(userPrincipal.id, postId)
            return false
        }

        //좋아요 추가
        val addLike = LikeEntity(user, post)

        likeRepository.save(addLike)

        return true


    }

    override fun countLikes(postId: Long): Int { // 숫자
        return likeRepository.countByPostId(postId)
    }

    override fun isLiked(userId: Long, postId: Long): Boolean{
        return likeRepository.existsByUserIdAndPostId(userId, postId)
    }
}
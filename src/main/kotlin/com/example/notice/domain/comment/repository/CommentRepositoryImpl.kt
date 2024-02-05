package com.example.notice.domain.comment.repository

import com.example.notice.domain.comment.model.CommentEntity
import com.example.notice.domain.comment.model.QCommentEntity
import com.example.notice.infra.querydsl.QueryDslSupport
import com.querydsl.core.BooleanBuilder
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class CommentRepositoryImpl: QueryDslSupport(), CustomCommentRepository {
    private val comment = QCommentEntity.commentEntity

    override fun findByPageable(pageable: Pageable): Page<CommentEntity> {
        val whereClause = BooleanBuilder()

        val totalCount = queryFactory.select(comment.count()).from(comment).where(whereClause).fetchOne() ?: 0L

        val query = queryFactory.selectFrom(comment)
            .where(whereClause)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())

        if (pageable.sort.isSorted) {
            when (pageable.sort.first()?.property) {
                "id" -> query.orderBy(comment.id.asc())
                "nickname" -> query.orderBy(comment.user.profile.nickname.asc())
                "comment" -> query.orderBy(comment.comment.asc())
                "createdAt" -> query.orderBy(comment.createdAt.asc())
                else -> query.orderBy(comment.id.asc())
            }
        }

        val contents = query.fetch()

        return PageImpl(contents, pageable, totalCount)

    }
}

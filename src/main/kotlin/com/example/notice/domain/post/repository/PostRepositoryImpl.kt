package com.example.notice.domain.post.repository

import com.example.notice.domain.post.model.PostEntity
import com.example.notice.domain.post.model.PostStatus
import com.example.notice.domain.post.model.QPostEntity
import com.example.notice.infra.querydsl.QueryDslSupport
import com.querydsl.core.BooleanBuilder
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class PostRepositoryImpl: QueryDslSupport(), CustomPostRepository {
    private val post= QPostEntity.postEntity

    override fun searchPostListByTitle(title: String): List<PostEntity> {
        return queryFactory.selectFrom(post)
            .where(post.title.containsIgnoreCase(title))
            .fetch()
    }

    override fun findByPageableAndStatus(pageable: Pageable, postStatus: PostStatus?): Page<PostEntity> {
        val whereClause = BooleanBuilder()
        postStatus?.let { whereClause.and(post.status.eq(postStatus)) }

        val totalCount = queryFactory.select(post.count()).from(post).where(whereClause).fetchOne() ?: 0L

        val query = queryFactory.selectFrom(post)
            .where(whereClause)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())

        if (pageable.sort.isSorted) {
            when (pageable.sort.first()?.property) {
                "id" -> query.orderBy(post.id.asc())
                "title" -> query.orderBy(post.title.asc())
                else -> query.orderBy(post.id.asc())
            }
        }

        val contents = query.fetch()

        return PageImpl(contents, pageable, totalCount)

    }
}

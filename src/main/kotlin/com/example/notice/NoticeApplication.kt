package com.example.notice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy

@EnableAspectJAutoProxy
@SpringBootApplication
class NoticeApplication

fun main(args: Array<String>) {
	runApplication<NoticeApplication>(*args)
}

package com.example.notice.feature.image.controller

import com.example.notice.feature.image.dto.UploadImageResponse
import com.example.notice.infra.aws.S3Service
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@Tag(name = "image", description = "이미지")
@RequestMapping("/api/images")
@RestController
class ImageController(
    private val s3Service: S3Service
) {
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun uploadImage(@RequestParam("image") multipartFile: MultipartFile): ResponseEntity<UploadImageResponse> {
        return ResponseEntity
            .ok(UploadImageResponse(url=s3Service.upload(multipartFile)))

    }
}
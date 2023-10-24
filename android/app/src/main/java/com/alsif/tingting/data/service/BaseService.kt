package com.alsif.tingting.data.service

import com.alsif.tingting.data.model.CommentDto
import retrofit2.Response
import retrofit2.http.*


interface BaseService {
    // TODO 서비스 로직
    @GET("comments")
    suspend fun getComments(): Response<List<CommentDto>>
}
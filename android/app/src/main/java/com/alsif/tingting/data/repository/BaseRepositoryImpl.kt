package com.alsif.tingting.data.repository


import com.alsif.tingting.data.model.CommentDto
import com.alsif.tingting.data.service.BaseService
import com.alsif.tingting.util.handleApi
import javax.inject.Inject

class BaseRepositoryImpl @Inject constructor(
    private val baseService: BaseService
) : BaseRepository {
    // TODO 구현할 메소드
    override suspend fun getCommentList(): List<CommentDto> {
        return handleApi { baseService.getComments() }
    }
}
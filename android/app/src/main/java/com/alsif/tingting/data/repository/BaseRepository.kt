package com.alsif.tingting.data.repository

import com.alsif.tingting.data.model.CommentDto

interface BaseRepository {
    // TODO 구현해야할 메소드
    suspend fun getCommentList(): List<CommentDto>
}
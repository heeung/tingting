package com.alsif.tingting.data.repository

import com.alsif.tingting.data.model.ConcertHallInfoDto
import com.alsif.tingting.data.model.SeatSelectionDto
import com.alsif.tingting.data.model.response.DeleteTicketResponseDto
import com.alsif.tingting.data.model.response.ReservedListResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReserveRepository {
    suspend fun getReservedTicketList(userSeq: Int, currentPage: Int, itemCount: Int): ReservedListResponseDto

    suspend fun getConcertHallInfo(concertSeq: Int): ConcertHallInfoDto

    suspend fun deleteTicket(ticketSeq: Int, userSeq: Int): DeleteTicketResponseDto

    suspend fun getSectionSeatList(concertDetailSeq: Int, concertHallSeq: Int, target: String) : List<SeatSelectionDto>
}
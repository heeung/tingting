package com.alsif.tingtinqg.data.service

import com.alsif.tingting.data.model.ConcertHallInfoDto
import com.alsif.tingting.data.model.SeatSelectionDto
import com.alsif.tingting.data.model.request.ReserveRequestDto
import com.alsif.tingting.data.model.response.DeleteTicketResponseDto
import com.alsif.tingting.data.model.response.MessageResponseDto
import com.alsif.tingting.data.model.response.ReservedListResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import java.nio.channels.SelectionKey

interface ReserveService {
    @GET("users/{userSeq}/ticket")
    suspend fun getReservedTicketList(
        @Path("userSeq") userSeq: Int,
        @Query("currentPage") currentPage: Int,
        @Query("itemCount") itemCount: Int
    ): Response<ReservedListResponseDto>

    @GET("book/{concertSeq}")
    suspend fun getConcertHallInfo(
        @Path("concertSeq") concertSeq: Int
    ): Response<ConcertHallInfoDto>

    @DELETE("book/{ticketSeq}")
    suspend fun deleteTicket(
        @Path("ticketSeq") ticketSeq: Int,
        @Query("userSeq") userSeq: Int
    ): Response<DeleteTicketResponseDto>

    @GET("book/{concertDetailSeq}/section")
    suspend fun getSectionSeatList(
        @Path("concertDetailSeq") concertDetailSeq: Int,
        @Query("concertHallSeq") concertHallSeq: Int,
        @Query("target") target: String
    ) : Response<List<SeatSelectionDto>>

    @GET("book/{concertDetailSeq}/seat")
    suspend fun getIsPossibleSeat(
        @Path("concertDetailSeq") concertDetailSeq: Int,
        @Query("seatSeqs") seatSeqs: List<Long>
//        @QueryMap()
    ): Response<MessageResponseDto>

    @POST("book/{concertDetailSeq}/seat")
    suspend fun postReservation(
        @Path("concertDetailSeq") concertDetailSeq: Int,
        @Query("userSeq") userSeq: Int,
        @Query("section") section: String,
        @Body seatSeqs: ReserveRequestDto
    ): Response<MessageResponseDto>
}
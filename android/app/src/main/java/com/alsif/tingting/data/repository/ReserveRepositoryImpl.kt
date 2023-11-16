package com.alsif.tingting.data.repository

import com.alsif.tingting.data.model.ConcertHallInfoDto
import com.alsif.tingting.data.model.SeatSelectionDto
import com.alsif.tingting.data.model.request.ReserveRequestDto
import com.alsif.tingting.data.model.response.DeleteTicketResponseDto
import com.alsif.tingting.data.model.response.MessageResponseDto
import com.alsif.tingting.data.model.response.ReservedListResponseDto
import com.alsif.tingting.util.handleApi
import com.alsif.tingtinqg.data.service.ReserveService
import javax.inject.Inject

class ReserveRepositoryImpl @Inject constructor(
    private val reserveService: ReserveService
): ReserveRepository {
    /**
     * GET 예매 리스트
     */
    override suspend fun getReservedTicketList(
        userSeq: Int,
        currentPage: Int,
        itemCount: Int
    ): ReservedListResponseDto {
        return handleApi { reserveService.getReservedTicketList(userSeq, currentPage, itemCount) }
    }

    override suspend fun getConcertHallInfo(concertSeq: Int): ConcertHallInfoDto {
        return handleApi { reserveService.getConcertHallInfo(concertSeq) }
    }

    override suspend fun deleteTicket(ticketSeq: Int, userSeq: Int): DeleteTicketResponseDto {
        return handleApi { reserveService.deleteTicket(ticketSeq, userSeq) }
    }

    override suspend fun getSectionSeatList(
        concertDetailSeq: Int,
        concertHallSeq: Int,
        target: String
    ): List<SeatSelectionDto> {
        return handleApi { reserveService.getSectionSeatList(concertDetailSeq, concertHallSeq, target) }
    }

    override suspend fun getIsPossibleSeat(
        concertDetailSeq: Int,
        seatSeqs: List<Long>
    ): MessageResponseDto {
        return handleApi { reserveService.getIsPossibleSeat(concertDetailSeq, seatSeqs) }
    }

    override suspend fun postReservation(
        concertDetailSeq: Int,
        userSeq: Int,
        section: String,
        seatSeqs: ReserveRequestDto
    ): MessageResponseDto {
        return handleApi { reserveService.postReservation(concertDetailSeq, userSeq, section, seatSeqs) }
    }

}
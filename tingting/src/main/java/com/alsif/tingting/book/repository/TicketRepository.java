package com.alsif.tingting.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alsif.tingting.book.entity.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}

package com.alsif.book.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alsif.book.book.entity.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}

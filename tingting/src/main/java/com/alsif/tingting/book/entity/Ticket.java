package com.alsif.tingting.book.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.alsif.tingting.concert.entity.ConcertDetail;
import com.alsif.tingting.global.entity.BaseEntity;
import com.alsif.tingting.user.entity.Point;
import com.alsif.tingting.user.entity.User;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ticket")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ticket extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_seq", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "concert_detail_seq", nullable = false)
	private ConcertDetail concertDetail;

	private LocalDateTime deletedDate;

	@OneToMany(mappedBy = "ticket")
	private final List<Point> points = new ArrayList<>();

	@OneToMany(mappedBy = "ticket")
	private final List<TicketSeat> ticketSeats = new ArrayList<>();

	@Builder
	public Ticket(User user, ConcertDetail concertDetail) {
		this.user = user;
		this.concertDetail = concertDetail;
	}

	public void updateDeletedDate(LocalDateTime date) {
		this.deletedDate = date;
	}

	public void setSeq(long seq){
		this.seq = seq;
	}

	public void addPoint(Point point){
		this.points.add(point);
	}

	public void addTicketSeats(TicketSeat ticketSeat){
		this.ticketSeats.add(ticketSeat);
	}

	@Override
	public String toString() {
		return "Ticket {"
			+ "seq=" + seq
			+ ", user=" + user
			+ ", concertDetail=" + concertDetail
			+ ", deletedDate=" + deletedDate
			+ '}';
	}
}

package com.alsif.tingting.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.alsif.tingting.book.entity.Ticket;
import com.alsif.tingting.global.entity.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "point")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Point extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_seq", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ticket_seq")
	private Ticket ticket;

	@Column(nullable = false)
	private Long pay;

	@Column(nullable = false)
	private Long total;

	@Builder
	public Point(User user, Ticket ticket, Long pay, Long total) {
		this.user = user;
		this.ticket = ticket;
		this.pay = pay;
		this.total = total;
	}

	public void setTicket(Ticket ticket) {
		if (this.ticket != null) {
			this.ticket.getPoints().remove(this);
		}

		this.ticket = ticket;
		ticket.getPoints().add(this);
	}

	@Override
	public String toString() {
		return "Point {"
			+ "seq=" + seq
			+ ", user=" + user
			+ ", ticket=" + ticket
			+ ", change=" + pay
			+ ", total=" + total
			+ '}';
	}
}

package com.alsif.book.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.alsif.book.book.entity.Ticket;
import com.alsif.book.global.entity.BaseEntity;

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
	private Integer seq;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_seq", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ticket_seq")
	private Ticket ticket;

	@Column(nullable = false)
	private Integer pay;

	@Column(nullable = false)
	private Integer total;

	@Builder
	public Point(User user, Ticket ticket, Integer pay, Integer total) {
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

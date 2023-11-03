package com.alsif.tingting.user.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.alsif.tingting.concert.entity.Concert;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_concert")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserConcert {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_seq", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "concert_seq", nullable = false)
	private Concert concert;

	@Builder
	public UserConcert(User user, Concert concert) {
		this.user = user;
		this.concert = concert;
	}

	@Override
	public String toString() {
		return "UserConcert {"
			+ "seq=" + seq
			+ ", user=" + user
			+ ", concert=" + concert
			+ '}';
	}
}

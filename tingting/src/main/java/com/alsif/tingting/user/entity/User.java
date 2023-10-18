package com.alsif.tingting.user.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private Long money;

	private LocalDateTime deletedDate;

	@Builder
	public User(String email, Long money) {
		this.email = email;
		this.money = money;
	}

	public void updateDeletedDate(LocalDateTime date) {
		this.deletedDate = date;
	}

	@Override
	public String toString() {
		return "User {"
			+ "seq=" + seq
			+ ", email=" + email
			+ ", money=" + money
			+ ", deletedDate=" + deletedDate
			+ "}";
	}
}

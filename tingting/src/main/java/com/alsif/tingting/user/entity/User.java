package com.alsif.tingting.user.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alsif.tingting.global.entity.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer seq;

	@Column(unique = true, nullable = false)
	private String email;

	private LocalDateTime deletedDate;

	@Builder
	public User(String email) {
		this.email = email;
	}

	public static User seqOf(Integer seq) {
		return new User(seq);
	}

	private User(Integer seq) {
		this.seq = seq;
	}

	public void updateDeletedDate(LocalDateTime date) {
		this.deletedDate = date;
	}

	@Override
	public String toString() {
		return "User {"
			+ "seq=" + seq
			+ ", email='" + email + '\''
			+ ", deletedDate=" + deletedDate
			+ '}';
	}
}

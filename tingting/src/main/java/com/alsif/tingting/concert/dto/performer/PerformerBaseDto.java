package com.alsif.tingting.concert.dto.performer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Schema(description = "가수 정보")
public class PerformerBaseDto {

	@Schema(description = "가수 PK", type = "Long", example = "1")
	private Long seq;

	@Schema(description = "가수 이름", type = "String", example = "임영웅")
	private String performerName;

	@Schema(description = "가수 이미지", type = "String", example = "https://i.namu.wiki/i/BlV61WGRgdyAtbLrT5h8t38I5FOV0tJgQJJv0Lcsb5-DcpffJ0-kraVsKe2NPAhCTL2tYJT5NZ3RAh0oFvMOi-0p9UaZVh_ED8HCAAnjp_NvtiH0S0a4tcUuFcd1b6WwMQ0Sc7nQu7Y1Ynb8lOY5qw.webp")
	private String performerImageUrl;
}

package com.alsif.tingting.global.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "에러 정보")
public class ErrorResponseDto {

	@Schema(description = "에러 메시지", type = "String", example = "Error Message")
	private String message;
}

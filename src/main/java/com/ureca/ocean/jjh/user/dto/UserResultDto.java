package com.ureca.ocean.jjh.user.dto;

import lombok.Data;

import java.util.List;

@Data
//@Builder 없다.
public class UserResultDto {
	private String result;
	private UserResponseDto userDto;
	private List<UserResponseDto> userList;
}

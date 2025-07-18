package com.ureca.ocean.jjh.user.dto.request;

import com.ureca.ocean.jjh.user.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    private String nickname;     // nullable = false
    private String address;
    private String password;     // nullable = false
}

package com.test.Member.dto;

import com.test.Member.entity.DisabilityType;
import com.test.Member.entity.Gender;
import com.test.Member.entity.Role;

public record MemberResDto(
        Long memberId,
        String name,
        String nickName,
        String profileUrlImage,
        String email,
        Integer age,
        Gender gender,
        Role role,
        DisabilityType disabilityType
)
{

}

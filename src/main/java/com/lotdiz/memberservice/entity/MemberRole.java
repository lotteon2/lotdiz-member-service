package com.lotdiz.memberservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum MemberRole {
    USER,
    MANAGER,
    ADMIN
}

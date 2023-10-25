package com.lotdiz.memberservice.entity;

import lombok.Getter;

@Getter
public enum MemberRole {
  ROLE_USER("ROLE_USER"),
  ROLE_MANAGER("ROLE_MANAGER"),
  ROLE_ADMIN("ROLE_ADMIN");

  private final String value;

  private MemberRole(String value) {
    this.value = value;
  }
}

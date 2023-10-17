package com.lotdiz.memberservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class ResultDataResponse<T> {
  private final String code;
  private final String message;
  private final String detail;
  private final T data;
}

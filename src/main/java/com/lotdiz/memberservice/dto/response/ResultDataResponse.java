package com.lotdiz.memberservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@Builder
public class ResultDataResponse<T> {
  private final String code;
  private final String message;
  private final String detail;

  @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
  private final T data;
}

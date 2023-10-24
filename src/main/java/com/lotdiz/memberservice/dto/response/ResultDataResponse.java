package com.lotdiz.memberservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
public class ResultDataResponse<T> {
  private String code;
  private String message;
  private String detail;

//  @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
  private T data;
}

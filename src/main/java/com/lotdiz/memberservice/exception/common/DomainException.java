package com.lotdiz.memberservice.exception.common;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public abstract class DomainException extends RuntimeException {
  private final Map<String, String> validation = new HashMap<>();

  public DomainException(String msg) {
    super(msg);
  }

  public abstract int getStatusCode();

  public void addValidation(String fieldName, String errorMessage) {
    validation.put(fieldName, errorMessage);
  }
}

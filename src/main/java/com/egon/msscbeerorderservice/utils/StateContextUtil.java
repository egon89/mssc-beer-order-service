package com.egon.msscbeerorderservice.utils;

import lombok.experimental.UtilityClass;
import org.springframework.statemachine.StateContext;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

@UtilityClass
public class StateContextUtil {

  @SuppressWarnings("unchecked")
  public <T> T getValueFromMessageHeader(StateContext<?, ?> stateContext, String headerName) {
    final var value = stateContext.getMessageHeader(
        Optional.ofNullable(headerName).orElseThrow(() -> new IllegalArgumentException("The header name is required")));
    requireNonNull(value, "%s header not found".formatted(headerName));

    return (T) value;
  }
}

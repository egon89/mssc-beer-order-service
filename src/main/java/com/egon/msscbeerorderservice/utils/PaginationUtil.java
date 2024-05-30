package com.egon.msscbeerorderservice.utils;

import lombok.experimental.UtilityClass;

import java.util.Objects;

@UtilityClass
public class PaginationUtil {
  public static final String DEFAULT_PAGE_NUMBER = "0";
  public static final String DEFAULT_PAGE_SIZE = "25";

  public void validateRequestParams(Integer pageNumber, Integer pageSize) {
    if (Objects.nonNull(pageNumber) && pageNumber >= 0) return;
    if (Objects.nonNull(pageSize) && pageSize >= 0) return;

    throw new IllegalArgumentException("Invalid request parameters");
  }
}

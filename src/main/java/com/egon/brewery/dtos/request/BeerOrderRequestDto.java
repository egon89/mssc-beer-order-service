package com.egon.brewery.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerOrderRequestDto {
  private String customerRef;
  private String orderStatusCallbackUrl;
  private List<BeerOrderLineRequestDto> beerOrderLines;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class BeerOrderLineRequestDto {
    private String upc;
    private String beerName;
    private UUID beerId;
    private int orderQuantity;
  }
}

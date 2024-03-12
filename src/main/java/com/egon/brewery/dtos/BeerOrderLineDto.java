package com.egon.brewery.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BeerOrderLineDto extends BaseDto {

  @Builder
  public BeerOrderLineDto(
      UUID id, Integer version, OffsetDateTime createdAt, OffsetDateTime updatedAt, String upc, String beerName,
      String beerStyle, UUID beerId, int orderQuantity, BigDecimal price) {
    super(id, version, createdAt, updatedAt);
    this.upc = upc;
    this.beerName = beerName;
    this.beerId = beerId;
    this.orderQuantity = orderQuantity;
  }

  private String upc;
  private String beerName;
  private String beerStyle;
  private UUID beerId;
  private int orderQuantity;
  private BigDecimal price;
}

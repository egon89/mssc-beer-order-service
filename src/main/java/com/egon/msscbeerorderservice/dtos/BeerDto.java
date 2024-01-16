package com.egon.msscbeerorderservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class BeerDto {

  private UUID id;

  private String name;

  private String style;

  private BigDecimal price;

  private String upc;

  private Integer quantityOnHand;

  private OffsetDateTime createdAt;

  private OffsetDateTime updatedAt;

  private Long version;
}

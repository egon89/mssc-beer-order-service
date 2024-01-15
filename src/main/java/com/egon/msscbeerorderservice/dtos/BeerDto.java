package com.egon.msscbeerorderservice.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
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

  @JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ssZ", shape=JsonFormat.Shape.STRING)
  private OffsetDateTime createdAt;

  @JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ssZ", shape=JsonFormat.Shape.STRING)
  private OffsetDateTime updatedAt;

  private Long version;
}

package com.egon.msscbeerorderservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseDto {
  private UUID id;

  private Integer version;

  private OffsetDateTime createdAt;

  private OffsetDateTime updatedAt;
}

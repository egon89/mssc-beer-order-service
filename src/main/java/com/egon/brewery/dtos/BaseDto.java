package com.egon.brewery.dtos;

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
  protected UUID id;

  protected Integer version;

  protected OffsetDateTime createdAt;

  protected OffsetDateTime updatedAt;
}

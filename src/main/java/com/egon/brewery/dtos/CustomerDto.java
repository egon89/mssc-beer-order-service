package com.egon.brewery.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerDto extends BaseDto {

  @Builder
  public CustomerDto(UUID id, Integer version, OffsetDateTime createdAt, OffsetDateTime updatedAt, String name) {
    super(id, version, createdAt, updatedAt);
    this.name = name;
  }

  private String name;
}

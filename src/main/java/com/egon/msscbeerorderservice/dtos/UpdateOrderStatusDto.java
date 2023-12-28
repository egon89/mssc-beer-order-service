package com.egon.msscbeerorderservice.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UpdateOrderStatusDto extends BaseDto {

  @Builder
  public UpdateOrderStatusDto(
      UUID id, Integer version, OffsetDateTime createdAt, OffsetDateTime updatedAt, UUID orderId, String customerRef,
      String orderStatus) {
    super(id, version, createdAt, updatedAt);
    this.orderId = orderId;
    this.customerRef = customerRef;
    this.orderStatus = orderStatus;
  }

  private UUID orderId;
  private String customerRef;
  private String orderStatus;
}

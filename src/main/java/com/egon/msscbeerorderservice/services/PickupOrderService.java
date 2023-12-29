package com.egon.msscbeerorderservice.services;

import java.util.UUID;

public interface PickupOrderService {
  void execute(UUID customerId, UUID orderId);
}

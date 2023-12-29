package com.egon.msscbeerorderservice.controllers;

import com.egon.msscbeerorderservice.services.PickupOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/customers/{customerId}")
@RequiredArgsConstructor
public class PickupBeerOrderController {

  private final PickupOrderService pickupOrderService;

  @PutMapping("/orders/{orderId}/pickup")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void pickupBeerOrder(@PathVariable UUID customerId, @PathVariable UUID orderId) {
    pickupOrderService.execute(customerId, orderId);
  }
}

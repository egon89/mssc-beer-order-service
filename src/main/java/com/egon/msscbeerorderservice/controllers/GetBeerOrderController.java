package com.egon.msscbeerorderservice.controllers;

import com.egon.brewery.dtos.BeerOrderDto;
import com.egon.msscbeerorderservice.services.GetBeerOrderByIdService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/customers/{customerId}")
@RequiredArgsConstructor
public class GetBeerOrderController {

  private final GetBeerOrderByIdService getBeerOrderByIdService;

  @GetMapping("/orders/{orderId}")
  public ResponseEntity<BeerOrderDto> getBeerOrder(@PathVariable UUID customerId, @PathVariable UUID orderId) {
    return ResponseEntity.ok(getBeerOrderByIdService.execute(customerId, orderId));
  }
}

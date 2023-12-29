package com.egon.msscbeerorderservice.controllers;

import com.egon.msscbeerorderservice.dtos.BeerOrderDto;
import com.egon.msscbeerorderservice.dtos.request.BeerOrderRequestDto;
import com.egon.msscbeerorderservice.mappers.BeerOrderMapper;
import com.egon.msscbeerorderservice.services.PlaceOrderBeerOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/customers/{customerId}")
@RequiredArgsConstructor
public class PlaceBeerOrderController {

  private final PlaceOrderBeerOrderService placeBeerOrderService;
  private final BeerOrderMapper beerOrderMapper;

  @PostMapping("/orders")
  public ResponseEntity<BeerOrderDto> placeOrder(
      @PathVariable UUID customerId, @RequestBody BeerOrderRequestDto beerOrderRequestDto) {
    return new ResponseEntity<>(
        placeBeerOrderService.execute(customerId, beerOrderMapper.toDto(beerOrderRequestDto)), HttpStatus.CREATED);
  }
}

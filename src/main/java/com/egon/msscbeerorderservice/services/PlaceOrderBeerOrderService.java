package com.egon.msscbeerorderservice.services;

import com.egon.msscbeerorderservice.dtos.BeerOrderDto;

import java.util.UUID;

public interface PlaceOrderBeerOrderService {
  BeerOrderDto execute(UUID customerId, BeerOrderDto beerOrderDto);
}

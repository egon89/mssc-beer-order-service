package com.egon.msscbeerorderservice.services;

import com.egon.brewery.dtos.BeerOrderDto;

public interface AllocationPassedService {
  void execute(BeerOrderDto beerOrderDto);
}

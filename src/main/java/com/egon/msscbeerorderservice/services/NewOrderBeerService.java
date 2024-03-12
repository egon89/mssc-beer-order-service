package com.egon.msscbeerorderservice.services;

import com.egon.brewery.dtos.BeerOrderDto;

public interface NewOrderBeerService {
  BeerOrderDto execute(BeerOrderDto beerOrderDto);
}

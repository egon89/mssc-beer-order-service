package com.egon.msscbeerorderservice.services;

import com.egon.msscbeerorderservice.dtos.BeerDto;

import java.util.Optional;

public interface GetBeerByUpc {
  Optional<BeerDto> execute(String upc);
}

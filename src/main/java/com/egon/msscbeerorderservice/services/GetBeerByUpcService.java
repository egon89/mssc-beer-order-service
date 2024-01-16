package com.egon.msscbeerorderservice.services;

import com.egon.msscbeerorderservice.dtos.BeerDto;

import java.util.Optional;

public interface GetBeerByUpcService {
  Optional<BeerDto> execute(String upc);
}

package com.egon.msscbeerorderservice.services;

import com.egon.msscbeerorderservice.dtos.BeerDto;

import java.util.Optional;
import java.util.UUID;

public interface GetBeerById {
  Optional<BeerDto> execute(UUID id);
}

package com.egon.msscbeerorderservice.services;

import com.egon.brewery.dtos.BeerDto;

import java.util.Optional;
import java.util.UUID;

public interface GetBeerByIdService {
  Optional<BeerDto> execute(UUID id);
}

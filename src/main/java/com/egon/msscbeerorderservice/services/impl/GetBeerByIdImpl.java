package com.egon.msscbeerorderservice.services.impl;

import com.egon.msscbeerorderservice.dtos.BeerDto;
import com.egon.msscbeerorderservice.services.GetBeerById;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetBeerByIdImpl implements GetBeerById {

  private final RestTemplate restTemplate;

  @Value("${beer.service.host}")
  private String host;

  @Override
  public Optional<BeerDto> execute(UUID id) {
    log.debug("Getting beer by id {}", id.toString());
    return Optional.ofNullable(
        restTemplate.getForObject(host.concat("/{id}"), BeerDto.class, id));
  }
}

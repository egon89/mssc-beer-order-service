package com.egon.msscbeerorderservice.services.impl;

import com.egon.msscbeerorderservice.dtos.BeerDto;
import com.egon.msscbeerorderservice.services.GetBeerByUpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetBeerByUpcImpl implements GetBeerByUpc {
  private final RestTemplate restTemplate;

  @Value("${beer.service.host}")
  private String host;

  @Override
  public Optional<BeerDto> execute(String upc) {
    log.debug("Getting beer by upc {}", upc);
    return Optional.ofNullable(
        restTemplate.getForObject(host.concat("/upc/{upc}"), BeerDto.class, upc));
  }
}

package com.egon.msscbeerorderservice.services.impl;

import com.egon.brewery.dtos.BeerDto;
import com.egon.msscbeerorderservice.services.GetBeerByUpcService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static com.egon.msscbeerorderservice.utils.BeerIntegrationUtil.beerPathFactory;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetBeerByUpcServiceImpl implements GetBeerByUpcService {
  private final RestTemplate restTemplate;

  @Value("${beer.service.host}")
  private String host;

  @Override
  public Optional<BeerDto> execute(String upc) {
    log.debug("Getting beer by upc {}", upc);

    return Optional.ofNullable(
        restTemplate.getForObject(beerPathFactory(host, "/upc/{upc}"), BeerDto.class, upc));
  }
}

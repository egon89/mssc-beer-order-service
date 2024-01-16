package com.egon.msscbeerorderservice.utils;

import lombok.experimental.UtilityClass;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class BeerIntegrationUtils {
  public static final String BEER_PATH = "/api/v1/beers";

  public static String beerPathFactory(String host, String... values) {
    final var valuesAsString = String.join("", values);

    return host.concat(BEER_PATH).concat(valuesAsString);
  }
}

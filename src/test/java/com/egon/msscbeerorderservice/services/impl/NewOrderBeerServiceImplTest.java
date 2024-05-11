package com.egon.msscbeerorderservice.services.impl;

import com.egon.brewery.dtos.BeerDto;
import com.egon.msscbeerorderservice.enums.OrderStatusEnum;
import com.egon.msscbeerorderservice.repositories.BeerOrderRepository;
import com.egon.msscbeerorderservice.services.NewOrderBeerService;
import com.egon.msscbeerorderservice.utils.BeerIntegrationUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.maciejwalkowiak.wiremock.spring.ConfigureWireMock;
import com.maciejwalkowiak.wiremock.spring.EnableWireMock;
import com.maciejwalkowiak.wiremock.spring.InjectWireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.egon.msscbeerorderservice.MsscBeerOrderServiceApplicationTests.*;
import static com.egon.msscbeerorderservice.helper.BeerOrderHelper.createNewBeerOrderDto;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

// https://wiremock.org/docs/solutions/spring-boot/
// https://github.com/maciejwalkowiak/wiremock-spring-boot
// https://foojay.io/today/testing-spring-boot-jms-with-activemq-artemis-and-testcontainers/
@Testcontainers
@SpringBootTest
@EnableWireMock({
    @ConfigureWireMock(name = "beer-service", property = "beer.service.host")
})
class NewOrderBeerServiceImplTest {

  @InjectWireMock("beer-service")
  private WireMockServer wiremock;

  @Container
  static GenericContainer<?> artemis = new GenericContainer<>(DockerImageName.parse(ACTIVEMQ_ARTEMIS_IMAGE))
      .withEnv(ANONYMOUS_LOGIN, "true")
      .withExposedPorts(61616)
      .withReuse(Boolean.TRUE);

  @DynamicPropertySource
  static void artemisProperties(DynamicPropertyRegistry registry) {
    registry.add(BROKER_URL_PROPERTY,
        () -> BROKER_URL.formatted(artemis.getHost(), artemis.getMappedPort(61616)));
  }

  @Autowired
  NewOrderBeerService newOrderBeerService;

  @Autowired
  BeerOrderRepository repository;

  @Autowired
  ObjectMapper objectMapper;

  @Test
  void shouldCreateANewBeerOrder() throws JsonProcessingException {
    final var upc = String.valueOf(new Random().nextInt(50000));
    final var path = "%s/upc/%s".formatted(BeerIntegrationUtil.BEER_PATH, upc);
    final var beerResponse = BeerDto.builder().id(UUID.randomUUID()).upc(upc).name("Beer A").build();
    final var dto = createNewBeerOrderDto(UUID.fromString("97e1aef4-1c90-471a-8290-ef4df6237a60"), upc);
    wiremock.stubFor(get(path)
        .willReturn(okJson(objectMapper.writeValueAsString(beerResponse))));

    final var savedBeerOrder = newOrderBeerService.execute(dto);
    assertThat(OrderStatusEnum.NEW).isEqualTo(savedBeerOrder.getOrderStatus());

    await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
      final var beerOrder = repository.findById(savedBeerOrder.getId()).orElseThrow();
      assertThat(OrderStatusEnum.ALLOCATED).isEqualTo(beerOrder.getOrderStatus());
    });
  }

  @Test
  void shouldFailValidationForANewBeerOrder() throws JsonProcessingException {
    final var upc = String.valueOf(new Random().nextInt(50000));
    final var path = "%s/upc/%s".formatted(BeerIntegrationUtil.BEER_PATH, upc);
    final var beerResponse = BeerDto.builder().id(UUID.randomUUID()).upc(upc).name("Beer B").build();
    final var dto = createNewBeerOrderDto(UUID.fromString("97e1aef4-1c90-471a-8290-ef4df6237a60"), upc)
        .toBuilder()
        .customerRef("fail-validation")
        .build();
    wiremock.stubFor(get(path)
        .willReturn(okJson(objectMapper.writeValueAsString(beerResponse))));

    final var savedBeerOrder = newOrderBeerService.execute(dto);
    assertThat(OrderStatusEnum.NEW).isEqualTo(savedBeerOrder.getOrderStatus());

    await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
      final var beerOrder = repository.findById(savedBeerOrder.getId()).orElseThrow();
      assertThat(OrderStatusEnum.VALIDATION_EXCEPTION).isEqualTo(beerOrder.getOrderStatus());
    });
  }
}
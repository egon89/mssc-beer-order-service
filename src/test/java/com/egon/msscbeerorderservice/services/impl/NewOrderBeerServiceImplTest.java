package com.egon.msscbeerorderservice.services.impl;

import com.egon.brewery.dtos.BeerDto;
import com.egon.brewery.dtos.BeerOrderDto;
import com.egon.brewery.dtos.BeerOrderLineDto;
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
import org.springframework.jms.core.JmsTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

// https://wiremock.org/docs/solutions/spring-boot/
// https://github.com/maciejwalkowiak/wiremock-spring-boot
// @SpringBootTest(properties = "spring.profiles.include=test")
@SpringBootTest
@EnableWireMock({
    @ConfigureWireMock(name = "beer-service", property = "beer.service.host")
})
class NewOrderBeerServiceImplTest {

  @InjectWireMock("beer-service")
  private WireMockServer wiremock;

  @Autowired
  NewOrderBeerService newOrderBeerService;

  @Autowired
  BeerOrderRepository repository;

  // remove the jmsTemplate mock to run an integration test
//  @MockBean
  @Autowired
  JmsTemplate jmsTemplate;

  @Autowired
  ObjectMapper objectMapper;

  @Test
  void shouldCreateANewBeerOrder() throws JsonProcessingException {
    final var upc = String.valueOf(new Random().nextInt(50000));
    final var path = "%s/upc/%s".formatted(BeerIntegrationUtil.BEER_PATH, upc);
    final var beerResponse = BeerDto.builder().id(UUID.randomUUID()).upc(upc).name("Beer A").build();
    final var orderLine = BeerOrderLineDto.builder()
        .beerId(UUID.randomUUID())
        .upc(upc)
        .price(new BigDecimal(new Random().nextLong(10)))
        .build();
    final var dto = BeerOrderDto.builder()
        .id(UUID.fromString("97e1aef4-1c90-471a-8290-ef4df6237a60"))
        .orderStatus(OrderStatusEnum.NEW)
        .beerOrderLines(List.of(orderLine))
        .build();
    wiremock.stubFor(get(path)
        .willReturn(okJson(objectMapper.writeValueAsString(beerResponse))));

    final var savedBeerOrder = newOrderBeerService.execute(dto);
    //assertThat(savedBeerOrder.getOrderStatus()).isEqualTo(OrderStatusEnum.NEW);

    await().untilAsserted(() -> {
      final var beerOrder = repository.findById(savedBeerOrder.getId()).orElseThrow();
      assertThat(beerOrder.getOrderStatus()).isEqualTo(OrderStatusEnum.VALIDATION_PENDING);
    });
//    final var beerOrder = repository.findById(savedBeerOrder.getId()).orElseThrow();
//    assertThat(beerOrder.getOrderStatus()).isEqualTo(OrderStatusEnum.VALIDATION_PENDING);
//    verify(jmsTemplate).convertAndSend(anyString(), any(ValidateBeerOrderRequest.class));
  }
}
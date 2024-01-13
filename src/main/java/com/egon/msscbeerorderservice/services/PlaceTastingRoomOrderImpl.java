package com.egon.msscbeerorderservice.services;

import com.egon.msscbeerorderservice.dtos.BeerOrderDto;
import com.egon.msscbeerorderservice.dtos.BeerOrderLineDto;
import com.egon.msscbeerorderservice.entities.CustomerEntity;
import com.egon.msscbeerorderservice.repositories.BeerOrderRepository;
import com.egon.msscbeerorderservice.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceTastingRoomOrderImpl implements PlaceTastingRoomOrder {

  public static final String BEER_1_UPC = "0631234200036";
  public static final String BEER_2_UPC = "0631234300019";
  public static final String BEER_3_UPC = "0083783375213";
  public static final String TASTING_ROOM = "Tasting Room";

  private final CustomerRepository customerRepository;
  private final PlaceOrderBeerOrderService placeOrderBeerOrderService;
  private final BeerOrderRepository beerOrderRepository;
  private final List<String> beerUpcs = List.of(BEER_1_UPC, BEER_2_UPC, BEER_3_UPC);

  @Transactional
  @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
  @Override
  public void execute() {
    log.info("place tasting room order task starting...");
    final var customerList = customerRepository.findAllByNameLike(TASTING_ROOM);
    if (Objects.isNull(customerList) || customerList.size() != 1) {
      log.error("Error in tasting room");
      return;
    }

    final var savedOrder = placeOrder(customerList.getFirst());
    log.info("Beer order {} created by place tasting room order task", savedOrder.getId().toString());
  }

  private BeerOrderDto placeOrder(CustomerEntity customer) {
    final var beerToOrder = getRandomBeerUpc();
    final var beerOrderLine = BeerOrderLineDto.builder()
        .upc(beerToOrder)
        .orderQuantity(new Random().nextInt(6))
        .build();
    final var beerOrder = BeerOrderDto.builder()
        .customerId(customer.getId())
        .customerRef(UUID.randomUUID().toString())
        .beerOrderLines(List.of(beerOrderLine))
        .build();

    return placeOrderBeerOrderService.execute(customer.getId(), beerOrder);
  }

  private String getRandomBeerUpc() {
    return beerUpcs.get(new Random().nextInt(beerUpcs.size()));
  }
}

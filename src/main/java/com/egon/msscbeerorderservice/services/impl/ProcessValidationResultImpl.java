package com.egon.msscbeerorderservice.services.impl;

import com.egon.brewery.dtos.BeerOrderDto;
import com.egon.msscbeerorderservice.entities.BeerOrderEntity;
import com.egon.msscbeerorderservice.enums.OrderEventEnum;
import com.egon.msscbeerorderservice.enums.OrderStatusEnum;
import com.egon.msscbeerorderservice.interceptors.BeerOrderStateChangeInterceptor;
import com.egon.msscbeerorderservice.mappers.BeerOrderMapper;
import com.egon.msscbeerorderservice.repositories.BeerOrderRepository;
import com.egon.msscbeerorderservice.services.BaseBeerOrderManager;
import com.egon.msscbeerorderservice.services.ProcessValidationResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Consumer;

@Slf4j
@Service
public class ProcessValidationResultImpl extends BaseBeerOrderManager implements ProcessValidationResult {
  public ProcessValidationResultImpl(
      StateMachineFactory<OrderStatusEnum, OrderEventEnum> stateMachineFactory,
      BeerOrderMapper mapper,
      BeerOrderRepository repository,
      BeerOrderStateChangeInterceptor beerOrderStateChangeInterceptor) {
    super(stateMachineFactory, mapper, repository, beerOrderStateChangeInterceptor);
  }

  @Override
  public void execute(UUID beerOrderId, boolean isValid) {
    repository.findAll().forEach(beerOrder -> System.out.println(beerOrder.getId()));
    final var beerOrder = repository.findById(beerOrderId).orElseThrow(() -> new RuntimeException("Not found"));
    final var consumer = isValid ? validationPassedConsumer : validationFailedConsumer;
    executeEvent(consumer, beerOrder);
  }

  private void executeEvent(Consumer<BeerOrderDto> consumer, BeerOrderEntity beerOrder) {
    consumer.accept(mapper.toDto(beerOrder));
  }

  private final Consumer<BeerOrderDto> validationPassedConsumer = beerOrder -> {
    log.debug("Validation passed for beer order {} (status {})", beerOrder.getId(), beerOrder.getOrderStatus());
    sendEvent(beerOrder, OrderEventEnum.VALIDATION_PASSED);
    final var validateOrder = repository.findById(beerOrder.getId())
        .orElseThrow(() -> new RuntimeException("Not found"));
    log.debug("Validation passed event sent for beer order {} (status {})", validateOrder.getId(), validateOrder.getOrderStatus());
    sendEvent(mapper.toDto(validateOrder), OrderEventEnum.ALLOCATE_ORDER);
  };

  private final Consumer<BeerOrderDto> validationFailedConsumer = beerOrder -> {
    log.debug("Validation failed for beer order {}", beerOrder.getId());
    sendEvent(beerOrder, OrderEventEnum.VALIDATION_FAILED);
  };
}

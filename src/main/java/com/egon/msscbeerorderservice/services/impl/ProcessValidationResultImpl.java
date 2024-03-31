package com.egon.msscbeerorderservice.services.impl;

import com.egon.msscbeerorderservice.enums.OrderEventEnum;
import com.egon.msscbeerorderservice.enums.OrderStatusEnum;
import com.egon.msscbeerorderservice.interceptors.BeerOrderStateChangeInterceptor;
import com.egon.msscbeerorderservice.mappers.BeerOrderMapper;
import com.egon.msscbeerorderservice.repositories.BeerOrderRepository;
import com.egon.msscbeerorderservice.services.BaseBeerOrderManager;
import com.egon.msscbeerorderservice.services.ProcessValidationResult;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
    final var beerOrder = repository.findById(beerOrderId).orElseThrow(() -> new RuntimeException("Not found"));
    final var nextEvent = isValid ? OrderEventEnum.VALIDATION_PASSED : OrderEventEnum.VALIDATION_FAILED;
    sendEvent(mapper.toDto(beerOrder), nextEvent);
  }
}

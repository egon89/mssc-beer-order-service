package com.egon.msscbeerorderservice.services.impl;

import com.egon.brewery.dtos.BeerOrderDto;
import com.egon.msscbeerorderservice.enums.OrderEventEnum;
import com.egon.msscbeerorderservice.enums.OrderStatusEnum;
import com.egon.msscbeerorderservice.interceptors.BeerOrderStateChangeInterceptor;
import com.egon.msscbeerorderservice.mappers.BeerOrderMapper;
import com.egon.msscbeerorderservice.repositories.BeerOrderRepository;
import com.egon.msscbeerorderservice.services.AllocationPendingInventoryService;
import com.egon.msscbeerorderservice.services.BaseBeerOrderManager;
import com.egon.msscbeerorderservice.services.UpdateAllocateQuantityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AllocationPendingInventoryServiceImpl
    extends BaseBeerOrderManager implements AllocationPendingInventoryService {

  private final UpdateAllocateQuantityService updateAllocateQuantityService;

  public AllocationPendingInventoryServiceImpl(
      StateMachineFactory<OrderStatusEnum, OrderEventEnum> stateMachineFactory,
      BeerOrderMapper mapper,
      BeerOrderRepository repository,
      BeerOrderStateChangeInterceptor beerOrderStateChangeInterceptor,
      UpdateAllocateQuantityService updateAllocateQuantityService) {
    super(stateMachineFactory, mapper, repository, beerOrderStateChangeInterceptor);
    this.updateAllocateQuantityService = updateAllocateQuantityService;
  }

  @Override
  public void execute(BeerOrderDto beerOrderDto) {
    final var order = repository.findById(beerOrderDto.getId()).orElseThrow();
    sendEvent(mapper.toDto(order), OrderEventEnum.ALLOCATION_NO_INVENTORY);
    updateAllocateQuantityService.execute(beerOrderDto, order);
  }
}

package com.egon.msscbeerorderservice.services.impl;

import com.egon.brewery.dtos.BeerOrderDto;
import com.egon.msscbeerorderservice.enums.OrderEventEnum;
import com.egon.msscbeerorderservice.enums.OrderStatusEnum;
import com.egon.msscbeerorderservice.mappers.BeerOrderMapper;
import com.egon.msscbeerorderservice.repositories.BeerOrderRepository;
import com.egon.msscbeerorderservice.services.BaseBeerOrderManager;
import com.egon.msscbeerorderservice.services.NewOrderBeerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class NewOrderBeerServiceImpl extends BaseBeerOrderManager implements NewOrderBeerService {

  public NewOrderBeerServiceImpl(
      StateMachineFactory<OrderStatusEnum, OrderEventEnum> stateMachineFactory,
      BeerOrderMapper mapper,
      BeerOrderRepository repository) {
    super(stateMachineFactory, mapper, repository);
  }

  @Transactional
  @Override
  public BeerOrderDto execute(BeerOrderDto beerOrderDto) {
    beerOrderDto.setId(null);
    beerOrderDto.setOrderStatus(OrderStatusEnum.NEW);

    var savedBeerOrder = repository.save(mapper.toEntity(beerOrderDto));
    var savedBeerOrderDto = mapper.toDto(savedBeerOrder);
    sendEvent(savedBeerOrderDto, OrderEventEnum.VALIDATE_ORDER);

    return savedBeerOrderDto;
  }
}

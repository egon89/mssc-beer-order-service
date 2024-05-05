package com.egon.msscbeerorderservice.listerners;

import com.egon.brewery.dtos.events.AllocateBeerOrderResult;
import com.egon.msscbeerorderservice.config.JmsConfig;
import com.egon.msscbeerorderservice.services.AllocationFailedService;
import com.egon.msscbeerorderservice.services.AllocationPassedService;
import com.egon.msscbeerorderservice.services.AllocationPendingInventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderAllocationResultListener {

  private final AllocationPassedService allocationPassedService;
  private final AllocationPendingInventoryService allocationPendingInventoryService;
  private final AllocationFailedService allocationFailedService;

  @JmsListener(destination = JmsConfig.ALLOCATE_ORDER_RESPONSE_QUEUE)
  public void listen(AllocateBeerOrderResult result) {
    log.debug("Listen {} queue for order {}", JmsConfig.ALLOCATE_ORDER_RESPONSE_QUEUE, result.getBeerOrderDto().getId());
    Objects.requireNonNull(result);
    switch (result) {
      case AllocateBeerOrderResult a when a.isAllocationError() && a.isPendingInventory() ->
          throw new IllegalArgumentException("Invalid allocate order result");
      case AllocateBeerOrderResult a when a.isPendingInventory() ->
          allocationPendingInventoryService.execute(result.getBeerOrderDto());
      case AllocateBeerOrderResult a when a.isAllocationError() ->
          allocationFailedService.execute(result.getBeerOrderDto());
      default -> allocationPassedService.execute(result.getBeerOrderDto());
    }
  }
}

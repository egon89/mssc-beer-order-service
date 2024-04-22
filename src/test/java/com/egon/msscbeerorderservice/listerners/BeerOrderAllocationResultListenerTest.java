package com.egon.msscbeerorderservice.listerners;

import com.egon.brewery.dtos.BeerOrderDto;
import com.egon.brewery.dtos.events.AllocateBeerOrderResult;
import com.egon.msscbeerorderservice.services.AllocationFailedService;
import com.egon.msscbeerorderservice.services.AllocationPassedService;
import com.egon.msscbeerorderservice.services.AllocationPendingInventoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest
class BeerOrderAllocationResultListenerTest {

  @Autowired
  BeerOrderAllocationResultListener listener;

  @MockBean
  AllocationPassedService allocationPassedService;

  @MockBean
  AllocationPendingInventoryService allocationPendingInventoryService;

  @MockBean
  AllocationFailedService allocationFailedService;

  @Test
  void shouldCallPassedServiceWhenErrorAndPendingInventoryAreFalse() {
    final var result = AllocateBeerOrderResult.builder()
        .beerOrderDto(BeerOrderDto.builder().id(UUID.randomUUID()).build())
        .allocationError(false)
        .pendingInventory(false)
        .build();

    listener.listen(result);

    verify(allocationPassedService).execute(eq(result.getBeerOrderDto()));
    verify(allocationPendingInventoryService, never()).execute(any());
    verify(allocationFailedService, never()).execute(any());
  }

  @Test
  void shouldCallPendingInventoryServiceWhenPendingInventoryIsTrue() {
    final var result = AllocateBeerOrderResult.builder()
        .beerOrderDto(BeerOrderDto.builder().id(UUID.randomUUID()).build())
        .allocationError(false)
        .pendingInventory(true)
        .build();

    listener.listen(result);

    verify(allocationPendingInventoryService).execute(eq(result.getBeerOrderDto()));
    verify(allocationPassedService, never()).execute(any());
    verify(allocationFailedService, never()).execute(any());
  }

  @Test
  void shouldCallFailedServiceWhenErrorIsTrue() {
    final var result = AllocateBeerOrderResult.builder()
        .beerOrderDto(BeerOrderDto.builder().id(UUID.randomUUID()).build())
        .allocationError(true)
        .pendingInventory(false)
        .build();

    listener.listen(result);

    verify(allocationFailedService).execute(eq(result.getBeerOrderDto()));
    verify(allocationPendingInventoryService, never()).execute(any());
    verify(allocationPassedService, never()).execute(any());
  }

  @Test
  void shouldThrowsErrorWhenErrorAndPendingInventoryAreTrue() {
    final var result = AllocateBeerOrderResult.builder()
        .beerOrderDto(BeerOrderDto.builder().id(UUID.randomUUID()).build())
        .allocationError(true)
        .pendingInventory(true)
        .build();

    assertThrows(IllegalArgumentException.class, () -> listener.listen(result));

    verify(allocationFailedService, never()).execute(any());
    verify(allocationPendingInventoryService, never()).execute(any());
    verify(allocationPassedService, never()).execute(any());
  }
}
package com.egon.brewery.dtos.events;

import com.egon.brewery.dtos.BeerOrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllocateBeerOrderResult {
  private BeerOrderDto beerOrderDto;
  private boolean allocationError;
  private boolean pendingInventory;
}

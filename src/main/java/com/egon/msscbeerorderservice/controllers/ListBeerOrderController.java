package com.egon.msscbeerorderservice.controllers;

import com.egon.brewery.dtos.BeerOrderPagedListDto;
import com.egon.msscbeerorderservice.services.PagedListBeerOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/customers/{customerId}")
@RequiredArgsConstructor
public class ListBeerOrderController {

  private static final String DEFAULT_PAGE_NUMBER = "0";
  private static final String DEFAULT_PAGE_SIZE = "25";

  private final PagedListBeerOrderService pagedListBeerOrderService;

  @GetMapping("/orders")
  public ResponseEntity<BeerOrderPagedListDto> listBeerOrders(
      @PathVariable UUID customerId,
      @RequestParam(required = false, defaultValue = DEFAULT_PAGE_NUMBER) Integer pageNumber,
      @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize
  ) {
    validateRequestParams(pageNumber, pageSize);

    return ResponseEntity.ok(
        pagedListBeerOrderService.execute(customerId, PageRequest.of(pageNumber, pageSize)));
  }

  private void validateRequestParams(Integer pageNumber, Integer pageSize) {
    if (Objects.nonNull(pageNumber) && pageNumber >= 0) return;
    if (Objects.nonNull(pageSize) && pageSize >= 0) return;

    throw new IllegalArgumentException("Invalid request parameters");
  }
}

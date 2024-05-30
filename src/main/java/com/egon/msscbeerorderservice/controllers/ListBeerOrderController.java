package com.egon.msscbeerorderservice.controllers;

import com.egon.brewery.dtos.BeerOrderPagedListDto;
import com.egon.msscbeerorderservice.services.PagedListBeerOrderService;
import com.egon.msscbeerorderservice.utils.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.egon.msscbeerorderservice.utils.PaginationUtil.DEFAULT_PAGE_NUMBER;
import static com.egon.msscbeerorderservice.utils.PaginationUtil.DEFAULT_PAGE_SIZE;

@RestController
@RequestMapping("/customers/{customerId}")
@RequiredArgsConstructor
public class ListBeerOrderController {

  private final PagedListBeerOrderService pagedListBeerOrderService;

  @GetMapping("/orders")
  public ResponseEntity<BeerOrderPagedListDto> listBeerOrders(
      @PathVariable UUID customerId,
      @RequestParam(required = false, defaultValue = DEFAULT_PAGE_NUMBER) Integer pageNumber,
      @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize
  ) {
    PaginationUtil.validateRequestParams(pageNumber, pageSize);

    return ResponseEntity.ok(
        pagedListBeerOrderService.execute(customerId, PageRequest.of(pageNumber, pageSize)));
  }
}

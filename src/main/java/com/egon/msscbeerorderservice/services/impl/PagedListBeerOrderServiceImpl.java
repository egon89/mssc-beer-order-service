package com.egon.msscbeerorderservice.services.impl;

import com.egon.brewery.dtos.BeerOrderPagedListDto;
import com.egon.msscbeerorderservice.mappers.BeerOrderMapper;
import com.egon.msscbeerorderservice.repositories.BeerOrderRepository;
import com.egon.msscbeerorderservice.repositories.CustomerRepository;
import com.egon.msscbeerorderservice.services.PagedListBeerOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PagedListBeerOrderServiceImpl implements PagedListBeerOrderService {

  private final BeerOrderRepository beerOrderRepository;
  private final CustomerRepository customerRepository;
  private final BeerOrderMapper mapper;

  @Override
  public BeerOrderPagedListDto execute(UUID customerId, Pageable pageable) {
    // TODO: create a customer find by id service
    var customer = customerRepository.findById(customerId).orElseThrow(RuntimeException::new);
    var beerOrderPage = beerOrderRepository.findAllByCustomer(customer, pageable);
    var beerOrderDtoList = beerOrderPage.stream()
        .map(mapper::toDto)
        .toList();

    return new BeerOrderPagedListDto(
        beerOrderDtoList,
        PageRequest.of(
          beerOrderPage.getPageable().getPageNumber(),
          beerOrderPage.getPageable().getPageSize()),
        beerOrderPage.getTotalElements());
  }
}

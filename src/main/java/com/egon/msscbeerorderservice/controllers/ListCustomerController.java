package com.egon.msscbeerorderservice.controllers;

import com.egon.brewery.dtos.CustomerPagedListDto;
import com.egon.msscbeerorderservice.services.ListCustomerService;
import com.egon.msscbeerorderservice.utils.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.egon.msscbeerorderservice.utils.PaginationUtil.DEFAULT_PAGE_NUMBER;
import static com.egon.msscbeerorderservice.utils.PaginationUtil.DEFAULT_PAGE_SIZE;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class ListCustomerController {

  private final ListCustomerService service;

  @GetMapping()
  public ResponseEntity<CustomerPagedListDto> listCustomers(
      @RequestParam(required = false, defaultValue = DEFAULT_PAGE_NUMBER) Integer pageNumber,
      @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize
  ) {
    PaginationUtil.validateRequestParams(pageNumber, pageSize);
    return ResponseEntity.ok(
        service.execute(PageRequest.of(pageNumber, pageSize)));
  }
}

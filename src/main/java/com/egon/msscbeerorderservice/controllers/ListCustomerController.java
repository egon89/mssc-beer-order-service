package com.egon.msscbeerorderservice.controllers;

import com.egon.brewery.dtos.CustomerDto;
import com.egon.msscbeerorderservice.services.ListCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class ListCustomerController {

  private final ListCustomerService service;

  @GetMapping()
  public ResponseEntity<List<CustomerDto>> listCustomers() {
    return ResponseEntity.ok(service.execute());
  }
}

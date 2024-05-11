package com.egon.msscbeerorderservice.mappers;

import com.egon.brewery.dtos.BeerOrderDto;
import com.egon.brewery.dtos.request.BeerOrderRequestDto;
import com.egon.msscbeerorderservice.entities.BeerOrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {DateMapper.class, BeerOrderLineMapper.class})
public interface BeerOrderMapper {
  @Mapping(target = "customerId", source = "customer.id")
  @Mapping(target = "customerRef", source = "customerReference")
  BeerOrderDto toDto(BeerOrderEntity entity);

  BeerOrderDto toDto(BeerOrderRequestDto requestDto);

  @Mapping(target = "customerReference", source = "customerRef")
  BeerOrderEntity toEntity(BeerOrderDto dto);
}

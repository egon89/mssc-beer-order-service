package com.egon.msscbeerorderservice.mappers;

import com.egon.msscbeerorderservice.dtos.BeerOrderDto;
import com.egon.msscbeerorderservice.dtos.request.BeerOrderRequestDto;
import com.egon.msscbeerorderservice.entities.BeerOrderEntity;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class, BeerOrderLineMapper.class})
public interface BeerOrderMapper {
  BeerOrderDto toDto(BeerOrderEntity entity);

  BeerOrderDto toDto(BeerOrderRequestDto requestDto);

  BeerOrderEntity toEntity(BeerOrderDto dto);
}

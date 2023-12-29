package com.egon.msscbeerorderservice.mappers;

import com.egon.msscbeerorderservice.dtos.BeerOrderDto;
import com.egon.msscbeerorderservice.entities.BeerOrderEntity;
import org.mapstruct.Mapper;

@Mapper(uses = DateMapper.class)
public interface BeerOrderMapper {
  BeerOrderDto toDto(BeerOrderEntity entity);

  BeerOrderEntity toEntity(BeerOrderDto dto);
}

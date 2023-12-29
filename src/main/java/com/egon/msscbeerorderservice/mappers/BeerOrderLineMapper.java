package com.egon.msscbeerorderservice.mappers;

import com.egon.msscbeerorderservice.dtos.BeerOrderLineDto;
import com.egon.msscbeerorderservice.entities.BeerOrderLineEntity;
import org.mapstruct.Mapper;

@Mapper(uses = DateMapper.class)
public interface BeerOrderLineMapper {
  BeerOrderLineDto toDto(BeerOrderLineEntity entity);
  BeerOrderLineEntity toEntity(BeerOrderLineDto dto);
}

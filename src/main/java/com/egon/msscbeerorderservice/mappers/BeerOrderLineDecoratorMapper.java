package com.egon.msscbeerorderservice.mappers;

import com.egon.brewery.dtos.BeerOrderLineDto;
import com.egon.msscbeerorderservice.entities.BeerOrderLineEntity;
import com.egon.msscbeerorderservice.services.GetBeerByUpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class BeerOrderLineDecoratorMapper implements BeerOrderLineMapper {
  @Autowired
  @Qualifier("delegate")
  private BeerOrderLineMapper mapper;

  @Autowired
  private GetBeerByUpcService getBeerByUpcService;

  @Override
  public BeerOrderLineDto toDto(BeerOrderLineEntity entity) {
    final var beerOrderLineDto = mapper.toDto(entity);
    getBeerByUpcService.execute(entity.getUpc())
        .ifPresent(beerDto -> {
          beerOrderLineDto.setBeerId(beerDto.getId());
          beerOrderLineDto.setBeerName(beerDto.getName());
          beerOrderLineDto.setBeerStyle(beerDto.getStyle());
          beerOrderLineDto.setPrice(beerDto.getPrice());
        });

    return beerOrderLineDto;
  }
}

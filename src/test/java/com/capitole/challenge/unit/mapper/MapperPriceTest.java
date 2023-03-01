package com.capitole.challenge.unit.mapper;

import com.capitole.challenge.model.PriceResponse;
import com.capitole.challenge.model.entity.PriceEntity;
import com.capitole.challenge.service.mapper.PriceMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MapperPriceTest {

    @Test
    void givenPriceEntitytoPriceResponse_whenMaps_thenCorrect(){
        PriceEntity priceEntity = new PriceEntity();
            priceEntity.setId(1L);
            priceEntity.setBrandId(1L);
            priceEntity.setPriceListId(1L);
            priceEntity.setProductId(35455L);
            priceEntity.setStartDate(LocalDateTime.parse("2020-06-14T10:00:00"));
            priceEntity.setEndDate(LocalDateTime.parse("2020-12-31T23:59:59"));
            priceEntity.setPrice(35.50);
            priceEntity.setCurrency("EUR");
        PriceResponse priceResponse = PriceMapper.INSTANCE.entityToPriceResponse(priceEntity);
        assertEquals(priceResponse.getProductId(), priceEntity.getProductId().toString());
        assertEquals(priceResponse.getBrandId(), priceEntity.getBrandId().toString());
        assertEquals(priceResponse.getPriceListId(), priceEntity.getPriceListId().toString());
        assertEquals(priceResponse.getPrice(), priceEntity.getPrice());
        assertNotNull(priceResponse.getStartDate());
        assertNotNull(priceResponse.getEndDate());
    }
}

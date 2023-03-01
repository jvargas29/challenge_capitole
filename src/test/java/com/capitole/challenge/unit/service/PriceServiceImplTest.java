package com.capitole.challenge.unit.service;

import com.capitole.challenge.exception.PriceNotFoundException;
import com.capitole.challenge.model.PriceResponse;
import com.capitole.challenge.model.entity.PriceEntity;
import com.capitole.challenge.model.repository.PriceRepository;
import com.capitole.challenge.rest.dto.PriceDto;
import com.capitole.challenge.service.PriceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceServiceImplTest {

    PriceRepository priceRepository = Mockito.mock(PriceRepository.class);

    PriceServiceImpl priceService = new PriceServiceImpl(priceRepository);

    PriceDto priceDto;
    PriceEntity priceEntity;
    PriceEntity priceEntityMaxPiority;

    @BeforeEach
    public void setup(){
        priceDto = PriceDto.builder()
                .dateApplicationPrice(LocalDateTime.parse("2020-07-03T23:59:59"))
                .productId(1L)
                .brandId(1L)
                .build();

        priceEntity = new PriceEntity();
        priceEntity.setId(1L);
        priceEntity.setBrandId(1L);
        priceEntity.setPriceListId(1L);
        priceEntity.setProductId(35455L);
        priceEntity.setStartDate(LocalDateTime.parse("2020-06-14T10:00:00"));
        priceEntity.setEndDate(LocalDateTime.parse("2020-12-31T23:59:59"));
        priceEntity.setPrice(35.50);
        priceEntity.setCurrency("EUR");
        priceEntity.setPriority(0);

        priceEntityMaxPiority = new PriceEntity();
        priceEntityMaxPiority.setId(2L);
        priceEntityMaxPiority.setBrandId(1L);
        priceEntityMaxPiority.setPriceListId(1L);
        priceEntityMaxPiority.setProductId(35455L);
        priceEntityMaxPiority.setStartDate(LocalDateTime.parse("2020-06-14T10:00:00"));
        priceEntityMaxPiority.setEndDate(LocalDateTime.parse("2020-12-31T23:59:59"));
        priceEntityMaxPiority.setPrice(40.50);
        priceEntityMaxPiority.setCurrency("EUR");
        priceEntityMaxPiority.setPriority(5);
    }

    @Test
    void givenValidPriceDTO_whenGetPrices_thenReturnPriceResponse_Ok(){
        when(priceRepository.getByPriceDTO(any(LocalDateTime.class), anyLong(), anyLong())).thenReturn(List.of(priceEntity));
        PriceResponse priceResponse = priceService.getCurrentPriceByPriceDTO(priceDto);

        assertEquals("35455", priceResponse.getProductId());
        assertEquals("1", priceResponse.getBrandId());
        assertEquals("1", priceResponse.getPriceListId());
        assertEquals(35.50, priceResponse.getPrice());
        assertEquals(OffsetDateTime.parse("2020-06-14T10:00:00Z", DateTimeFormatter.ISO_OFFSET_DATE_TIME), priceResponse.getStartDate());
        assertEquals(OffsetDateTime.parse("2020-12-31T23:59:59Z", DateTimeFormatter.ISO_OFFSET_DATE_TIME), priceResponse.getEndDate());
    }

    @Test
    void givenValidPriceDTO_whenGetPrices_thenReturnPriceMaxPriority_Ok(){
        when(priceRepository.getByPriceDTO(any(LocalDateTime.class), anyLong(), anyLong())).thenReturn(List.of(priceEntity,priceEntityMaxPiority));
        PriceResponse priceResponse = priceService.getCurrentPriceByPriceDTO(priceDto);

        assertEquals("35455", priceResponse.getProductId());
        assertEquals("1", priceResponse.getBrandId());
        assertEquals("1", priceResponse.getPriceListId());
        assertEquals(40.50, priceResponse.getPrice());
        assertEquals(OffsetDateTime.parse("2020-06-14T10:00:00Z", DateTimeFormatter.ISO_OFFSET_DATE_TIME), priceResponse.getStartDate());
        assertEquals(OffsetDateTime.parse("2020-12-31T23:59:59Z", DateTimeFormatter.ISO_OFFSET_DATE_TIME), priceResponse.getEndDate());
    }


    @Test
    void givenNotFoundPriceDTO_whenGetPrices_thenReturnException(){
        when(priceRepository.getByPriceDTO(any(LocalDateTime.class), anyLong(), anyLong())).thenReturn(new ArrayList<>());
        assertThrows(PriceNotFoundException.class,() -> priceService.getCurrentPriceByPriceDTO(priceDto));
    }

}

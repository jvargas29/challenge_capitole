package com.capitole.challenge.service.mapper;

import com.capitole.challenge.model.PriceResponse;
import com.capitole.challenge.model.entity.PriceEntity;
import com.capitole.challenge.model.repository.PriceRepository;
import com.capitole.challenge.rest.dto.PriceDto;
import com.capitole.challenge.service.PriceServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceServiceImplTest {
    @Mock
    PriceRepository priceRepository;

    @InjectMocks
    PriceServiceImpl priceService;

    @Test
    void givenValidPriceDTO_whenGetPrices_thenReturnPriceResponse_Ok(){
        PriceDto priceDto = PriceDto.builder()
                .dateApplicationPrice(LocalDateTime.parse("2020-07-03T23:59:59"))
                .productId(1L)
                .brandId(1L)
                .build();

        PriceEntity priceEntity = new PriceEntity();
        priceEntity.setId(1L);
        priceEntity.setBrandId(1L);
        priceEntity.setPriceListId(1L);
        priceEntity.setProductId(35455L);
        priceEntity.setStartDate(LocalDateTime.parse("2020-06-14T10:00:00"));
        priceEntity.setEndDate(LocalDateTime.parse("2020-12-31T23:59:59"));
        priceEntity.setPrice(35.50);
        priceEntity.setCurrency("EUR");

        when(priceRepository.getByPriceDTO(any(LocalDateTime.class), anyLong(), anyLong())).thenReturn(List.of(priceEntity));
        PriceResponse priceResponse = priceService.getCurrentPriceByPriceDTO(priceDto);

        assertEquals("35455", priceResponse.getProductId());
        assertEquals("1", priceResponse.getBrandId());
        assertEquals("1", priceResponse.getPriceListId());
        assertEquals(35.50, priceResponse.getPrice());
        assertEquals(OffsetDateTime.parse("2020-06-14T10:00:00Z", DateTimeFormatter.ISO_OFFSET_DATE_TIME), priceResponse.getStartDate());
        assertEquals(OffsetDateTime.parse("2020-12-31T23:59:59Z", DateTimeFormatter.ISO_OFFSET_DATE_TIME), priceResponse.getEndDate());
    }

}

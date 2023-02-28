package com.capitole.challenge.rest;

import com.capitole.challenge.exception.PriceNotFoundException;
import com.capitole.challenge.model.PriceResponse;
import com.capitole.challenge.rest.dto.PriceDto;
import com.capitole.challenge.service.PriceServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class PricesApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PriceServiceImpl priceService;

    @Test
    void whenValidParam_thenReturns200() throws Exception {
        PriceResponse priceResponse = new PriceResponse();
        priceResponse.setProductId("1");
        priceResponse.setBrandId("1");
        priceResponse.setPriceListId("1");
        priceResponse.setPrice(35.50);
        priceResponse.setStartDate(OffsetDateTime.parse("2020-06-14T00:00:00Z", DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        priceResponse.setEndDate(OffsetDateTime.parse("2020-12-31T23:59:59Z", DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        when(priceService.getCurrentPriceByPriceDTO(any(PriceDto.class))).thenReturn(priceResponse);

        mockMvc.perform(get("/prices")
                .contentType(MediaType.APPLICATION_JSON)
                        .param("dateApplicationPrice", "2020-06-20 00:00:00")
                        .param("productId","1" )
                        .param("brandId", "1"))
                .andExpect(jsonPath("$.productId").value("1"))
                .andExpect(jsonPath("$.brandId").value("1"))
                .andExpect(jsonPath("$.priceListId").value("1"))
                .andExpect(jsonPath("$.price").value(35.50))
                .andExpect(jsonPath("$.startDate").value("2020-06-14T00:00:00Z"))
                .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59Z"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void whenBadParam_thenReturns400() throws Exception {
        doThrow(PriceNotFoundException.class).when(priceService).getCurrentPriceByPriceDTO(any(PriceDto.class));

        mockMvc.perform(get("/prices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("dateApplicationPrice", "2020-06-20 00:00:00")
                        .param("productId","1" )
                        .param("brandId", "1"))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    void whenFailService_thenReturns500() throws Exception {
        doThrow(RuntimeException.class).when(priceService).getCurrentPriceByPriceDTO(any(PriceDto.class));

        mockMvc.perform(get("/prices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("dateApplicationPrice", "2020-06-20 00:00:00")
                        .param("productId","1" )
                        .param("brandId", "1"))
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(status().is5xxServerError())
                .andDo(print());
    }
}

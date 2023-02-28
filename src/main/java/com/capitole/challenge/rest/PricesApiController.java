package com.capitole.challenge.rest;

import com.capitole.challenge.api.PricesApi;
import com.capitole.challenge.model.PriceResponse;
import com.capitole.challenge.rest.dto.PriceDto;
import com.capitole.challenge.service.PriceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class PricesApiController implements PricesApi {


    private final PriceServiceImpl priceService;

    @Autowired
    public PricesApiController(PriceServiceImpl priceService) {
        this.priceService = priceService;
    }

    @Override
    public ResponseEntity<PriceResponse> getPrice(@NotNull @Valid String dateApplicationPrice, @NotNull @Valid BigDecimal productId, @NotNull @Valid BigDecimal brandId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(dateApplicationPrice, formatter);

        PriceDto priceDto = PriceDto.builder()
                .dateApplicationPrice(dateTime)
                .productId(productId.longValue())
                .brandId(brandId.longValue())
                .build();

        PriceResponse priceResponse = priceService.getCurrentPriceByPriceDTO(priceDto);
        return ResponseEntity.ok().body(priceResponse);
    }
}

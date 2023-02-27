package com.capitole.challenge.service;

import com.capitole.challenge.model.PriceResponse;
import com.capitole.challenge.rest.dto.PriceDto;

public interface PriceService {

    PriceResponse getCurrentPriceByPriceDTO(PriceDto priceDto);
}

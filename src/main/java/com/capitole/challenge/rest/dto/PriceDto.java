package com.capitole.challenge.rest.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PriceDto {

    private LocalDateTime dateApplicationPrice;

    private Long productId;

    private Long brandId;
}

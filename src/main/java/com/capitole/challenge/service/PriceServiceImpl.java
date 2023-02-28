package com.capitole.challenge.service;

import com.capitole.challenge.exception.PriceNotFoundException;
import com.capitole.challenge.model.PriceResponse;
import com.capitole.challenge.model.entity.PriceEntity;
import com.capitole.challenge.model.repository.PriceRepository;
import com.capitole.challenge.rest.dto.PriceDto;
import com.capitole.challenge.service.mapper.PriceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class PriceServiceImpl implements PriceService{


    private final PriceRepository priceRepository;

    @Autowired
    public PriceServiceImpl(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public PriceResponse getCurrentPriceByPriceDTO(PriceDto priceDto){
        List<PriceEntity> priceEntity = priceRepository.getByPriceDTO(priceDto.getDateApplicationPrice(),priceDto.getProductId(),priceDto.getBrandId());
        PriceEntity priorityPriceEntity = priceEntity.stream().max(Comparator.comparingInt(PriceEntity::getPriority))
                .orElseThrow(PriceNotFoundException::new);
        return PriceMapper.INSTANCE.entityToPriceResponse(priorityPriceEntity);
    }
}

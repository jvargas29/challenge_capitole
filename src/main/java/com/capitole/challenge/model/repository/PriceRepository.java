package com.capitole.challenge.model.repository;

import com.capitole.challenge.model.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<PriceEntity,Long> {

    @Query(value = "SELECT p FROM PriceEntity p WHERE :dateApplication >= p.startDate and :dateApplication <= p.endDate and p.productId = :productId and p.brandId = :brandId")
    List<PriceEntity> getByPriceDTO(@Param("dateApplication")LocalDateTime dateApplication, @Param("productId")Long productId, @Param("brandId")Long brandId);
}
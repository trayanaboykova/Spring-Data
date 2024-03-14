package org.jsonprocessing_exercise.data.repositories;

import org.jsonprocessing_exercise.data.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Set<Product> findAllByPriceBetweenAndBuyerIsNullOrderByPrice(BigDecimal from, BigDecimal to);
}

package org.jsonprocessing_exercise.data.repositories;

import org.jsonprocessing_exercise.data.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
}

package org.xmlprocessing_exercise.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.xmlprocessing_exercise.data.entities.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

}

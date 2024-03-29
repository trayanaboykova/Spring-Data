package org.xmlprocessing_exercise.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.xmlprocessing_exercise.data.entities.Car;


import java.util.Set;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Set<Car> findAllByMakeOrderByTravelledDistanceDesc(String toyota);

}

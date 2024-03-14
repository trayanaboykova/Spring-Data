package org.jsonprocessing_exercise.data.repositories;

import org.jsonprocessing_exercise.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Set<User> findAllBySoldBuyerIsNotNull();
}

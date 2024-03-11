package org.automappingobjects_exercise.data.repositories;

import org.automappingobjects_exercise.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}

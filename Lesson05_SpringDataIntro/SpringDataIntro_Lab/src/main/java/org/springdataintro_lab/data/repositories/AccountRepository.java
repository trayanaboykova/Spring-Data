package org.springdataintro_lab.data.repositories;

import org.springdataintro_lab.data.entitites.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

}

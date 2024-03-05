package org.springdataintro_lab.service;

import org.springdataintro_lab.data.entitites.Account;

import java.math.BigDecimal;

public interface AccountService {
    void createAccount(Account account);
    void withdrawMoney(BigDecimal money, Integer id);
    void transferMoney(BigDecimal money, Integer id);
}

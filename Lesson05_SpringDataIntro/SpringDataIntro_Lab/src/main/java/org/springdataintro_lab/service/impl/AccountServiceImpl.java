package org.springdataintro_lab.service.impl;

import org.springdataintro_lab.data.entitites.Account;
import org.springdataintro_lab.data.repositories.AccountRepository;
import org.springdataintro_lab.service.AccountService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void createAccount(Account account) {
        this.accountRepository.saveAndFlush(account);
    }

    @Override
    public void withdrawMoney(BigDecimal money, Integer id) {
        Optional<Account> optional = this.accountRepository.findById(id);
        if (optional.isPresent()) {
            Account account = optional.get();

            if (account.getBalance().compareTo(money) >= 0) {
                account.setBalance(account.getBalance().subtract(money));
                this.accountRepository.saveAndFlush(account);
            }
        }
    }

    @Override
    public void transferMoney(BigDecimal money, Integer id) {

    }
}

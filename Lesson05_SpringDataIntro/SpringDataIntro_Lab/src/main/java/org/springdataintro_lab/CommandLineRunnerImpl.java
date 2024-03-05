package org.springdataintro_lab;

import org.springdataintro_lab.data.entitites.Account;
import org.springdataintro_lab.data.entitites.User;
import org.springdataintro_lab.data.repositories.AccountRepository;
import org.springdataintro_lab.data.repositories.UserRepository;
import org.springdataintro_lab.service.AccountService;
import org.springdataintro_lab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    @Autowired
    private final UserService userService;
    @Autowired
    private final AccountService accountService;

    public CommandLineRunnerImpl(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void run(String... args) throws Exception {
        //     User user = new User("Augustine", 23);
        //     this.userService.register(user);
        //    User user = this.userService.findUserById(2);
        //    Account account = new Account(BigDecimal.valueOf(5000), user);
        //    this.accountService.createAccount(account);

        //     User firstUser = this.userService.findUserById(1);
        //     User secondUser = this.userService.findUserById(2);
        //     this.accountService.withdrawMoney(BigDecimal.valueOf(50), 1);
        //     System.out.println();

        User user = this.userService.findByName("Taylor");
        System.out.println();
    }
}

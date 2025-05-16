package com.ivanuil.scalabledistributedsystems.web.controller;

import com.ivanuil.scalabledistributedsystems.service.BankAccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankAccountController {

    private final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping("/deposit")
    public void deposit(@RequestParam int txId,
                        @RequestParam int accountId,
                        @RequestParam int amount) {
        bankAccountService.deposit(txId, accountId, amount);
    }

    @PostMapping("/withdraw")
    public void withdraw(@RequestParam int txId,
                         @RequestParam int accountId,
                         @RequestParam int amount) {
        bankAccountService.withdraw(txId, accountId, amount);
    }

    @GetMapping("/balance")
    public int balance(@RequestParam int accountId) {
        return bankAccountService.getBalance(accountId);
    }

}

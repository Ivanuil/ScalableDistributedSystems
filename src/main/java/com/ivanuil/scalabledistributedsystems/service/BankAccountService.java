package com.ivanuil.scalabledistributedsystems.service;

import com.ivanuil.scalabledistributedsystems.repository.BankAccountRepository;
import org.springframework.stereotype.Service;

@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public void deposit(int transactionId, int accountId, double amount) {
        bankAccountRepository.deposit(transactionId, accountId, amount);
    }

    public void withdraw(int transactionId, int accountId, double amount) {
        bankAccountRepository.withdraw(transactionId, accountId, amount);
    }

    public int getBalance(int accountId) {
        return bankAccountRepository.balance(accountId);
    }

}

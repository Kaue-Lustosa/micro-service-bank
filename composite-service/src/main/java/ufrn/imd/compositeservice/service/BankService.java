package ufrn.imd.compositeservice.service;

import ufrn.imd.compositeservice.CustomFeatures.BankException;
import ufrn.imd.compositeservice.model.Account;
import ufrn.imd.compositeservice.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankService {

    @Autowired
    private AccountRepository accountRepository;

    public double balance(String accountId) throws BankException {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new BankException("Non-existent account."));

        return account.getBalance();
    }

    public List<Account> findAll(){
        return accountRepository.findAll();
    }

    public String createAccountOp(String accountId) throws BankException {
        if (accountRepository.existsById(accountId)) {
            throw new BankException("Account creation error: this account already exists");
        }
        Account novaAccount = new Account(accountId, 0);
        accountRepository.save(novaAccount);
        return "Operation completed successfully!";
    }

    public String deleteAccountOp(String accountId) throws BankException {

        Account account = accountRepository.findById(accountId).orElseThrow(() -> new BankException("Non-existent account"));
        accountRepository.delete(account);

        return "Operation completed successfully!";
    }

    public String transferOp(String idOrigin, String idDestination, double value) throws BankException {
        Account origin = accountRepository.findById(idOrigin)
                .orElseThrow(() -> new BankException("Origin account not found!"));

        Account destination = accountRepository.findById(idDestination)
                .orElseThrow(() -> new BankException("Destination account not found!"));

        if (value <= 0) {
            throw new BankException("Invalid transfer amount!");
        }

        if (origin.getBalance() < value) {
            throw new BankException("Insufficient balance.");
        }

        origin.withdrawOp(value);
        destination.depositOp(value);

        accountRepository.save(origin);
        accountRepository.save(destination);

        return "Operation completed successfully!";
    }

    public String depositOp(String accountId, double value) throws BankException {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new BankException("Non-existent account."));

        if (value <= 0) {
            throw new BankException("Invalid deposit amount!");
        }

        account.depositOp(value);
        accountRepository.save(account);

        return "Operation completed successfully!";
    }
}
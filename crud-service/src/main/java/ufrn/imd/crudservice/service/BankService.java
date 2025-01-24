package ufrn.imd.crudservice.service;

import ufrn.imd.crudservice.CustomFeatures.BankException;
import ufrn.imd.crudservice.client.BankServiceClient;
import ufrn.imd.crudservice.dto.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {

    @Autowired
    private BankServiceClient bankServiceClient;

    public double balance(String accountId) throws BankException {
        Account account = bankServiceClient.findById(accountId)
                .orElseThrow(() -> new BankException("Non-existent account."));

        return account.getBalance();
    }

    public List<Account> findAll(){
        return bankServiceClient.findAll();
    }

    public String createAccountOp(String accountId) throws BankException {
        if (bankServiceClient.existsById(accountId)) {
            throw new BankException("Account creation error: this account already exists");
        }
        Account novaAccount = new Account(accountId, 0);
        bankServiceClient.save(novaAccount);
        return "Operation completed successfully!";
    }

    public void deleteAccountOp(String accountId) throws BankException {

        Account account = bankServiceClient.findById(accountId).orElseThrow(() -> new BankException("Non-existent account"));
        bankServiceClient.delete(account);

    }

    public String transferOp(String idOrigin, String idDestination, double value) throws BankException {
        Account origin = bankServiceClient.findById(idOrigin)
                .orElseThrow(() -> new BankException("Origin account not found!"));

        Account destination = bankServiceClient.findById(idDestination)
                .orElseThrow(() -> new BankException("Destination account not found!"));

        if (value <= 0) {
            throw new BankException("Invalid transfer amount!");
        }

        if (origin.getBalance() < value) {
            throw new BankException("Insufficient balance.");
        }

        origin.withdrawOp(value);
        destination.depositOp(value);

        bankServiceClient.save(origin);
        bankServiceClient.save(destination);

        return "Operation completed successfully!";
    }

    public String depositOp(String accountId, double value) throws BankException {
        Account account = bankServiceClient.findById(accountId)
                .orElseThrow(() -> new BankException("Non-existent account."));

        if (value <= 0) {
            throw new BankException("Invalid deposit amount!");
        }

        account.depositOp(value);
        bankServiceClient.save(account);

        return "Operation completed successfully!";
    }
}
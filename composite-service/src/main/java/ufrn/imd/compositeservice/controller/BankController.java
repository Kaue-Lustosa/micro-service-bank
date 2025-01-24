package ufrn.imd.compositeservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ufrn.imd.compositeservice.CustomFeatures.BankException;
import ufrn.imd.compositeservice.model.Account;
import ufrn.imd.compositeservice.service.BankService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bank")
public class BankController {

    @Autowired
    private final BankService bank;

    public BankController(BankService bank) {
        this.bank = bank;
    }

    @GetMapping("/public/welcome")
    public String welcome() {
        return "Welcome to Fall Bank!";
    }

    @GetMapping("/private/balance/{id}")
    public double balance(@PathVariable(value = "id", required = true) String id) throws BankException {
        double balance = 0;
        balance = bank.balance(id);
        return balance;
    }

    @PutMapping("/private/depositOp/{id}/{value}")
    public String deposit(@PathVariable(value = "id", required = true) String id, @PathVariable(value = "value", required = true) double value) throws BankException {
        return bank.depositOp(id, value);

    }

    @PutMapping("/private/transferOp/{idOrigin}/{idDestiny}/{value}")
    public String transferOp(@PathVariable String idOrigin, @PathVariable String idDestiny, @PathVariable double value) throws BankException {
        return bank.transferOp(idOrigin, idDestiny, value);
    }

    @PostMapping("/admin/createAccountOp")
    public void createAccountOp(@RequestBody Map<String, String> payload) throws BankException {
        String id = payload.get("id");
        bank.createAccountOp(id);
    }

    @DeleteMapping("/admin/deleteAccountOp/{id}")
    public String deleteAccountOp(@PathVariable(value = "id", required = true) String id) throws BankException {
        return bank.deleteAccountOp(id);
    }

    @GetMapping("/admin/listAccountsOp")
    public List<Account> findAll() {

        return bank.findAll();
    }
}
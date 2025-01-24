package ufrn.imd.crudservice.controller;

import ufrn.imd.crudservice.CustomFeatures.BankException;
import ufrn.imd.crudservice.dto.Account;
import ufrn.imd.crudservice.service.BankService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class AccountController {

    private final BankService bankService;

    public AccountController(BankService bankService) {
        this.bankService = bankService;
    }

    // Creates a new account
    @PostMapping
    public ResponseEntity<String> createAccountOp(@RequestBody String accountId) throws BankException {
        String created = bankService.createAccountOp(accountId);
        return ResponseEntity.ok(created);
    }

    // Obtain all accounts
    @GetMapping
    public ResponseEntity<List<Account>> findAll() {
        List<Account> accounts = bankService.findAll();
        return ResponseEntity.ok(accounts);
    }

    // Obtains account balance
    @GetMapping("/{id}")
    public ResponseEntity<Double> balance(@PathVariable String id) throws BankException {
        Optional<Double> account = Optional.of(bankService.balance(id));
        return account.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Transfer values between accounts
    @PutMapping("/{id}")
    public ResponseEntity<String> transferOp(@PathVariable String id, @RequestBody String idDestination, @RequestBody double value) throws BankException {
        Optional<String> updated = bankService.transferOp(id, idDestination, value).describeConstable();
        return updated.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Deposits a value in an account
    @PutMapping("/{id}")
    public ResponseEntity<String> depositOp(@PathVariable String id, @RequestBody double value) throws BankException {
        Optional<String> updated = bankService.depositOp(id, value).describeConstable();
        return updated.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete accounts
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccountOp(@PathVariable String id) throws BankException {
        bankService.deleteAccountOp(id);
        return ResponseEntity.noContent().build();
    }
}
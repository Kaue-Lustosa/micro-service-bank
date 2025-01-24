package ufrn.imd.crudservice.client;

import ufrn.imd.crudservice.dto.Account;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@FeignClient(name = "bank-service")
public interface BankServiceClient extends JpaRepository<Account, String> {

    // Circuit Breaker e Retry applied to account creation
    @CircuitBreaker(name = "bankServiceCircuitBreaker", fallbackMethod = "fallbackCreateAccount")
    @Retry(name = "dbServiceRetry", fallbackMethod = "fallbackCreateAccount")
    @PostMapping("/db/employees")
    Account createAccount(@RequestBody String account);

    // Circuit Breaker e Retry applied in the search of all accounts
    @CircuitBreaker(name = "bankServiceCircuitBreaker", fallbackMethod = "fallbackFindAll")
    @Retry(name = "dbServiceRetry", fallbackMethod = "fallbackFindAll")
    @GetMapping("/db/employees")
    List<Account> findAll();

    // Circuit Breaker e Retry applied to balance
    @CircuitBreaker(name = "bankServiceCircuitBreaker", fallbackMethod = "fallbackBalance")
    @Retry(name = "dbServiceRetry", fallbackMethod = "fallbackBalance")
    @GetMapping("/db/employees/{id}")
    Account balance(@PathVariable("id") String id);

    // Circuit Breaker e Retry applied to transfer operations
    @CircuitBreaker(name = "bankServiceCircuitBreaker", fallbackMethod = "fallbackTransferOp")
    @Retry(name = "dbServiceRetry", fallbackMethod = "fallbackTransferOp")
    @PutMapping("/db/employees/{id}")
    Account transferOp(@PathVariable("id") String idOrigin, String idDestination, double value);

    // Circuit Breaker e Retry applied to deposit operations
    @CircuitBreaker(name = "bankServiceCircuitBreaker", fallbackMethod = "fallbackDepositOp")
    @Retry(name = "dbServiceRetry", fallbackMethod = "fallbackDepositOp")
    @PutMapping("/db/employees/{id}")
    Account depositOp(@PathVariable("id") String id, double value);

    // Circuit Breaker e Retry applied to deleting accounts
    @CircuitBreaker(name = "bankServiceCircuitBreaker", fallbackMethod = "fallbackDeleteAccountOp")
    @Retry(name = "dbServiceRetry", fallbackMethod = "fallbackDeleteAccountOp")
    @DeleteMapping("/db/employees/{id}")
    void deleteAccountOp(@PathVariable("id") String id);

    // Métodos de fallback
    default Account fallbackCreateAccount(String account, Throwable t) {
        // Retorno padrão em caso de falha
        return new Account();  // Can return default or an error message
    }

    default List<Account> fallbackFindAll(Throwable t) {
        return List.of();  // Return an empty list in case of failure
    }

    default Account fallbackBalance(String id, Throwable t) {
        return new Account();  // Return a default value in case of failure
    }

    default Account fallbackTransferOp(String idOrigin, String idDestination, double value, Throwable t) {
        return new Account();  // Return a default value in case of failure
    }

    default Account fallbackDepositOp(String id, double value, Throwable t) {
        return new Account();  // Return a default value in case of failure
    }

    default void fallbackDeleteAccountOp(String id, Throwable t) {
        // Can return an error or an adequate value to failures
    }
}
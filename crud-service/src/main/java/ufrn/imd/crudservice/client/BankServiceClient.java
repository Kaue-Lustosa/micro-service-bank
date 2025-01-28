package ufrn.imd.crudservice.client;

import org.springframework.stereotype.Component;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(name = "composite-service")
public interface BankServiceClient{

    // Circuit Breaker e Retry applied to welcome string
    @CircuitBreaker(name = "bankServiceCircuitBreaker", fallbackMethod = "fallbackWelcome")
    @Retry(name = "BankServiceRetry", fallbackMethod = "fallbackWelcome")
    @GetMapping("/bank/public/welcome")
    String welcome();

    // Circuit Breaker e Retry applied to sum function
    @CircuitBreaker(name = "bankServiceCircuitBreaker", fallbackMethod = "fallbackSum")
    @Retry(name = "BankServiceRetry", fallbackMethod = "fallbackSum")
    @GetMapping("/bank/public/sum/{x}/{y}")
    double sum(@PathVariable double x,@PathVariable double y);

    // Circuit Breaker e Retry applied to sum function
    @CircuitBreaker(name = "bankServiceCircuitBreaker", fallbackMethod = "fallbackMultiplication")
    @Retry(name = "dbServiceRetry", fallbackMethod = "fallbackMultiplication")
    @GetMapping("/bank/public/multiplication/{x}/{y}")
    double multiplication(@PathVariable double x,@PathVariable double y);
}